package com.company;

public class Piece implements Comparable{

    int length;
    int quantity;

    public Piece(int length, int quantity) {
        this.length = length;
        this.quantity = quantity;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int compareTo(Object o) {
        int compare = ((Piece) o).getLength();
        return compare - this.length;
    }
}
