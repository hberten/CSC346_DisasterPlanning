/*
Hunter Berten
CSC 354
This program allows the user to input a 5-digit zip code, followed by a distance in miles. It will then produce a list
of places (obtained from a specific database) that are within the specified number of miles from the specified zip code.
The name, state, population, housing units, and distance (in kilometers and miles) from the specified zip code will be
listed for each place.
*/

package com.company;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Prompts for and obtains the zip code and miles specified
//        Scanner input = new Scanner(System.in);
//        System.out.println("Enter a zip code:");
//        String zipCode = input.next();
//        System.out.println("Enter a distance in miles:");
//        int miles = Integer.parseInt(input.next());

        String host = "jdbc:mysql://turing.cs.missouriwestern.edu:3306/misc";
        String user = "csc254";
        String password = "age126";

        Connection conn;
        Statement st;
        ResultSet rs;

        try {
            conn = DriverManager.getConnection(host, user, password);

            if (conn == null) {
                System.err.println("Connection failed.");
                System.exit(2);
            }

            String queryString = "SELECT * from zips2 WHERE zipcode = '64506' AND locationtype = 'PRIMARY'";
            st = conn.createStatement();
            st.execute(queryString);
            rs = st.executeQuery(queryString);
            rs.next();

            double inputLat = rs.getDouble("lat");
            double inputLong = rs.getDouble("long");

            queryString = "SELECT * from zips2 WHERE locationtype = 'PRIMARY'";
            st = conn.createStatement();
            st.execute(queryString);
            rs = st.executeQuery(queryString);

            while (rs.next()) {
                double currentLat = rs.getDouble("lat");
                double currentLong = rs.getDouble("long");
                double distanceInKilos = calculateDistance(inputLat, currentLat, inputLong, currentLong);
                if (convertKilometersToMiles(distanceInKilos) > 10) continue;

                String name = rs.getString("city");
                String state = rs.getString("state");
                int population = rs.getInt("estimatedpopulation");
//                String housingUnits = rs.getString("city");

                System.out.printf("%s\t%s\t%d\n", name, state, population);
            }

            conn.close();
        } catch (SQLException e) {
            System.err.println("Failed to connect to database.");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static double convertKilometersToMiles(double kilometers) {
        return kilometers / 1.609344;
    }

    public static double convertMilesToKilometers(double miles) {
        return miles * 1.609344;
    }

    public static double calculateDistance(double lat1, double lat2, double lon1, double lon2) {
        int R = 6371;
        double φ1 = Math.toRadians(lat1);
        double φ2 =  Math.toRadians(lat2);
        double Δφ = Math.toRadians(lat2-lat1);
        double Δλ = Math.toRadians(lon2-lon1);

        double a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
                Math.cos(φ1) * Math.cos(φ2) *
                        Math.sin(Δλ/2) * Math.sin(Δλ/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R * c;
    }
}
