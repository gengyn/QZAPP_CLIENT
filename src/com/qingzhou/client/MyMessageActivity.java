package com.qingzhou.client;


import java.util.ArrayList;
import java.util.List;

import com.qingzhou.app.utils.DialogUtils;
import com.qingzhou.app.utils.Logger;
import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.ChatActivity;
import com.qingzhou.client.adapter.ChatListViewAdapter;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.domain.ChatMsg;
import com.qingzhou.client.domain.Interlocutor;
import com.qingzhou.client.service.MessageService;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 我的消息Activity
 * @author hihi
 *
 */
public class MyMessageActivity extends BaseActivity {
	
	private static final String TAG = "MyMessageActivity";
	private QcApp qcApp;
	public static MyMessageActivity instance = null;
	private ListView mListView;
	private List<Interlocutor> mChatList = new ArrayList<Interlocutor>();
	private ChatListViewAdapter mAdapter;
	private Button btn_addressbook;
	private TextView nodata;
	private ImageButton btn_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymessage);
        qcApp = (QcApp)getApplication();
        instance = this;
        
      //close button
		btn_back = (ImageButton) this.findViewById(R.id.btn_back);
		btn_back.setOnClickListener(new Button.OnClickListener(){//创建监听    
		      public void onClick(View v) {    
		    	  MyMessageActivity.this.finish();
		      }    
		});
        
        mListView = (ListView) findViewById(R.id.chat_list);
        initChatList();
        initAddressBook();
      //接收消息的广播
        registerMessageReceiver();
    }
    
    @Override
   	public void onDestroy() {
   		Logger.v(TAG, "onDestroy()");
   		unregisterReceiver(mMessageReceiver);

   		super.onDestroy();
    }
    
    @Override
	public void onResume() {
    	Logger.d(TAG, "mymessageActivity启动状态");
    	Constants.mymessageActivity_isBackground = false;
    	//重新加载列表
    	reLoadChat();
		super.onResume();
	}

	@Override
	public void onPause() {
		Logger.d(TAG, "mymessageActivity关闭状态");
		Constants.mymessageActivity_isBackground = true;
		super.onPause();
	}
    
    //接收消息广播
    public static final String MESSAGE_RECEIVED_ACTION = "com.qingzhou.app.MESSAGE_RECEIVED_ACTION";
    private MessageReceiver mMessageReceiver;
    
    public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}
    
    public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
				ChatMsg chatMsg = (ChatMsg) intent.getSerializableExtra(Constants.KEY_MESSAGE);
				//Toast.makeText(getBaseContext(), "接收到消息"+chatMsg.getText(), Toast.LENGTH_LONG).show();
				//重新加载
				reLoadChat();
				
			}
		}
	}
    
    /**
     * 重新加载对话人列表
     */
    private void reLoadChat()
    {
    	Logger.d(TAG, "重新加载列表");
    	List<Interlocutor> reList = listChat();
    	
    	mChatList.clear();
    	mChatList.addAll(reList);
    	mAdapter.notifyDataSetChanged();
    }

    /**
     * 查询联络人列表
     */
    private List<Interlocutor> listChat()
    {
    	MessageService messageService = new MessageService(this);
    	return messageService.listInterlocutor(qcApp.getUserPhone());
    }
    /**
     * 初始化对话人列表
     */
	private void initChatList()
	{
		mAdapter = new ChatListViewAdapter(this,mChatList,qcApp.getAddressBook());
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(getBaseContext(), "selectd:" +arg2 +" id:"+arg3, Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				intent.putExtra("OPPOSITE", mChatList.get(arg2).getI_mobile());
				intent.putExtra("OPPOSITENAME", mChatList.get(arg2).getI_name());
				intent.setClass(MyMessageActivity.this, ChatActivity.class);
				startActivity(intent);
				
			}
		});
		mListView.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				showItemDelDialog(MyMessageActivity.this, qcApp.getUserPhone(),mChatList.get(arg2).getI_mobile());
				return false;
			}
			
		});
		
//		mListView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {  
//
//			@Override
//			public void onCreateContextMenu(ContextMenu menu, View v,
//					ContextMenuInfo menuInfo) {
//				// TODO Auto-generated method stub
//				menu.setHeaderTitle("长按菜单-ContextMenu");     
//                menu.add(0, 0, 0, "弹出长按菜单0");  
//                menu.add(0, 1, 0, "弹出长按菜单1");
//			}  
//        });   
	}
	
//	@Override  
//    public boolean onContextItemSelected(MenuItem item) {  
//        setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目");   
//        return super.onContextItemSelected(item);  
//    }  
	
	public void showItemDelDialog(final Context context,final String my_mobile,final String other_mobile) {  
		AlertDialog.Builder builder = new Builder(context);  
		builder.setMessage("确定删除吗?");  
		builder.setTitle("提示");  
		builder.setPositiveButton("确定", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				MessageService messageService = new MessageService(context);
				messageService.delMessage(my_mobile,other_mobile);
				reLoadChat();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();				
			}
		});
        
        builder.create().show();  
    }  
	
	
	//----------------联系人相关-------------------
	public void initAddressBook()
	{
		btn_addressbook = (Button)findViewById(R.id.btn_addressbook);
		
		btn_addressbook.setOnClickListener(new View.OnClickListener() {  
			            @Override  
			            public void onClick(View arg0) {  
			            	start_exchange();
			            }  
			        });  
	}
	
	/**
	 * 工程联络人点击事件
	 */
	private void start_exchange()
	{
		DialogUtils.showAddressBookDialog(MyMessageActivity.this, qcApp.getUserBase());
	}
	
	public void stylist_phone_onclick(View v)
	{
		DialogUtils.action_call(MyMessageActivity.this,qcApp.getUserBase().getReg_stylist_mobile());
	}
	
	public void project_mgr_phone_onclick(View v)
	{
		DialogUtils.action_call(MyMessageActivity.this,qcApp.getUserBase().getReg_project_mgr_mobile());
	}
	
	public void customer_mgr_phone_onclick(View v)
	{
		DialogUtils.action_call(MyMessageActivity.this,qcApp.getUserBase().getReg_customer_mgr_mobile());
	}
	
	public void stylist_message_onclick(View v)
	{
		DialogUtils.toMessage(MyMessageActivity.this,qcApp.getUserBase().getReg_stylist_mobile());
	}
	
	public void project_mgr_message_onclick(View v)
	{
		DialogUtils.toMessage(MyMessageActivity.this,qcApp.getUserBase().getReg_project_mgr_mobile());
	}
	
	public void customer_mgr_message_onclick(View v)
	{
		DialogUtils.toMessage(MyMessageActivity.this,qcApp.getUserBase().getReg_customer_mgr_mobile());
	}
}
    
    

