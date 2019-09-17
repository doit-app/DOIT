package com.example.doitappfin.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.doitappfin.R;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>  {

    private List<String> Address;
    private ArrayList<Float> Distance;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ArrayList examheading,clientheading;
    Integer lastSelectedItemPos;
    int selectedPosition=-1,first=1;
    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, ArrayList<String> data, ArrayList<Float> data1) {
        this.mInflater = LayoutInflater.from(context);
        this.Address = data;
        this.Distance = data1;

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);





        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(selectedPosition==position || first==1){
            holder.myTextViewaddr.setTextColor(Color.parseColor("#000000"));
            holder.myTextViewdist.setTextColor(Color.parseColor("#000000"));

        }
        else{
            holder.myTextViewaddr.setTextColor(Color.parseColor("#AAAAAA"));
            holder.myTextViewdist.setTextColor(Color.parseColor("#AAAAAA"));

        }

        if(Distance.size()>0) {
            if (Address.get(position) != null )   {
                String addr = Address.get(position);
                Float dist = Distance.get(position);
                holder.myTextViewaddr.setText("" + addr+"");
                String c = dist + "";
                c = c.substring(0, 4);
                holder.myTextViewdist.setText((c+" km").trim());

               // System.out.println(Address);
            }
        }








    }

    // total number of rows
    @Override
    public int getItemCount() {
        return Address.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextViewaddr,myTextViewdist;

        ViewHolder(View itemView) {
            super(itemView);
            myTextViewaddr = itemView.findViewById(R.id.addr);
            myTextViewdist = itemView.findViewById(R.id.dist);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            selectedPosition=getAdapterPosition();
            first=0;
            notifyDataSetChanged();
        }



    }



    // convenience method for getting data at click position
    String getItem(int id) {
        return Address.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void UpdateItemsList(List<String> addr, List<Float> dist)
    {
        Address=new ArrayList();
        Address.addAll(addr);
        Distance=new ArrayList();
        Distance.addAll(dist);
        notifyDataSetChanged();

    }

    class SubcategoryGetSet{
        //your other objects, getters and setters
        boolean selected;
        public boolean isSelected() { return selected; }
        public void setSelected(boolean selected) { this.selected = selected; }
    }

}