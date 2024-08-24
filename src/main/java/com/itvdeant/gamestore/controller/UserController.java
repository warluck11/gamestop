package com.itvdeant.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.itvdeant.gamestore.dao.LoginDao;
import com.itvdeant.gamestore.dao.RegisterDao;
import com.itvdeant.gamestore.dao.UpdateUserDao;
import com.itvdeant.gamestore.entity.User;
import com.itvdeant.gamestore.repository.UserRepository;
import com.itvdeant.gamestore.service.UserService;

//@RestController
@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private AuthenticationManager AuthenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	
	/*
	
	@PostMapping("/reg")
	public ResponseEntity<?> register(@RequestBody RegisterDao registerDao){
		return ResponseEntity.ok(this.userService.register(registerDao));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDao loginDao){
		Authentication authentication = AuthenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginDao.getEmail(), 
																loginDao.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return ResponseEntity.ok("User Logged In!!");
	}
	
	*/
	
	@GetMapping("/reg")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	
	@PostMapping("/reg")
	public String reg(Model model,@ModelAttribute("user") RegisterDao registerDao) {
		model.addAttribute("user", registerDao);
		this.userService.register(registerDao);
		return "register";
	}
	
	
	@GetMapping("/login")
	public String log(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}
	
	@PostMapping("/login")
	public String login(Model model,@ModelAttribute("user") LoginDao loginDao) {
		model.addAttribute("user", loginDao);
		Authentication authentication = AuthenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDao.getEmail(), 
														loginDao.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		return "index";
	}
	
	
	@GetMapping("")
	public String getAllUsers(Model model) {
		model.addAttribute("users", this.userService.getallUsers());
		return "showusers";
	}
	
	
	@GetMapping("/update/{id}")
	public String update(Model model, @PathVariable("id") Integer id ) {
		
		model.addAttribute("user", this.userRepository.findById(id));
		
		return "updateuser";
	}
	
	
	@PostMapping("/update/{id}")
	public String updateUser(Model model,@PathVariable("id") Integer id,@ModelAttribute("user") UpdateUserDao updateUserDao) {
		model.addAttribute("user", updateUserDao);
		this.userService.UpadateUser(updateUserDao, id);
		
		return "redirect:/users";
	}
	
	
	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id) {
		
		this.userService.delete(id);
		
		return "redirect:/users";
	}
	
	
	
	
	
	
	
	
	
	
	
}
