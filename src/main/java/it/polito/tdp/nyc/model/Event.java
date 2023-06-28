package it.polito.tdp.nyc.model;

public class Event implements Comparable<Event> {
	
	public enum EventType{
		SHARE,
		STOP
		}

	private int durata;
	private int tempo;
	private NTA nta;
	private EventType type;
	
	public Event(EventType type,int durata, int tempo, NTA nta) {
		super();
		this.type = type;
		this.durata = durata;
		this.tempo = tempo;
		this.nta = nta;
	}
	
	

	public EventType getType() {
		return type;
	}

	public int getDurata() {
		return durata;
	}

	public int getTempo() {
		return tempo;
	}

	public NTA getNta() {
		return nta;
	}

	@Override
	public int compareTo(Event o) {
		return this.getTempo()- o.getTempo();
	}

	
	
	
	
}
