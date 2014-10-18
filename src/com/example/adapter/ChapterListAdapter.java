package com.example.adapter;

import java.util.List;

import com.example.noteatclass.R;
import com.example.obj.Chapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChapterListAdapter extends BaseAdapter {
	
	private Context context;
	private List<Chapter> chapters;
	
	public ChapterListAdapter(Context context, List<Chapter> chapters){
		this.context = context;
		this.chapters = chapters;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return chapters.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return chapters.get(position);
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
		if(convertView == null || (holder = (ViewHolder)convertView.getTag()).flag != position){
			holder = new ViewHolder();
			holder.flag = position;
			Chapter chapter = chapters.get(position);
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item_chapter, null);
			TextView tv_chaptername = (TextView) convertView.findViewById(R.id.tv_li_chaptername);
			TextView tv_notesnum = (TextView) convertView.findViewById(R.id.tv_chapter_notesnum);
			
			tv_chaptername.setText(chapter.getName());
			tv_notesnum.setText(String.valueOf(chapter.getNotes().size()));
			
			convertView.setTag(holder);
		}
		return convertView;
	}

}
