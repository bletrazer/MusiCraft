package fr.bletrazer.musicraft.music.instruction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.configuration.file.YamlConfiguration;

import fr.bletrazer.musicraft.Main;
import fr.bletrazer.musicraft.TimerCore;

public class Track extends TimerCore {

	private String name;
	private ArrayList<Note> notes = new ArrayList<>();
	private Iterator<Note> iterator;
	private ArrayList<UUID> recipients = new ArrayList<>();
	private Boolean repeat = false;

	// --------- constructors ------------
	public Track() {
		super();

	}

	private Track(String name) {
		super();
		this.setName(name);
	}

	// ------------- timer
	@Override
	protected void onStart() {
		this.setIterator(notes.iterator());
	}

	@Override
	protected void onStop() {
		this.setRecipients(new ArrayList<>());
		
	}

	@Override
	protected void onScheduling(Boolean running) {
		Boolean stop = true;
		
		if(this.getRunning() && !this.getRecipients().isEmpty() ) {
			if (this.getIterator().hasNext() ) {
				getNextNote().playNote(this.getRecipients());
				stop = false;
				
			} else if(this.getRepeat() ){
				this.setIterator(notes.iterator());
				getNextNote().playNote(this.getRecipients());
				stop = false;
				
			}
		}
		
		if(stop) {
			this.setRunning(false);
		}
	}

	// ----------- public ----------------

	@Override
	public boolean equals(Object obj) {
		Boolean res = false;

		if (obj != null && obj instanceof Track) {
			Track track = (Track) obj;

			if (this.getName() != null && this.getName().equals(track.getName())) {
				if (this.getNotes() != null && this.getNotes().equals(track.getNotes())) {
					if (this.getSpeed() != null && this.getSpeed().equals(track.getTimerCore().getSpeed())) {
						res = true;

					}
				}
			}
		}

		return res;
	}

	public Boolean addRecipient(UUID uuid) {
		Boolean res = false;
		if(!this.getRecipients().contains(uuid)) {
			this.getRecipients().add(uuid);
			res = true;
		}
		
		return res;
	}

	public void addAll(ArrayList<UUID> uuidlist) {
		for (UUID uuid : uuidlist) {
			this.addRecipient(uuid);
		}
	}

	public void removeRecipient(UUID uuid) {
		this.getRecipients().remove(uuid);

		if (this.getRecipients().isEmpty()) {
			this.setRunning(false);
		}
	}

	// ----------------- handler ------------------

	private static final String DEFAULT_PATH = "tracks";
	private static File folder;

	private static File getTrackFile(String name) {
		return new File(Track.folder.getPath() + File.separator + name + ".yml");
	}

	public static void save(Track track) {
		File file = Track.getTrackFile(track.getName());
		YamlConfiguration toSave = YamlConfiguration.loadConfiguration(file);

		toSave.set("Track.Timings", track.getTimerCore().getSpeed() );
		toSave.set("Track.Repeat", track.getRepeat() == null ? false : track.getRepeat());
		toSave.set("Track.Notes", track.getNotes());

		try {
			toSave.save(file);
			Main.getInstance().getLogger().log(Level.INFO,
					String.format("\"%s.yml\" successfully saved!.", track.getName()));

		} catch (IOException e) {
			Main.getInstance().getLogger().log(Level.WARNING,
					String.format("An error as occured while saving Track \"%s\".", track.getName()));
			e.printStackTrace();
		}

	}

	public static Boolean loadTrack(String name) {
		Boolean action = false;
		File file = Track.getTrackFile(name);
		
		if(file.exists() ) {
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			if(config.getString("Track") != null) {
				
				String timingStr = config.getString("Track.Timing");
				List<?> noteslist = config.getList("Track.Notes");
				
				if(!timingStr.isEmpty() && timingStr != null && !noteslist.isEmpty() ) {
					Track toLoad = new Track(name);
					Boolean load = true;
					
					try {
						Integer timing = Integer.valueOf(timingStr);
						toLoad.getTimerCore().setSpeed(timing);
						
					} catch (NumberFormatException e) {
						load = false;
						
					}
					
					Integer noteNumber = 1;
					for(Object obj : noteslist) {
						if(obj instanceof String) {
							String str = (String) obj;
							
							if(!str.equalsIgnoreCase("empty") ){
								Note musicnote = Note.ValueOf(str);
								
								if(musicnote != null) {
									toLoad.setNextnote(musicnote);
									
								} else {
									load = false;
									
								}
							} else {
								toLoad.setNextnote(new Note());
							}
						} else {
							load = false;
							
						}
						
						if(!load) {
							Main.getInstance().getLogger().log(Level.WARNING, String.format("Error while parsing \"[%s]\" at line %s for \"%s.yml\".", (String) obj, noteNumber, name) );
							break;
						} else {
							noteNumber ++;
							
						}
					}
					
					Boolean repeat = config.getBoolean("Track.Repeat");
					
					if(repeat != null ) {
						toLoad.setRepeat(repeat);
					}
					
					if(load) {
						TrackHandler.add(toLoad, true); // true to override existing cache
						Main.getInstance().getLogger().log(Level.INFO, String.format("Track \"%s\" loaded!", name) );
						action = true;
					}
					
				}
			} else {
				Main.getInstance().getLogger().log(Level.INFO, String.format("Incrorrect file: \"%s\".", name) );
			}
			
			
			
		} else {
			Main.getInstance().getLogger().log(Level.WARNING, String.format("Error: \"%s.yml\" not found.", name) );
		}
		
		return action;
	}

	public static void initialize() {
		String path = Main.getInstance().getConfig().getString("tracks_folder");
		if (path == null) {
			path = DEFAULT_PATH;
		}

		File folder = new File(Main.getInstance().getDataFolder().getPath() + File.separator + path);
		if (!folder.exists()) {
			folder.mkdirs();

		}

		Track.setFolder(folder);

		for (File file : folder.listFiles()) {
			if (file.getName().endsWith(".yml")) {
				loadTrack(file.getName().replace(".yml", ""));
			}
		}
	}

	public static File getFolder() {
		return folder;
	}

	public static void setFolder(File folder) {
		Track.folder = folder;
	}

	// --------- setters || getters ----------

	public ArrayList<Note> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<Note> notes) {
		this.notes = notes;
	}

	public Iterator<Note> getIterator() {
		return iterator;
	}

	public void setIterator(Iterator<Note> iterator) {
		this.iterator = iterator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<UUID> getRecipients() {
		return recipients;
	}

	public void setRecipients(ArrayList<UUID> recipients) {
		this.recipients = recipients;
	}

	public Note getNextNote() {
		return this.getIterator().next();
	}

	public void setNextnote(Note note) {
		this.getNotes().add(note);
	}

	public Boolean getRepeat() {
		return repeat;
	}

	public void setRepeat(Boolean repeat) {
		this.repeat = repeat;
	}
}