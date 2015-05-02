package j3.wrenn;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Juan on 4/25/2015.
 */
public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder>
{
    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();
    public ViewAdapter(Context context, List<Information> data)
    {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.custom_row, parent,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        Information current = data.get(position);
        holder.title.setText(current.title);
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.list_text);
        }
    }
}
