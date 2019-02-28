package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;
	
	@Override
	public ItemInfo getItemByItemId(Long itemId) {
		try{
			//调用rest的服务查询商品基本信息
			String string = HttpClientUtil.doPost(REST_BASE_URL + ITEM_INFO_URL + itemId);
			if(!StringUtils.isBlank(string)){
				TaotaoResult result = TaotaoResult.formatToPojo(string, ItemInfo.class);
				if(result.getStatus() == 200){
					ItemInfo itemInfo = (ItemInfo)result.getData();
					return itemInfo;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getItemDescByItemId(Long itemId) {

		try{
			//去商品描述信息
			String string = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
			
			//转化成java对象
			TaotaoResult pojo = TaotaoResult.formatToPojo(string, TbItemDesc.class);
			
			if(pojo.getStatus() == 200){
				TbItemDesc itemDesc = (TbItemDesc)pojo.getData();
				String desc = itemDesc.getItemDesc();
				return desc;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getItemParamByItemId(Long itemId) {
		
		try{
			//取商品 规格参数
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
			
			//转化为java对象
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
			if(taotaoResult.getStatus() == 200){
				TbItemParamItem itemParam = (TbItemParamItem)taotaoResult.getData();
				String paramData = itemParam.getParamData();
				List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
				StringBuffer sb = new StringBuffer();
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
				sb.append("    <tbody>\n");
				for(Map m1:jsonList) {
					sb.append("        <tr>\n");
					sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
					sb.append("        </tr>\n");
					List<Map> list2 = (List<Map>) m1.get("params");
					for(Map m2:list2) {
						sb.append("        <tr>\n");
						sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
						sb.append("            <td>"+m2.get("v")+"</td>\n");
						sb.append("        </tr>\n");
					}
				}
				sb.append("    </tbody>\n");
				sb.append("</table>");
				
				return sb.toString();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
