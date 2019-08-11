package com.example.doitappfin.ui;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import java.util.ArrayList;
import java.util.List;


import com.example.doitappfin.R;
import com.example.doitappfin.utils.MyCustomPagerAdapter;
import com.example.doitappfin.utils.RecycleViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.Toast;
import android.os.Handler;

import android.view.Menu;

import android.app.SearchManager;
import android.widget.EditText;
import android.graphics.Color;
import android.text.InputFilter;
import android.text.Spanned;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class TrainCertActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private int imagestc[] = {R.drawable.apple, R.drawable.blue, R.drawable.mango, R.drawable.orange,R.drawable.apple, R.drawable.blue, R.drawable.mango, R.drawable.orange,R.drawable.apple, R.drawable.blue, R.drawable.mango, R.drawable.orange};
  private  String[]  fruitstc={"apple","grapes","mango","orange","apple","grapes","mango","orange","apple","grapes","mango","orange"};
    // @BindView(R.id.recycler_view)
    // RecyclerView recyclerView;

    //@BindView(R.id.toolbar)


    private Intent i;
    //Toolbar toolbar;
    private Toolbar toolbar;

    // @BindView(R.id.swipe_refresh_recycler_list)
    // SwipeRefreshLayout swipeRefreshRecyclerList;

    private SwipeRefreshLayout swipeRefreshRecyclerList;
    private RecyclerViewAdapterTrainCert mAdapter;

private ArrayList<RecycleViewModel> recycleViewModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_cert);
        i=getIntent();

        // ButterKnife.bind(this);
        findViews();
        initToolbar(i.getStringExtra("val"));
        recycleViewModelList=new ArrayList<RecycleViewModel>();



        if(i.getStringExtra("val").equals("Certification"))
        {
            DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("MainData").child("CertificationData");
            recycleViewModelList.clear();
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot d1:dataSnapshot.getChildren())
                    {
                        RecycleViewModel obj = d1.getValue(RecycleViewModel.class);
                        recycleViewModelList.add(obj);
                        System.out.println("in act  "+obj.getImage());


                    }
                    mAdapter = new RecyclerViewAdapterTrainCert(TrainCertActivity.this, recycleViewModelList);
                 //   System.out.println("in act  "+recycleViewModelList.get(1).getPic());

                    setAdapter();

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
        else {

        }
        setAdapter();




        swipeRefreshRecyclerList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Do your stuff on refresh
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (swipeRefreshRecyclerList.isRefreshing())
                            swipeRefreshRecyclerList.setRefreshing(false);
                    }
                }, 5000);

            }
        });

    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshRecyclerList = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_recycler_list);
    }

    public void initToolbar(String title) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_search, menu);


        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat
                .getActionView(menu.findItem(R.id.action_search));

        SearchManager searchManager = (SearchManager) this.getSystemService(this.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));

        //changing edittext color
        EditText searchEdit = ((EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text));
        searchEdit.setTextColor(Color.WHITE);
        searchEdit.setHintTextColor(Color.WHITE);
        searchEdit.setBackgroundColor(Color.TRANSPARENT);
        searchEdit.setHint("Search");

        InputFilter[] fArray = new InputFilter[2];
        fArray[0] = new InputFilter.LengthFilter(40);
        fArray[1] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                for (int i = start; i < end; i++) {

                    if (!Character.isLetterOrDigit(source.charAt(i)))
                        return "";
                }


                return null;


            }
        };
        searchEdit.setFilters(fArray);
        View v = searchView.findViewById(androidx.appcompat.R.id.search_plate);
        v.setBackgroundColor(Color.TRANSPARENT);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<RecycleViewModel> filterList = new ArrayList<RecycleViewModel>();
                if (s.length() > 0) {
                    for (int i = 0; i < recycleViewModelList.size(); i++) {
                        if (recycleViewModelList.get(i).getTitle().toLowerCase().contains(s.toString().toLowerCase())) {
                            filterList.add(recycleViewModelList.get(i));
                            mAdapter.updateList(filterList);
                        }
                    }

                } else {

                    mAdapter.updateList(recycleViewModelList);
                }
                return false;
            }
        });


        return true;
    }


    private void setAdapter() {


        for (int i =0;i<imagestc.length;i++)
        {
           // recycleViewModelList.add(new AbstractModel(fruitstc[i], "Hello " + fruitstc[i],imagestc[i]));

        }



        mAdapter = new RecyclerViewAdapterTrainCert(TrainCertActivity.this, recycleViewModelList);



        final GridLayoutManager layoutManager = new GridLayoutManager(TrainCertActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);


        recyclerView.setAdapter(mAdapter);


        mAdapter.SetOnItemClickListener(new RecyclerViewAdapterTrainCert.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, RecycleViewModel model) {

                //handle item click events here
                    startActivity(new Intent(TrainCertActivity.this, MapsActivity.class));

               // Toast.makeText(TrainCertActivity.this, "Hey " + model.getTitle(), Toast.LENGTH_SHORT).show();


            }
        });


    }


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
