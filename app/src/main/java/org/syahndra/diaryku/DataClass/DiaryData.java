package org.syahndra.diaryku.DataClass;

public class DiaryData {

    public int id;
    public String emotion;
    public String date;
    public String content;

    public DiaryData(int id, String emotion, String date, String content) {
        this.id = id;
        this.emotion = emotion;
        this.date = date;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
