package com.example.notesapp4;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<RecyclerEntity> list;
    RecyclerAdapter adapter;
    FloatingActionButton addButton;
    MyDatabaseHelper myDB;
    ArrayList<String> noteId, noteTitle, noteContent;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        addButton = findViewById(R.id.add_button);
        /**************************************** ADD BUTTON ****************************************/
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });

        /**************************************** DISPLAY DATA ****************************************/
        // initialise arrays to display data
        myDB = new MyDatabaseHelper(MainActivity.this);
        noteId = new ArrayList<>();
        noteTitle = new ArrayList<>();
        noteContent = new ArrayList<>();
        storeInArray();
        Log.i("print array", String.valueOf(noteTitle));
        adapter = new RecyclerAdapter(MainActivity.this, this, noteId, noteTitle, noteContent);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        /**************************************** LIST ITEMS ****************************************/
//        list = new ArrayList<>();
//
//        list.add(new RecyclerEntity("This is the best title", "some random text", false));
//        list.add(new RecyclerEntity("This is the second-best title", "some random text", false));
//        list.add(new RecyclerEntity("This is the best title", "some random text", false));
//        list.add(new RecyclerEntity("This is the second-best title", "some random text", false));
//        list.add(new RecyclerEntity("This is the best title", "some random text", false));
//        list.add(new RecyclerEntity("This is the second-best title", "some random text", false));
//        list.add(new RecyclerEntity("This is the best title", "some random text", false));
//        list.add(new RecyclerEntity("This is the second-best title", "some random text", false));
    }

    /**************************************** DATABASE ****************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }
    }
    void storeInArray() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data to display", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                noteId.add(cursor.getString(0));
                noteTitle.add(cursor.getString(1));
                noteContent.add(cursor.getString(2));
            }
        }
    }

        //... rest of the list items

//        adapter = new RecyclerAdapter(this, list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);

//        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            private final ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.purple_200));
//
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
////                adapter.showMenu(viewHolder.getAdapterPosition());
//               // adapter.deleteItem(viewHolder.getAdapterPosition());
//            }
//
//            @Override
//            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//
//                View itemView = viewHolder.itemView;
//
//                if (dX > 0) {
//                    background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
//                } else if (dX < 0) {
//                    background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
//                } else {
//                    background.setBounds(0, 0, 0, 0);
//                }
//
//                background.draw(c);
//            }
//        };
//
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
//        itemTouchHelper.attachToRecyclerView(recyclerView);
//
//        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                adapter.closeMenu();
//            }
//        });
//
//    }
//    @Override
//    public void onBackPressed() {
//        if (adapter.isMenuShown()) {
//            adapter.closeMenu();
//        } else {
//            super.onBackPressed();
//        }
//    }





}