package com.example.obj;

//章节类，用于表示章节和存储章节信息及笔记内容。

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;

public class Chapter {
	
	//分别表示章节的 学期 ， 课程 ， 名称 ， 笔记内容。
	private String term;
	private String cursor;
	private String name;
	private List<String> notes;
	
	public Chapter(String term , String cursor , String name , ArrayList<String> notes)
	{
	    this.term = term;
	    this.cursor = cursor;
	    this.name = name;
	    this.notes = notes;
	}
	
	public Chapter(String term , String cursor , String name){
		this.term = term;
	    this.cursor = cursor;
	    this.name = name;
	    
	    //Log.d("getChapter", name);
	    
	    notes = new ArrayList<String>();
	    
	    File file = new File(Environment.getExternalStorageDirectory().toString() + "/ClassNote/" +
		term + "/" + cursor + "/" + name + ".txt");
		try {
			FileInputStream fis = new FileInputStream(file);
			try {
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				String data = new String(buffer);
				String[] array = data.split("=-=");
				if(array != null)
				{
					//把建立的章节对象补充完整
					
					//String[] a = new String[array.length];
					Integer i = 0;
					//this.notesnum = 0;
					for(String s: array)
					{
						//al.add(s);
						//this.notesnum = i + 1;
						//a[i] = this.notesnum.toString() + ".";
						//if(array[i].length() > 6)
							//a[i] += (String) array[i].subSequence(0, 5) + "...";
						//else
							//a[i] += array[i] + "...";
						//i++;
						char[] l = s.toCharArray();
						int count = 0;
						for(char c:l){
							if(c!=' '){
								count ++;
								break;
							}
						}
						if(count != 0){
							notes.add(s);
						}
					}
					//this.cpt.setNotes(al);
					//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,a);
					//this.lv_notes.setAdapter(adapter);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(fis != null)
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getTerm()
	{
		return this.term;
	}
	public void setTerm(String term)
	{
		this.term = term;
	}
	
	public String getCursor()
	{
		return this.cursor;
	}
	public void setCursor(String cursor)
	{
		this.cursor = cursor;
	}
	
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public List<String> getNotes()
	{
		return this.notes;
	}
	public void setNotes(ArrayList<String> notes)
	{
		this.notes = notes;
	}
	
	//存储该章节。
	public void save()
	{
		if(this.name == null)
			return;
		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() 
    			+ "/ClassNote/" + this.term + "/" + this.cursor + "/" + this.name + ".txt");
		if(!file.exists())
    	{
    		try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
		
		String text = new String();
		
		//判断动态数组是否为空
		if(notes != null)
		{
			for(int i = 0 ; i < notes.size() ; i ++)
			{
				text = text + notes.get(i) + "=-=";
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(file);
			
			try {
				fos.write(text.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
