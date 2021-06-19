package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Adiacente;
import it.polito.tdp.PremierLeague.model.Player;

public class PremierLeagueDAO {
	
//	public List<Player> listAllPlayers(){
//		String sql = "SELECT * FROM Players";
//		List<Player> result = new ArrayList<Player>();
//		Connection conn = DBConnect.getConnection();
//
//		try {
//			PreparedStatement st = conn.prepareStatement(sql);
//			ResultSet res = st.executeQuery();
//			while (res.next()) {
//
//				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
//				
//				result.add(player);
//			}
//			conn.close();
//			return result;
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void loadIdMap(Double goal, Map<Integer, Player> idMap) {
		String sql = "SELECT p.`PlayerID` as id , p.`Name` as nome , avg(a.`Goals`) as media "
				+ "FROM `Players` as p, `Actions` as a "
				+ "WHERE p.`PlayerID`=a.`PlayerID`  "
				+ "GROUP BY p.`PlayerID`, p.`Name` "
				+ "HAVING avg(a.`Goals`) > ? ";
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, goal);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Player p = idMap.get(res.getInt("id")) ;
				if(p == null) {
					p = new Player (res.getInt("id"), res.getString("nome"), res.getDouble("media")) ;
					idMap.put(p.getPlayerID(), p);
				}
				
				
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		
	}

	public List<Adiacente> getEdge(Map<Integer, Player> idMap) {
		String sql = "SELECT a1.`PlayerID`as id1 , a2.`PlayerID` as id2, sum(a1.`TimePlayed`)-sum(a2.`TimePlayed`) as peso "
				+ "FROM `Actions` as a1, `Actions` as a2 "
				+ "WHERE a1.`PlayerID` <> a2.`PlayerID` "
				+ "	AND a1.`Starts` = 1 "
				+ "	AND a2.`Starts` = 1 "
				+ "	AND a2.`TeamID` <> a1.`TeamID` "
				+ "	AND a1.`MatchID`=a2.`MatchID`"
				+ "GROUP BY a1.`PlayerID`, a2.`PlayerID`  "
				+ "HAVING sum(a1.`TimePlayed`)-sum(a2.`TimePlayed`) > 0 ";
		List<Adiacente> result = new ArrayList<>() ;
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Player p1 = idMap.get(res.getInt("id1")) ;
				Player p2 = idMap.get(res.getInt("id2")) ;
				
				if(p1 != null && p2 != null) {
					result.add(new Adiacente (p1,p2, res.getDouble("peso")));
				}
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
