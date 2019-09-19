package com.example.doitappfin.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doitappfin.R;
import com.example.doitappfin.ui.RecyclerViewAdapterTrainCert;

import java.util.ArrayList;

public class myCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    ArrayList<myCourseModel> model;
    ArrayList<String> date,course;
    private OnItemClickListener mItemClickListener;


    public myCourseAdapter(Context mContext, ArrayList<myCourseModel> model, ArrayList<String> date, ArrayList<String> course) {
        this.mContext = mContext;
        this.model = model;
        this.date = date;
        this.course = course;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_mycourse, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
        //    final certModel model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;


            //    System.out.println(model.getTitle()+" "+model.getPic()+" "+model.getPrice());

        }

    }

    @Override
    public int getItemCount() {

        return  model.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private myCourseModel getItem(int position) {
        return model.get(position);
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position, myCourseModel model);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(final View itemView) {
            super(itemView);}
    }


    }
