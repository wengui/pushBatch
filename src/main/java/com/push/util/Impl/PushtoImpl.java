package com.push.util.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.APNTemplate;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.push.util.Ipush;

/**
 * 消息推送接口
 * @author yuting
 *
 */
@Component
public class PushtoImpl implements Ipush{	
	static String appId = "CheMtfOtG48k8QLXt7CdR";
	static String appkey = "YoN2xo5o7463N0wqGFkhF8";
	static String master = "4R8zoqK2kr9chS7UGrQO31";
	static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	static String devicetoken = "59374d5ec6b1ab4e7388cf6b5c634da6d8cc69592a93c384d6ede69561d6f025";
	static String url ="http://sdk.open.api.igexin.com/serviceex";

	public TransmissionTemplate TransmissionTemplateDemo()
			throws Exception {
		TransmissionTemplate template = new TransmissionTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setTransmissionType(1);
		template.setTransmissionContent("os-toApp");
//		template.setPushInfo("actionLocKey", 3, "message", "sound", "payload",
//				"locKey", "locArgs", "launchImage");
		return template;
	}

	public LinkTemplate linkTemplateDemo() throws Exception {
		LinkTemplate template = new LinkTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setTitle("ddd");
		template.setText("ddd");
		template.setLogo("icon.png");
		template.setLogoUrl("");
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		template.setUrl("http://www.baidu.com");
		return template;
	}
	
	public LinkTemplate linkTemplateDemo(String title,String message,String date) throws Exception {
		LinkTemplate template = new LinkTemplate();
		template.setAppId(appId);
		template.setAppkey(appkey);
		template.setTitle(title);
		template.setText(message);
		
		template.setLogo("icon.png");
		template.setLogoUrl("");
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		template.setUrl("http://www.baidu.com");
		return template;
	}
	
	/**
	 * 
	 * @param title 标题
	 * @param message 信息
	 * @param date 后台需要的数据
	 * @return
	 * @throws Exception 
	 */
	public String push(String title,String message,String date) throws Exception{
		
		IGtPush push = new IGtPush(host, appkey, master);
		LinkTemplate template = linkTemplateDemo(title,message,date);
		AppMessage pushMessage = new AppMessage();
		pushMessage.setData(template);

		pushMessage.setOffline(true);
		pushMessage.setOfflineExpireTime(24 * 1000 * 3600);

		List<String> appIdList = new ArrayList<String>();
		List<String> phoneTypeList = new ArrayList<String>();

		appIdList.add(appId);

		pushMessage.setAppIdList(appIdList);
		
		// 设置推送系统
		phoneTypeList.add("ANDROID");
		phoneTypeList.add("IOS");
		pushMessage.setPhoneTypeList(phoneTypeList);
		
		IPushResult ret = push.pushMessageToApp(pushMessage);
		return ret.getResponse().get("result").toString();
	}
	
	

	    public void apnpush() throws Exception {
	       IGtPush push = new IGtPush(url, appkey, master);
	       
	       APNTemplate t = new APNTemplate();
	       APNPayload apnpayload = new APNPayload();
	       apnpayload.setSound("");
	       APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
	       alertMsg.setTitle("aaaaaa");
	       alertMsg.setBody("bbbb");
	       alertMsg.setTitleLocKey("ccccc");
	       alertMsg.setActionLocKey("ddddd");
	       apnpayload.setAlertMsg(alertMsg);
	       t.setAPNInfo(apnpayload);
	       ListMessage message = new ListMessage();
	       message.setData(t);
	       String contentId = push.getAPNContentId(appId, message);
	       System.out.println(contentId);
	       List<String> dtl = new ArrayList<String>();
	       dtl.add(devicetoken);
	       System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
	       IPushResult ret = push.pushAPNMessageToList(appId, contentId, dtl);
	       System.out.println(ret.getResponse());
	    }
	    
/*	    public static void main(String[] args) throws Exception {
	       apnpush();
	    }*/


}