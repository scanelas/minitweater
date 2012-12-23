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

import com.sc.minitweater.data.dao.UserDao;
import com.sc.minitweater.data.entity.User;

public class UserDaoImpl implements UserDao{

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	private static Logger LOG = Logger.getLogger(UserDaoImpl.class);
	
	private static final class UserMapper implements RowMapper<User> {

	    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	User user = new User();
	    	user.setId(rs.getInt("id"));
	    	user.setEmail(rs.getString("email"));
	    	user.setName(rs.getString("name"));
	    	Calendar cal = Calendar.getInstance();
	    	cal.setTimeInMillis(rs.getDate("createdDate").getTime());
	    	user.setCreatedDate(cal.getTime());
	    	user.setUsername(rs.getString("username"));
	        return user;
	    }        
	}


	
	public boolean followUser(Integer followerUserId, Integer followsUserId) {
		
		boolean result = false;
		
		if(followerUserId != null && followsUserId != null)
		{
			String query = "insert into Follows (follower, follows, createdDate) " +
					"values (:follower, :follows, :createdDate)";
			
			SqlParameterSource namedParameters = 
					new MapSqlParameterSource().addValue("follower", followerUserId).
					addValue("createdDate", Calendar.getInstance().getTime())
					.addValue("follows", followsUserId);
			
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
			LOG.warn("Invalid follow data");
		}
		
		return result;
	}


	
	public boolean unfollowUser(Integer followerUserId, Integer followsUserId) {
		
		boolean result = false;
		
		if(followerUserId != null && followsUserId != null)
		{
			String query = "delete from Follows where follower = :follower and follows = :follows";
			
			SqlParameterSource namedParameters = 
					new MapSqlParameterSource().addValue("follower", followerUserId)
					.addValue("follows", followsUserId);
			
			try
			{
				int id = jdbcTemplate.update(query, namedParameters);
				
				if(id == 1)
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
			LOG.warn("Invalid unfollow data");
		}
		
		return result;
	}

	
	public List<User> getUsersFollowing(Integer userId) {
		
		List<User> result = new ArrayList<User>();
		
		if(userId != null)
		{
			String query = "select id, username, name, email, createdDate from User " +
					"where id in (select follower from Follows where follows = :userId)";
			
			SqlParameterSource namedParameters = new MapSqlParameterSource("userId", userId);
			
			try
			{
				List<User> usersFollowing = this.jdbcTemplate.query(query, namedParameters, new UserMapper());
				
				if(usersFollowing != null && ! usersFollowing.isEmpty())
				{
					result = usersFollowing;
				}
			}
			catch(Exception e)
			{
				LOG.error(e.getMessage(), e);
			}
			
		}
		else
		{
			LOG.warn("Invalid user data");
		}
		
		return result;
	}

	
	public List<User> getWhoUserFollows(Integer userId) {
		
		List<User> result = new ArrayList<User>();
		
		if(userId != null)
		{
			String query = "select id, username, name, email, createdDate from User " +
					"where id in (select follows from Follows where follower = :userId)";
			
			SqlParameterSource namedParameters = new MapSqlParameterSource("userId", userId);
			
			try
			{
				List<User> usersFollows = this.jdbcTemplate.query(query, namedParameters, new UserMapper());
				
				if(usersFollows != null && ! usersFollows.isEmpty())
				{
					result = usersFollows;
				}
			}
			catch(Exception e)
			{
				LOG.error(e.getMessage(), e);
			}
			
		}
		else
		{
			LOG.warn("Invalid user data");
		}
		
		return result;
	}
	
	
	public boolean createUser(String name, String username, String email) {

		boolean result = false;
		
		if(name != null && username != null && email != null)
		{
			String query = "insert into User (name, username, email, createdDate) " +
					"values (:name, :username, :email, :createdDate)";
			
			SqlParameterSource namedParameters = 
					new MapSqlParameterSource().addValue("name", name).
					addValue("createdDate", Calendar.getInstance().getTime())
					.addValue("username", username).addValue("email", email);
			
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
			LOG.warn("Invalid create user data");
		}
		
		return result;
	}
	
}
