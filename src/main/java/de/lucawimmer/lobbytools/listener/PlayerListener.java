/*
 * Copyright (c) 2014, Luca Wimmer(magl1te)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.lucawimmer.lobbytools.listener;

import de.lucawimmer.lobbytools.LobbyTools;
import de.lucawimmer.lobbytools.utils.SimpleConfig;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class PlayerListener implements Listener {

    private SimpleConfig config = LobbyTools.getDefaultConfig();

    @EventHandler
    public void clickInventory(InventoryClickEvent evt) {
        Player player = (Player) evt.getWhoClicked();
        if (config.getBoolean("lobbytools.disable-inventory-move")) {
            if (config.getBoolean("lobbytools.use-permissions")) {
                if (config.getBoolean("lobbytools.use-per-world-permissions")) {
                    if (!player.hasPermission("lobbytools." + player.getWorld().getName() + ".allowinventory")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            evt.setCancelled(true);
                    }
                } else {
                    if (!player.hasPermission("lobbytools.allowinventory")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            evt.setCancelled(true);
                    }
                }
            } else {
                if (!LobbyTools.TOGGLE.contains(player))
                    evt.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMoveItem(InventoryMoveItemEvent evt) {
        if (!(evt.getSource().getHolder() instanceof Player))
            return;

        Player player = (Player) evt.getSource().getHolder();
        if (config.getBoolean("lobbytools.disable-inventory-move")) {
            if (config.getBoolean("lobbytools.use-permissions")) {
                if (config.getBoolean("lobbytools.use-per-world-permissions")) {
                    if (!player.hasPermission("lobbytools." + player.getWorld().getName() + ".allowinventory")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            evt.setCancelled(true);
                    }
                } else {
                    if (!player.hasPermission("lobbytools.allowinventory")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            evt.setCancelled(true);
                    }
                }
            } else {
                if (!LobbyTools.TOGGLE.contains(player))
                    evt.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void itemDrop(PlayerDropItemEvent evt) {
        Player player = evt.getPlayer();
        if (config.getBoolean("lobbytools.disable-item-drop")) {
            if (config.getBoolean("lobbytools.use-permissions")) {
                if (config.getBoolean("lobbytools.use-per-world-permissions")) {
                    if (!player.hasPermission("lobbytools." + player.getWorld().getName() + ".allowitemdrop")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            evt.setCancelled(true);
                    }
                } else {
                    if (!player.hasPermission("lobbytools.allowitemdrop")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            evt.setCancelled(true);
                    }
                }

            } else {
                if (!LobbyTools.TOGGLE.contains(player))
                    evt.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void itemPickup(PlayerPickupItemEvent evt) {
        Player player = evt.getPlayer();
        if (config.getBoolean("lobbytools.disable-item-pickup")) {
            if (config.getBoolean("lobbytools.use-permissions")) {
                if (config.getBoolean("lobbytools.use-per-world-permissions")) {
                    if (!player.hasPermission("lobbytools." + player.getWorld().getName() + ".allowitempickup")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            evt.setCancelled(true);
                    }
                } else {
                    if (!player.hasPermission("lobbytools.allowitempickup")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            evt.setCancelled(true);
                    }
                }

            } else {
                if (!LobbyTools.TOGGLE.contains(player))
                    evt.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent evt) {
        Player player = evt.getPlayer();
        if (config.getBoolean("lobbytools.disable-build")) {
            if (config.getBoolean("lobbytools.use-permissions")) {
                if (config.getBoolean("lobbytools.use-per-world-permissions")) {
                    if (!player.hasPermission("lobbytools." + player.getWorld().getName() + ".allowbuild")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            evt.setCancelled(true);
                    }
                } else {
                    if (!player.hasPermission("lobbytools.allowbuild")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            evt.setCancelled(true);
                    }
                }
            } else {
                if (!LobbyTools.TOGGLE.contains(player))
                    evt.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent evt) {
        Player player = evt.getPlayer();
        if (config.getBoolean("lobbytools.disable-build")) {
            if (config.getBoolean("lobbytools.use-permissions")) {
                if (config.getBoolean("lobbytools.use-per-world-permissions")) {
                    if (!player.hasPermission("lobbytools." + player.getWorld().getName() + ".allowbuild")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            evt.setCancelled(true);
                    }
                } else {
                    if (!player.hasPermission("lobbytools.allowbuild")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            evt.setCancelled(true);
                    }
                }

            } else {
                if (!LobbyTools.TOGGLE.contains(player))
                    evt.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent evt) {
        if (config.getBoolean("lobbytools.clearinv")) {
            evt.getPlayer().getInventory().clear();
        }

        if (config.getBoolean(("lobbytools.use-exact-spawn"))) {
            evt.getPlayer().teleport(config.getLocation("lobbytools.exact-spawn-loc"));
        }

        for (int x = 0; x < 10; x = x + 1) {
            if (config.getBoolean("lobbytools.hotbar.slot" + x + ".use")) {
                ItemStack item = parseString(config.getString("lobbytools.hotbar.slot" + x + ".id"));
                if (config.getString("lobbytools.hotbar.slot" + x + ".id") != null && config.getString("lobbytools.hotbar.slot" + x + ".name") != null) {
                    ItemMeta im = item.getItemMeta();
                    im.setDisplayName(config.getString("lobbytools.hotbar.slot" + x + ".name").replaceAll("&", "§"));
                    item.setItemMeta(im);
                }
                evt.getPlayer().getInventory().setItem(x - 1, item);
            }
        }

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent evt) {
        if (config.getBoolean("lobbytools.disable-chat")) {
            if (!LobbyTools.TOGGLE.contains(evt.getPlayer()))
                evt.setCancelled(true);
        }
        if (LobbyTools.CHAT) {
            if (!LobbyTools.TOGGLE.contains(evt.getPlayer()))
                evt.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (config.getBoolean("lobbytools.disable-falldamage")) {
                    event.setCancelled(true);
                }
            }

            if (config.getBoolean("lobbytools.disable-alldamage")) {
                event.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (config.getBoolean("lobbytools.enable-teleport-height"))
            if (e.getPlayer().getLocation().getY() < config.getInt("lobbytools.teleport-height"))
                e.getPlayer().teleport(config.getLocation("lobbytools.teleport-height-location"));
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (config.getBoolean("lobbytools.disable-hunger")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent evt) {
        if (config.getBoolean("lobbytools.disable-death-messages"))
            evt.setDeathMessage(null);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent evt) {
        if (config.getBoolean("lobbytools.clearinv")) {
            evt.getPlayer().getInventory().clear();
        }

        for (int x = 0; x < 10; x = x + 1) {
            if (config.getBoolean("lobbytools.hotbar.slot" + x + ".use")) {
                ItemStack item = parseString(config.getString("lobbytools.hotbar.slot" + x + ".id"));
                if (config.getString("lobbytools.hotbar.slot" + x + ".id") != null && config.getString("lobbytools.hotbar.slot" + x + ".name") != null) {
                    ItemMeta im = item.getItemMeta();
                    im.setDisplayName(config.getString("lobbytools.hotbar.slot" + x + ".name").replaceAll("&", "§"));
                    item.setItemMeta(im);
                }
                evt.getPlayer().getInventory().setItem(x - 1, item);
            }
        }

    }

    public ItemStack parseString(String itemId) {
        String[] parts = itemId.split(":");
        int matId = Integer.parseInt(parts[0]);
        if (parts.length == 2) {
            short data = Short.parseShort(parts[1]);
            return new ItemStack(Material.getMaterial(matId), 1, data);
        }
        return new ItemStack(Material.getMaterial(matId));
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent evt) {
        Player player = evt.getPlayer();
        if (evt.getTo().getBlockX() == evt.getFrom().getBlockX() &&
                evt.getTo().getBlockY() == evt.getFrom().getBlockY() &&
                evt.getTo().getBlockZ() == evt.getFrom().getBlockZ()) {
            return;
        }


        if (config.getBoolean("lobbytools.enable-trampoline")) {
            if (evt.getPlayer().getLocation().getWorld().getBlockAt(evt.getPlayer().getLocation()).getType() == Material.getMaterial(171)) {
                if (evt.getPlayer().getLocation().getWorld().getBlockAt(evt.getPlayer().getLocation()).getRelative(0, -1, 0).getType() == Material.getMaterial(config.getInt("lobbytools.trampoline-id"))) {
                    evt.getPlayer().setVelocity(new Vector(evt.getPlayer().getVelocity().getX(), config.getInt("lobbytools.trampoline-height"), evt.getPlayer().getVelocity().getZ()));
                }
            }
        }

        if (config.getBoolean("lobbytools.enable-launchpad")) {
            Material blockid = evt.getPlayer().getLocation().getWorld().getBlockAt(evt.getPlayer().getLocation()).getType();
            if (blockid == Material.STONE_PLATE || blockid == Material.GOLD_PLATE || blockid == Material.IRON_PLATE || blockid == Material.WOOD_PLATE) {
                if (evt.getPlayer().getLocation().getWorld().getBlockAt(evt.getPlayer().getLocation()).getRelative(0, -1, 0).getType() == Material.getMaterial(config.getInt("lobbytools.launchpad-id"))) {
                    evt.getPlayer().setVelocity(evt.getPlayer().getLocation().getDirection().multiply(config.getInt("lobbytools.launchpad-speed")));
                    evt.getPlayer().setVelocity(new Vector(evt.getPlayer().getVelocity().getX(), 1.0D, evt.getPlayer().getVelocity().getZ()));
                    player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1, 1);
                    player.getWorld().playEffect(player.getLocation(), Effect.SMOKE, 1);
                }
            }
        }
    }
}
