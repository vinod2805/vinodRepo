package com.solactive.code.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.solactive.code.exceptions.InvalidFormatException;
import com.solactive.code.model.Tick;

@Component
public class TickServiceUtil {
	private static final Logger log = LogManager.getLogger(TickServiceUtil.class);
	public Tick convertStringToTick(String[] data) {
		Tick t = new Tick();
		for(String res:data) {
			if(res.substring(0, res.indexOf("=")).equalsIgnoreCase("TIMESTAMP")) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS");
			    Date parsedDate = null;
				try {
					parsedDate = dateFormat.parse(res.substring(res.indexOf("=")+1, res.length()));
				} catch (ParseException e) {
					e.printStackTrace();
					throw new InvalidFormatException("Invalid Format of TimeStamp: "+e);
				}
				t.setTimestamp(parsedDate);
			}
			
			if(res.substring(0, res.indexOf("=")).equalsIgnoreCase("PRICE")) {
				t.setPrice(res.substring(res.indexOf("=")+1, res.length()).equalsIgnoreCase("")?null:new BigDecimal(res.substring(res.indexOf("=")+1, res.length())));
			}
			
			if(res.substring(0, res.indexOf("=")).equalsIgnoreCase("CLOSE_PRICE")) {
				t.setClose_price(res.substring(res.indexOf("=")+1, res.length()).equalsIgnoreCase("")?null:new BigDecimal(res.substring(res.indexOf("=")+1, res.length())));
			}
		
			if(res.substring(0, res.indexOf("=")).equalsIgnoreCase("CURRENCY")) {
				t.setCurrency(res.substring(res.indexOf("=")+1, res.length()));
			}
			
			if(res.substring(0, res.indexOf("=")).equalsIgnoreCase("RIC")) {
				t.setRic(res.substring(res.indexOf("=")+1, res.length()));
			}
		
		}
		return t;
	}
	
}
