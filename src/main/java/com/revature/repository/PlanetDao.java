package com.revature.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.revature.utilities.ConnectionUtil;
import com.revature.models.Planet;

public class PlanetDao {
    
    public List<Planet> getAllPlanets(){
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "SELECT * FROM planets";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			List<Planet> planets = new ArrayList<>();

			while(rs.next()){
				Planet planet = new Planet();
				planet.setId(rs.getInt(1));
				planet.setName(rs.getString(2));
				planet.setOwnerId(rs.getInt(3));
				planets.add(planet);
			}
			return planets;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Planet getPlanetByName(String owner, String planetName) {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "SELECT * FROM planets WHERE name=? AND owner_id=(SELECT id FROM users WHERE username=?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, planetName);
			statement.setString(2, owner);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				Planet planet = new Planet();
				planet.setId(rs.getInt(1));
				planet.setName(rs.getString(2));
				planet.setOwnerId(rs.getInt(3));
				return planet;
			}
			return new Planet();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Planet getPlanetById(String username, int planetId) {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "SELECT * FROM planets WHERE id=? AND owner_id=(SELECT id FROM users WHERE username=?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, planetId);
			statement.setString(2, username);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				Planet planet = new Planet();
				planet.setId(rs.getInt(1));
				planet.setName(rs.getString(2));
				planet.setOwnerId(rs.getInt(3));
				return planet;
			}
			return new Planet();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Planet createPlanet(String username, Planet p) {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "INSERT INTO planets(name, owner_id) VALUES (?, (SELECT id FROM users WHERE username=?))";
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, p.getName());
			statement.setString(2, username);
			statement.executeUpdate();
	
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				p.setId(rs.getInt(1));
			}
			return p;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void deletePlanetById(int planetId) {
		try(Connection connection = ConnectionUtil.createConnection()) {
			String sql = "DELETE FROM planets WHERE id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, planetId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

/*	public static void main(String[] args) {
		PlanetDao planetDao = new PlanetDao();
		try {
			System.out.println(planetDao.getAllPlanets());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}*/	
}


