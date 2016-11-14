package me.prison;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.prison.tokens.listener.TokenManager;

public class Main extends JavaPlugin {
	
    public static File file;
    public static FileConfiguration config;
	
	public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        config = getConfig();
        file = new File(getDataFolder(), "players.yml");
		getCommand("token").setExecutor(new me.prison.tokens.CommandToken());
        
		getServer().getPluginManager().registerEvents(new me.prison.tokens.listener.ClickTokens(), this);
		getServer().getPluginManager().registerEvents(new me.prison.tokens.listener.Explosive(), this);
		getServer().getPluginManager().registerEvents(new me.prison.tokens.sign.SignClick(), this);
		getServer().getPluginManager().registerEvents(new me.prison.tokens.sign.SignCreate(), this);
		
		Player[] aplayer;
        int j = (aplayer = Bukkit.getOnlinePlayers()).length;
        for(int i = 0; i < j; i++) {
            Player p = aplayer[i];
            TokenManager etManager;
            if(!config.contains((new StringBuilder("Players.")).append(p.getUniqueId().toString()).toString())) {
                TokenManager player = new TokenManager(p.getUniqueId().toString(), p.getName(), 0);
                config.set((new StringBuilder("Players.")).append(p.getUniqueId().toString()).toString(), Integer.valueOf(0));
                try {
                    config.save(file);
                }
                catch(IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                etManager = new TokenManager(p.getUniqueId().toString(), p.getName(), config.getInt((new StringBuilder("Players.")).append(p.getUniqueId().toString()).toString()));
            }
        }
	}

}
