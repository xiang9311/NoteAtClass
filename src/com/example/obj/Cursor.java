package com.example.obj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
import android.util.Log;

public class Cursor {
	
	private String term;
	private String name;
	private Teacher teacher;
	private String infor;
	
	public Cursor(String term , String name , Teacher teacher , String infor)
	{
		this.term = term;
		this.name = name;
		this.teacher = teacher;
		this.infor = infor;
	}
	
	public Cursor(String term , String name)
	{
		this.term = term;
		this.name = name;
		
		byte[] buffer = null;
		File file = new File(Environment.getExternalStorageDirectory().toString() + "/ClassNote/" + term +
				"/" + name + "/" + "infor.txt");
		try {
			FileInputStream fis = new FileInputStream(file);
			try {
				buffer = new byte[fis.available()];
				fis.read(buffer);
				String data = new String(buffer);
				String[] information = data.split("=-=");
				String n = "";
				String num = "";
				String e = "";
				String i = "";
				int k = information.length;
				for(int j = 2 ; j < k ; j ++)
				{
					if(j == 2)
						n = information[j];
					if(j == 3)
						num = information[j];
					if(j == 4)
						e = information[j];
					if(j == 5)
						i = information[j];
				}
				
				Teacher teacher = new Teacher(term , n , num , e);
				this.teacher = teacher;
				this.infor = i;
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
	
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Teacher getTeacher()
	{
		return this.teacher;
	}
	public void setTeacher(Teacher teacher)
	{
		this.teacher = teacher;
	}
	
	public String getInfor()
	{
		return this.infor;
	}
	public void set(String infor)
	{
		this.infor = infor;
	}
	
    public void save()
    {
    	File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() 
    			+ "/ClassNote/" + this.term + "/" + name);
    	if(!folder.exists())
    	{
    		folder.mkdir();
    	}
    	
    	File file = new File(folder.getAbsolutePath() + "/infor.txt");
    	if(!file.exists())
    	{
    		try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	//规则 学期+课程名字+老师名字+老师手机+老师email+备注
    	String text = this.term + "=-=" + this.name + "=-=" + this.teacher.getName() + "=-=" +
    			this.teacher.getPhone() + "=-=" + this.teacher.getEmail() + "=-=" + this.infor;
    	try {
			FileOutputStream fos = new FileOutputStream(file);
			
			try {
				fos.write(text.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(fos != null)
			{
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static List<Chapter> getChapters(String term , String cursor){
    	List<Chapter> chapters = new ArrayList<Chapter>();
    	
    	File file = new File(Environment.getExternalStorageDirectory().toString() + "/ClassNote/" +
    			term + "/" + cursor);
    	File[] files = file.listFiles();
    	for(File f: files){
    		if(!f.getName().equals("infor.txt")){
    			Chapter ch = new Chapter(term, cursor, f.getName().split("\\.")[0]);
    			chapters.add(ch);
    		}
    	}
    	
    	return chapters;
    }
}
