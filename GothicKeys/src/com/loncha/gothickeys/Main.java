package com.loncha.gothickeys;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.block.*;

public class Main extends JavaPlugin implements Listener {
	Crafteos c;
	Lockpicks l;
	String rutaSaves = "plugins/GothicKeys/locks.txt";
	
	public void onEnable() {
		l = new Lockpicks();
		
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(this.c = new Crafteos(), this);
		
		File f = new File("plugins/GothicKeys");
		
		if (!f.exists()) {
			f.mkdir();
		}
		
		cargarLocks();
	}
	
	@EventHandler
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("renombrarllave")) {
			if (args.length > 0) {
				if (p.getInventory().getItemInMainHand() != null) {
					if (p.getInventory().getItemInMainHand().hasItemMeta()) {
						if (p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
							if (p.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).equals("Número de llave:")) {
								String nombre = "";
								for (String s : args) {
									nombre += s+" ";
								}
								ItemStack item = p.getInventory().getItemInMainHand();
								ItemMeta meta = item.getItemMeta();
								
								meta.setDisplayName(nombre);
								item.setItemMeta(meta);
								
								return true;
								
							}
						} else {
							p.sendTitle(ChatColor.DARK_RED+"Solo puedes renombrar una llave a la que le hayas asignado una cerradura","");
							return true;
						}
					}
				}
			} else {
				return false;
			}
		} else if (cmd.getName().equalsIgnoreCase("test")) {
			Block b = p.getLocation().getBlock();
			l.lockPick(p, b, 0);
			
			return true;
		}
		
		return false;
		
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if (!e.getInventory().getName().contains("Cerradura")) {

			if (e.isRightClick()) {
				ItemStack clickedItem = e.getCurrentItem();
				ItemStack itemOnCursor = p.getItemOnCursor();
				
				if (itemOnCursor != null) {
					if (itemOnCursor.hasItemMeta()) {
						//ASIGNAR LLAVE A UNA CERRADURA
						if (itemOnCursor.getItemMeta().getDisplayName().equals("Llave")) {
							if (!itemOnCursor.getItemMeta().hasLore()) {
								if (clickedItem.hasItemMeta()) {
									if (clickedItem.getItemMeta().hasLore()) {
										if (clickedItem.getItemMeta().getDisplayName().contains("Cerradura")) {
											e.setCancelled(true);
											
											ItemMeta onCursorMeta = itemOnCursor.getItemMeta();
											List<String> lore = new ArrayList<String>(Arrays.asList("Número de llave:", clickedItem.getItemMeta().getLore().get(1)));
											onCursorMeta.setLore(lore);
											itemOnCursor.setItemMeta(onCursorMeta);
											p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 3.0F, 1F);
											
										} else if (clickedItem.getItemMeta().getLore().get(0).equals("Número de llave:")) {
											if (clickedItem.getItemMeta().hasLore()) {
												ItemMeta onCursorMeta = itemOnCursor.getItemMeta();
												List<String> lore = new ArrayList<String>(Arrays.asList("Número de llave:", clickedItem.getItemMeta().getLore().get(1)));
												onCursorMeta.setLore(lore);
												itemOnCursor.setItemMeta(onCursorMeta);
												p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 3.0F, 1F);
												
											}
										}
									}
								}
							}
						//CLONAR CERRADURAS
						} else if (itemOnCursor.getItemMeta().getDisplayName().contains("Cerradura")) {
							if (clickedItem.hasItemMeta()) {
								if (clickedItem.getItemMeta().hasLore()) {
									if (clickedItem.getItemMeta().getDisplayName().equals(itemOnCursor.getItemMeta().getDisplayName())) {
										e.setCancelled(true);
										
										ItemMeta clickedItemMeta = clickedItem.getItemMeta();
										
										itemOnCursor.setItemMeta(clickedItemMeta);
										p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 3.0F, 1F);
										
									}
								}
							}
						}
					}
				}
			}
		} else {
			ItemStack clickedItem = e.getCurrentItem();
			Inventory inv = e.getInventory();
			
			if (clickedItem.hasItemMeta()) {
				if (clickedItem.getItemMeta().getDisplayName().contains("Perno")) {
					if (clickedItem.getItemMeta().hasLore()) {
						String nombre = clickedItem.getItemMeta().getDisplayName();
						String posicion = clickedItem.getItemMeta().getLore().get(0);
						ItemStack[] items = inv.getContents();
						
						int slot = 0;
						
						for (int i = 0; i < items.length; i++) {
							ItemStack temp = inv.getItem(i);
							
							if (temp != null) {
								if (temp.hasItemMeta()) {
									ItemMeta meta = temp.getItemMeta();
									
									if (meta.getDisplayName().equals(nombre)) {
										slot = i;
									}
								}
							}
						}
						
						if (slot-9 > 0) {
							//Funcionalidad
							int numeroPerno = Integer.valueOf(nombre.replaceAll("\\D+",""))-1;
							
							ItemStack nPernosMovidos = inv.getItem(9);
							int nPernos = Integer.valueOf(nPernosMovidos.getItemMeta().getDisplayName());
							
							if (clickedItem.getItemMeta().getLore().get(0).equals("0")) {
								nPernos++;
								ItemMeta pernoMeta = nPernosMovidos.getItemMeta();
								pernoMeta.setDisplayName(String.valueOf(nPernos));
								nPernosMovidos.setItemMeta(pernoMeta);
							}
							
							nPernos = Integer.valueOf(nPernosMovidos.getItemMeta().getDisplayName());
							
							if (l.checkOrden(p, numeroPerno, String.valueOf(nPernos))) {
								
								inv.remove(clickedItem);
								int sumarPos = Integer.valueOf(posicion);
								sumarPos++;
								ItemMeta meta = clickedItem.getItemMeta();
								meta.setLore(new ArrayList<String>(Arrays.asList(String.valueOf(sumarPos))));
								clickedItem.setItemMeta(meta);
								inv.setItem(slot-9, clickedItem);
								
								String posicionPerno = clickedItem.getItemMeta().getLore().get(0);
								l.checkPosicion(p, numeroPerno, posicionPerno);
							}
							
							ArrayList<String> posiciones = new ArrayList<String>();
							
							for (int i = 27; i < 36; i++ ) {
								if (inv.getItem(i) != null) {
									if (inv.getItem(i).hasItemMeta()) {
										if (inv.getItem(i).getItemMeta().hasLore()) {
											if (inv.getItem(i).getItemMeta().getDisplayName().contains("Perno")) {
												posiciones.add(inv.getItem(i).getItemMeta().getLore().get(0));
											}
										}
									}
								}
							}
							
							if (l.checkIfOpen(p, posiciones)) {
								p.closeInventory();
								p.sendTitle(ChatColor.GREEN+"Cerradura forzada con éxito", "");
								Block b = l.locked.get(p);
								
								if (b.getType().toString().contains("TRAP_DOOR") || b.getType().toString().contains("TRAPDOOR")) {
									BlockState bs = b.getState();
									Openable openable = (Openable) bs.getData();
									
									openable.setOpen(true);
									bs.update();
									
								} else if (b.getType().toString().contains("DOOR")) {
									BlockState bs = b.getState();
									Door openable = (Door) bs.getData();
									
									if (openable.isTopHalf()) {
										Location locDown = new Location(p.getWorld(), b.getX(), b.getY()-1, b.getZ());
										Block bDown = locDown.getBlock();
										
										BlockState bsDown = bDown.getState();
										Door openableDown = (Door) bsDown.getData();
										
										openableDown.setOpen(true);
										bsDown.update();
										
									} else {
									
										openable.setOpen(true);
										bs.update();
									}
									
								} else if (b.getType().toString().contains("CHEST") || b.getType().toString().contains("SHULKER")) {
									BlockState bs = b.getState();
									Chest c = (Chest) b.getState();

									Inventory chestInv = c.getInventory();
									
									p.openInventory(chestInv);
								}
							}
							
							
						}
					}
				}
			}
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			//SI HACES CLICK DERECHO EN UNA TRAPDOOR
			if (b.getType().toString().contains("TRAP_DOOR") || b.getType().toString().contains("TRAPDOOR")) {
				if (p.getInventory().getItemInMainHand().hasItemMeta()) {
					
					BlockState blockState = b.getState();
					Openable door = (Openable) blockState.getData();
					
					if (p.getInventory().getItemInMainHand().hasItemMeta() && p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
						switch(p.getInventory().getItemInMainHand().getItemMeta().getLore().get(0)){	
							case "Número de cerradura:":
								e.setCancelled(true);
								
								List<String> lore = p.getInventory().getItemInMainHand().getItemMeta().getLore();
								
								if (b.getType().toString().contains("DOOR")) {
									String idLlave = lore.get(1);
									String dificultadCerradura = lore.get(2);
									
									if (!b.hasMetadata("locked")) {
										crearLocked(b.getLocation(), idLlave, dificultadCerradura, true);
										p.getInventory().remove(p.getInventory().getItemInMainHand());
									} else {
										p.sendTitle(ChatColor.GREEN+"Esta puerta ya tiene cerradura","");
									}
										
								}
								
								break;
							
								case "Número de llave:":
									if (b.hasMetadata("locked")) {
										e.setCancelled(true);
										
										if (p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
											List<String> lorellave = p.getInventory().getItemInMainHand().getItemMeta().getLore();
											
											if (b.getType().toString().contains("DOOR")) {
												String idLlave = lorellave.get(1);
													if (b.hasMetadata(idLlave)) {
														if (!door.isOpen()) {
															door.setOpen(true);
															blockState.setData((MaterialData) door);
															blockState.update();
															
														} else {
															door.setOpen(false);
															blockState.setData((MaterialData) door);
															blockState.update();
															
														}
													} else {
														p.sendTitle(ChatColor.RED+"Bloqueado", "");
													}
												}
											}
										}
								break;
								
								case "Ganzúa":
									if (b.hasMetadata("locked")) {
										e.setCancelled(true);
										
										if (b.hasMetadata("Débil")) {
											l.lockPick(p, b, 0);
										} else if (b.hasMetadata("Normal")) {
											l.lockPick(p, b, 1);
										} else if (b.hasMetadata("Dificil")) {
											l.lockPick(p, b, 2);
										}
										
										for (Player players : Bukkit.getOnlinePlayers()) {
											if (p.getLocation().distanceSquared(players.getLocation()) <= 10) {
												players.sendMessage(ChatColor.GOLD+p.getDisplayName()+ ChatColor.WHITE+" está forzando una cerradura");
											}
										}
										
									} else {
										p.sendTitle(ChatColor.GREEN+"Esta puerta no tiene cerradura","");
									}
									break;
								
								default:
									if (b.hasMetadata("locked")) {
										e.setCancelled(true);
										p.sendTitle(ChatColor.RED+"Bloqueado", "");
									}
									
									break;
							
							
						}
					} else {
						if (b.hasMetadata("locked")) {
							e.setCancelled(true);
							p.sendTitle(ChatColor.RED+"Bloqueado", "");
						}
					}
				} else {
					if (b.hasMetadata("locked")) {
						e.setCancelled(true);
						p.sendTitle(ChatColor.RED+"Bloqueado", "");
					}
				}
			//SI HACES CLICK DERECHO EN UNA PUERTA
			} else if (b.getType().toString().contains("DOOR")) {
				if (p.getInventory().getItemInMainHand().hasItemMeta()) {
					
					BlockState blockState = b.getState();
					Door door = (Door) blockState.getData();
					
					if (p.getInventory().getItemInMainHand().hasItemMeta() && p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
						switch(p.getInventory().getItemInMainHand().getItemMeta().getLore().get(0)){	
							case "Número de cerradura:":
								e.setCancelled(true);
								
								List<String> lore = p.getInventory().getItemInMainHand().getItemMeta().getLore();
								
								if (door.isTopHalf()) {
									Location l = new Location(p.getWorld(), b.getX(), b.getY()-1, b.getZ());
									Block ba = l.getBlock();
									
									if (!ba.hasMetadata("locked")) {
										String idLlave = lore.get(1);
										String dificultadCerradura = lore.get(2);
										
										crearLocked(ba.getLocation(), idLlave, dificultadCerradura, true);
										p.getInventory().remove(p.getInventory().getItemInMainHand());
									} else {
										p.sendTitle(ChatColor.GREEN+"Esta puerta ya tiene cerradura","");
									}
	
									break;
								}
								
								if (b.getType().toString().contains("DOOR")) {
									String idLlave = lore.get(1);
									String dificultadCerradura = lore.get(2);
									
									if (!b.hasMetadata("locked")) {
										crearLocked(b.getLocation(), idLlave, dificultadCerradura, true);
										p.getInventory().remove(p.getInventory().getItemInMainHand());
									} else {
										p.sendTitle(ChatColor.GREEN+"Esta puerta ya tiene cerradura","");
									}
										
								}
								
								break;
							
								case "Número de llave:":
									BlockState blockStateDown1 = b.getState();
									Door doordown1 = (Door) blockStateDown1.getData();
									
									Location ldown = new Location(p.getWorld(), b.getX(), b.getY()-1, b.getZ());
									Block badown = ldown.getBlock();
									if (door.isTopHalf()) {
										if (badown.hasMetadata("locked")) {
											e.setCancelled(true);
											
											if (p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
												List<String> lorellave = p.getInventory().getItemInMainHand().getItemMeta().getLore();
												if (b.getType().toString().contains("DOOR")) {
													String idLlave = lorellave.get(1);
	
													if (door.isTopHalf()) {
	
														Location l = new Location(p.getWorld(), b.getX(), b.getY()-1, b.getZ());
														Block ba = l.getBlock();
														
														if (ba.hasMetadata(idLlave)) {
															BlockState blockStateDown = ba.getState();
															Door doordown = (Door) blockStateDown.getData();
															
															if (!doordown.isOpen()) {
																doordown.setOpen(true);
																blockStateDown.setData((MaterialData) doordown);
																blockStateDown.update();
																
															} else {
																doordown.setOpen(false);
																blockStateDown.setData((MaterialData) doordown);
																blockStateDown.update();
																
															}
														} else {
															p.sendTitle(ChatColor.RED+"Bloqueado", "");
														}
													} else {
														if (b.hasMetadata(idLlave)) {
															if (!door.isOpen()) {
																door.setOpen(true);
																blockState.setData((MaterialData) door);
																blockState.update();
																
															} else {
																door.setOpen(false);
																blockState.setData((MaterialData) door);
																blockState.update();
																
															}
														} else {
															p.sendTitle(ChatColor.RED+"Bloqueado", "");
														}
													}
												}
											}
										}
									}
									if (b.hasMetadata("locked")) {
										e.setCancelled(true);
										
										if (p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
											List<String> lorellave = p.getInventory().getItemInMainHand().getItemMeta().getLore();
											if (b.getType().toString().contains("DOOR")) {
												String idLlave = lorellave.get(1);
													
												if (door.isTopHalf()) {
													Location l = new Location(p.getWorld(), b.getX(), b.getY()-1, b.getZ());
													Block ba = l.getBlock();
													
													if (ba.hasMetadata(idLlave)) {
														BlockState blockStateDown = ba.getState();
														Door doordown = (Door) blockStateDown.getData();
														
														if (!doordown.isOpen()) {
															doordown.setOpen(true);
															blockStateDown.setData((MaterialData) doordown);
															blockStateDown.update();
															
														} else {
															doordown.setOpen(false);
															blockStateDown.setData((MaterialData) doordown);
															blockStateDown.update();
															
														}
													} else {
														p.sendTitle(ChatColor.RED+"Bloqueado", "");
													}
												} else {
													if (b.hasMetadata(idLlave)) {
														if (!door.isOpen()) {
															door.setOpen(true);
															blockState.setData((MaterialData) door);
															blockState.update();
															
														} else {
															door.setOpen(false);
															blockState.setData((MaterialData) door);
															blockState.update();
															
														}
													} else {
														p.sendTitle(ChatColor.RED+"Bloqueado", "");
													}
												}
											}
										}
									}
								break;
								
								case "Ganzúa":
									if (door.isTopHalf()) {
										Location loc = new Location(p.getWorld(), b.getX(), b.getY()-1, b.getZ());
										Block ba = loc.getBlock();
										
										if (ba.hasMetadata("locked")) {
											e.setCancelled(true);
	
											if (ba.hasMetadata("Débil")) {
												l.lockPick(p, b, 0);
											} else if (ba.hasMetadata("Normal")) {
												l.lockPick(p, b, 1);
											} else if (ba.hasMetadata("Dificil")) {
												l.lockPick(p, b, 2);
											}
											
											for (Player players : Bukkit.getOnlinePlayers()) {
												if (p.getLocation().distanceSquared(players.getLocation()) <= 10) {
													players.sendMessage(ChatColor.GOLD+p.getDisplayName()+ ChatColor.WHITE+" está forzando una cerradura");
												}
											}
											
										} else {
											p.sendTitle(ChatColor.GREEN+"Esta puerta no tiene cerradura","");
										}
									} else {
										if (b.hasMetadata("locked")) {
											e.setCancelled(true);
											
											if (b.hasMetadata("Débil")) {
												l.lockPick(p, b, 0);
											} else if (b.hasMetadata("Normal")) {
												l.lockPick(p, b, 1);
											} else if (b.hasMetadata("Dificil")) {
												l.lockPick(p, b, 2);
											}
											
											for (Player players : Bukkit.getOnlinePlayers()) {
												if (p.getLocation().distanceSquared(players.getLocation()) <= 10) {
													players.sendMessage(ChatColor.GOLD+p.getDisplayName()+ ChatColor.WHITE+" está forzando una cerradura");
												}
											}
											
										} else {
											p.sendTitle(ChatColor.GREEN+"Esta puerta no tiene cerradura","");
										}
									}
									break;
								
								default:
									if (door.isTopHalf()) {
										Location l = new Location(p.getWorld(), b.getX(), b.getY()-1, b.getZ());
										Block ba = l.getBlock();
										
										if (ba.hasMetadata("locked")) {
											e.setCancelled(true);
											p.sendTitle(ChatColor.RED+"Bloqueado", "");
										}	
									}
									if (b.hasMetadata("locked")) {
										e.setCancelled(true);
										p.sendTitle(ChatColor.RED+"Bloqueado", "");
									}
									
									break;
							
							
						}
					} else {
						if (door.isTopHalf()) {
							Location l = new Location(p.getWorld(), b.getX(), b.getY()-1, b.getZ());
							Block ba = l.getBlock();
							
							if (ba.hasMetadata("locked")) {
								e.setCancelled(true);
								p.sendTitle(ChatColor.RED+"Bloqueado", "");
							}
						} else {
							if (b.hasMetadata("locked")) {
								e.setCancelled(true);
								p.sendTitle(ChatColor.RED+"Bloqueado", "");
							}
						}
					}
				} else {
					BlockState blockState3 = b.getState();
					Door door3 = (Door) blockState3.getData();
					
					if (door3.isTopHalf()) {
						Location l = new Location(p.getWorld(), b.getX(), b.getY()-1, b.getZ());
						Block ba = l.getBlock();
						
						if (ba.hasMetadata("locked")) {
							e.setCancelled(true);
							p.sendTitle(ChatColor.RED+"Bloqueado", "");
						}
					}
					if (b.hasMetadata("locked")) {
						e.setCancelled(true);
						p.sendTitle(ChatColor.RED+"Bloqueado", "");
					}
				}
				
			//SI HACES CLICK DERECHO EN UN CHEST/SHULKER BOX
			} else if (b.getType().toString().contains("CHEST") || b.getType().toString().contains("SHULKER")) {
				if (p.getInventory().getItemInMainHand().hasItemMeta() && p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
					switch(p.getInventory().getItemInMainHand().getItemMeta().getLore().get(0)){
						case "Número de cerradura:":
							e.setCancelled(true);
							
							List<String> lore = p.getInventory().getItemInMainHand().getItemMeta().getLore();
							String idLlave = lore.get(1);
							String dificultadCerradura = lore.get(2);
							
							if (!b.hasMetadata("locked")) {
								crearLocked(b.getLocation(), idLlave, dificultadCerradura, true);
								p.getInventory().remove(p.getInventory().getItemInMainHand());
							} else {
								p.sendTitle(ChatColor.GREEN+"Este cofre ya tiene cerradura","");
							}
							
							break;
							
						case "Número de llave:":
							if (b.hasMetadata("locked")) {
								if (p.getInventory().getItemInMainHand().getItemMeta().hasLore()) {
									List<String> lorellave = p.getInventory().getItemInMainHand().getItemMeta().getLore();
									
									if (!b.hasMetadata(lorellave.get(1))) {
										e.setCancelled(true);
										p.sendTitle(ChatColor.RED+"Bloqueado", "");
									}
								} else {
									e.setCancelled(true);
									p.sendTitle(ChatColor.RED+"Bloqueado", "");
								}
							}
							break;
						
						case "Ganzúa":
							if (b.hasMetadata("locked")) {
								e.setCancelled(true);

								if (b.hasMetadata("Débil")) {
									l.lockPick(p, b, 0);
								} else if (b.hasMetadata("Normal")) {
									l.lockPick(p, b, 1);
								} else if (b.hasMetadata("Dificil")) {
									l.lockPick(p, b, 2);
								}
								
								for (Player players : Bukkit.getOnlinePlayers()) {
									if (p.getLocation().distanceSquared(players.getLocation()) <= 10) {
										players.sendMessage(ChatColor.GOLD+p.getDisplayName()+ ChatColor.WHITE+" está forzando una cerradura");
									}
								}
								
							} else {
								p.sendTitle(ChatColor.GREEN+"Este cofre no tiene cerradura","");
							}
							break;
							
						default:
							if (b.hasMetadata("locked")) {
								e.setCancelled(true);
								p.sendTitle(ChatColor.RED+"Bloqueado", "");
							}
							break;
					}
					
				} else {
					if (b.hasMetadata("locked")) {
						e.setCancelled(true);
						p.sendTitle(ChatColor.RED+"Bloqueado", "");
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Block b = e.getBlock();
		
		if (b.hasMetadata("locked")) {
			b.removeMetadata("locked", this);
			eliminarLocks(b.getLocation());
		}
	}
	
	//MÉTODOS PARA GUARDAR, CARGAR Y CREAR PUERTAS/BLOQUES
	public void guardarLocks(Block b, String ruta, String nombre, String dificultad) {
		File f  = new File(ruta);
		
		try {
			if (!f.exists()) {
				f.createNewFile();

			}
			
			//Nombre x y z
			String datos[] = {nombre, dificultad, String.valueOf(b.getX()), String.valueOf(b.getY()), String.valueOf(b.getZ())};
			
			FileWriter fw = new FileWriter(f, true);
			BufferedWriter bw = new BufferedWriter (fw);
			
			for (String dato : datos) {
				bw.write(dato+" ");
			}
			bw.newLine();
			
			bw.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void cargarLocks() {
		String rutaArchivo = "plugins/GothicKeys/locks.txt";
		File f = new File(rutaArchivo);
		
		try {
			if (f.exists()) {
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				
				String line;
				while((line = br.readLine()) != null) {
					String[] datos = line.split(" ");
					
					String nombre = datos[0];
					String dificultadCerradura = datos[1];
					Location l = new Location(Bukkit.getWorld("Gothic"), Integer.valueOf(datos[2]), Integer.valueOf(datos[3]), Integer.valueOf(datos[4]));
					
					crearLocked(l, nombre, dificultadCerradura, false);
				}
				
				br.close();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void crearLocked(Location loc, String nombre, String dificultadCerradura, boolean primeravez) {
		String rutaArchivo = "plugins/GothicKeys/locks.txt";

		Location posicion = loc;
		Block b = posicion.getBlock();
		b.setMetadata("locked", new FixedMetadataValue(this, "codigo"));
		b.setMetadata(dificultadCerradura, new FixedMetadataValue(this, "codigo"));
		b.setMetadata(nombre, new FixedMetadataValue(this, "codigo"));
		
		if (primeravez) {
			guardarLocks(b, rutaArchivo, nombre, dificultadCerradura);
		}

	}
	
	public void eliminarLocks(Location l) {
		String rutaArchivo = "plugins/GothicKeys/locks.txt";
		File f = new File(rutaArchivo);
		
		String coordenadas = (int) (l.getX()) + " " + (int) (l.getY()) + " " + (int) (l.getZ());
		
		ArrayList<String> nuevoArchivo = new ArrayList<String>();
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			while((line = br.readLine()) != null) {
				if (!line.contains(coordenadas)) {
					nuevoArchivo.add(line);
				}
			}
			
			br.close();
			f.delete();
			
			f.createNewFile();
			
			FileWriter fw = new FileWriter(f, true);
			BufferedWriter bw = new BufferedWriter (fw);

			for (String dato : nuevoArchivo) {
				bw.write(dato);
				bw.newLine();
			}
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
			
	}

}
