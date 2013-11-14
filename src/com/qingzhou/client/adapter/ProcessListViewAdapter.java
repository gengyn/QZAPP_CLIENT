package com.qingzhou.client.adapter;

import java.util.List;

import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.LoadingActivity;
import com.qingzhou.client.R;
import com.qingzhou.client.R.color;
import com.qingzhou.client.R.drawable;
import com.qingzhou.client.R.id;
import com.qingzhou.client.R.layout;
import com.qingzhou.client.R.string;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.domain.RestProjectPlanDetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 工程进度行碎片
 * @author hihi
 *
 */
public class ProcessListViewAdapter extends BaseAdapter {
	
	private List<RestProjectPlanDetail> rows;
	private Context ctx;
    private LayoutInflater mInflater;

	public ProcessListViewAdapter(Context context,List<RestProjectPlanDetail> rows)
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
		RestProjectPlanDetail entity = rows.get(position);
		convertView = mInflater.inflate(R.layout.processlist,null);
		ViewHolder viewHolder = new ViewHolder();
		
		viewHolder.process_name = (TextView) convertView.findViewById(R.id.process_name);
		viewHolder.process_logo = (ImageView) convertView.findViewById(R.id.process_logo);
		viewHolder.process_date = (TextView) convertView.findViewById(R.id.process_date);
		viewHolder.process_status = (TextView) convertView.findViewById(R.id.process_status);
		viewHolder.process_photo = (TextView) convertView.findViewById(R.id.process_photo);
		viewHolder.list_row = (RelativeLayout) convertView.findViewById(R.id.list_row);
		convertView.setTag(viewHolder);
		
		viewHolder.process_name.setText(entity.getProject_process_name());
		if (!StringUtils.isEmpty(entity.getSche_start_project()))
		{
			viewHolder.process_date.setText(String.format(ctx.getResources().
					getString(R.string.process_date),entity.getSche_start_project(),entity.getSche_end_project()));
		}
		viewHolder.process_photo.setText(entity.getPhotocount()>0?entity.getPhotocount()+"":"");
		//如果有图片，则显示图片的图标，并且加上点击事件
		if (entity.getPhotocount()>0)
		{
			viewHolder.process_logo.setImageResource(R.drawable.preview);
			final String schedetail_id = entity.getSchedetail_id();
			viewHolder.process_logo.setOnClickListener(new View.OnClickListener() {  
			            @Override  
			            public void onClick(View arg0) {  
			            	photoClick(schedetail_id);
			            }  
			        });  
		}
			
		initProcessStatus(viewHolder,entity.getProject_process_status());
		return convertView;
	}
	
	static class ViewHolder { 
        public TextView process_name;
        public ImageView process_logo;
        public TextView process_date;
        public TextView process_status;
        public TextView process_photo;
        public RelativeLayout list_row;
        
    }
	
	/**
	 * 返回进程状态
	 * @param entity
	 * @return
	 */
	private void initProcessStatus(ViewHolder viewHolder,int status)
	{
		switch(status)
        {
        case Constants.PROCESS_END:
        	viewHolder.process_status.setText(ctx.getResources().getString(R.string.process_end));
        	viewHolder.process_status.setTextColor(ctx.getResources().getColor(R.color.green));
        	//viewHolder.process_logo.setImageResource(R.drawable.process_end);
        	break;
        case Constants.PROCESS_GOING:
        	viewHolder.process_status.setText(ctx.getResources().getString(R.string.process_going));
        	//viewHolder.process_logo.setImageResource(R.drawable.process_going);
        	viewHolder.list_row.setBackgroundColor(ctx.getResources().getColor(R.color.orange));
        	break;
        case Constants.PROCESS_NOSTART:
        	viewHolder.process_status.setText(ctx.getResources().getString(R.string.process_nostart));
        	//viewHolder.process_logo.setImageResource(R.drawable.process_nostart);
        	break;
        default:
        	viewHolder.process_status.setText("未知");
        }
	}
	
	/**
	 * 查看图片
	 * @param v
	 */
	public void photoClick(String schedetail_id)
	{
		//Toast.makeText(ctx, "查看图片"+schedetail_id, Toast.LENGTH_LONG).show();
		Intent intent = new Intent();
		intent.putExtra("schedetail_id", schedetail_id);
		intent.putExtra("FLAG", Constants.SHOW_PHOTO);
		intent.setClass(ctx,LoadingActivity.class);
		ctx.startActivity(intent);
		
	}
}
