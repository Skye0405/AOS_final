package com.example.aos;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlDataBaseHelper extends SQLiteOpenHelper {
    private static final String DataBaseName = "DataBase";
    private static final int DataBaseVersion = 1;

    public SqlDataBaseHelper(@Nullable Context context) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SqlTable = "CREATE TABLE IF NOT EXISTS bus_geton(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "busNum varchar (10) not null," +
                "busStop varchar (10) not null," +
                "Count INT DEFAULT 0 not null" +
                ")";
        sqLiteDatabase.execSQL(SqlTable);
        String SqlTable1 = "CREATE TABLE IF NOT EXISTS bus_getoff(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "License varchar (30) not null," +
                "busStop varchar (10) not null," +
                "Count INT DEFAULT 0 not null" +
                ")";
        sqLiteDatabase.execSQL(SqlTable1);
        String SqlTable2 = "CREATE TABLE IF NOT EXISTS passenger(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Pid varchar (30) not null," +
                "busNum varchar (10) not null," +
                "OnStop varchar (10)," +
                "OffStop varchar (10)," +
                "getonTime datetime," +
                "getOffTime datetime," +
                "License varchar (10)" +
                ")";
        sqLiteDatabase.execSQL(SqlTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //String SQL = "DROP TABLE " + DataBaseTable;
        sqLiteDatabase.execSQL("DROP TABLE bus_geton");
        sqLiteDatabase.execSQL("DROP TABLE bus_getoff");
        sqLiteDatabase.execSQL("DROP TABLE passenger");
    }
}
