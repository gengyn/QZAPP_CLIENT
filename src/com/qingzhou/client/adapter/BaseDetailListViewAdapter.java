package com.qingzhou.client.adapter;

import java.util.List;

import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.R;
import com.qingzhou.client.domain.BaseDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * 基础明细行适配器
 * @author hihi
 *
 */
public class BaseDetailListViewAdapter extends BaseAdapter {
	
	private List<BaseDetail> rows;
	private Context ctx;
    private LayoutInflater mInflater;

	public BaseDetailListViewAdapter(Context context,List<BaseDetail> rows)
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
		BaseDetail entity = rows.get(position);
		convertView = mInflater.inflate(R.layout.myhome_basedetail_list,null);
		ViewHolder viewHolder = new ViewHolder();
		
		viewHolder.basName = (TextView) convertView.findViewById(R.id.basName);
		viewHolder.basePrice = (TextView) convertView.findViewById(R.id.basePrice);
		viewHolder.practicalcount = (TextView) convertView.findViewById(R.id.practicalcount);
		viewHolder.practicalprice = (TextView) convertView.findViewById(R.id.practicalprice);
		
		convertView.setTag(viewHolder);
		
		viewHolder.basName.setText(entity.getBasName());
		viewHolder.basePrice.setText(String.format(ctx.getResources().
				getString(R.string.unit_price_title),StringUtils.formatDecimal(entity.getBasePrice())+"/"+entity.getBasUnit()));
		viewHolder.practicalcount.setText(String.format(ctx.getResources().
				getString(R.string.count_title),entity.getPracticalcount()));
		viewHolder.practicalprice.setText(String.format(ctx.getResources().
				getString(R.string.price_title),StringUtils.formatDecimal(entity.getPracticalprice())));
		
		return convertView;
	}
	
	static class ViewHolder { 
        public TextView basName;
        public TextView basePrice;
        public TextView practicalcount;
        public TextView practicalprice;
        
    }
}
