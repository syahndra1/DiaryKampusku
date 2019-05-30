package org.syahndra.diaryku;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.syahndra.diaryku.Adapter.MemoCardAdapter;
import org.syahndra.diaryku.DataBase.DBHelper;
import org.syahndra.diaryku.DataClass.DiaryData;
import org.syahndra.diaryku.DataClass.HomeWorkData;
import org.syahndra.diaryku.DataClass.MemoData;
import org.syahndra.diaryku.Dialog.DialogEditMemo;
import org.syahndra.diaryku.Dialog.MyDialogListener;

import java.util.ArrayList;

public class FragmentMemo extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    ArrayList<MemoData> memoDataArrayList;
    ArrayList<MemoData> CopyDataList;

    Button MemoButton;

    public FragmentMemo() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo, container, false);

        final DBHelper dbHelper = new DBHelper(getContext(),"DataBase.db",null,2);

        CopyDataList = dbHelper.getResultMemoData();

        recyclerView = (RecyclerView)view.findViewById(R.id.cardview_memo);
        MemoButton = (Button)view.findViewById(R.id.memo_edit_button);

        recyclerView.setHasFixedSize(true);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        memoDataArrayList = new ArrayList<MemoData>();
        adapter = new MemoCardAdapter(memoDataArrayList,getContext(),getActivity());
        recyclerView.setAdapter(adapter);

        for(int i=0; i<CopyDataList.size();i++){
            memoDataArrayList.add(CopyDataList.get(i));
        }
        adapter.notifyDataSetChanged();

        /////////////////////

        MemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogEditMemo dialogEditMemo = new DialogEditMemo(getContext());
                dialogEditMemo.setCanceledOnTouchOutside(false);
                dialogEditMemo.setDialogListener(new MyDialogListener() {
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
                dialogEditMemo.show();
            }
        });


        return view;
    }

    private void refresh(){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }

}
