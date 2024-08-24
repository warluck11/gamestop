package com.itvdeant.gamestore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.itvdeant.gamestore.dao.RegisterDao;
import com.itvdeant.gamestore.dao.UpdateUserDao;
import com.itvdeant.gamestore.entity.User;
import com.itvdeant.gamestore.repository.UserRepository;


@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	public List<User> getallUsers(){
		List<User> users = this.userRepository.findAll();
		
		return users;
	}
	
	
	public User register(RegisterDao registerDao) {
		
		if(this.userRepository.findByEmail(registerDao.getEmail()).isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Already Exist");
		}
		
		User user = new User();
		
		
		user.setFirst_name(registerDao.getFirst_name());
		user.setLast_name(registerDao.getLast_name());
		user.setEmail(registerDao.getEmail());
		user.setPassword(passwordEncoder.encode(registerDao.getPassword()));
		user.setAddress(registerDao.getAddress());
		user.setRoles(registerDao.getRoles());
		
		this.userRepository.save(user);
		
		return user;
	}
	
	
	public User UpadateUser(UpdateUserDao updateUserDao, Integer id) {
		
		User user = this.userRepository.findById(id).orElse(null);
		
		if(updateUserDao.getFirst_name() != null) {
			user.setFirst_name(updateUserDao.getFirst_name());
		}
		
		if(updateUserDao.getLast_name() != null) {
			user.setLast_name(updateUserDao.getLast_name());
		}
		
		if(updateUserDao.getEmail() != null) {
			user.setEmail(updateUserDao.getEmail());
		}
		
		if(updateUserDao.getAddress() != null) {
			user.setAddress(updateUserDao.getAddress());
		}
		
		if(updateUserDao.getPassword() != null) {
			user.setPassword(updateUserDao.getPassword());
		}
		
		if(updateUserDao.getRoles() != null) {
			user.setRoles(updateUserDao.getRoles());
		}
		
		this.userRepository.save(user);
		
		return user;
		
	}
	
	
	public void delete(Integer id){
		User user = this.userRepository.findById(id).orElse(null);
		
		this.userRepository.delete(user);
	}
	
	
}
