package com.luoye.demo.mybrowser.web.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class Mywebview extends WebView {

	private OnoverscolledListener onoverscolledListener;
	
	public Mywebview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	 



	public interface OnoverscolledListener{
		void onochanged(boolean isover);
	}

	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
		// TODO Auto-generated method stub

			onoverscolledListener.onochanged(clampedX); 
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
	 }
	
	public void setOnoverscolledListener(OnoverscolledListener listener){
		 		onoverscolledListener= listener;  
	 }  


}
