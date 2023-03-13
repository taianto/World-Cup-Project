import java.sql.* ;
import java.util.Scanner;
import java.util.List;



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
                    "\n     3. For you to design" +
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
                        "         (SELECT MID, COUNTRY1, COUNTRY2, ROUND, DATE, T1GOALS, COUNT(G2.COUNTRY) AS T2GOALS\n" +
                        "          FROM\n" +
                        "              (SELECT table2.MID, table2.COUNTRY1, table2.COUNTRY2, table2.ROUND, table2.DATE, COUNT(G1.COUNTRY) AS T1GOALS\n" +
                        "               FROM\n" +
                        "                   (SELECT table1.MATCH_ID AS MID, table1.COUNTRY1 AS COUNTRY1, MP.COUNTRY AS COUNTRY2, DATE, ROUND\n" +
                        "                    FROM\n" +
                        "                        (SELECT MP2.MATCH_ID, MIN(COUNTRY) AS COUNTRY1, M.DATE, M.ROUND\n" +
                        "                         FROM MATCH_PARTICIPANTS AS MP2, MATCH AS M\n" +
                        "                         WHERE M.MATCH_ID = MP2.MATCH_ID\n" +
                        "                         GROUP BY MP2.MATCH_ID, M.DATE, M.ROUND) table1 LEFT OUTER JOIN MATCH_PARTICIPANTS AS MP\n" +
                        "                                                                                        ON table1.MATCH_ID = MP.MATCH_ID AND COUNTRY1 != MP.COUNTRY) table2\n" +
                        "                       LEFT OUTER JOIN GOAL AS G1 ON table2.MID = G1.MATCH_ID AND table2.COUNTRY1 = G1.COUNTRY\n" +
                        "               GROUP BY table2.MID, table2.COUNTRY1, table2.COUNTRY2, table2.ROUND, table2.DATE, G1.COUNTRY) table3\n" +
                        "                  LEFT OUTER JOIN GOAL AS G2 ON MID = G2.MATCH_ID AND COUNTRY2 = G2.COUNTRY\n" +
                        "          GROUP BY MID, COUNTRY1, COUNTRY2, ROUND, DATE, T1GOALS, G2.COUNTRY) table4\n" +
                        "             LEFT OUTER JOIN TICKET\n" +
                        "                             ON MID = TICKET.MATCH_ID AND TICKET.STATUS = 'SOLD'\n" +
                        "     GROUP BY MID, COUNTRY1, COUNTRY2, ROUND, DATE, T1GOALS, T2GOALS, TICKET.MATCH_ID)\n" +
                        "WHERE COUNTRY1 = '" + country + "' OR COUNTRY2 = '" + country + "'";
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
                    int t1Goals = rs.getInt("T1Goals");
                    int t2Goals = rs.getInt("T2Goals");
                    int ticketsSold = rs.getInt("Tickets_Sold");
                    System.out.printf("\n%-16s %-16s %-16s %-15s %-16d %-16d %-15d", country1, country2, round, date, t1Goals, t2Goals,
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
        //String sampleDate = "2023-05-13";
        boolean exit = false;
        Scanner input = new Scanner(System.in);
        while(!exit) {

            try {
                String querySQL = "SELECT * FROM\n" +
                        "(SELECT  table1.MATCH_ID AS MID, table1.COUNTRY1 AS COUNTRY1, MP.COUNTRY AS COUNTRY2, DATE, ROUND\n" +
                        "FROM\n" +
                        "(SELECT MP2.MATCH_ID, MIN(COUNTRY) AS COUNTRY1, M.DATE, M.ROUND\n" +
                        "FROM MATCH_PARTICIPANTS AS MP2, MATCH AS M\n" +
                        "WHERE M.MATCH_ID = MP2.MATCH_ID\n" +
                        "GROUP BY MP2.MATCH_ID, M.DATE, M.ROUND) table1\n" +
                        "LEFT OUTER JOIN MATCH_PARTICIPANTS AS MP\n" +
                        "ON table1.MATCH_ID = MP.MATCH_ID AND COUNTRY1 != MP.COUNTRY ) table3\n" +
                        "WHERE DATE BETWEEN '" + "2023-05-07" + "' AND '" + "2023-05-14" + "' AND COUNTRY1 IS NOT NULL AND COUNTRY2 IS NOT NULL";

                java.sql.ResultSet rs = statement.executeQuery(querySQL);
                System.out.println("Matches :");

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
                if (option.equalsIgnoreCase("p")) {
                    exit = true;
                }

                String matchIdentifier = option;

                System.out.print("Enter [Country Name] you wish to insert into or [P] to go to the previous menu: ");
                option = input.next();
                if (option.equalsIgnoreCase("p")) {
                    exit = true;
                }

                String matchCountry = option;

                querySQL = "SELECT NAME, table1.SHIRT_NUM AS SHIRT_NUM, SPECIFIC_POS, ENTRY_TIME,EXIT_TIME, NVL(MAX(YELLOW_OCCURRANCE),0) AS YELLOW, NVL(SUM(IS_RED),0) as RED FROM\n" +
                        "(SELECT NAME, P.SHIRT_NUM AS SHIRT_NUM, SPECIFIC_POS, ENTRY_TIME, EXIT_TIME, P.COUNTRY AS COUNTRY, MATCH_ID\n" +
                        "FROM PLAYSIN JOIN PLAYER P on PLAYSIN.COUNTRY = P.COUNTRY and PLAYSIN.SHIRT_NUM = P.SHIRT_NUM\n" +
                        "WHERE PLAYSIN.COUNTRY = '" + matchCountry + "' AND PLAYSIN.MATCH_ID = " + matchIdentifier + ") table1\n" +
                        "LEFT OUTER JOIN BOOKING ON table1.SHIRT_NUM = BOOKING.SHIRT_NUM AND BOOKING.COUNTRY = table1.COUNTRY AND BOOKING.MATCH_ID = table1.MATCH_ID\n" +
                        "group by NAME, SPECIFIC_POS, ENTRY_TIME, EXIT_TIME  , table1.SHIRT_NUM\n";
                rs = statement.executeQuery(querySQL);

                System.out.println("The following players from "+matchCountry+" are already entered for match "+matchIdentifier +":");

                int playerCount = 0;
                while (rs.next()) {
                    playerCount++;
                    String name = rs.getString("Name");
                    int shirt = rs.getInt("Shirt_num");
                    String pos = rs.getString("Specific_pos");
                    String enter = rs.getString("Entry_time");
                    String leave = rs.getString("Exit_time");
                    int yellow = rs.getInt("Shirt_num");
                    int red = rs.getInt("Shirt_num");


                    System.out.printf("%-16s %d %-16s %-16s %-16s\n", name, shirt, pos, enter, leave,yellow, red);

                } // while rs.next()



                querySQL = "SELECT NAME, SHIRT_NUM, GENERAL_POSITION FROM PLAYER\n" +
                        "WHERE COUNTRY = '" +matchCountry + "' and SHIRT_NUM NOT IN (SELECT PLAYSIN.SHIRT_NUM FROM PLAYSIN\n" +
                        "                                                WHERE COUNTRY = '"+ matchCountry+"' AND MATCH_ID = "+ matchIdentifier+ ")";
                rs = statement.executeQuery(querySQL);

                System.out.println("Possible players not yet selected:");

                int iterator = 0;
                while (rs.next()) {
                    iterator++;
                    String name = rs.getString("Name");
                    int shirt = rs.getInt("Shirt_num");
                    String pos = rs.getString("GENERAL_POSITION");


                    System.out.printf("%d %-16s %d %-16s \n", iterator, name, shirt, pos);

                } // while rs.next()

                System.out.println("Enter the number of the player you want to insert or [P]\n" +
                        "to go to the previous menu.");
                option = input.next();
                if (option.equalsIgnoreCase("p")) {
                    exit = true;
                }




            } catch (SQLException e) {
                sqlCode = e.getErrorCode(); // Get SQLCODE
                sqlState = e.getSQLState(); // Get SQLSTATE

                // Your code to handle errors comes here;
                // something more meaningful than a print would be good
                System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
                System.out.println(e);
            } // try catch
        } // while !exit
    }

    public static void manageSubstitutes() {

    }
}