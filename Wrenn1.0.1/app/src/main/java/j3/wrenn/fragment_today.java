package j3.wrenn;

/**
 * Created by newuser on 4/25/15.
 */


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by newuser on 4/25/15.
 */


public class fragment_today extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView recView;
    private ViewAdapter adapter;
    SwipeRefreshLayout refreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d("In OnCreate Today", "In OnCreawte TODAY");
        View layout = inflater.inflate(R.layout.fragment_today, container, false);

        //Configure the swipe refresh layout
        refreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.refresh_today);
        refreshLayout.setOnRefreshListener(this);

        recView = (RecyclerView) layout.findViewById(R.id.today_list);
        adapter= new ViewAdapter(getActivity(),getData());
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return layout;
    }

    public List<Information> getData()
    {
        List<Information> data = new ArrayList<>();

        DBOperations db = new DBOperations(getActivity());
        Cursor cr = db.retrieve(db);
        //cr.moveToFirst();
        Log.d("fragment_today", "Before LOOP");
        while(cr.moveToNext())
        {
            Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH) + 1;
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            int dbDay = Integer.parseInt(cr.getString(5));
            int dbMonth = Integer.parseInt(cr.getString(4));
            int dbYear = Integer.parseInt(cr.getString(3));
            // year day month
            Log.d("fragment_today ","Value of current day: " +Integer.toString(mDay));
            Log.d("fragment_today", "Before if Statement " + cr.getString(3) + cr.getString(4) + cr.getString(5) + " ID " + cr.getColumnIndex("id"));
            if(dbDay == mDay && dbMonth == mMonth && dbYear == mYear) {
                Information current = new Information();
                current.title = cr.getString(1);
                current.id = cr.getString(0);

                data.add(current);
                Log.d("fragment_today", "LOOPING THROUGH DB FOR INFO" + cr.getString(4));
            }

        }

        return data;
    }

    @Override
    public void onRefresh()
    {   //Start showing the refresh animation
        refreshLayout.setRefreshing(true);

        //Simulate a long running activity
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){updateList();}
        }, 1500);

    }

    private void updateList()
    {
        Log.d("In fragment_tomorrow", "IN UPDATE LIST");
        //Add the next batch of countries to the list

        // recView = (RecyclerView) layout.findViewById(R.id.tomorrow_list);
        adapter= new ViewAdapter(getActivity(),getData());
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Signify that we are done refreshing
        refreshLayout.setRefreshing(false);
    }

}


