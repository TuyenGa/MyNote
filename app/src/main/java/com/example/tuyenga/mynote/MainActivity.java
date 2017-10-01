package com.example.tuyenga.mynote;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button btnAdd;
    private Button btnDelete;
    private EditText edtNote;
    private TextView lblNoNote;
    private ListView lstNote;

    private NoteDataSource dataSource;
    private ArrayList<NoteModel> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //find view by id
        btnAdd = (Button) this.findViewById(R.id.btn_add);
        edtNote = (EditText) this.findViewById(R.id.edt_note);
        lstNote = (ListView) this.findViewById(R.id.lst_note);
        lblNoNote = (TextView) this.findViewById(R.id.lbl_noNote);


        btnAdd.setOnClickListener(this);

        // list view

        lstNote.setOnItemClickListener(this);

        //create data source
        dataSource = new NoteDataSource(this);
        dataSource.open();

        //get all notes form sqlite and them on listview
        updateListView();
    }

    private void updateListView()
    {
        arr = dataSource.getAllNote();
        if (arr != null && arr.size()>0)
        {
            lblNoNote.setVisibility(View.INVISIBLE);
            lstNote.setVisibility(View.VISIBLE);

            // view all note to listview
            NoteAdapter adapter = new NoteAdapter(this, (ArrayList<NoteModel>) arr);
            lstNote.setAdapter(adapter);
        }
        else {
            lblNoNote.setVisibility(View.VISIBLE);
            lstNote.setVisibility(View.INVISIBLE);
        }
    }

    protected void onDestroy()
    {
        super.onDestroy();
        dataSource.close();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_add)
        {
            String note = edtNote.getText().toString();
            if (note.trim().length() > 0)
            {
                dataSource.addNewNote(note);

                updateListView();

                edtNote.setText("");

            } else {

                Toast.makeText(this,"Please input your note",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void deleteNote(NoteModel note)
    {
        dataSource.deleteNote(note);

        updateListView();

        Toast.makeText(this,"Delete note success",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(this,Edit_activity.class);
        NoteModel note = arr.get(position);
        Bundle data = new Bundle();
        data.putInt("id", note.getId());
        data.putString("note", note.getNote());
        data.putString("datetime", note.getDatetime());
        intent.putExtra("DataEdit", data);
        startActivity(intent);
    }
}
