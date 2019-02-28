package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
/**
 * 商品搜索Service
 * Create by dingfeiyang
 *
 * 2018年11月16日
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	
	@Override
	public SearchResult seacher(String queryString, Integer page) {
		// 调用taotao-search的服务
		//查询参数
		Map<String, String> param = new HashMap<>();
		param.put("q", queryString);
		param.put("page", page+"");
		try{
			//调用服务
			String doGet = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
			//将字符串转换成java对象
			TaotaoResult formatToPojo = TaotaoResult.formatToPojo(doGet, SearchResult.class);
			if(formatToPojo.getStatus() == 200){
				SearchResult result = (SearchResult) formatToPojo.getData();
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

}
