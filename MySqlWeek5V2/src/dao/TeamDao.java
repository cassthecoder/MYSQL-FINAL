package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Team;

public class TeamDao {
	
	private Connection connection;
	private MemberDao memberDao;
	private final String GET_TEAMS_QUERY = "SELECT * FROM teams";
	private final String GET_TEAM_BY_ID_QUERY = "SELECT * FROM teams WHERE id = ?";
	private final String CREATE_TEAM_UPDATE = "INSERT INTO teams(name) VALUES (?)";
	
	public TeamDao() {
		connection = DBConnection.getConnection();
		memberDao = new MemberDao();
	}
	
	public List<Team> getTeams() throws SQLException{
		ResultSet rs = connection.prepareStatement(GET_TEAMS_QUERY).executeQuery();
		List<Team> teams = new ArrayList<Team>();
		
		while(rs.next()) {
			teams.add(populateTeam(rs.getInt(1), rs.getString(2)));
		}
		
		return teams;
	}
	
	public Team getTeamById(int id) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(GET_TEAM_BY_ID_QUERY);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return populateTeam(rs.getInt(1), rs.getString(2));
	}
	
	private Team populateTeam(int id, String name) throws SQLException {
		return new Team(id, name, memberDao.getMembersByTeamId(id));
	}
	
	public void createTeam(String teamName) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(CREATE_TEAM_UPDATE);
		ps.setString(1, teamName );
		ps.executeUpdate();
	}
}
