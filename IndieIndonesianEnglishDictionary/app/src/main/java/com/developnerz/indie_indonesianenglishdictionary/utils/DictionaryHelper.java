package com.developnerz.indie_indonesianenglishdictionary.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.developnerz.indie_indonesianenglishdictionary.model.DictionaryModel;
import com.developnerz.indie_indonesianenglishdictionary.utils.sqlite.DatabaseHelper;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.developnerz.indie_indonesianenglishdictionary.utils.sqlite.DatabaseContract.DictionaryColumns.DESC;
import static com.developnerz.indie_indonesianenglishdictionary.utils.sqlite.DatabaseContract.DictionaryColumns.KEYWORD;

/**
 * Created by Rych Emrycho on 8/31/2018 at 12:50 AM.
 * Updated by Rych Emrycho on 8/31/2018 at 12:50 AM.
 */
public class DictionaryHelper {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public DictionaryHelper(Context context) {
        this.context = context;
    }

    public DictionaryHelper open() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<DictionaryModel> getDataByKeyword(String tableName, String keyword){
        String result = "";
        Cursor cursor = database.query(tableName, null, KEYWORD
                + " LIKE ? ", new String[]{keyword + "%"}, null, null, _ID
                + " ASC", null);
        cursor.moveToFirst();
        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        DictionaryModel dictionaryModel;
        if (cursor.getCount() > 0){
            do {
                dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                dictionaryModel.setKeyword(cursor.getString(cursor.getColumnIndexOrThrow(KEYWORD)));
                dictionaryModel.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESC)));
                arrayList.add(dictionaryModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<DictionaryModel> getAllData(String tableName){
        Cursor cursor = database.query(tableName, null, null, null, null, null, _ID
        + " ASC", null);
        cursor.moveToFirst();
        ArrayList<DictionaryModel> arrayList = new ArrayList<>();
        DictionaryModel dictionaryModel;
        if (cursor.getCount() > 0){
            do {
                dictionaryModel = new DictionaryModel();
                dictionaryModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                dictionaryModel.setKeyword(cursor.getString(cursor.getColumnIndexOrThrow(KEYWORD)));
                dictionaryModel.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESC)));
                arrayList.add(dictionaryModel);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(String tableName, DictionaryModel dictionaryModel){
        ContentValues initialVal = new ContentValues();
        initialVal.put(KEYWORD, dictionaryModel.getKeyword());
        initialVal.put(DESC, dictionaryModel.getDesc());
        return database.insert(tableName, null, initialVal);
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(String tableName, DictionaryModel dictionaryModel){
        String sql = "INSERT INTO " + tableName
                + " (" + KEYWORD + ", " + DESC + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, dictionaryModel.getKeyword());
        stmt.bindString(2, dictionaryModel.getDesc());
        stmt.execute();
        stmt.clearBindings();
    }

    public int update(String tableName, DictionaryModel dictionaryModel){
        ContentValues args = new ContentValues();
        args.put(KEYWORD, dictionaryModel.getKeyword());
        args.put(DESC, dictionaryModel.getDesc());
        return database.update(tableName, args, _ID + " = '"
        + dictionaryModel.getId() + "'", null);
    }

    public int delete(String tableName, int id){
        return database.delete(tableName, _ID
                + " = '" + id + "'", null);
    }
}
