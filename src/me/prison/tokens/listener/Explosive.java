package me.prison.tokens.listener;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import me.prison.Main;

import java.util.*;
import net.minecraft.server.v1_8_R1.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Explosive implements Listener {
	
	private void msg(CommandSender s, String msg) {
		s.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
   
	@SuppressWarnings("rawtypes")
	public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(!allowsBuilding(p, e.getBlock().getLocation()))
            return;
        ((TokenManager)TokenManager.players.get(p.getUniqueId().toString())).setBlocksBroken(((TokenManager)TokenManager.players.get(p.getUniqueId().toString())).getBlocksBroken() + 1);
        if(((TokenManager)TokenManager.players.get(p.getUniqueId().toString())).getBlocksBroken() % 1000 == 0) {
            if(!p.hasPermission("blocksbreak.doubleet")) {
                return;
            }
            msg(p, "&a&l(!) &aYou earned 1 E-Tokens.");
        }
        if(p.getInventory().getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
            if(p.getItemInHand().getItemMeta().getLore() == null)
                return;
            List lore2 = p.getItemInHand().getItemMeta().getLore();
            for(Iterator iterator = lore2.iterator(); iterator.hasNext();) {
                String s = (String)iterator.next();
                String args[] = s.split(" ");
                if(ChatColor.stripColor(args[0]).equals("Haste")) {
                    if(args[1].equals("1")) {
                        double pecentage = Math.random() * 100D;
                        if(pecentage > 75D)
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 0));
                    } else
                    if(args[1].equals("2")) {
                        double pecentage = Math.random() * 100D;
                        if(pecentage > 60D)
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 1));
                    } else
                    if(args[1].equals("3")) {
                        double pecentage = Math.random() * 100D;
                        if(pecentage > 45D)
                            p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 200, 2));
                    }
                } else
                if(ChatColor.stripColor(args[0]).equals("Nightvision")) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 200, 0));
                } else
                if(ChatColor.stripColor(args[0]).equals("Explosive")) {
                    double pecentage = Math.random() * 100D;
                    if(pecentage > 45D)
                        createExplosion(e.getBlock().getLocation(), p, Integer.valueOf(args[1]).intValue());
                } else
                if(ChatColor.stripColor(args[0]).equals("Speed"))
                    if(args[1].equals("1")) {
                        double pecentage = Math.random() * 100D;
                        if(pecentage > 75D)
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0));
                    } else
                    if(args[1].equals("2")) {
                        double pecentage = Math.random() * 100D;
                        if(pecentage > 60D)
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
                    } else
                    if(args[1].equals("3")) {
                        double pecentage = Math.random() * 100D;
                        if(pecentage > 45D)
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 2));
                    }
            }
        }
    }

    @SuppressWarnings("rawtypes")
	public void createExplosion(Location l, Player p, int amount) {
        ArrayList blocks;
        int i;
        blocks = new ArrayList();
        int radius = 2;
        for(int x = -radius; x <= radius; x++) {
            for(int y = -radius; y <= radius; y++)
            {
                for(int z = -radius; z <= radius; z++) {
                    Location loc = l.getBlock().getRelative(x, y, z).getLocation();
                    loc.getBlock().getType();
                    Material.DISPENSER;
                    for(Iterator iterator = Main.config.getStringList("ExplodableBlocks").iterator(); iterator.hasNext();) {
                        String s = (String)iterator.next();
                        Material m = Material.getMaterial(Integer.valueOf(s).intValue());
                        if(loc.getBlock().getType() == m)
                            blocks.add(loc.getBlock());
                    }

                }

            }

        }

        i = 0;
          goto _L1
_L3:
        Block b = (Block)blocks.get((new Random()).nextInt(blocks.size()));
        if(allowsBuilding(p, b.getLocation()))
            try
            {
                int loots = (int)(Math.floor((double)p.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) * 0.25D) + 1.0D);
                if(b.getType() == Material.DIAMOND_ORE)
                    p.getInventory().addItem(new ItemStack[] {
                        new ItemStack(Material.DIAMOND, loots)
                    });
                else
                if(b.getType() == Material.IRON_ORE)
                    p.getInventory().addItem(new ItemStack[] {
                        new ItemStack(Material.IRON_INGOT, loots)
                    });
                else
                if(b.getType() == Material.GOLD_ORE)
                    p.getInventory().addItem(new ItemStack[] {
                        new ItemStack(Material.GOLD_INGOT, loots)
                    });
                else
                if(b.getType() == Material.COAL_ORE)
                    p.getInventory().addItem(new ItemStack[] {
                        new ItemStack(Material.COAL, loots)
                    });
                else
                if(b.getType() == Material.EMERALD_ORE)
                    p.getInventory().addItem(new ItemStack[] {
                        new ItemStack(Material.EMERALD, loots)
                    });
                else
                if(b.getType() == Material.LAPIS_ORE)
                    b.breakNaturally();
                else
                if(b.getType() == Material.REDSTONE_ORE)
                    p.getInventory().addItem(new ItemStack[] {
                        new ItemStack(Material.REDSTONE, loots)
                    });
                else
                    p.getInventory().addItem(new ItemStack[] {
                        new ItemStack(b.getType(), loots)
                    });
                b.setType(Material.AIR);
            }
            catch(Exception e) {
                return;
            }
        i++;
_L1:
        if(i < amount / 5) goto _L3; else goto _L2
_L2:
        Player aplayer[];
        int k = (aplayer = Bukkit.getOnlinePlayers()).length;
        for(int j = 0; j < k; j++) {
            Player online = aplayer[j];
            ((CraftPlayer)online).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldParticles("largeexplode", (float)l.getX(), (float)l.getY(), (float)l.getZ(), 0.0F, 0.0F, 0.0F, 1.0F, 1));
        }

        return;
    }

    private WorldGuardPlugin getWorldGuard()
    {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if(plugin == null || !(plugin instanceof WorldGuardPlugin))
            return null;
        else
            return (WorldGuardPlugin)plugin;
    }

    private boolean allowsBuilding(Player player, Location l) {
        return getWorldGuard().canBuild(player, l);
    }
}
