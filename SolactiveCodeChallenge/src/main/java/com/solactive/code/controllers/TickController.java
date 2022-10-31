package com.solactive.code.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solactive.code.model.Tick;
import com.solactive.code.services.TickService;

@RestController
@RequestMapping(path = "/rest")
public class TickController {
	
	@Autowired
	private TickService tickService;
	
	@PostMapping("/ticks")
	ResponseEntity<List<Tick>> getAllTicks(){
		return ResponseEntity.ok().body(this.tickService.getAllTicks());
	}
	
	@PostMapping("/createTick")
	ResponseEntity<String> createTick(@RequestBody String tick){
		return ResponseEntity.ok().body(this.tickService.createTick(tick));
	}
	
	@PostMapping("/getTicksByRic")
	ResponseEntity<List<Tick>> getTicksByRic(@RequestBody String ric){
		return ResponseEntity.ok().body(this.tickService.getTicksByRic(ric));
	}
	
}
