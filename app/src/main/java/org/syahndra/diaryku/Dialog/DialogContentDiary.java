package org.syahndra.diaryku.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.syahndra.diaryku.DataBase.DBHelper;
import org.syahndra.diaryku.DataClass.DiaryData;
import org.syahndra.diaryku.R;

public class DialogContentDiary extends Dialog implements View.OnClickListener {

    Context context;
    MyDialogListener dialogListener;
    DiaryData diaryData;

    TextView TextContent;
    TextView TextEmotion;
    TextView TextDate;

    Button OkButton;
    Button DeleteButton;

    DBHelper dbHelper;

    public DialogContentDiary(@NonNull Context context, DiaryData diaryData) {
        super(context);
        this.context = context;
        this.diaryData = diaryData;
    }

    public void setDialogListener(MyDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_contentdiary);

        dbHelper = new DBHelper(getContext(),"DataBase.db",null,2);

        TextContent = (TextView)findViewById(R.id.contentdiary_content);
        TextEmotion = (TextView)findViewById(R.id.contentdiary_emotion);
        TextDate = (TextView)findViewById(R.id.contentdiary_date);

        OkButton = (Button)findViewById(R.id.button_contentdiary_complete);
        DeleteButton = (Button)findViewById(R.id.contentdiary_delete_button);

        OkButton.setOnClickListener(this);
        DeleteButton.setOnClickListener(this);

        TextContent.setText(diaryData.getContent());
        TextEmotion.setText(diaryData.getEmotion());
        TextDate.setText(diaryData.getDate());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_contentdiary_complete:
                dismiss();
                break;
            case R.id.contentdiary_delete_button:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Apakah kamu yakin ?");

                alertDialogBuilder
//                        .setMessage("Saya ingin menghapus ?")
                        .setCancelable(false)
                        .setPositiveButton("Hapus",
                                new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dbHelper.DiaryDelete(diaryData.getId());
                                        dialogListener.onNegativeClicked();
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
                dismiss();
                break;
        }
    }
}
