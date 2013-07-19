package com.qingzhou.client;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 联系人弹出框使用
 * @author hihi
 *
 */
public class CMListAdapter extends BaseAdapter {
	
	private CharSequence[] rows;
	private Context ctx;
    private LayoutInflater mInflater;

	public CMListAdapter(Context context,CharSequence[]  rows)
	{
		ctx = context;
		this.rows = rows;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rows.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return rows[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = mInflater.inflate(R.layout.cm_list,null);
		ViewHolder viewHolder = new ViewHolder();
		
		viewHolder.cm_name = (TextView) convertView.findViewById(R.id.cm_name);
		
		
		convertView.setTag(viewHolder);
		
		viewHolder.cm_name.setText(rows[position]);
		
		return convertView;
	}
	
	static class ViewHolder { 
        public TextView cm_name;
    }
}
