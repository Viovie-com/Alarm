package com.viovie.alarmclock;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_ADD = 101;
    public static final int REQUEST_UPDATE = 102;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        for (int i = 0, n = menu.size() ; i < n ; i++) {
            Drawable icon = menu.getItem(i).getIcon();
            icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        }
        return true;
    }

    private void initialData() {
        mList = DataStorage.read(this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add: {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                MainActivity.this.startActivityForResult(intent, REQUEST_ADD);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK || data.getExtras() == null) return;
        switch (requestCode) {
            case REQUEST_ADD: {
                Bundle bundle = data.getExtras();
                AlarmItem item = (AlarmItem) bundle.get(EditActivity.PARAM_ITEM);
                mList.add(item);
                mItemAdapter.notifyItemInserted(mList.size()-1);
                break;
            }
            case REQUEST_UPDATE: {
                Bundle bundle = data.getExtras();
                AlarmItem item = (AlarmItem) bundle.get(EditActivity.PARAM_ITEM);
                int position = bundle.getInt(EditActivity.PARAM_ITEM_POSITION);
                mList.set(position, item);
                mItemAdapter.notifyItemChanged(position);
            }
        }
    }
}
