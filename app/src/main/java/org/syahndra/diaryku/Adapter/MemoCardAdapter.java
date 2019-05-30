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
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.syahndra.diaryku.DataBase.DBHelper;
import org.syahndra.diaryku.DataClass.DiaryData;
import org.syahndra.diaryku.DataClass.HomeWorkData;
import org.syahndra.diaryku.DataClass.MemoData;
import org.syahndra.diaryku.Dialog.DialogContentMemo;
import org.syahndra.diaryku.Dialog.MyDialogListener;
import org.syahndra.diaryku.R;

import java.util.ArrayList;

public class MemoCardAdapter extends RecyclerView.Adapter<MemoCardAdapter.ViewHolder> {


    private ArrayList<MemoData> MemoDataList = new ArrayList<MemoData>();
    private Context context;
    private DBHelper dbHelper;
    private Activity activity;

    public MemoCardAdapter(ArrayList<MemoData> homeWorkDataArrayList,Context context,Activity activity){
        this.MemoDataList = homeWorkDataArrayList;
        this.context = context;
        this.dbHelper = new DBHelper(context,"DataBase.db",null,2);
        this.activity = activity;
    }

    @NonNull
    @Override
    public MemoCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_memo,viewGroup,false);

        MemoCardAdapter.ViewHolder vh = new MemoCardAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MemoCardAdapter.ViewHolder holder, int position) {

        final int pos = position;

        if(pos%3==0){
            holder.DateLayout.setBackgroundColor(Color.parseColor("#EBEFC9"));
        }
        else if(pos%3==1){
            holder.DateLayout.setBackgroundColor(Color.parseColor("#EEE0B7"));
        }
        else if(pos%3==2){
            holder.DateLayout.setBackgroundColor(Color.parseColor("#E8CAAF"));
        }

//        if(MemoDataList.get(pos).getId()%5==0){
//            holder.DateLayout.setBackgroundColor(Color.parseColor("#EAC866"));
//        }
//        else if(MemoDataList.get(pos).getId()%5==1){
//            holder.DateLayout.setBackgroundColor(Color.parseColor("#F7E6D1"));
//        }
//        else if(MemoDataList.get(pos).getId()%5==2){
//            holder.DateLayout.setBackgroundColor(Color.parseColor("#D2C4D5"));
//        }
//        else if(MemoDataList.get(pos).getId()%5==3){
//            holder.DateLayout.setBackgroundColor(Color.parseColor("#EFD9DF"));
//        }
//        else if(MemoDataList.get(pos).getId()%5==4){
//            holder.DateLayout.setBackgroundColor(Color.parseColor("#C5E0E4"));
//        }

        holder.MemoContentText.setText(MemoDataList.get(position).getContent());
        holder.MemoDateText.setText(MemoDataList.get(position).getDate());
        if(position%2==1){
            holder.MemoContentText.setMinHeight(400);
        }
        else{
            holder.MemoContentText.setMinHeight(300);
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogContentMemo dialogContentMemo = new DialogContentMemo(context,MemoDataList.get(pos));
                dialogContentMemo.setCanceledOnTouchOutside(false);
                dialogContentMemo.setDialogListener(new MyDialogListener() {
                    @Override
                    public void onPositiveClicked(String input) {

                    }
                    @Override
                    public void onMemoClicked(MemoData input) {
                        MemoDataList.set(pos,input);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onHomeWorkClicked(HomeWorkData input) {

                    }

                    @Override
                    public void onDiaryClicked(DiaryData input) {

                    }

                    @Override
                    public void onNegativeClicked() {
                        MemoDataList.remove(pos);
                        notifyDataSetChanged();
                    }
                });
                dialogContentMemo.show();
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
                                        dbHelper.MemoDelete(MemoDataList.get(pos).getId());
                                        MemoDataList.remove(pos);
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
        return MemoDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView MemoContentText;
        public TextView MemoDateText;
        public RelativeLayout item;
        public RelativeLayout DateLayout;

        public ViewHolder(View view) {
            super(view);
            MemoContentText = (TextView)view.findViewById(R.id.card_memo_content_text);
            MemoDateText = (TextView)view.findViewById(R.id.card_memo_date_text);
            item = (RelativeLayout)view.findViewById(R.id.memocard_layout);
            DateLayout = (RelativeLayout)view.findViewById(R.id.memocard_date_layout);
        }
    }

}
