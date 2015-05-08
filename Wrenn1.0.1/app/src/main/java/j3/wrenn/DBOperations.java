package j3.wrenn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Juan on 4/25/2015.
 */
public class DBOperations extends SQLiteOpenHelper{
    public static final String EVENT_ID = "id";
    public static final String EVENT_NAME = "event_name";
    public static final String EVENT_LOCATION = "event_location";
    public static final String EVENT_TIME = "event_time";
    public static final String EVENT_DATE = "event_date";
    public static final String EVENT_MODE = "event_mode";
    public static final String EVENT_NOTIFICATION = "event_notification";

    public static final String EVENT_MONTH = "_month";
    public static final String EVENT_YEAR = "_year";
    public static final String EVENT_DAY = "_day";
    public static final String EVENT_HOUR = "_hour";
    public static final String EVENT_MINUTE = "_minute";

    public static final String DATABASE_NAME = "wrenn_db";
    public static final String TABLE_NAME = "events";
    public static final int database_version = 1;

    public static Map<String, String> id_map = new HashMap<String, String>();

    public String CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    "("+ EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + EVENT_NAME + " TEXT,"
                    + EVENT_LOCATION + " TEXT,"
                    + EVENT_YEAR + " INTEGER,"
                    + EVENT_MONTH + " INTEGER,"
                    + EVENT_DAY + " INTEGER,"
                    + EVENT_HOUR + " INTEGER,"
                    + EVENT_MINUTE + " INTEGER,"
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

    public void db_insert(DBOperations dbops, String name, String location, int year, int month, int day, int hour, int min, String mode, String not)
    {
        SQLiteDatabase query = dbops.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EVENT_NAME, name);
        cv.put(EVENT_LOCATION, location);
        cv.put(EVENT_YEAR, year);
        cv.put(EVENT_MONTH, month);
        cv.put(EVENT_DAY, day);
        cv.put(EVENT_HOUR, hour);
        cv.put(EVENT_MINUTE, min);
        cv.put(EVENT_MODE, mode);
        cv.put(EVENT_NOTIFICATION, not);
        // continue for all values
        long k = query.insert(TABLE_NAME, null, cv);
        id_map.put(Integer.toString((int) k), name);
        Log.d("DBOps", "ONE Row Inserted ID " + Integer.toString((int) k));
    }

    public void db_delete(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
             db.delete(TABLE_NAME, "ID = ?", new String[]{id});
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.close();
        }
    }

    public Cursor retrieve(DBOperations dbops)
    {   //query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy)
        SQLiteDatabase db = dbops.getReadableDatabase();
        String[] columns = {DBOperations.EVENT_ID, DBOperations.EVENT_NAME, DBOperations.EVENT_LOCATION, DBOperations.EVENT_YEAR, DBOperations.EVENT_MONTH, DBOperations.EVENT_DAY,
                DBOperations.EVENT_HOUR, DBOperations.EVENT_MINUTE, DBOperations.EVENT_MODE, DBOperations.EVENT_NOTIFICATION};

        Log.d("DBOps", DBOperations.EVENT_NAME);
        Cursor cr = db.query(DBOperations.TABLE_NAME, columns, null, null, null, null, null);
        Log.d("DBops", "After query");
        return cr;
    }

/*    public Cursor retrieveTomorrow(DBOperations dbops)
    {
        SQLiteDatabase db = dbops.getReadableDatabase();
        String[] columns = {DBOperations.EVENT_NAME, DBOperations.EVENT_LOCATION, DBOperations.EVENT_YEAR, DBOperations.EVENT_MONTH, DBOperations.EVENT_DAY,
                DBOperations.EVENT_HOUR, DBOperations.EVENT_MINUTE, DBOperations.EVENT_MODE, DBOperations.EVENT_NOTIFICATION};

        Log.d("DBOps", DBOperations.EVENT_NAME);
        Cursor cr = db.query(DBOperations.TABLE_NAME, columns, null, null, null, null, null);
        Log.d("DBops", "After query");
        return cr;
    }*/

}
