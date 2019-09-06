package com.example.doitappfin.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.doitappfin.R;
import com.example.doitappfin.utils.MyRecyclerViewAdapter;
import com.example.doitappfin.utils.RadioListAdapter;

import java.util.ArrayList;

public class RadioActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> modelList = new ArrayList<>();
    private RadioListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_viw);
        modelList.add("a1");
        modelList.add("a2");
        modelList.add("a3");
        modelList.add("a4");
        modelList.add("a5");
   //     setAdapter();
    }

 /*   private void setAdapter() {
        mAdapter = new RadioListAdapter(RadioActivity.this, modelList);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(RadioActivity.this, R.drawable.divider_recyclerview));
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new RadioListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String model) {

                //handle item click events here
                Toast.makeText(RadioActivity.this, "Hey " + model, Toast.LENGTH_SHORT).show();


            }
        });



    }

*/

}
