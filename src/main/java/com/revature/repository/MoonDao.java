package com.revature.repository;

import java.sql.*;
import java.util.*;

import com.revature.models.Moon;
import com.revature.utilities.ConnectionUtil;

public class MoonDao {
    
    public List<Moon> getAllMoons() {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "SELECT * FROM moons";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			List<Moon> moons = new ArrayList<>();
			while (rs.next()) {
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));
				moons.add(moon);
			}
			return moons;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public Moon getMoonByName(String username, String moonName) {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "SELECT * FROM moons m INNER JOIN planets p ON m.my_planet_id=p.id INNER JOIN users u ON p.owner_id=u.id WHERE u.username=? AND m.name=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, moonName);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));
				return moon;
			}
			return new Moon();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Moon getMoonById(String username, int moonId) {
        try(Connection connection = ConnectionUtil.createConnection()) {
            String sql = "SELECT * FROM moons m INNER JOIN planets p ON m.my_planet_id=p.id INNER JOIN users u ON p.owner_id=u.id WHERE u.username=? AND m.id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setInt(2, moonId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Moon moon = new Moon();
                moon.setId(rs.getInt(1));
                moon.setName(rs.getString(2));
                moon.setMyPlanetId(rs.getInt(3));
                return moon;
            }
            return new Moon();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

	public Moon createMoon(String username, Moon m) {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "INSERT INTO moons(name, my_planet_id) VALUES (?, (SELECT p.id FROM planets p INNER JOIN users u ON p.owner_id=u.id WHERE u.username=?))";
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, m.getName());
			statement.setString(2, username);
			statement.executeUpdate();
	
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				m.setId(rs.getInt(1));
				return m;
			}
			return new Moon();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void deleteMoonById(int moonId) {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "DELETE FROM moons WHERE id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, moonId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Moon> getMoonsFromPlanet(int planetId) {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "SELECT * FROM moons WHERE my_planet_id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, planetId);
			ResultSet rs = statement.executeQuery();
			List<Moon> moons = new ArrayList<>();
	
			while(rs.next()) {
				Moon moon = new Moon();
				moon.setId(rs.getInt(1));
				moon.setName(rs.getString(2));
				moon.setMyPlanetId(rs.getInt(3));
				moons.add(moon);
			}
			return moons;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
/*	public static void main(String[] args) {
		MoonDao moonDao = new MoonDao();
		try {
			System.out.println(moonDao.getAllMoons());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}*/	
}
