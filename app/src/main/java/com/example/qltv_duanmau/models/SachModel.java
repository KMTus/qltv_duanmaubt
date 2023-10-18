package com.example.qltv_duanmau.models;

public class SachModel {
    private int id;
    private String tenSach;
    private int giaThue;
    private String tenTL;
    private int nam_xuat_ban;
    private int idTL;

    public SachModel(int id, String tenSach, int giaThue, String tenTL , int nam_xuat_ban, int idTL) {
        this.id = id;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.tenTL = tenTL;
        this.nam_xuat_ban = nam_xuat_ban;
        this.idTL = idTL;
    }

    public int getNam_xuat_ban() {
        return nam_xuat_ban;
    }

    public void setNam_xuat_ban(int nam_xuat_ban) {
        this.nam_xuat_ban = nam_xuat_ban;
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
