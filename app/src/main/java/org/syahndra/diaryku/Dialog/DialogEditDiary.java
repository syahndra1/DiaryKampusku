package org.syahndra.diaryku.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.syahndra.diaryku.DataBase.DBHelper;
import org.syahndra.diaryku.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DialogEditDiary extends Dialog {
    private MyDialogListener dialogListener;
    private Context context;

    Button AngryButton;
    Button SadButton;
    Button TiredButton;
    Button PeaceButton;
    Button HappayButton;

    EditText EditContent;
    TextView EmotionText;

    Button OkButton;
    Button CancelButton;

    LinearLayout TabLayout;

    DBHelper dbHelper;

    String date;

    public DialogEditDiary(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public void setDialogListener(MyDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_editdiary);

        dbHelper = new DBHelper(getContext(),"DataBase.db",null,2);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd (E)");
        Date CurrentTIme = new Date();
        date = simpleDateFormat.format(CurrentTIme);

        TabLayout = (LinearLayout)findViewById(R.id.edit_diary_tablayout);

        AngryButton = (Button)findViewById(R.id.edit_diary_angrybutton);
        SadButton = (Button)findViewById(R.id.edit_diary_sadbutton);
        TiredButton = (Button)findViewById(R.id.edit_diary_tiredbutton);
        PeaceButton = (Button)findViewById(R.id.edit_diary_peacebutton);
        HappayButton = (Button)findViewById(R.id.edit_diary_happybutton);

        EditContent = (EditText)findViewById(R.id.edit_diary_content);
        EmotionText = (TextView)findViewById(R.id.edit_diary_emotiontext);
        OkButton = (Button)findViewById(R.id.button_editdiary_complete);
        CancelButton = (Button)findViewById(R.id.button_editdiary_cancel);


        AngryButton.setOnClickListener(movePageListener);
        AngryButton.setTag(0);
        SadButton.setOnClickListener(movePageListener);
        SadButton.setTag(1);
        TiredButton.setOnClickListener(movePageListener);
        TiredButton.setTag(2);
        PeaceButton.setOnClickListener(movePageListener);
        PeaceButton.setTag(3);
        HappayButton.setOnClickListener(movePageListener);
        HappayButton.setTag(4);


        PeaceButton.setSelected(true);
        EmotionText.setText("Tenang");

        OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Date = date;
                String Content = EditContent.getText().toString();
                String Emotion = EmotionText.getText().toString();

                dbHelper.DiaryInsert(Date,Content,Emotion);
                dialogListener.onNegativeClicked();

                dismiss();
            }
        });
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();

            int i = 0;

            if(tag==0){
                EmotionText.setText("Marah");
            }
            else if(tag==1){
                EmotionText.setText("Suram");
            }
            else if(tag==2){
                EmotionText.setText("Penat");
            }
            else if(tag==3){
                EmotionText.setText("Tenang");
            }
            else if(tag==4){
                EmotionText.setText("Bahagia");
            }

            while(i<5){
                if(tag==i){
                    TabLayout.findViewWithTag(i).setSelected(true);
                }
                else{
                    TabLayout.findViewWithTag(i).setSelected(false);
                }
                i++;
            }
        }
    };


}
