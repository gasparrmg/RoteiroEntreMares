package com.lasige.roteiroentremares.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

class PuzzleAdapter extends BaseAdapter {

    private ArrayList<Button> buttons;
    private int columnWidth;
    private int columnHeight;

    public PuzzleAdapter(ArrayList<Button> buttons, int columnWidth, int columnHeight) {
        this.buttons = buttons;
        this.columnWidth = columnWidth;
        this.columnHeight = columnHeight;
    }

    @Override
    public int getCount() {
        return buttons.size();
    }

    @Override
    public Button getItem(int position) {
        return buttons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null) {
            button = buttons.get(position);
        } else {
            button = (Button) convertView;
        }

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(columnWidth, columnHeight);

        button.setLayoutParams(params);

        return button;
    }
}
