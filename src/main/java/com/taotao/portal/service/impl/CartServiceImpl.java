package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
/**
 * 
 * 购物车Service
 * 
 * Create by dingfeiyang
 *
 * 2018年11月29日
 */
@Service
public class CartServiceImpl implements CartService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	
	
	/**
	 * 添加购物车商品
	 */
	@Override
	public TaotaoResult addCartItem(Long itemId, Integer num,HttpServletRequest request,HttpServletResponse response) {
		
		CartItem cartItem = null;
		//从cookie中获取商品列表
		List<CartItem> cartItemList = getCartItemList(request);
		if(cartItemList.size() > 0 && cartItemList != null){
			//判断cookie商品列表中是否有该商品
			for(CartItem item : cartItemList){
				if(item.getId().equals(itemId)){
					//若存在，改变该商品的数量
					item.setNum(num + item.getNum());
					cartItem = item;
					break;
				}
			}
		}
		
		if(cartItem == null){
			cartItem = new CartItem();
			//调用rest服务，根据商品id查询商品
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL +itemId);
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbItem.class);
			if(result.getStatus() == 200){
				TbItem item = (TbItem)result.getData();
				
				//尽量减少cookie存的数据
				cartItem.setId(item.getId());
				cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
				cartItem.setNum(num);
				cartItem.setPrice(item.getPrice());
				cartItem.setTitle(item.getTitle());
			}
			
			//添加到购物车列表
			cartItemList.add(cartItem);
		}
		
		//将商品列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItemList), true);
		
		return TaotaoResult.ok();
	}
	
	/**
	 *从cookie中获取购物车列表
	 */
	public List<CartItem> getCartItemList(HttpServletRequest request){
		//从cookie中取购物车信息
		String json = CookieUtils.getCookieValue(request, "TT_CART", true);
		if(json == null){
			return new ArrayList<CartItem>();
		}
		
		try{
			//将json转换成商品列表
			List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return new ArrayList<CartItem>();
		}
		
	}

	@Override
	public List<CartItem> getCartItemList(HttpServletRequest request,
			HttpServletResponse response) {
		List<CartItem> itemList = getCartItemList(request);
		return itemList;
	}

	@Override
	public TaotaoResult updateCartItemNum(Long itemId, Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		//从cookie中获取商品列表
		List<CartItem> cartItemList = getCartItemList(request);
		if(cartItemList.size() > 0 && cartItemList != null){
			//判断cookie商品列表中是否有该商品
			for(CartItem item : cartItemList){
				if(item.getId().equals(itemId)){
					//若存在，改变该商品的数量
					item.setNum(num);
				}
			}
			CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(cartItemList), true);
		}
		return TaotaoResult.ok();
	}

	/**
	 * 删除购物车商品
	 */
	@Override
	public TaotaoResult deleteItemCart(Long itemId, HttpServletRequest request,
			HttpServletResponse response) {
		List<CartItem> itemList = this.getCartItemList(request);
		for(CartItem item : itemList){
			if(item.getId().equals(itemId)){
				itemList.remove(item);
				break;
			}
		}
		
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList),true);
		return TaotaoResult.ok();
	}
	
}
