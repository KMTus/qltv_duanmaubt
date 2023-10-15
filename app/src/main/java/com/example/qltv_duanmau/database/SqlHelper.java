package com.example.qltv_duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Library.db";

    private static final int VER = 3;

    public SqlHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // bảng thể loại sách
        String tb_loai_sach =
                "CREATE TABLE loai_sach (" +
                        "id_loai_sach INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "ten_the_loai TEXT NOT NULL," +
                        "ngay DATE)";


        sqLiteDatabase.execSQL(tb_loai_sach);


        // bảng sách

        String tb_sach =
                "CREATE TABLE sach (" +
                        "id_sach INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "ten_sach TEXT NOT NULL," +
                        "gia_thue INTEGER," +
                        "ten_the_loai INTEGER," +
                        "id_the_loai_sach INTEGER REFERENCES loai_sach(id_loai_sach))";  // Chỉnh "INTERGER" thành "INTEGER"

        sqLiteDatabase.execSQL(tb_sach);

        String tb_thanhvien =
                "CREATE TABLE thanh_vien (" +
                        "id_thanh_vien INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "ten TEXT NOT NULL," +
                        "nam_sinh TEXT NOT NULL," +
                        "can_cuoc text not null)";

        sqLiteDatabase.execSQL(tb_thanhvien);

        String tb_tk =
                "CREATE TABLE thu_thu (" +
                        "id_thu_thu INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "username TEXT NOT NULL," +
                        "password TEXT NOT NULL, " +
                        "role INTEGER NOT NULL, " +
                        "ten TEXT NOT NULL)";

        sqLiteDatabase.execSQL(tb_tk);

        String insert_tk = "INSERT INTO thu_thu (username, password, role, ten) VALUES ('admin', 'admin123', 1, ' admin');";
        sqLiteDatabase.execSQL(insert_tk);

        String phieu_muon =
                "CREATE TABLE phieu_muon(" +
                        "id_phieu_muon INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "id_sach INTEGER REFERENCES sach(id_sach)," +
                        "id_thanh_vien INTEGER REFERENCES thanh_vien(id_thanh_vien)," +
                        "id_thu_thu INTEGER REFERENCES thu_thu(id_thu_thu)," +
                        "gia_tien REAL NOT NULL," +
                        "trang_thai INTEGER NOT NULL," +
                        "ngay DATE NOT NULL)";
        sqLiteDatabase.execSQL(phieu_muon);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropLoaiSach = "drop table if exists loai_sach";
        sqLiteDatabase.execSQL(dropLoaiSach);

        String dropSach = "drop table if exists sach";
        sqLiteDatabase.execSQL(dropSach);

        String dropthanhVien = "drop table if exists thanh_vien";
        sqLiteDatabase.execSQL(dropthanhVien);

        String drop_tai_khoan = "drop table if exists thu_thu";
        sqLiteDatabase.execSQL(drop_tai_khoan);

        String drop_phieu_muon = "drop table if exists phieu_muon";
        sqLiteDatabase.execSQL(drop_phieu_muon);

        onCreate(sqLiteDatabase);
    }
}
