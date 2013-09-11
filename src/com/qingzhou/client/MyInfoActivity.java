package com.qingzhou.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.qingzhou.client.MyInfoViewAdapter;
import com.qingzhou.client.common.GlobalParameter;
import com.qingzhou.client.common.QcApp;
import com.qingzhou.client.common.RestService;
import com.qingzhou.client.domain.Myinfo;
import com.qingzhou.client.util.HttpUtils;
import com.qingzhou.client.util.StringUtils;
import com.qingzhou.client.util.ThreadPoolUtils;
import com.qingzhou.client.widget.PullToRefreshListView;

/**
 * 资讯活动
 * 
 * @author hihi
 * 
 */
public class MyInfoActivity extends Activity {

	private QcApp qcApp;
	private List<Myinfo> mInfo = new ArrayList<Myinfo>();
	private View info_footer;
	private TextView info_foot_more;
	private ProgressBar info_foot_progress;
	private PullToRefreshListView infoListView;
	private Handler infoHandler;
	private MyInfoViewAdapter infoAdapter;
	
	private int pageSize = GlobalParameter.PAGESIZE;//默认分页每页行数
	private int pageNo = 1;//默认页数
	
	private static final int INFO_MORE = 0x01;//更多
	private static final int INFO_EMPTY = 0x02;//为空
	private static final int INFO_LOADING = 0x06;//正在加载标识
	private static final int INFO_INIT = 0x03;//初始化标识
	private static final int INFO_REFRESH = 0x04;//下拉更新标识
	private static final int INFO_ROLL = 0x05;//滚动更新标识
	
