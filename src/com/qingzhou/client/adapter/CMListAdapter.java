package com.qingzhou.client.adapter;

import java.util.List;

import com.qingzhou.client.R;
import com.qingzhou.client.R.id;
import com.qingzhou.client.R.layout;
import com.qingzhou.client.R.string;
import com.qingzhou.client.domain.UserBase;

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
	
	private UserBase user;
	private Context ctx;
    private LayoutInflater mInflater;

	public CMListAdapter(Context context,UserBase user)
	{
		ctx = context;
		this.user = user;
		mInflater = LayoutInflater.from(context);
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView = mInflater.inflate(R.layout.project_exchang_list,null);
		ViewHolder viewHolder = new ViewHolder();
		
		viewHolder.stylist = (TextView) convertView.findViewById(R.id.stylist);
		viewHolder.project_mgr = (TextView) convertView.findViewById(R.id.project_mgr);
		viewHolder.customer_mgr = (TextView) convertView.findViewById(R.id.customer_mgr);
		
		
		convertView.setTag(viewHolder);
		
		viewHolder.stylist.setText(String.format(ctx.getResources().getString(R.string.stylist)
				,user.getReg_stylist_name()));
		viewHolder.project_mgr.setText(String.format(ctx.getResources().getString(R.string.project_mgr)
				,user.getReg_project_mgr_name()));
		viewHolder.customer_mgr.setText(String.format(ctx.getResources().getString(R.string.customer_mgr)
				,user.getReg_customer_mgr_name()));
		
		return convertView;
	}
	
	static class ViewHolder { 
        public TextView stylist;
        public TextView project_mgr;
        public TextView customer_mgr;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}



	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return user;
	}



	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}
