package com.becky.becky.model;

public class Message {

    private String chatId;
	private String text;
	private String userName;
	private String time;

    public String getChatId() {
        return chatId;
    }
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public String getSenderName() {
		return "You";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
