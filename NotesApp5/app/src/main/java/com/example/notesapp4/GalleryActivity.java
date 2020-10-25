package com.example.notesapp4;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "GalleryActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getIncomingIntent();
        Log.d(TAG, "onCreate:started");
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("list_item")){
            String imageTitle = getIntent().getStringExtra("title");
            setImage(imageTitle);
        }
    }
    private void setImage(String imageTitle){
        TextView title = findViewById(R.id.image_title);
        title.setText(imageTitle);

        //ImageView image = findViewById(R.id.image_view);

    }
}
