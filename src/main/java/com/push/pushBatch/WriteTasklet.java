package com.push.pushBatch;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;


@Component("WriteTasklet")
public class WriteTasklet implements Tasklet{

/*	@Autowired
	private UserInfoReadMapper UserInfoMapper;*/
    private String message;
    
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
        return RepeatStatus.FINISHED;
    }

}
