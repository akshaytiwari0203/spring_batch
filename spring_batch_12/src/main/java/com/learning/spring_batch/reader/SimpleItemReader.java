package com.learning.spring_batch.reader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class SimpleItemReader implements ItemReader<String> {
	
	private List<String> dataList = new ArrayList<String>();
	private Iterator<String> iterator;
	
	

	public SimpleItemReader() {
		super();
		dataList.add("1");
		dataList.add("2");
		dataList.add("3");
		dataList.add("4");
		dataList.add("5");
		dataList.add("6");
		dataList.add("7");
		dataList.add("8");
		iterator = dataList.iterator();
	}



	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return iterator.hasNext()? iterator.next() : null;
	}

}
