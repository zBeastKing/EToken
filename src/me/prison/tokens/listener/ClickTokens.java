package me.prison.tokens.listener;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ClickTokens implements Listener {
	
	/*
		ItemStack etoken = new ItemStack(Material.MAGMA_CREAM, e.getPlayer().getItemInHand().getAmount());
        ItemStack air = new ItemStack(Material.AIR);
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getAction() != Action.RIGHT_CLICK_AIR)
            return;
        if(e.getItem() == null)
            return;
        if(e.getItem().getType() == Material.MAGMA_CREAM)
        {
            e.getPlayer().sendMessage(" ");
            ((Stats)Stats.players.get(e.getPlayer().getUniqueId().toString())).setTokens(((Stats)Stats.players.get(e.getPlayer().getUniqueId().toString())).getTokens() + e.getPlayer().getItemInHand().getAmount());
            e.getPlayer().sendMessage((new StringBuilder("\247a\247l+\247a\247l")).append(e.getPlayer().getItemInHand().getAmount()).append(" \247a\247lEnchantment Tokens!").toString());
            e.getPlayer().sendMessage(" ");
            e.getPlayer().setItemInHand(air);
            e.getPlayer().updateInventory();
        }
	 */
	
    public void onPlayerInteract(PlayerInteractEvent e) {
    	ItemStack item = e.getItem();
    	Player p = e.getPlayer();
    	Action action = e.getAction();
    	if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
    		if (item == null) return;
    		if (p.getItemInHand().getType() == Material.MAGMA_CREAM && p.getItemInHand().getItemMeta().getDisplayName() == ChatColor.AQUA + "E-Tokens " + ChatColor.GRAY + "(Click)" && p.getItemInHand().getItemMeta().getLore() == Arrays.asList(ChatColor.GRAY + "This item can be redeemed by clicking")) {
    			((TokenManager)TokenManager.players.get(e.getPlayer().getUniqueId().toString())).setTokens(((TokenManager)TokenManager.players.get(e.getPlayer().getUniqueId().toString())).getTokens() + e.getPlayer().getItemInHand().getAmount());
    			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l(!) &eYou redeemed &f" + p.getItemInHand().getAmount() + " &eE-Tokens."));
    			e.getPlayer().updateInventory();
    		}
    	}
    }

}
