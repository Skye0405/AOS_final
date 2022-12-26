package com.example.aos;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlDataBaseHelper extends SQLiteOpenHelper {
    private static final String DataBaseName = "DataBase";
    private static final String DataBaseTable = "BusServer";
    private static final int DataBaseVersion = 1;

    public SqlDataBaseHelper(@Nullable Context context) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SqlTable = "CREATE TABLE IF NOT EXISTS " + DataBaseTable+ "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "License varchar (30)," +
                "busNum varchar (10) not null," +
                "busStop varchar (10) not null," +
                "Count INT DEFAULT 0 not null" +
                ")";
        //SqlTable = "ALTER TABLE " + DataBaseTable + " RENAME TO Bus";
        sqLiteDatabase.execSQL(SqlTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL = "DROP TABLE " + DataBaseTable;
        sqLiteDatabase.execSQL(SQL);
    }
}
