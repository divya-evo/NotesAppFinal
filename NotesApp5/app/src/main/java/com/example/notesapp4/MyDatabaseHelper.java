package com.example.notesapp4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.CaseMap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "NoteLibrary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "note_entry";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "note_title";
    private static final String COLUMN_CONTENT = "note_content";
    //public static final String COLUMN_PAGES = "note_entry";


    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String query = "CREATE TABLE " + TABLE_NAME +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITLE + " TEXT, " +
            COLUMN_CONTENT + " TEXT); " ;

    db.execSQL(query);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<NoteItem> getAllNotes(){
        String tableName = TABLE_NAME;
        String[] columns = {"_id", "note_title", "note_content"};

        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<NoteItem> storeNotes = new ArrayList<>();
        Cursor cursor = db.query(
                tableName,
                columns,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()){
            do {
                String id = cursor.getString(0);
                String title = cursor.getString(1);
                String content = cursor.getString(2);

                // Add cursor row to storeNotes
                storeNotes.add(new NoteItem( title, content, false));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return storeNotes;
    }
    //String id,
//    public void addNote( String title, String content){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//
//        //cv.put(COLUMN_ID, id);
//        cv.put(COLUMN_TITLE, title);
//        cv.put(COLUMN_CONTENT, content);
//       long result =  db.insert(TABLE_NAME, null, cv);
//       if(result == -1){
//           Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//       } else {
//           Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
//       }
//    }

    public void addNote(NoteItem noteItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        //cv.put(COLUMN_ID, id);
        cv.put(COLUMN_TITLE, noteItem.getTitle());
        cv.put(COLUMN_CONTENT, noteItem.getContent());
        long result =  db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);

        }
        return cursor;
    }
    public String getNewestId() {
        String id = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String tableName = TABLE_NAME;
        String[] columns = {"MAX(_id)"};
        Cursor cursor = db.query(
                tableName,
                columns,
                null,
                null,
                null,
                null,
                null,
                "1");

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getString(0);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return id;
    }

    void updateData(NoteItem noteItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, noteItem.getTitle());
        cv.put(COLUMN_CONTENT, noteItem.getContent());

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[] {String.valueOf(noteItem.getId())});
        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[] {row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deleted successfully!", Toast.LENGTH_SHORT).show();
        }
    }

}
