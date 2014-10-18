package com.example.noteatclass;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.obj.Chapter;
import com.example.obj.Cursor;
import com.example.obj.Teacher;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class AddCursorActivity extends BaseActivity {
	
	private TextView tv_addcursor;
	private Button b_save;
	private EditText et_cursorname;
	private EditText et_teachername;
	private EditText et_teacherphone;
	private EditText et_teacheremail;
	private EditText et_cursorinfor;
	
	private String header;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_cursor);
		
		//��ʼ���ؼ�
		this.tv_addcursor = (TextView)findViewById(R.id.tv_addcursor);
		this.b_save = (Button)findViewById(R.id.b_addcursor_save);
		this.et_cursorname = (EditText)findViewById(R.id.et_cursor_name);
		this.et_teachername = (EditText)findViewById(R.id.et_teacher_name);
		this.et_teacherphone = (EditText)findViewById(R.id.et_teacher_phone);
		this.et_teacheremail = (EditText)findViewById(R.id.et_teacher_email);
		this.et_cursorinfor = (EditText)findViewById(R.id.et_more);
		
		
		//��ȡ��Ϣ���ж����޸Ļ���
		//from==1���޸� from == 0�����
		Bundle bundle = this.getIntent().getExtras();
		int from = bundle.getInt("from");
		header = bundle.getString("term");
		if(from == 1){
			tv_addcursor.setText("�޸Ŀγ�");
			et_cursorname.setText(bundle.getString("cursorname"));
			et_teachername.setText(bundle.getString("teachername"));
			et_teacherphone.setText(bundle.getString("teacherphone"));
			et_teacheremail.setText(bundle.getString("teacheremail"));
			et_cursorinfor.setText(bundle.getString("infor"));
		}
		
		//�¼�����
		b_save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String cursorname = et_cursorname.getText().toString();
				String teachername = et_teachername.getText().toString();
				String teacherphone = et_teacherphone.getText().toString();
				String teacheremail = et_teacheremail.getText().toString();
				String cursorinfor = et_cursorinfor.getText().toString();
				if(cursorname == null || cursorname.equals(""))
				{
					makeToast("�γ����Ʋ���Ϊ��");
					return;
				}
				if(!isValidString(cursorname)){
					makeToast("�γ�����ֻ�ܴ�����Ӣ��");
					return;
				}
				Teacher teacher = new Teacher(header , teachername , teacherphone , teacheremail);
				Cursor cursor = new Cursor(header , cursorname , teacher , cursorinfor);
				cursor.save();
				Chapter ch = new Chapter(header , cursorname , "Ĭ��" , null);
				ch.save();
				
				finish();
			}
			
		});
		
		((ImageButton) findViewById(R.id.ib_addcursor_back)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
			
		});
		
	}
	
	 // У��  ֻ����Ӣ����ĸ������
    public static boolean isValidString(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA5a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_cursor, menu);
		return true;
	}

}
