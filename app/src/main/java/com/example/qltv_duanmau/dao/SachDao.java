package com.example.qltv_duanmau.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.qltv_duanmau.database.SqlHelper;
import com.example.qltv_duanmau.models.LoaiSachModel;
import com.example.qltv_duanmau.models.SachModel;

import java.util.ArrayList;
import java.util.List;

public class SachDao {
    private final SqlHelper dbHelper;
    public SachDao(Context context) {
        dbHelper = new SqlHelper(context);
    }

    public ArrayList<SachModel> getListSach(){
        ArrayList<SachModel> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.beginTransaction();
//      String tb_sach =
//                "CREATE TABLE sach (" +
//                        "id_sach INTEGER PRIMARY KEY AUTOINCREMENT," +
//                        "ten_sach TEXT NOT NULL," +
//                        "gia_thue INTEGER," +
//                        "ten_the_loai INTEGER," +
//                        "nam_suat_ban INTEGER ," +
//                        "id_the_loai_sach INTEGER REFERENCES loai_sach(id_loai_sach))";  // Chỉnh "INTERGER" thành "INTEGER"
        try {
            Cursor c = database.rawQuery("select * from sach",null);
            if (c.getCount() > 0){
                c.moveToFirst();
                do {
                    list.add(new SachModel(
                            c.getInt(0),
                            c.getString(1),
                            c.getInt(2),
                            c.getString(3),
                            c.getInt(4),
                            c.getInt(5)));
                }while (c.moveToNext());
                database.setTransactionSuccessful();
            }
        }catch (Exception e){
            Log.e("Error", "getListSach: " + e.getMessage());
        }finally {
            database.endTransaction();
        }
        return list;
    }

    public SachModel getId(String id) {

        String sql = "SELECT * FROM sach WHERE id_sach=?";
        List<SachModel> list = getData(sql, id);
        return list.get(0);
    }
    @SuppressLint("Range")
    public List<SachModel> getData(String dl, String... Arays /* có hoặc không nhiều phần tử*/) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        List<SachModel> sachList = new ArrayList<>();


        Cursor cursor =database .rawQuery(dl, Arays);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id_sach"));
                String tenSach = cursor.getString(cursor.getColumnIndex("ten_sach"));
                int giaThue = cursor.getInt(cursor.getColumnIndex("gia_thue"));
                String tenTL = cursor.getString(cursor.getColumnIndex("ten_the_loai"));
                int namxb = cursor.getInt(cursor.getColumnIndex("nam_suat_ban"));
                int idTL = cursor.getInt(cursor.getColumnIndex("id_the_loai_sach"));

                SachModel sach = new SachModel(id, tenSach, giaThue, tenTL , namxb, idTL);
                sachList.add(sach);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return sachList;
    }



    public boolean addSach(SachModel sachModel) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();

        ContentValues values = new ContentValues();


        values.put("ten_sach", sachModel.getTenSach());
        values.put("gia_thue", sachModel.getGiaThue());
        values.put("ten_the_loai", sachModel.getTenTL());
        values.put("nam_suat_ban",sachModel.getNam_xuat_ban());
        values.put("id_the_loai_sach", sachModel.getIdTL());



        database.setTransactionSuccessful();
        database.endTransaction();

        long check = database.insert("sach", null, values);

        return check != -1;
    }

    public boolean removeSach(int id){
        SQLiteDatabase sqLiteDatabase =dbHelper.getWritableDatabase();
        int row = sqLiteDatabase.delete("sach", "id_sach = ?", new String[]{String.valueOf(id)});
        return  row!=-1;
    }

    public List<String> getAllTenTheLoai() {
        List<String> loaiSachList = new ArrayList<>();

        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] columns = {"ten_the_loai"};

            cursor = database.query("loai_sach", columns, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") String tenLoaiSach = cursor.getString(
                            cursor.getColumnIndex("ten_the_loai"));
                    loaiSachList.add(tenLoaiSach);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("SachDAO", "Error querying LoaiSach: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return loaiSachList;
    }

    @SuppressLint("Range")
    public int getLoaiSachIdByTenTheLoai(String tenTheLoai) {
        int id = -1;

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {"id_loai_sach"};
        String selection = "ten_the_loai = ?";
        String[] selectionArgs = {tenTheLoai};

        Cursor cursor = db.query("loai_sach", columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex("id_loai_sach"));
            cursor.close();
        }

        return id;
    }

    public  boolean updateSach ( SachModel sachModel ){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("ten_sach", sachModel.getTenSach());
        values.put("gia_thue", sachModel.getGiaThue());
        values.put("ten_the_loai", sachModel.getTenTL());
        values.put("nam_suat_ban",sachModel.getNam_xuat_ban());
        values.put("id_the_loai_sach", sachModel.getIdTL());

        int check = database.update("sach", values, "id_sach = ?", new String[]{String.valueOf(sachModel.getId())});
        return  check!=-1;
    }

    public LoaiSachModel getLoaiSachById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        LoaiSachModel loaiSach = null;

        try {
            String[] columns = {"id_loai_sach", "ten_the_loai", "ngay"};
            String selection = "id_loai_sach = ?";
            String[] selectionArgs = {String.valueOf(id)};

            cursor = db.query("loai_sach", columns, selection, selectionArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                @SuppressLint("Range") int idLoaiSach = cursor.getInt(cursor.getColumnIndex("id_loai_sach"));
                @SuppressLint("Range") String tenTheLoai = cursor.getString(cursor.getColumnIndex("ten_the_loai"));

                loaiSach = new LoaiSachModel(idLoaiSach, tenTheLoai);
            }
        } catch (Exception e) {
            Log.e("LoaiSachDao", "Error querying LoaiSach by ID: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return loaiSach;
    }



}
