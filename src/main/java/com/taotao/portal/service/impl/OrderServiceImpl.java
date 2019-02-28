package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.common.util.JsonUtils;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;
/**
 * 
 * 订单管理Service
 * 
 * Create by dingfeiyang
 *
 * 2018年12月3日
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	
	@Override
	public String createOrder(Order order) {
		String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		TaotaoResult result = TaotaoResult.format(json);
		if(result.getStatus() == 200){
			Object orderId = result.getData();
			return orderId.toString();
		}
		
		return null;
	}

}
