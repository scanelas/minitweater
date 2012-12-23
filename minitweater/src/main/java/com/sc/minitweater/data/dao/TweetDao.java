package com.sc.minitweater.data.dao;

import java.util.List;

import com.sc.minitweater.data.entity.Tweet;

public interface TweetDao {

	public List<Tweet> getTweetsFromUsers(List<Integer> users);
	
	public boolean publishTweet(Integer user, String text); 
	
	public List<Tweet> getFilteredTweetsFromUsers(List<Integer> users, String filter);
	
}
