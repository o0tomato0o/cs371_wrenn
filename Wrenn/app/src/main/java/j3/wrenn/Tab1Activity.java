package j3.wrenn;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Juan on 4/2/2015.
 */
//Source: http://www.androidhive.info/2012/05/android-combining-tab-layout-and-list-view/
public class Tab1Activity extends ListActivity{

    //progress dialog
    private ProgressDialog pDialog;

    ArrayList<HashMap<String, String>> eventList;

    static final String TAG_FROM = "from";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1_list);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "BBr1Gxg1QXLrDAVZZlb6W8OVfwUQEEHCtuOUd8PA", "fgLZMkqVfO5giVGzkPAUiZjjV7hUGyf14BoVVERZ");

        eventList = new ArrayList<HashMap<String, String>>();
        new LoadEvents().execute();
    }

    /**
     * Background Async Task to Load all INBOX messages by making HTTP Request
     * */
    class LoadEvents extends AsyncTask<String, String, String>
    {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Tab1Activity.this);
            pDialog.setMessage("Loading Events ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args)
        {
            //Get events from source

            ParseQuery<ParseObject> query = ParseQuery.getQuery("EventObject");
            query.whereEqualTo("event_date", "Today");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> event, com.parse.ParseException e) {
                    if(e == null)
                    {
                        Log.d("Tab1Activity", "EXCEPTION NULL! OK");
                        String info = " ";
                        HashMap<String, String> map = new HashMap<String, String>();
                        for(ParseObject p : event)
                        {
                            info = p.getString("event_name") + " " + p.getString("event_time");
                            map.put(TAG_FROM, info);
                            eventList.add(map);
                            Log.d("Tab1Activity", "ADDED ITEM TO EVENTLIST: " + p.getString("event_name"));
                        }
                    }else{
                        Log.d("Tab1Activity", "SOMETHING WRONG! ELSE!");
                    }
                }
            });

            //Check log cat for response
            Log.d("Tab1Activity", "in doInBackground");


            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            Tab1Activity.this, eventList,
                            R.layout.tab1_list_item, new String[] {TAG_FROM},
                            new int[] { R.id.from});
                    // updating listview
                    setListAdapter(adapter);
                }
            });

        }
    }

}
