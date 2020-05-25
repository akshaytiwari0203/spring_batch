package com.learning.spring_batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {
	
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
	public Job deliverPackageJob() {
		return this.jobBuilderFactory.get("deliverPackageJob").start(packageItemStep())
				.next(driveToAddressStep())
				.next(givePackageToCustomerStep())
				.build();
	}
	
	@Bean
	public Step packageItemStep() {
		return this.stepBuilderFactory.get("packageItemStep").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				//String item = chunkContext.getStepContext().getJobParameters().get("item").toString();
				//System.out.println(String.format("Value of item is %s", item));
				System.out.println("The item has been packaged");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	
	@Bean
	public Step driveToAddressStep() {
		return this.stepBuilderFactory.get("driveToAddressStep").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
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

	

}
