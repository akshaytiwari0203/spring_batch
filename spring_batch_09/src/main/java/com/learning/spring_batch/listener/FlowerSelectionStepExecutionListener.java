package com.learning.spring_batch.listener;

import java.lang.annotation.Retention;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class FlowerSelectionStepExecutionListener implements StepExecutionListener{

	@Override
	public void beforeStep(StepExecution stepExecution) {
		System.out.println("Executing before step logic for:" + stepExecution.getStepName());
		
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		System.out.println("Executing after step logic for:" + stepExecution.getStepName());
		boolean trimReqd = true;
		
		return  trimReqd ? new ExitStatus("TRIM_REQD") :  new ExitStatus("NO_TRIM_REQD") ;
		
	}

}
