package com.qingzhou.app.photo;

import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qingzhou.app.image.ui.RecyclingImageView;
import com.qingzhou.app.image.util.ImageFetcher;
import com.qingzhou.app.image.util.ImageCache.ImageCacheParams;
import com.qingzhou.client.R;
import com.qingzhou.client.domain.Images;
import com.qingzhou.client.domain.RestProjectPhoto;

/**
 * 图片查看Fragment
 * @author hihi
 *
 */
@SuppressLint("ValidFragment")
public class PhotoFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String TAG = "PhotoFragment";
    private static final String IMAGE_CACHE_DIR = "thumbs";
    private static final String BIGIMAGE_CACHE_DIR = "images";

    private int mImageThumbSize;
    private int mImageThumbSpacing;
    private ImageAdapter mAdapter;
    private ImageFetcher mImageFetcher;
    private ImageFetcher mBigImageFetcher;
    private TextView count;
    private ImageView photo_imageView;


    public PhotoFragment() {
    	
    }
    /**
     * 初始化,传入图片地址
     * @param photoList
     */
    public PhotoFragment(List<RestProjectPhoto> photoList) {
    	Images.setImageUrls(photoList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSize = 100;
        mImageThumbSpacing = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);

        mAdapter = new ImageAdapter(getActivity());
        
        ImageCacheParams cacheParams = new ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);
        
        cacheParams.setMemCacheSizePercent(0.25f); // Set memory cache to 25% of app memory

        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
        //mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);
        
        
      //获取屏幕的分辨率，用于设置图片的宽带和高度
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int height = displayMetrics.heightPixels;
        final int width = displayMetrics.widthPixels;
        
        final int longest = (height > width ? height : width) / 2;
        ImageCacheParams cacheParamsBig = new ImageCacheParams(getActivity(), BIGIMAGE_CACHE_DIR);
        cacheParamsBig.setMemCacheSizePercent(0.25f);
        mBigImageFetcher = new ImageFetcher(getActivity(), longest);
        mBigImageFetcher.setImageFadeIn(false);
        //mBigImageFetcher.setLoadingImage(R.drawable.ic_launcher);
        mBigImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParamsBig);
               
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.projectplan_photo, container, false);
        count = (TextView) v.findViewById(R.id.count);
        photo_imageView = (ImageView)v.findViewById(R.id.photo_imageView);
        final GridView mGridView = (GridView) v.findViewById(R.id.myGrid);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
        mGridView.setNumColumns(Images.getImageUrls().size());
        //动态设定GridView的宽度
        mGridView.setLayoutParams(new LinearLayout.LayoutParams (
        		mImageThumbSize*Images.getImageUrls().size()+mImageThumbSpacing*Images.getImageUrls().size(),mImageThumbSize));

        mGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    mImageFetcher.setPauseWork(true);
                } else {
                    mImageFetcher.setPauseWork(false);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
            }
        });

        /**
         * 默认显示第一张
         */
        if (Images.getImageUrls().size()> 0)
        	defaultShow();
        
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mImageFetcher.setExitTasksEarly(false);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        mImageFetcher.setPauseWork(false);
        mImageFetcher.setExitTasksEarly(true);
        mImageFetcher.flushCache();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImageFetcher.closeCache();
    }

    /**
     * 缩略图点击事件
     */
    @TargetApi(16)
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    	
    	count.setText(position + 1 +"/"+Images.getImageUrls().size());
    	mBigImageFetcher.loadImage(getResources().getString(R.string.project_photo_url)
				+Images.getImageUrls().get(position).getFile_path().replace("\\", "/"), photo_imageView);
    }
    
    /**
     * 默认显示第一张
     */
    public void defaultShow()
    {
    	count.setText( 1 +"/"+Images.getImageUrls().size());
    	
    	mBigImageFetcher.loadImage(getResources().getString(R.string.project_photo_url)
				+Images.getImageUrls().get(0).getFile_path().replace("\\", "/"), photo_imageView);
    }

    /**
     * 缩略图适配器
     
     */
    private class ImageAdapter extends BaseAdapter {

        private final Context mContext;
        private GridView.LayoutParams mImageViewLayoutParams;

        public ImageAdapter(Context context) {
            super();
            mContext = context;
            mImageViewLayoutParams = new GridView.LayoutParams(
            		mImageThumbSize, mImageThumbSize);
           
        }

        @Override
        public int getCount() {
            // Size + number of columns for top empty row
            return Images.getImageUrls().size();
        }

        @Override
        public Object getItem(int position) {
            return mContext.getResources().getString(R.string.project_photo_url)
    				+Images.getImageUrls().get(position).getFile_path().replace("\\", "/");
        }

        @Override
        public long getItemId(int position) {
            return position ;
        }

       
        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            
            ImageView imageView;
            if (convertView == null) { 
                imageView = new RecyclingImageView(mContext);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setLayoutParams(mImageViewLayoutParams);
            } else { // Otherwise re-use the converted view
                imageView = (ImageView) convertView;
            }
            //默认图片
            imageView.setBackgroundResource(R.drawable.ic_launcher);

            //加载图片
            mImageFetcher.setLoadingImage(R.drawable.empty_photo);
            mImageFetcher.loadImage(mContext.getResources().getString(R.string.project_photo_url)
    				+Images.getImageUrls().get(position).getFile_path().replace("\\", "/"), imageView);
            return imageView;
        }

    }
}
