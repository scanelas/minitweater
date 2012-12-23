package com.sc.minitweater.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sc.minitweater.data.dao.TweetDao;

@Configurable(autowire = Autowire.BY_NAME)
@ContextConfiguration(locations={"classpath:test-context.xml",
"classpath:/META-INF/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TweetDaoTest {

	@Autowired
	private TweetDao tweetDao;
	
	@Test
	public void testPublishTweet()
	{
		assertTrue(tweetDao.publishTweet(2, "New Test Tweet"));
	}
	
	@Test
	public void testGetTweetsFromUsers()
	{
		List<Integer> users = Arrays.asList(new Integer[]{1,2,3,4,5});
		assertNotNull(tweetDao.getTweetsFromUsers(users));
	}
	
	@Test
	public void testGetFilteredTweetsFromUsers()
	{
		List<Integer> users = Arrays.asList(new Integer[]{1,2,3,4,5});
		String filter = "scanelas";
		
		assertNotNull(tweetDao.getFilteredTweetsFromUsers(users, filter));
	}
}
