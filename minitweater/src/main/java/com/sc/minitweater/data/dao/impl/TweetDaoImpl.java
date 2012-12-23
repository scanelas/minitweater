package com.sc.minitweater.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.sc.minitweater.data.dao.TweetDao;
import com.sc.minitweater.data.entity.Tweet;

public class TweetDaoImpl implements TweetDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private static Logger LOG = Logger.getLogger(TweetDaoImpl.class);
	
	private static final class TweetMapper implements RowMapper<Tweet> {

	    public Tweet mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Tweet tweet = new Tweet();
	    	tweet.setId(rs.getInt("id"));
	    	tweet.setText(rs.getString("text"));
	    	Calendar cal = Calendar.getInstance();
	    	cal.setTimeInMillis(rs.getDate("timestamp").getTime());
	    	tweet.setTimestamp(cal.getTime());
	    	tweet.setUserId(rs.getInt("userId"));
	        return tweet;
	    }        
	}


	
	public List<Tweet> getTweetsFromUsers(List<Integer> users) {
		
		List<Tweet> result = new ArrayList<Tweet>();
		
		if(users != null && !users.isEmpty())
		{
			String query = "select id, text, timestamp, userId from Tweet where" +
					" userId in (:users)";
			
			SqlParameterSource namedParameters = new MapSqlParameterSource("users", users);
			
			try
			{
				List<Tweet> allTweets = this.jdbcTemplate.query(query,namedParameters, new TweetMapper());
				
				if(allTweets != null && !allTweets.isEmpty())
				{
					result = allTweets;
				}
			}
			catch(Exception e)
			{
				LOG.error(e.getMessage(), e);
			}
			
		}
		else
		{
			LOG.warn("Invalid User List");
		}
		
		return result;
	}


	
	public boolean publishTweet(Integer user, String text) {
		
		boolean result = false;

		if(user != null && (text != null && text.length() > 0))
		{
			final String query = "insert into Tweet (text, timestamp, userId) values (:text, :timestamp, :userId)";
		
			SqlParameterSource namedParameters = 
					new MapSqlParameterSource().addValue("text", text).addValue("timestamp", Calendar.getInstance().getTime())
					.addValue("userId", user);
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			
			try
			{
				int id = jdbcTemplate.update(query, namedParameters, keyHolder, new String[]{"id"});
				
				if(id == 1 && keyHolder.getKey() != null)
				{
					result = true;
				}
			}
			catch(Exception e)
			{
				LOG.error(e.getMessage(), e);
			}
			
		}
		else
		{
			LOG.warn("Invalid tweet data");
		}
		
		return result;
	}
	
	public List<Tweet> getFilteredTweetsFromUsers(List<Integer> users,
			String filter) {
		
		List<Tweet> result = new ArrayList<Tweet>();
		
		if(users != null && !users.isEmpty())
		{	
			String query = "select tweet.id, tweet.text, tweet.timestamp, tweet.userId from Tweet, User " +
					"where tweet.userId = user.id " +
					"and user.id in (:users) and " +
					"( " +
					"text like :filter " +
					"or " +
					"user.id in (select id from user where username like :filter or name like :filter or email like :filter) " +
					")";
			
			SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("users", users)
					.addValue("filter", "%"+filter+"%");
			
			try
			{
				List<Tweet> allTweets = this.jdbcTemplate.query(query,namedParameters, new TweetMapper());
				
				if(allTweets != null && !allTweets.isEmpty())
				{
					result = allTweets;
				}
			}
			catch(Exception e)
			{
				LOG.error(e.getMessage(), e);
			}
			
		}
		else
		{
			LOG.warn("Invalid User List");
		}
		
		return result;
	}
}
