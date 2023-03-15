import java.sql.* ;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


class Soccer
{
    public static void main ( String [ ] args ) throws SQLException {
        // Unique table names.  Either the user supplies a unique identifier as a command line argument, or the program makes one up.
        String tableName = "";
        int sqlCode = 0;      // Variable to hold SQLCODE
        String sqlState = "00000";  // Variable to hold SQLSTATE

        if (args.length > 0)
            tableName += args[0];
        else
            tableName += "exampletbl";

        // Register the driver.  You must register the driver before you can use it.
        try {
            DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
        } catch (Exception cnfe) {
            System.out.println("Class not found");
        }

        // This is the url you must use for DB2.
        //Note: This url may not valid now ! Check for the correct year and semester and server name.
        String url = "jdbc:db2://winter2023-comp421.cs.mcgill.ca:50000/cs421";

        //REMEMBER to remove your user id and password before submitting your code!!
        String your_userid = "Cs421g16";
        String your_password = "A5squ4d8rU5";
        //AS AN ALTERNATIVE, you can just set your password in the shell environment in the Unix (as shown below) and read it from there.
        //$  export SOCSPASSWD=yoursocspasswd
        if (your_userid == null && (your_userid = System.getenv("SOCSUSER")) == null) {
            System.err.println("Error!! do not have a password to connect to the database!");
            System.exit(1);
        }
        if (your_password == null && (your_password = System.getenv("SOCSPASSWD")) == null) {
            System.err.println("Error!! do not have a password to connect to the database!");
            System.exit(1);
        }
        Connection con = DriverManager.getConnection(url, your_userid, your_password);
        Statement statement = con.createStatement();

        mainMenu(statement);
        /*
        // Querying a table
        try
        {
            String querySQL = "SELECT * from Stadium";
            System.out.println (querySQL) ;
            java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;

            while ( rs.next ( ) )
            {
                String id = rs.getString ( 1 );
                System.out.println ("id:  " + id);
            }
            System.out.println ("DONE");
        }
        catch (SQLException e)
        {
            sqlCode = e.getErrorCode(); // Get SQLCODE
            sqlState = e.getSQLState(); // Get SQLSTATE

            // Your code to handle errors comes here;
            // something more meaningful than a print would be good
            System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            System.out.println(e);
        }


         */


        // Finally but importantly close the statement and connection
        statement.close ( ) ;
        con.close ( ) ;

        System.out.print("\nGoodbye!");
    }

    public static void mainMenu(Statement statement) {
        int sqlCode = 0;      // Variable to hold SQLCODE
        String sqlState = "00000";  // Variable to hold SQLSTATE
        boolean exit = false;
        Scanner input = new Scanner(System.in);
        while(!exit) {
            System.out.print("Soccer Main Menu");
            System.out.print("\n     1. List information of matches of a country" +
                    "\n     2. Insert initial player information for a match" +
                    "\n     3. Modify active players from a previous match" +
                    "\n     4. Exit application" +
                    "\nPlease Enter Your Option: ");

            if (input.hasNextInt()) {
                int option = input.nextInt();
                switch(option) {
                    case 1:
                        countryMatchInfo(sqlCode, sqlState, statement);
                        break;
                    case 2:
                        initialPlayerInfo(sqlCode, sqlState, statement);
                        break;
                    case 3:
                        manageSubstitutes(sqlCode, sqlState, statement);
                        break;
                    case 4:
                        exit = true;
                        break;
                    default:
                        System.out.print("ERROR: Please enter a valid option!");
                }
            }
        }
    }

