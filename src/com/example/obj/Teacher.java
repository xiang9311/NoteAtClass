package com.example.obj;

public class Teacher {
	
	private String name;
	private String phone;
	private String email;
	private String infor;
	private String term;
	
	public Teacher(String term , String name , String phone , String email)
	{
		this.term = term;
		this.name = name;
		this.phone = phone;
		this.email = email;
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
	
	public String getPhone()
	{
		return this.phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
    
	public String getInfor()
	{
		return this.infor;
	}
	public void setInfor(String infor)
	{
		this.infor = infor;
	}

}
