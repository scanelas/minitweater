package com.sc.minitweater.service;

import java.util.List;

import com.sc.minitweater.data.entity.Tweet;
import com.sc.minitweater.data.entity.User;

public interface MiniTweaterService {

	List<Tweet> readTweets(Integer id,String search);

	List<User> getFollowers(Integer id);

	List<User> getFollowing(Integer id);

	boolean followUser(Integer followsUserId, Integer followerUserId);

	boolean unfollowUser(Integer followsUserId, Integer followerUserId);
	
	boolean isValidToken(String token);

}
