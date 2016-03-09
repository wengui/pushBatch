package com.push.pushBatch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.push.bean.gen.NanoCheckerPushHistory;
import com.push.bean.gen.NanoCheckerResult;
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
	private ILoadFile loadFile;
	@Autowired
	private Ipush push;

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
		String title = "健康检查";// 推送消息title
		String message = "你有一条最新的检查结果的通知！";// 推送消息messsage
		
		// 传递数据的Map
		HashMap<String, String> dataMap = new HashMap<String, String>();

		// 读取文件的目录取得
		String filePath = "C:/txtTest/";
		ArrayList<String> fileName = loadFile.getAllFileName("C:/txtTest");
		// 根据目标文件夹下文件列表循环读取数据
		for (String name : fileName) {
			String patientName = "";// 患者姓名
			Date testTime = null;// 检查时间
			
			// 从文件中读取的数据
			ArrayList<NanoCheckerResult> resultList = loadFile.readTxtFile(filePath + name);
			
			// 根据读取的内容向数据库中插入检查结果
			for (NanoCheckerResult result : resultList) {
				patientName = result.getPatientname();
				testTime = result.getTesttime();
				nanoCheckerResultWriteMapper.insertSelective(result);
			}

			// 数据操作完成后，将文件移动的备份文件夹中
			loadFile.move(filePath + name, "C:/txtTest1/");

			// 患者姓名
			dataMap.put("patientName", patientName);
			// 检查时间
			dataMap.put("testTime", sdf.format(testTime));
			
			push.push(title, message, dataMap);
			// 向苹果手机推送消息
			push.apnpush(title, message, dataMap);

			// 推送记录登录
			NanoCheckerPushHistory record = new NanoCheckerPushHistory();
			record.setDoctorname("余医生");// 推送对象
			record.setPushtime(new Date());// 推送时间
			// TODO 测试项目
			record.setPushcontent("三合一");// 患者测试项目
			record.setPatientname(patientName);// 患者姓名
			record.setTesttime(testTime);// 患者测试时间

			// 推送记录登录
			nanoCheckerPushHistoryWriteMapper.insertSelective(record);
		}
	}

}
