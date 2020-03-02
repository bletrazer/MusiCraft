package fr.bletrazer.musicraft.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.bletrazer.musicraft.MessagesManager;
import fr.bletrazer.musicraft.music.Track;
import fr.bletrazer.musicraft.music.TrackHandler;

public class Cmd_musicraft implements CommandExecutor {
	
	public static final String CMD_LABEl = "musicraft";
	
	private final List<String> ARGS_LOADMUSIC = Arrays.asList(new String[] {"loadmusic", "lobu"});
	private final List<String> ARGS_LOADTRACK = Arrays.asList(new String[] {"loadtrack", "lotr"});
	
	private final List<String> ARGS_LISTMUSICS = Arrays.asList(new String[] {"listmusic", "libu"});
	private final List<String> ARGS_LISTTRACK = Arrays.asList(new String[] {"listtrack", "litr"});
	
	private final List<String> ARGS_PLAYMUSIC = Arrays.asList(new String[] {"playmusic", "plbu"});
	private final List<String> ARGS_PLAYTRACK = Arrays.asList(new String[] {"playtrack", "pltr"});
	
	private final List<String> ARGS_STOP = Arrays.asList(new String[] {"stop", "s"});
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(args.length == 0) {
			
		} else if (args.length == 1) {
			if(ARGS_LOADMUSIC.contains(args[0].toLowerCase()) || ARGS_LOADTRACK.contains(args[0].toLowerCase()) ) {
				sender.sendMessage(String.format("Correct usage: /musicraft %s <name>", args[0].toLowerCase() ));
				
			} else if (ARGS_LISTMUSICS.contains(args[0].toLowerCase()) ) {/*
				sender.sendMessage(String.format("Loaded musicss:\n%s", TrackMusic.getTrackMusicCache().keySet().toString().replace("[", "").replace("]", "") ));*/
				MessagesManager.sendMessage(sender, "§4", "this command is not finished now");//TODO
				
			} else if (ARGS_LISTTRACK.contains(args[0].toLowerCase()) ) {
				sender.sendMessage(String.format("Loaded Musics:\n%s", TrackHandler.get().keySet().toString().replace("[", "").replace("]", "") ));
				
			} else if (ARGS_STOP.contains(args[0].toLowerCase()) ) {
				if(sender instanceof Player) {
					Player player = (Player)sender;
					TrackHandler.removeToAll(player.getUniqueId());
					sender.sendMessage("You are no longer listening musics.");
					
				} else {
					sender.sendMessage("Only players can do this command.");
				}
				
			} else {
				sender.sendMessage("Unknown command.");
			}
		} else if (args.length == 2) {
			
			if(ARGS_LOADTRACK.contains(args[0].toLowerCase()) ) {
				 // load track
				if (Track.loadTrack(args[1]) ) {
					sender.sendMessage(String.format("Track \"%s\" successfully loaded!", args[1].toLowerCase() ));
					
				} else {
					sender.sendMessage("Faillure, check the console for more informations.");
					
				}
				 
			} else if (ARGS_LOADMUSIC.contains(args[0].toLowerCase()) ) {/*
				//load music
				if (TrackMusic.loadTrackMusic(args[1]) ) {
					sender.sendMessage(String.format("Music \"%s\" successfully loaded!", args[1].toLowerCase() ));
					
				} else {
					sender.sendMessage("Faillure, check the console for more informations.");
					
				}*/
				MessagesManager.sendMessage(sender, "§4", "this command is not finished now");//TODO
				
			} else if(ARGS_PLAYMUSIC.contains(args[0].toLowerCase()) ) { //TODO ne pas pouvoir lancé deux fois la meme track
				MessagesManager.sendMessage(sender, "§4", "this command is not finished now");//TODO
				/*
					if(TrackMusic.playTrackMusic(args[1], null, false) ) {
						sender.sendMessage(String.format("Now listening music: \"%s\".", args[1].toLowerCase()));
						
					} else {
						sender.sendMessage(String.format("\"%s\" not found", args[1].toLowerCase() ));
					}*/
			} else if(ARGS_PLAYTRACK.contains(args[0].toLowerCase()) ) {
				if(sender instanceof Player) {
					Player player = (Player) sender;
					Track track = TrackHandler.get(args[1]);
					
					if(track != null) {
						if(track.addRecipient(player.getUniqueId()) ) {
							track.start();
							sender.sendMessage(String.format("Now listening track: \"%s\".", args[1].toLowerCase()));
							
						} else {
							sender.sendMessage(String.format("Already listetrack: \"%s\".", args[1].toLowerCase()));
						}
					} else {
						sender.sendMessage(String.format("\"%s\" not found", args[1].toLowerCase() ));
					}
				}
			} else {
				sender.sendMessage("Unknown command.");
			}
		} else if (args.length == 3 && ARGS_PLAYMUSIC.contains(args[0].toLowerCase()) && args[2].equalsIgnoreCase("repeat")) {
			/*
			if(TrackMusic.playTrackMusic(args[1], null, true) ) {
				sender.sendMessage(String.format("Now listening music: \"%s\".", args[1].toLowerCase()));
				
			} else {
				sender.sendMessage(String.format("\"%s\" not found", args[1].toLowerCase() ));
			}
			*/
			MessagesManager.sendMessage(sender, "§4", "this command is not finished now");//TODO
			
		} else {
			sender.sendMessage("Unknown command.");
		}
		
		return false;
	}
	
}
