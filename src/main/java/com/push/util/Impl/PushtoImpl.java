package com.push.util.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.payload.APNPayload;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.APNTemplate;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
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
	//static String devicetoken = "59374d5ec6b1ab4e7388cf6b5c634da6d8cc69592a93c384d6ede69561d6f025";
	static String devicetoken = "2E394A0B232E6E0FE11F0936851E1523ECDA44139541CAC1AEC5F73A5857BBB9";
	static String url ="http://sdk.open.api.igexin.com/serviceex";

	/**
	 * 
	 * @param title 标题
	 * @param message 信息
	 * @param date 后台需要的数据
	 * @return
	 * @throws Exception 
	 */
	public String push(String title,String message,Map<String,String> dateMap) throws Exception{
		
		IGtPush push = new IGtPush(host, appkey, master);
		// 通知模板取得
		NotificationTemplate template = NotificationTemplateDemo(title,message,dateMap);
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
		pushMessage.setPhoneTypeList(phoneTypeList);
		
		IPushResult ret = push.pushMessageToApp(pushMessage);
		return ret.getResponse().get("result").toString();
	}

	/**
	 * 消息推送
	 */
	public String apnpush(String title, String messages, Map<String,String> dateMap) throws Exception {
		IGtPush push = new IGtPush(url, appkey, master);

		APNTemplate template = new APNTemplate();
		// 苹果手机接收内容处理
		APNPayload apnpayload = new APNPayload();
		apnpayload.setSound("");
		APNPayload.DictionaryAlertMsg alertMsg = new APNPayload.DictionaryAlertMsg();
		// IOS手机推送的title
		alertMsg.setTitle(title + "苹果手机");
		// IOS手机推送的message
		alertMsg.setBody(messages);
		alertMsg.setTitleLocKey("ccccc");
		alertMsg.setActionLocKey("ddddd" + "苹果手机");
		apnpayload.setAlertMsg(alertMsg);
		apnpayload.addCustomMsg("name", dateMap.get("patientName"));
		apnpayload.addCustomMsg("time", dateMap.get("testTime"));
		template.setAPNInfo(apnpayload);
		ListMessage message = new ListMessage();
		message.setData(template);
		String contentId = push.getAPNContentId(appId, message);
		List<String> dtl = new ArrayList<String>();
		dtl.add(devicetoken);
		
		// 消息推送处理
		IPushResult ret = push.pushAPNMessageToList(appId, contentId, dtl);
		
		// 推送状态返回
		return ret.getResponse().get("result").toString();
	}
	    
	    /**
	     * 透传消息模板
	     * 透传消息是指消息传递到客户端只有消息内容，展现形式由客户端自行定义。
	     * 客户端可自定义通知的展现形式，也可自定义通知到达之后的动作，或者不做任何展现。
	     */
		public TransmissionTemplate TransmissionTemplateDemo() throws Exception {
			TransmissionTemplate template = new TransmissionTemplate();
			template.setAppId(appId);
			template.setAppkey(appkey);
			// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
			template.setTransmissionType(1);
			template.setTransmissionContent("os-toApp");
//			template.setPushInfo("actionLocKey", 3, "message", "sound", "payload",
//					"locKey", "locArgs", "launchImage");
			return template;
		}

		/**
		 * 点击通知打开网页模板
		 * 推送广促销活动，用户点击通知栏信息，
		 * 直接打开到指定的促销活动页面，推送直接到达指定页面，免去了中间过程的用户流失
		 */
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
		
		/**
		 * 点击通知打开网页模板
		 * 推送广促销活动，用户点击通知栏信息，
		 * 直接打开到指定的促销活动页面，推送直接到达指定页面，免去了中间过程的用户流失
		 * 
		 * @param 推送title
		 * @param 推送显示的message
		 * @param 推送到前台的数据
		 */
		public LinkTemplate linkTemplateDemo(String title,String message,String date) throws Exception {
			LinkTemplate template = new LinkTemplate();
			template.setAppId(appId);
			template.setAppkey(appkey);
			template.setTitle(title+"安卓手机");
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
		 * 点击通知打开应用模板
		 * 针对沉默用户，发送推送消息，点击消息栏的通知可直接激活启动应用，提升应用的转化率
		 * @return
		 * @throws Exception
		 */
		public NotificationTemplate NotificationTemplateDemo(String title,String message,Map<String,String> dateMap)
				throws Exception {
			
			NotificationTemplate template = new NotificationTemplate();
			// 安卓手机推送内容处理
			template.setAppId(appId);
			template.setAppkey(appkey);
			// 安卓手机推送的title
			template.setTitle(title + "点击通知打开应用模板");
			// 安卓手机推送的message
			template.setText(message);
			template.setLogo("icon.png");
			template.setLogoUrl("");
			template.setIsRing(true);
			template.setIsVibrate(true);
			template.setIsClearable(true);
			// 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
			template.setTransmissionType(1);
			template.setTransmissionContent(dateMap.get("patientName") + "," + dateMap.get("testTime"));
			return template;
		}
}