package com.solactive.code.services;

import java.util.List;

import com.solactive.code.model.Tick;

public interface TickService {
	
	String createTick(String tick);
	
	List<Tick> getAllTicks();
	
	List<Tick> getTicksByRic(String ric);
 
}
