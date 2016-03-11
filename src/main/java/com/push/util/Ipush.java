package com.push.util;

import java.util.List;
import java.util.Map;

import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;

public interface Ipush {

	/**
	 * 消息模板
	 * @return
	 * @throws Exception
	 */
	public TransmissionTemplate TransmissionTemplateDemo()
			throws Exception;
	
	/**
	 * 消息模板
	 * @return
	 * @throws Exception
	 */
	public LinkTemplate linkTemplateDemo() throws Exception;
	
	/**
	 * 消息模板
	 * @param title 标题
	 * @param message 信息
	 * @param date 后台需要的数据
	 * @return
	 * @throws Exception
	 */
	public LinkTemplate linkTemplateDemo(String title,String message,String date) throws Exception;
	
	/**
	 * 
	 * @param title 标题
	 * @param message 信息
	 * @param date 后台需要的数据
	 * @return
	 */
	public String push(String title,String message,Map<String,String> dateMap) throws Exception;
	
	/**
	 * 推送给苹果手机
	 * @param title
	 * @param messages
	 * @param date
	 * @throws Exception
	 */
	public String apnpush(String title,String messages,Map<String,String> dateMap, List<String> dtl) throws Exception;
	
	/**
	 * 点击通知打开应用模板
	 * 针对沉默用户，发送推送消息，点击消息栏的通知可直接激活启动应用，提升应用的转化率
	 * @return
	 * @throws Exception
	 */
	public NotificationTemplate NotificationTemplateDemo(String title,String message,Map<String,String> dateMap)
			throws Exception;
			
}
