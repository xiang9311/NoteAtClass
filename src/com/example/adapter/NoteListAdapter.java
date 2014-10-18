package com.example.adapter;

import java.util.List;

import com.example.noteatclass.R;
import com.example.obj.Note;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoteListAdapter extends BaseAdapter {

	private Context context;
	private List<Note> notes;
	
	public NoteListAdapter(Context context , List<Note> notes){
		this.context = context;
		this.notes = notes;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return notes.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return notes.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	static class ViewHolder{
		int flag = -1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null|| (holder = (ViewHolder) convertView.getTag()).flag != position){
			holder = new ViewHolder();
			holder.flag = position;
			
			Log.d("getView","="+position);
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item_note, null);
			TextView tv_chaptername = (TextView) convertView.findViewById(R.id.tv_linote_chaptername);
			TextView tv_note = (TextView) convertView.findViewById(R.id.tv_linote_note);
			
			tv_chaptername.setText(notes.get(position).getChaptername());
			tv_note.setText(notes.get(position).getContent());
			
			convertView.setTag(holder);
		}
		
		return convertView;
	}

}
