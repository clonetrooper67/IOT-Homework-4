package zephan.fedex;

import java.util.LinkedList;

public class TheBigIdea extends Thread {

    Package myParcel;
    int threadNum;

    public TheBigIdea(Package parcel, int threadNum) {
        this.myParcel = parcel;
        this.threadNum = threadNum;
    }

    public void run() {
        try {
            Node[] FedexMap = new Node[25];

            String cities[] = { "Northborough, MA", "Edison, NJ", "Pittsburgh, PA", "Allentown, PA", "Martinsburg, WV",
                    "Charlotte, NC", "Atlanta, GA", "Orlando, FL", "Memphis, TN", "Grove City, OH", "Indianapolis, IN",
                    "Detroit, MI", "New Berlin, WI", "Minneapolis, MN", "St. Louis, MO", "Kansas, KS", "Dallas, TX",
                    "Houston, TX", "Denver, CO", "Salt Lake City, UT", "Phoenix, AZ", "Los Angeles, CA", "Chino, CA",
                    "Sacramento, CA", "Seattle, WA" };

//			Fill the graph with nodes
            for (int i = 0; i < 25; i++) {
                Node temp = new Node(cities[i], i, 4);
                FedexMap[i] = temp;
            }

//			Assign Nodes 0,1,23, and 24 their edges
            FedexMap[0].addEdge(1);
            FedexMap[0].addEdge(2);
            FedexMap[0].addEdge(3);
            FedexMap[0].addEdge(4);

            FedexMap[1].addEdge(0);
            FedexMap[1].addEdge(2);
            FedexMap[1].addEdge(3);
            FedexMap[1].addEdge(4);

            FedexMap[23].addEdge(20);
            FedexMap[23].addEdge(21);
            FedexMap[23].addEdge(22);
            FedexMap[23].addEdge(24);

            FedexMap[24].addEdge(20);
            FedexMap[24].addEdge(21);
            FedexMap[24].addEdge(22);
            FedexMap[24].addEdge(23);

//			Assign nodes 3-22 their edges
            for (int i = 2; i < 23; i++) {
                FedexMap[i].addEdge(i - 2);
                FedexMap[i].addEdge(i - 1);
                FedexMap[i].addEdge(i + 1);
                FedexMap[i].addEdge(i + 2);
            }

//			The this instance of the graph for BFS
            Graph newGraph = new Graph(25, FedexMap, myParcel.getStartCity(), myParcel.getEndCity());
//			Find the shortest path
            newGraph.findPath();

            LinkedList<Integer> path = newGraph.getPath();

//			Finish out the path
            myParcel.fastestPath = path;
            myParcel.fastestPath.add(myParcel.getEndCity());

            myParcel.alreadySorted = true;

//			Since this is done write the package to the database and the main parcel list
            Main.parcelArray.set(threadNum, myParcel);

        } catch (Exception e) {
            System.out.println("Ruh roh the world is ending: " + e);
        }
    }
}
