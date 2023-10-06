package com.example.qltv_duanmau.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qltv_duanmau.database.SqlHelper;
import com.example.qltv_duanmau.models.SachModel;
import com.example.qltv_duanmau.models.TopBookModel;

import java.util.ArrayList;
import java.util.List;

public class ThongKeDao {

    private SqlHelper sqliteConnect;

    private SQLiteDatabase sqLiteDatabase;

    private Context context;

    public ThongKeDao(Context context) {
        this.context = context;
        sqliteConnect = new SqlHelper(context);
        sqLiteDatabase = sqliteConnect.getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<TopBookModel> getTopBookModels() {
        String query = "SELECT id_sach, COUNT(id_sach) as count " +
                "FROM phieu_muon " +
                "GROUP BY id_sach " +
                "ORDER BY count DESC " +
                "LIMIT 10";
        List<TopBookModel> list = new ArrayList<>();
        SachDao sachDao = new SachDao(context);
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        while (cursor.moveToNext()) {
            TopBookModel topBookModel = new TopBookModel();
            int idSach = cursor.getInt(cursor.getColumnIndex("id_sach"));
            SachModel sachModels = sachDao.getId(String.valueOf(idSach)); // Assuming you have a method to retrieve SachModels by id_sach
            topBookModel.title_book = sachModels.getTenSach();
            topBookModel.quantity = cursor.getInt(cursor.getColumnIndex("count"));
            list.add(topBookModel);
        }

        cursor.close();
        return list;
    }

    public int doanhThuTheoNgay(String tungay, String dengay) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(gia_tien) FROM phieu_muon WHERE ngay >='" + tungay + "'AND ngay <='" + dengay + "'", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }




}
