package org.syahndra.diaryku;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.syahndra.diaryku.Adapter.HomeWorkCardAdapter;
import org.syahndra.diaryku.DataBase.DBHelper;
import org.syahndra.diaryku.DataClass.DiaryData;
import org.syahndra.diaryku.DataClass.HomeWorkData;
import org.syahndra.diaryku.DataClass.MemoData;
import org.syahndra.diaryku.Dialog.DialogEditHomeWork;
import org.syahndra.diaryku.Dialog.MyDialogListener;

import java.util.ArrayList;

public class FragmentHomeWork extends Fragment {

    Button TestButton;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    ArrayList<HomeWorkData> homeWorkDataArrayList;
    ArrayList<HomeWorkData> CopyDataList;


    public FragmentHomeWork() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homework, container, false);

        final DBHelper dbHelper = new DBHelper(getContext(),"DataBase.db",null,2);

        CopyDataList = dbHelper.getResultHomeworkData();

        TestButton = (Button)view.findViewById(R.id.homework_edit_button);
        recyclerView = (RecyclerView)view.findViewById(R.id.cardview_homework);

        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        homeWorkDataArrayList = new ArrayList<HomeWorkData>();
        adapter = new HomeWorkCardAdapter(homeWorkDataArrayList,getContext(),getActivity());
        recyclerView.setAdapter(adapter);

        for(int i=0; i<CopyDataList.size();i++){
            homeWorkDataArrayList.add(CopyDataList.get(i));
        }
        adapter.notifyDataSetChanged();

        TestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogEditHomeWork dialogEditHomeWork = new DialogEditHomeWork(getContext());
                dialogEditHomeWork.setCanceledOnTouchOutside(false);
                dialogEditHomeWork.setDialogListener(new MyDialogListener() {
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
                        refresh();
                    }
                });
                dialogEditHomeWork.show();

            }
        });


        return view;
    }

    public void refresh(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }

}
