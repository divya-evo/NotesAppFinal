package com.example.notesapp4;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {
    EditText idInput, titleInput, contentInput;
    Button updateNoteButton, deleteNoteButton, shareNoteButton;
    String title, content, id;
    ArrayList<NoteItem> myValues = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //idInput = findViewById(R.id.id_input2);
        titleInput = findViewById(R.id.title_input2);
        contentInput = findViewById(R.id.content_input2);
        updateNoteButton = findViewById(R.id.updateNote_button);
        shareNoteButton = findViewById(R.id.shareNote_button);
        deleteNoteButton = findViewById(R.id.deleteNote_button);
        // first this
        getAndSetIntentData();

        //set action bar
        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle(title);
        }

        updateNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleText = titleInput.getText().toString().trim();
                String contentText = contentInput.getText().toString().trim();

                Intent intent = new Intent();
                intent.putExtra("title new", titleText);
                intent.putExtra("content new", contentText);

                Log.i("check value title", title);
                Log.i("check value title new", titleText);
                setResult(RESULT_OK, intent);
                finish();

//                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
//                // and then this
//                NoteItem updateNote = new NoteItem(titleText, contentText, false);
//                myDB.updateData(updateNote);

//                myDB.updateData(id, title, content);
            }
        });
        shareNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleText = titleInput.getText().toString().trim();
                String contentText = contentInput.getText().toString().trim();
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                // and then this
                NoteItem shareNote = new NoteItem(titleText, contentText, false);
                Intent intent = new Intent(Intent.ACTION_SEND);
                Log.i("sharing", shareNote.getTitle());
                intent.putExtra(Intent.EXTRA_TEXT, "I would like to share my Note named: " + shareNote.getTitle() + "\n Here is the relevant information "
                        + shareNote.getContent());
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Share Note"));
            }
        });
        deleteNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               confirmDialog();

            }
        });

    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("content") ){


            //getting data
            id =  getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            content = getIntent().getStringExtra("content");

            //setting data - works

            titleInput.setText(title);
            contentInput.setText(content);


        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete "+ title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}