package fr.bletrazer.musicraft.music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.bletrazer.musicraft.Main;

public class Note {
	private Sound note;
	private Float volume = 1.0f;
	private Float pitch = 1.0f;
	
	// --------------- constructors --------------------
	
	public Note() {

	}
	
	public Note(Sound note) {
		this.setNote(note);
		this.setVolume(1f);
		this.setPitch(1f);
		
	}
	
	public Note(Sound note, Float volume, Float pitch) {
		this.setNote(note);
		this.setVolume(volume);
		this.setPitch(pitch);
	}
	
	// ------------------ public -----------------------
	
	public Boolean playNote(ArrayList<UUID> recipientsUuid ) {
		Boolean played = false;
		
		if(this.getNote() != null ) {
			for(UUID uuid : recipientsUuid ) {
				Player player = Bukkit.getPlayer(uuid);
				if(player != null && player.isOnline() ) {
					player.playSound(player.getLocation().clone().add(0,  1,  0), this.getNote(), this.getVolume(), this.getPitch());
				}
				
			}
		}
		
		return played;
	}
	
	public String toString() {
		return String.format("Sound: %s, Volume: %s, Pitch: %s", this.getNote().name().toLowerCase());
	}
	
	public static Note ValueOf(String str) {
		Note res = new Note();
		
		String noSpaces = str.replace(" ", "");
		
		List<String> allParts = Arrays.asList(noSpaces.split(",") );
		
		for(String part : allParts ) {
			if(part.contains(":")) {
				
				List<String> subParts = Arrays.asList(part.split(":") );
				String type = subParts.get(0);
				String value = subParts.get(1);
				
				try {
				
					if((type.equalsIgnoreCase("sound") ) ) {
						Sound sound = Sound.valueOf(value.toUpperCase());
						res.setNote(sound);
						
					} else if (type.equalsIgnoreCase("volume") ) {
						Float volume = Float.valueOf(value);
						res.setVolume(volume);
						
					} else if (type.equalsIgnoreCase("pitch") ) {
						Float pitch = Float.valueOf(value);
						res.setPitch(pitch);
					}
					
				} catch(IllegalArgumentException e) {
					res = null;
					break;
				}
				
			} else {
				if(!part.equalsIgnoreCase("empty")) {
					res = null;
					Main.getInstance().getLogger().log(Level.WARNING, String.format("Parsing error: no \"MusicNote\" data found!"));
					break;
				}
			}
			
		}
		
		return res;
	}
	
	
	// ----------- setters || getters ------------------
	
	public Sound getNote() {
		return note;
	}

	public void setNote(Sound note) {
		this.note = note;
	}

	public Float getVolume() {
		return volume;
	}

	public void setVolume(Float volume) {
		this.volume = volume;
	}

	public Float getPitch() {
		return pitch;
	}

	public void setPitch(Float pitch) {
		this.pitch = pitch;
	}
	
	
	
}
