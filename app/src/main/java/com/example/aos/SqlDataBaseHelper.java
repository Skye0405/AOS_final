package com.example.aos;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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
        String SqlTable1 = "CREATE TABLE IF NOT EXISTS Bus(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "License varchar (10) not null," +
                "busNum varchar (10) not null," +
                "busStop varchar (10) not null" +
                ")";
        sqLiteDatabase.execSQL(SqlTable1);
        String SqlTable2 = "CREATE TABLE IF NOT EXISTS passenger(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Pid varchar (30) not null," +
                "busNum varchar (10) not null," +
                "OnStop varchar (10)," +
                "OffStop varchar (10)," +
                "Date varchar (10)," +
                "getonTime varchar (10)," +
                "getOffTime varchar (10)," +
                "License varchar (10)" +
                ")";
        sqLiteDatabase.execSQL(SqlTable2);
        String SqlTable3 = "CREATE TABLE IF NOT EXISTS complain(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Location varchar (30) not null," +
                "Time varchar (30) not null," +
                "License varchar (10) not null," +
                "Driver varchar (10)," +
                "Subject varchar (30) not null," +
                "Content varchar (200) not null," +
                "Passenger varchar (10)," +
                "Contact varchar (10)" +
                ")";
        sqLiteDatabase.execSQL(SqlTable3);
        ArrayList<String> busNum = new ArrayList<>();
        ArrayList<String> busStop1 = new ArrayList<>();
        ArrayList<String> busStop2 = new ArrayList<>();
        busNum.add("100");
        busNum.add("20");
        //100號路線的
        busStop1.add("1.中山一路");
        busStop1.add("2.中山二路");
        busStop1.add("3.中山三路");
        busStop1.add("4.中山四路");
        busStop1.add("5.臨海一路");
        busStop1.add("6.臨海二路");
        busStop1.add("7.臨海三路");
        busStop1.add("8.臨海四路");
        busStop1.add("9.建國路");
        //20號路線的
        busStop2.add("1.中山一路");
        busStop2.add("2.中山二路");
        busStop2.add("3.中山三路");
        busStop2.add("4.中山四路");
        busStop2.add("5.五福一路");
        busStop2.add("6.五福二路");
        busStop2.add("7.五福三路");
        busStop2.add("8.五福四路");
        busStop2.add("9.青年一路");
        for(int j = 0; j < busStop1.size(); j++) {
            String addData = "INSERT INTO bus_geton(busNum, busStop, Count)" +
                    "VALUES('" + busNum.get(0) + "', '" + busStop1.get(j) + "', 0)";
            sqLiteDatabase.execSQL(addData);
            addData = "INSERT INTO bus_geton(busNum, busStop, Count)" +
                    "VALUES('" + busNum.get(1) + "', '" + busStop2.get(j) + "', 0)";
            sqLiteDatabase.execSQL(addData);
        }
        String addData = "INSERT INTO Bus(License, busNum, busStop)" +
                "VALUES('uuu-1111', '100', '中山一路')";
        sqLiteDatabase.execSQL(addData);
        String addRecord1 = "INSERT INTO passenger(Pid, busNum, OnStop, OffStop, Date, getonTime, getoffTime, License)" +
                "VALUES('111111', '100', '3.中山三路', '6.臨海二路', '2022-12-23', '12:51:25', '13:13:06', 'uuu-1111')";
        sqLiteDatabase.execSQL(addRecord1);
        String addRecord2 = "INSERT INTO passenger(Pid, busNum, OnStop, OffStop, Date, getonTime, getoffTime, License)" +
                "VALUES('111111', '20', '3.中山三路', '9.青年一路', '2022-12-25', '11:31:48', '12:23:53', 'jid-2567')";
        sqLiteDatabase.execSQL(addRecord2);
        System.out.println("資料庫創建成功了喔喔喔喔喔喔喔喔喔喔喔喔喔喔喔喔");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //String SQL = "DROP TABLE " + DataBaseTable;
        sqLiteDatabase.execSQL("DROP TABLE bus_geton");
        sqLiteDatabase.execSQL("DROP TABLE Bus");
        sqLiteDatabase.execSQL("DROP TABLE passenger");
        sqLiteDatabase.execSQL("DROP TABLE complain");
    }
}
