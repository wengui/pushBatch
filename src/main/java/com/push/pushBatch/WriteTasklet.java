package com.push.pushBatch;

import java.util.ArrayList;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.push.bean.gen.NanoCheckerResult;
import com.push.dao.writedao.NanoCheckerResultWriteMapper;
import com.push.util.ILoadFile;


@Component("WriteTasklet")
public class WriteTasklet implements Tasklet{

/*	@Autowired
	private UserInfoReadMapper UserInfoMapper;*/
	@Autowired
	private NanoCheckerResultWriteMapper nanoCheckerResultWriteMapper;
    private String message;
	@Autowired
	private ILoadFile loadFile;
    
    /**
     * @param message
     * the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext)throws Exception {
        String filePath = "C:/txtTest/";
        ArrayList<String> fileName = loadFile.getAllFileName("C:/txtTest");
		for (String name : fileName) {
			ArrayList<NanoCheckerResult> resultList = loadFile.readTxtFile(filePath + name);
			
			for(NanoCheckerResult result : resultList){
				nanoCheckerResultWriteMapper.insertSelective(result);
			}
			
			// 数据操作完成后，将文件移动的备份文件夹中
			loadFile.move(filePath + name, "C:/txtTest1/");
		}
        return RepeatStatus.FINISHED;
    }

}
