package fr.bletrazer.musicraft;

import org.bukkit.command.CommandSender;

public class MessagesManager {

	private static String prefix = "&7[&eMusic&7]&r:";

	public static Boolean sendMessage(CommandSender recipient, String color, String msg) {
		Boolean success = false;

		String message = String.format("%s: %s", prefix, msg.replace("§r", color));

		if (message != null && recipient != null) {
			recipient.sendMessage(message);
			success = true;
		}

		return success;
	}

	public static Boolean initialize() {
		Boolean success = false;
		String toSet = Main.getInstance().getConfig().getString("message_prefix");

		if (toSet != null) {
			prefix = toSet.replace("&", "§");
			success = true;
		}

		return success;

	}

}
