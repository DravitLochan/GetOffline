package nd.com.getoffline;


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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ln;

        public MyViewHolder(View view) {
            super(view);
            ln = (TextView) view.findViewById(R.id.link_name);
        }
    }

    public MyAdap(List<PageInfo> pageList) {
        this.pageList = pageList;
    }

    @Override
    public MyAdap.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_link, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyAdap.MyViewHolder holder, int position) {

        PageInfo page = pageList.get(position);
        holder.ln.setText(page.getName());
    }

    @Override
    public int getItemCount() {
        return pageList.size();
    }
}
