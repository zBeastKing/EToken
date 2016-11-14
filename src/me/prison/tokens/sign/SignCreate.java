package me.prison.tokens.sign;

import java.io.IOException;
import java.util.*;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.prison.Main;
import me.prison.tokens.listener.TokenManager;

public class SignCreate implements Listener{
	
	private void msg(CommandSender s, String msg) {
		s.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	
    public void onSignChange(SignChangeEvent e) {
    	Player p = e.getPlayer();
        if(e.getLine(0).equalsIgnoreCase("[Enchant]")) {
            if(!e.getPlayer().hasPermission("prison.tokens.signs"))
                return;
            e.setLine(0, "&1[Enchant]");
            if(!SignClick.enchants.contains(e.getLine(2))) {
                e.getBlock().breakNaturally();
                msg(p, "&c&l(!) &cThat is not a valid enchant.");
                return;
            }
            e.setLine(1, "+ 1");
            e.setLine(2, e.getLine(2)); // Enchant
            e.setLine(3, e.getLine(3) + " E-Tokens");
        }
    }

    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        TokenManager etManager;
        if(!Main.config.contains((new StringBuilder("Players.")).append(p.getUniqueId().toString()).toString())) {
            TokenManager player = new TokenManager(p.getUniqueId().toString(), p.getName(), 0);
            Main.config.set((new StringBuilder("Players.")).append(p.getUniqueId().toString()).toString(), Integer.valueOf(0));
            try {
                Main.config.save(Main.file);
            }
            catch(IOException e1) {
                e1.printStackTrace();
            }
        } else {
            etManager = new TokenManager(p.getUniqueId().toString(), p.getName(), Main.config.getInt((new StringBuilder("Players.")).append(p.getUniqueId().toString()).toString()));
        }
    }

    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(!Main.config.contains((new StringBuilder("Players.")).append(p.getUniqueId().toString()).toString())) {
            Main.config.set((new StringBuilder("Players.")).append(p.getUniqueId().toString()).toString(), Integer.valueOf(0));
            try {
                Main.config.save(Main.file);
            }
            catch(IOException e1) {
                e1.printStackTrace();
            }
        } else {
            Main.config.set((new StringBuilder("Players.")).append(p.getUniqueId().toString()).toString(), Integer.valueOf(((TokenManager)TokenManager.players.get(p.getUniqueId().toString())).getTokens()));
            try {
                Main.config.save(Main.file);
            }
            catch(IOException e1) {
                e1.printStackTrace();
            }
            TokenManager.players.remove(p.getUniqueId().toString());
        }
    }

}
