package com.am1ne.mvvm;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.am1ne.mvvm.adapters.RecyclerAdapter;
import com.am1ne.mvvm.models.NicePlace;
import com.am1ne.mvvm.viewmodels.MainActivityViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;

    RecyclerAdapter adapter;
    MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        init();
    }

    private void init() {

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mainActivityViewModel.init();
        mainActivityViewModel.getNicePlaces().observe(this, nicePlaces -> adapter.notifyDataSetChanged());
        mainActivityViewModel.getIsUpdating().observe(this, aBoolean -> {
            if(aBoolean){
                progress_bar.setVisibility(View.VISIBLE);
            }else {
                progress_bar.setVisibility(View.GONE);
                recycler_view.smoothScrollToPosition(mainActivityViewModel.getNicePlaces().getValue().size()-1);

            }
        });

        initRecycleView();
    }

    private void initRecycleView() {
        adapter = new RecyclerAdapter(this, mainActivityViewModel.getNicePlaces().getValue());
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setAdapter(adapter);
    }

    @OnClick(R.id.fab)
    public void fab() {
        mainActivityViewModel.addNewNicePlace(new NicePlace("https://i.redd.it/j6myfqglup501.jpg",
                "New One"));
    }

}
