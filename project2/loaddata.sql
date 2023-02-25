-- Include your INSERT SQL statements in this file.
-- Make sure to terminate each statement with a semicolon (;)

-- LEAVE this statement on. It is required to connect to your database.
CONNECT TO cs421;

-- Remember to put the INSERT statements for the tables with foreign key references
--    ONLY AFTER the parent tables!



INSERT INTO Stadium (stadium_name, capacity, location) VALUES
                                                           ("Olympic Stadium", 61,004, "Montreal, Quebec, Canada"),
                                                           ("Commonwealth Stadium", 56302, "11000 Stadium Road Edmonton, Alberta, Canada"),
                                                           ("BC Place", 54320, "777 Pacific Boulevard Vancouver, British Columbia"),
                                                           ("Rogers Centre", 47568, "Toronto, Ontario"),
                                                           ("McMahon Stadium", 37317, "Calgary, Alberta, Canada")
;

INSERT INTO Match (match_id, date, time, round, stadium_name) VALUES
                                                                  (100, "05/06/23", "12:00:00", "group", "Olympic Stadium"),
                                                                  (101, "05/06/23", "15:00:00", "group", "BC Place"),
                                                                  (102, "05/07/23", "12:00:00", "group", "McMahon Stadium"),
                                                                  (103, "05/07/23", "15:00:00", "group", "Commonswealth Stadium"),
                                                                  (104, "05/13/23", "12:00:00", "group", "Rogers Centre"),
                                                                  (105, "05/13/23", "15:00:00", "group", "BC Place"),
                                                                  (106, "05/14/23", "12:00:00", "group", "Rogers Centre"),
                                                                  (107, "05/14/23", "15:00:00", "group", "Commonswealth Stadium")
;

INSERT INTO Seat (seat_id, stadium_name) VALUES
                                             ("0001", "Olympic Stadium"),
                                             ("0002", "Olympic Stadium"),
                                             ("0003", "Olympic Stadium"),
                                             ("0004", "Olympic Stadium"),
                                             ("0005", "Olympic Stadium"),
                                             ("1A", "BC Place"),
                                             ("1B", "BC Place"),
                                             ("1C", "BC Place"),
                                             ("2A", "BC Place"),
                                             ("2B", "BC Place"),
                                             ("140", "McMahon Stadium"),
                                             ("150", "McMahon Stadium"),
                                             ("160", "McMahon Stadium"),
                                             ("170", "McMahon Stadium"),
                                             ("180", "McMahon Stadium"),
                                             ("0001", "Commonswealth Stadium"),
                                             ("0002", "Commonswealth Stadium"),
                                             ("0003", "Commonswealth Stadium"),
                                             ("0004", "Commonswealth Stadium"),
                                             ("0005", "Commonswealth Stadium"),
                                             ("1A", "Rogers Centre"),
                                             ("1B", "Rogers Centre"),
                                             ("1C", "Rogers Centre"),
                                             ("1D", "Rogers Centre"),
                                             ("1E", "Rogers Centre")
;

