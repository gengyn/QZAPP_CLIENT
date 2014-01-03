package com.qingzhou.client.adapter;

import java.util.List;

import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.R;
import com.qingzhou.client.domain.BaseInventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * 基础清单行适配器
 * @author hihi
 *
 */
public class BaseInventoryListViewAdapter extends BaseAdapter {
	
	private List<BaseInventory> rows;
	private Context ctx;
    private LayoutInflater mInflater;

	public BaseInventoryListViewAdapter(Context context,List<BaseInventory> rows)
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
		BaseInventory entity = rows.get(position);
		convertView = mInflater.inflate(R.layout.mycontract_baseinventory_list,null);
		ViewHolder viewHolder = new ViewHolder();
		
		viewHolder.basName = (TextView) convertView.findViewById(R.id.basName);
		viewHolder.basePrice = (TextView) convertView.findViewById(R.id.basePrice);
		viewHolder.contracePrice = (TextView) convertView.findViewById(R.id.contracePrice);
		viewHolder.changePrice = (TextView) convertView.findViewById(R.id.changePrice);
		viewHolder.practicalPrice = (TextView) convertView.findViewById(R.id.practicalPrice);
		convertView.setTag(viewHolder);
		
		viewHolder.basName.setText(entity.getBasName());
		if (StringUtils.isEmpty(entity.getFlag()))
		{
			if (!StringUtils.isEmpty(entity.getBasePrice()))
			{
				viewHolder.basePrice.setText(String.format(ctx.getResources().
						getString(R.string.unit_price_title),entity.getBasePrice()+"/"+entity.getBasUnit()));
			}
			viewHolder.contracePrice.setText(String.format(ctx.getResources().
					getString(R.string.contracePrice_title),StringUtils.formatDecimal(entity.getContracePrice())));
			viewHolder.changePrice.setText(String.format(ctx.getResources().
					getString(R.string.changePrice_title),StringUtils.formatDecimal(entity.getChangePrice())));
			if (entity.getChangePrice().indexOf("-") != -1)
			{
				viewHolder.changePrice.setTextColor(ctx.getResources().getColor(R.color.green));
			}else if (Double.parseDouble(entity.getChangePrice()) >1 )
			{
				viewHolder.changePrice.setTextColor(ctx.getResources().getColor(R.color.red));
			}
			viewHolder.practicalPrice.setText(String.format(ctx.getResources().
					getString(R.string.practicalPrice_title),StringUtils.formatDecimal(entity.getPracticalPrice())));
		
		}else if (entity.getFlag().equals("1"))//是优惠
		{
			viewHolder.changePrice.setText(String.format(ctx.getResources().
					getString(R.string.favorable_title),StringUtils.formatDecimal(entity.getChangePrice())));
			viewHolder.changePrice.setTextColor(ctx.getResources().getColor(R.color.green));
		}
		return convertView;
	}
	

	static class ViewHolder { 
        public TextView basName;
        public TextView basePrice;
        public TextView contracePrice;
        public TextView changePrice;
        public TextView practicalPrice;
        
    }
}
