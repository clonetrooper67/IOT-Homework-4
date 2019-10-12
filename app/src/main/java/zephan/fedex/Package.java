package zephan.fedex;

import java.util.*;

public class Package {

    int trackingNumber;
    int startCity;
    int endCity;
    int weight;
    boolean alreadySorted;

    String shipDate;
    String arrivalDate;

    LinkedList<Integer> fastestPath = new LinkedList<>();
    LinkedList<Integer> currentPath = new LinkedList<>();
    LinkedList<Integer> timeOfTravel = new LinkedList<>();

    Package(int trackingNumber, int startCity, int endCity, int weight) {
        this.trackingNumber = trackingNumber;
        this.startCity = startCity;
        this.endCity = endCity;
        this.weight = weight;
        shipDate = "";
        timeOfTravel.push(0);
        alreadySorted = false;
    }

    public void printPath() {
        for (int i = 0; i < fastestPath.size(); i++) {
            System.out.println(fastestPath.get(i));
        }
    }


    public boolean isAlreadySorted() {
        return alreadySorted;
    }

    public void setAlreadySorted(boolean alreadySorted) {
        this.alreadySorted = alreadySorted;
    }

    public LinkedList<Integer> getTimeOfTravel() {
        return timeOfTravel;
    }

    public void setTimeOfTravel(LinkedList<Integer> timeOfTravel) {
        this.timeOfTravel = timeOfTravel;
    }

    public LinkedList<Integer> getFastestPath() {
        return fastestPath;
    }

    public void setFastestPath(LinkedList<Integer> fastestPath) {
        this.fastestPath = fastestPath;
    }

    public LinkedList<Integer> getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(LinkedList<Integer> currentPath) {
        this.currentPath = currentPath;
    }

    public int getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(int trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public int getStartCity() {
        return startCity;
    }

    public void setStartCity(int startCity) {
        this.startCity = startCity;
    }

    public int getEndCity() {
        return endCity;
    }

    public void setEndCity(int endCity) {
        this.endCity = endCity;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getShipDate() {
        return shipDate;
    }

    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

}
