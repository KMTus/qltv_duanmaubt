package com.example.qltv_duanmau.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qltv_duanmau.database.SqlHelper;
import com.example.qltv_duanmau.models.PhieuMuonModels;
import com.example.qltv_duanmau.models.ThanhVienModels;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDao {

    private SqlHelper sqlHelper;

    private SQLiteDatabase sqLiteDatabase;

    public PhieuMuonDao(Context context) {
        sqlHelper = new SqlHelper(context);
        sqLiteDatabase = sqlHelper.getWritableDatabase();
    }

    public long insertPM(PhieuMuonModels phieuMuonModels){
        ContentValues contentValues = new ContentValues();
        contentValues.put(PhieuMuonModels.COL_ID_SACH, phieuMuonModels.getId_sach());
        contentValues.put(PhieuMuonModels.COL_ID_THANH_VIEN, phieuMuonModels.getId_thanh_vien());
        contentValues.put(PhieuMuonModels.COL_ID_THU_THU, phieuMuonModels.getId_thu_thu());
        contentValues.put(PhieuMuonModels.COL_GIA_TIEN, phieuMuonModels.getGia_tien());
        contentValues.put(PhieuMuonModels.COL_TRANGTHAI, phieuMuonModels.getTrangthai());
        contentValues.put(PhieuMuonModels.COL_NGAY, phieuMuonModels.getNgay());
        return sqLiteDatabase.insert(PhieuMuonModels.DB_NAME, null, contentValues);
    }
    public int updatePM(PhieuMuonModels phieuMuonModels){
        ContentValues contentValues = new ContentValues();
        contentValues.put(PhieuMuonModels.COL_ID_SACH, phieuMuonModels.getId_sach());
        contentValues.put(PhieuMuonModels.COL_ID_THANH_VIEN, phieuMuonModels.getId_thanh_vien());
        contentValues.put(PhieuMuonModels.COL_ID_THU_THU, phieuMuonModels.getId_thu_thu());
        contentValues.put(PhieuMuonModels.COL_GIA_TIEN, phieuMuonModels.getGia_tien());
        contentValues.put(PhieuMuonModels.COL_TRANGTHAI, phieuMuonModels.getTrangthai());
        contentValues.put(PhieuMuonModels.COL_NGAY, phieuMuonModels.getNgay());
        return sqLiteDatabase.update(PhieuMuonModels.DB_NAME, contentValues, "id_phieu_muon=?", new String[]{String.valueOf(phieuMuonModels.getId_phieu())});
    }

    public int deletePM(PhieuMuonModels phieuMuonModels){
        return sqLiteDatabase.delete(PhieuMuonModels.DB_NAME, "id_phieu_muon=?", new String[]{String.valueOf(phieuMuonModels.getId_phieu())});
    }

    public List<PhieuMuonModels> getDataPhieuMuon(){

        String query = "SELECT * FROM " + PhieuMuonModels.DB_NAME;
        List<PhieuMuonModels> list = getData(query);
        return list;
    }

    public PhieuMuonModels getIDPM(int id){
        String query = "SELECT * FROM phieu_muon WHERE id_phieu_muon=?";
        List<PhieuMuonModels> list = getData(query, String.valueOf(id));
        return list.get(0);
    }
    @SuppressLint("Range")
    private List<PhieuMuonModels> getData(String query, String...args){
        List<PhieuMuonModels> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(query, args);
        while (cursor.moveToNext()) {
            PhieuMuonModels phieuMuonModels = new PhieuMuonModels();
            phieuMuonModels.setId_phieu(cursor.getInt(cursor.getColumnIndex(PhieuMuonModels.COL_ID_PM)));
            phieuMuonModels.setId_sach(cursor.getInt(cursor.getColumnIndex(PhieuMuonModels.COL_ID_SACH)));
            phieuMuonModels.setId_thanh_vien(cursor.getInt(cursor.getColumnIndex(PhieuMuonModels.COL_ID_THANH_VIEN)));
            phieuMuonModels.setId_thu_thu(cursor.getInt(cursor.getColumnIndex(PhieuMuonModels.COL_ID_THU_THU)));
            phieuMuonModels.setGia_tien(cursor.getInt(cursor.getColumnIndex(PhieuMuonModels.COL_GIA_TIEN)));
            phieuMuonModels.setTrangthai(cursor.getInt(cursor.getColumnIndex(PhieuMuonModels.COL_TRANGTHAI)));
            phieuMuonModels.setNgay(cursor.getString(cursor.getColumnIndex(PhieuMuonModels.COL_NGAY)));
            list.add(phieuMuonModels);
        }
        return list;
    }
}


