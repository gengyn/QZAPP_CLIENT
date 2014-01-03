package com.qingzhou.app.widget;

import java.util.Observable;
import java.util.Observer;

import com.qingzhou.app.image.util.RecyclingBitmapDrawable;
import com.qingzhou.app.utils.Logger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class ZoomImageView extends ImageView implements Observer {

	/** Paint object used when drawing bitmap. */
	private final Paint mPaint = new Paint(Paint.FILTER_BITMAP_FLAG);

	/** Rectangle used (and re-used) for cropping source image. */
	private final Rect mRectSrc = new Rect();

	/** Rectangle used (and re-used) for specifying drawing area on canvas. */
	private final Rect mRectDst = new Rect();

	/** Object holding aspect quotient */
	private final AspectQuotient mAspectQuotient = new AspectQuotient();

	/** The bitmap that we're zooming in, and drawing on the screen. */
	private Bitmap mBitmap;

	/** State of the zoom. */
	private ZoomState mState;

	private BasicZoomControl mZoomControl;
	private BasicZoomListener mZoomListener;
	private OneClickListener oneClickListener;

	public void setOneClickListener(OneClickListener oneClickListener) {
		this.oneClickListener = oneClickListener;
	}

	public ZoomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mZoomControl = new BasicZoomControl();

		mZoomListener = new BasicZoomListener();
		mZoomListener.setZoomControl(mZoomControl, this);

		setZoomState(mZoomControl.getZoomState());

		setOnTouchListener(mZoomListener);

		mZoomControl.setAspectQuotient(getAspectQuotient());
	}
	
	@Override
    protected void onDetachedFromWindow() {
        // This has been detached from Window, so clear the drawable
        setImageDrawable(null);

        super.onDetachedFromWindow();
    }

	public void zoomImage(float f, float x, float y) {
		mZoomControl.zoom(f, x, y);
	}

	@Override
    public void setImageDrawable(Drawable drawable) {
        // Keep hold of previous Drawable
        final Drawable previousDrawable = getDrawable();

        // Call super to set new Drawable
        if (drawable != null)
        {
	        Bitmap tBitmap = ((BitmapDrawable)drawable).getBitmap();
	        if (tBitmap != null)
	        	setImage(tBitmap);
	        else 
	        	super.setImageDrawable(drawable);
        }
        else 
        	super.setImageDrawable(null);

        // Notify new Drawable that it is being displayed
        notifyDrawable(drawable, true);

        // Notify old Drawable so it is no longer being displayed
        notifyDrawable(previousDrawable, false);
    }

    /**
     * Notifies the drawable that it's displayed state has changed.
     *
     * @param drawable
     * @param isDisplayed
     */
    private static void notifyDrawable(Drawable drawable, final boolean isDisplayed) {
        if (drawable instanceof RecyclingBitmapDrawable) {
            // The drawable is a CountingBitmapDrawable, so notify it
            ((RecyclingBitmapDrawable) drawable).setIsDisplayed(isDisplayed);
        } else if (drawable instanceof LayerDrawable) {
            // The drawable is a LayerDrawable, so recurse on each layer
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            for (int i = 0, z = layerDrawable.getNumberOfLayers(); i < z; i++) {
                notifyDrawable(layerDrawable.getDrawable(i), isDisplayed);
            }
        }
    }
    
	public void setImage(Bitmap bitmap) {
		if (bitmap == null) return;
		mBitmap = bitmap;
		mAspectQuotient.updateAspectQuotient(getWidth(), getHeight(),
				mBitmap.getWidth(), mBitmap.getHeight());
		mAspectQuotient.notifyObservers();

		invalidate();
	}

	private void setZoomState(ZoomState state) {
		if (mState != null) {
			mState.deleteObserver(this);
		}

		mState = state;
		mState.addObserver(this);

		invalidate();
	}

	private AspectQuotient getAspectQuotient() {
		return mAspectQuotient;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// getParent().requestDisallowInterceptTouchEvent(true);
		if (mBitmap != null && mState != null) {
			final float aspectQuotient = mAspectQuotient.get();

			final int viewWidth = getWidth();
			final int viewHeight = getHeight();
			final int bitmapWidth = mBitmap.getWidth();
			final int bitmapHeight = mBitmap.getHeight();

			final float panX = mState.getPanX();
			final float panY = mState.getPanY();
			final float zoomX = mState.getZoomX(aspectQuotient) * viewWidth
					/ bitmapWidth;
			final float zoomY = mState.getZoomY(aspectQuotient) * viewHeight
					/ bitmapHeight;

			// Setup source and destination rectangles
			mRectSrc.left = (int) (panX * bitmapWidth - viewWidth / (zoomX * 2));
			mRectSrc.top = (int) (panY * bitmapHeight - viewHeight
					/ (zoomY * 2));
			mRectSrc.right = (int) (mRectSrc.left + viewWidth / zoomX);
			mRectSrc.bottom = (int) (mRectSrc.top + viewHeight / zoomY);
			mRectDst.left = getLeft();
			mRectDst.top = getTop();
			mRectDst.right = getRight();
			mRectDst.bottom = getBottom();

			// Adjust source rectangle so that it fits within the source image.
			if (mRectSrc.left < 0) {
				mRectDst.left += -mRectSrc.left * zoomX;
				mRectSrc.left = 0;
			}
			if (mRectSrc.right > bitmapWidth) {

				mRectDst.right -= (mRectSrc.right - bitmapWidth) * zoomX;
				mRectSrc.right = bitmapWidth;
			}
			if (mRectSrc.top < 0) {
				mRectDst.top += -mRectSrc.top * zoomY;
				mRectSrc.top = 0;
			}
			if (mRectSrc.bottom > bitmapHeight) {
				mRectDst.bottom -= (mRectSrc.bottom - bitmapHeight) * zoomY;
				mRectSrc.bottom = bitmapHeight;
			}

			canvas.drawBitmap(mBitmap, mRectSrc, mRectDst, mPaint);
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		if (mBitmap != null)
		{
			super.onLayout(changed, left, top, right, bottom);
			mAspectQuotient.updateAspectQuotient(getWidth(), getHeight(),
					mBitmap.getWidth(), mBitmap.getHeight());
	
			mAspectQuotient.notifyObservers();
		}
	}

	@Override
	public void update(Observable observable, Object data) {
		invalidate();
	}

	private class BasicZoomListener implements View.OnTouchListener {

		/** Zoom control to manipulate */
		private BasicZoomControl mZoomControl;

		private float mFirstX = -1;
		private float mFirstY = -1;
		private float mSecondX = -1;
		private float mSecondY = -1;

		private int mOldCounts = 0;
		boolean enable = true;

		private ZoomImageView zoomImageView;

		/**
		 * Sets the zoom control to manipulate
		 * 
		 * @param control
		 *            Zoom control
		 */
		public void setZoomControl(BasicZoomControl control,
				ZoomImageView zoomImageView) {
			mZoomControl = control;
			this.zoomImageView = zoomImageView;
			
		}

		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				enable = true;
				mOldCounts = 1;
				mFirstX = event.getX();
				mFirstY = event.getY();
				break;

			case MotionEvent.ACTION_MOVE: {
				enable = false;
				float fFirstX = event.getX();
				float fFirstY = event.getY();

				int nCounts = event.getPointerCount();

				if (1 == nCounts) {
					mOldCounts = 1;
					float dx = (fFirstX - mFirstX) / v.getWidth();
					float dy = (fFirstY - mFirstY) / v.getHeight();
				
					if (mState.getZoom() > 1 && !mZoomControl.isMaxRound()) {
						//tag = mState.getZoom();
						zoomImageView.getParent()
								.requestDisallowInterceptTouchEvent(true);
						
					}
					mZoomControl.pan(-dx, -dy);
				} else if (1 == mOldCounts) {
					mSecondX = event.getX(event.getPointerId(nCounts - 1));
					mSecondY = event.getY(event.getPointerId(nCounts - 1));
					mOldCounts = nCounts;
					zoomImageView.getParent()
							.requestDisallowInterceptTouchEvent(false);
				} else {
					zoomImageView.getParent()
							.requestDisallowInterceptTouchEvent(true);
					float fSecondX = event
							.getX(event.getPointerId(nCounts - 1));
					float fSecondY = event
							.getY(event.getPointerId(nCounts - 1));

					double nLengthOld = getLength(mFirstX, mFirstY, mSecondX,
							mSecondY);
					double nLengthNow = getLength(fFirstX, fFirstY, fSecondX,
							fSecondY);

					float d = (float) ((nLengthNow - nLengthOld) / v.getWidth());

					mZoomControl.zoom((float) Math.pow(20, d),
							((fFirstX + fSecondX) / 2 / v.getWidth()),
							((fFirstY + fSecondY) / 2 / v.getHeight()));

					mSecondX = fSecondX;
					mSecondY = fSecondY;
				}
				mFirstX = fFirstX;
				mFirstY = fFirstY;

				break;
			}
			case MotionEvent.ACTION_UP: {
				zoomImageView.getParent().requestDisallowInterceptTouchEvent(
						false);
				
				if (event.getPointerCount() == 1 && enable
						&& oneClickListener != null) {
					oneClickListener.onClick();
				}
				break;
			}

			}

			return true;
		}

		private double getLength(float x1, float y1, float x2, float y2) {
			return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
		}
	}

	protected class BasicZoomControl implements Observer {

		/** Minimum zoom level limit */
		private static final float MIN_ZOOM = 1;

		/** Maximum zoom level limit */
		private static final float MAX_ZOOM = 16;

		/** Zoom state under control */
		private final ZoomState mState = new ZoomState();

		/** Object holding aspect quotient of view and content */
		private AspectQuotient mAspectQuotient;
		
		private boolean isMaxRound = false;

		public boolean isMaxRound() {
			return isMaxRound;
		}

		/**
		 * Set reference object holding aspect quotient
		 * 
		 * @param aspectQuotient
		 *            Object holding aspect quotient
		 */
		public void setAspectQuotient(AspectQuotient aspectQuotient) {
			if (mAspectQuotient != null) {
				mAspectQuotient.deleteObserver(this);
			}

			mAspectQuotient = aspectQuotient;
			mAspectQuotient.addObserver(this);
		}

		/**
		 * Get zoom state being controlled
		 * 
		 * @return The zoom state
		 */
		public ZoomState getZoomState() {
			return mState;
		}

		/**
		 * Zoom
		 * 
		 * @param f
		 *            Factor of zoom to apply
		 * @param x
		 *            X-coordinate of invariant position
		 * @param y
		 *            Y-coordinate of invariant position
		 */
		public void zoom(float f, float x, float y) {

			// Log.d("Zoom", "zoom f = " + f);

			final float aspectQuotient = mAspectQuotient.get();

			final float prevZoomX = mState.getZoomX(aspectQuotient);
			final float prevZoomY = mState.getZoomY(aspectQuotient);

			mState.setZoom(mState.getZoom() * f);
			limitZoom();

			final float newZoomX = mState.getZoomX(aspectQuotient);
			final float newZoomY = mState.getZoomY(aspectQuotient);

			// Pan to keep x and y coordinate invariant
			mState.setPanX(mState.getPanX() + (x - .5f)
					* (1f / prevZoomX - 1f / newZoomX));
			mState.setPanY(mState.getPanY() + (y - .5f)
					* (1f / prevZoomY - 1f / newZoomY));

			limitPan();

			mState.notifyObservers();
		}

		/**
		 * Pan
		 * 
		 * @param dx
		 *            Amount to pan in x-dimension
		 * @param dy
		 *            Amount to pan in y-dimension
		 */
		public void pan(float dx, float dy) {
			final float aspectQuotient = mAspectQuotient.get();

			mState.setPanX(mState.getPanX() + dx
					/ mState.getZoomX(aspectQuotient));
			mState.setPanY(mState.getPanY() + dy
					/ mState.getZoomY(aspectQuotient));
			isMaxRound = false;
			limitPan();

			mState.notifyObservers();
		}

		/**
		 * Help function to figure out max delta of pan from center position.
		 * 
		 * @param zoom
		 *            Zoom value
		 * @return Max delta of pan
		 */
		private float getMaxPanDelta(float zoom) {
			return Math.max(0f, .5f * ((zoom - 1) / zoom));
		}

		/**
		 * Force zoom to stay within limits
		 */
		private void limitZoom() {
			if (mState.getZoom() < MIN_ZOOM) {
				mState.setZoom(MIN_ZOOM);
			} else if (mState.getZoom() > MAX_ZOOM) {
				mState.setZoom(MAX_ZOOM);
			}
		}

		/**
		 * Force pan to stay within limits
		 */
		private void limitPan() {
			final float aspectQuotient = mAspectQuotient.get();

			final float zoomX = mState.getZoomX(aspectQuotient);
			final float zoomY = mState.getZoomY(aspectQuotient);

			final float panMinX = .5f - getMaxPanDelta(zoomX);
			final float panMaxX = .5f + getMaxPanDelta(zoomX);
			final float panMinY = .5f - getMaxPanDelta(zoomY);
			final float panMaxY = .5f + getMaxPanDelta(zoomY);

			if (mState.getPanX() < panMinX) {
				mState.setPanX(panMinX);
				isMaxRound = true;
			}
			if (mState.getPanX() > panMaxX) {
				mState.setPanX(panMaxX);
				isMaxRound = true;
			}
			if (mState.getPanY() < panMinY) {
				mState.setPanY(panMinY);
			}
			if (mState.getPanY() > panMaxY) {
				mState.setPanY(panMaxY);
			}
		}

		// Observable interface implementation

		public void update(Observable observable, Object data) {
			limitZoom();
			limitPan();
		}
	}

	private class AspectQuotient extends Observable {

		/**
		 * Aspect quotient
		 */
		private float mAspectQuotient;

		// Public methods

		/**
		 * Gets aspect quotient
		 * 
		 * @return The aspect quotient
		 */
		public float get() {
			return mAspectQuotient;
		}

		/**
		 * Updates and recalculates aspect quotient based on supplied view and
		 * content dimensions.
		 * 
		 * @param viewWidth
		 *            Width of view
		 * @param viewHeight
		 *            Height of view
		 * @param contentWidth
		 *            Width of content
		 * @param contentHeight
		 *            Height of content
		 */
		public void updateAspectQuotient(float viewWidth, float viewHeight,
				float contentWidth, float contentHeight) {
			final float aspectQuotient = (contentWidth / contentHeight)
					/ (viewWidth / viewHeight);

			if (aspectQuotient != mAspectQuotient) {
				mAspectQuotient = aspectQuotient;
				setChanged();
			}
		}
	}

	private class ZoomState extends Observable {
		/**
		 * Zoom level A value of 1.0 means the content fits the view.
		 */
		private float mZoom;

		/**
		 * Pan position x-coordinate X-coordinate of zoom window center
		 * position, relative to the width of the content.
		 */
		private float mPanX;

		/**
		 * Pan position y-coordinate Y-coordinate of zoom window center
		 * position, relative to the height of the content.
		 */
		private float mPanY;

		// Public methods

		/**
		 * Get current x-pan
		 * 
		 * @return current x-pan
		 */
		public float getPanX() {
			return mPanX;
		}

		/**
		 * Get current y-pan
		 * 
		 * @return Current y-pan
		 */
		public float getPanY() {
			return mPanY;
		}

		/**
		 * Get current zoom value
		 * 
		 * @return Current zoom value
		 */
		public float getZoom() {
			return mZoom;
		}

		/**
		 * Help function for calculating current zoom value in x-dimension
		 * 
		 * @param aspectQuotient
		 *            (Aspect ratio content) / (Aspect ratio view)
		 * @return Current zoom value in x-dimension
		 */
		public float getZoomX(float aspectQuotient) {
			return Math.min(mZoom, mZoom * aspectQuotient);
		}

		/**
		 * Help function for calculating current zoom value in y-dimension
		 * 
		 * @param aspectQuotient
		 *            (Aspect ratio content) / (Aspect ratio view)
		 * @return Current zoom value in y-dimension
		 */
		public float getZoomY(float aspectQuotient) {
			return Math.min(mZoom, mZoom / aspectQuotient);
		}

		/**
		 * Set pan-x
		 * 
		 * @param panX
		 *            Pan-x value to set
		 */
		public void setPanX(float panX) {
			if (panX != mPanX) {
				mPanX = panX;
				setChanged();
			}
		}

		/**
		 * Set pan-y
		 * 
		 * @param panY
		 *            Pan-y value to set
		 */
		public void setPanY(float panY) {
			if (panY != mPanY) {
				mPanY = panY;
				setChanged();
			}
		}

		/**
		 * Set zoom
		 * 
		 * @param zoom
		 *            Zoom value to set
		 */
		public void setZoom(float zoom) {
			if (zoom != mZoom) {
				mZoom = zoom;
				setChanged();
			}
		}
	}

	public interface OneClickListener {
		public void onClick();
	}
}