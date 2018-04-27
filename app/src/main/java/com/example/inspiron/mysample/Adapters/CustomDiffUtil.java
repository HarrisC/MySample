package com.example.inspiron.mysample.Adapters;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.ArrayList;

/**
 * Created by Harris on 26/2/2018
 * Project Name MySample.
 */

public class CustomDiffUtil extends DiffUtil.Callback {
    private ArrayList<Object> originalList;
    private ArrayList<Object> newList;

    public CustomDiffUtil(ArrayList<Object> originalList, ArrayList<Object> newList) {
        this.originalList = originalList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return originalList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        // TODO insert compare logic
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        // TODO insert compare logic
        return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // TODO insert compare logic
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
