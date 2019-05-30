package org.syahndra.diaryku.Dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.syahndra.diaryku.DataBase.DBHelper;
import org.syahndra.diaryku.DataClass.HomeWorkData;
import org.syahndra.diaryku.R;

import java.util.Calendar;

public class DialogReviseHomework extends Dialog implements View.OnClickListener {

    private MyDialogListener dialogListener;
    private Context context;

    Button DayPickerButton;
    Button CancelButton;
    Button OKButton;
    TextView DayText;
    EditText EditClassName;
    EditText EditContent;

    String Date;
    String Content;
    String Subject;

    DBHelper dbHelper;

    HomeWorkData homeWorkData;


    public DialogReviseHomework(@NonNull Context context,HomeWorkData homeWorkData) {
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
        setContentView(R.layout.dialog_edithomework);

        dbHelper = new DBHelper(getContext(),"DataBase.db",null,2);

        DayPickerButton = (Button)findViewById(R.id.button_datepicker);
        CancelButton = (Button)findViewById(R.id.button_edithomework_cancel);
        OKButton = (Button)findViewById(R.id.button_edithomework_complete);
        DayText = (TextView)findViewById(R.id.text_daypicker);
        EditClassName = (EditText)findViewById(R.id.edit_homework_classname);
        EditContent = (EditText)findViewById(R.id.edit_homework_content);

        DayPickerButton.setOnClickListener(this);
        CancelButton.setOnClickListener(this);
        OKButton.setOnClickListener(this);

        EditClassName.setText(homeWorkData.getClassName());
        EditContent.setText(homeWorkData.getContent());
        DayText.setText(homeWorkData.getDate());
        Date =homeWorkData.getDate();


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.button_datepicker:
                showDialog();
                break;
            case R.id.button_edithomework_cancel:
                dismiss();
                break;
            case R.id.button_edithomework_complete:
                Subject = EditClassName.getText().toString();
                Content = EditContent.getText().toString();

                homeWorkData.setClassName(Subject);
                homeWorkData.setContent(Content);
                homeWorkData.setDate(Date);

                //D_day
                dbHelper.UpdateHomework(Subject,Content,Date,homeWorkData.getId());
                dialogListener.onHomeWorkClicked(homeWorkData);
                dismiss();
                break;
        }


    }

    private void showDialog(){

        final Calendar pickedDate = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month +=1;
                        Date = year + "." + month + "." + dayOfMonth;

                        DayText.setText(Date);
                    }
                },
                pickedDate.get(Calendar.YEAR),
                pickedDate.get(Calendar.MONTH),
                pickedDate.get(Calendar.DATE)
        );

        datePickerDialog.show();
    }
}
