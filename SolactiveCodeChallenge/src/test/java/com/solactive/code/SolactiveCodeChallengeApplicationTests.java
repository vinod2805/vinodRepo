package com.solactive.code;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.solactive.code.model.Tick;
import com.solactive.code.repository.TickRepository;
import com.solactive.code.services.TickServiceImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class SolactiveCodeChallengeApplicationTests {
	
	@Autowired
	TickServiceImpl tickServiceImpl;
	
	@Autowired
	private TickRepository tickRepository;

	@Test
	@Order(1)
	public void testCreateTick() {
		String result = tickServiceImpl.createTick("TIMESTAMP=2020-05-09T13:49:54.000|PRICE=5.24|CLOSE_PRICE=|CURRENCY=EUR|RIC=AAPL.OQ");
		assertEquals("Success", result);
	}
	
	@Test
	@Order(2)
	public void testGetAllTicks() {
		List<Tick> ticks = tickServiceImpl.getAllTicks();
		assertThat(ticks).size().isGreaterThan(0);
	}
	
	@Test
	@Order(3)
	public void testGetTicksByRic() {
		List<Tick> ticks = tickServiceImpl.getTicksByRic("AAPL.OQ");
		assertEquals("AAPL.OQ", ticks.get(0).getRic());
	}
	
	@Test
	@Order(4)
	public void testDeleteAll() {
		tickRepository.deleteAll();
		List<Tick> ticks = tickServiceImpl.getAllTicks();
		assertThat(ticks).size().isZero();
	}

}
