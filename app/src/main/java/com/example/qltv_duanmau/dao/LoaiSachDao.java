package com.example.qltv_duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.qltv_duanmau.database.SqlHelper;
import com.example.qltv_duanmau.models.LoaiSachModel;

import java.util.ArrayList;

public class LoaiSachDao {
    private final SqlHelper dbHelper;

    public LoaiSachDao(Context context) {
        dbHelper = new SqlHelper(context);
    }

    public ArrayList<LoaiSachModel> getListLoaiSach(){
        //tạo một danh sách để add dữ liệu vào SanPham
        ArrayList<LoaiSachModel> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor c = database.rawQuery("select * from loai_sach",null);
            if (c.getCount() > 0){
                c.moveToFirst();
                do {
                    list.add(new LoaiSachModel(
                            c.getInt(0),
                            c.getString(1)));
                }while (c.moveToNext());
                database.setTransactionSuccessful();
            }
        }catch (Exception e){
            Log.e("Error", "getListLoaiSach: " + e.getMessage());
        }finally {
            database.endTransaction();
        }
        return list;
    }


    public boolean addLoaiSach(LoaiSachModel loaiSachModel) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues values = new ContentValues();


        values.put("ten_the_loai", loaiSachModel.getTenTL());

        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.insert("loai_sach", null, values);

        return check != -1;
    }

    public boolean removeLoaiSach(int id){
        SQLiteDatabase sqLiteDatabase =dbHelper.getWritableDatabase();
        int row = sqLiteDatabase.delete("loai_sach", "id_loai_sach = ?", new String[]{String.valueOf(id)});
        return  row!=-1;
    }

    public  boolean updateLoaiSach ( LoaiSachModel loaiSachModel ){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("ten_the_loai", loaiSachModel.getTenTL());


        int check = database.update("loai_sach", values, "id_loai_sach = ?", new String[]{String.valueOf(loaiSachModel.getId())});
        return  check!=-1;
    }




}