    public static void countryMatchInfo(int sqlCode, String sqlState, Statement statement) {
        boolean exit = false;
        Scanner input = new Scanner(System.in);
        while(!exit) {
            System.out.print("\nPlease enter country name: ");
            String country = input.next();
            try {
                String querySQL = "SELECT COUNTRY1, COUNTRY2, ROUND, DATE, T1GOALS, T2GOALS, TICKETS_SOLD\n" +
                        "FROM\n" +
                        "    (SELECT MID, COUNTRY1, COUNTRY2, ROUND, DATE, T1GOALS, T2GOALS, COUNT(TICKET.MATCH_ID) AS TICKETS_SOLD\n" +
                        "     FROM\n" +
                        "         ((SELECT MID, COUNTRY1, COUNTRY2, ROUND, DATE, T1GOALS, COUNT(G2.COUNTRY) AS T2GOALS\n" +
                        "           FROM\n" +
                        "               (SELECT table2.MID, table2.COUNTRY1, table2.COUNTRY2, table2.ROUND, table2.DATE, COUNT(G1.COUNTRY) AS T1GOALS\n" +
                        "                FROM\n" +
                        "                    (SELECT table1.MATCH_ID AS MID, table1.COUNTRY1 AS COUNTRY1, MP.COUNTRY AS COUNTRY2, DATE, ROUND\n" +
                        "                     FROM\n" +
                        "                         (SELECT M.MATCH_ID, MIN(COUNTRY) AS COUNTRY1, M.DATE, M.ROUND\n" +
                        "                          FROM MATCH AS M LEFT OUTER JOIN MATCH_PARTICIPANTS AS MP2\n" +
                        "                                                          ON M.MATCH_ID = MP2.MATCH_ID\n" +
                        "                          where M.MATCH_ID in (select MATCH_ID from PLAYED_MATCH)\n" +
                        "                          GROUP BY M.MATCH_ID, M.DATE, M.ROUND) table1 LEFT OUTER JOIN MATCH_PARTICIPANTS AS MP\n" +
                        "                                                                                       ON table1.MATCH_ID = MP.MATCH_ID AND COUNTRY1 != MP.COUNTRY) table2\n" +
                        "                        LEFT OUTER JOIN GOAL AS G1\n" +
                        "                                        ON table2.MID = G1.MATCH_ID AND table2.COUNTRY1 = G1.COUNTRY\n" +
                        "                GROUP BY table2.MID, table2.COUNTRY1, table2.COUNTRY2, table2.ROUND, table2.DATE, G1.COUNTRY) table3\n" +
                        "                   LEFT OUTER JOIN GOAL AS G2\n" +
                        "                                   ON MID = G2.MATCH_ID AND COUNTRY2 = G2.COUNTRY\n" +
                        "           GROUP BY MID, COUNTRY1, COUNTRY2, ROUND, DATE, T1GOALS, G2.COUNTRY)\n" +
                        "          union\n" +
                        "          (SELECT MID, COUNTRY1, COUNTRY2, ROUND, DATE, T1GOALS, NULL AS T2GOALS\n" +
                        "           FROM\n" +
                        "               (SELECT table2.MID, table2.COUNTRY1, table2.COUNTRY2, table2.ROUND, table2.DATE, NULL AS T1GOALS\n" +
                        "                FROM\n" +
                        "                    (SELECT table1.MATCH_ID AS MID, table1.COUNTRY1 AS COUNTRY1, MP.COUNTRY AS COUNTRY2, DATE, ROUND\n" +
                        "                     FROM\n" +
                        "                         (SELECT M.MATCH_ID, MIN(COUNTRY) AS COUNTRY1, M.DATE, M.ROUND\n" +
                        "                          FROM MATCH AS M LEFT OUTER JOIN MATCH_PARTICIPANTS AS MP2\n" +
                        "                                                          ON M.MATCH_ID = MP2.MATCH_ID\n" +
                        "                          where M.MATCH_ID not in (select MATCH_ID from PLAYED_MATCH)\n" +
                        "                          GROUP BY M.MATCH_ID, M.DATE, M.ROUND) table1 LEFT OUTER JOIN MATCH_PARTICIPANTS AS MP\n" +
                        "                                                                                       ON table1.MATCH_ID = MP.MATCH_ID AND COUNTRY1 != MP.COUNTRY) table2\n" +
                        "                        LEFT OUTER JOIN GOAL AS G1\n" +
                        "                                        ON table2.MID = G1.MATCH_ID AND table2.COUNTRY1 = G1.COUNTRY\n" +
                        "                GROUP BY table2.MID, table2.COUNTRY1, table2.COUNTRY2, table2.ROUND, table2.DATE, G1.COUNTRY) table3\n" +
                        "                   LEFT OUTER JOIN GOAL AS G2\n" +
                        "                                   ON MID = G2.MATCH_ID AND COUNTRY2 = G2.COUNTRY\n" +
                        "           GROUP BY MID, COUNTRY1, COUNTRY2, ROUND, DATE, T1GOALS, G2.COUNTRY))table4\n" +
                        "             LEFT OUTER JOIN TICKET\n" +
                        "                             ON MID = TICKET.MATCH_ID AND TICKET.STATUS = 'SOLD'\n" +
                        "     GROUP BY MID, COUNTRY1, COUNTRY2, ROUND, DATE, T1GOALS, T2GOALS, TICKET.MATCH_ID)\n" +
                        "WHERE COUNTRY1 = '"+country+"' OR COUNTRY2 = '"+country+"'";
                java.sql.ResultSet rs = statement.executeQuery(querySQL);
                System.out.print("\n-----------------------------------------------------------------------------------" +
                        "-------------------------------");
                int rowCount = 0;
                System.out.printf("\n%-16s %-16s %-16s %-15s %-16s %-16s %-15s", "Country1", "Country2", "Round", "Date",
                        "Country1 Goals", "Country2 Goals", "Tickets Sold");
                System.out.print("\n-----------------------------------------------------------------------------------" +
                        "-------------------------------");
                while (rs.next()) {
                    String country1 = rs.getString("Country1");
                    String country2 = rs.getString("Country2");
                    String round = rs.getString("Round");
                    String date = rs.getString("Date");
                    String t1Goals = rs.getString("T1Goals");
                    String t2Goals = rs.getString("T2Goals");
                    int ticketsSold = rs.getInt("Tickets_Sold");

                    System.out.printf("\n%-16s %-16s %-16s %-15s %-16s %-16s %-15d", country1, country2, round, date, t1Goals, t2Goals,
                            ticketsSold);
                    rowCount++;
                }
                System.out.println("\nDONE with " + rowCount + " rows outputted.\n");
            } catch (SQLException e) {
                sqlCode = e.getErrorCode(); // Get SQLCODE
                sqlState = e.getSQLState(); // Get SQLSTATE

                // Your code to handle errors comes here;
                // something more meaningful than a print would be good
                System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
                System.out.println(e);
            }
            System.out.print("Enter [A] to find matches of another country, [P] to go to the previous menu: ");
            String option = input.next();
            if (option.equalsIgnoreCase("p")) {
                exit = true;
            }
        }
    }

