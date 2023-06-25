package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	NYCDao dao;
	List<Hotspot> allHotspots;
	SimpleWeightedGraph<NTA,DefaultWeightedEdge> grafo;
	Map<Integer,NTA> mapNTA;
	Simulatore simulator;
	Map<NTA,Integer> numTotShare;
	
	public Model() {
		dao = new NYCDao();
		allHotspots = new ArrayList<Hotspot>(dao.getAllHotspot());
		mapNTA = new HashMap<>();
		
		}
	
	public List<String> simulazione(double probability, int duration) {
		
		simulator=new Simulatore(duration,probability,this.grafo);
		
		simulator.initialize();
		simulator.run();

		numTotShare = new HashMap<NTA,Integer>(simulator.datiOutput());
	
		List<String> output = new ArrayList<>();
		for(NTA n : numTotShare.keySet()) {
			output.add(""+n.getNTACode()+" "+ numTotShare.get(n) );
		}
		return output;
	}
	
	
	public void creaGrafo(String b) {
		
		this.grafo = new SimpleWeightedGraph<NTA,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.NTAsByBorough(b));
		int peso=0;
		for(NTA n1: this.grafo.vertexSet()) {
			for(NTA n2:this.grafo.vertexSet()) {
				
				if(!n1.equals(n2)) {
					peso=0;
					int conta=0;
					if(n1.getSSIDs().size()>= n2.getSSIDs().size()) {
						for(String s1 :n1.getSSIDs()) {
							conta=0;
							for(String s2:n2.getSSIDs()) {
								if(s2.compareTo(s1)!=0) {
									conta++;
									}}
							if(conta == n2.getSSIDs().size()) {
							 peso++;
						}}
				}
					else {
						for(String s2 :n2.getSSIDs()) {
							conta=0;
							for(String s1:n1.getSSIDs()) {
								if(s1.compareTo(s2)!=0) {
									conta++;
									}}
							if(conta == n1.getSSIDs().size()) {
							 peso++;
						}}
						
					}
				Graphs.addEdge(this.grafo, n1, n2, peso);}}}
					
			
	}
	
	public List<String> analisiArchi() {
		
		List<String> analisi = new ArrayList<>();
		
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)> this.pesoMedio()) {
				String s;
				s = ""+this.grafo.getEdgeSource(e).getNTACode()+ " " +this.grafo.getEdgeTarget(e).getNTACode()
						+" "+this.grafo.getEdgeWeight(e)+"\n";
				analisi.add(s);
			}
			
		}
		
		return analisi;
		
	}
	
	
	public Integer getNVertices() {
		return this.grafo.vertexSet().size();
		
	}
	
	public Integer getNEdges() {
		return this.grafo.edgeSet().size();
		
	}
	
	public List<NTA> NTAsByBorough(String b){
		List<NTA> ntas = this.dao.getNTAByBorough(b);
		return ntas;
	}
	
	public List<String> allBorough(){
		
		List<String> boroughs= new ArrayList<String>();
		
		for(Hotspot h : this.allHotspots) {
			if(!boroughs.contains(h.getBorough())) {
				boroughs.add(h.getBorough());
				}
		}
		return boroughs;
	}
	
	public Double pesoMedio() {
		double pesoTotale=0.0;
		Set<DefaultWeightedEdge> set = new HashSet<>(this.grafo.edgeSet());
		
		for(DefaultWeightedEdge e1 : set) {
			pesoTotale+= this.grafo.getEdgeWeight(e1);}
		
		return (pesoTotale/this.grafo.edgeSet().size());
		}
			
		
	
}
