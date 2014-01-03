package com.qingzhou.client.adapter;

import java.util.List;

import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.R;
import com.qingzhou.client.domain.MaterialInventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * 主材清单行适配器
 * @author hihi
 *
 */
public class MaterialInventoryListViewAdapter extends BaseAdapter {
	
	private List<MaterialInventory> rows;
	private Context ctx;
    private LayoutInflater mInflater;

	public MaterialInventoryListViewAdapter(Context context,List<MaterialInventory> materialList)
	{
		ctx = context;
		this.rows = materialList;
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
		MaterialInventory entity = rows.get(position);
		convertView = mInflater.inflate(R.layout.mycontract_materialinventory_list,null);
		ViewHolder viewHolder = new ViewHolder();
		
		viewHolder.materialName = (TextView) convertView.findViewById(R.id.imaterialName);
		viewHolder.materialSort = (TextView) convertView.findViewById(R.id.imaterialSort);
		viewHolder.materialBrand = (TextView) convertView.findViewById(R.id.imaterialBrand);
		viewHolder.materialSalePrice = (TextView) convertView.findViewById(R.id.imaterialSalePrice);
		viewHolder.mcontractPrice = (TextView) convertView.findViewById(R.id.mcontractPrice);
		viewHolder.mchangePrice = (TextView) convertView.findViewById(R.id.mchangePrice);
		viewHolder.mpracticalPrice = (TextView) convertView.findViewById(R.id.mpracticalPrice);
		convertView.setTag(viewHolder);
		
		
		if (StringUtils.isEmpty(entity.getFlag()))
		{
			viewHolder.materialName.setText(getSpec(entity.getMaterialName(),entity.getMaterialSpec(),entity.getMaterialModel(),"-"));
			if (!StringUtils.isEmpty(entity.getMaterialSort()))
				viewHolder.materialSort.setText(entity.getMaterialSort());
			if (!StringUtils.isEmpty(entity.getMaterialBrand()))
				viewHolder.materialBrand.setText(String.format(ctx.getResources().
						getString(R.string.brand_title),entity.getMaterialBrand()));
			if (!StringUtils.isEmpty(entity.getMaterialSalePrice()))
			{
				viewHolder.materialSalePrice.setText(String.format(ctx.getResources().
						getString(R.string.unit_price_title),entity.getMaterialSalePrice()+"/"+entity.getMaterialUnit()));
			}
			viewHolder.mcontractPrice.setText(String.format(ctx.getResources().
					getString(R.string.contracePrice_title),StringUtils.formatDecimal(entity.getContractPrice())));
			viewHolder.mchangePrice.setText(String.format(ctx.getResources().
					getString(R.string.changePrice_title),StringUtils.formatDecimal(entity.getChangePrice())));
			if (entity.getChangePrice().indexOf("-") != -1)
			{
				viewHolder.mchangePrice.setTextColor(ctx.getResources().getColor(R.color.green));
			}else if (Double.parseDouble(entity.getChangePrice()) >1 )
			{
				viewHolder.mchangePrice.setTextColor(ctx.getResources().getColor(R.color.red));
			}
			viewHolder.mpracticalPrice.setText(String.format(ctx.getResources().
					getString(R.string.practicalPrice_title),StringUtils.formatDecimal(entity.getPracticalPrice())));
		
		}else if (entity.getFlag().equals("1"))//是优惠
		{
			viewHolder.materialName.setText(entity.getMaterialName());
			viewHolder.materialSalePrice.setText(String.format(ctx.getResources().
					getString(R.string.favorable_title),StringUtils.formatDecimal(entity.getChangePrice())));
			viewHolder.materialSalePrice.setTextColor(ctx.getResources().getColor(R.color.green));
		}
		return convertView;
	}
	
	/**
	 * 组合项目描述
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
        public TextView materialName;
        public TextView materialSort;
        public TextView materialBrand;
        public TextView materialSalePrice;
        public TextView mcontractPrice;
        public TextView mchangePrice;
        public TextView mpracticalPrice;
        
    }
}
