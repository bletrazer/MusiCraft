package fr.bletrazer.musicraft.music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

import fr.bletrazer.musicraft.Main;

public class Note {
	private ArrayList<Sound> notes = new ArrayList<>();
	private ArrayList<Float> volumes = new ArrayList<>();
	private ArrayList<Float> pitches = new ArrayList<>();
	
	// --------------- constructors --------------------
	
	public Note() {

	}
	
	public Note(ArrayList<Sound> notes, ArrayList<Float> volumes, ArrayList<Float> pitch) {
		this.setNotes(notes);
		this.setVolumes(volumes);
		this.setPitches(pitches);
	}
	
	// ------------------ public -----------------------
	
	public Boolean playNote(ArrayList<UUID> recipientsUuid ) {
		Boolean played = false;
		
		if(this.getNotes() != null ) {
			for(UUID uuid : recipientsUuid ) {
				Player player = Bukkit.getPlayer(uuid);
				if(player != null && player.isOnline() ) {
					
					for(Integer index = 0; index < this.getNotes().size(); index ++) {
						Sound s = this.getNotes().get(index);
						Float pitch = this.getPitches().size() -1 >= index ? this.getPitches().get(index) : 1.0f;
						Float volume = this.getVolumes().size() -1 >= index ? this.getVolumes().get(index) : 1.0f;
						player.playSound(player.getLocation().clone().add(0,  1,  0), s, volume, pitch);
					}
				}
				
			}
		}
		
		return played;
	}
	
	public String toString() {
		List<String> stringlist = new ArrayList<>();
		
		for(Integer index = 0; index < this.getNotes().size(); index ++) {
			List<String> templist = new ArrayList<>();
			
			Sound s = this.getNotes().get(index);
			if(s != null) {
				templist.add(String.format("Sound: %s", s.name().toLowerCase()) );
			}
			
			Float pitch = this.getPitches().size() >= index ? null : this.getPitches().get(index);
			if(pitch != null) {
				templist.add(String.format("Pitch: %s", pitch) );
			}
			
			String volume = this.getVolumes().size() >= index ? null : this.getVolumes().get(index).toString();
			if(volume != null) {
				templist.add(String.format("Volume: %s", volume) );
			}
			
			stringlist.add(StringUtils.join(templist, ", ").replace("[", "").replace("]", "") );
		}
		
		return StringUtils.join(stringlist, ", ").replace("[", "").replace("]", "");
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
						res.addNote(sound);
						
					} else if (type.equalsIgnoreCase("volume") ) {
						Float volume = Float.valueOf(value);
						res.addVolume(volume);
						
					} else if (type.equalsIgnoreCase("pitch") ) {
						Float pitch = Float.valueOf(value);
						res.addPitch(pitch);
					}
					
				} catch(IllegalArgumentException e) {
					res = null;
					break;
				}
				
			} else {
				if(part != null ) {
					res = null;
					Main.getInstance().getLogger().log(Level.WARNING, String.format("Parsing error: no \"MusicNote\" data found!"));
					break;
				}
			}
			
		}
		
		return res;
	}
	
	// ----------- setters || getters ------------------

	public ArrayList<Sound> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<Sound> notes) {
		this.notes = notes;
	}

	public void addNote(Sound sound) {
		this.getNotes().add(sound);
	}

	public ArrayList<Float> getVolumes() {
		return volumes;
	}

	public void setVolumes(ArrayList<Float> volumes) {
		this.volumes = volumes;
	}

	public void addVolume(Float volume) {
		this.getVolumes().add(volume);
	}

	public ArrayList<Float> getPitches() {
		return pitches;
	}

	public void setPitches(ArrayList<Float> pitches) {
		this.pitches = pitches;
	}

	public void addPitch(Float pitch) {
		this.getPitches().add(pitch);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}