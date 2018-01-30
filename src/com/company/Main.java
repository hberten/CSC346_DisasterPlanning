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

            String queryString = "SELECT * from zips";

            st = conn.createStatement();
            st.execute(queryString);

            rs = st.executeQuery(queryString);

            while (rs.next()) {
                System.out.println(rs.getString("county"));
            }

            conn.close();
        } catch (SQLException e) {
            System.err.println("Failed to connect to database.");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
