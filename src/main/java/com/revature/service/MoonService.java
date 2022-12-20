package com.revature.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import com.revature.models.Moon;
import com.revature.repository.MoonDao;

public class MoonService {
	public static Logger logger = LoggerFactory.getLogger(MoonService.class);
	private MoonDao dao;
	
	public MoonService(){
		this.dao = new MoonDao();
	}

	public List<Moon> getAllMoons() {
		return dao.getAllMoons();
	}

	public Moon getMoonByName(String username, String moonName) {
		return dao.getMoonByName(username, moonName);
	}

	public Moon getMoonById(String username, int moonId) {
		return dao.getMoonById(username, moonId);
	}

	public Moon createMoon(String username, Moon m) {
		return dao.createMoon(username, m);
	}

	public void deleteMoonById(int moonId) {
		dao.deleteMoonById(moonId);
	}

	public List<Moon> getMoonsFromPlanet(int planetId) {
		return dao.getMoonsFromPlanet(planetId);
	}
}
