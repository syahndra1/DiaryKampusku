package org.syahndra.diaryku.DataClass;

public class HomeWorkData {

    int Id;
    String ClassName;
    String Content;
    String Date;

    public HomeWorkData(int id, String className, String content, String date) {
        Id = id;
        ClassName = className;
        Content = content;
        Date = date;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
