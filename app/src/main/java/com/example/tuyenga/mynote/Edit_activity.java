package com.example.tuyenga.mynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Edit_activity extends AppCompatActivity implements View.OnClickListener {
    EditText editNote ;
    private NoteDataSource dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("DataEdit");
        int id = data.getInt("id");
        String note = data.getString("note");
        String datetime = data.getString("datetime");
        editNote = (EditText) findViewById(R.id.editNote);
        Button buttonNote = (Button) findViewById(R.id.btn_Edit);

        editNote.setText(note);

        buttonNote.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String note = editNote.getText().toString();

        dataSource.editNote(note,(NoteModel) view.getTag());


    }
}
