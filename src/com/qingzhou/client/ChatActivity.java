package com.qingzhou.client;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qingzhou.app.utils.Logger;
import com.qingzhou.app.utils.StringUtils;
import com.qingzhou.client.MyMessageActivity.MessageReceiver;
import com.qingzhou.client.adapter.ChatMsgViewAdapter;
import com.qingzhou.client.common.Constants;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.domain.ChatMsg;
import com.qingzhou.client.domain.Interlocutor;
import com.qingzhou.client.domain.MessageLog;
import com.qingzhou.client.service.MessageService;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class ChatActivity extends Activity implements OnClickListener{
	private static final String TAG = "ChatActivity";

	private QcApp qcApp;
	private Button mBtnSend;
	private Button mBtnBack;
	private EditText mEditTextContent;
	private ListView mListView;
	private TextView mChat_name;
	private ChatMsgViewAdapter mAdapter;
	private RelativeLayout chat_layout;
	private List<ChatMsg> mDataArrays = new ArrayList<ChatMsg>();
	
	private String myMobile = "";//我的电话
	private String myName = "";//我的姓名
	private String oppositeMobile = "";//对方的电话
	private String oppositeName = "";//对方姓名
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting);
        qcApp = (QcApp)getApplication();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        
        myMobile = qcApp.getUserPhone();
        myName = qcApp.getAddressBook().get(myMobile);
        oppositeMobile = getIntent().getStringExtra("OPPOSITE");
        oppositeName = qcApp.getAddressBook().containsKey(oppositeMobile)?
				qcApp.getAddressBook().get(oppositeMobile):oppositeMobile;
        
        initView();
        initData();
        
        //接收消息的广播
        registerMessageReceiver();
      
    }
    
    @Override
	public void onResume() {
    	Logger.d(TAG, "chatActivity启动状态");
    	Constants.chatActivity_isBackground = false;
		super.onResume();
	}

	@Override
	public void onPause() {
		Logger.d(TAG, "chatActivity关闭状态");
		Constants.chatActivity_isBackground = true;
		super.onPause();
	}
	
	@Override
   	public void onDestroy() {
   		Logger.v(TAG, "onDestroy()");
   		unregisterReceiver(mMessageReceiver);

   		super.onDestroy();
    }
	
	//接收消息广播
    public static final String MESSAGE_RECEIVED_BYID_ACTION = "com.qingzhou.app.MESSAGE_RECEIVED_BYID_ACTION";
    private MessageReceiver mMessageReceiver;
    
    public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_BYID_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}
    
    public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_BYID_ACTION.equals(intent.getAction())) {
				ChatMsg chatMsg = (ChatMsg) intent.getSerializableExtra(Constants.KEY_MESSAGE);
				//Toast.makeText(getBaseContext(), "接收到消息"+chatMsg.getText()+"收件人为："+myMobile, Toast.LENGTH_LONG).show();
				if (chatMsg.getSender().equals(oppositeMobile))
				{
					receiverMsg(chatMsg);
				}

			}
		}
	}
    
    public void initView()
    {
    	mListView = (ListView) findViewById(R.id.listview);
    	mBtnSend = (Button) findViewById(R.id.btn_send);
    	mBtnSend.setOnClickListener(this);
    	mBtnBack = (Button) findViewById(R.id.btn_back);
    	mBtnBack.setOnClickListener(this);
    	mChat_name = (TextView)findViewById(R.id.chat_name);
    	mChat_name.setText(oppositeName);
    	
    	mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
    	chat_layout = (RelativeLayout) findViewById(R.id.chat_layout);
    	chat_layout.setClickable(true);
    	chat_layout.setOnClickListener(new OnClickListener()
    	{
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), "点击背景", Toast.LENGTH_LONG).show();
				getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
			}
    		
    	});
    }
    
   /**
    * 初始化对话数据
    */
    public void initData()
    {
    	MessageService messageService = new MessageService(this);
    	List<MessageLog> messageList = messageService.listMyMessage(myMobile, oppositeMobile);
    	
    	for(int i = 0; i < messageList.size(); i++)
    	{
    		MessageLog msg = messageList.get(i);
    		ChatMsg entity = new ChatMsg();
    		if (msg.getSender_mobile().equals(myMobile))
    		{
    			entity.setSenderName(myName);
    			entity.setComMeg(true);
    		}
    		else 
    		{
    			entity.setSenderName(oppositeName);
    			entity.setComMeg(false);
    		}
    		
    		entity.setDate(msg.getMsg_time());
    		entity.setText(msg.getMsg_content());
    		entity.setImg_url(msg.getImg_url());
    		entity.setVoice_url(msg.getVoice_url());

    		mDataArrays.add(entity);
    	}

    	mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
		
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.btn_send:
			send();
			break;
		case R.id.btn_back:
			finish();
			break;
		}
	}
	
	/**
	 * 发送消息
	 */
	private void send()
	{
		//频率控制
		if (Constants.LAST_MYMESSAGE != -1 && 
				System.currentTimeMillis() - Constants.LAST_MYMESSAGE < Constants.LIMIT_MYMESSAGE )
			Toast.makeText(getBaseContext(), "发送频率过快", Toast.LENGTH_SHORT).show();
		else
		{
		//---------------------------------------------
		
			String contString = mEditTextContent.getText().toString().trim();
			if (contString.length() > 0)
			{
				ChatMsg entity = new ChatMsg();
				entity.setDate(StringUtils.getCurDate());
				entity.setSender(myMobile);
				entity.setSenderName(myName);
				entity.setComMeg(true);
				entity.setText(contString);
				entity.setReceiver(oppositeMobile);
				entity.setImg_url("");
				entity.setVoice_url("");
				
				mDataArrays.add(entity);
				mAdapter.notifyDataSetChanged();
				
				mEditTextContent.setText("");
				
				mListView.setSelection(mListView.getCount() - 1);
				
				//保存消息，发送消息
				MessageService messageService = new MessageService(this);
				messageService.sendMessage(entity);
				//频率控制
				Constants.LAST_MYMESSAGE = System.currentTimeMillis();
			}
			
		}
	}
	
	/**
	 * 收到消息
	 * @param entity
	 */
	public void receiverMsg(ChatMsg entity)
	{
		entity.setSenderName(oppositeName);
		entity.setDate(StringUtils.getCurDate());
		entity.setComMeg(false);
		//设置为已读
		MessageService msgService = new MessageService(this);
		msgService.setReadFlag(oppositeMobile);
		
		mDataArrays.add(entity);
		mAdapter.notifyDataSetChanged();
		mListView.setSelection(mListView.getCount() - 1);
	}
    
}