INSERT INTO Ticket (seat_id, stadium_name, match_id, price, status) VALUES
                                                                        ("0001", "Olympic Stadium", 100, 50.99, "AVAILABLE"),
                                                                        ("0002", "Olympic Stadium", 100, 50.99, "AVAILABLE"),
                                                                        ("0003", "Olympic Stadium", 100, 40.99, "SOLD"),
                                                                        ("0004", "Olympic Stadium", 100, 40.99, "SOLD"),
                                                                        ("0005", "Olympic Stadium", 100, 40.99, "SOLD"),
                                                                        ("1A", "BC Place", 101, 50.99, "AVAILABLE"),
                                                                        ("1B", "BC Place", 101, 50.99, "AVAILABLE"),
                                                                        ("1C", "BC Place", 101, 50.99, "SOLD"),
                                                                        ("2A", "BC Place", 101, 30.99, "SOLD"),
                                                                        ("2B", "BC Place", 101, 30.99, "AVAILABLE"),
                                                                        ("1A", "BC Place", 105, 50.99, "SOLD"),
                                                                        ("1B", "BC Place", 105, 50.99, "SOLD"),
                                                                        ("1C", "BC Place", 105, 50.99, "SOLD"),
                                                                        ("2A", "BC Place", 105, 30.99, "AVAILABLE"),
                                                                        ("2B", "BC Place", 105, 30.99, "AVAILABLE"),
                                                                        ("140", "McMahon Stadium", 102, 60.99, "SOLD"),
                                                                        ("150", "McMahon Stadium", 102, 50.99, "SOLD"),
                                                                        ("160", "McMahon Stadium", 102, 40.99, "SOLD"),
                                                                        ("170", "McMahon Stadium", 102, 30.99, "SOLD"),
                                                                        ("180", "McMahon Stadium", 102, 30.99, "SOLD"),
                                                                        ("0001", "Commonswealth Stadium", 103, 50.99, "AVAILABLE"),
                                                                        ("0002", "Commonswealth Stadium", 103, 50.99, "AVAILABLE"),
                                                                        ("0003", "Commonswealth Stadium", 103, 40.99, "SOLD"),
                                                                        ("0004", "Commonswealth Stadium", 103, 40.99, "SOLD"),
                                                                        ("0005", "Commonswealth Stadium", 103, 40.99, "SOLD"),
                                                                        ("0001", "Commonswealth Stadium", 107, 50.99, "AVAILABLE"),
                                                                        ("0002", "Commonswealth Stadium", 107, 50.99, "AVAILABLE"),
                                                                        ("0003", "Commonswealth Stadium", 107, 40.99, "AVAILABLE"),
                                                                        ("0004", "Commonswealth Stadium", 107, 40.99, "SOLD"),
                                                                        ("0005", "Commonswealth Stadium", 107, 40.99, "SOLD"),
                                                                        ("1A", "Rogers Centre", 104, 50.99, "SOLD"),
                                                                        ("1B", "Rogers Centre", 104, 50.99, "SOLD"),
                                                                        ("1C", "Rogers Centre", 104, 50.99, "SOLD"),
                                                                        ("1D", "Rogers Centre", 104, 50.99, "SOLD"),
                                                                        ("1E", "Rogers Centre", 104, 50.99, "AVAILABLE"),
                                                                        ("1A", "Rogers Centre", 106, 50.99, "AVAILABLE"),
                                                                        ("1B", "Rogers Centre", 106, 50.99, "AVAILABLE"),
                                                                        ("1C", "Rogers Centre", 106, 50.99, "AVAILABLE"),
                                                                        ("1D", "Rogers Centre", 106, 50.99, "AVAILABLE"),
                                                                        ("1E", "Rogers Centre", 106, 50.99, "AVAILABLE")
;

INSERT INTO Client (email, first_name, last_name) VALUES
                                                      ("miguel.chandra@gmail.com", "Miguel", "Chandra"),
                                                      ("sadiq.miaa@yahoo.com", "Sadiq", "Miia"),
                                                      ("timotheus.amada@gmail.com", "Timotheus", "Amada"),
                                                      ("amelina.rainer@gmail.com", "Amelina", "Rainer"),
                                                      ("moana.jalil@gmail.com", "Moana", "Jalil"),
                                                      ("amable.archippe@outlook.com", "Amable", "Archippe"),
                                                      ("aziz.niloufar@gmail.com", "Aziz", "Niloufar")
;

INSERT INTO Receipt (transaction_id, total_cost, email) VALUES
                                                            (70089, 122.97, "miguel.chandra@gmail.com"),
                                                            (70090, 81.98, "sadiq.miaa@yahoo.com"),
                                                            (70091, 152.97, "timotheus.amada@gmail.com"),
                                                            (70092, 214.95, "amelina.rainer@gmail.com"),
                                                            (70093, 122.97, "moana.jalil@gmail.com"),
                                                            (70094, 81.98, "amable.archippe@outlook.com"),
                                                            (70095, 203.96, "aziz.niloufar@gmail.com")
;

