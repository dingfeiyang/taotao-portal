package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;

/**
 * 
 * 订单Controller
 * 
 * Create by dingfeiyang
 *
 * 2018年12月3日
 */

@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request,HttpServletResponse response,Model model){
		List<CartItem> cartItemList = cartService.getCartItemList(request, response);
		model.addAttribute("cartList", cartItemList);
		return "order-cart";
	}
	
	/**
	 * 生成订单
	 */
	@RequestMapping("/create")
	public String showSuccess(Model model,Order order,HttpServletRequest request){
		try{
			//在request域中获取用户信息
			TbUser user = (TbUser)request.getAttribute("user");
			//在调用服务之前补充order中的用户信息
			order.setUserId(user.getId());
			order.setBuyerNick(user.getUsername());
			
			String orderId = orderService.createOrder(order);
			model.addAttribute("orderId", orderId);
			model.addAttribute("payment", order.getPayment());
			model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			model.addAttribute("message", "订单提交失败，请稍后重试");
			return "error/exception";
		}
	}
}
