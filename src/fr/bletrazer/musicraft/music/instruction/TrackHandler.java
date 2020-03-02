package fr.bletrazer.musicraft.music.instruction;

import java.util.HashMap;
import java.util.UUID;

public class TrackHandler {
	private static HashMap<String, Track> cache = new HashMap<>();
	
	public static void add(Track track, Boolean override) {
		if (track != null && track.getName() != null) {
			if (override) {
				Track tempTrack = cache.get(track.getName());
				if(tempTrack != null) {
					tempTrack.setRunning(false);
				}
				
				cache.put(track.getName(), track);
				
			} else if (cache.get(track.getName()) == null) {
				cache.put(track.getName(), track);
			}
		}
	}
	
	public static void removeToAll(UUID uuid) {
		for(Track track : cache.values()) {
			track.removeRecipient(uuid);
		}
		
	}
	
	public static HashMap<String, Track> get() {
		return cache;
	}
	
	public static Track get(String trackname) {
		return cache.get(trackname);
	}
}
