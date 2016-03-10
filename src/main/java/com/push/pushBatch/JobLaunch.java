package com.push.pushBatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class JobLaunch {

	private static Log log = LogFactory.getLog(JobLaunch.class);
	
	public static void main(String[] args) {
        try {
        	
        	log.info("batch start");
        	
            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/config/springBatch.xml");
 
            JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
            Job job = (Job) context.getBean("helloWorldJob"); 
 
            // JobLauncher可以用来启动Job
            JobExecution result = jobLauncher.run(job, new JobParameters());
             
            // 处理结束，控制台打印处理结果 
            System.out.println(result.toString());
            
            log.info("batch end");
            
        } catch (Exception e) {
        	System.out.println(e);
        	log.error("batch error:" + e.getMessage());
            throw new RuntimeException(e);
            
        }
    }
}
