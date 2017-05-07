package s.tertj.myvocabulary.DB;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

public class MyCursorLoader extends CursorLoader {

    private DB db;

    public MyCursorLoader(Context context, DB db) {
        super(context);
        this.db = db;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = db.getAllData();
        return cursor;
    }

}