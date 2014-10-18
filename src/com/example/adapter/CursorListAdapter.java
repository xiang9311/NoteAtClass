package com.example.adapter;

import java.util.List;

import com.example.adapter.ChapterListAdapter.ViewHolder;
import com.example.noteatclass.R;
import com.example.obj.Cursor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CursorListAdapter extends BaseAdapter {
	
	private Context context;
	private List<Cursor> list;
	
	public CursorListAdapter(Context context, List<Cursor> list){
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
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
			Cursor cursor = list.get(position);
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item_cursor, null);
			TextView tv_cursorname = (TextView) convertView.findViewById(R.id.tv_li_cursor_name);
			TextView tv_teachername = (TextView) convertView.findViewById(R.id.tv_li_teacher_name);
			
//			if(cursor.getTeacher().getName().equals(""))
//				tv_teachername.setVisibility(View.GONE);
				
			tv_cursorname.setText(cursor.getName());
			tv_teachername.setText(cursor.getTeacher().getName());
			
			convertView.setTag(holder);
		}
		return convertView;
	}

}
