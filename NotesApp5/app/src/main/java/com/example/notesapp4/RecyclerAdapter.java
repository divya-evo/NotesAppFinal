package com.example.notesapp4;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//
//public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    List<RecyclerEntity> list;
    Context context;
    Activity activity;

    private final int SHOW_MENU = 1;
    private final int HIDE_MENU = 2;
    private static final String TAG = "RecyclerAdapter";
    ArrayList<String> noteIdArray, noteTitleArray, noteContentArray;

//    public RecyclerAdapter(Context context, List<RecyclerEntity> articlesList) {
//        this.list = articlesList;
//        this.context = context;
//    }

    public RecyclerAdapter(Activity activity, Context context, ArrayList noteIdArray, ArrayList noteTitleArray, ArrayList noteContentArray) {
        this.activity = activity;
        this.context = context;
        this.noteIdArray = noteIdArray;
        this.noteTitleArray = noteTitleArray;
        this.noteContentArray = noteContentArray;

    }
//    @Override
//    public int getItemViewType(int position) {
//        if(list.get(position).isShowMenu()){
//            return SHOW_MENU;
//        }else{
//            return HIDE_MENU;
//        }
//    }
    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list, parent, false);
        return new MyViewHolder(v);
//        if(viewType==SHOW_MENU){
//            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list, parent, false);
//
//            return new MenuViewHolder(v);
//        }else{
//            v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list, parent, false);
//            return new MyViewHolder(v);
//        }
    }




//    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       holder.noteIdText.setText(String.valueOf(noteIdArray.get(position)));
       holder.noteTitleText.setText(String.valueOf(noteTitleArray.get(position)));
       holder.noteContentText.setText(String.valueOf(noteContentArray.get(position)));

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(noteIdArray.get(position)));
                intent.putExtra("title", String.valueOf(noteTitleArray.get(position)));
                intent.putExtra("content", String.valueOf(noteContentArray.get(position)));
               activity.startActivityForResult(intent, 1);
            }
        });
        Log.d(TAG,noteTitleArray.toString());

//        RecyclerEntity entity = list.get(position);
//        if(holder instanceof MyViewHolder){
//            ((MyViewHolder)holder).noteTitle.setText(entity.getTitle());
//            ((MyViewHolder)holder).noteContent.setText(entity.getTitle());
//            //((MyViewHolder)holder).imageView.setImageDrawable(context.getResources().getDrawable(entity.getImage()));
//            ((MyViewHolder)holder).container.setOnLongClickListener(new View.OnLongClickListener() {
//
//                @Override
//                public boolean onLongClick(View v) {
//                    showMenu(position);
//                    return true;
//
//                }
//            });
//
//            ((MyViewHolder)holder).container.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, list.get(position).getTitle() , Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "onClick:clicked");
//                    Intent intent = new Intent(context, GalleryActivity.class);
//                    intent.putExtra("list_item", (Serializable) list.get(position).getTitle());
//                    context.startActivity(intent);
//                }
//            });
//        }



//        if(holder instanceof MenuViewHolder){
//            //Menu Actions
//            //((MenuViewHolder)holder).edit.setOnClickListener(null);
//        }

    }

    @Override
    public int getItemCount() {
        //return list.size();
        return noteTitleArray.size();
    }


//    public void showMenu(int position) {
//        for(int i=0; i<list.size(); i++){
//            list.get(i).setShowMenu(false);
//        }
//        list.get(position).setShowMenu(true);
//        notifyDataSetChanged();
//    }


//    public boolean isMenuShown() {
//        for(int i=0; i<list.size(); i++){
//            if(list.get(i).isShowMenu()){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void closeMenu() {
//        for(int i=0; i<list.size(); i++){
//            list.get(i).setShowMenu(false);
//        }
//        notifyDataSetChanged();
//    }
//
//    public void deleteItem(int adapterPosition) {
//
//        list.remove(adapterPosition);
//        notifyItemRemoved(adapterPosition);
//    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView noteIdText, noteTitleText, noteContentText;

      //  ImageView imageView;
        LinearLayout container;
        LinearLayout updateLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            noteIdText = itemView.findViewById(R.id.id_input);
            noteTitleText = itemView.findViewById(R.id.note_title_text);
            noteContentText = itemView.findViewById(R.id.note_content_text);
            //imageView = itemView.findViewById(R.id.imageView);
            container = itemView.findViewById(R.id.container);
            updateLayout = itemView.findViewById(R.id.updateLayout);
        }
    }
    public class MenuViewHolder extends RecyclerView.ViewHolder{
        public MenuViewHolder(View view){
            super(view);
        }
    }

}
