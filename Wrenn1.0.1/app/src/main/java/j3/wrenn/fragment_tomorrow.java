package j3.wrenn;

/**
 * Created by newuser on 4/25/15.
 */
import android.database.Cursor;
import android.os.Bundle;
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
import android.os.Handler;

/**
 * Created by newuser on 4/25/15.
 */
public class fragment_tomorrow extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView recView;
    private ViewAdapter adapter;
    SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
    {
        Log.d("In OnCreate Tomorrow", "In OnCreawte TOMORROW");
        View layout = inflater.inflate(R.layout.fragment_tomorrow, container, false);

        //Configure the swipe refresh layout
        refreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.refresh_tomorrow);
        refreshLayout.setOnRefreshListener(this);

        // Put the list
        recView = (RecyclerView) layout.findViewById(R.id.tomorrow_list);
        adapter= new ViewAdapter(getActivity(),getTomorrowData());
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return layout;
    }


    public List<Information> getTomorrowData()
    {
        List<Information> data = new ArrayList<>();

        DBOperations db = new DBOperations(getActivity());
        Cursor cr = db.retrieve(db);
        //cr.moveToFirst();

        while(cr.moveToNext())
        {
            Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH) + 1;
            int mDay = c.get(Calendar.DAY_OF_MONTH) +1;

            int dbDay = Integer.parseInt(cr.getString(5));
            int dbMonth = Integer.parseInt(cr.getString(4));
            int dbYear = Integer.parseInt(cr.getString(3));

            Log.d("fragment_tomorrow","Value of current date: " + Integer.toString(mDay) + Integer.toString(mMonth) + Integer.toString(mYear));
            Log.d("fragment_tomorrow", "Before if Statement " + cr.getString(3) + cr.getString(4) + cr.getString(5));
            if(dbDay == mDay && dbMonth == mMonth && dbYear == mYear) {
                Information current = new Information();
                current.title = cr.getString(1);
                current.id = cr.getString(0);
                data.add(current);
                Log.d("fragment_tomorrow", "LOOPING THROUGH DB FOR INFO" + cr.getString(4));
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
        adapter= new ViewAdapter(getActivity(),getTomorrowData());
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Signify that we are done refreshing
        refreshLayout.setRefreshing(false);
    }

}
