package com.push.pushBatch;

import java.util.ArrayList;
import java.util.Date;

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
public class WriteTasklet implements Tasklet{

/*	@Autowired
	private UserInfoReadMapper UserInfoMapper;*/
	@Autowired
	private NanoCheckerResultWriteMapper nanoCheckerResultWriteMapper;
	@Autowired
	private NanoCheckerPushHistoryWriteMapper nanoCheckerPushHistoryWriteMapper;
	
	@Autowired
	private ILoadFile loadFile;
	@Autowired
	private Ipush push;
    
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext)throws Exception {
        
    	// 调用主要处理
    	excuteBatch();
    	
        return RepeatStatus.FINISHED;
    }
    
    /**
     * batch 主要处理
     * @throws Exception
     */
    public void excuteBatch() throws Exception{
    	String filePath = "C:/txtTest/";
        ArrayList<String> fileName = loadFile.getAllFileName("C:/txtTest");
		for (String name : fileName) {
			ArrayList<NanoCheckerResult> resultList = loadFile.readTxtFile(filePath + name);
			
			String patientName = "";
			Date testTime =null;
			for(NanoCheckerResult result : resultList){
				patientName = result.getPatientname();
				testTime = result.getTesttime();
				nanoCheckerResultWriteMapper.insertSelective(result);
			}
			
			// 数据操作完成后，将文件移动的备份文件夹中
			loadFile.move(filePath + name, "C:/txtTest1/");
			
			// 消息推送
			String title ="健康检查";
			String message ="你有一条最新的检查结果的通知！";
			String status = push.push(title, message, null);
			if("ok".equals(status)){
				// 推送记录登录
				NanoCheckerPushHistory record = new NanoCheckerPushHistory();
				record.setDoctorname("余医生");// 推送对象
				record.setPushtime(new Date());// 推送时间
				//TODO 测试项目
				record.setPushcontent("三合一");// 患者测试项目
				record.setPatientname(patientName);// 患者姓名
				record.setTesttime(testTime);// 患者测试时间
				
				// 推送记录登录
				nanoCheckerPushHistoryWriteMapper.insertSelective(record);
			}
		}
    }

}
