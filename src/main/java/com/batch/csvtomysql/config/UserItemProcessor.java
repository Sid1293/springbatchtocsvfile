package com.batch.csvtomysql.config;

import org.springframework.batch.item.ItemProcessor;

import com.batch.csvtomysql.model.User;

public class UserItemProcessor implements ItemProcessor<User, User>{

	@Override
	public User process(User item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}

}
