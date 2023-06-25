package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.List;

public class NTA {
	
	private String NTACode;
    private String NTAName;
    private List<String> SSIDs;
	
    public NTA(String nTACode, String nTAName) {
		super();
		NTACode = nTACode;
		NTAName = nTAName;
		SSIDs= new ArrayList<>();
	}

	public String getNTACode() {
		return NTACode;
	}

	public String getNTAName() {
		return NTAName;
	}
    
	public void addSSID(String SSID) {
		this.SSIDs.add(SSID);
	}
    
    public List<String> getSSIDs(){
    	return SSIDs;
    }
}
