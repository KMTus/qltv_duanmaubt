package com.example.qltv_duanmau.models;

public class PhieuMuonModels {
    private int id_phieu;

    private int id_sach;

    private int id_thanh_vien;

    private int id_thu_thu;

    private int gia_tien;

    private int trangthai;

    private String ngay;

    public static final String COL_ID_PM = "id_phieu_muon";
    public static final String COL_ID_SACH = "id_sach";

    public static final String COL_ID_THANH_VIEN = "id_thanh_vien";

    public static final String COL_ID_THU_THU = "id_thu_thu";

    public static final String COL_GIA_TIEN = "gia_tien";

    public static final String COL_TRANGTHAI = "trang_thai";

    public static final String COL_NGAY = "ngay";

    public static final String DB_NAME = "phieu_muon";

    public PhieuMuonModels(int id_phieu, int id_sach, int id_thanh_vien, int id_thu_thu, int gia_tien, int trangthai, String ngay) {
        this.id_phieu = id_phieu;
        this.id_sach = id_sach;
        this.id_thanh_vien = id_thanh_vien;
        this.id_thu_thu = id_thu_thu;
        this.gia_tien = gia_tien;
        this.trangthai = trangthai;
        this.ngay = ngay;
    }

    public PhieuMuonModels() {
    }

    public int getId_phieu() {
        return id_phieu;
    }

    public void setId_phieu(int id_phieu) {
        this.id_phieu = id_phieu;
    }

    public int getId_sach() {
        return id_sach;
    }

    public void setId_sach(int id_sach) {
        this.id_sach = id_sach;
    }

    public int getId_thanh_vien() {
        return id_thanh_vien;
    }

    public void setId_thanh_vien(int id_thanh_vien) {
        this.id_thanh_vien = id_thanh_vien;
    }

    public int getId_thu_thu() {
        return id_thu_thu;
    }

    public void setId_thu_thu(int id_thu_thu) {
        this.id_thu_thu = id_thu_thu;
    }

    public int getGia_tien() {
        return gia_tien;
    }

    public void setGia_tien(int gia_tien) {
        this.gia_tien = gia_tien;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
