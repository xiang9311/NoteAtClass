package com.example.obj;

public class Note {
	public String chaptername;
	public String content;
	
	public Note(String chaptername , String content){
		this.chaptername = chaptername;
		this.content = content;
	}
	
	public String getChaptername(){
		return this.chaptername;
	}
	
	public String getContent(){
		return this.content;
	}
}
