/*
Hunter Berten
CSC 354
This program allows the user to input a 5-digit zip code, followed by a distance in miles. It will then produce a list
of places (obtained from a specific database) that are within the specified number of miles from the specified zip code.
The name, state, population, housing units, and distance (in kilometers and miles) from the specified zip code will be
listed for each place.
*/

package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a zip code:");
        String zipCode = input.next();
        System.out.println("Enter a distance in miles:");
        int miles = Integer.parseInt(input.next());
    }
}
