package com.example.qltv_duanmau.models;

public class SachModel {
    private int id;
    private String tenSach;
    private int giaThue;
    private String tenTL;
    private int idTL;

    public SachModel(int id, String tenSach, int giaThue, String tenTL, int idTL) {
        this.id = id;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.tenTL = tenTL;
        this.idTL = idTL;
    }

    public SachModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public String getTenTL() {
        return tenTL;
    }

    public void setTenTL(String tenTL) {
        this.tenTL = tenTL;
    }

    public int getIdTL() {
        return idTL;
    }

    public void setIdTL(int idTL) {
        this.idTL = idTL;
    }
}
