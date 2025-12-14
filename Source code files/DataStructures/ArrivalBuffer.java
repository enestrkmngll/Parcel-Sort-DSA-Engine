package DataStructures;

import Parcel.Parcel;

public class ArrivalBuffer {
    private Node head;
    private Node tail;
    private int size;
    private int capacity;

    private class Node {
        Parcel data;
        Node next;

        Node(Parcel parcel) {
            this.data = parcel;
            this.next = null;
        }
    }

    public ArrivalBuffer(int capacity) {
        this.capacity = capacity;  
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void enqueue(Parcel parcel) {
        if (isFull()) {
        System.out.println("Queue overflow!!!");
            return;
        }
            
        Node newNode = new Node(parcel);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public Parcel dequeue() {
        if (isEmpty()) {
            return null;
        }
            
        Parcel deletedParcel = head.data;
        head = head.next;
        size--;
            
        if (isEmpty()) {
            tail = null;
        }
            
        return deletedParcel;
    }

    public Parcel peek() {
        if (isEmpty()) {
            return null;
        }
        return head.data;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}
