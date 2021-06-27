package com.example.onthi_luyentap_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NguyenHuuLuan_SQLite extends SQLiteOpenHelper {
    //TODO câu 2
    private static NguyenHuuLuan_SQLite instance = null;
    //TODO 2.1 Sửa Các Cột Cần Thiết
    public static final String DATABASE_NAME = "allthings_management";
    private static final String TABLE_NAME = "Contact_NguyenHuuLuan";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NUMBER = "number";

    private static int VERSION = 1;

    private Context context;
    // hàm tạo
    public NguyenHuuLuan_SQLite(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }
    // Singleton
    public static NguyenHuuLuan_SQLite getInstance(Context context){
        if (instance == null){
            instance = new NguyenHuuLuan_SQLite(context);
            setdata();
        }
        return instance;
    }


    // TODO 2.2 Câu lệnh tạo SQL
    private String SQLCREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    COLUMN_ID + " INTERGER primary key , " +
                    COLUMN_NAME + " TEXT , " +
                    COLUMN_NUMBER + " TEXT ) " ;


    // TODO 2.3 Khởi tạo dữ liệu cho SQL LITE Với tên sinh viên là tên thứ 5
    private static void setdata() {
        Contact_NguyenHuuLuan object1 = new Contact_NguyenHuuLuan(1,"Ân Vinh","0123456778");
        Contact_NguyenHuuLuan object2 = new Contact_NguyenHuuLuan(2,"Quan Loan","01452456778");
        Contact_NguyenHuuLuan object3 = new Contact_NguyenHuuLuan(3,"Lân Vũ","012342346778");
        Contact_NguyenHuuLuan object4 = new Contact_NguyenHuuLuan(4,"Nguyễn Hữu Thảo","012345378");
        Contact_NguyenHuuLuan object5 = new Contact_NguyenHuuLuan(5,"Vân Vận","0123356778");
        Contact_NguyenHuuLuan object6 = new Contact_NguyenHuuLuan(6,"Khôi Ngô","0123456778");

        instance.insert(object1);
        instance.insert(object2);
        instance.insert(object3);
        instance.insert(object4);
        instance.insert(object5);
        instance.insert(object6);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //chạy câu lệnh tạo bảng
        db.execSQL(SQLCREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //TODO 2.4 SQL INSERT
    public long insert(Contact_NguyenHuuLuan table)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //==================================================
        values.put(COLUMN_ID,table.getId());
        values.put(COLUMN_NAME,table.getName());
        values.put(COLUMN_NUMBER,table.getPhonenumber());

        //==================================================
        //chạy câu lệnh insert
        long result = db.insert(TABLE_NAME,null,values);
        db.close();
        return result;
    }
    // TODO 2.5 SQL UPDATE
    public long edit(Contact_NguyenHuuLuan table)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //==================================================

        values.put(COLUMN_ID,table.getId());
        values.put(COLUMN_NAME,table.getName());
        values.put(COLUMN_NUMBER,table.getPhonenumber());

        //=================================================
        long result = db.update(TABLE_NAME,values, COLUMN_ID + "=?", new String[]{String.valueOf(table.getId())});
        db.close();
        return result;
    }
    // TODO 2.6 SQL DELETE
    public long delete(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,COLUMN_ID + "=?",new String[]{id});
        db.close();
        return result;
    }
    // TODO 2.7 SQL GET ALL DATA
    public List<Contact_NguyenHuuLuan> getALL()
    {
        List<Contact_NguyenHuuLuan> listdata = new ArrayList<>();
        String querySELECT = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_NAME ;

        SQLiteDatabase db = this.getReadableDatabase();
        // con trỏ trong sql
        Cursor cursor = db.rawQuery(querySELECT,null);
        if (cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                //==========================================================
                int id                    = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name               = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String phonenumber        = cursor.getString(cursor.getColumnIndex(COLUMN_NUMBER));
                Contact_NguyenHuuLuan row = new Contact_NguyenHuuLuan(id,name,phonenumber);
                //=========================================================================
                // thêm đối tượng vào danh sách
                listdata.add(row);
                cursor.moveToNext();
            }
        }
        db.close();
        return listdata;
    }
}
