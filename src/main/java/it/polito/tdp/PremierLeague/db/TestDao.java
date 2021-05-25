package it.polito.tdp.PremierLeague.db;

import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Team;

public class TestDao {

	public static void main(String[] args) {
		TestDao testDao = new TestDao();
		testDao.run();
	}
	
	public void run() {
		PremierLeagueDAO dao = new PremierLeagueDAO();
		Map<Integer,Team> map = new HashMap<>();
//		System.out.println("Players:");
//		System.out.println(dao.listAllPlayers());
		System.out.println("Teams:");
		dao.listAllTeams(map);

//		System.out.println(dao.listAllTeams());
//		System.out.println("Actions:");
//		System.out.println(dao.listAllActions());
//		System.out.println("Matches:");
//		System.out.println(dao.listAllMatches());
		System.out.println(dao.getClassifica(map));		
	}

}
