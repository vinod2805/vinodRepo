package com.solactive.code.services;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.CSVWriter;
import com.solactive.code.exceptions.InvalidFormatException;
import com.solactive.code.model.Tick;
import com.solactive.code.repository.TickRepository;
import com.solactive.code.util.TickServiceUtil;

@Service
@Transactional
public class TickServiceImpl implements TickService {
	
	private static final Logger log = LogManager.getLogger(TickServiceImpl.class);
	
	@Autowired
	private TickRepository tickRepository;
	@Autowired
	private TickServiceUtil util;
	@Autowired
	private Environment env;
	
	@Override
	public String createTick(String tick) {
	try {
		String[] lines = tick.split("\\R");
		for(String line:lines) {
			String[] data = line.split("\\|");
			Tick t = util.convertStringToTick(data);
			if(t.getRic()==null || t.getCurrency()==null || t.getTimestamp()==null || (t.getPrice()==null && t.getClose_price()==null))
				throw new InvalidFormatException("Invalid Format");
			tickRepository.save(t);
			if(t.getClose_price()!=null && !t.getClose_price().toString().equalsIgnoreCase(""))
				exportCSV(t.getRic());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new InvalidFormatException("Error Occurred: "+e);
		}
		return "Success";
	}

	@Override
	public List<Tick> getAllTicks() {
		return this.tickRepository.findAll();
	}

	@Override
	public List<Tick> getTicksByRic(String ric) {
		List<Tick> ticks = this.tickRepository.findAll();
		List<Tick> filteredTicks = ticks.stream().filter(tick -> tick.getRic().equalsIgnoreCase(ric)).collect(Collectors.toList());
		return filteredTicks;
	}
	
	@Async
	public void exportCSV(String ric) {
		List<Tick> ticks = getTicksByRic(ric);
		String[] header = {"TIMESTAMP", "PRICE", "CLOSE_PRICE", "CURRENCY", "RIC"};
		List<String[]> csvData = new ArrayList<>();
		csvData.add(header);
		ticks.forEach(tick ->{
			String[] record = {tick.getTimestamp().toString(),tick.getPrice()==null?"":tick.getPrice().toString(),
					tick.getClose_price()==null?"":tick.getClose_price().toString(),
					tick.getCurrency(),tick.getRic()};
			csvData.add(record);
		});
		if(csvData.size()>1) {
		String filePath = env.getProperty("filePath");
		log.info("File Export Path: "+filePath);
		 try (CSVWriter writer = new CSVWriter(new FileWriter(filePath+"\\export_"+ric+"_"+System.currentTimeMillis()+".csv"))) {
	            writer.writeAll(csvData);
	        } catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
