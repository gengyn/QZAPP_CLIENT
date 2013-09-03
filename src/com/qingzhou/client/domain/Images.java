package com.qingzhou.client.domain;

import java.util.List;



public class Images {

  
    public static List<RestProjectPhoto> imageUrls ;

	public static List<RestProjectPhoto> getImageUrls() {
		return imageUrls;
	}

	public static void setImageUrls(List<RestProjectPhoto> imageUrls) {
		Images.imageUrls = imageUrls;
	}
    
    
}
