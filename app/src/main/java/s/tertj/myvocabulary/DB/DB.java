package s.tertj.myvocabulary.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DB {

    public static final String DB_NAME = "mydb";
    public static final int DB_VERSION = 1;
    public static final String DB_TABLE = "clothes";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EW = "englishWord";
    public static final String COLUMN_RW = "russianWord";
    public static final String COLUMN_WORD_TAG = "wordTag";

    public static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_EW + " text, " +
                    COLUMN_RW + " text, " +
                    COLUMN_WORD_TAG + " text" +
                    ");";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    public Cursor getCurrentData(String currentID) {
        String selection = COLUMN_ID + "=" + currentID;
        return mDB.query(DB_TABLE, null, selection, null, null, null, null);
    }
    public void addRec(String englishWord, String rusianWord, String tag) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EW, englishWord);
        cv.put(COLUMN_RW, rusianWord);
        cv.put(COLUMN_WORD_TAG, tag);
        mDB.insert(DB_TABLE, null, cv);
    }

    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    public void updateRec(long id, String name, String type, String color) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EW, name);
        cv.put(COLUMN_RW, type);
        cv.put(COLUMN_WORD_TAG, color);
        mDB.update(DB_TABLE, cv, COLUMN_ID + " = " + id, null);
    }
}