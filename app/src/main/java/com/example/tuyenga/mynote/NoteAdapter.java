package com.example.tuyenga.mynote;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tuyenga on 27/09/2017.
 */

public class NoteAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private List<NoteModel> arr;

    public NoteAdapter(Context context, List<NoteModel> arr) {
        this.context = context;
        this.arr = arr;
    }



    @Override
    public int getCount() {
        return this.arr.size();
    }

    @Override
    public Object getItem(int position) {
        return this.arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View rowView = view;
        if (rowView == null)
        {
            LayoutInflater inflater = ((Activity) this.context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.item_activity,parent,false);
        }
        TextView lblNote = (TextView) rowView.findViewById(R.id.lbl_note);
        TextView lbldatetime = (TextView) rowView.findViewById(R.id.lbl_datetime);
        Button btnDelete = (Button) rowView.findViewById(R.id.btn_delete);


        btnDelete.setTag(arr.get(position));
        btnDelete.setOnClickListener(this);

        lblNote.setText(arr.get(position).getNote());
        lbldatetime.setText(arr.get(position).getDatetime());



        return rowView;
    }

    @Override
    public void onClick(View view) {
        ((MainActivity) this.context).deleteNote((NoteModel) view.getTag());
    }
}
