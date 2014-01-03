package com.qingzhou.client.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.R;
import com.qingzhou.client.R.id;
import com.qingzhou.client.R.layout;
import com.qingzhou.client.R.string;
import com.qingzhou.client.domain.ContractDiscount;

/**
 * 优惠信息行碎片
 * @author hihi
 *
 */
public class FavorableViewAdapter extends BaseAdapter {
	
	private List<ContractDiscount> rows;
	private Context ctx;
    private LayoutInflater mInflater;

	public FavorableViewAdapter(Context context,List<ContractDiscount> rows)
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
		ContractDiscount entity = rows.get(position);
		convertView =  mInflater.inflate(R.layout.favorable_list,null);
		ViewHolder viewHolder = new ViewHolder();
		
		viewHolder.discount_type_name = (TextView) convertView.findViewById(R.id.discount_type_name);
		viewHolder.discount_mode_name = (TextView) convertView.findViewById(R.id.discount_mode_name);
		viewHolder.discount_content = (TextView) convertView.findViewById(R.id.discount_content);
		viewHolder.item_value = (TextView) convertView.findViewById(R.id.item_value);
		viewHolder.f_count = (TextView)convertView.findViewById(R.id.f_count);
		convertView.setTag(viewHolder);
		
		viewHolder.discount_type_name.setText(entity.getDiscount_type_name());
		viewHolder.discount_mode_name.setText(entity.getDiscount_mode_name());
		//优惠方式为打折时
		if (entity.getDiscount_mode().equals("4"))
			viewHolder.discount_content.setText(StringUtils.formatDiscount(entity.getDiscount_content()));
		else viewHolder.discount_content.setText(entity.getDiscount_content());
		//优惠方式为赠送时
		if (entity.getDiscount_mode().equals("2"))
		{
			viewHolder.discount_content.setText(entity.getDiscount_content()+"("+StringUtils.formatDecimal(entity.getItem_value())+")");
			viewHolder.item_value.setText(StringUtils.formatDecimal("0"));
		}
		else
			viewHolder.item_value.setText(StringUtils.formatDecimal(entity.getItem_value()));
		viewHolder.f_count.setText(String.format(ctx.getResources().getString(R.string.favorable_title),position+1+""));
		
		return convertView;
	}
	
	static class ViewHolder { 
        public TextView f_count;
		public TextView discount_type_name;
        public TextView discount_mode_name;
        public TextView discount_content;
        public TextView item_value;
    }
}
