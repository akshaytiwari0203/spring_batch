package com.learning.spring_batch.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class CorrectItemDecider implements JobExecutionDecider{

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		//The decider logic goes here
		boolean isItemCorrect = true;
		String status = isItemCorrect ? "CORRECT" : "INCORRECT";
		System.out.println("CorrectItemDecider Status " + status);
		return new FlowExecutionStatus(status);
	}

}
