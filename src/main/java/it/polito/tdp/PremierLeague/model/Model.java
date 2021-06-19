package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	private PremierLeagueDAO dao ;
	private SimpleDirectedWeightedGraph <Player, DefaultWeightedEdge> grafo ;
	private Map <Integer , Player> idMap;
	private boolean flagGrafo;
	
	public Model() {
		this.dao = new PremierLeagueDAO () ;
		
	}
	

	public void creaGrafo(Double goal) {
		
		this.grafo = new SimpleDirectedWeightedGraph <> (DefaultWeightedEdge.class) ;
		
		this.idMap = new HashMap<>() ;
		
		this.dao.loadIdMap(goal, idMap) ;
		
		Graphs.addAllVertices(this.grafo, this.idMap.values()) ;
		
		List<Adiacente> edge = new ArrayList<>() ;
		
		edge = this.dao.getEdge(this.idMap) ;
		
		for(Adiacente ed : edge) {
			Graphs.addEdge(this.grafo, ed.getP1(), ed.getP2(), ed.getPeso()) ;
		}
		
		this.flagGrafo = true;
		
	}
	
	public boolean isCreateGRaf() {
		return this.flagGrafo;
	}
	
	public String infoGrafo () {
		if(this.flagGrafo) {
			String s = "GRAFO CREATO \n#VERTICI= "+this.grafo.vertexSet().size()
					+"\n#ARCHI= "+ this.grafo.edgeSet().size();
			return s;
		}
		return null;
	}


	public List<Player> getBest() {
		double bestTime = this.cercaBestTime();
		//System.out.println(bestTime);
		List<Player> best = new ArrayList<>();
		
		for(Player p : this.grafo.vertexSet()) {
			if(this.calcolaDelta(p) == (bestTime)) {
				best.add(p);
				//System.out.println(p);
			}
		}
		
		return best;
	}


	private double cercaBestTime() {
		
		double delta_best = -1.0;
		
		for(Player p : this.grafo.vertexSet()) {
			double delta = this.calcolaDelta(p);
			if(delta_best == -1 || delta > delta_best) {
				delta_best = delta;
				System.out.println(p);
				System.out.println(delta);
			}
		}
		
		return delta_best;
	}


	private double calcolaDelta(Player p) {
		int min =(int) this.grafo.outDegreeOf(p);
		
		return min ;
	}


	public List<Adiacente> getBattutti(Player p) {
		List<Adiacente> result = new ArrayList<>() ;
		for(DefaultWeightedEdge ed : this.grafo.outgoingEdgesOf(p)) {
			Double peso  = this.grafo.getEdgeWeight(ed);
			Player bat = Graphs.getOppositeVertex(this.grafo, ed, p) ;
			result.add(new Adiacente(p, bat, peso));
		}
		Collections.sort(result, new OrderBattuti());
		
		return result;
	}

}
