package com.taotao.portal.service;

import com.taotao.portal.pojo.ItemInfo;

public interface ItemService {

	ItemInfo getItemByItemId(Long itemId);
	
	String getItemDescByItemId(Long itemId);
	
	String getItemParamByItemId(Long itemId);
}
