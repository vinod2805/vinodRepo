package com.solactive.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.solactive.code.model.Tick;

public interface TickRepository extends JpaRepository<Tick, Long> {
	

}