    public static void initialPlayerInfo(int sqlCode, String sqlState, Statement statement) {



        class Local {
            public void menu1(int sqlCode, String sqlState, Statement statement){
                Scanner input = new Scanner(System.in);
                while(true){
                    LocalDate nowDate = LocalDate.now();
                    LocalDate nextDate = nowDate.plusDays(3);
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime myTime = LocalTime.parse(LocalTime.now().format(myFormatObj));

                    try {
                        String querySQL = "SELECT *  FROM (\n" +
                                "SELECT * FROM\n" +
                                "(SELECT  table1.MATCH_ID AS MID, table1.COUNTRY1 AS COUNTRY1, MP.COUNTRY AS COUNTRY2, DATE, ROUND\n" +
                                "FROM\n" +
                                "(SELECT MP2.MATCH_ID, MIN(COUNTRY) AS COUNTRY1, M.DATE, M.ROUND\n" +
                                "FROM MATCH_PARTICIPANTS AS MP2, MATCH AS M\n" +
                                "WHERE M.MATCH_ID = MP2.MATCH_ID\n" +
                                "GROUP BY MP2.MATCH_ID, M.DATE, M.ROUND) table1\n" +
                                "LEFT OUTER JOIN MATCH_PARTICIPANTS AS MP\n" +
                                "ON table1.MATCH_ID = MP.MATCH_ID AND COUNTRY1 != MP.COUNTRY ) table3 JOIN (SELECT MATCH_ID, TIME FROM MATCH) table4 ON table3.MID = table4.MATCH_ID\n" +
                                "WHERE (DATE >= '"+nowDate.toString() +"' AND TIME >= '"+myTime.toString()+"') OR DATE > '"+nowDate.toString()+"' AND COUNTRY1 IS NOT NULL AND COUNTRY2 IS NOT NULL)\n" +
                                "    table6 INTERSECT\n" +
                                "\n" +
                                "(SELECT * FROM\n" +
                                "    (SELECT  table1.MATCH_ID AS MID, table1.COUNTRY1 AS COUNTRY1, MP.COUNTRY AS COUNTRY2, DATE, ROUND\n" +
                                "     FROM\n" +
                                "         (SELECT MP2.MATCH_ID, MIN(COUNTRY) AS COUNTRY1, M.DATE, M.ROUND\n" +
                                "          FROM MATCH_PARTICIPANTS AS MP2, MATCH AS M\n" +
                                "          WHERE M.MATCH_ID = MP2.MATCH_ID\n" +
                                "          GROUP BY MP2.MATCH_ID, M.DATE, M.ROUND) table1\n" +
                                "             LEFT OUTER JOIN MATCH_PARTICIPANTS AS MP\n" +
                                "                             ON table1.MATCH_ID = MP.MATCH_ID AND COUNTRY1 != MP.COUNTRY ) table3 JOIN (SELECT MATCH_ID, TIME FROM MATCH) table4 ON table3.MID = table4.MATCH_ID\n" +
                                "WHERE (DATE <= '"+nextDate.toString() +"' AND TIME <= '"+myTime.toString()+"') OR DATE < '"+nextDate.toString() +"' AND COUNTRY1 IS NOT NULL AND COUNTRY2 IS NOT NULL)";

                        java.sql.ResultSet rs = statement.executeQuery(querySQL);
                        System.out.println("\nCurrent Date: " +nowDate.toString());
                        System.out.println("Current Time: " +myTime.toString());
                        System.out.println("\nMatches within next 72 hours:");

                        while (rs.next()) {
                            int mid = rs.getInt("MID");
                            String country1 = rs.getString("Country1");
                            String country2 = rs.getString("Country2");
                            String date = rs.getString("Date");
                            String round = rs.getString("Round");

                            System.out.printf("%d %-16s %-16s %-16s %-16s\n", mid, country1, country2, date, round);

                        } // while rs.next()

                        System.out.print("\nEnter [Match Identifier] you wish to insert into or [P] to go to the previous menu: ");
                        String option = input.next();
                        System.out.println(option);
                        if (option.equalsIgnoreCase("p")) {
                            break;
                        }

                        String matchIdentifier = option;

                        System.out.print("Enter [Country Name] you wish to insert into or [P] to go to the previous menu: ");
                        option = input.next();
                        if (option.equalsIgnoreCase("p")) {
                            break;
                        }

                        String matchCountry = option;
                        menu2(sqlCode, sqlState, statement, matchIdentifier,matchCountry);

                    }catch (SQLException e) {
                        sqlCode = e.getErrorCode(); // Get SQLCODE
                        sqlState = e.getSQLState(); // Get SQLSTATE

                        // Your code to handle errors comes here;
                        // something more meaningful than a print would be good
                        System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
                        System.out.println(e);
                    } // try catch
                } // exit
            } // menu1

            public void menu2(int sqlCode, String sqlState, Statement statement, String matchIdentifier, String matchCountry){
                boolean exit = false;
                Scanner input = new Scanner(System.in);
                while(true){

                    try {
                        String querySQL = "SELECT NAME, table1.SHIRT_NUM AS SHIRT_NUM, SPECIFIC_POS, ENTRY_TIME,EXIT_TIME, NVL(MAX(YELLOW_OCCURRANCE),0) AS YELLOW, NVL(SUM(IS_RED),0) as RED FROM\n" +
                                "(SELECT NAME, P.SHIRT_NUM AS SHIRT_NUM, SPECIFIC_POS, ENTRY_TIME, EXIT_TIME, P.COUNTRY AS COUNTRY, MATCH_ID\n" +
                                "FROM PLAYSIN JOIN PLAYER P on PLAYSIN.COUNTRY = P.COUNTRY and PLAYSIN.SHIRT_NUM = P.SHIRT_NUM\n" +
                                "WHERE PLAYSIN.COUNTRY = '" + matchCountry + "' AND PLAYSIN.MATCH_ID = " + matchIdentifier + ") table1\n" +
                                "LEFT OUTER JOIN BOOKING ON table1.SHIRT_NUM = BOOKING.SHIRT_NUM AND BOOKING.COUNTRY = table1.COUNTRY AND BOOKING.MATCH_ID = table1.MATCH_ID\n" +
                                "group by NAME, SPECIFIC_POS, ENTRY_TIME, EXIT_TIME  , table1.SHIRT_NUM\n";
                        java.sql.ResultSet rs = statement.executeQuery(querySQL);

                        System.out.println("The following players from "+matchCountry+" are already entered for match "+matchIdentifier +":");

                        int playerCount = 0;
                        while (rs.next()) {
                            playerCount++;
                            String name = rs.getString("Name");
                            int shirt = rs.getInt("Shirt_num");
                            String pos = rs.getString("Specific_pos");
                            String enter = rs.getString("Entry_time");
                            String leave = rs.getString("Exit_time");
                            int yellow = rs.getInt("YELLOW");
                            int red = rs.getInt("RED");


                            System.out.printf("%-16s %d %-16s form: %-16s to: %-16s yellow: %d red: %d\n", name, shirt, pos, enter, leave,yellow, red);

                        } // while rs.next()



                        querySQL = "SELECT NAME, SHIRT_NUM, GENERAL_POSITION FROM PLAYER\n" +
                                "WHERE COUNTRY = '" +matchCountry + "' and SHIRT_NUM NOT IN (SELECT PLAYSIN.SHIRT_NUM FROM PLAYSIN\n" +
                                "                                                WHERE COUNTRY = '"+ matchCountry+"' AND MATCH_ID = "+ matchIdentifier+ ")";
                        rs = statement.executeQuery(querySQL);

                        System.out.println("Possible players not yet selected:");

                        ArrayList addable = new ArrayList();
                        int counter = 0;

                        while (rs.next()) {

                            counter++;
                            String name = rs.getString("Name");
                            int shirt = rs.getInt("Shirt_num");
                            String pos = rs.getString("GENERAL_POSITION");
                            ArrayList myTuple = new ArrayList();
                            myTuple.add(matchCountry);
                            myTuple.add(shirt);
                            myTuple.add(matchIdentifier);
                            myTuple.add(pos);
                            myTuple.add("00:00:00");
                            addable.add(myTuple);
                            System.out.printf("%d %-16s %d %-16s \n", counter, name, shirt, pos);

                        } // while rs.next()

                        System.out.println("Enter the [number] (not shirt number) of the player you want to insert or [P]\n" +
                                "to go to the previous menu.");
                        String option = input.next();
                        if (option.equalsIgnoreCase("p")) {
                            break;
                        }
                        int maxp = 3;
                        if (playerCount >= maxp){
                            System.out.println("FAILED TO INSERT, TEAM IS FULL (MAX PLAYERS = " + maxp +" ).");
                            menu2(sqlCode, sqlState, statement, matchIdentifier,matchCountry);
                            break;
                        }
                        int selection = Integer.parseInt(option);
                        selection--;
                        ArrayList addTuple = (ArrayList) addable.get(selection);

                        System.out.println("Enter the [position] of the player you want to insert or [P]\n" +
                                "to go to the previous menu.");
                        option = input.next();
                        if (option.equalsIgnoreCase("p")) {
                            break;
                        }

                        String insertSQL = "INSERT INTO PlaysIn (country, shirt_num, match_id, specific_pos, entry_time, exit_time) VALUES ('" +
                                matchCountry+"',"+addTuple.get(1)+","+matchIdentifier+",'"+option.toString()+"','00:00:00', NULL)" ;

                        statement.executeUpdate ( insertSQL ) ;

                        String removeSQL = "DELETE FROM PLAYSIN WHERE COUNTRY = '" + addTuple.get(0) +"' AND SHIRT_NUM = " + addTuple.get(1).toString();

                    }catch (SQLException e) {
                        sqlCode = e.getErrorCode(); // Get SQLCODE
                        sqlState = e.getSQLState(); // Get SQLSTATE

                        // Your code to handle errors comes here;
                        // something more meaningful than a print would be good
                        System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
                        System.out.println(e);
                    } // try catch
                } // exit

            } // menu 2
        } // end Local Class

        // run the menu
        new Local().menu1(sqlCode, sqlState, statement);

    } // end initialPlayerInfo

