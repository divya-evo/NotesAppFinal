package com.example.notesapp4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText idInput, titleInput, contentInput;
    Button addNoteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

      //  idInput = findViewById(R.id.id_input);
        titleInput = findViewById(R.id.title_input);
        contentInput = findViewById(R.id.content_input);
        addNoteButton = findViewById(R.id.addNote_button);


        //idInput.getText().toString().trim(),
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleText = titleInput.getText().toString();
                String contentText = contentInput.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("Title Text", titleText);
                intent.putExtra("Content Text", contentText);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}