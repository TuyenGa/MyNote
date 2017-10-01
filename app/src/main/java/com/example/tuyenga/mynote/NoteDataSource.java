package com.example.tuyenga.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.DateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tuyenga on 26/09/2017.
 */

public class NoteDataSource {
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String[] allColumns = {NoteSQLiteHelper.COLUMN_ID, NoteSQLiteHelper.COLUMN_NOTE, NoteSQLiteHelper.COLUMN_DATETIME};

    private Context context;

    public NoteDataSource(Context context)
    {
        this.context = context;
        sqLiteOpenHelper = new NoteSQLiteHelper(context);
    }
    public void open() throws SQLiteException{
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close() throws SQLiteException{
        sqLiteOpenHelper.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addNewNote(String note)
    {
        //get datetime
        String datetime = Calendar.getInstance().getTime().toString();

        // System.out.println(datetime);
        ContentValues values = new ContentValues();
        values.put(NoteSQLiteHelper.COLUMN_NOTE,note);
        values.put(NoteSQLiteHelper.COLUMN_DATETIME,datetime);

        // insert
        sqLiteDatabase.insert(NoteSQLiteHelper.TABLE_NAME,null,values);

        Toast.makeText(this.context,"add new note success",Toast.LENGTH_LONG).show();
    }
    public void editNote(String note,NoteModel Note)
    {

        String datetime = Calendar.getInstance().getTime().toString();

        ContentValues values = new ContentValues();
        values.put(NoteSQLiteHelper.COLUMN_NOTE,note);
        values.put(NoteSQLiteHelper.COLUMN_DATETIME,datetime);

        sqLiteDatabase.update(NoteSQLiteHelper.TABLE_NAME,values,NoteSQLiteHelper.COLUMN_ID+" = "+Note.getId(),null);

    }
    public void deleteNote(NoteModel note) throws SQLiteException{
        sqLiteDatabase.delete(NoteSQLiteHelper.TABLE_NAME, NoteSQLiteHelper.COLUMN_ID +" = "+ note.getId(),null);
        Toast.makeText(this.context,"delete note success",Toast.LENGTH_LONG).show();
    }

    public ArrayList<NoteModel> getAllNote()
    {
        ArrayList<NoteModel> arr = new ArrayList<NoteModel>();

        Cursor cursor = sqLiteDatabase.query(NoteSQLiteHelper.TABLE_NAME,allColumns,null,null,null,null,null);

        if (cursor == null)
        {
            return null;
        }
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            NoteModel model = cursorToModel(cursor);
            arr.add(model);
            cursor.moveToNext();
        }
        return  arr;
    }

    public NoteModel cursorToModel(Cursor cursor)
    {
        NoteModel model = new NoteModel();
        model.setId(cursor.getInt(0));
        model.setNote(cursor.getString(1));
        model.setDatetime(cursor.getString(2));

        return  model;
    }
}
