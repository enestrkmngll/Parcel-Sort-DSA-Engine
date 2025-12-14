package DataStructures;

import Parcel.Parcel;

public class ReturnStack { 
    private static class StackNode {
        Parcel parcel;
        StackNode next;

        StackNode(Parcel parcel) {
            this.parcel = parcel;
        }
    }

    private StackNode top;
    private int size;

    public ReturnStack() {
        top = null;
        size = 0;
    }

    public void push(Parcel parcel) {
        StackNode newNode = new StackNode(parcel);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public Parcel pop() {
        if (isEmpty()) {
            return null;
        }
        Parcel deletedParcel = top.parcel;
        top = top.next;
        size--;
        return deletedParcel;
    }

    public Parcel peek() {
        if (isEmpty()) {
            return null;
        }
        return top.parcel;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}
