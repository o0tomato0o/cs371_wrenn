package j3.wrenn;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.ArrayList;

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
        cal = (CalendarView) getActivity().findViewById(R.id.calendarView);
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                Toast.makeText(getActivity().getApplicationContext(),
                        +month+"/"+dayOfMonth+"/"+year, Toast.LENGTH_LONG).show();
                //trigger an update to the list according to the date here
            }
        });
    }

}
/*

package com.kidkare.navigationdrawer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import com.example.navigationdrawer.R;
import com.kidkare.dataaccessobjects.Event;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ListAdapter;
import android.widget.ListView;

public class FragmentCalendar extends Fragment {
	public ArrayList<Event> eventList = new ArrayList<Event>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.US);
	GregorianCalendar cal = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));

    public static Fragment newInstance(Context context) {
    	FragmentCalendar f = new FragmentCalendar();

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_calendar, null);
        final CalendarView c = (CalendarView) root.findViewById(R.id.calendarView1);
        cal.setTimeInMillis(c.getDate());
        eventList = MainActivity.datasource.getEvent(Integer.parseInt(sdf.format(cal.getTime())));

        ListAdapter adapter = new EventListAdapter(getActivity(), R.layout.notification_list_item, eventList);
        ListView listview = (ListView)root.findViewById(R.id.eventList);
        listview.setAdapter(adapter);

        c.setOnDateChangeListener(new OnDateChangeListener() {

    		@Override
    		public void onSelectedDayChange(CalendarView view, int year, int month,int dayOfMonth) {
    			cal.setTimeInMillis(c.getDate());
    	        eventList = MainActivity.datasource.getEvent(Integer.parseInt(sdf.format(cal.getTime())));

    	        ListAdapter adapter = new EventListAdapter(getActivity(), R.layout.notification_list_item, eventList);
    	        ListView listview = (ListView)root.findViewById(R.id.eventList);
    	        listview.setAdapter(adapter);
    		}
    	});


        return root;
    }

}

Status API Training Shop Blog About
© 2015 GitHub, Inc. Terms Privacy Security Contact

* */