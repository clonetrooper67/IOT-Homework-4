package zephan.fedex;

import java.util.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {

    private static Connection connect = null;
    private static Statement statement = null;
    private static ResultSet resultSet = null;

    public static void readDatabase() throws Exception {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/fedex?" + "user=root&password=&serverTimezone=UTC");

//			Prepare the query, statement, and get the result set
            String query = "SELECT * FROM packages";
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
//				Get the data we want from each set
                int trackingNumber = rs.getInt("trackingNumber");
                int startCity = rs.getInt("startCity");
                int endCity = rs.getInt("endCity");
                int weight = rs.getInt("weight");

                boolean alreadySorted = rs.getBoolean("alreadySorted");

                String currentPath = rs.getString("currentPath");
                String fastestPath = rs.getString("fastestPath");
                String timeOfTravel = rs.getString("timeOfTravel");
//				Make the linked lists we need
                LinkedList<Integer> currentPathList = new LinkedList<Integer>();
                LinkedList<Integer> fastestPathList = new LinkedList<Integer>();
                LinkedList<Integer> timeOfTravelList = new LinkedList<Integer>();
//				Make string arrays the data received from the database
                String[] currentPathArray = currentPath.split(",");
                String[] fastestPathArray = fastestPath.split(",");
                String[] timeOfTravelArray = timeOfTravel.split(",");
//				Parse through each Array and push it into the list
                for (int i = 1; i < currentPathArray.length; i++) {
                    currentPathList.add(Integer.parseInt(currentPathArray[i]));
                }

                for (int i = 1; i < fastestPathArray.length; i++) {
                    fastestPathList.add(Integer.parseInt(fastestPathArray[i]));
                }

                for (int i = 1; i < timeOfTravelArray.length; i++) {
                    timeOfTravelList.add(Integer.parseInt(timeOfTravelArray[i]));
                }
//				Make a new package with the information
                Package parcel = new Package(trackingNumber, startCity, endCity, weight);

                parcel.setCurrentPath(currentPathList);
                parcel.setFastestPath(fastestPathList);
                parcel.setTimeOfTravel(timeOfTravelList);
                parcel.setAlreadySorted(alreadySorted);
//				Push the package to the main array
                Main.parcelArray.push(parcel);
            }

            st.close();

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    public static void writeDatabase(Package currentPackage) throws Exception {
        try {

            String currentPath = "";
            String fastestPath = "";
            String timeOfTravel = "";
//			Make the needed strings to write the lists to the database
            for (int i = 0; i < currentPackage.currentPath.size(); i++) {
                currentPath = currentPath + "," + Integer.toString(currentPackage.currentPath.get(i));
            }

            for (int i = 0; i < currentPackage.fastestPath.size(); i++) {
                fastestPath = fastestPath + "," + Integer.toString(currentPackage.fastestPath.get(i));
            }

            for (int i = 0; i < currentPackage.timeOfTravel.size(); i++) {
                timeOfTravel = timeOfTravel + "," + Integer.toString(currentPackage.timeOfTravel.get(i));
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/fedex?" + "user=root&password=&serverTimezone=UTC");
//			Prepare the query
            String query = " insert into packages (trackingNumber, startCity, endCity, weight, alreadySorted, currentPath, fastestPath, timeOfTravel)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, currentPackage.trackingNumber);
            preparedStatement.setInt(2, currentPackage.startCity);
            preparedStatement.setInt(3, currentPackage.endCity);
            preparedStatement.setInt(4, currentPackage.weight);
            preparedStatement.setBoolean(5, true);
            preparedStatement.setString(6, currentPath);
            preparedStatement.setString(7, fastestPath);
            preparedStatement.setString(8, timeOfTravel);

            // execute the statement
            preparedStatement.execute();

            connect.close();
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    public static void updateDatabase(Package currentPackage) throws Exception {

        String currentPath = "";
        String fastestPath = "";
        String timeOfTravel = "";
//		Make the needed strings to write the lists to the database
        for (int i = 0; i < currentPackage.currentPath.size(); i++) {
            currentPath = currentPath + "," + Integer.toString(currentPackage.currentPath.get(i));
        }

        for (int i = 0; i < currentPackage.fastestPath.size(); i++) {
            fastestPath = fastestPath + "," + Integer.toString(currentPackage.fastestPath.get(i));
        }

        for (int i = 0; i < currentPackage.timeOfTravel.size(); i++) {
            timeOfTravel = timeOfTravel + "," + Integer.toString(currentPackage.timeOfTravel.get(i));
        }

        Class.forName("com.mysql.cj.jdbc.Driver");

        connect = DriverManager
                .getConnection("jdbc:mysql://localhost/fedex?" + "user=root&password=&serverTimezone=UTC");

        String query = "update packages set currentPath = '" + currentPath + "' where trackingNumber = "
                + +currentPackage.getTrackingNumber() + ";";

        PreparedStatement preparedStatement = connect.prepareStatement(query);
        preparedStatement.execute();

        query = "update packages set fastestPath = '" + fastestPath + "' where trackingNumber = "
                + +currentPackage.getTrackingNumber() + ";";

        preparedStatement = connect.prepareStatement(query);
        preparedStatement.execute();

        query = "update packages set timeOfTravel = '" + timeOfTravel + "' where trackingNumber = "
                + +currentPackage.getTrackingNumber() + ";";

        preparedStatement = connect.prepareStatement(query);
        preparedStatement.execute();
        close();
    }

    public static void clearDatabase() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        connect = DriverManager
                .getConnection("jdbc:mysql://localhost/fedex?" + "user=root&password=&serverTimezone=UTC");
        String query = "truncate table packages";

        PreparedStatement preparedStatement = connect.prepareStatement(query);
        preparedStatement.execute();

        close();
    }

    private static void close() throws Exception {

        if (resultSet != null) {
            resultSet.close();
        }

        if (statement != null) {
            statement.close();
        }

        if (connect != null) {
            connect.close();
        }

    }
}
