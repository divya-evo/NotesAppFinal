package com.example.notesapp4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import static com.example.notesapp4.R.style.AppThemeBlue;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<RecyclerEntity> list;
    RecyclerAdapter adapter;

    RecyclerAdapter.MyViewHolder holder;
    LinearLayout container;
    FloatingActionButton addButton, transformButton;

    MyDatabaseHelper myDB;
    private SQLiteDatabase database;

    ArrayList<NoteItem> myValues = new ArrayList<>();
    private boolean showMenu = false;

    // night mode
    private Switch modeSwitch;
    public static final String MyPreferences = "nightModePref";
    public static final String Key_IsNightMode = "isNightMode";
    SharedPreferences sharedPreferences;

    // collapsible menu
    LinearLayout mLinearLayout, nLinearLayout;
    LinearLayout mLinearLayoutHeader;
    FloatingActionButton collapseButton, colourButton, redButton, blueButton;

    // change layout
    boolean isList = true;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setTheme();
       // Utils.onActivityCreateSetTheme(this);
       // initTheme();
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.recyclerview);
        container = findViewById(R.id.container);
        addButton = findViewById(R.id.add_button);
        transformButton = findViewById(R.id.transform_button);
        colourButton = findViewById(R.id.colour_button);



        // night mode
        modeSwitch = findViewById(R.id.switch_mode);
        sharedPreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);




        /**************************************** DISPLAY DATA ****************************************/
        // initialise arrays to display data
        myDB = new MyDatabaseHelper(MainActivity.this);
        database = myDB.getWritableDatabase();
        myValues = myDB.getAllNotes();
//        noteId = new ArrayList<>();
//        noteTitle = new ArrayList<>();
//        noteContent = new ArrayList<>();
        //storeInArray();
//        Log.i("print array", String.valueOf(myValues));
        // adapter = new RecyclerAdapter(MainActivity.this, this, noteId, noteTitle, noteContent);
        adapter = new RecyclerAdapter(MainActivity.this, this, myValues);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        //initTheme();

        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);

        /**************************************** BUTTON CLICK LISTENERS ****************************************/
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, 2);


            }
        });

        transformButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isList){
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    isList = true;

                } else {
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    isList = false;
                }
            }
        });

//        colourButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("on clicked", "colour");
//                Intent intent = new Intent();
//                startActivityForResult(intent, 4);
//            }
//        });



//        holder.container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int position = holder.getAdapterPosition();
//                Log.i("check value",  myValues.get(position).getTitle());
//
////                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
////                int position = holder.getAdapterPosition();
////                NoteItem noteItem = new NoteItem();
////                Log.i("check value",  myValues.get(position).getTitle());
////                intent.putExtra("id", myValues.get(position).getId());
////                intent.putExtra("title", myValues.get(position).getTitle());
////                intent.putExtra("content", myValues.get(position).getContent());
////                startActivityForResult(intent, 3);
//            }
//        });

//        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
//
//                Log.i("check value",  myValues.get(position).getTitle());
//                intent.putExtra("id", myValues.get(position).getId());
//                intent.putExtra("title", myValues.get(position).getTitle());
//                intent.putExtra("content", myValues.get(position).getContent());
//                startActivityForResult(intent, 1);
//            }
//        });



        /**************************************** SWITCH MODE ****************************************/
        checkNightModeActivated();
        modeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                saveNightModeState(true);
                recreate();

            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                saveNightModeState(false);
                recreate();
            }
        });


        /**************************************** SWIPE MENU ****************************************/

        //... rest of the list items
//
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
//                adapter.showMenu(viewHolder.getAdapterPosition());
//                // adapter.deleteItem(viewHolder.getAdapterPosition());
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
//        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener()
//
//        {
//            @Override
//            public void onScrollChange (View v,int scrollX, int scrollY, int oldScrollX, int oldScrollY)
//            {
//                adapter.closeMenu();
//            }
//        });
//
        /**************************************** COLLAPSIBLE MENU ****************************************/
        mLinearLayout = (LinearLayout) findViewById(R.id.expandable);

        //set visibility to GONE
        mLinearLayout.setVisibility(View.INVISIBLE);


        mLinearLayoutHeader = (LinearLayout) findViewById(R.id.header);

        collapseButton = findViewById(R.id.collapseButton);
        colourButton = findViewById(R.id.colour_button);
        collapseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!mLinearLayout.isShown()){
                    expand();
                }else{
                    collapse();
                }
            }
        });

    }




