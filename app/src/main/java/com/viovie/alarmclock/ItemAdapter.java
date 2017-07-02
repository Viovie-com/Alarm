package com.viovie.alarmclock;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<AlarmItem> mList;
    private List<View> mItemList;
    private List<View> mDeleteList;

    public ItemAdapter(Context context, @NonNull List<AlarmItem> list) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mList = list;
        mItemList = new ArrayList<>();
        mDeleteList = new ArrayList<>();
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int)v.getTag();
                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra(EditActivity.PARAM_ITEM, mList.get(pos));
                mContext.startActivity(intent);
            }
        });
        mItemList.add(holder.itemView);

        AlarmItem item = mList.get(position);
        holder.dateText.setText(item.getDate());
        holder.timeText.setText(item.getTime());
        holder.titleText.setText(item.title);

        holder.deleteImage.setTag(position);
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                mList.remove(pos);
                notifyItemRemoved(pos);

                for (int i = pos + 1 ; i < mDeleteList.size() ; i++) {
                    mDeleteList.get(i).setTag(((int)mDeleteList.get(i).getTag())-1);
                    mItemList.get(i).setTag(((int)mItemList.get(i).getTag())-1);
                }
                mDeleteList.remove(pos);
                mItemList.remove(pos);
            }
        });
        mDeleteList.add(holder.deleteImage);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateText;
        TextView timeText;
        TextView titleText;
        ImageView deleteImage;

        ViewHolder(View itemView) {
            super(itemView);

            dateText = (TextView) itemView.findViewById(R.id.text_date);
            timeText = (TextView) itemView.findViewById(R.id.text_time);
            titleText = (TextView) itemView.findViewById(R.id.text_title);
            deleteImage = (ImageView) itemView.findViewById(R.id.image_delete);
        }
    }
}
