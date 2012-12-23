package com.sc.minitweater.data.dao;

import java.util.List;

import com.sc.minitweater.data.entity.User;

public interface UserDao {

	public boolean followUser(Integer followerUserId, Integer followsUserId);
	
	public boolean unfollowUser(Integer followerUserId, Integer followsUserId);
	
	public List<User> getUsersFollowing(Integer userId);
	
	public List<User> getWhoUserFollows(Integer userId);

	public boolean createUser(String name, String username, String email);
}
