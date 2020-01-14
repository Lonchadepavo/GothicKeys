package com.loncha.gothickeys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Crafteos implements Listener{
	
	@EventHandler
	public void onItemCraft(CraftItemEvent e) {
		if (e.getInventory().getResult().hasItemMeta()) {
			String nombre = e.getInventory().getResult().getItemMeta().getDisplayName();
			
			if (nombre.equals("Cerradura débil")) {
				e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ANVIL_USE, 3.0F, 1F);
				ItemStack item = e.getInventory().getResult();
				ItemMeta meta = item.getItemMeta();
				
				int idCerradura = getNumeroRandom();
				
				List<String> lore = new ArrayList<String>(Arrays.asList("Número de cerradura:", String.valueOf(idCerradura), "Débil"));
				meta.setLore(lore);
				
				item.setItemMeta(meta);
				
			} else if (nombre.equals("Cerradura normal")) {
				e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ANVIL_USE, 3.0F, 1F);
				ItemStack item = e.getInventory().getResult();
				ItemMeta meta = item.getItemMeta();
				
				int idCerradura = getNumeroRandom();
				
				List<String> lore = new ArrayList<String>(Arrays.asList("Número de cerradura:", String.valueOf(idCerradura), "Normal"));
				meta.setLore(lore);
				
				item.setItemMeta(meta);
				
			} else if (nombre.equals("Cerradura dificil")) {
				e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ANVIL_USE, 3.0F, 1F);
				ItemStack item = e.getInventory().getResult();
				ItemMeta meta = item.getItemMeta();
				
				int idCerradura = getNumeroRandom();
				
				List<String> lore = new ArrayList<String>(Arrays.asList("Número de cerradura:", String.valueOf(idCerradura), "Dificil"));
				meta.setLore(lore);
				
				item.setItemMeta(meta);
				
			} else if (nombre.contains("Ganzúa")) {
				e.getWhoClicked().getWorld().playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ANVIL_USE, 3.0F, 1F);
				ItemStack item = e.getInventory().getResult();
				ItemMeta meta = item.getItemMeta();
				
				List<String> lore = new ArrayList<String>(Arrays.asList("Ganzúa"));
				meta.setLore(lore);
				
				item.setItemMeta(meta);
			}
		}
	}

	public int getNumeroRandom() {
		return (int)(Math.random()*999999)+1;
	}
}
