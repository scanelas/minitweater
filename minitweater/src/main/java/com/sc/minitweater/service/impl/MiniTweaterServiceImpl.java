package com.sc.minitweater.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sc.minitweater.data.dao.TweetDao;
import com.sc.minitweater.data.dao.UserDao;
import com.sc.minitweater.data.entity.Tweet;
import com.sc.minitweater.data.entity.User;
import com.sc.minitweater.service.MiniTweaterService;

public class MiniTweaterServiceImpl implements MiniTweaterService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private TweetDao tweetDao;
	
	
	public List<Tweet> readTweets(Integer id, String search) {
		
		List<Tweet> result = new ArrayList<Tweet>();
		
		List<Integer> users = getUsers(id);
		
		if(search != null && search.length() > 0)
		{
			result = tweetDao.getFilteredTweetsFromUsers(users, search);
		}
		else
		{
			result = tweetDao.getTweetsFromUsers(users);
		}
		
		return result;
	}
	
	private List<Integer> getUsers(Integer id)
	{
		List<User> following = getFollowing(id);
		List<Integer> followingId = new ArrayList<Integer>();
		
		followingId.add(id);
		
		for (User user : following) 
		{
			followingId.add(user.getId());
		}
		
		return followingId;
	}

	
	public List<User> getFollowers(Integer id) 
	{	
		return userDao.getUsersFollowing(id);
	}

	
	public List<User> getFollowing(Integer id) 
	{
		return userDao.getWhoUserFollows(id);
	}

	
	public boolean followUser(Integer followsUserId, Integer followerUserId) 
	{
		return userDao.followUser(followerUserId, followsUserId);
	}

	
	public boolean unfollowUser(Integer followsUserId, Integer followerUserId) 
	{
		return userDao.unfollowUser(followerUserId, followsUserId);
	}

	public boolean isValidToken(String token)
	{
		return true;
	}


	public UserDao getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public TweetDao getTweetDao() {
		return tweetDao;
	}


	public void setTweetDao(TweetDao tweetDao) {
		this.tweetDao = tweetDao;
	}
	
}
