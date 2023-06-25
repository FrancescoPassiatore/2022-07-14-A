package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.nyc.model.Hotspot;
import it.polito.tdp.nyc.model.NTA;

public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<NTA> getNTAByBorough(String borough){
		
		String sql = "SELECT DISTINCT n.NTACode,n.NTAName,n.SSID "
				+ "FROM nyc_wifi_hotspot_locations n "
				+ "WHERE n.Borough=? "
				+ "ORDER BY n.NTACode ";
		
		List<NTA> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, borough);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				//Per inserire il primo NTA
				if(result.isEmpty()) {
					NTA n =new NTA(res.getString("NTACode"),res.getString("NTAName"));
					result.add(n);
					n.addSSID(res.getString("SSID"));
				}else {
					
				boolean controllo=false;
				//Controllo se gia appartiene al resultSet, in quel casso aggiungere l'ssid alla lista
				for(NTA nta : result) {
				 if(nta.getNTACode().compareTo(res.getString("NTACode"))==0) {
					nta.addSSID(res.getString("SSID"));
					controllo=true;
					}}
				//Non presente , quindi aggiungo uno nuovo
				if(controllo==false) {
					NTA n =new NTA(res.getString("NTACode"),res.getString("NTAName"));
					result.add(n);
					n.addSSID(res.getString("SSID"));
				}
				}
					
				}
			
					
					
					
			
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	
	
	
}
