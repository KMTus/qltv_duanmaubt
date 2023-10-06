package com.example.qltv_duanmau.models;

public class TopBookModel {
    public String title_book;

    public int quantity;

    public TopBookModel(String title_book, int quantity) {
        this.title_book = title_book;
        this.quantity = quantity;
    }

    public TopBookModel() {
    }

    public String getTitle_book() {
        return title_book;
    }

    public void setTitle_book(String title_book) {
        this.title_book = title_book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
