package com.developnerz.indie_indonesianenglishdictionary.utils.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.developnerz.indie_indonesianenglishdictionary.utils.sqlite.DatabaseContract.DictionaryColumns.DESC;
import static com.developnerz.indie_indonesianenglishdictionary.utils.sqlite.DatabaseContract.DictionaryColumns.KEYWORD;
import static com.developnerz.indie_indonesianenglishdictionary.utils.sqlite.DatabaseContract.TABLE_NAME_EN_ID;
import static com.developnerz.indie_indonesianenglishdictionary.utils.sqlite.DatabaseContract.TABLE_NAME_ID_EN;

/**
 * Created by Rych Emrycho on 8/31/2018 at 12:35 AM.
 * Updated by Rych Emrycho on 8/31/2018 at 12:35 AM.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbdictionary";
    private static int DATABASE_VERSION = 1;
    private static String CREATE_TABLE_ID_EN = "create table "
            + TABLE_NAME_ID_EN + "(" + _ID + " integer primary key autoincrement, "
            + KEYWORD + " text not null, "
            + DESC + " text not null);";

    private static String CREATE_TABLE_EN_ID = "create table "
            + TABLE_NAME_EN_ID + "(" + _ID + " integer primary key autoincrement, "
            + KEYWORD + " text not null, "
            + DESC + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ID_EN);
        sqLiteDatabase.execSQL(CREATE_TABLE_EN_ID);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ID_EN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EN_ID);
        onCreate(sqLiteDatabase);
    }
}
