package com.qingzhou.client.adapter;

import java.util.List;

import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.R;
import com.qingzhou.client.domain.BaseDetail;
import com.qingzhou.client.domain.MainDetail;

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
public class MainDetailListViewAdapter extends BaseAdapter {
	
	private List<MainDetail> rows;
	private Context ctx;
    private LayoutInflater mInflater;

	public MainDetailListViewAdapter(Context context,List<MainDetail> rows)
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
		MainDetail entity = rows.get(position);
		convertView = mInflater.inflate(R.layout.myhome_maindetail_list,null);
		ViewHolder viewHolder = new ViewHolder();
		
		viewHolder.materialSpec = (TextView) convertView.findViewById(R.id.materialSpec);
		viewHolder.materialBrand = (TextView) convertView.findViewById(R.id.materialBrand);
		viewHolder.materialSalePrice = (TextView) convertView.findViewById(R.id.materialSalePrice);
		viewHolder.materialcount = (TextView) convertView.findViewById(R.id.materialcount);
		viewHolder.materialPrice = (TextView) convertView.findViewById(R.id.materialPrice);
		viewHolder.materialStatus = (TextView) convertView.findViewById(R.id.materialStatus);
		convertView.setTag(viewHolder);
		
		viewHolder.materialSpec.setText(getSpec(entity.getMaterialName(),entity.getMaterialSpec(),entity.getMaterialModel(),"-"));
		viewHolder.materialBrand.setText(String.format(ctx.getResources().
				getString(R.string.brand_title),entity.getMaterialBrand()));
		viewHolder.materialSalePrice.setText(String.format(ctx.getResources().
				getString(R.string.unit_price_title),StringUtils.formatDecimal(entity.getMaterialSalePrice())+"/"+entity.getMaterialUnit()));
		viewHolder.materialcount.setText(String.format(ctx.getResources().
				getString(R.string.count_title),entity.getMaterialcount()));
		viewHolder.materialPrice.setText(String.format(ctx.getResources().
				getString(R.string.price_title),StringUtils.formatDecimal(entity.getMaterialPrice())));
		viewHolder.materialStatus.setText(String.format(ctx.getResources().
				getString(R.string.main_status),entity.getMaterialStatus()));
		
		return convertView;
	}
	
	/**
	 * 返回项目描述
	 * @param name
	 * @param spec
	 * @param model
	 * @param separator
	 * @return
	 */
	private String getSpec(String name,String spec,String model,String separator)
	{
		return StringUtils.emptyStringIfNull(name)
				+ separator
				+ StringUtils.emptyStringIfNull(spec)
				+ separator
				+ StringUtils.emptyStringIfNull(model);
	}
	
	static class ViewHolder { 
        public TextView materialSpec;
        public TextView materialBrand;
        public TextView materialSalePrice;
        public TextView materialcount;
        public TextView materialPrice;
        public TextView materialStatus;
        
    }
}
