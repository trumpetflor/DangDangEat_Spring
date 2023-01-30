package com.thisteam.dangdangeat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.thisteam.dangdangeat.service.AdminService;

@Controller
public class AdminController {

	@Autowired
	private AdminService service;
	
	//top에 있는 about 페이지로 이동 
	@GetMapping(value = "/About.ad")
	public String insert() {
		return "main/about";
	}
	
}













