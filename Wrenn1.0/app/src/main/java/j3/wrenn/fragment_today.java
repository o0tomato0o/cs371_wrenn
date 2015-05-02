package j3.wrenn;

/**
 * Created by newuser on 4/25/15.
 */


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by newuser on 4/25/15.
 */


public class fragment_today extends Fragment {

    private RecyclerView recView;
    private ViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View layout = inflater.inflate(R.layout.fragment_today, container, false);
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

        while(cr.moveToNext())
        {

            Information current = new Information();
            current.title = cr.getString(0);
            data.add(current);
            log.d("current is " + current )
            Log.d("fragment_today", "LOOPING THROUGH DB FOR INFO");
        }

        return data;
    }

}


