package com.example.noteatclass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.adapter.ChapterListAdapter;
import com.example.adapter.NoteListAdapter;
import com.example.noteatclass.R.id;
import com.example.obj.Chapter;
import com.example.obj.Cursor;
import com.example.obj.Note;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

public class ChapterActivity extends BaseActivity {
	
	public static ChapterActivity that = null;

	private ImageButton ib_chapters_back;
	private TextView tv_allnotes;
	private TextView tv_allchapters;
	private ListView lv_allnotes;
	private ListView lv_allchapters;
	private ImageButton ib_addchapter;
	private ImageButton ib_editchapter;
	private EditText et_empty;
	private Button b_savenoteinlist;
	
	private String cursorname;
	private String term;
	
	public List<Note> li_notes;
	public List<Chapter> li_chapters;
	
	private boolean added;
	private int state;
	private String chapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chapter);
		
		that = this;
		
		//获取其他活动传来的参数
		Bundle b = getIntent().getExtras();
		cursorname = b.getString("cursorname");
		term = b.getString("term");
		
		//设置各个控件
		ib_chapters_back = (ImageButton) findViewById(R.id.ib_chapters_back);
		tv_allnotes = (TextView) findViewById(R.id.tv_allnotes);
		tv_allchapters = (TextView) findViewById(R.id.tv_allchapters);
		lv_allnotes = (ListView) findViewById(R.id.lv_allnotes);
		lv_allchapters = (ListView) findViewById(R.id.lv_chapters);
		ib_addchapter = (ImageButton) findViewById(R.id.ib_addchapter);
		et_empty = (EditText) findViewById(R.id.et_empty);
		ib_editchapter = (ImageButton) findViewById(R.id.ib_editchapter);
		b_savenoteinlist = (Button) findViewById(R.id.b_savenoteinlist);
		
		//初始化某些变量     两个列表
		((TextView) findViewById(R.id.tv_cursorname)).setText(cursorname);
		li_chapters = Cursor.getChapters(term, cursorname);
		li_notes = new ArrayList<Note>();
		for(Chapter ch:li_chapters){
			List<String> notes = ch.getNotes();
			for(String s:notes){
				Note n = new Note(ch.getName(),s);
				li_notes.add(n);
			}
		}
		
		added = false;//刚开始没哟添加底部的view
		state = 0;
		chapter = "全部笔记";
		
		
		
		//事件处理
		ib_chapters_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
			
		});
		
		tv_allnotes.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(lv_allnotes.getVisibility() == View.VISIBLE){
					lv_allnotes.setVisibility(View.GONE);
					Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_down);
					anim.setFillAfter(true);//动画完成后保持完成后的状态
					((ImageView) findViewById(R.id.iv_down_notes)).startAnimation(anim);
					
				}
				else{
					lv_allnotes.setVisibility(View.VISIBLE);
					Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_up);
					anim.setFillAfter(true);//动画完成后保持完成后的状态
					((ImageView) findViewById(R.id.iv_down_notes)).startAnimation(anim);
				}
			}
			
		});
		
		tv_allchapters.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(lv_allchapters.getVisibility() == View.VISIBLE){
					lv_allchapters.setVisibility(View.GONE);
					Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_down);
					anim.setFillAfter(true);//动画完成后保持完成后的状态
					((ImageView) findViewById(R.id.iv_down_chapters)).startAnimation(anim);
				}
				else{
					lv_allchapters.setVisibility(View.VISIBLE);
					Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_up);
					anim.setFillAfter(true);//动画完成后保持完成后的状态
					((ImageView) findViewById(R.id.iv_down_chapters)).startAnimation(anim);
				}
			}
			
		});
		
		lv_allchapters.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				ListView lv = (ListView)arg0;
				String s = ((Chapter)lv.getItemAtPosition(arg2)).getName();
				final String p = Environment.getExternalStorageDirectory().toString() + "/ClassNote/"
						+ term + "/" + cursorname + "/" + s + ".txt";
				Builder builder = new AlertDialog.Builder(ChapterActivity.this);
				//builder.setTitle("删除章节");
				View titleView = LayoutInflater.from(getBaseContext()).inflate(R.layout.my_title_view, null);
				((TextView) titleView.findViewById(R.id.tv_title)).setText("删除章节");
				builder.setCustomTitle(titleView);
				builder.setPositiveButton("删除", new AlertDialog.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						File file = new File(p);
						if(file.exists())
							file.delete();
						fillListView();
					}
					
				});
				
				builder.setNegativeButton("取消", null);
				builder.create().show();
				return true;
			}
			
		});
		
		lv_allnotes.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					final int arg2, long arg3) {
				// TODO Auto-generated method stub
				//final int p = arg2;
				Builder builder = new AlertDialog.Builder(ChapterActivity.this);
				//builder.setTitle("删除条目");
				View titleView = LayoutInflater.from(getBaseContext()).inflate(R.layout.my_title_view, null);
				((TextView) titleView.findViewById(R.id.tv_title)).setText("删除条目");
				builder.setCustomTitle(titleView);
				builder.setPositiveButton("删除", new AlertDialog.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Chapter ch = new Chapter(term,cursorname,li_notes.get(arg2).getChaptername());
						List<String> al = ch.getNotes();
						String s = li_notes.get(arg2).getContent();
						int key = 0;
						for(int i = 0 ;i < al.size() ; i ++){
							if(al.get(i).equals(s)){
								key = i;
								break;
							}
						}
						al.remove(key);
						ch.setNotes((ArrayList<String>) al);
						ch.save();
						fillListView();
					}
					
				});

				builder.setNegativeButton("取消", null);
				builder.create().show();
				return true;
			}
			
		});
		
		//添加章节
		ib_addchapter.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				LayoutInflater li = LayoutInflater.from(ChapterActivity.this);
				final View view = li.inflate(R.layout.input_cursor_name,null);
				Builder builder = new AlertDialog.Builder(ChapterActivity.this);
				View titleView = li.inflate(R.layout.my_title_view, null);
				((TextView) titleView.findViewById(R.id.tv_title)).setText("添加章节");
				//builder.setTitle("添加章节");
				builder.setCustomTitle(titleView);
				builder.setView(view);
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						EditText et_inputcursorname = (EditText)view.findViewById(R.id.et_inputcursorname);
						String name = et_inputcursorname.getText().toString();
						if(chapterExist(name)){
							makeToast("该章节已存在");
							return;
						}
						if(name.length() > 0)		
						{
							Chapter chapter = new Chapter(term , cursorname , name , null);
							chapter.save();
							fillListView();
						}
					}

				});
				builder.setNegativeButton("取消", null);
				builder.create();
				builder.show();
			}
			
		});
		
		//查看章节笔记
		lv_allchapters.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//更改列表内容
				Chapter ch = li_chapters.get(arg2);                   //获得当前章节
				li_notes.clear();
				List<String> notes = ch.getNotes();
				for(String s:notes){
					Note n = new Note(ch.getName(),s);
					li_notes.add(n);
				}
				//设置显示textview内容
				tv_allnotes.setText(ch.getName());
				
				//展开list
				if(lv_allnotes.getVisibility() == View.GONE){
					lv_allnotes.setVisibility(View.VISIBLE);
					Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_up);
					anim.setFillAfter(true);//动画完成后保持完成后的状态
					((ImageView) findViewById(R.id.iv_down_notes)).startAnimation(anim);
				}
				
				//添加顶部的view
				View headerView = LayoutInflater.from(getBaseContext()).inflate(R.layout.header_view, null);
				headerView.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						added = false;
						((LinearLayout) findViewById(R.id.ll_content)).removeViewAt(0);
						tv_allnotes.setText("全部笔记");
						state = 0;
						chapter = "全部笔记";
						//展开list
						if(lv_allnotes.getVisibility() == View.GONE){
							lv_allnotes.setVisibility(View.VISIBLE);
							Animation anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_up);
							anim.setFillAfter(true);//动画完成后保持完成后的状态
							((ImageView) findViewById(R.id.iv_down_notes)).startAnimation(anim);
						}
						fillListView();
					}
					
				});
				if(!added){
					((LinearLayout) findViewById(R.id.ll_content)).addView(headerView, 0);
					added = true;
				}
				//更改当前状态
				state = 1;
				chapter = ch.getName();
				lv_allnotes.setAdapter(new NoteListAdapter(getBaseContext(),li_notes));
			}
			
		});
		
		//点击笔记列表
		lv_allnotes.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				intent.setClass(ChapterActivity.this,CheckNoteActivity.class);
				Bundle b = new Bundle();
				b.putString("term", term);
				b.putString("cursor", cursorname);
				b.putString("chapter", chapter);
				b.putInt("state",state);
				b.putInt("current", arg2);
				intent.putExtras(b);
				startActivity(intent);
			}
			
		});
		
		//设置编辑框获取焦点事件
		et_empty.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				//丢失焦点
				if(!arg1){
				//隐藏键盘
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(et_empty.getWindowToken(), 0);
				}
				//获取焦点
				else{
					b_savenoteinlist.setVisibility(View.VISIBLE);
					
					ib_addchapter.setVisibility(View.INVISIBLE);
					ib_editchapter.setVisibility(View.INVISIBLE);
				}
			}
			
		});
		
		//保存笔记事件
		b_savenoteinlist.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String s = et_empty.getText().toString();
				
				et_empty.clearFocus();
				et_empty.setText("");
				
				b_savenoteinlist.setVisibility(View.GONE);
				
				ib_addchapter.setVisibility(View.VISIBLE);
				ib_editchapter.setVisibility(View.VISIBLE);
				
				if(s.length() == 0)
					return;
				
				//添加到“默认”章节
				if(state == 0){
					List<String> li = new ArrayList<String>();
					li.add(s);
					Chapter ch = new Chapter(term,cursorname,"默认",(ArrayList<String>) li);
					ch.save();
				}
				else{
					List<String> li = new ArrayList<String>();
					li.add(s);
					Chapter ch = new Chapter(term,cursorname,chapter,(ArrayList<String>) li);
					ch.save();
				}
				
				fillListView();
			}
			
		});
		
		//lv_allnotes.addFooterView(et_empty);
		lv_allnotes.setAdapter(new NoteListAdapter(getBaseContext(),li_notes));
		//lv_allnotes.setEmptyView(LayoutInflater.from(getBaseContext()).inflate(R.layout.listview_empty, null));
		lv_allchapters.setAdapter(new ChapterListAdapter(getBaseContext(),li_chapters));
		lv_allnotes.setEmptyView(et_empty);
