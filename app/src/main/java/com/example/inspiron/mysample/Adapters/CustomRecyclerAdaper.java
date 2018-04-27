package com.example.inspiron.mysample.Adapters;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.inspiron.mysample.MultiThread.MultiThreadSupplier;
import com.example.inspiron.mysample.R;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Created by Harris on 26/2/2018
 * Project Name MySample.
 */

public class CustomRecyclerAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface ItemClickCallback {
        void OnItemClick(View v, int position);
        void OnItemLongClick(View v, int position);
    }

    private Context context;
    private ArrayList<Object> dataList;
    private ItemClickCallback itemClickCallback;
    private AdapterUpdateCallback dataUpdateCallback;
    private Deque<ArrayList<Object>> pendingUpdates;

    public CustomRecyclerAdaper(Context context, ArrayList<Object> dataList) {
        this.context = context;
        this.dataList = new ArrayList<>();
        this.dataList.addAll(dataList);
        this.dataUpdateCallback = new AdapterUpdateCallback(new ArrayList<>());
        pendingUpdates = new ArrayDeque<>();
    }

    private void update(ArrayList<Object> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
    }

    private static class CustomRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        private CustomRecyclerViewHolder(View itemView, TextView title) {
            super(itemView);
            this.title = title;
        }

        public static CustomRecyclerViewHolder newInstance(View parent) {
            TextView title = parent.findViewById(R.id.title);
            return new CustomRecyclerViewHolder(parent, title);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyclerview_item, parent, false);
        final CustomRecyclerViewHolder viewHolder = CustomRecyclerViewHolder.newInstance(parent);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickCallback != null)
                    itemClickCallback.OnItemClick(view, viewHolder.getAdapterPosition());
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (itemClickCallback != null)
                    itemClickCallback.OnItemLongClick(view, viewHolder.getAdapterPosition());;
                view.performClick();
                return true;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CustomRecyclerViewHolder viewHolder = (CustomRecyclerViewHolder) holder;
        Object object = dataList.get(position);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setItemClickCallback(ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    private void applyDiffResult(ArrayList<Object> changes, DiffUtil.DiffResult diffResult) {
        pendingUpdates.remove(changes);
        dispatchFinalUpdate(changes, diffResult);
        if (pendingUpdates.size() > 0) {
            ArrayList<Object> latestUpdate = pendingUpdates.pop();
            pendingUpdates.clear();
            updateItemsInternal(latestUpdate);
        }
    }

    public void dispatchChanges(ArrayList<Object> changes) {
        pendingUpdates.push(changes);
        if (pendingUpdates.size() > 1) return;
        updateItemsInternal(changes);
    }

    private void updateItemsInternal(final ArrayList<Object> changes) {
        final ArrayList<Object> original = new ArrayList<>(this.dataList);
        MultiThreadSupplier.getsInstance().forBackgroundTasks().execute(new Runnable() {
            @Override
            public void run() {
                final DiffUtil.DiffResult diffResult =
                        DiffUtil.calculateDiff(new CustomDiffUtil(original, changes));
                MultiThreadSupplier.getsInstance().forMainThreadTasks().execute(new Runnable() {
                    @Override
                    public void run() {
                        applyDiffResult(changes, diffResult);
                    }
                });
            }
        });
    }

    private void dispatchFinalUpdate(ArrayList<Object> changes, DiffUtil.DiffResult diffResult) {
        dataUpdateCallback.updateChanges(changes);
        diffResult.dispatchUpdatesTo(dataUpdateCallback);
    }

    private class AdapterUpdateCallback implements ListUpdateCallback {

        private ArrayList<Object> changes;

        private AdapterUpdateCallback(ArrayList<Object> changes) {
            this.changes = changes;
        }

        private void updateChanges(ArrayList<Object> changes) {
            this.changes.clear();
            this.changes.addAll(changes);
        }

        @Override
        public void onInserted(int position, int count) {
            update(changes);
            notifyItemRangeChanged(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            update(changes);
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            update(changes);
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count, Object payload) {
            update(changes);
            notifyItemRangeChanged(position, count, payload);
        }
    }
}
