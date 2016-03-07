package com.push.util;

import com.gexin.rp.sdk.template.LinkTemplate;
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
	public String push(String title,String message,String date) throws Exception;
			
}
