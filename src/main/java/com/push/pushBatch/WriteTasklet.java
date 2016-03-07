package com.push.pushBatch;

import java.util.ArrayList;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.push.util.ILoadFile;


@Component("WriteTasklet")
public class WriteTasklet implements Tasklet{

/*	@Autowired
	private UserInfoReadMapper UserInfoMapper;*/
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
        System.out.println(message);
        //UserInfo info = UserInfoMapper.selectByPrimaryKey(206);
        //System.out.println(info);
        String filePath = "C:/txtTest/";
        ArrayList<String> fileName = loadFile.getAllFileName("C:/txtTest");
		for (String name : fileName) {
			// System.out.println(name);
			loadFile.readTxtFile(filePath + name);
			//loadFile.copy(filePath + name, "C:/txtTest1/");
			loadFile.move(filePath + name, "C:/txtTest1/");
		}
        return RepeatStatus.FINISHED;
    }

}
