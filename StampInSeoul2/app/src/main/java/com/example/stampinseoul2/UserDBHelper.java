package com.example.stampinseoul2;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.stampinseoul2.Model.ThemeData;
import com.example.stampinseoul2.Model.User;

import java.util.ArrayList;


public class UserDBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DB_NAME = "StampInSeoulDB";
    private static final int VERSION = 1;
    private static UserDBHelper userDBHelper = null;

    private UserDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    public static UserDBHelper getInstance(Context context) {
        if (userDBHelper == null) {
            userDBHelper = new UserDBHelper(context);
        }
        return userDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create Table userTBL(" +
                "userId TEXT not null primary key," +
                "userName TEXT not null," +
                "profileImage TEXT not null);");

//        sqLiteDatabase.execSQL("CREATE TABLE userTBL("
//                + "userId TEXT PRIMARY KEY,"
//                + "userName TEXT, "
//                + "profileImage TEXT); ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists userTBL");
        onCreate(sqLiteDatabase);
    }

    public void insertUserInfo(User user) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.execSQL("INSERT or REPLACE INTO userTBL values('" +
                    user.getUserId() + "','" + user.getUserName() + "','" + user.getProfileImage() + "');");
            Log.d("insertUserInfo", "성공");
        } catch (SQLException e) {
            Log.d("insertUserInfo", e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }

    }


    public void createUserLikePlaceTBL(User user) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + "LikePlace" + user.getUserId() + "TBL(title TEXT primary key, addr TEXT,mapX REAL,mapY REAL,image TEXT);");
            Log.d("DBcreateUserLikeTBL", "성공");
        } catch (SQLException e) {
            Log.d("DBcreateUserLikeTBL", e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }

    }

    public ArrayList<String> likeLoad(User user) {
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        try {
            sqLiteDatabase = this.getWritableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT title FROM LikePlace" + user.getUserId() + "TBL;", null);
            while (cursor.moveToNext()) {
                arrayList.add(cursor.getString(0));
            }
            Log.d("DBLikeLoad", "성공");
        } catch (SQLException e) {
            Log.d("DBLikeLoad", e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            sqLiteDatabase.close();
        }
        return arrayList;
    }

    public void likeDelete(User user, ThemeData data) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.execSQL("DELETE FROM LikePlace" + user.getUserId() + "TBL WHERE title='" + data.getTitle() + "';");
            Log.d("DBLikeDelete", "성공");
        } catch (SQLException e) {
            Log.d("DBLikeDelete", e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }

    }

    public void likeInsert(User user, ThemeData data) {
        SQLiteDatabase sqLiteDatabase = null;
        try {
            sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.execSQL("INSERT INTO LikePlace" + user.getUserId() + "TBL (title,addr,mapX,mapY,image) VALUES ('"
                    + data.getTitle() + "','"
                    + data.getAddr() + "','"
                    + data.getMapX() + "','"
                    + data.getMapY() + "','"
                    + data.getFirstImage() + "');");
            Log.d("DBLikeInsert", "성공");
        } catch (SQLException e) {
            Log.d("DBLikeInsert", e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }

    }

    public ArrayList<ThemeData> likePlaceLoad(User user) {
        ArrayList<ThemeData> arrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        try {
            sqLiteDatabase = this.getWritableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM LikePlace" + user.getUserId() + "TBL;", null);
            while (cursor.moveToNext()) {
                arrayList.add(new ThemeData(cursor.getString(0), cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getString(4)));
            }
            Log.d("DBLikePlaceLoad", "성공");
        } catch (SQLException e) {
            Log.d("DBLikePlaceLoad", e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
            sqLiteDatabase.close();
        }
        return arrayList;
    }

}