INSERT INTO Transaction (seat_id, stadium_name, match_id, transaction_id) VALUES
                                                                              ("0003", "Olympic Stadium", 100, 70089),
                                                                              ("0004", "Olympic Stadium", 100, 70089),
                                                                              ("0005", "Olympic Stadium", 100, 70089),
                                                                              ("1C", "BC Place", 101, 70090),
                                                                              ("2A", "BC Place", 101, 70090),
                                                                              ("1A", "BC Place", 105, 70091),
                                                                              ("1B", "BC Place", 105, 70091),
                                                                              ("1C", "BC Place", 105, 70091),
                                                                              ("140", "McMahon Stadium", 102, 70092),
                                                                              ("150", "McMahon Stadium", 102, 70092),
                                                                              ("160", "McMahon Stadium", 102, 70092),
                                                                              ("170", "McMahon Stadium", 102, 70092),
                                                                              ("180", "McMahon Stadium", 102, 70092),
                                                                              ("0003", "Commonswealth Stadium", 103, 70093),
                                                                              ("0004", "Commonswealth Stadium", 103, 70093),
                                                                              ("0005", "Commonswealth Stadium", 103, 70093),
                                                                              ("0004", "Commonswealth Stadium", 107, 70094),
                                                                              ("0005", "Commonswealth Stadium", 107, 70094),
                                                                              ("1A", "Rogers Centre", 104, 70095),
                                                                              ("1B", "Rogers Centre", 104, 70095),
                                                                              ("1C", "Rogers Centre", 104, 70095),
                                                                              ("1D", "Rogers Centre", 104, 70095)
;

INSERT INTO Team (country, name, website_url, group) VALUES
                                                         ("Germany", "Deutsche Fußballnationalmannschaft der Frauen", "https://www.dfb.de/en/en-start/", "A"),
                                                         ("France", "Équipe de France féminine de football", "https://uk.fff.fr/", "A"),
                                                         ("Spain", "Selección Española de Fútbol Femenina", "https://rfef.es/es", "A"),
                                                         ("Portugal", "A Selecção das Quinas", "https://www.fpf.pt/pt/", "A"),
                                                         ("Canada", "Équipe du Canada féminine de soccer", "https://canadasoccer.com/", "A")
;



INSERT INTO Match_Participants (match_id, country) VALUES
                                                       (100, "Germany"),
                                                       (100, "France"),
                                                       (101, "Germany"),
                                                       (101, "Spain"),
                                                       (102, "Germany"),
                                                       (102, "Portugal"),
                                                       (103, "Germany"),
                                                       (103, "Canada"),
                                                       (104, "France"),
                                                       (104, "Spain"),
                                                       (105, "France"),
                                                       (105, "Portugal"),
                                                       (106, "Canada"),
                                                       (106, "France"),
                                                       (107, "Spain"),
                                                       (107, "Portugal")
;

INSERT INTO Played_Match (match_id, duration) VALUES
                                                  (100, "00:96:47"),
                                                  (101, "00:93:23"),
                                                  (102, "00:92:03"),
                                                  (103, "00:97:34"),
                                                  (104, "00:91:27")
;


INSERT INTO Player (country, shirt_num, name, dob, general_position) VALUES
                                                                         ("Germany", 1, "Aliana Conner", "06/19/1994", "goalkeeper"),
                                                                         ("Germany", 6, "Abbigail Pratt", "03/18/1993", "defender"),
                                                                         ("Germany", 7, "Willow Soto", "05/01/2002", "midfielder"),
                                                                         ("Germany", 11, "Christine Sinclair", "07/18/2003", "striker"),
                                                                         ("France", 1, "Laci Walker", "05/27/1993", "goalkeeper"),
                                                                         ("France", 4, "Tiana Sanford", "10/17/1994", "defender"),
                                                                         ("France", 3, "Stacy Johnston", "11/28/1996", "midfielder"),
                                                                         ("France", 10, "Jewel Santana", "11/26/1995", "striker"),
                                                                         ("Spain", 1, "Elliana Burns", "12/19/1996", "goalkeeper"),
                                                                         ("Spain", 9, "Briley Owens", "12/20/1999", "defender"),
                                                                         ("Spain", 5, "Fatima Wiggins", "12/18/1996", "midfielder"),
                                                                         ("Spain", 14, "Ashanti Sims", "08/23/1997", "striker"),
                                                                         ("Portugal", 1, "Amari Payne", "02/07/1995", "goalkeeper"),
                                                                         ("Portugal", 6, "Iyana Mcfarland", "12/03/1998", "defender"),
                                                                         ("Portugal", 15, "Rubi Arroyo", "03/30/2000", "midfielder"),
                                                                         ("Portugal", 11, "Tiffany Huerta", "05/09/2000", "striker"),
                                                                         ("Canada", 1, "Daisy Salas", "10/29/1995", "goalkeeper"),
                                                                         ("Canada", 6, "Mya Bautista", "10/06/1994", "defender"),
                                                                         ("Canada", 3, "Melina Graves", "07/26/1993", "midfielder"),
                                                                         ("Canada", 16, "Emmalee Boone", "04/25/1997", "striker")
