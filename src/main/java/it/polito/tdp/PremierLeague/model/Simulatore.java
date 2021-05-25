package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simulatore {

	
	//input fissi
	private int nReporter;
	private int xCritico;
	private List<Match> partite;
	private List<Team> classifica;
	
	//dati
	private Map<Team,Integer> mapReporter;
	
	//output
	private double REPORTERMEDI;
	private int PARTITECRITICHE;
	
	public void init(int N, int X, List<Match> matches, List<Team> squadre) {
		
		this.nReporter = N;
		this.xCritico = X;
		this.partite = matches;
		this.classifica = squadre;
		
		REPORTERMEDI = 0;
		PARTITECRITICHE = 0;
		mapReporter = new HashMap<>();
		
		for(Team t: squadre) {
			mapReporter.put(t, N);
		}
		
	}
	
	public void run() {
		for(Match m: partite) {
			Team tVincente = null;
			Team tPerdente = null;
			Team home = new Team(m.getTeamHomeID(),m.getTeamHomeNAME());
			Team away = new Team(m.getTeamAwayID(),m.getTeamAwayNAME());
			int nReporterPartita = mapReporter.get(home) + mapReporter.get(away);
			
			this.REPORTERMEDI += nReporterPartita;
			if(nReporterPartita < this.xCritico) {
				this.PARTITECRITICHE ++;
			}
			
			switch(m.getResultOfTeamHome()) {
				case 1:
					tVincente = home;
					tPerdente = away;
					break;
				case -1:
					tVincente = home;
					tPerdente = away;
					break;
				case 0:
					continue;
			}
			
			double probVitt = Math.random();
			double probPerd = Math.random();
			if(probVitt<=0.5) {
				int indiceVincente = this.classifica.indexOf(tVincente);
				if( indiceVincente != 0 && this.mapReporter.get(tVincente) > 0) {
					this.mapReporter.put(tVincente, mapReporter.get(tVincente)-1);
					
					int indiceCasuale = (int) Math.floor( Math.random() * (indiceVincente));
					Team squadraAumenta = this.classifica.get(indiceCasuale);
					
					this.mapReporter.put(squadraAumenta, this.mapReporter.get(squadraAumenta) +1);
					
				}
			}
			
			if(probPerd <= 0.2) {
				int nReporterPersi = (int) Math.floor(Math.random()*(this.mapReporter.get(tPerdente)+1));
				int indicePerdente = this.classifica.indexOf(tPerdente);

				if(nReporterPersi != 0 && indicePerdente != (this.classifica.size()-1) ) {
					this.mapReporter.put(tPerdente, mapReporter.get(tPerdente)-nReporterPersi);
					
					int indiceCasuale = (int) Math.floor(Math.random()*(this.classifica.size()-1-indicePerdente)) + (indicePerdente +1);
					Team squadraAumenta = this.classifica.get(indiceCasuale);
					
					this.mapReporter.put(squadraAumenta, this.mapReporter.get(squadraAumenta) +nReporterPersi);

					
				}
			}

		}
		
		this.REPORTERMEDI = REPORTERMEDI/partite.size();
	}
	
	public double getMedia() {
		return this.REPORTERMEDI;
	}
	
	public int getCritiche() {
		return this.PARTITECRITICHE;
	}
	
	
}
