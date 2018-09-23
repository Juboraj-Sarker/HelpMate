package com.juborajsarker.helpmate.model;

public class LastChat {

    String uid;
    String userName;
    String lastMessageId;
    String lastMessage;
    String lastMessageDate;
    String lastMessageTime;

    public LastChat(String uid, String userName, String lastMessageId, String lastMessage,
                    String lastMessageDate, String lastMessageTime) {

        this.uid = uid;
        this.userName = userName;
        this.lastMessageId = lastMessageId;
        this.lastMessage = lastMessage;
        this.lastMessageDate = lastMessageDate;
        this.lastMessageTime = lastMessageTime;

    }

    public LastChat() {

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(String lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageDate() {
        return lastMessageDate;
    }

    public void setLastMessageDate(String lastMessageDate) {
        this.lastMessageDate = lastMessageDate;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
