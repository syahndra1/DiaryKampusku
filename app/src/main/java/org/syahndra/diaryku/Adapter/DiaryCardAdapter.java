package org.syahndra.diaryku.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.syahndra.diaryku.DataBase.DBHelper;
import org.syahndra.diaryku.DataClass.DiaryData;
import org.syahndra.diaryku.DataClass.HomeWorkData;
import org.syahndra.diaryku.DataClass.MemoData;
import org.syahndra.diaryku.Dialog.DialogContentDiary;
import org.syahndra.diaryku.Dialog.MyDialogListener;
import org.syahndra.diaryku.R;

import java.util.ArrayList;

public class DiaryCardAdapter extends RecyclerView.Adapter<DiaryCardAdapter.ViewHolder> {
    private ArrayList<DiaryData> DiaryList = new ArrayList<DiaryData>();
    private Context context;
    private DBHelper dbHelper;
    private Activity activity;

    public DiaryCardAdapter(ArrayList<DiaryData> DiaryList, Context context, Activity activity){
        this.DiaryList = DiaryList;
        this.context = context;
        this.dbHelper = new DBHelper(context,"DataBase.db",null,2);
        this.activity = activity;
    }

    @NonNull
    @Override
    public DiaryCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_diary2,viewGroup,false);

        DiaryCardAdapter.ViewHolder vh = new DiaryCardAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryCardAdapter.ViewHolder holder, final int position) {
        final int pos = position;
        String dayofweek;
        String date;

        if(DiaryList.get(pos).getDate().endsWith("(Senin)")){
            dayofweek = "MON";
            date = DiaryList.get(pos).getDate().replace(" (Senin)","");
        }
        else if(DiaryList.get(pos).getDate().endsWith("(Selasa)")){
            dayofweek = "TUE";
            date = DiaryList.get(pos).getDate().replace(" (Selasa)","");
        }
        else if(DiaryList.get(pos).getDate().endsWith("(Rabu)")){
            dayofweek = "WED";
            date = DiaryList.get(pos).getDate().replace(" (Rabu)","");
        }
        else if(DiaryList.get(pos).getDate().endsWith("(Kamis)")){
            dayofweek = "THU";
            date = DiaryList.get(pos).getDate().replace(" (Kamis)","");
        }
        else if(DiaryList.get(pos).getDate().endsWith("(Jumat)")){
            dayofweek = "FRI";
            date = DiaryList.get(pos).getDate().replace(" (Jumat)","");
        }
        else if(DiaryList.get(pos).getDate().endsWith("(Sabtu)")){
            dayofweek = "SAT";
            date = DiaryList.get(pos).getDate().replace(" (Sabtu)","");
        }
        else if(DiaryList.get(pos).getDate().endsWith("(Minggu)")) {
            dayofweek = "SUN";
            date = DiaryList.get(pos).getDate().replace(" (Minggu)","");
        }
        else{
            dayofweek="null";
            date = "null";
        }



        if(DiaryList.get(pos).getEmotion().equals("Marah")){
            holder.emotion.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_emotion_angry_white));
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.angry));
        }
        else if(DiaryList.get(pos).getEmotion().equals("Suram")){
            holder.emotion.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_emotion_sad_white));
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.sad));
        }
        else if(DiaryList.get(pos).getEmotion().equals("Tenang")){
            holder.emotion.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_emotion_peace_white));
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.peace));
        }
        else if(DiaryList.get(pos).getEmotion().equals("Bahagia")){
            holder.emotion.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_emotion_happy_white));
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.happy));
        }
        else if(DiaryList.get(pos).getEmotion().equals("Penat")){
            holder.emotion.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_emotion_tired_white));
            holder.layout.setBackgroundColor(context.getResources().getColor(R.color.tired));
        }


        holder.DayofWeekText.setText(dayofweek);
        holder.DateText.setText(date);


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogContentDiary dialogContentDiary = new DialogContentDiary(context,DiaryList.get(position));
                dialogContentDiary.setDialogListener(new MyDialogListener() {
                    @Override
                    public void onPositiveClicked(String input) {

                    }

                    @Override
                    public void onMemoClicked(MemoData input) {

                    }

                    @Override
                    public void onHomeWorkClicked(HomeWorkData input) {

                    }

                    @Override
                    public void onDiaryClicked(DiaryData input) {

                    }

                    @Override
                    public void onNegativeClicked() {
                        DiaryList.remove(pos);
                        notifyDataSetChanged();
                    }
                });
                dialogContentDiary.show();
            }
        });
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Vibrator vibrator = (Vibrator)activity.getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(50);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Apakah kamu yakin ?");

                alertDialogBuilder
                        .setMessage("Saya ingin menghapus ?")
                        .setCancelable(false)
                        .setPositiveButton("Hapus",
                                new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dbHelper.DiaryDelete(DiaryList.get(pos).getId());
                                        DiaryList.remove(pos);
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
        return DiaryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView DayofWeekText;
        TextView DateText;
        ImageView emotion;
        LinearLayout item;
        RelativeLayout layout;


        public ViewHolder(View view) {
            super(view);
            DayofWeekText = (TextView)view.findViewById(R.id.diarycard2_dayofweek_text);
            DateText = (TextView)view.findViewById(R.id.diarycard2_datetext);
            emotion = (ImageView)view.findViewById(R.id.diarycard2_emotion_icon);
            item = (LinearLayout)view.findViewById(R.id.diarycard2_item);
            layout = (RelativeLayout)view.findViewById(R.id.diarycard2_emotion_layout);
        }
    }
}