//		setListViewHeight(lv_allnotes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chapter, menu);
		return true;
	}
	
	public void fillListView(){
		li_chapters = Cursor.getChapters(term, cursorname);
		li_notes = new ArrayList<Note>();
		for(Chapter ch:li_chapters){
			if(state == 0){
				List<String> notes = ch.getNotes();
				for(String s:notes){
					Note n = new Note(ch.getName(),s);
					li_notes.add(n);
				}
			}
			else{
				if(ch.getName().equals(chapter)){
					List<String> notes = ch.getNotes();
					for(String s:notes){
						Note n = new Note(ch.getName(),s);
						li_notes.add(n);
					}
				}
			}
		}
		lv_allnotes.setAdapter(new NoteListAdapter(getBaseContext(),li_notes));
		//lv_allnotes.setEmptyView(LayoutInflater.from(getBaseContext()).inflate(R.layout.listview_empty, null));
		lv_allchapters.setAdapter(new ChapterListAdapter(getBaseContext(),li_chapters));
	}
	
	private boolean chapterExist(String name){
		for(Chapter ch:li_chapters){
			if(name.equals(ch.getName()))
				return true;
		}
		return false;
	}
	
	public void setListViewHeight(ListView li){
		ListAdapter adapter = li.getAdapter();
		if(adapter == null)
			return;
		int totalHeight = 0;
		for(int i = 0 ; i < adapter.getCount(); i++){
			View item = adapter.getView(i, null, li);
			item.measure(0, 0);
			totalHeight += item.getMeasuredHeight();
		}
		if(totalHeight < getBaseContext().getResources().getDisplayMetrics().heightPixels * 0.6);{
			ViewGroup.LayoutParams params = li.getLayoutParams();
			params.height = totalHeight + (li.getDividerHeight() * (adapter.getCount() - 1));
			
			//li.setLayoutParams(params);
		}
	}

}
