package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
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

}
