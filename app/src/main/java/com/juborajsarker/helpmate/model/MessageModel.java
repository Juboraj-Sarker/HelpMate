package com.juborajsarker.helpmate.model;

public class MessageModel {

    String messageID;
    String sendFrom;
    String sendTo;
    String userName;
    String userFullName;
    String mainMessageText;
    String date;
    String time;


    public MessageModel(String messageID, String sendFrom, String sendTo, String userName,
                        String userFullName, String mainMessageText, String date, String time) {

        this.messageID = messageID;
        this.sendFrom = sendFrom;
        this.sendTo = sendTo;
        this.userName = userName;
        this.userFullName = userFullName;
        this.mainMessageText = mainMessageText;
        this.date = date;
        this.time = time;

    }

    public MessageModel() {

    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getMainMessageText() {
        return mainMessageText;
    }

    public void setMainMessageText(String mainMessageText) {
        this.mainMessageText = mainMessageText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
