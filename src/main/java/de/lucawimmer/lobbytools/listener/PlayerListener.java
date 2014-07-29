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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
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

import java.util.HashSet;

public class PlayerListener implements Listener {

    private SimpleConfig config = LobbyTools.getDefaultConfig();

    public static Entity[] getNearbyEntities(Location l, int radius) {
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16)) / 16;
        HashSet<Entity> radiusEntities = new HashSet<Entity>();
        for (int chX = 0 - chunkRadius; chX <= chunkRadius; chX++) {
            for (int chZ = 0 - chunkRadius; chZ <= chunkRadius; chZ++) {
                int x = (int) l.getX(), y = (int) l.getY(), z = (int) l.getZ();
                for (Entity e : new Location(l.getWorld(), x + (chX * 16), y, z + (chZ * 16)).getChunk().getEntities()) {
                    if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock())
                        radiusEntities.add(e);
                }
            }
        }
        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }

    @EventHandler
    public void clickInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (config.getBoolean("lobbytools.disable-inventory-move")) {
            if (config.getBoolean("lobbytools.use-permissions")) {
                if (config.getBoolean("lobbytools.use-per-world-permissions")) {
                    if (!player.hasPermission("lobbytools." + player.getWorld().getName() + ".allowinventory")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            e.setCancelled(true);
                    }
                } else {
                    if (!player.hasPermission("lobbytools.allowinventory")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            e.setCancelled(true);
                    }
                }
            } else {
                if (!LobbyTools.TOGGLE.contains(player))
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMoveItem(InventoryMoveItemEvent e) {
        if (!(e.getSource().getHolder() instanceof Player))
            return;

        Player player = (Player) e.getSource().getHolder();
        if (config.getBoolean("lobbytools.disable-inventory-move")) {
            if (config.getBoolean("lobbytools.use-permissions")) {
                if (config.getBoolean("lobbytools.use-per-world-permissions")) {
                    if (!player.hasPermission("lobbytools." + player.getWorld().getName() + ".allowinventory")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            e.setCancelled(true);
                    }
                } else {
                    if (!player.hasPermission("lobbytools.allowinventory")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            e.setCancelled(true);
                    }
                }
            } else {
                if (!LobbyTools.TOGGLE.contains(player))
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void itemDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        if (config.getBoolean("lobbytools.disable-item-drop")) {
            if (config.getBoolean("lobbytools.use-permissions")) {
                if (config.getBoolean("lobbytools.use-per-world-permissions")) {
                    if (!player.hasPermission("lobbytools." + player.getWorld().getName() + ".allowitemdrop")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            e.setCancelled(true);
                    }
                } else {
                    if (!player.hasPermission("lobbytools.allowitemdrop")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            e.setCancelled(true);
                    }
                }

            } else {
                if (!LobbyTools.TOGGLE.contains(player))
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void itemPickup(PlayerPickupItemEvent e) {
        Player player = e.getPlayer();
        if (config.getBoolean("lobbytools.disable-item-pickup")) {
            if (config.getBoolean("lobbytools.use-permissions")) {
                if (config.getBoolean("lobbytools.use-per-world-permissions")) {
                    if (!player.hasPermission("lobbytools." + player.getWorld().getName() + ".allowitempickup")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            e.setCancelled(true);
                    }
                } else {
                    if (!player.hasPermission("lobbytools.allowitempickup")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            e.setCancelled(true);
                    }
                }

            } else {
                if (!LobbyTools.TOGGLE.contains(player))
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (config.getBoolean("lobbytools.disable-build")) {
            if (config.getBoolean("lobbytools.use-permissions")) {
                if (config.getBoolean("lobbytools.use-per-world-permissions")) {
                    if (!player.hasPermission("lobbytools." + player.getWorld().getName() + ".allowbuild")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            e.setCancelled(true);
                    }
                } else {
                    if (!player.hasPermission("lobbytools.allowbuild")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            e.setCancelled(true);
                    }
                }
            } else {
                if (!LobbyTools.TOGGLE.contains(player))
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (config.getBoolean("lobbytools.disable-build")) {
            if (config.getBoolean("lobbytools.use-permissions")) {
                if (config.getBoolean("lobbytools.use-per-world-permissions")) {
                    if (!player.hasPermission("lobbytools." + player.getWorld().getName() + ".allowbuild")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            e.setCancelled(true);
                    }
                } else {
                    if (!player.hasPermission("lobbytools.allowbuild")) {
                        if (!LobbyTools.TOGGLE.contains(player))
                            e.setCancelled(true);
                    }
                }

            } else {
                if (!LobbyTools.TOGGLE.contains(player))
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (config.getBoolean("lobbytools.clearinv"))
            e.getPlayer().getInventory().clear();

        if (config.getBoolean(("lobbytools.use-exact-spawn")))
            e.getPlayer().teleport(config.getLocation("lobbytools.exact-spawn-loc"));

        if (config.getBoolean("lobbytools.disable-join-message"))
            e.setJoinMessage(null);

        for (int x = 0; x < 10; x = x + 1) {
            if (config.getBoolean("lobbytools.hotbar.slot" + x + ".use")) {
                ItemStack item = parseString(config.getString("lobbytools.hotbar.slot" + x + ".id"));
                if (config.getString("lobbytools.hotbar.slot" + x + ".id") != null && config.getString("lobbytools.hotbar.slot" + x + ".name") != null) {
                    ItemMeta im = item.getItemMeta();
                    im.setDisplayName(config.getString("lobbytools.hotbar.slot" + x + ".name").replaceAll("&", "§"));
                    item.setItemMeta(im);
                }
                e.getPlayer().getInventory().setItem(x - 1, item);
            }
        }

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (config.getBoolean("lobbytools.disable-chat")) {
            if (!LobbyTools.TOGGLE.contains(e.getPlayer()))
                e.setCancelled(true);
        }
        if (LobbyTools.CHAT) {
            if (!LobbyTools.TOGGLE.contains(e.getPlayer()))
                e.setCancelled(true);
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
    public void onDeath(PlayerDeathEvent e) {
        if (config.getBoolean("lobbytools.disable-death-messages"))
            e.setDeathMessage(null);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if (config.getBoolean("lobbytools.clearinv")) {
            e.getPlayer().getInventory().clear();
        }

        for (int x = 0; x < 10; x = x + 1) {
            if (config.getBoolean("lobbytools.hotbar.slot" + x + ".use")) {
                ItemStack item = parseString(config.getString("lobbytools.hotbar.slot" + x + ".id"));
                if (config.getString("lobbytools.hotbar.slot" + x + ".id") != null && config.getString("lobbytools.hotbar.slot" + x + ".name") != null) {
                    ItemMeta im = item.getItemMeta();
                    im.setDisplayName(config.getString("lobbytools.hotbar.slot" + x + ".name").replaceAll("&", "§"));
                    item.setItemMeta(im);
                }
                e.getPlayer().getInventory().setItem(x - 1, item);
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
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (e.getTo().getBlockX() == e.getFrom().getBlockX() &&
                e.getTo().getBlockY() == e.getFrom().getBlockY() &&
                e.getTo().getBlockZ() == e.getFrom().getBlockZ()) {
            return;
        }


        if (config.getBoolean("lobbytools.enable-trampoline")) {
            if (e.getPlayer().getLocation().getWorld().getBlockAt(e.getPlayer().getLocation()).getType() == Material.getMaterial(171)) {
                if (e.getPlayer().getLocation().getWorld().getBlockAt(e.getPlayer().getLocation()).getRelative(0, -1, 0).getType() == Material.getMaterial(config.getInt("lobbytools.trampoline-id"))) {
                    e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getX(), config.getInt("lobbytools.trampoline-height"), e.getPlayer().getVelocity().getZ()));
                }
            }
        }

        if (config.getBoolean("lobbytools.enable-launchpad")) {
            Material blockid = e.getPlayer().getLocation().getWorld().getBlockAt(e.getPlayer().getLocation()).getType();
            if (blockid == Material.STONE_PLATE || blockid == Material.GOLD_PLATE || blockid == Material.IRON_PLATE || blockid == Material.WOOD_PLATE) {
                if (e.getPlayer().getLocation().getWorld().getBlockAt(e.getPlayer().getLocation()).getRelative(0, -1, 0).getType() == Material.getMaterial(config.getInt("lobbytools.launchpad-id"))) {
                    e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(config.getInt("lobbytools.launchpad-speed")));
                    e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getX(), 1.0D, e.getPlayer().getVelocity().getZ()));
                    player.playSound(player.getLocation(), Sound.WITHER_SHOOT, 1, 1);
                    for (Entity entity : getNearbyEntities(e.getPlayer().getLocation(), 30)) {
                        if (entity instanceof Player)
                            ((Player) entity).playEffect(e.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 4);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (config.getBoolean("lobbytools.disable-quit-message"))
            e.setQuitMessage(null);
    }


}
