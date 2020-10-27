package com.example.notesapp4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//
//public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    List<RecyclerEntity> list;
    private final ArrayList<NoteItem> myValues;
    NoteItem noteItem;
    Context context;
    Activity activity;
    MyDatabaseHelper db;

    // change layout
    private static final int LIST_ITEM = 0;
    private static final int GRID_ITEM = 1;
    boolean isSwitchView = true;

    //animation
    Animation translate_anim;




//    public RecyclerAdapter(Context context, List<RecyclerEntity> articlesList) {
//        this.list = articlesList;
//        this.context = context;
//    }

    //    public RecyclerAdapter(Activity activity, Context context, ArrayList noteIdArray, ArrayList noteTitleArray, ArrayList noteContentArray) {
    public RecyclerAdapter(Activity activity, Context context, ArrayList<NoteItem> myValues) {
        this.activity = activity;
        this.context = context;
        this.myValues = myValues;
        db = new MyDatabaseHelper(context);
//        this.noteIdArray = noteIdArray;
//        this.noteTitleArray = noteTitleArray;
//        this.noteContentArray = noteContentArray;

    }


//    @Override
//    public int getItemViewType(int position) {
//        if(myValues.get(position).isShowMenu()){
//            return SHOW_MENU;
//        }else{
//            return HIDE_MENU;
//        }
//    }
    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;

    }




    //    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.noteTitleText.setText(myValues.get(position).getTitle());
        holder.noteContentText.setText(myValues.get(position).getContent());
        String id = myValues.get(position).getId();
        holder.itemView.setTag(id);
        holder.position = position;

        //*****************UPDATE*********************


//        holder.container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(context, UpdateActivity.class);
////                intent.putExtra("id", String.valueOf(noteIdArray.get(position)));
////                intent.putExtra("title", String.valueOf(noteTitleArray.get(position)));
////                intent.putExtra("content", String.valueOf(noteContentArray.get(position)));
//
//                intent.putExtra("id", String.valueOf(myValues.get(position).getId()));
//                intent.putExtra("title", String.valueOf(myValues.get(position).getTitle()));
//                intent.putExtra("content", String.valueOf(myValues.get(position).getContent()));
//               activity.startActivityForResult(intent, 1);
//            }
//        });

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, UpdateActivity.class);
                // int position = holder.getAdapterPosition();
                NoteItem noteItem = new NoteItem();
                Log.i("check value",  myValues.get(position).getTitle());
                intent.putExtra("id", myValues.get(position).getId());
                intent.putExtra("title", myValues.get(position).getTitle());
                intent.putExtra("content", myValues.get(position).getContent());
                intent.putExtra("note position", position);

                if (myValues.get(position).getId() != null){
                    Log.i("Id sent", myValues.get(position).getId());
                }
                else {
                    Log.i("Id sent", "null");
                }

                Log.i("note positon sent", String.valueOf(position));

                activity.startActivityForResult(intent, 3);
            }
        });

        //RecyclerEntity entity = list.get(position);
        //  myValues.get(position);
        //  if(holder != null){
        // ((MyViewHolder)holder).noteTitle.setText(entity.getTitle());
//            ((MyViewHolder)holder).noteContent.setText(entity.getTitle());
//            //((MyViewHolder)holder).imageView.setImageDrawable(context.getResources().getDrawable(entity.getImage()));

        holder.container.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                Log.i("clickled", "click");
                //showMenu(position);
                return true;

            }
        });
//
//            ((MyViewHolder)holder).container.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, myValues.get(position).getTitle() , Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "onClick:clicked");
//                    Intent intent = new Intent(context, GalleryActivity.class);
//                    intent.putExtra("list_item", (Serializable) myValues.get(position).getTitle());
//                    context.startActivity(intent);
//                }
//            });




        if(holder instanceof MyViewHolder){
            //Menu Actions
            //((MenuViewHolder)holder).edit.setOnClickListener(null);

        }

    }



    @Override
    public int getItemCount() {
        //return list.size();
        // return noteTitleArray.size();
        return myValues.size();
    }


//    public void showMenu(int position) {
//        for(int i=0; i<myValues.size(); i++){
//            myValues.get(i).setShowMenu(false);
//        }
//        myValues.get(position).setShowMenu(true);
//        notifyDataSetChanged();
//    }
//
//
//    public boolean isMenuShown() {
//        for(int i=0; i<myValues.size(); i++){
//            if(myValues.get(i).isShowMenu()){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void closeMenu() {
//        for(int i=0; i<myValues.size(); i++){
//            myValues.get(i).setShowMenu(false);
//        }
//        notifyDataSetChanged();
//    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  noteTitleText, noteContentText;
        public int position = 0;


        LinearLayout container;
        LinearLayout updateLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            noteTitleText = itemView.findViewById(R.id.note_title_text);
            noteContentText = itemView.findViewById(R.id.note_content_text);

            container = itemView.findViewById(R.id.container);
            updateLayout = itemView.findViewById(R.id.updateLayout);

            //animation
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            container.setAnimation(translate_anim);




        }
    }
    public class MenuViewHolder extends RecyclerAdapter.MyViewHolder{
        public MenuViewHolder(View view){
            super(view);
        }
    }


}
