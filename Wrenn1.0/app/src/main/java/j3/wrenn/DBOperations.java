package j3.wrenn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Juan on 4/25/2015.
 */
public class DBOperations extends SQLiteOpenHelper{
    public static final String EVENT_ID = "_id";
    public static final String EVENT_NAME = "event_name";
    public static final String EVENT_LOCATION = "event_location";
    public static final String EVENT_TIME = "event_time";
    public static final String EVENT_DATE = "event_date";
    public static final String EVENT_MODE = "event_mode";
    public static final String EVENT_NOTIFICATION = "event_notification";

    public static final int EVENT_MONTH = 0;
    public static final int EVENT_YEAR = 0;
    public static final int EVENT_DAY = 0;
    public static final int EVENT_HOUR = 0;
    public static final int EVENT_MINUTE = 0;

    public static final String DATABASE_NAME = "wrenn_db";
    public static final String TABLE_NAME = "events";
    public static final int database_version = 1;

    public String CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    "("+ EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + EVENT_NAME + " TEXT,"
                    + EVENT_LOCATION + " TEXT,"
                    + EVENT_DATE + " TEXT,"
                    + EVENT_TIME + " TEXT,"
                    + EVENT_MODE + " TEXT,"
                    + EVENT_NOTIFICATION +
                    " TEXT );";

    public DBOperations(Context context)
    {
        super(context, DATABASE_NAME, null, database_version);
        Log.d("DBOps", "DB created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb)
    {
        sdb.execSQL(CREATE_QUERY);
        Log.d("DBOps", "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sdb, int old_vers, int new_vers)
    {

    }

    public void db_insert(DBOperations dbops, String name, String location, String time, String date, String mode, String not)
    {
        SQLiteDatabase query = dbops.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EVENT_NAME, name);
        cv.put(EVENT_LOCATION, location);
        cv.put(EVENT_DATE, date);
        cv.put(EVENT_TIME, time);
        cv.put(EVENT_MODE, mode);
        cv.put(EVENT_NOTIFICATION, not);
        // continue for all values
        long k = query.insert(TABLE_NAME, null, cv);
        Log.d("DBOps", "ONE Row Inserted");
    }

    public Cursor retrieve(DBOperations dbops)
    {
        SQLiteDatabase db = dbops.getReadableDatabase();
        String[] columns = {DBOperations.EVENT_NAME, DBOperations.EVENT_LOCATION, DBOperations.EVENT_DATE,
                DBOperations.EVENT_TIME, DBOperations.EVENT_MODE, DBOperations.EVENT_NOTIFICATION};
        Cursor cr = db.query(DBOperations.TABLE_NAME, columns, null, null, null, null, null);
        return cr;
    }
}
