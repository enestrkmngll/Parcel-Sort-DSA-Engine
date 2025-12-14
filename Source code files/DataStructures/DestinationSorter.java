package DataStructures;

import Parcel.Parcel;

public class DestinationSorter {  
    private class BSTNode {
        String cityName;
        ArrivalBuffer parcelList; 
        BSTNode left, right;

        BSTNode(String cityName, int parcelListCapacity) {
            this.cityName = cityName;
            this.parcelList = new ArrivalBuffer(parcelListCapacity);
            this.left = this.right = null;
        }
    }

    private BSTNode root;
    private int parcelListCapacity;  

    public DestinationSorter(int parcelListCapacity) {
        root = null;
        this.parcelListCapacity=parcelListCapacity;
    }

    public void insertParcel(Parcel parcel) {
        root = insertParcelRecursive(root, parcel);
    }

    private BSTNode insertParcelRecursive(BSTNode node, Parcel parcel) {   
        if (node == null) {                                                 
            BSTNode newNode = new BSTNode(parcel.getDestinationCity(),parcelListCapacity);
            newNode.parcelList.enqueue(parcel);
            return newNode;
        }

        int cmp = parcel.getDestinationCity().compareTo(node.cityName); 
        if (cmp < 0) {
            node.left = insertParcelRecursive(node.left, parcel);
        } else if (cmp > 0) {
            node.right = insertParcelRecursive(node.right, parcel);
        } else {
            node.parcelList.enqueue(parcel);
        }
        return node;
    }

    public ArrivalBuffer getCityParcels(String city) { 
        BSTNode node = findNode(root, city);
        
        if(node !=null){
            return node.parcelList;
        }else{
            return null;
        }
    }

    private BSTNode findNode(BSTNode node, String city) { 
        if (node == null) return null;
            
        int cmp = city.compareTo(node.cityName);
        if (cmp < 0){
            return findNode(node.left, city);
        } 
        else if (cmp > 0){
            return findNode(node.right, city);
        }
        else {
            return node;
        }
    }

    public void inOrderTraversal() {
        inOrderTraversalRecursive(root);
    }

    private void inOrderTraversalRecursive(BSTNode node) {
        if (node != null) {
            inOrderTraversalRecursive(node.left);
            System.out.println("City: " + node.cityName + ", Parcels: " + node.parcelList.size());
            inOrderTraversalRecursive(node.right);
        }
    }

    public Parcel removeParcel(String city, String parcelID) {  
        BSTNode node = findNode(root, city);
        if (node == null){
            return null;
        }

        ArrivalBuffer tempQueue = new ArrivalBuffer(parcelListCapacity); 
        Parcel foundParcel = null;

        while (!node.parcelList.isEmpty()) {
            Parcel parcel = node.parcelList.dequeue(); 
            if (parcel.getParcelID().equals(parcelID) && foundParcel == null) {
                foundParcel = parcel; 
            } else {
                tempQueue.enqueue(parcel);
            }
        }

        while (!tempQueue.isEmpty()) {
            node.parcelList.enqueue(tempQueue.dequeue()); 
        }

        return foundParcel;
    }

    public int countCityParcels(String city) {  
        BSTNode node = findNode(root, city);

        if(node != null){
            return node.parcelList.size();
        }else{
            return 0;
        }
    }

    public int heightOfBST() {                                                 
        return heightOfBSTRecursive(root);
    }

    private int heightOfBSTRecursive(BSTNode node) {
        if (node == null){
            return 0;
        } 
        return Math.max(heightOfBSTRecursive(node.left), heightOfBSTRecursive(node.right))+1;         
    }

    public int numberOfNodes() {
        return numberOfNodesRecursive(root);
    }

    private int numberOfNodesRecursive(BSTNode node) {
        if (node == null) return 0;
        return 1 + numberOfNodesRecursive(node.left) + numberOfNodesRecursive(node.right);
    }
        
    public String highestParcelCity() {
        return highestParcelCityRecursive(root, root.cityName, 0);
    }

    private String highestParcelCityRecursive(BSTNode node, String currentCity, int currentMax) {  
        if (node == null) {
            return currentCity;
        }

        String leftHighestCity = highestParcelCityRecursive(node.left, currentCity, currentMax);

        int leftMax;
        if (leftHighestCity.equals(currentCity)) {
            leftMax = currentMax;
        } else {
            leftMax = getCityParcels(leftHighestCity).size();
        }

        int currentSize = node.parcelList.size();
        if (currentSize > leftMax) {
            currentCity = node.cityName;
            currentMax = currentSize;
        } else {
            currentCity = leftHighestCity;
        }

        return highestParcelCityRecursive(node.right, currentCity, currentMax);
    }
}
