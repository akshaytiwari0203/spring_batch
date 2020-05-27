package com.learning.spring_batch.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class DeliveryDecider implements JobExecutionDecider{

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		//The decider logic goes here
		boolean isCustomerAtHome = true;
		String status = isCustomerAtHome ? "P" : "N";
		System.out.println("Decider Status " + status);
		return new FlowExecutionStatus(status);
	}

}
