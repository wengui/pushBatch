package com.push.pushBatch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.push.bean.gen.NanoCheckerPushHistory;
import com.push.bean.gen.NanoCheckerResult;
import com.push.dao.readdao.NanoCheckerDeviceTokenReadMapper;
import com.push.dao.writedao.NanoCheckerPushHistoryWriteMapper;
import com.push.dao.writedao.NanoCheckerResultWriteMapper;
import com.push.util.ILoadFile;
import com.push.util.Ipush;

@Component("WriteTasklet")
public class WriteTasklet implements Tasklet {

	/**
	 * 日期格式
	 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	@Autowired
	private NanoCheckerResultWriteMapper nanoCheckerResultWriteMapper;
	@Autowired
	private NanoCheckerPushHistoryWriteMapper nanoCheckerPushHistoryWriteMapper;
	@Autowired
	private NanoCheckerDeviceTokenReadMapper nanoCheckerDeviceTokenReadMapper;

	@Autowired
	private ILoadFile loadFile;
	@Autowired
	private Ipush push;
	
	@SuppressWarnings("serial")
	public final static Map<String,String> map = new HashMap<String,String>() {{
		put("NT-proBNP", "N端脑钠肽");  
	    put("PCT", "降钙素原"); 
	    put("D-dimer", "D二聚体"); 
	    put("TnI", "肌钙蛋白"); 
	    //TODO
	    put("Tn I", "肌钙蛋白"); 
	    put("Myo", "肌红蛋白"); 
	    put("CK-MB", "肌酸激酶同工酶"); 
	}};

	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

		// 调用主要处理
		excuteBatch();
		System.out.println("push.apnpush()");
		return RepeatStatus.FINISHED;
	}

	/**
	 * batch 主要处理
	 * 
	 * @throws Exception
	 */
	public void excuteBatch() throws Exception {
		// 消息推送
		String title = "医疗直通车";// 推送消息title
		String message = "你有一条最新的检查结果的通知！";// 推送消息messsage
		
		// 传递数据的Map
		HashMap<String, String> dataMap = new HashMap<String, String>();
		
		// IOS deviceToken取得
		List<String> deviceTokenList = nanoCheckerDeviceTokenReadMapper.selectAll();

		// 读取文件的目录取得
		String filePath = "C:/SRC/";
		ArrayList<String> fileName = loadFile.getAllFileName("C:/SRC");
		// 根据目标文件夹下文件列表循环读取数据
		for (String name : fileName) {
			String sampleid = "";// 标本号 
			String patientName = "";// 患者姓名
			Date testTime = null;// 检查时间
			String itemName = "";// 测试项目
			StringBuffer pushcontent = new StringBuffer();// 测试项目名称
			
			// 从文件中读取的数
			ArrayList<NanoCheckerResult> resultList = loadFile.readTxtFile(filePath + name);
			
			if(resultList != null && resultList.size()>0){
				patientName = resultList.get(0).getPatientname();
				testTime = resultList.get(0).getTesttime();
				sampleid = resultList.get(0).getSampleid();
			}
			// 根据读取的内容向数据库中插入检查结果
			for (NanoCheckerResult result : resultList) {
				// 测试项目名称取得
				if(map.containsKey(result.getItemname())){
					pushcontent.append(map.get(result.getItemname()));
					pushcontent.append(",");
				}
				nanoCheckerResultWriteMapper.insertSelective(result);
			}

			// 数据操作完成后，将文件移动的备份文件夹中
			loadFile.move(filePath + name, "C:/DIR/");
			// 删除源文件夹内容
			loadFile.deleteDirectory(filePath + name);

			// 患者姓名
			dataMap.put("patientName", patientName);
			// 检查时间
			dataMap.put("testTime", sdf.format(testTime));
			
			// 向安卓手机推送消息
			push.push(title, message, dataMap);
			// 向苹果手机推送消息
			push.apnpush(title, message, dataMap, deviceTokenList);

			// 推送记录登录
			NanoCheckerPushHistory record = new NanoCheckerPushHistory();
			record.setDoctorname("医生");// 推送对象
			record.setPushtime(new Date());// 推送时间
			// 测试项目
			itemName = pushcontent.toString();
			if(!"".equals(itemName) && itemName != null){
				record.setPushcontent(itemName.substring(0, itemName.length()-1));// 患者测试项目
			}else{
				record.setPushcontent("");
			}
			
			record.setPatientname(patientName);// 患者姓名
			record.setTesttime(testTime);// 患者测试时间
			record.setSampleid(sampleid);// 标本号

			// 推送记录登录
			nanoCheckerPushHistoryWriteMapper.insertSelective(record);
		}
	}

}
