package com.mastertool.pdfreader.model;

public class PDFFile {

    private String path;
    private String name;
    private String date;

    public PDFFile(String path, String name, String date) {
        this.path = path;
        this.name = name;
        this.date = date;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
