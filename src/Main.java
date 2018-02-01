/*
Hunter Berten
CSC 354
This program allows the user to input a 5-digit zip code, followed by a distance in miles. It will then produce a list
of places (obtained from a specific database) that are within the specified number of miles from the specified zip code.
The name, state, population, housing units, and distance (in kilometers and miles) from the specified zip code will be
listed for each place.
*/

import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Prompts for and obtains the zip code and miles specified
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a zip code:");
        String zipCode = input.next();
        System.out.println("Enter a distance in miles:");
        int miles = Integer.parseInt(input.next());

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

            // Obtains the place with the inputted zip code, and gets its lat and long
            String queryString = "SELECT * from zips2 WHERE zipcode = '" + zipCode + "' AND locationtype = 'PRIMARY'";
            st = conn.createStatement();
            st.execute(queryString);
            rs = st.executeQuery(queryString);
            rs.next();
            double inputLat = rs.getDouble("lat");
            double inputLong = rs.getDouble("long");

            // Obtains the rest of the records
            queryString = "SELECT * from zips2 WHERE locationtype = 'PRIMARY'";
            st = conn.createStatement();
            st.execute(queryString);
            rs = st.executeQuery(queryString);

            HashMap<String, Place> placeHashMap = new HashMap<>(); // A hash that stores places, with city and state denoting values
            while (rs.next()) {
                // If current record's distance is from the inputted zip is greater than inputted distance, continue
                double currentLat = rs.getDouble("lat");
                double currentLong = rs.getDouble("long");
                double distanceInKilos = calculateDistance(inputLat, currentLat, inputLong, currentLong);
                if (convertKilometersToMiles(distanceInKilos) > miles) continue;

                String cityState = rs.getString("city") + ", " + rs.getString("state");
                if (placeHashMap.containsKey(cityState)) {
                    // If city and state pair already exists, add current record's population to that Place
                    placeHashMap.get(cityState).population += rs.getInt("estimatedpopulation");
                    placeHashMap.get(cityState).taxReturns += rs.getInt("taxreturnsfiled");
                } else {
                    // Create new Place and add it to placeHashMap
                    String currentZipCode = rs.getString("zipcode");
                    int population = rs.getInt("estimatedpopulation");
                    int taxReturns = rs.getInt("taxreturnsfiled");

                    placeHashMap.put(cityState, new Place(cityState, currentZipCode, population, taxReturns));
                }
            }

            // Prints header, then data
            System.out.printf("\n%-7s %-20s %-12s %-10s\n", "Zip", "City, State", "Population", "Households");
            for (Place place: placeHashMap.values()) {
                System.out.printf("%-7s %-20s %-12d %-10d\n", place.zipCode, place.cityState, place.population, place.taxReturns);
            }

            conn.close();
        } catch (SQLException e) {
            System.err.println("Failed to connect to database.");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    static double convertKilometersToMiles(double kilometers) {
        return kilometers / 1.609344;
    }

    static double convertMilesToKilometers(double miles) {
        return miles * 1.609344;
    }

    // Calculates the distance (in kilometers) between two lat and long coordinates
    static double calculateDistance(double lat1, double lat2, double lon1, double lon2) {
        int R = 6371;
        double lat1Radians = Math.toRadians(lat1);
        double lat2Radians =  Math.toRadians(lat2);
        double latDiff = Math.toRadians(lat2-lat1);
        double lonDiff = Math.toRadians(lon2-lon1);

        double a = Math.sin(latDiff/2) * Math.sin(latDiff/2) +
                Math.cos(lat1Radians) * Math.cos(lat2Radians) *
                        Math.sin(lonDiff/2) * Math.sin(lonDiff/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R * c;
    }
}
