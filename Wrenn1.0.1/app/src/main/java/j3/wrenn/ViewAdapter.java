package j3.wrenn;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Context c;
    public ViewAdapter(Context context, List<Information> data)
    {
        c = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position)
    {
        Information current = data.get(position);
        DBOperations d = new DBOperations(c);
        d.db_delete(current.id);
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.custom_row, parent,false);
        Log.d("ViewAdapter", "onCreateViewHolder called");
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {
        Information current = data.get(position);
        Log.d("ViewAdapter", "onBindViewHolder " +position);
        holder.title.setText(current.title);

    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView title;
        ImageView img;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.list_text);
            img = (ImageView) itemView.findViewById(R.id.list_img);
            img.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(c, "Item Deleted at " + getPosition() , Toast.LENGTH_SHORT).show();
            delete(getPosition());
        }
    }
}