;

INSERT INTO Coach (country, role, name, dob) VALUES
                                                 ("Germany", "head coach", "Regan Roman", "06/19/1994"),
                                                 ("Germany", "defensive coach", "Colt Webb", "03/18/1993"),
                                                 ("Germany", "attacking coach", "Austin Weiss", "05/01/2002"),
                                                 ("France", "head coach", "Lilianna Quinn", "07/18/2003"),
                                                 ("France", "defensive coach", "Camryn Curtis", "05/27/1993"),
                                                 ("France", "attacking coach", "Mckenzie Macias", "10/17/1994"),
                                                 ("Spain", "head coach", "Rubi Malone", "11/28/1996"),
                                                 ("Spain", "defensive coach", "Kellen Rangel", "11/26/1995"),
                                                 ("Spain", "attacking coach", "Crystal Mcintosh", "12/19/1996"),
                                                 ("Portugal", "head coach", "Tess Jacobs", "12/20/1999"),
                                                 ("Portugal", "defensive coach", "Conor Adkins", "12/18/1996"),
                                                 ("Portugal", "attacking coach", "Ayana Russo", "08/23/1997"),
                                                 ("Canada", "head coach", "Damion Norton", "02/07/1995"),
                                                 ("Canada", "defensive coach", "Cassius Martin", "12/03/1998"),
                                                 ("Canada", "attacking coach", "Kyler Petersen", "03/30/2000")
;


INSERT INTO Referee (referee_id, name, experience, nationality) VALUES
                                                                    (3000, "Jazlynn Nolan", 7, "Irish"),
                                                                    (3001, "Bob Marley", 3, "Portugese"),
                                                                    (3002, "Mckenna Davis", 13, "American"),
                                                                    (3003, "Isla Sanford", 4, "English"),
                                                                    (3004, "Tessa Garza", 10, "Spanish"),
                                                                    (3005, "Alina Chambers", 7, "Canadian"),
                                                                    (3006, "Arely Frederique", 6, "French"),
                                                                    (3007, "Stacy Adams", 3, "American")
;

INSERT INTO PlaysIn (country, shirt_num, match_id, specific_pos, entry_time, exit_time) VALUES
                                                                                            ("Germany", 1, 100, "goalkeeper", "00:00:00", "00:96:47"),
                                                                                            ("Germany", 6, 100, "center back", "00:00:00", "00:73:21"),
                                                                                            ("Germany", 11, 100, "center forward", "00:00:00", "00:96:47"),
                                                                                            ("France", 1, 100, "goalkeeper", "00:00:00", "00:96:47"),
                                                                                            ("France", 10, 100, "center forward", "00:00:00", "00:96:47"),
                                                                                            ("France", 3, 100, "left mid", "00:00:00", "00:96:47"),
                                                                                            ("Germany", 1, 101, "goalkeeper", "00:00:00", "00:93:23"),
                                                                                            ("Germany", 6, 101, "center back", "00:57:32", "00:93:23"),
                                                                                            ("Germany", 7, 101, "center mid", "00:00:00", "00:57:31"),
                                                                                            ("Germany", 11, 101, "center forward", "00:00:00", "00:93:23"),
                                                                                            ("Spain", 1, 101, "goalkeeper", "00:00:00", "00:93:23"),
                                                                                            ("Spain", 9, 101, "right back", "00:00:00", "00:93:23"),
                                                                                            ("Spain", 5, 101, "left back", "00:32:16", "00:93:23"),
                                                                                            ("Spain", 14, 101, "center mid", "00:00:00", "00:32:15"),
                                                                                            ("Germany", 1, 102, "goalkeeper", "00:00:00", "00:92:03"),
                                                                                            ("Germany", 6, 102, "center back", "00:00:00", "00:92:03"),
                                                                                            ("Germany", 11, 102, "center forward", "00:00:00", "00:92:03"),
                                                                                            ("Portugal", 1, 102, "goalkeeper", "00:00:00", "00:92:03"),
                                                                                            ("Portugal", 6, 102, "center back", "00:00:00", "00:92:03"),
                                                                                            ("Portugal", 15, 102, "left winger", "00:46:13", "00:92:03"),
                                                                                            ("Portugal", 11, 102, "right winger", "00:00:00", "00:46:12"),
                                                                                            ("Germany", 1, 103, "goalkeeper", "00:00:00", "00:97:34"),
                                                                                            ("Germany", 7, 103, "center back", "00:00:00", "00:97:34"),
                                                                                            ("Germany", 11, 103, "center forward", "00:00:00", "00:97:34"),
                                                                                            ("Canada", 1, 103, "goalkeeper", "00:00:00", "00:97:34"),
                                                                                            ("Canada", 6, 103, "center back", "00:63:12", "00:97:34"),
                                                                                            ("Canada", 3, 103, "center mid", "00:00:00", "00:63:11"),
                                                                                            ("Canada", 16, 103, "center forward", "00:00:00", "00:97:34"),
                                                                                            ("France", 1, 104, "goalkeeper", "00:00:00", "00:91:27"),
                                                                                            ("France", 3, 104, "left back", "00:00:00", "00:91:27"),
                                                                                            ("France", 10, 104, "right back", "00:00:00", "00:91:27"),
                                                                                            ("Spain", 1, 104, "goalkeeper", "00:00:00", "00:91:27"),
                                                                                            ("Spain", 9, 104, "left winger", "00:00:00", "00:91:27"),
                                                                                            ("Spain", 14, 104, "right winger", "00:00:00", "00:67:24")
