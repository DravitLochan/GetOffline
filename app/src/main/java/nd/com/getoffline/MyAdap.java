package nd.com.getoffline;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DravitLochan on 19-12-2016.
 */

public class MyAdap extends RecyclerView.Adapter<MyAdap.MyViewHolder> {
    private List<PageInfo> pageList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ln;

        public MyViewHolder(View view) {
            super(view);
            ln = (TextView) view.findViewById(R.id.link_name);
        }
    }

    public MyAdap(Context context,List<PageInfo> pageList) {

        this.context=context;
        this.pageList = pageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_link, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyAdap.MyViewHolder holder,final int position) {

        holder.ln.setText(pageList.get(position).getName());

        holder.ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context,webpage.class);
                i.putExtra("src",pageList.get(position).getSrcCode());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return pageList.size();
    }
}
