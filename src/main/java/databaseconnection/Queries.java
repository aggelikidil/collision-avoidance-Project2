package databaseconnection;

import parameter.ParameterSettings;

import java.sql.*;

public class Queries {
    private ParameterSettings settings;

    public Queries(ParameterSettings settings) {
        this.settings = settings;
    }

    /**
     * anazitisi sthn vasi dedomenon
     * @param terminal_1    proti siskeui
     * @param terminal_2    deuterisiskei
     * @param proximity_value   timi tou proximity
     * @param minx      min timi tou aksona x gia to accelerometer
     * @param maxx      max timi tou aksona x gia to accelerometer
     * @param miny      min timi tou aksona y gia to accelerometer
     * @param maxy      max timi tou aksona y gia to accelerometer
     * @param minz      min timi tou aksona z gia to accelerometer
     * @param maxz      max timi tou aksona z gia to accelerometer
     * @param min_latitude  min timi gia to geometriko mikos
     * @param max_latitude  max timi gia to geometriko mikos
     * @param min_longitude min timi gia to geometriko platos
     * @param max_longitude max timi gia to geometriko platos
     * @param calendar_from from this date
     * @param calendar_to   to that date
     * @param set_time_from from this time
     * @param set_time_to   to that time
     * @param confirmed_collision   epivevaiomenes sigkrouseis
     * @param unconfirmed_collision mi epivevaiomenes sigkrouseis
     * @return table   pinakas apotelesmaton
     */
    public SampleTable search (String terminal_1, String terminal_2, Float proximity_value, Float minx, Float maxx, Float miny, Float maxy, Float minz, Float maxz, Float min_latitude, Float max_latitude, Float min_longitude, Float max_longitude, String calendar_from, String calendar_to, String set_time_from, String set_time_to, Boolean confirmed_collision, Boolean unconfirmed_collision) {

        Connection connection = null;

        try {
            //connect to database
            connection = DriverManager.getConnection("jdbc:mysql://" + settings.mysql_ip +"/" + settings.mysql_databasename, settings.mysql_username, settings.mysql_password);

            //create a statement
            Statement stmt = connection.createStatement();

            //create sql statement
            String sql;

            if (terminal_1 == null && terminal_2 == null && proximity_value == null && (minx == null || maxx==null) && (miny == null || maxy == null) &&  (minz == null || maxz == null) &&  (min_latitude == null ||  max_latitude == null) &&  (min_longitude == null ||
                    max_longitude == null) && (calendar_from == null || calendar_to == null) &&
                    (set_time_from ==null || set_time_to==null) &&  confirmed_collision == null && unconfirmed_collision == null) {
                sql = "SELECT * FROM collisions "; //
            } else {
                sql = "SELECT * FROM collisions where"; //

                int counter = 0;

                if (terminal_1 != null || terminal_2 != null) {
                    if (terminal_1 == null) {
                        if (counter != 0) {
                            sql += " and ";
                        }
                        counter++;
                        sql += " terminal_id like '" + terminal_2 + "'";
                    }

                    if (terminal_2 == null) {
                        if (counter != 0) {
                            sql += " and ";
                        }
                        counter++;
                        sql += " terminal_id like '" + terminal_1 + "'";
                    }


                    if (terminal_1 != null && terminal_2 != null) {
                        if (counter != 0) {
                            sql += " and ";
                        }
                        counter++;
                        sql += " (terminal_id like '" + terminal_1 + "' or terminal_id like '" + terminal_2 + "')";
                    }
                }

                if (proximity_value !=null) {
                   if (counter != 0) {
                        sql += " and ";

                   }
                   counter++;
                   sql += " sensorvalue1 = " + proximity_value;

                }

                if (minx != null && maxx != null) {
                    if (counter != 0) {
                        sql += " and ";
                    }
                    counter++;
                    sql += " (sensorvalue2x BETWEEN " + minx + " AND " + maxx  + ")" ;

                }

                if (miny != null && maxy != null) {

                    if (counter != 0) {
                        sql += " and ";
                    }
                    counter++;
                    sql += " (sensorvalue2y BETWEEN " + miny + " AND " + maxy  + ")" ;

                }


                if (minz != null && maxz != null) {

                    if (counter != 0) {
                        sql += " and ";
                    }
                    counter++;
                    sql += " (sensorvalue2z BETWEEN " + minz + " AND  " + maxz  + ")" ;

                }

                if (min_latitude != null && max_latitude != null) {

                    if (counter != 0) {
                        sql += " and ";
                    }
                    counter++;
                    sql += " (latitude BETWEEN " + min_latitude + " AND " + max_latitude  + ")" ;

                }

                if (min_longitude != null && max_longitude != null) {

                    if (counter != 0) {
                        sql += " and ";
                    }
                    counter++;
                    sql += " (longtitude BETWEEN " + min_longitude + " AND " + max_longitude  + ")" ;

                }

                if (calendar_from != null && calendar_to != null) {

                    if (counter != 0) {
                        sql += " and ";
                    }
                    counter++;
                    sql += " ( date_time BETWEEN '" + calendar_from + "' AND '" + calendar_to  + "')" ;

                }

                //if (set_time_from != null && set_time_to != null) {

                  //  if (counter != 0) {
                    //   sql += " and ";
                    //}
                   //counter++;

                    //sql += "select * from collisions where cast(date_time as time) between cast('1:00' as time) and cast('12:00' as time)"

//                   String t0 = "cast('" + set_time_from + "' as time)";
  //                  String t1 = "cast('" + set_time_to + "' as time)";

    //               sql += " ( cast(date_time as time) BETWEEN " + t0 + " AND " + t1  + ")" ;
//
  //              }

                if (confirmed_collision != null || unconfirmed_collision != null) {
                    if (confirmed_collision == null && unconfirmed_collision != null) {
                        if (counter != 0) {
                            sql += " and ";
                        }
                        counter++;
                        sql += " confirmed_collision = " + false ;
                    }

                    if (confirmed_collision != null && unconfirmed_collision == null) {
                        if (counter != 0) {
                            sql += " and ";
                        }
                        counter++;
                        sql += " confirmed_collision = " + true ;
                    }


                    if (confirmed_collision != null && unconfirmed_collision != null) {
                        if (counter != 0) {
                            sql += " and ";
                        }
                        counter++;
                        sql += " (confirmed_collision = " + true + " or confirmed_collision = " + false + ")";
                    }
                }

                if (confirmed_collision == null && unconfirmed_collision == null) {
                    if (counter != 0) {
                        sql += " and ";
                    }
                    counter++;
                    sql += " (confirmed_collision = " + true + " or confirmed_collision = " + false + ")";                }

            }

            sql += " order by `date_time` desc ";
            System.out.println("Sql query is " + sql);


            // send query to database:
            ResultSet rs = stmt.executeQuery(sql);

            // read results from database and store to RAM

            SampleTable table = new SampleTable();

            while (true) {
                boolean nextlineExists = rs.next();

                if (nextlineExists) {
                    SampleLine line = new SampleLine();

                    line.LINE_id = rs.getInt("LINE_id");
                    line.terminal_id = rs.getString("terminal_id");
                    line.sensortype1 = rs.getString("sensortype1");
                    line.no_of_values1 = rs.getInt("no_of_values1");
                    line.sensorvalue1 = rs.getFloat("sensorvalue1");
                    line.unit_sensor1 = rs.getString("unit_sensor1");
                    line.sensortype2 = rs.getString("sensortype2");
                    line.no_of_values2 = rs.getInt("no_of_values2");
                    line.sensorvalue2x = rs.getFloat("sensorvalue2x");
                    line.sensorvalue2y = rs.getFloat("sensorvalue2y");
                    line.sensorvalue2z = rs.getFloat("sensorvalue2z");
                    line.unit_sensor2 = rs.getString("unit_sensor2");
                    line.datetime = rs.getTimestamp("date_time");
                    line.latitude = rs.getFloat("latitude");
                    line.longtitude = rs.getFloat("longtitude");
                    line.confirmed_collision = rs.getBoolean("confirmed_collision");

                    table.lines.add(line);
                } else {
                    break;
                }
            }

            rs.close();
            stmt.close();
            connection.close();

            return table;
        } catch (Exception e) {

            try {
                connection.close();
            } catch (Exception ex) {

            }
            e.printStackTrace();
            return null;
        }
    }

