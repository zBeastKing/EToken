package me.prison.tokens;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.prison.tokens.listener.TokenManager;

public class CommandToken implements CommandExecutor {
	
	private void msg(CommandSender s, String msg) {
		s.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	
	public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
		
		if (!(s instanceof Player)) {
			if (args.length == 0) {
				msg(s, "&f/token give <player> <amount>");
				msg(s, "&f/token remove <player> <amount>");
			}
			if (args[0].equalsIgnoreCase("give")) {
				if (args.length < 3) {
					msg(s, "&c&l(!) &c/token give <player> <amount>");
					return true;
				} else {
					Player target = Bukkit.getPlayer(args[1]);
					if (target == null) {
						msg(s, "&c&l(!) &cPlayer not found!");
						return true;
					} else {
						((TokenManager)TokenManager.players.get(target.getUniqueId().toString())).setTokens(((TokenManager)TokenManager.players.get(target.getUniqueId().toString())).getTokens() + Integer.valueOf(args[2]).intValue());
						msg(s, "&e&l(!) &e" + target.getName() + " have been added " + args[2] + " E-Tokens.");
						msg(target, "&a&l(!) &a+ " + args[2] + " E-Tokens.");
					}
				}
			} else if (args[0].equalsIgnoreCase("remove")) {
				if (args.length < 3) {
					msg(s, "&c&l(!) &c/token remove <player> <amount>");
					return true;
				} else {
					Player target = Bukkit.getPlayer(args[1]);
					if (target == null) {
						msg(s, "&c&l(!) &cPlayer not found!");
						return true;
					} else {
						((TokenManager)TokenManager.players.get(target.getUniqueId().toString())).setTokens(((TokenManager)TokenManager.players.get(target.getUniqueId().toString())).getTokens() - Integer.valueOf(args[2]).intValue());
						msg(s, "&e&l(!) &e" + target.getName() + " have been taken " + args[2] + " E-Tokens.");
						msg(target, "&c&l(!) &c- " + args[2] + " E-Tokens.");
					}
				}
			}	
		}
		if (args.length == 0) {
			if (!s.hasPermission("prison.tokens.check")) {
				msg(s, "&c&l(!) &cYou don't have permissions!");
				return true;
			}
			msg(s, "&e&l(!) &eYour tokens is: &f" + ((TokenManager)TokenManager.players.get(((OfflinePlayer)s).getUniqueId().toString())).getTokens() + "&e.");
		} /*
		else if (args.length < 1) {
			if (!s.hasPermission("prison.tokens.check.others")) {
				msg(s, "&c&l(!) &cYou don't have permissions!");
				return true;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				msg(s, "&c&l(!) &cPlayer not found!");
				return true;
			} else {
				msg(s, "&e&l(!) &e" + target.getName() + "'s tokens: &f" + ((TokenManager)TokenManager.players.get(target.getUniqueId().toString())).getTokens() + "&e.");
			}
		}
		*/
		if (args[0].equalsIgnoreCase("withdraw")) {
			Player p = (Player) s;
			if (args.length < 2) {
				if (!s.hasPermission("prison.tokens.withdraw")) {
					msg(s, "&c&l(!) &cYou don't have permissions!");
					return true;
				}
				msg(s, "&e&l(!) &e/token withdraw <amount>");
				return true;
			} else if (args[1].contains("-")) {
				msg(s, "&e&l(!) &e/token withdraw <amount>");
				return true;
			}
            int amnt = Integer.valueOf(args[1]).intValue();
            if (((TokenManager)TokenManager.players.get(p.getUniqueId().toString())).getTokens() < amnt) {
            	msg(s, "&c&l(!) &cYou don't have enough E-Tokens");
            	return true;
            } else {
                ItemStack etoken = new ItemStack(Material.MAGMA_CREAM, amnt);
                ItemMeta etokenMeta = etoken.getItemMeta();
                etokenMeta.setDisplayName(ChatColor.AQUA + "E-Tokens " + ChatColor.GRAY + "(Click)");
                etokenMeta.setLore(Arrays.asList(ChatColor.GRAY + "This item can be redeemed by clicking"));
                etoken.setItemMeta(etokenMeta);
                p.getInventory().addItem(new ItemStack[] { etoken });
                
                msg(s, "&c&l(!) &c-" + args[1] + " E-Tokens.");
                ((TokenManager)TokenManager.players.get(p.getUniqueId().toString())).setTokens(((TokenManager)TokenManager.players.get(p.getUniqueId().toString())).getTokens() - amnt);
            }
		}
		return true;
	}

}
