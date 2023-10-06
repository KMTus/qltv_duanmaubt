package com.example.qltv_duanmau.dao;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qltv_duanmau.database.SqlHelper;
import com.example.qltv_duanmau.models.LoaiSachModel;
import com.example.qltv_duanmau.models.ThuThuModel;

import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private final SqlHelper dbHelper;

    public AccountDao(Context context) {
        dbHelper = new SqlHelper(context);
    }

    public ArrayList<ThuThuModel> getAllAccounts() {
        ArrayList<ThuThuModel> accountList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM thu_thu", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    // Lấy thông tin tài khoản từ cột dựa trên tên cột
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id_thu_thu"));
                    @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("username"));
                    @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("ten"));
                    @SuppressLint("Range") int role = Integer.parseInt(cursor.getString(cursor.getColumnIndex("role")));
                    // Tạo đối tượng Account và thêm vào ArrayList
                    ThuThuModel account = new ThuThuModel(id, username, password, name, role);
                    accountList.add(account);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        return accountList;
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("select * from thu_thu where username=?",
                new String[]{username});
        if (c.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean removeAccount(int id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int row = sqLiteDatabase.delete("thu_thu", "id_thu_thu = ?", new String[]{String.valueOf(id)});
        return row != -1;
    }

    public boolean updateAccount(ThuThuModel thuThuModel) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("username", thuThuModel.getUsername());
        cv.put("password", thuThuModel.getPassword());
        cv.put("ten", thuThuModel.getName());


        int check = database.update("thu_thu", cv, "id_thu_thu = ?", new String[]{String.valueOf(thuThuModel.getId())});
        return check != -1;
    }


    public void register(String username, String password, String name, int role) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("role", role);
        cv.put("ten", name);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert("thu_thu", null, cv);
        db.close();
    }

    public int login(String username, String password, int role) {
        int result = 0;
        String str[] = new String[3];
        str[0] = username;
        str[1] = password;
        str[2] = String.valueOf(role);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from thu_thu where username=? and password=? and role=?", str);
        if (c.moveToNext()) {
            result = 1;
        }
        return result;
    }

    public int changePassword(ThuThuModel nhanVien) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("password", nhanVien.getPassword());
        return sqLiteDatabase.update("thu_thu", values, "id_thu_thu=?", new String[]{String.valueOf(nhanVien.getId())});
    }

    public ThuThuModel getThuThuById(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "id_thu_thu",
                "username",
                "password",
                "role",
                "ten"
        };

        String selection = "username = ?";
        String[] selectionArgs = {String.valueOf(username)};

        Cursor cursor = db.query(
                "thu_thu",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();

            ThuThuModel thuThu = new ThuThuModel();
            thuThu.setId((int) cursor.getLong(cursor.getColumnIndexOrThrow("id_thu_thu")));
            thuThu.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
            thuThu.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            thuThu.setRole(cursor.getInt(cursor.getColumnIndexOrThrow("role")));
            thuThu.setName(cursor.getString(cursor.getColumnIndexOrThrow("ten")));

            cursor.close();
            return thuThu;
        }

        return null;
    }

    public ThuThuModel getObjectID(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "id_thu_thu",
                "username",
                "password",
                "role",
                "ten"
        };

        String selection = "id_thu_thu = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(
                "thu_thu",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();

            ThuThuModel thuThu = new ThuThuModel();
            thuThu.setId((int) cursor.getLong(cursor.getColumnIndexOrThrow("id_thu_thu")));
            thuThu.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
            thuThu.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            thuThu.setRole(cursor.getInt(cursor.getColumnIndexOrThrow("role")));
            thuThu.setName(cursor.getString(cursor.getColumnIndexOrThrow("ten")));

            cursor.close();
            return thuThu;
        }

        return null;
    }



}
