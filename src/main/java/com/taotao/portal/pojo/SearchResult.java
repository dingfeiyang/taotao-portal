package com.taotao.portal.pojo;

import java.util.List;

public class SearchResult {
	//商品列表
	private List<Item> itemList;
	//总记录数
	private Long recordCount;
	//总页数
	private Long pageCount;
	//当前页
	private Long curCount;
	public List<Item> getItemList() {
		return itemList;
	}
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
	public Long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	public Long getPageCount() {
		return pageCount;
	}
	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}
	public Long getCurCount() {
		return curCount;
	}
	public void setCurCount(Long curCount) {
		this.curCount = curCount;
	}
}
