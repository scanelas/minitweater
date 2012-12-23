package com.sc.minitweater.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sc.minitweater.data.dao.UserDao;


@Configurable(autowire = Autowire.BY_NAME)
@ContextConfiguration(locations={"classpath:test-context.xml",
"classpath:/META-INF/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoTest {

	@Autowired
	private UserDao userDao;
	
	@Test
	public void testCreateUser()
	{
		String name = "Test Name";
		String username = "TestUserName2";
		String email = "test@test.test";
		
		assertTrue(userDao.createUser(name, username, email));
	}
	
	@Test
	public void testFollowUser()
	{
		Integer followerUserId = 2;
		Integer followsUserId = 5;
		
		assertTrue(userDao.followUser(followerUserId, followsUserId));
	}
	
	@Test
	public void testUnfollowUser()
	{
		Integer followerUserId = 1;
		Integer followsUserId = 2;
		
		assertTrue(userDao.unfollowUser(followerUserId, followsUserId));
	}
	
	@Test
	public void testUsersFollowing()
	{
		Integer userId = 1;
		
		assertNotNull(userDao.getUsersFollowing(userId));
	}
	
	@Test
	public void testWhoUserFollowing()
	{
		Integer userId = 1;
		
		assertNotNull(userDao.getWhoUserFollows(userId));
	}
}
