package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private Map<Integer ,Team> idMap;
	private Graph<Team, DefaultWeightedEdge> grafo;
	private Simulatore sim;
	private List<Team> classifica;
	
	public Model() {
		dao = new PremierLeagueDAO();
		idMap = new HashMap<>();
		dao.listAllTeams(idMap);
		sim = new Simulatore();
		classifica = new ArrayList<>();
	}
	
	public void creaGrafo() {
		
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//vertici
		Graphs.addAllVertices(grafo, idMap.values());
		
		//archi
		classifica = dao.getClassifica(idMap);
		
		for(Team t1: classifica) {
			for (Team t2: classifica) {
				DefaultWeightedEdge e = grafo.getEdge(t2, t1);
				if(!t1.equals(t2) && e== null) {
					int posizione = t1.getScore()-t2.getScore();
					if(posizione > 0) {
						Graphs.addEdge(grafo, t1, t2, posizione);
					}else if(posizione < 0) {
						Graphs.addEdge(grafo, t2, t1, -posizione);
					}
				}

			}
		}
				
	}
	
	public List<SquadraDifferenza> getMigliori(Team team){
		List<SquadraDifferenza> result = new ArrayList<>();
		
		for(Team t: Graphs.predecessorListOf(grafo, team)) {
			DefaultWeightedEdge e = grafo.getEdge(t, team);
			result.add(new SquadraDifferenza(t,grafo.getEdgeWeight(e)));
		}
		Collections.sort(result);
		return result;
	}
	
	public List<SquadraDifferenza> getPeggiori(Team team){
		List<SquadraDifferenza> result = new ArrayList<>();
		
		for(Team t: Graphs.successorListOf(grafo, team)) {
			DefaultWeightedEdge e = grafo.getEdge(team, t);
			result.add(new SquadraDifferenza(t,grafo.getEdgeWeight(e)));
		}
		Collections.sort(result);
		return result;
	}
	
	public List<Team> getSquadre(){
		return new ArrayList<>(idMap.values());
	}
	
	public int getSizeVertex() {
		return grafo.vertexSet().size();
	}
	
	public int getSizeEdge() {
		return grafo.edgeSet().size();
	}
	
	public void setSim(int N, int X) {
		sim.init(N, X, dao.listAllMatches(), classifica);
		sim.run();
	}
	
	public double getMediaReporter() {
		return sim.getMedia();
	}
	
	public int getPartiteCritiche() {
		return sim.getCritiche();
	}
}
