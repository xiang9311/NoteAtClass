package com.example.noteatclass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.adapter.CursorListAdapter;
import com.example.obj.Cursor;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
	
	private long exitTime;
	private String header;
	
	private ImageButton ib_addcursor;
	private ListView lv_cursors;
	private TextView tv_main_time;
	private ImageButton ib_choose_year;
	
	private List<Cursor> al;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//绑定各个View
		ib_addcursor = (ImageButton) findViewById(R.id.ib_addcursor);
		lv_cursors = (ListView) findViewById(R.id.lv_cursors);
		tv_main_time = (TextView) findViewById(R.id.tv_main_time);
		ib_choose_year = (ImageButton) findViewById(R.id.ib_choose_year);
		
		//初始化处理
		//检查根文件夹
        checkRootFolder();
        
        fileListView();
		
		
		
		//事件处理
		ib_addcursor.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, AddCursorActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("from", 0);
				bundle.putString("term", header);
				intent.putExtras(bundle);
				startActivity(intent);
			}} );
		
		ib_choose_year.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// 
				File folder = new File(Environment.getExternalStorageDirectory().toString() + "/ClassNote");
				final String[] array = folder.list();
				Builder builder = new AlertDialog.Builder(MainActivity.this);
				//builder.setTitle("学期列表");
				View customTitleView = LayoutInflater.from(getBaseContext()).inflate(R.layout.my_title_view, null);
				builder.setCustomTitle(customTitleView);
				builder.setItems(array, new AlertDialog.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// 
						String s = array[arg1];
						tv_main_time.setText(s);
						header = s;
						fileListView();
					}
					
				});
				builder.create().show();
			}
        	
        });
		
		lv_cursors.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String cursorname = al.get(arg2).getName();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ChapterActivity.class);
				Bundle b = new Bundle();
				b.putString("term", header);
				b.putString("cursorname", cursorname);
				intent.putExtras(b);
				startActivity(intent);
			}
			
		});
	}
	
	//填充listView
	private void fileListView() {
		// 
		String Path = Environment.getExternalStorageDirectory().toString() + "/ClassNote/" + header;
		File folder = new File(Path);
		File[] cursors = folder.listFiles();

		al = new ArrayList<Cursor>();
		for(int i = 0 ; i < cursors.length ; i++){
			if(cursors[i].isDirectory()){
				Cursor cursor = new Cursor(header,cursors[i].getName());
				al.add(cursor);
			}
		}
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,al);
		lv_cursors.setAdapter(new CursorListAdapter(getBaseContext(),al));
	}
	
	
	//检测当前时间，调整学期
	private void checkRootFolder() {
		// 
    	Time t = new Time();
    	t.setToNow();
        Integer year = t.year;
        Integer month = t.month;
        String foldername;
        File root = Environment.getExternalStorageDirectory();
        String appHome = root.getPath() + "/ClassNote";
        
        File appFile = new File(appHome);
        
        //检查app根目录
        if(!appFile.exists())
        {
	        appFile.mkdir();
        }
        
        if(month >= 8)
        	foldername = year.toString() + "年第二学期";
        else
        	foldername = year.toString() + "年第一学期";
        
        header = foldername;
        
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
        	Toast.makeText(this, "未能找到合适的外部存储器", Toast.LENGTH_LONG).show();
        }
        else
        {
        	
        	File file = new File(appFile.getAbsolutePath() + "/" +foldername);
        	if(!file.exists())
        	{
        		file.mkdirs();
        	}
        }
        
        this.tv_main_time.setText(foldername);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(System.currentTimeMillis() - exitTime > 2000){
				Toast.makeText(this, "再按一次退出课堂笔记", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
				return false;
			}
			else{
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		fileListView();
	}

}
