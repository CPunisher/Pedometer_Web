package com.cpunisher.pedo.util.mail;

public class MailMessage {

    private String to;
    private String title;
    private String content;

    public MailMessage() {
    }

    public MailMessage(String to, String title, String content) {
        this.to = to;
        this.title = title;
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MailMessage{" +
                "to='" + to + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