//    @Override
//    public void onBackPressed() {
//        if (adapter.isMenuShown()) {
//            adapter.closeMenu();
//        } else {
//            super.onBackPressed();
//        }
//    }


    /**************************************** DATABASE ****************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // updating
        if (requestCode == 1) {
            recreate();
        }
        // creating
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String titleText = data.getStringExtra("Title Text");
                String contentText = data.getStringExtra("Content Text");

                NoteItem newNote = new NoteItem(titleText, contentText, false);
                myDB.addNote(newNote);
                myValues.add(newNote);
                adapter.notifyItemInserted(myValues.indexOf(newNote));
                newNote.setId(myDB.getNewestId());
                Log.i("new note id", newNote.getId());
            }
        }
        //updating
        if(requestCode == 3){
            if (resultCode == RESULT_OK) {
                String state = data.getStringExtra("state");
                if (state.equals("UPDATING")) {
                    String titleText = data.getStringExtra("title new");
                    String contentText = data.getStringExtra("content new");
                    String id = data.getStringExtra("id");
                    Log.i("ID passed back", id);
                    NoteItem updateNote = null;

                    // check each note for matching ID then break and reassign update note if matches
                    for (NoteItem noteItem : myValues) {
                        if (noteItem.getId().equals(id)) {
                            Log.i("Note updated", "True");
                            updateNote = noteItem;
                        }
                    }

                    updateNote.setTitle(titleText);
                    updateNote.setContent(contentText);
                    myDB.updateData(updateNote);
                    adapter.notifyItemChanged(myValues.indexOf(updateNote));
                }
                if (state.equals("DELETING")) {
                    String id = data.getStringExtra("id");
                    Log.i("ID passed back", id);
                    NoteItem updateNote = null;

                    // check each note for matching ID then break and reassign update note if matches
                    for (NoteItem noteItem : myValues) {
                        Log.i("note Id", noteItem.getId());
                        if (noteItem.getId().equals(id)) {
                            Log.i("Note deleted", "True");
                            updateNote = noteItem;
                        }
                    }

                    int notePos = myValues.indexOf(updateNote);
                    myDB.deleteOneRow(updateNote.getId());
                    myValues.remove(notePos);
                    adapter.notifyItemRemoved(notePos);
                }

            }
        }
    }

    void storeInArray() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data to display", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
//                noteId.add(cursor.getString(0));
//                noteTitle.add(cursor.getString(1));
//                noteContent.add(cursor.getString(2));
                //  noteContent.add(cursor.getString(2));
                String str1 = cursor.getString(1);
                String str2 = cursor.getString(2);

                NoteItem d = new NoteItem(str1, str2, false);
                myValues.add(d);
                //d.setId(myDB.getNewestId());
                // set new id


            }
        }
    }




    /**************************************** SWITCH MODE ****************************************/
    private void saveNightModeState(boolean nightMode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Key_IsNightMode, nightMode);
        editor.apply();
    }

    public void checkNightModeActivated() {
        if (sharedPreferences.getBoolean(Key_IsNightMode, false)) {
            modeSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            modeSwitch.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
    /**************************************** COLLAPSIBLE MENU ****************************************/
    private void expand() {
        //set Visible
        mLinearLayout.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mLinearLayout.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight());
        mAnimator.start();
    }

    private void collapse() {
        int finalHeight = mLinearLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0);
        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                mLinearLayout.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        mAnimator.start();
    }

    private ValueAnimator slideAnimator(int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mLinearLayout.getLayoutParams();
                layoutParams.height = value;
                mLinearLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }



    /**************************************** ITEM TOUCH HELPER ****************************************/
    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Log.i("swiped", "yay");

//            Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
//            String state = "DELETING";
//            intent.putExtra("state", state);
//            startActivityForResult(intent, 3);
//            adapter.notifyDataSetChanged();

        }

    };



}