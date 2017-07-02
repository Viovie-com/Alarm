package com.viovie.alarmclock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AlarmItem> mList;
    private RecyclerView mRecyclerView;
    private ItemAdapter mItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialData();
        initialRecyclerView();
    }

    private void initialData() {
        mList = new ArrayList<>();
        for (int i = 0 ; i < 3; i++) {
            AlarmItem ai = new AlarmItem();
            ai.title = "Test title:" + i ;
            mList.add(ai);
        }
    }

    private void initialRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mItemAdapter = new ItemAdapter(this, mList);
        mRecyclerView.setAdapter(mItemAdapter);

        DividerItemDecoration divider = new DividerItemDecoration(this, layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }
}
