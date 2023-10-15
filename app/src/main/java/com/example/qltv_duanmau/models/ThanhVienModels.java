package com.example.qltv_duanmau.models;

public class ThanhVienModels {
    private int idThanhVien;
    private String tenThanhVien;

    private String namSinh;
    private String CanCuoc;

    public ThanhVienModels(int idThanhVien, String tenThanhVien, String namSinh , String canCuoc) {
        this.idThanhVien = idThanhVien;
        this.tenThanhVien = tenThanhVien;
        this.namSinh = namSinh;
        this.CanCuoc = canCuoc;
    }

    public ThanhVienModels() {
    }

    public int getIdThanhVien() {
        return idThanhVien;
    }

    public void setIdThanhVien(int idThanhVien) {
        this.idThanhVien = idThanhVien;
    }

    public String getTenThanhVien() {
        return tenThanhVien;
    }

    public void setTenThanhVien(String tenThanhVien) {
        this.tenThanhVien = tenThanhVien;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    public String getCanCuoc() {
        return CanCuoc;
    }

    public void setCanCuoc(String canCuoc) {
        CanCuoc = canCuoc;
    }

    public static final String TB_NAME = "thanh_vien";

    public static final String COL_ID = "id_thanh_vien";
    public static final String COL_TEN = "ten";

    public static final String COL_NAM_SINH = "nam_sinh";
    public static final  String COL_CAN_CUOC = "can_cuoc";
}
