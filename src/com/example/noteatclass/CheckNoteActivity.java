package com.example.noteatclass;

import java.util.ArrayList;
import java.util.List;

import com.example.obj.Chapter;
import com.example.obj.Note;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class CheckNoteActivity extends BaseActivity implements ViewSwitcher.ViewFactory{
	
	private EditText et_editnotes;
	private TextSwitcher ts_notes;
	private TextView tv_chaptername_notes;
	private Button b_shang;
	private Button b_xia;
	private Button b_savenote;
	private ImageButton ib_editnote;
	private ImageButton ib_addnote;
	private Button b_savenewnote;
	private TextView tv_current;
	
	private String term;
	private String cursor;
	private String chapter;
	private int state;//如果是0，则是当前note对应章节,如果是1，则是对应chapter，
	private int liststate;
	private int currentNote;
	private List<Note> notes; //传递过来的列表信息
	
	private float downx = 0;
	
	private int editstate;
	private int _EDIT = 0;
	private int _EDITNEW = 1;
	private int _CHECK = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_note);
		
		Bundle b = getIntent().getExtras();
		term = b.getString("term");
		cursor = b.getString("cursor");
		chapter = b.getString("chapter");
		state = b.getInt("state");
		currentNote = b.getInt("current");
		//liststate = b.getInt("liststate",0);
		notes = ChapterActivity.that.li_notes;
		
		//绑定各个控件
		et_editnotes = (EditText) findViewById(R.id.et_editnotes);
		ts_notes = (TextSwitcher) findViewById(R.id.ts_notes);
		tv_chaptername_notes = (TextView) findViewById(R.id.tv_chaptername_notes);
		b_shang = (Button) findViewById(R.id.b_shang);
		b_xia = (Button) findViewById(R.id.b_xia);
		b_savenote = (Button) findViewById(R.id.b_savenote);
		ib_editnote = (ImageButton) findViewById(R.id.ib_editnote);
		ib_addnote = (ImageButton) findViewById(R.id.ib_addnote);
		b_savenewnote = (Button) findViewById(R.id.b_savenewnote);
		tv_current = (TextView) findViewById(R.id.tv_current);
		
		
		//初始化一些变量
		ts_notes.setFactory(this);
		editstate = _CHECK;
		
		ts_notes.setText(notes.get(currentNote).getContent());
		tv_chaptername_notes.setText(notes.get(currentNote).getChaptername());
		tv_current.setText(currentNote+1+"/"+notes.size());
		
		//事件处理
		((ImageButton) findViewById(R.id.ib_notes_back)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				checkState();
				finish();
			}
			
		});
		
		((RelativeLayout) findViewById(R.id.rl_switch)).setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(editstate != _CHECK){
					return false;
				}
				switch (event.getAction()){
				case MotionEvent.ACTION_DOWN:
					downx = event.getX();
					
					break;
				
				case MotionEvent.ACTION_UP:
					float x = event.getX();
					if(Math.abs(downx - x) > 30){
						if(downx > x){
							ts_notes.setInAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_in_right));
							ts_notes.setOutAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_out_left));
							currentNote = (++currentNote)%notes.size();
							ts_notes.setText(notes.get(currentNote).getContent());
							tv_chaptername_notes.setText(notes.get(currentNote).getChaptername());
							tv_current.setText(currentNote+1+"/"+notes.size());
						}
						else{
							ts_notes.setInAnimation(AnimationUtils.loadAnimation(getBaseContext(), android.R.anim.slide_in_left));
							ts_notes.setOutAnimation(AnimationUtils.loadAnimation(getBaseContext(), android.R.anim.slide_out_right));
							currentNote = (--currentNote + notes.size())%notes.size();
							ts_notes.setText(notes.get(currentNote).getContent());
							tv_chaptername_notes.setText(notes.get(currentNote).getChaptername());
							tv_current.setText(currentNote+1+"/"+notes.size());
						}
					}
					break;
				}
				return true;
			}
			
		});
		
		b_shang.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				currentNote = (--currentNote + notes.size())%notes.size();
				ts_notes.setInAnimation(AnimationUtils.loadAnimation(getBaseContext(), android.R.anim.slide_in_left));
				ts_notes.setOutAnimation(AnimationUtils.loadAnimation(getBaseContext(), android.R.anim.slide_out_right));
				ts_notes.setText(notes.get(currentNote).getContent());
				tv_chaptername_notes.setText(notes.get(currentNote).getChaptername());
				tv_current.setText(currentNote+1+"/"+notes.size());
			}
			
		});
		
		b_shang.setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				currentNote = 0;
				ts_notes.setInAnimation(AnimationUtils.loadAnimation(getBaseContext(), android.R.anim.slide_in_left));
				ts_notes.setOutAnimation(AnimationUtils.loadAnimation(getBaseContext(), android.R.anim.slide_out_right));
				ts_notes.setText(notes.get(currentNote).getContent());
				tv_chaptername_notes.setText(notes.get(currentNote).getChaptername());
				tv_current.setText(currentNote+1+"/"+notes.size());
				return true;
			}
			
		});
		
		b_xia.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				ts_notes.setInAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_in_right));
				ts_notes.setOutAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_out_left));
				currentNote = (++currentNote)%notes.size();
				ts_notes.setText(notes.get(currentNote).getContent());
				tv_chaptername_notes.setText(notes.get(currentNote).getChaptername());
				tv_current.setText(currentNote+1+"/"+notes.size());
			}
			
		});
		
		b_xia.setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				currentNote = notes.size() - 1;
				ts_notes.setInAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_in_right));
				ts_notes.setOutAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.slide_out_left));
				ts_notes.setText(notes.get(currentNote).getContent());
				tv_chaptername_notes.setText(notes.get(currentNote).getChaptername());
				tv_current.setText(currentNote+1+"/"+notes.size());
				return true;
			}
			
		});
		
		//编辑当前note
		ib_editnote.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//五个需要隐藏的控件，一个需要屏蔽的事件
				editstate = _EDIT;
				ib_editnote.setVisibility(View.INVISIBLE);
				ib_addnote.setVisibility(View.INVISIBLE);
				ts_notes.setVisibility(View.INVISIBLE);
				b_shang.setVisibility(View.INVISIBLE);
				b_xia.setVisibility(View.INVISIBLE);
				
				et_editnotes.setVisibility(View.VISIBLE);
				et_editnotes.setText(notes.get(currentNote).getContent());
				
				
				b_savenote.setVisibility(View.VISIBLE);
				et_editnotes.requestFocus(View.FOCUS_RIGHT);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(et_editnotes, InputMethodManager.SHOW_FORCED);
				
			}
			
		});
		
		b_savenote.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				saveNote();
			}	
		});
		
		ib_addnote.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//五个需要隐藏的控件，一个需要屏蔽的事件
				editstate = _EDITNEW;
				ib_editnote.setVisibility(View.INVISIBLE);
				ib_addnote.setVisibility(View.INVISIBLE);
				ts_notes.setVisibility(View.INVISIBLE);
				b_shang.setVisibility(View.INVISIBLE);
				b_xia.setVisibility(View.INVISIBLE);
				
				et_editnotes.setVisibility(View.VISIBLE);
				et_editnotes.setText("");
				
				
				b_savenewnote.setVisibility(View.VISIBLE);
				et_editnotes.requestFocus(View.FOCUS_RIGHT);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(et_editnotes, InputMethodManager.SHOW_FORCED);
			}
			
		});
		
		b_savenewnote.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				saveNewNote();
			}
		});
	}
	
	private void saveNote(){
		ts_notes.clearAnimation();
		String s = et_editnotes.getText().toString();
		//五个需要显示的控件，一个需要屏蔽的事件
		editstate = _CHECK;
		ib_editnote.setVisibility(View.VISIBLE);
		ib_addnote.setVisibility(View.VISIBLE);
		ts_notes.setVisibility(View.VISIBLE);
		b_shang.setVisibility(View.VISIBLE);
		b_xia.setVisibility(View.VISIBLE);
		
		//隐藏键盘
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et_editnotes.getWindowToken(), 0);
		et_editnotes.setVisibility(View.GONE);
		b_savenote.setVisibility(View.INVISIBLE);
		
		if(s.length()==0)
			return;
		
		if(state == 1){
			List<String> l = new ArrayList<String>();
			String theChaptername = notes.get(currentNote).getChaptername();
			for(Note n:notes){
				l.add(n.getContent());
			}
			l.remove(currentNote);
			l.add(currentNote, s);
			Chapter ch = new Chapter(term,cursor,theChaptername,(ArrayList<String>)l);
			ch.save();
			
			//通知各个列表改变内容
			ChapterActivity.that.fillListView();
			notes = ChapterActivity.that.li_notes;
			ts_notes.clearAnimation();
			ts_notes.setText(notes.get(currentNote).getContent());
		}
		else{
			List<String> l = new ArrayList<String>();
			String theChaptername = notes.get(currentNote).getChaptername();
			int count = 0;
			for(Chapter ch:ChapterActivity.that.li_chapters){
				if(ch.getName().equals(theChaptername)){
					int order = currentNote - count;
					l = ch.getNotes();
					l.remove(order);
					l.add(order, s);
					Chapter cch = new Chapter(term,cursor,theChaptername,(ArrayList<String>)l);
					cch.save();
					
					//通知各个列表改变内容
					ChapterActivity.that.fillListView();
					notes = ChapterActivity.that.li_notes;
					ts_notes.clearAnimation();
					ts_notes.setText(notes.get(currentNote).getContent());
				}
				count += ch.getNotes().size();
			}
		}
		
		tv_current.setText(currentNote+1+"/"+notes.size());

	}
	
	private void saveNewNote(){
		String s = et_editnotes.getText().toString();
		//五个需要显示的控件，一个需要屏蔽的事件
		editstate = _CHECK;
		ib_editnote.setVisibility(View.VISIBLE);
		ib_addnote.setVisibility(View.VISIBLE);
		ts_notes.setVisibility(View.VISIBLE);
		b_shang.setVisibility(View.VISIBLE);
		b_xia.setVisibility(View.VISIBLE);
		
		//隐藏键盘
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(et_editnotes.getWindowToken(), 0);
		et_editnotes.setVisibility(View.GONE);
		b_savenewnote.setVisibility(View.INVISIBLE);
		
		
		
		if(s.length() == 0)
			return;
		
		if(state == 1){
			List<String> l = new ArrayList<String>();
			String theChaptername = notes.get(currentNote).getChaptername();
			for(Note n:notes){
				l.add(n.getContent());
			}
			l.add(s);
			Chapter ch = new Chapter(term,cursor,theChaptername,(ArrayList<String>)l);
			ch.save();
			
			//通知各个列表改变内容
			ChapterActivity.that.fillListView();
			notes = ChapterActivity.that.li_notes;
			ts_notes.clearAnimation();
			ts_notes.setText(notes.get(currentNote).getContent());
		}
		else{
			List<String> l = new ArrayList<String>();
			String theChaptername = notes.get(currentNote).getChaptername();
			for(Chapter ch:ChapterActivity.that.li_chapters){
				if(ch.getName().equals(theChaptername)){
					l = ch.getNotes();
					l.add(s);
					Chapter cch = new Chapter(term,cursor,theChaptername,(ArrayList<String>)l);
					cch.save();
					
					//通知各个列表改变内容
					ChapterActivity.that.fillListView();
					notes = ChapterActivity.that.li_notes;
					ts_notes.clearAnimation();
					ts_notes.setText(notes.get(currentNote).getContent());
				}
			}
		}
		tv_current.setText(currentNote+1+"/"+notes.size());
	}
	
	private void checkState(){
		if(editstate == _EDIT){
			saveNote();
		}
		else if(editstate == _EDITNEW){
			saveNewNote();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_note, menu);
		return true;
	}

	@Override
	public View makeView() {
		// TODO Auto-generated method stub
		TextView tv = new TextView(this);
		tv.setTextSize(20);
		return tv;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			checkState();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	

}
