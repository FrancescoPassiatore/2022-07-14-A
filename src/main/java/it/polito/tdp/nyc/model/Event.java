package it.polito.tdp.nyc.model;

public class Event implements Comparable<Event> {

	public enum EventType{
		SHARE,
		STOP
		}
	
	private int time;
	private int duration;
	private NTA nta;
	private EventType type;
	
	
	public Event(int time, int duration, NTA nta, EventType type) {
		super();
		this.time = time;
		this.duration = duration;
		this.nta = nta;
		this.type = type;
	}


	public int getTime() {
		return time;
	}


	public int getDuration() {
		return duration;
	}


	public NTA getNta() {
		return nta;
	}


	public EventType getType() {
		return type;
	}
	
	

	@Override
	public int compareTo(Event o) {
		
		return this.time-o.time;
	}
	

	
	
}