    /**
     * eisagogi stin vasi dedomenon
     * @param terminal siskeui
     * @param sensortype1 proximity
     * @param no_of_values1 1
     * @param proximity_value timi tou proximity
     * @param unit_sensor1 cm
     * @param sensortype2 accelerometer
     * @param no_of_values2 3
     * @param x timi aksona x
     * @param y timi aksona y
     * @param z timi aksona z
     * @param unit_sensor2 m/s^2
     * @param latitude geometriko mikos
     * @param longitude geometriko platos
     * @param datetime hmerominia kai ora
     * @param confirmed_collision epivevaiomeni sigkroysi
     * @return pk to primary key ths grammhs
     *
     */
    public int insert(String terminal,String sensortype1, Integer no_of_values1, Float proximity_value, String unit_sensor1, String sensortype2, int no_of_values2, Float x, Float y,
                          Float z,  String unit_sensor2, Float latitude,Float longitude, java.util.Date datetime, Boolean confirmed_collision) {
        Connection connection = null;

        try {
            // connect to database
            connection = DriverManager.getConnection("jdbc:mysql://" + settings.mysql_ip +"/" + settings.mysql_databasename, settings.mysql_username, settings.mysql_password);

            //create sql statement
            String sql;

            sql = "INSERT INTO `database`.`collisions` " +
                    "(`terminal_id`," +
                    "`sensortype1`," +
                    "`no_of_values1`," +
                    "`sensorvalue1`," +
                    "`unit_sensor1`," +
                    "`sensortype2`," +
                    "`no_of_values2`," +
                    "`sensorvalue2x`," +
                    "`sensorvalue2y`," +
                    "`sensorvalue2z`," +
                    "`unit_sensor2`," +
                    "`date_time`," +
                    "`latitude`," +
                    "`longtitude`," +
                    "`confirmed_collision`)" +
                    "VALUES" +
                    "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            // Step 2 - create a statement
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, terminal);
            stmt.setString(2, sensortype1);
            stmt.setInt(3, no_of_values1);
            stmt.setFloat(4, proximity_value);
            stmt.setString(5, unit_sensor1);
            stmt.setString(6, sensortype2);
            stmt.setInt(7, no_of_values2);
            stmt.setFloat(8, x);
            stmt.setFloat(9, y);
            stmt.setFloat(10, z);
            stmt.setString(11, unit_sensor2);
            stmt.setTimestamp(12, new java.sql.Timestamp(datetime.getTime()));
            stmt.setFloat(13, latitude);
            stmt.setFloat(14, longitude);
            stmt.setBoolean(15, confirmed_collision);

//            System.out.println("Sql query is " + stmt);

            // send query to database:
            int affected = stmt.executeUpdate();

            stmt.close();

            if (affected == 1) {
                // retrieve the Primary key of last inserted line
                sql = "SELECT LINE_id FROM collisions WHERE terminal_id = ? and date_time=(SELECT Max(date_time) FROM collisions where terminal_id = ?)";

                PreparedStatement stmt2 = connection.prepareStatement(sql);
                stmt2.setString(1, terminal);
                stmt2.setString(2, terminal);

                ResultSet set = stmt2.executeQuery();
                set.next();

                int pk = set.getInt("LINE_id");
                stmt2.close();

                connection.close();
                return pk;
            } else {
                connection.close();
                return -1;
            }
        } catch (Exception e) {

            try {
                connection.close();
            } catch (Exception ex) {

            }
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * ananeosi tis timis tou confirmed collisions
     * @param line_id to id tis grammis pou tha ananeothei
     * @return true/false
     */
    public boolean update(int line_id) {
        Connection connection = null;

        try {
            //connect to database
            connection = DriverManager.getConnection("jdbc:mysql://" + settings.mysql_ip +"/" + settings.mysql_databasename, settings.mysql_username, settings.mysql_password);

            // create sql statement
            String sql;

            sql = "UPDATE `database`.`collisions` set confirmed_collision = true where LINE_id = ?";

            // create a statement
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, line_id);

            System.out.println("Sql query is " + stmt);

            // send query to database:
            int affected = stmt.executeUpdate();

            stmt.close();
            connection.close();

            if (affected == 1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();

            try {
                connection.close();
            } catch (Exception ex) {

            }

            return false;
        }
    }
}
