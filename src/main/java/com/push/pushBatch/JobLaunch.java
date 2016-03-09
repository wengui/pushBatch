package com.push.pushBatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class JobLaunch {

	public static void main(String[] args) {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/config/springBatch.xml");
 
            JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
            Job job = (Job) context.getBean("helloWorldJob"); 
 
            // JobLauncher可以用来启动Job
            JobExecution result = jobLauncher.run(job, new JobParameters());
             
            // 处理结束，控制台打印处理结果 
            System.out.println(result.toString());
            
        } catch (Exception e) {
        	System.out.println(e);
            throw new RuntimeException(e);
            
        }
    }
}
