package com.qingzhou.client;

import java.util.List;

import com.qingzhou.client.domain.Myinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 行碎片
 * @author hihi
 *
 */
public class MyInfoViewAdapter extends BaseAdapter {
	
	public List<Myinfo> rows;
	private Context ctx;
    private LayoutInflater mInflater;

	public MyInfoViewAdapter(Context context,List<Myinfo> rows)
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
		Myinfo entity = rows.get(position);
		convertView = mInflater.inflate(R.layout.infolist_item,null);
		ViewHolder viewHolder = new ViewHolder();
		
		viewHolder.info_title = (TextView) convertView.findViewById(R.id.info_title);
		viewHolder.info_date = (TextView) convertView.findViewById(R.id.info_date);
		viewHolder.info_type = (TextView) convertView.findViewById(R.id.info_type);
		viewHolder.info_flag = (ImageView) convertView.findViewById(R.id.info_flag);
		
		convertView.setTag(viewHolder);
		
		viewHolder.info_title.setText(entity.getInfo_title());
		viewHolder.info_date.setText(entity.getInfo_date());
		viewHolder.info_type.setText(entity.getInfo_type());
		if (entity.getToday_flag() == 1)
			viewHolder.info_flag.setVisibility(View.VISIBLE);
		
		return convertView;
	}
	
	static class ViewHolder { 
        public TextView info_title;
        public TextView info_date;
        public TextView info_type;
        public ImageView info_flag;
    }
}
