/*
 * Copyright (C) 2011-2021, YouTransactor. All Rights Reserved.
 *
 * Use of this product is contingent on the existence of an executed license
 * agreement between YouTransactor or one of its sublicensee, and your
 * organization, which specifies this software's terms of use. This software
 * is here defined as YouTransactor Intellectual Property for the purposes
 * of determining terms of use as defined within the license agreement.
 */
package com.youtransactor.sampleapp.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.youtransactor.sampleapp.R;

import java.util.List;

public class LogsAdapter extends RecyclerView.Adapter<LogsAdapter.ViewHolder> {

    private List<String> logs;

    public LogsAdapter(List<String> logs) {
        this.logs = logs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View logView = inflater.inflate(R.layout.custome_row_layout, parent, false);

        return new ViewHolder(logView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String log = logs.get(position);

        TextView textView = holder.logTextView;
        textView.setText(log);
    }

    @Override
    public int getItemCount() {
        return logs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView logTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            logTextView = itemView.findViewById(R.id.log_message);
        }
    }

    public void clear() {
        int size = logs.size();
        logs.clear();
        notifyItemRangeRemoved(0, size);
    }
}
