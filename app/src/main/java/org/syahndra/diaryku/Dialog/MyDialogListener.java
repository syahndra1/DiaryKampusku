package org.syahndra.diaryku.Dialog;

import org.syahndra.diaryku.DataClass.DiaryData;
import org.syahndra.diaryku.DataClass.HomeWorkData;
import org.syahndra.diaryku.DataClass.MemoData;

public interface MyDialogListener {
    public void onPositiveClicked(String input);
    public void onMemoClicked(MemoData input);
    public void onHomeWorkClicked(HomeWorkData input);
    public void onDiaryClicked(DiaryData input);
    public void onNegativeClicked();
}
