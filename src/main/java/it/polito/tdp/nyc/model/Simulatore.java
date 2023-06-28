package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.nyc.model.Event.EventType;

public class Simulatore {
	
	//Dati in input
	private double probShare;
	private int durata;
	
	//Stato simulazione
	private Graph<NTA,DefaultWeightedEdge> grafo;
	private Map<NTA,Integer> numberShares;
	private List<NTA> vertici;
	
	//Dati in output
	private Map<NTA,Integer> numberTotShares;
	
	//Coda eventi
	private PriorityQueue<Event> queue ;

	public Simulatore(double probShare, int durata, Graph<NTA, DefaultWeightedEdge> grafo) {
		super();
		this.probShare = probShare;
		this.durata = durata;
		this.grafo = grafo;
	}
	
	public void initialize() {
		
		this.numberShares= new HashMap<>();
		this.numberTotShares = new HashMap<>();
		
		for(NTA n : this.grafo.vertexSet()) {
			this.numberShares.put(n, 0);
			this.numberTotShares.put(n, 0);
		}
		
		vertici=new ArrayList<>(this.grafo.vertexSet());
		
		queue = new PriorityQueue<>();
		
		for(int t= 0; t<=100;t++) {
			if(Math.random()<this.probShare) {
				int n =(int)( Math.random()*this.vertici.size());
				queue.add(new Event(EventType.SHARE,this.durata,t,this.vertici.get(n)));
			}
		}
	}
	
	public void run() {
		
		while(!queue.isEmpty()) {
			Event e = queue.poll();
			int timeE = e.getTempo();
			
			if(timeE>100) {
				break;
			}
			
			int durataE = e.getDurata();
			NTA ntaE = e.getNta();
			
			switch(e.getType()) {
			case SHARE:
				
				this.numberShares.put(ntaE,this.numberShares.get(ntaE)+1 );
				this.numberTotShares.put(ntaE, this.numberTotShares.get(ntaE)+1);
				
				this.queue.add(new Event(EventType.STOP,durataE+timeE,0,ntaE));
				NTA nuovo = findAdj(ntaE);
				this.queue.add(new Event(EventType.SHARE,durataE/2,timeE+1,nuovo));
				
				break;
				
			case STOP:
				
				this.numberShares.put(ntaE, this.numberShares.get(ntaE)-1);
				
				break;
			}
		}
	}

	private NTA findAdj(NTA ntaE) {
		
		List<NTA> adj = new ArrayList<>(Graphs.neighborListOf(this.grafo, ntaE));
		double pesoMax=0;
		NTA adjBest= null;
		for(NTA n : adj) {
			DefaultWeightedEdge e = this.grafo.getEdge(n, ntaE);
			if (this.grafo.getEdgeWeight(e)>pesoMax) {
				pesoMax = this.grafo.getEdgeWeight(e);
				adjBest = n;
			}
		}
		
		return adjBest;
	}
	
	public Map<NTA,Integer> datiInOutput(){
		return this.numberTotShares;
	}
}