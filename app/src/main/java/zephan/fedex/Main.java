package zephan.fedex;

import java.util.*;

public class Main {

    public static LinkedList<Package> parcelArray = new LinkedList<Package>();

    public static void main() throws Exception {

        String cities[] = { "Northborough, MA", "Edison, NJ", "Pittsburgh, PA", "Allentown, PA", "Martinsburg, WV",
                "Charlotte, NC", "Atlanta, GA", "Orlando, FL", "Memphis, TN", "Grove City, OH", "Indianapolis, IN",
                "Detroit, MI", "New Berlin, WI", "Minneapolis, MN", "St. Louis, MO", "Kansas, KS", "Dallas, TX",
                "Houston, TX", "Denver, CO", "Salt Lake City, UT", "Phoenix, AZ", "Los Angeles, CA", "Chino, CA",
                "Sacramento, CA", "Seattle, WA" };

        LinkedList<Thread> threads = new LinkedList<Thread>();

//		Read in the database
//        Database.readDatabase();

        parcelArray.push(new Package(0, 4, 8, 1));
        parcelArray.push(new Package(1, 4, 8, 1));
        parcelArray.push(new Package(2, 4, 8, 1));
        parcelArray.push(new Package(3, 4, 8, 1));
        parcelArray.push(new Package(4, 4, 8, 1));
        parcelArray.push(new Package(5, 4, 8, 1));
        parcelArray.push(new Package(6, 4, 8, 1));
        parcelArray.push(new Package(7, 4, 8, 1));
        parcelArray.push(new Package(8, 4, 8, 1));
        parcelArray.push(new Package(9, 4, 8, 1));
        parcelArray.push(new Package(10, 4, 8, 1));
        parcelArray.push(new Package(11, 4, 8, 1));
        parcelArray.push(new Package(12, 4, 8, 1));
        parcelArray.push(new Package(13, 4, 8, 1));
        parcelArray.push(new Package(14, 4, 8, 1));

//		Go ahead and find the path for all of these
        for (int i = 0; i < parcelArray.size(); i++) {
            if (!parcelArray.get(i).alreadySorted) {
                TheBigIdea object = new TheBigIdea(parcelArray.get(i), i);
                object.start();
                threads.push(object);
            }
        }
//		Join the threads
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).join();
        }

        Scanner superScantron3000 = new Scanner(System.in);
//		Make the timer for moving the packages
        Timer timer = new Timer();
        TimerTask task = new Chronos();

//		Move packages every 5 seconds
        timer.schedule(task, 5000, 5000);

        while (true) {
//			Get the option
            System.out.print("Please choose either [1] making a package or [2] tracking a package: ");
            int choice = superScantron3000.nextInt();
            System.out.println();
//			If it's 1 make a package if it's 2 track a Package
            if (choice == 1) {
//				Get the needed information
                System.out.println("Please input the city you wish to send your package from: ");

                for (int i = 0; i < cities.length; i++) {
                    System.out.println(i + " - " + cities[i]);
                }

                int startCity = superScantron3000.nextInt();

                System.out.println("Please input the city you wish to send your package to: ");

                for (int i = 0; i < cities.length; i++) {
                    System.out.println(i + " - " + cities[i]);
                }

                int endCity = superScantron3000.nextInt();

                System.out.println("Please input the weight of the package");

                int weight = superScantron3000.nextInt();

                parcelArray.push(new Package(parcelArray.size(), startCity, endCity, weight));
//				Clear the database
                if (parcelArray.size() > 15) {
//                    DatabaseHelper.clearDatabase();
                    parcelArray.clear();
                    Package temp = new Package(parcelArray.size(), startCity, endCity, weight);
                    parcelArray.push(temp);
                }

                // Go forth and make the thread
                for (int i = 0; i < parcelArray.size(); i++) {
                    if (!parcelArray.get(i).alreadySorted) {
                        TheBigIdea object = new TheBigIdea(parcelArray.get(i), i);
                        object.start();
                        threads.push(object);
                    }
                }

                System.out.println("Package was created with a tracking number of: " + (parcelArray.size() - 1));

            } else if (choice == 2) {
                System.out.print("Please input the tracking number of the parcel you wish to track: ");

                String requestedTrack = superScantron3000.next();
//			The Try/Catch lets you exit the code by typing in a character or string instead of an int
                try {
                    boolean found = false;
//					Loop through all of the packages
                    for (int i = 0; i < parcelArray.size(); i++) {
//						If the package is found output it's current path and weight
                        if (Integer.parseInt(requestedTrack) == parcelArray.get(i).getTrackingNumber()) {
                            System.out.println(
                                    "Found your parcel! It's got a weight of: " + parcelArray.get(i).getWeight());
                            found = true;

                            for (int j = 0; j < parcelArray.get(i).currentPath.size(); j++) {
                                System.out.println(cities[parcelArray.get(i).currentPath.get(j)] + " | On Day: "
                                        + parcelArray.get(i).getTimeOfTravel().get(j));
                            }
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Please Try a Different Tracking Number");
                    }
                } catch (Exception e) {
                    break;
                }
            } else {
                break;
            }
        }
        timer.cancel();
        superScantron3000.close();
    }
}