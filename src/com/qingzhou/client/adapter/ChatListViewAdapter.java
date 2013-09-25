package com.qingzhou.client.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.R;
import com.qingzhou.client.R.color;
import com.qingzhou.client.R.id;
import com.qingzhou.client.R.layout;
import com.qingzhou.client.domain.Interlocutor;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 对话人列表适配器
 * @author hihi
 *
 */
public class ChatListViewAdapter extends BaseAdapter {
	
	private List<Interlocutor> rows;
	private Context ctx;
    private LayoutInflater mInflater;
    private Map<String,String> addressBook;//通讯簿

	public ChatListViewAdapter(Context context,List<Interlocutor> rows, Map<String,String> addressBook)
	{
		this.ctx = context;
		if (rows == null)
			this.rows = new ArrayList<Interlocutor>();
		else
			this.rows = rows;
		this.addressBook = addressBook;
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
		Interlocutor entity = rows.get(position);
		convertView = mInflater.inflate(R.layout.chatlist_item,null);
		ViewHolder viewHolder = new ViewHolder();
		
		viewHolder.chat_head = (ImageView) convertView.findViewById(R.id.chat_head);
		viewHolder.chat_opposite = (TextView) convertView.findViewById(R.id.chat_opposite);
		viewHolder.chat_lasttime = (TextView) convertView.findViewById(R.id.chat_lasttime);
		viewHolder.chat_lastmsg = (TextView) convertView.findViewById(R.id.chat_lastmsg);
		viewHolder.chat_row = (RelativeLayout)convertView.findViewById(R.id.chat_row);
		convertView.setTag(viewHolder);
		
		//如通讯录中不存在，则显示具体号码
		viewHolder.chat_opposite.setText(
				addressBook.containsKey(entity.getI_mobile())?addressBook.get(entity.getI_mobile()):entity.getI_mobile());
		viewHolder.chat_lasttime.setText(entity.getLast_time());
		viewHolder.chat_lastmsg.setText(entity.getLast_message());
		if (entity.getIsreaded().equals("0"))
		{
			//如消息未读取，字体加粗
			viewHolder.chat_opposite.getPaint().setFakeBoldText(true);
			viewHolder.chat_lasttime.getPaint().setFakeBoldText(true);
			viewHolder.chat_lastmsg.getPaint().setFakeBoldText(true);
			viewHolder.chat_row.setBackgroundColor(ctx.getResources().getColor(R.color.beige));
		}
			
		
		return convertView;
	}
	
	static class ViewHolder { 
        
        public ImageView chat_head;
        public TextView chat_opposite;
        public TextView chat_lasttime;
        public TextView chat_lastmsg;
        public RelativeLayout chat_row;
        
    }
	
		
	
}
