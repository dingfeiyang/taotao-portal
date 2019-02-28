package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

public class ItemInfo extends TbItem{

	public String[] getImages(){
		if(getImage()!=""){
			String images[] = getImage().split(",");
			return images;
		}else{
			return null;
		}
	}
}
