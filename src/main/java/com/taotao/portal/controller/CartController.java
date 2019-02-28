package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

/**
 * 
 * 购物车商品Controller
 * 
 * Create by dingfeiyang
 *
 * 2018年11月29日
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	/**
	 * 购物车列表添加
	 */
	@RequestMapping("/add/{itemId}")
	public String addCartItemList(@PathVariable Long itemId,@RequestParam(defaultValue = "1") Integer num,HttpServletRequest request,HttpServletResponse response){
		cartService.addCartItem(itemId, num, request, response);
		return "redirect:/cart/success.html";
	}
	@RequestMapping("/success")
	public String showSuccess(){
		return "cartSuccess";
	}
	
	/**
	 * 展示购物车列表
	 */
	@RequestMapping("/cart")
	public String showCatr(HttpServletRequest request,HttpServletResponse response,Model model){
		List<CartItem> itemList = cartService.getCartItemList(request, response);
		model.addAttribute("cartList", itemList);
		return "cart";
	}
	
	/**
	 * 修改购物车数量
	 */
	@RequestMapping("/updateNum/{itemId}")
	public String updateCartItemNum(@PathVariable Long itemId,Integer num,HttpServletRequest request,HttpServletResponse response){
		cartService.updateCartItemNum(itemId, num, request, response);
		return "redirect:/cart/success.html";
	}
	
	/**
	 * 删除购物车商品
	 */
	@RequestMapping("/delete/{itemId}")
	public String deleteItemCart(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response){
		cartService.deleteItemCart(itemId, request, response);
		return "redirect:/cart/cart.html";
	}
}
