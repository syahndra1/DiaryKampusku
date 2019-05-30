package org.syahndra.diaryku.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.syahndra.diaryku.DataBase.DBHelper;
import org.syahndra.diaryku.DataClass.DiaryData;
import org.syahndra.diaryku.DataClass.HomeWorkData;
import org.syahndra.diaryku.DataClass.MemoData;
import org.syahndra.diaryku.Dialog.DialogContentHomework;
import org.syahndra.diaryku.Dialog.MyDialogListener;
import org.syahndra.diaryku.R;

import java.util.ArrayList;

public class HomeWorkCardAdapter extends RecyclerView.Adapter<HomeWorkCardAdapter.ViewHolder> {

    private ArrayList<HomeWorkData> homeWorkDataArrayList = new ArrayList<HomeWorkData>();
    private Context context;
    private DBHelper dbHelper;
    private Activity activity;

    public HomeWorkCardAdapter(ArrayList<HomeWorkData> homeWorkDataArrayList, Context context, Activity activity){
        this.homeWorkDataArrayList = homeWorkDataArrayList;
        this.context = context;
        this.dbHelper = new DBHelper(context,"DataBase.db",null,2);
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_homework,viewGroup,false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final int pos = position;

        if(homeWorkDataArrayList.get(pos).getId()%6==0){
            holder.TextLayout.setBackgroundColor(Color.parseColor("#B5E5C0"));
        }
        else if(homeWorkDataArrayList.get(pos).getId()%6==1){
            holder.TextLayout.setBackgroundColor(Color.parseColor("#FC9D9A"));
        }
        else if(homeWorkDataArrayList.get(pos).getId()%6==2){
            holder.TextLayout.setBackgroundColor(Color.parseColor("#F9CDAD"));
        }
        else if(homeWorkDataArrayList.get(pos).getId()%6==3){
            holder.TextLayout.setBackgroundColor(Color.parseColor("#C8C8A9"));
        }
        else if(homeWorkDataArrayList.get(pos).getId()%6==4){
            holder.TextLayout.setBackgroundColor(Color.parseColor("#6A5A5D"));
        }
        else if(homeWorkDataArrayList.get(pos).getId()%6==5){
            holder.TextLayout.setBackgroundColor(Color.parseColor("#83AF9B"));
        }

        holder.TextClass.setText(homeWorkDataArrayList.get(position).getClassName());
        holder.TextDate.setText(homeWorkDataArrayList.get(position).getDate());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogContentHomework dialogContentHomework = new DialogContentHomework(context,homeWorkDataArrayList.get(pos));
                dialogContentHomework.setCanceledOnTouchOutside(false);
                dialogContentHomework.setDialogListener(new MyDialogListener() {
                    @Override
                    public void onPositiveClicked(String input) {

                    }
                    @Override
                    public void onMemoClicked(MemoData input) {

                    }

                    @Override
                    public void onHomeWorkClicked(HomeWorkData input) {
                        homeWorkDataArrayList.set(position,input);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onDiaryClicked(DiaryData input) {

                    }

                    @Override
                    public void onNegativeClicked() {
                        homeWorkDataArrayList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                dialogContentHomework.show();
            }
        });
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Vibrator vibrator = (Vibrator)activity.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(50);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Anda yakin ingin menghapus ?");

                alertDialogBuilder
                        .setMessage("Saya ingin menghapus ?")
                        .setCancelable(false)
                        .setPositiveButton("Hapus",
                                new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dbHelper.HomeworkDelete(homeWorkDataArrayList.get(position).getId());
                                        homeWorkDataArrayList.remove(position);
                                        notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("Batalkan",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return homeWorkDataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TextClass;
        public TextView TextDate;
        public LinearLayout item;
        public RelativeLayout TextLayout;

        public ViewHolder(View view) {
            super(view);
            TextClass = (TextView)view.findViewById(R.id.card_homework_classname);
            TextDate = (TextView)view.findViewById(R.id.card_homework_datepicker);
            item = (LinearLayout)view.findViewById(R.id.homeworkcard_item);
            TextLayout = (RelativeLayout)view.findViewById(R.id.text_layout);
        }
    }
}
