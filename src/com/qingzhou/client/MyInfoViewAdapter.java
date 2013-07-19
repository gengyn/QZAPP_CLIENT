package com.qingzhou.client;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 行碎片
 * @author hihi
 *
 */
public class MyInfoViewAdapter extends BaseAdapter {
	
	private List<MyInfoEntity> rows;
	private Context ctx;
    private LayoutInflater mInflater;

	public MyInfoViewAdapter(Context context,List<MyInfoEntity> rows)
	{
		ctx = context;
		this.rows = rows;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rows.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return rows.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyInfoEntity entity = rows.get(position);
		convertView = mInflater.inflate(R.layout.infolist,null);
		ViewHolder viewHolder = new ViewHolder();
		
		viewHolder.info_title = (TextView) convertView.findViewById(R.id.info_title);
		viewHolder.info_date = (TextView) convertView.findViewById(R.id.info_date);
		
		convertView.setTag(viewHolder);
		
		viewHolder.info_title.setText(entity.getInfo_title());
		viewHolder.info_date.setText(entity.getInfo_date());
		
		return convertView;
	}
	
	static class ViewHolder { 
        public TextView info_title;
        public TextView info_date;
    }
}
