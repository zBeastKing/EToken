package me.prison.tokens.sign;

import java.util.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import me.prison.tokens.listener.TokenManager;


public class SignClick implements Listener {
	
    public static ArrayList enchants = new ArrayList();
    
    private void msg(CommandSender s, String msg) {
    	s.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
	
    public static void loadEnchants()
    {
        enchants.add("Haste");
        enchants.add("Speed");
        enchants.add("Nightvision");
        enchants.add("Explosive");
        enchants.add("Fortune");
        enchants.add("Silktouch");
        enchants.add("Efficiency");
        enchants.add("Unbreaking");
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void onSignClick(PlayerInteractEvent e) {
        if((e.getAction() != Action.RIGHT_CLICK_BLOCK || e.getPlayer().getItemInHand().getType() != Material.DIAMOND_PICKAXE));
            return;
        Player p = e.getPlayer();
        if(e.getClickedBlock().getState() instanceof Sign) {
            Sign s = (Sign)e.getClickedBlock().getState();
            if(s.getLine(0).equalsIgnoreCase("-[Enchant]-")) {
                if(!enchants.contains(ChatColor.stripColor(s.getLine(2)))) {
                    e.setCancelled(true);
                    return;
                }
                String price = ChatColor.stripColor(s.getLine(3));
                int cost = Integer.valueOf(price).intValue();
                TokenManager player = (TokenManager)TokenManager.players.get(p.getUniqueId().toString());
                if(player.getTokens() < cost) {
                    msg(p, "&c&l(!) &cYou need &n" + cost + " E-Tokens &cmore for this enchant!");
                    return;
                }
                if(ChatColor.stripColor(s.getLine(2)).equals("Haste") || ChatColor.stripColor(s.getLine(2)).equals("Speed") || ChatColor.stripColor(s.getLine(2)).equals("Explosive") || ChatColor.stripColor(s.getLine(2)).equals("Nightvision")) {
                	if(p.getItemInHand().getItemMeta().getLore() == null) {
                        List lore = new ArrayList();
                        lore.add(ChatColor.GRAY + ChatColor.stripColor(s.getLine(2)) + " 1");
                        ItemStack is = p.getItemInHand();
                        ItemMeta im = is.getItemMeta();
                        im.setLore(lore);
                        ((TokenManager)TokenManager.players.get(p.getUniqueId().toString())).setTokens(((TokenManager)TokenManager.players.get(p.getUniqueId().toString())).getTokens() - cost);
                        msg(p, "&a&l(!) &aYou paid &f" + cost + " E-Tokens &afor +1 &n" + s.getLine(2) + "!");
                        is.setItemMeta(im);
                        p.getInventory().setItemInHand(is);
                        return;
                    }
                    int level = 0;
                    for(Iterator iterator = p.getItemInHand().getItemMeta().getLore().iterator(); iterator.hasNext();) {
                        String q = (String)iterator.next();
                        String args[] = q.split(" ");
                        if(ChatColor.stripColor(args[0]).equalsIgnoreCase(ChatColor.stripColor(s.getLine(2))))
                            level = Integer.valueOf(args[1]).intValue();
                    }

                    if(!ChatColor.stripColor(s.getLine(2)).equals("Explosive")) {
                    	if(Integer.valueOf(level + 1).intValue() > 100) {
                    		msg(p, "&c&l(!) &cYou reached the max level for &n" + s.getLine(2) + "&c!");
                    		return;
                    	}
                    List lore = p.getInventory().getItemInHand().getItemMeta().getLore();
                    msg(p, "&a&l(!) &aYou paid &f" + cost + " E-Tokens &afor +1 &n" + s.getLine(2) + "&a!");
                    ((TokenManager)TokenManager.players.get(p.getUniqueId().toString())).setTokens(((TokenManager)TokenManager.players.get(p.getUniqueId().toString())).getTokens() - cost);
                    lore.remove(ChatColor.GRAY + s.getLine(2) + " " + level);
                    lore.add(ChatColor.GRAY + ChatColor.stripColor(s.getLine(2)) + " " + Integer.valueOf(level + 1));
                    ItemStack is = p.getItemInHand();
                    ItemMeta im = is.getItemMeta();
                    im.setLore(lore);
                    is.setItemMeta(im);
                    p.getInventory().setItemInHand(is);
                } else {
                    Enchantment enchantment = getEnchant(ChatColor.stripColor(s.getLine(2)));
                    if(p.getInventory().getItemInHand().containsEnchantment(enchantment)) {
                        int level1 = p.getInventory().getItemInHand().getEnchantmentLevel(enchantment);
                        if(Integer.valueOf(level1 + 1).intValue() > 100) {
                            msg(p, "&c&l(!) &cYou reached the max level for " + s.getLine(2) + "!");
                        }
                        ((TokenManager)TokenManager.players.get(p.getUniqueId().toString())).setTokens(((TokenManager)TokenManager.players.get(p.getUniqueId().toString())).getTokens() - cost);
                        msg(p, "&a&l(!) &aYou paid &f" + cost + " E-Tokens &afor +1 &n" + s.getLine(2) + "!");
                        p.getInventory().getItemInHand().addUnsafeEnchantment(enchantment, level1 + 1);
                    } else {
                    	msg(p, "&a&l(!) &aYou paid &f" + cost + " E-Tokens &afor +1 &n" + s.getLine(2) + "!");
                    	((TokenManager)TokenManager.players.get(p.getUniqueId().toString())).setTokens(((TokenManager)TokenManager.players.get(p.getUniqueId().toString())).getTokens() - cost);
                        p.getInventory().getItemInHand().addUnsafeEnchantment(enchantment, 1);
                    }
                }
                }
            }
        }
    }

    public int getCustomEnchantLevel(ItemStack is, String enchant) {
        if(is.getItemMeta().getLore().isEmpty())
            return 0;
        for(Iterator iterator = is.getItemMeta().getLore().iterator(); iterator.hasNext();) {
            String s = (String)iterator.next();
            String args[] = s.split(" ");
            if(args[0].equalsIgnoreCase(ChatColor.stripColor(enchant)))
                return Integer.valueOf(args[1]).intValue();
        }
        return 0;
    }

    public static Enchantment getEnchant(String enchant) {
        if(enchant.equalsIgnoreCase("Efficiency"))
            return Enchantment.DIG_SPEED;
        if(enchant.equalsIgnoreCase("Unbreaking"))
            return Enchantment.DURABILITY;
        if(enchant.equalsIgnoreCase("Fortune"))
            return Enchantment.LOOT_BONUS_BLOCKS;
        if(enchant.equalsIgnoreCase("Silk Touch"))
            return Enchantment.SILK_TOUCH;
        else return null;
    }

}
