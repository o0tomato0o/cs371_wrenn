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
import java.util.List;

/**
 * Created by newuser on 4/25/15.
 */
public class fragment_tomorrow extends Fragment {
    private RecyclerView recView;
    private ViewAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View layout = inflater.inflate(R.layout.fragment_tomorrow, container, false);
        recView = (RecyclerView) layout.findViewById(R.id.tomorrow_list);
        adapter= new ViewAdapter(getActivity(),getTomorrowData());
        adapter.notifyItemRangeChanged(0, adapter.getItemCount());
        recView.setAdapter(adapter);
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        try {
            final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) getView().findViewById(R.id.refresh_tomorrow);
            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                /*adapter= new ViewAdapter(getActivity(),getTomorrowData());
                adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                recView.setAdapter(adapter);
                recView.setLayoutManager(new LinearLayoutManager(getActivity()));*/
                }
            });

        }catch(NullPointerException e) {
            System.out.println(e.toString());
        }

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
            Information current = new Information();
            current.title = cr.getString(0);
            data.add(current);
            Log.d("fragment_tomorrow", "LOOPING THROUGH DB FOR INFO");
        }

        return data;
    }

}
