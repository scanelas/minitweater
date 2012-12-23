package com.sc.minitweater.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.minitweater.data.entity.Tweet;
import com.sc.minitweater.data.entity.User;
import com.sc.minitweater.mvc.exception.NotValidTokenException;
import com.sc.minitweater.service.MiniTweaterService;

@Controller
public class MiniTweaterController {

	@Autowired
	private MiniTweaterService miniTweaterService;

	public MiniTweaterService getMiniTweaterService() {
		return miniTweaterService;
	}

	public void setMiniTweaterService(MiniTweaterService miniTweaterService) {
		this.miniTweaterService = miniTweaterService;
	}
	
	@RequestMapping(value="/read/{id}", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody List<Tweet> readTweets(@PathVariable("id") Integer id, 
    		@RequestParam("token") String token, @RequestParam(value="search", required=false) String search)
    {
		if(!miniTweaterService.isValidToken(token))
		{
			throw new NotValidTokenException();
		}
		
        return miniTweaterService.readTweets(id, search);
    }
	
	@RequestMapping(value="/followers/{id}", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody List<User> getFollowers(@PathVariable("id") Integer id, 
    		@RequestParam("token") String token)
    {
		if(!miniTweaterService.isValidToken(token))
		{
			throw new NotValidTokenException();
		}
		
		return miniTweaterService.getFollowers(id);
    }
	
	@RequestMapping(value="/following/{id}", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody List<User> getFollowing(@PathVariable("id") Integer id, 
    		@RequestParam("token") String token)
    {
		if(!miniTweaterService.isValidToken(token))
		{
			throw new NotValidTokenException();
		}
		
		return miniTweaterService.getFollowing(id);
    }
	
	@RequestMapping(value="/follow/{id}/{follows}", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody boolean followUser(@PathVariable("id") Integer id, 
    		@RequestParam("token") String token, @PathVariable("follows") Integer follows)
    {
		if(!miniTweaterService.isValidToken(token))
		{
			throw new NotValidTokenException();
		}
		
		return miniTweaterService.followUser(follows, id);
    }
	
	@RequestMapping(value="/unfollow/{id}/{unfollow}", method=RequestMethod.GET, produces="application/json")
    public @ResponseBody boolean unfollowUser(@PathVariable("id") Integer id, 
    		@RequestParam("token") String token, @PathVariable("unfollow") Integer unfollow)
    {
		if(!miniTweaterService.isValidToken(token))
		{
			throw new NotValidTokenException();
		}
		
		return miniTweaterService.unfollowUser(unfollow, id);
    }
	
}