    public static void manageSubstitutes(int sqlCode, String sqlState, Statement statement) {

        class Local {
            public void menu1(int sqlCode, String sqlState, Statement statement){
                Scanner input = new Scanner(System.in);
                while(true){
                    LocalDate nowDate = LocalDate.now();
                    LocalDate nextDate = nowDate.minusDays(3);
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalTime myTime = LocalTime.parse(LocalTime.now().format(myFormatObj));

                    try {
                        String querySQL = "SELECT *  FROM (\n" +
                                "SELECT * FROM\n" +
                                "(SELECT  table1.MATCH_ID AS MID, table1.COUNTRY1 AS COUNTRY1, MP.COUNTRY AS COUNTRY2, DATE, ROUND\n" +
                                "FROM\n" +
                                "(SELECT MP2.MATCH_ID, MIN(COUNTRY) AS COUNTRY1, M.DATE, M.ROUND\n" +
                                "FROM MATCH_PARTICIPANTS AS MP2, MATCH AS M\n" +
                                "WHERE M.MATCH_ID = MP2.MATCH_ID\n" +
                                "GROUP BY MP2.MATCH_ID, M.DATE, M.ROUND) table1\n" +
                                "LEFT OUTER JOIN MATCH_PARTICIPANTS AS MP\n" +
                                "ON table1.MATCH_ID = MP.MATCH_ID AND COUNTRY1 != MP.COUNTRY ) table3 JOIN (SELECT MATCH_ID, TIME FROM MATCH) table4 ON table3.MID = table4.MATCH_ID\n" +
                                "WHERE (DATE >= '"+nextDate.toString() +"' AND TIME >= '"+myTime.toString()+"') OR DATE > '"+nextDate.toString()+"' AND COUNTRY1 IS NOT NULL AND COUNTRY2 IS NOT NULL)\n" +
                                "    table6 INTERSECT\n" +
                                "\n" +
                                "(SELECT * FROM\n" +
                                "    (SELECT  table1.MATCH_ID AS MID, table1.COUNTRY1 AS COUNTRY1, MP.COUNTRY AS COUNTRY2, DATE, ROUND\n" +
                                "     FROM\n" +
                                "         (SELECT MP2.MATCH_ID, MIN(COUNTRY) AS COUNTRY1, M.DATE, M.ROUND\n" +
                                "          FROM MATCH_PARTICIPANTS AS MP2, MATCH AS M\n" +
                                "          WHERE M.MATCH_ID = MP2.MATCH_ID\n" +
                                "          GROUP BY MP2.MATCH_ID, M.DATE, M.ROUND) table1\n" +
                                "             LEFT OUTER JOIN MATCH_PARTICIPANTS AS MP\n" +
                                "                             ON table1.MATCH_ID = MP.MATCH_ID AND COUNTRY1 != MP.COUNTRY ) table3 JOIN (SELECT MATCH_ID, TIME FROM MATCH) table4 ON table3.MID = table4.MATCH_ID\n" +
                                "WHERE (DATE <= '"+nowDate.toString() +"' AND TIME <= '"+myTime.toString()+"') OR DATE < '"+nowDate.toString() +"' AND COUNTRY1 IS NOT NULL AND COUNTRY2 IS NOT NULL)";

                        java.sql.ResultSet rs = statement.executeQuery(querySQL);
                        System.out.println("\nCurrent Date: " +nowDate.toString());
                        System.out.println("Current Time: " +myTime.toString());
                        System.out.println("\nMatches within previous 72 hours:");

                        while (rs.next()) {
                            int mid = rs.getInt("MID");
                            String country1 = rs.getString("Country1");
                            String country2 = rs.getString("Country2");
                            String date = rs.getString("Date");
                            String round = rs.getString("Round");

                            System.out.printf("%d %-16s %-16s %-16s %-16s\n", mid, country1, country2, date, round);

                        } // while rs.next()

                        System.out.print("Enter [Match Identifier] you wish to insert into or [P] to go to the previous menu: ");
                        String option = input.next();
                        System.out.println(option);
                        if (option.equalsIgnoreCase("p")) {
                            break;
                        }

                        String matchIdentifier = option;

                        System.out.print("Enter [Country Name] you wish to insert into or [P] to go to the previous menu: ");
                        option = input.next();
                        if (option.equalsIgnoreCase("p")) {
                            break;
                        }

                        String matchCountry = option;
                        menu2(sqlCode, sqlState, statement, matchIdentifier,matchCountry);

                    }catch (SQLException e) {
                        sqlCode = e.getErrorCode(); // Get SQLCODE
                        sqlState = e.getSQLState(); // Get SQLSTATE

                        // Your code to handle errors comes here;
                        // something more meaningful than a print would be good
                        System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
                        System.out.println(e);
                    } // try catch
                } // exit
            } // menu1

            public void menu2(int sqlCode, String sqlState, Statement statement, String matchIdentifier, String matchCountry){
                boolean exit = false;
                Scanner input = new Scanner(System.in);
                while(true){

                    try {
                        String querySQL = "SELECT NAME, table1.SHIRT_NUM AS SHIRT_NUM, SPECIFIC_POS, ENTRY_TIME,EXIT_TIME, NVL(MAX(YELLOW_OCCURRANCE),0) AS YELLOW, NVL(SUM(IS_RED),0) as RED FROM\n" +
                                "(SELECT NAME, P.SHIRT_NUM AS SHIRT_NUM, SPECIFIC_POS, ENTRY_TIME, EXIT_TIME, P.COUNTRY AS COUNTRY, MATCH_ID\n" +
                                "FROM PLAYSIN JOIN PLAYER P on PLAYSIN.COUNTRY = P.COUNTRY and PLAYSIN.SHIRT_NUM = P.SHIRT_NUM\n" +
                                "WHERE PLAYSIN.COUNTRY = '" + matchCountry + "' AND PLAYSIN.MATCH_ID = " + matchIdentifier + ") table1\n" +
                                "LEFT OUTER JOIN BOOKING ON table1.SHIRT_NUM = BOOKING.SHIRT_NUM AND BOOKING.COUNTRY = table1.COUNTRY AND BOOKING.MATCH_ID = table1.MATCH_ID\n" +
                                "group by NAME, SPECIFIC_POS, ENTRY_TIME, EXIT_TIME  , table1.SHIRT_NUM\n";
                        java.sql.ResultSet rs = statement.executeQuery(querySQL);

                        System.out.println("\nThe following players from "+matchCountry+" are already entered for match "+matchIdentifier +":");
                        System.out.println("Chose those with exit_time = NULL and enter a valid exit_time.");
                        System.out.println("Type [MAX] to quick select end of match. Or another time.");
                        System.out.println("If you do not select MAX, you will be prompted with a substitute player to swap with (if exits, else will fail).");
                        System.out.println("You can enter [P] to cancel an individual operation and go back to previous menu.");

                        ArrayList selectable = new ArrayList();
                        int playerNumber =0;
                        while (rs.next()) {

                            String name = rs.getString("Name");
                            int shirt = rs.getInt("Shirt_num");
                            String pos = rs.getString("Specific_pos");
                            String enter = rs.getString("Entry_time");
                            String leave = rs.getString("Exit_time");
                            int yellow = rs.getInt("YELLOW");
                            int red = rs.getInt("RED");

                            if (leave == null ){
                                playerNumber++;
                                ArrayList myTuple = new ArrayList();
                                myTuple.add(shirt);
                                myTuple.add(pos);
                                selectable.add(myTuple);

                                System.out.printf("%d %-16s %d %-16s from: %-16s to: %-16s yellow: %d red: %d\n",playerNumber, name, shirt, pos, enter, leave,yellow, red);
                            }else{

                                System.out.printf("%s %-16s %d %-16s from: %-16s to: %-16s yellow: %d red: %d\n","-", name, shirt, pos, enter, leave,yellow, red);
                            }

                        } // while rs.next()

                        System.out.println("\nEnter the [number] (not shirt number) of the player you want to add exit_time to or [P]\n" +
                                "to go to the previous menu.");
                        String option = input.next();
                        if (option.equalsIgnoreCase("p")) {
                            break;
                        }

                        int selection = Integer.parseInt(option);
                        selection--;
                        ArrayList addTuple = (ArrayList) selectable.get(selection);

                        // get query to know max time for that match
                        querySQL = "SELECT DURATION FROM PLAYED_MATCH WHERE MATCH_ID = " + matchIdentifier ;
                        rs = statement.executeQuery(querySQL);

                        String maxTime = null;
                        while(rs.next()){
                            maxTime = rs.getString("DURATION");
                        }


                        LocalTime maxDuration = LocalTime.parse(maxTime);
                        LocalTime chosenDuration = LocalTime.parse("23:59:59");

                        while (chosenDuration.isAfter(maxDuration)){
                            System.out.println("Enter exit_time :");
                            option = input.next();


                            if (option.equalsIgnoreCase("p")) {
                                break;
                            }
                            if (option.equalsIgnoreCase("max")) {
                                chosenDuration = maxDuration;
                                break;
                            }
                            chosenDuration = LocalTime.parse(option);
                            if (chosenDuration.equals(maxDuration) || chosenDuration.isBefore(maxDuration) ){
                                break;
                            }
                            System.out.println("Bad selection");
                            System.out.println("Enter exit_time");
                        }

                        if (option.equalsIgnoreCase("p")) {
                            break;
                        }

                        // if time = max then insert into table
                        // and loop back
                        // else need to query leftover players if exist
                        if (maxDuration.equals(chosenDuration)){
                            // insert into table with max out

                            String updateSQL = "UPDATE PLAYSIN SET EXIT_TIME = '"+chosenDuration.toString()+"' WHERE SHIRT_NUM = "+addTuple.get(0)+" AND COUNTRY = '" + matchCountry +"' AND MATCH_ID = "+ matchIdentifier;
                            statement.executeUpdate(updateSQL);
                            // New exit_time should be applied


                        } else {
                            // need to chose replacement before completing the query even if it is the same as above

                            querySQL = "SELECT NAME, SHIRT_NUM, GENERAL_POSITION FROM PLAYER\n" +
                                    "WHERE COUNTRY = '" +matchCountry + "' and SHIRT_NUM NOT IN (SELECT PLAYSIN.SHIRT_NUM FROM PLAYSIN\n" +
                                    "                                                WHERE COUNTRY = '"+ matchCountry+"' AND MATCH_ID = "+ matchIdentifier+ ")";
                            rs = statement.executeQuery(querySQL);

                            System.out.println("Possible players to sub in:");

                            ArrayList addable = new ArrayList();
                            int counter = 0;

                            while (rs.next()) {

                                counter++;
                                String name = rs.getString("Name");
                                int shirt = rs.getInt("Shirt_num");
                                String pos = rs.getString("GENERAL_POSITION");
                                ArrayList myTuple = new ArrayList();
                                myTuple.add(matchCountry);
                                myTuple.add(shirt);
                                myTuple.add(matchIdentifier);
                                myTuple.add(pos);
                                myTuple.add("00:00:00");
                                addable.add(myTuple);
                                System.out.printf("%d %-16s %d %-16s \n", counter, name, shirt, pos);

                            } // while rs.next()

                            System.out.println("Enter the [number] (not shirt number) of the player you want to insert or [P]\n" +
                                    "to go to the previous menu.");
                            option = input.next();
                            if (option.equalsIgnoreCase("p")) {
                                break;
                            }
                            selection = Integer.parseInt(option);
                            selection--;
                            ArrayList addTuple1 = (ArrayList) addable.get(selection);


                            String insertSQL = "INSERT INTO PlaysIn (country, shirt_num, match_id, specific_pos, entry_time, exit_time) VALUES ('" +
                                    matchCountry+"',"+addTuple1.get(1).toString()+","+matchIdentifier+",'"+addTuple1.get(1)+"','"+chosenDuration.toString()+"', NULL)" ;

                            statement.executeUpdate ( insertSQL ) ;

                            String updateSQL = "UPDATE PLAYSIN SET EXIT_TIME = '"+chosenDuration.toString()+"' WHERE SHIRT_NUM = "+addTuple.get(0)+" AND COUNTRY = '" + matchCountry +"' AND MATCH_ID = "+ matchIdentifier;
                            statement.executeUpdate(updateSQL);


                        } // else
                    }catch (SQLException e) {
                        sqlCode = e.getErrorCode(); // Get SQLCODE
                        sqlState = e.getSQLState(); // Get SQLSTATE

                        // Your code to handle errors comes here;
                        // something more meaningful than a print would be good
                        System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
                        System.out.println(e);
                    } // try catch
                } // exit
            } // menu 2
        } // end Local Class

        // run the menu
        new Local().menu1(sqlCode, sqlState, statement);

    }
}
