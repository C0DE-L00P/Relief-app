package com.stem.relief;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomArrayAdapter2 extends ArrayAdapter {

    private View listItemView;

    public CustomArrayAdapter2(Activity context, List<String> details) {
        super(context, 0, details);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Make Done Goals list one by one
        listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.goal_item_done, parent, false);
        }

        String currentItem = String.valueOf(getItem(position));

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.done_text_view);
        nameTextView.setText(currentItem);

        return listItemView;
    }
}
