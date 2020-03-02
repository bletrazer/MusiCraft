package fr.bletrazer.musicraft;

import org.bukkit.plugin.java.JavaPlugin;

import fr.bletrazer.musicraft.commands.Cmd_musicraft;
import fr.bletrazer.musicraft.music.instruction.Track;

public class Main extends JavaPlugin {
	private static Main main;

	public static Main getInstance() {
		return main;
	}

	public void onEnable() {
		Main.main = this;
		this.saveDefaultConfig();
		MessagesManager.initialize();
		Track.initialize();

		this.getCommand(Cmd_musicraft.CMD_LABEl).setExecutor(new Cmd_musicraft());

	}

}