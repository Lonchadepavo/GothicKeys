package com.loncha.gothickeys;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Lockpicks implements Listener{
	HashMap<Player, String[][]> codigo = new HashMap<Player,String[][]>();
	HashMap<Player, Block> locked = new HashMap<Player,Block>();

	public void lockPick(Player p, Block b, int dificultad) {
		Inventory guiLockPick = Bukkit.createInventory(p, 36, "Cerradura");
		locked.put(p, b);
		
		switch(dificultad) {
			case 0:
				//Número de ganzúas
				createDisplay(Material.ARROW, guiLockPick, 0, "Ganzúas: " + String.valueOf(getNumeroGanzuas(p)), "Número de ganzúas disponibles");
				createDisplay(Material.BANNER, guiLockPick, 9, "0", "Número de pernos movidos");
				
				//Paneles de cristal
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 1, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 2, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 3, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 4, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 5, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 10, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 11, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 12, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 13, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 14, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 18, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 19, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 20, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 21, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 22, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 23, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 27, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 28, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 29, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 30, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 31, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 32, " ", "");
				
				//Pernos
				createDisplay(Material.IRON_DOOR, guiLockPick, 33, "Perno 1", "0");
				createDisplay(Material.IRON_DOOR, guiLockPick, 34, "Perno 2", "0");
				createDisplay(Material.IRON_DOOR, guiLockPick, 35, "Perno 3", "0");
				
				p.openInventory(guiLockPick);
				codigo.put(p, setCodigo(3));
				break;
			case 1:
				//Número de ganzúas
				createDisplay(Material.ARROW, guiLockPick, 0, "Ganzúas: " + String.valueOf(getNumeroGanzuas(p)), "Número de ganzúas disponibles");
				createDisplay(Material.BANNER, guiLockPick, 9, "0", "Número de pernos movidos");
				
				//Paneles de cristal
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 1, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 2, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 3, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 4, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 10, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 11, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 12, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 13, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 18, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 19, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 20, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 21, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 22, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 27, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 28, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 29, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 30, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 31, " ", "");
				
				//Pernos
				createDisplay(Material.IRON_DOOR, guiLockPick, 32, "Perno 1", "0");
				createDisplay(Material.IRON_DOOR, guiLockPick, 33, "Perno 2", "0");
				createDisplay(Material.IRON_DOOR, guiLockPick, 34, "Perno 3", "0");
				createDisplay(Material.IRON_DOOR, guiLockPick, 35, "Perno 4", "0");
				
				p.openInventory(guiLockPick);
				codigo.put(p, setCodigo(4));
				break;
			case 2:
				//Número de ganzúas
				createDisplay(Material.ARROW, guiLockPick, 0, "Ganzúas: " + String.valueOf(getNumeroGanzuas(p)), "Número de ganzúas disponibles");
				createDisplay(Material.BANNER, guiLockPick, 9, "0", "Número de pernos movidos");
				
				//Paneles de cristal
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 1, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 2, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 3, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 10, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 11, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 12, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 18, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 19, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 20, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 21, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 27, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 28, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 29, " ", "");
				createDisplay(Material.STAINED_GLASS_PANE, guiLockPick, 30, " ", "");
				
				//Pernos
				createDisplay(Material.IRON_DOOR, guiLockPick, 31, "Perno 1", "0");
				createDisplay(Material.IRON_DOOR, guiLockPick, 32, "Perno 2", "0");
				createDisplay(Material.IRON_DOOR, guiLockPick, 33, "Perno 3", "0");
				createDisplay(Material.IRON_DOOR, guiLockPick, 34, "Perno 4", "0");
				createDisplay(Material.IRON_DOOR, guiLockPick, 35, "Perno 5", "0");
				
				p.openInventory(guiLockPick);
				codigo.put(p, setCodigo(5));
				break;
		}
	}
	
	public int getNumeroGanzuas(Player p) {
		Inventory inv = p.getInventory();
		ItemStack[] items = inv.getContents();
		
		ArrayList<ItemStack> ganzuas = new ArrayList<ItemStack>();
		
		for (ItemStack item : items) {
			if (item != null) {
				if (item.hasItemMeta()) {
					if (item.getItemMeta().getDisplayName().contains("Ganzúa")) {
						if (item.getType() == Material.NETHER_STAR) {
							int numeroGanzuas = item.getAmount();
							for (int i = 1; i <= numeroGanzuas; i++) {
								ganzuas.add(item);
							}
						}
					}
				}
			}
		}
		return ganzuas.size();
	}
	
	public static String[][] setCodigo(int pernos) {
		//Primera columna = perno
		//Primera fila = orden (número aleatorio entre 1 y el número de pernos)
		//Segunda fila = posición (número aleatorio entre 1 y 3)
		String[][] codigo = new String[pernos][2];
		ArrayList<Integer> orden = new ArrayList<Integer>();
		
		for (int i = 0; i < pernos; i++) {
			orden.add(i+1);
		}
		
		//Establecer el orden de los pernos
		for (int i = 0; i < pernos; i++) {
			int rango = orden.size();
			int ordenRandom = (int)(Math.random() * ((orden.size()-1) + 1));
			int posicionRandom = (int)(Math.random() * ((3-1) + 1))+1;

			codigo[i][0] = String.valueOf(orden.get(ordenRandom));
			codigo[i][1] = String.valueOf(posicionRandom);
			
			orden.remove(ordenRandom);
		}
		
		return codigo;
	}
	
	public boolean checkOrden(Player p ,int numeroPerno, String ordenPerno) {
		String[][] checker = codigo.get(p);
		
		if (!checker[numeroPerno][0].equals(ordenPerno)) {
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (p.getLocation().distanceSquared(players.getLocation()) <= 5) {
					players.getWorld().playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 0.01F);
				}
			}
			restarGanzuas(p);
			
			p.closeInventory();
			p.sendTitle(ChatColor.DARK_RED+"Se ha roto una ganzúa", "");
			
			return false;
		} else {
			return true;
		}
	}
	
	public boolean checkPosicion(Player p, int numeroPerno, String posicionPerno) {
		String[][] checker = codigo.get(p);
		
		if (checker[numeroPerno][1].equals(posicionPerno)) {
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (p.getLocation().distanceSquared(players.getLocation()) <= 5) {
					players.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0F, 0.01F);
				}
			}
			
		} else if (Integer.valueOf(posicionPerno) > Integer.valueOf(checker[numeroPerno][1])) {
			System.out.println("entra");
			
			for (Player players : Bukkit.getOnlinePlayers()) {
				if (p.getLocation().distanceSquared(players.getLocation()) <= 5) {
					players.getWorld().playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 0.01F);
				}
			}
			restarGanzuas(p);
			
			p.closeInventory();
			p.sendTitle(ChatColor.DARK_RED+"Se ha roto una ganzúa", "");
		}
		return false;
	}
	
	public boolean checkIfOpen(Player p, ArrayList<String> posiciones) {
		String[][] checker = codigo.get(p);
		Boolean forzado = true;
		
		for (int i = 0 ; i < posiciones.size(); i++) {
			if (!posiciones.get(i).equals(checker[i][1])) {
				forzado = false;
			}
		}
		
		if (forzado) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public void restarGanzuas(Player p) {
		Inventory inv = p.getInventory();
		ItemStack ganzuas = p.getInventory().getItemInMainHand();

		if (ganzuas.hasItemMeta()) {
			if (ganzuas.getItemMeta().getDisplayName().contains("Ganzúa")) {
				if (ganzuas.getType() == Material.NETHER_STAR) {
					ganzuas.setAmount(ganzuas.getAmount()-1);
				}
			}
		}
	}
	
	public static void createDisplay(Material material, Inventory inv, int Slot, String name, String lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		ArrayList<String> Lore = new ArrayList<String>();
		Lore.add(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.setLore(Lore);
		item.setItemMeta(meta);
		
		 
		inv.setItem(Slot, item); 
		 
	}
}
