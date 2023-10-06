package com.example.qltv_duanmau.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qltv_duanmau.database.SqlHelper;
import com.example.qltv_duanmau.models.ThanhVienModels;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDao {

    private SqlHelper sqlHelper;

    private SQLiteDatabase sqLiteDatabase;

    public ThanhVienDao(Context context) {
        sqlHelper = new SqlHelper(context);
        sqLiteDatabase = sqlHelper.getWritableDatabase();
    }

    public long insert(ThanhVienModels thanhVien) {
        ContentValues cv = new ContentValues();
        cv.put(ThanhVienModels.COL_TEN, thanhVien.getTenThanhVien());
        cv.put(ThanhVienModels.COL_NAM_SINH, thanhVien.getNamSinh());
        return sqLiteDatabase.insert(ThanhVienModels.TB_NAME, null, cv);
    }

    public int updateThanhVien(ThanhVienModels thanhVien) {
        ContentValues cv = new ContentValues();
        cv.put(ThanhVienModels.COL_TEN, thanhVien.getTenThanhVien());
        cv.put(ThanhVienModels.COL_NAM_SINH, thanhVien.getNamSinh());
        return sqLiteDatabase.update(ThanhVienModels.TB_NAME, cv, "id_thanh_vien=?", new String[]{String.valueOf(thanhVien.getIdThanhVien())});
    }

    public int deleteThanhVien(ThanhVienModels thanhVienModels) {
        return sqLiteDatabase.delete(ThanhVienModels.TB_NAME, "id_thanh_vien=?", new String[]{String.valueOf(thanhVienModels.getIdThanhVien())});
    }

    @SuppressLint("Range")

    private List<ThanhVienModels> getData(String query, String... args) {
        List<ThanhVienModels> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(query, args);
        while (cursor.moveToNext()) {
            ThanhVienModels thanhVienModels = new ThanhVienModels();
            thanhVienModels.setIdThanhVien(cursor.getInt(cursor.getColumnIndex(ThanhVienModels.COL_ID)));
            thanhVienModels.setTenThanhVien(cursor.getString(cursor.getColumnIndex(ThanhVienModels.COL_TEN)));
            thanhVienModels.setNamSinh(cursor.getString(cursor.getColumnIndex(ThanhVienModels.COL_NAM_SINH)));
            list.add(thanhVienModels);
        }
        return list;
    }

    public ThanhVienModels getIDThanhVien(String id) {
        String query = "SELECT * FROM thanh_vien WHERE id_thanh_vien=?";
        List<ThanhVienModels> list = getData(query, id);
        return list.get(0);
    }

    public List<ThanhVienModels> getAllDataThanhVien() {
        String query = "SELECT * FROM thanh_vien";
        List<ThanhVienModels> list = getData(query);
        return list;
    }
}