	private int INFO_STATUS = INFO_INIT;//当前状态，默认为初始化

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.myinfo);
		qcApp = (QcApp)getApplication();
		initInfoList();
	}

	
	/**
	 * 初始化资讯
	 */
	private void initInfoList() {
				
		infoListView = (PullToRefreshListView) findViewById(R.id.info_list);
		
		//底部刷新栏，用于分页加载更多数据
		info_footer = getLayoutInflater().inflate(R.layout.listview_footer,	null);
		info_foot_more = (TextView)info_footer.findViewById(R.id.listview_foot_more);
		info_foot_progress = (ProgressBar) info_footer.findViewById(R.id.listview_foot_progress);
		infoListView.addFooterView(info_footer);
		
		infoAdapter = new MyInfoViewAdapter(MyInfoActivity.this, mInfo);
		infoListView.setAdapter(infoAdapter);
		
		//加载数据
		infoHandler = this.getInfoHandler();
		if (mInfo.isEmpty())
			loadInfoData();
				
		

		//加入单击事件
		infoListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// 点击头部、底部栏无效
				if (arg2 == 0 || arg1 == info_footer)
					return;
				
				Intent intent = new Intent (MyInfoActivity.this,MyInfoContentActivity.class);	
				Myinfo myinfo = (Myinfo)mInfo.get(arg2-1);
				intent.putExtra("MYINFO", myinfo);
				startActivity(intent);			
			}

		});
		infoListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				infoListView.onScrollStateChanged(view, scrollState);

				// 数据为空--不用继续下面代码了
				if (mInfo == null || mInfo.size() == 0)
					return;

				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(info_footer) == view
							.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}

				if (scrollEnd && StringUtils.toInt(infoListView.getTag()+"") == INFO_MORE)
				{
					infoListView.setTag(INFO_LOADING);
					info_foot_more.setText(R.string.load_ing);
					info_foot_progress.setVisibility(View.VISIBLE);
					INFO_STATUS = INFO_ROLL;
					loadInfoData();
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				infoListView.onScroll(view, firstVisibleItem, visibleItemCount,
						totalItemCount);
			}
		});
		infoListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			public void onRefresh() {
				//下拉刷新
				//infoListView.setTag(INFO_LOADING);
				INFO_STATUS = INFO_REFRESH;
				loadInfoData();
			}
		});
	}
	
	
	
	/**
	 * 加载资讯数据
	 */
	private List<Myinfo> getInfoList()
	{
		HttpUtils httpUtil = new HttpUtils();
		String myinfoJson = "";
		List<Myinfo> newInfo = new ArrayList<Myinfo>();
		try {
			myinfoJson = httpUtil.httpGetExecute(RestService.GET_MYINFO_URL+qcApp.getUserBase().getCustomer_id()+
					"/" + pageNo + "/" + pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!StringUtils.isNull(myinfoJson))
			newInfo = JSONArray.parseArray(myinfoJson,Myinfo.class);
		
		return newInfo;
	}
	
	/**
	 * 获取比上次请求数据更新的资讯
	 * @return
	 */
	private List<Myinfo> getLastInfoList()
	{
		List<Myinfo> testList = new ArrayList<Myinfo>();
		Myinfo myinfo = new Myinfo();
		myinfo.setInfo_title("测试顺序");
		myinfo.setInfo_date("2013-09-16");
		testList.add(myinfo);
		return testList;
	}
	
	
	/**
	 * 调用线程，加载资讯数据
	 */
	private void loadInfoData() {
		
		ThreadPoolUtils.execute(loadInfoRunnable);
	}
	
	/**
	 * 开启线程
	 */
	private Runnable loadInfoRunnable = new Runnable() {	
		@Override
		public void run() {
			Message msg = new Message();

			try {
				List<Myinfo> newList = new ArrayList<Myinfo>();
				switch(INFO_STATUS)
		        {
			        case INFO_INIT:          //初始化
			        	newList = getInfoList();
			        	break;
			        case INFO_ROLL:         //滚动刷新
			        	pageNo ++;//页数加1
						newList = getInfoList();
						break;
			        case INFO_REFRESH:		//下拉刷新
			        	newList = getLastInfoList();
			        	break;
		        }
				msg.what = newList.size();
				msg.obj = newList;
			} catch (Exception e) {
				e.printStackTrace();
				msg.what = -1;
				msg.obj = e;
			}
			infoHandler.sendMessage(msg);
			
		}
	};
	
	/**
	 * 回调
	 * @return
	 */
	private Handler getInfoHandler() {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					// listview数据处理
					handleData(msg);
					if (msg.what < pageSize) {
						infoListView.setTag(INFO_EMPTY);
						infoAdapter.notifyDataSetChanged();
						info_foot_more.setText(R.string.load_empty);
					} else if (msg.what == pageSize) {
						infoListView.setTag(INFO_MORE);
						infoAdapter.notifyDataSetChanged();
						info_foot_more.setText(R.string.load_more);
					}
						
				} else if (msg.what == -1) {
					// 有异常--显示加载出错 & 弹出错误消息
					infoListView.setTag(INFO_MORE);
					info_foot_more.setText(R.string.load_error);
				}
				if (infoAdapter.getCount() == 0) {
					infoListView.setTag(INFO_EMPTY);
					info_foot_more.setText(R.string.load_empty);
				}
				info_foot_progress.setVisibility(ProgressBar.GONE);
				infoListView.onRefreshComplete();
				infoListView.setSelection(0);

			}
		};
	}
	
	/**
	 * listview数据处理
	 */
	private void handleData(Message msg)
	{
		switch(INFO_STATUS)
        {
	        case INFO_INIT:          //初始化
				mInfo.addAll((List<Myinfo>)msg.obj);
	        	break;
	        case INFO_ROLL:         //滚动刷新
	        	//追加
	        	mInfo.addAll((List<Myinfo>)msg.obj);
				break;
	        case INFO_REFRESH:		//下拉刷新
	        	if (msg.what == 0)
	        		Toast.makeText(getBaseContext(), 
	        				getResources().getString(R.string.info_data_toast_none), Toast.LENGTH_SHORT).show();
	        	else if (msg.what > 0)
	        	{
		        	mInfo.addAll(0,(List<Myinfo>)msg.obj);
		        	Toast.makeText(getBaseContext(), 
		        			String.format(getResources().getString(R.string.info_data_toast_message),msg.what), Toast.LENGTH_SHORT).show();
	        	}
	        	break;
        }
	}
	

}
