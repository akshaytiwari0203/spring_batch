package com.learning.spring_batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.learning.spring_batch.decider.CorrectItemDecider;
import com.learning.spring_batch.decider.DeliveryDecider;

@org.springframework.context.annotation.Configuration
public class Configuration {
	
	public enum StepStatus {FAILED,SUCCESS};
	
	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public JobBuilderFactory getJobBuilderFactory() {
		return this.jobBuilderFactory;
	}
	
	@Bean
	public StepBuilderFactory getStepBuilderFactory() {
		return this.stepBuilderFactory;
	}
	
	@Bean
	public JobExecutionDecider deliveryDecider() {
		return new DeliveryDecider();
	}
	
	@Bean
	public JobExecutionDecider correctItemDecider() {
		return new CorrectItemDecider();
	}
	
	
	
	@Bean
	public Job deliverPackageJob() {
		return this.jobBuilderFactory.get("deliverPackageJob")
				.start(packageItemStep())
				.next(driveToAddressStep())
						.on("FAILED").to(storePackageStep())
					.from(driveToAddressStep())
						.on("*").to(deliveryDecider())
									.on("P").to(givePackageToCustomerStep())
												.on("*").to(correctItemDecider())
															.on("CORRECT").to(thankCustomerStep())
														.from(correctItemDecider()).on("INCORRECT").to(initiateRefundStep())
								.from(deliveryDecider())
									.on("N").to(leaveAtDoorStep())
				.end()
				.build();
	}
	
	@Bean
	public Step packageItemStep() {
		return this.stepBuilderFactory.get("packageItemStep").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				Object item = chunkContext.getStepContext().getJobParameters().get("item");
				System.out.println(String.format("The item %s has been packaged",item));
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step driveToAddressStep() {
		return this.stepBuilderFactory.get("driveToAddressStep").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				//Set this to false to make Job Successful
				boolean gotLost = false;
				if(gotLost) {
					throw new RuntimeException("Got Lost While Driving");
				}
				System.out.println("Successfully arrived at address");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step givePackageToCustomerStep() {
		return this.stepBuilderFactory.get("givePackageToCustomerStep").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Successfully given package to customer");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step storePackageStep() {
		return this.stepBuilderFactory.get("storePackageStep").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				Object item = chunkContext.getStepContext().getJobParameters().get("item");
				System.out.println(String.format("The item %s has been stored till the customer address is located",item));
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step leaveAtDoorStep() {
		return this.stepBuilderFactory.get("leaveAtDoorStep").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				Object item = chunkContext.getStepContext().getJobParameters().get("item");
				System.out.println(String.format("The item %s has been left at door as the customer was not at home",item));
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step thankCustomerStep() {
		return this.stepBuilderFactory.get("thankCustomerStep").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				Object item = chunkContext.getStepContext().getJobParameters().get("item");
				System.out.println(String.format("Thanking customer as %s is correct item",item));
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step initiateRefundStep() {
		return this.stepBuilderFactory.get("initiateRefundStep").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				Object item = chunkContext.getStepContext().getJobParameters().get("item");
				System.out.println(String.format("Initiating refund as %s is not correct item",item));
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	

}
