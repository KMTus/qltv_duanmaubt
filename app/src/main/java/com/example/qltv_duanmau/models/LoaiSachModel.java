package com.example.qltv_duanmau.models;

public class LoaiSachModel {
    private int id;
    private String tenTL;

    public LoaiSachModel(int id, String tenTL) {
        this.id = id;
        this.tenTL = tenTL;
    }

    public LoaiSachModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenTL() {
        return tenTL;
    }

    public void setTenTL(String tenTL) {
        this.tenTL = tenTL;
    }
}
