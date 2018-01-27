package com.example.mami.myapplication.adapter;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.mami.myapplication.R;
import com.example.mami.myapplication.bean.AmmendMent;

public class AmmendMentAdapter extends RecyclerView.Adapter<AmmendMentAdapter.ViewHolder> {
    ArrayList<AmmendMent> ammendList;
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtAmmendment;
        public View layout;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtAmmendment = (TextView) v.findViewById(R.id.tv_ammendamment);
        }
    }


     public AmmendMentAdapter( ArrayList<AmmendMent> ammendList) {
        this.ammendList = ammendList;
    }
    public void setData(ArrayList<AmmendMent> ammendList){
        this.ammendList=ammendList;
    }
     @Override
    public AmmendMentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.amenment_row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtAmmendment.setText(ammendList.get(position).getAmmendMentString());
     }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ammendList.size();
    }

}