;


INSERT INTO Goal (match_id, occurrence, minute, is_penalty, country, shirt_num) VALUES
                                                                                    (100, 1, 30, 0, "Germany", 11),
                                                                                    (100, 2, 45, 0, "Germany", 11),
                                                                                    (100, 3, 53, 0, "France", 10),
                                                                                    (100, 4, 60, 1, "France", 3),
                                                                                    (100, 5, 73, 0, "Germany", 11),
                                                                                    (101, 1, 33, 0, "Germany", 11),
                                                                                    (101, 2, 50, 0, "Spain", 5),
                                                                                    (101, 3, 63, 0, "Spain", 5),
                                                                                    (101, 4, 85, 0, "Germany", 6),
                                                                                    (102, 1, 5, 1, "Portugal", 6),
                                                                                    (102, 2, 34, 1, "Germany", 11),
                                                                                    (102, 3, 79, 0, "Germany", 11),
                                                                                    (103, 1, 23, 0, "Germany", 11),
                                                                                    (103, 2, 31, 0, "Germany", 11),
                                                                                    (103, 3, 45, 1, "Germany", 11),
                                                                                    (103, 4, 77, 0, "Germany", 7),
                                                                                    (104, 1, 34, 0, "Spain", 14)
;

INSERT INTO Refereeship (referee_id, match_id, role) VALUES
                                                         (3002, 100, "head referee"),
                                                         (3001, 100, "assistant referee"),
                                                         (3000, 100, "assistant referee"),
                                                         (3005, 101, "head referee"),
                                                         (3003, 101, "assistant referee"),
                                                         (3006, 101, "assistant referee"),
                                                         (3002, 102, "head referee"),
                                                         (3000, 102, "assistant referee"),
                                                         (3003, 102, "assistant referee"),
                                                         (3004, 103, "head referee"),
                                                         (3001, 103, "assistant referee"),
                                                         (3006, 103, "assistant referee"),
                                                         (3005, 104, "head referee"),
                                                         (3006, 104, "assistant referee"),
                                                         (3003, 104, "assistant referee")
;

INSERT INTO Booking (match_id, occurrence, country, shirt_num, referee_id, is_red, yellow_occurance) VALUES
                                                                                                         (100, 1, "Germany", 6, 3002, 0, 1),
                                                                                                         (100, 2, "Germany", 6, 3002, 0, 2),
                                                                                                         (101, 1, "Germany", 1, 3005, 0, 1),
                                                                                                         (101, 2, "Spain", 5, 3005, 0, 2),
                                                                                                         (102, 1, "Canada", 6, 3002, 0, 1),
                                                                                                         (103, 1, "Canada", 6, 3004, 0, 1),
                                                                                                         (104, 1, "Spain", 14, 3005, 1, )
;






















































































































































































































































































































































































































































































































































































































































































































































































