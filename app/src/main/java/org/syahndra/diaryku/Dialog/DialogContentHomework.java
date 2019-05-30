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
import org.syahndra.diaryku.DataClass.HomeWorkData;
import org.syahndra.diaryku.DataClass.MemoData;
import org.syahndra.diaryku.R;

public class DialogContentHomework extends Dialog implements View.OnClickListener {

    Context context;
    MyDialogListener dialogListener;
    HomeWorkData homeWorkData;

    TextView TextContent;
    TextView TextSubject;
    TextView TextDate;

    Button OkButton;
    Button DeleteButton;
    Button ReviseButton;

    DBHelper dbHelper;

    public DialogContentHomework(@NonNull Context context, HomeWorkData homeWorkData) {
        super(context);
        this.context = context;
        this.homeWorkData = homeWorkData;
    }

    public void setDialogListener(MyDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_contenthomework);

        dbHelper = new DBHelper(getContext(),"DataBase.db",null,2);

        TextContent = (TextView)findViewById(R.id.contenthomework_content);
        TextDate = (TextView)findViewById(R.id.contenthomework_date);
        TextSubject = (TextView)findViewById(R.id.contenthomework_subject);
        OkButton = (Button)findViewById(R.id.button_contenthomework_complete);
        DeleteButton = (Button)findViewById(R.id.homework_delete_button);
        ReviseButton = (Button)findViewById(R.id.homework_revise_button);

        TextContent.setText(homeWorkData.getContent());
        TextSubject.setText(homeWorkData.getClassName());
        TextDate.setText(homeWorkData.getDate());

        OkButton.setOnClickListener(this);
        DeleteButton.setOnClickListener(this);
        ReviseButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.button_contenthomework_complete:
                dismiss();
                break;
            case R.id.homework_delete_button:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Anda yakin ingin menghapus ?");

                alertDialogBuilder
                        .setMessage("Saya ingin menghapus ?")
                        .setCancelable(false)
                        .setPositiveButton("Hapus",
                                new DialogInterface.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        dbHelper.HomeworkDelete(homeWorkData.getId());
                                        dialogInterface.cancel();
                                        dialogListener.onNegativeClicked();
                                        dismiss();
                                    }
                                })
                        .setNegativeButton("Batalkan",
                                new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
            case R.id.homework_revise_button:
                final DialogReviseHomework dialogReviseHomework = new DialogReviseHomework(getContext(),homeWorkData);
                dialogReviseHomework.setCanceledOnTouchOutside(false);
                dialogReviseHomework.setDialogListener(new MyDialogListener() {
                    @Override
                    public void onPositiveClicked(String input) {

                    }

                    @Override
                    public void onMemoClicked(MemoData input) {
                    }

                    @Override
                    public void onHomeWorkClicked(HomeWorkData input) {
                        dialogListener.onHomeWorkClicked(input);
                    }

                    @Override
                    public void onDiaryClicked(DiaryData input) {

                    }

                    @Override
                    public void onNegativeClicked() {

                    }
                });
                dialogReviseHomework.show();
                dismiss();
                break;
        }
    }
}
