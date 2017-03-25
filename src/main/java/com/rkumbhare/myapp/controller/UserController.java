/**
 * @auther Rakesh
 * @time Feb 12, 2017
 */

package com.rkumbhare.myapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rkumbhare.myapp.exception.ControllerException;
import com.rkumbhare.myapp.vo.Address;
import com.rkumbhare.myapp.vo.ClientError;
import com.rkumbhare.myapp.vo.Contact;
import com.rkumbhare.myapp.vo.Message;
import com.rkumbhare.myapp.vo.User;
import com.rkumbhare.myapp.vo.UserInfo;
import com.rkumbhare.myapp.vo.UserRole;

@RestController
public class UserController {

	private List<User> userList;
	private List<UserRole> userRoleList;

	@PostConstruct
	public void init() {
		this.userList = new ArrayList<User>();
		this.userRoleList = new ArrayList<UserRole>();
		User user = null;

		this.userRoleList.add(new UserRole(1, "ADMIN"));
		this.userRoleList.add(new UserRole(2, "USER"));

		List<String> genderList = new ArrayList<String>();
		genderList.add("Male");
		genderList.add("Female");

		List<Date> dateList = Arrays.asList(new Date());

		for (int i = 1; i <= 10; i++) {
			user = new User();
			this.userList.add(user);

			user.setUserId(1000 + i);
			user.setUsername("user" + i);
			user.setPassword("password");
			user.setStartDate(new Date());
			user.setActive(true);
			Random random = new Random();
			user.setUserRole(userRoleList.get(random.nextInt(userRoleList.size())));
			UserInfo userInfo = new UserInfo();
			user.setUserInfo(userInfo);

			userInfo.setId(1000 + i);
			userInfo.setFname("fname" + i);
			userInfo.setLname("lname" + i);
			userInfo.setGender(genderList.get(random.nextInt(genderList.size())));
			userInfo.setDob(dateList.get(random.nextInt(dateList.size())));
			userInfo.setAddressList(Arrays.asList(new Address("Nagpur", "MH", "India", "440017")));
			userInfo.setContactList(Arrays.asList(new Contact("859599394" + i, "07128576" + i)));
		}
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public List<User> getUsers() {
		return this.userList;
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public User getUser(@PathVariable("userId") final int userId) throws ControllerException{
		Optional<User> optional = this.userList.stream().filter(user -> user.getUserId() == userId).findFirst();
		if (optional.isPresent()) {
			return optional.get();
		}else{
			throw new ControllerException("Invalid UserId : " + userId);
		}
	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT, consumes = {"application/json"}, 
			produces = {"application/json"})
	@ResponseBody
	public Message createUser(@RequestBody User user) {
		user.setUserId(this.userList.size() + 1001);
		user.getUserInfo().setId(this.userList.size() + 1001);
		this.userList.add(user);
		return new Message(true, "New User is created with UserId : " + user.getUserId());
	}
	
	@RequestMapping(value="/user", method=RequestMethod.POST, consumes={"application/json"},
			produces={"application/json"})
	@ResponseBody
	public Message updateUser(@RequestBody final User updatedUser){
		this.userList.stream().forEach(user -> {
			if(user.getUserId()==updatedUser.getUserId()){
				if(!StringUtils.isEmpty(updatedUser.getPassword())){
					user.setPassword(updatedUser.getPassword());
				}
				if(!StringUtils.isEmpty(updatedUser.getStartDate())){
					user.setStartDate(updatedUser.getStartDate());
				}
				if(!StringUtils.isEmpty(updatedUser.getEndDate())){
					user.setEndDate(updatedUser.getEndDate());
				}
				user.setActive(updatedUser.isActive());
				if(updatedUser.getUserRole() != null){
					user.setUserRole(updatedUser.getUserRole());
				}
				if(updatedUser.getUserInfo() != null){
					user.setUserInfo(updatedUser.getUserInfo());
				}
			}
			return;
		});
		
		return new Message(true, "User '" + updatedUser.getUserId() + "' is updated ");
	}
	
	@RequestMapping(value="/user/{userId}", method=RequestMethod.DELETE, produces={"application/json"})
	@ResponseBody
	public Message deleteUser(@PathVariable("userId") final int userId){
		this.userList = this.userList.stream().filter(user -> user.getUserId()!= userId).collect(Collectors.toList());
		return new Message(true, "User '" + userId + "' is deleted");
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ClientError> exceptionHandler(Exception e){
		ClientError error = new ClientError(HttpStatus.BAD_REQUEST, e.getMessage(), e.toString());
		ResponseEntity<ClientError> response = new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		return response;
	}

}
