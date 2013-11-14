package com.qingzhou.app.utils;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import com.qingzhou.app.utils.FileUtils;
import com.qingzhou.client.common.Constants;

/**
 * 获取图片工具类
 * @author hihi
 *
 */
public class PictureShowUtils {
	
	static String dirPath = Constants.CACHE_DIR;
	private static PictureShowUtils pictureShowUtils;
	private static final int FINISH_MESSAGE = 0x01;
	private static final int ERROR_MESSAGE = 0x02;
	
	private Context context;
	private String fileurl;
	private Bitmap bitPhoto;
	private ImageView imageView;
	/**
	 * 返回图片,先中本地缓存中找，如果没有则从网上下载，然后保存到缓存
	 * @param filename
	 * @return
	 */
	
	public PictureShowUtils(Context context)
	{
		this.context = context.getApplicationContext();
	}
	
	public static PictureShowUtils from(Context context)
	{
		// 如果不在ui线程中，则抛出异常
		if (Looper.myLooper() != Looper.getMainLooper()) {
			throw new RuntimeException("Cannot instantiate outside UI thread.");
		}
		if (pictureShowUtils == null)
			pictureShowUtils = new PictureShowUtils(context.getApplicationContext());
		
		return pictureShowUtils;
	}
	
	public Bitmap getImage(String fileurl)
	{
		//如果目录不存在则创建
		FileUtils.mkDir(dirPath);
		String filename = fileurl.substring(fileurl.lastIndexOf("/")+1);
		Bitmap bmap = null;
		boolean isOk = true;
		if (!FileUtils.isExists(dirPath + filename))
		{
			//网络获取图片，保存在缓存目录
			InputStream is = HttpUtils.getImageView(fileurl);
			isOk = FileUtils.writeFile(dirPath,filename,is);
		}
		
		if (isOk) bmap = BitmapFactory.decodeFile(dirPath+filename);
		return bmap;
	}
	
	public void displayImage(ImageView imageView,String fileurl)
	{
		//开启线程并执行
		this.imageView = imageView;
		this.fileurl = fileurl.replace("\\", "/");
        ThreadPoolUtils.execute(mRunnable);
	}
	
	/**
	 * 具体执行方法
	 */
	private Runnable mRunnable = new Runnable() {	
		@Override
		public void run() {
			try {
				bitPhoto = getImage(fileurl);
		    	mHandler.sendEmptyMessage(FINISH_MESSAGE);

			}  catch(Exception e){
				mHandler.sendEmptyMessage(ERROR_MESSAGE);
				e.printStackTrace();
			}
			
		}
	};
	
	/**
	 * 处理
	 */
	private Handler mHandler = new Handler(){
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
			case FINISH_MESSAGE:
				imageView.setImageBitmap(bitPhoto);
				break;
			case ERROR_MESSAGE:
				Toast.makeText(context, "图片加载失败，请重试", Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
    	};
    };
	
}
