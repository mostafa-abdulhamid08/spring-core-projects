package com.libraryProject.beans;

public class Magazine {
    private String title;
    private int issueNumber;

    public Magazine(String title, int issueNumber) {
        this.title = title;
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "title='" + title + '\'' +
                ", issueNumber=" + issueNumber +
                '}';
    }
}
