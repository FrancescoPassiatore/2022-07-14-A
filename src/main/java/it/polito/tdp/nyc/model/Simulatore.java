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
	private int duration;
	private double probShare;
	
	
	//Stato del sistema
	Graph<NTA,DefaultWeightedEdge> graph; //Grafo da esercizio punto 1
	Map<NTA,Integer> numberShares;
	
	//Output
	Map<NTA,Integer> numberTotShares;
	
	//Creazione Queue
	PriorityQueue<Event> queue;
	
	List<NTA> vertici ;
	
	//Costruttore per stabilire i parametri iniziali
	public Simulatore(int duration, double probShare, Graph<NTA, DefaultWeightedEdge> graph) {
		
		this.duration = duration;
		this.probShare = probShare;
		this.graph = graph;
	
	}

	public void initialize() {
		
		//Inizializzazione
		vertici = new ArrayList<>(this.graph.vertexSet());
		
		//creazione mappe
		numberShares = new HashMap<NTA,Integer>();
		numberTotShares=new HashMap<NTA,Integer>();
		
		//Inserire vertici(NTA) in lista e po aggiungere in mappa  con numshare =0
		for(NTA n : vertici) {
			this.numberShares.put(n, 0);
			this.numberTotShares.put(n, 0);
		}
		
		
		//Creazione evento
		queue= new PriorityQueue<Event>();
		
		//Inserimento eventi
		for(int t=0;t<100;t++) {
			double rand= Math.random();
			if( rand <= this.probShare) {
				int n = (int) (Math.random()* this.vertici.size());
				queue.add(new Event(t,this.duration,this.vertici.get(n),EventType.SHARE));
				}
			}
		}
	
	public void run() {
		
		while(!this.queue.isEmpty()) {
			
			Event e = this.queue.poll();
			
			int time = e.getTime();
			if(time>=100) {
				for(NTA n : this.numberShares.keySet()) {
					this.numberTotShares.put(n, this.numberShares.get(n));}
				break;
				}
			
			int durationNTA = e.getDuration();
			EventType type = e.getType();
			NTA nta = e.getNta();
			
			switch(type) {
			
			case SHARE:
				//Chiudere la condivisione del Nta
				this.numberShares.put(nta,this.numberShares.get(nta)+1);
				this.queue.add(new Event(time+durationNTA,0,nta,EventType.STOP));
				
				//Ricondividere con quello adiacente
				NTA nuovo = calcoloAdiacente(nta);
				if(nuovo!=null)
				 this.queue.add(new Event(time+1,durationNTA/2,nuovo,EventType.SHARE));
				
				break;
			
			case STOP:	
				break;
			
			}
		}
			
	}

	
	
	
	private NTA calcoloAdiacente(NTA nta) {
		
		int max = -1;
		NTA best=null;
		
		for(DefaultWeightedEdge e : this.graph.outgoingEdgesOf(nta)) {
			NTA n = Graphs.getOppositeVertex(this.graph, e, nta);
			int peso = (int)this.graph.getEdgeWeight(e);
			
			if(peso>max && this.numberShares.get(n)==0) {
				max=peso;
				best=n;
			}
		}
		
		return best;
	}

	public Map<NTA,Integer> datiOutput(){
		return this.numberTotShares;
	}
}
