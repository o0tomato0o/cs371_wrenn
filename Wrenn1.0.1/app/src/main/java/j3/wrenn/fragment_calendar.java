package j3.wrenn;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by newuser on 4/25/15.
 */
public class fragment_calendar extends Fragment {
    //public ArrayList<Event> eventList = new ArrayList<EventLog.Event>();
    CalendarView cal;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cal = (CalendarView) getActivity().findViewById(R.id.calendarView1);
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                Toast.makeText(getActivity().getApplicationContext(),
                        +(++month)+"/"+dayOfMonth+"/"+year, Toast.LENGTH_LONG).show();

                //trigger an update to the list according to the date here

                ArrayList<String> temp = getDailyEvents(month, dayOfMonth, year);
                //convert arraylist to string array

                String[] data = new String[temp.size()];
                data = temp.toArray(data);

//                int index = 0;
//                for (Object value : temp)
//                {
//                    Log.d("fragment_calendar ","name of the event: " +String.valueOf(value));
//                    data[index] = String.valueOf(value);
//                    index++;
//                }

                ListView lv = (ListView) getActivity().findViewById(R.id.dailyView1);
                ArrayAdapter list1 = new ArrayAdapter<String>(
                        getActivity(), android.R.layout.simple_list_item_1,data);
                lv.setAdapter(list1);

            }
        });
    }

    public ArrayList<String> getDailyEvents(int m, int d, int y)
    {
        List<Information> data = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<String>();

        DBOperations db = new DBOperations(getActivity());
        Cursor cr = db.retrieve(db);
        //cr.moveToFirst();
        Log.d("fragment_calendar", "Before LOOP");
        while(cr.moveToNext())
        {
            int mYear = y;
            int mMonth = m;
            int mDay = d;

            int dbDay = Integer.parseInt(cr.getString(5));
            int dbMonth = Integer.parseInt(cr.getString(4));
            int dbYear = Integer.parseInt(cr.getString(3));
            // year day month
            Log.d("fragment_today ","Value of calendar selected day: " +Integer.toString(mDay));
            Log.d("fragment_today", "Before if Statement " + cr.getString(3) + cr.getString(4) + cr.getString(5) + " ID " + cr.getColumnIndex("id"));

            if(dbDay == mDay && dbMonth == mMonth && dbYear == mYear) {
                Information current = new Information();
                current.title = cr.getString(1);
                titles.add(current.title);
                current.id = cr.getString(0);
                Log.d("fragment_calendar ","Title of the event is : "+current.title);

                data.add(current);
                Log.d("fragment_calendar", "LOOPING THROUGH DB FOR INFO" + cr.getString(4));
            }

        }

        return titles;
    }

}
