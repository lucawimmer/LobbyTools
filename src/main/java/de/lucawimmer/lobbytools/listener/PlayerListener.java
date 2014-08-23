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
import de.lucawimmer.lobbytools.utils.ItemSerialization;
import de.lucawimmer.lobbytools.utils.SimpleConfig;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class PlayerListener implements Listener {

    private SimpleConfig config = LobbyTools.getDefaultConfig();

    private static Entity[] getNearbyEntities(Location l, int radius) {
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

    public boolean hasPermissions(Player p, String perm) {
        if (config.getBoolean("use-permissions")) {
            if (config.getStringList("world-permissions").contains("all") || config.getStringList("world-permissions").contains("*") || config.getStringList("world-permissions").contains(p.getWorld().getName())) {
                if (p.hasPermission(perm)) {
                    if (isToggled(p))
                        return true;
                }
            } else {
                return true;
            }
        } else {
            if (isToggled(p))
                return true;
        }
        return false;
    }

    public boolean isToggled(Player p) {
        return LobbyTools.TOGGLE.contains(p);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            Integer is = e.getPlayer().getInventory().getHeldItemSlot() + 1;
            if (config.getBoolean("hotbar.slot" + is + ".use") && config.getBoolean("hotbar.slot" + is + ".execute-command")) {
                if (e.getPlayer().getItemInHand().equals(ItemSerialization.stringToItem(config.getString("hotbar.slot" + is + ".item"))))
                    Bukkit.dispatchCommand(e.getPlayer(), config.getString("hotbar.slot" + is + ".command"));
            }
        }
    }

    @EventHandler
    public void clickInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (config.getBoolean("disable-inventory-move")) {
            if (!hasPermissions(player, "lobbytools.allowinventory"))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMoveItem(InventoryMoveItemEvent e) {
        if (!(e.getSource().getHolder() instanceof Player))
            return;

        Player player = (Player) e.getSource().getHolder();
        if (config.getBoolean("disable-inventory-move")) {
            if (!hasPermissions(player, "lobbytools.allowinventory"))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void itemDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        if (config.getBoolean("disable-item-drop")) {
            if (!hasPermissions(player, "lobbytools.allowitemdop"))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void itemPickup(PlayerPickupItemEvent e) {
        Player player = e.getPlayer();
        if (config.getBoolean("disable-item-pickup")) {
            if (!hasPermissions(player, "lobbytools.allowitempickup"))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (config.getBoolean("disable-build")) {
            if (!hasPermissions(player, "lobbytools.allowbuild"))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFrameBreak(HangingBreakByEntityEvent e) {
        if (e.getEntity() instanceof ItemFrame) {
            if (e.getRemover() instanceof Player) {
                Player player = (Player) e.getRemover();
                if (config.getBoolean("disable-build")) {
                    if (!hasPermissions(player, "lobbytools.allowbuild"))
                        e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onFrameInteract(PlayerInteractEntityEvent e) {
        if (e.getRightClicked().getType().equals(EntityType.ITEM_FRAME))
            if (!hasPermissions(e.getPlayer(), "lobbytools.allowbuild"))
                e.setCancelled(true);
    }

    @EventHandler
    public void onFrameDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (config.getBoolean("disable-build")) {
                if (!hasPermissions((Player) e.getEntity(), "lobbytools.allowbuild"))
                    e.setCancelled(true);
            }
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (config.getBoolean("disable-build")) {
            if (!hasPermissions(player, "lobbytools.allowbuild"))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (config.getBoolean("clearinv"))
            e.getPlayer().getInventory().clear();

        if (config.getBoolean(("use-exact-spawn")))
            e.getPlayer().teleport(config.getLocation("exact-spawn-loc"));

        if (config.getBoolean("disable-join-message"))
            e.setJoinMessage(null);

        if (config.getBoolean("auto-toggle-op"))
            if (e.getPlayer().isOp())
                if (!LobbyTools.TOGGLE.contains(e.getPlayer()))
                    LobbyTools.TOGGLE.add(e.getPlayer());

        for (int x = 0; x < 10; x = x + 1) {
            if (config.getBoolean("hotbar.slot" + x + ".use")) {
                if (config.getString("hotbar.slot" + x + ".item") != null && config.getString("hotbar.slot" + x + ".item") != null) {
                    e.getPlayer().getInventory().setItem(x - 1, ItemSerialization.stringToItem(config.getString("hotbar.slot" + x + ".item")));
                }
                //e.getPlayer().getInventory().setItem(x - 1, item);
            }
        }

        if (config.getBoolean("give-infinite-potions")) {
            if (config.getStringList("give-potions-worlds").contains("all") || config.getStringList("give-potions-worlds").contains("*") || config.getStringList("give-potions-worlds").contains(e.getPlayer().getWorld().getName())) {
                for (String s : config.getStringList("infinite-potions")) {
                    if (PotionEffectType.getByName(s.split(":")[0]) != null) {
                        Integer potionlevel = 0;
                        if (s.split(":").length != 1) potionlevel = Integer.parseInt(s.split(":")[1]);


                        for (PotionEffect effect : e.getPlayer().getActivePotionEffects()) {
                            List<String> allPotions = new ArrayList<String>();
                            for (String ps : config.getStringList("infinite-potions")) allPotions.add(ps.split(":")[0]);

                            if (!allPotions.contains(effect.getType().getName()))
                                e.getPlayer().removePotionEffect(effect.getType());
                        }
                        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.getByName(s.split(":")[0]), 999999999, potionlevel));
                    } else {
                        Bukkit.getLogger().warning("[LobbyTools] Wrong effect name: " + s.split(":")[0]);
                        Bukkit.getLogger().warning("[LobbyTools] Please fix the name in config!");
                    }
                }
            } else {
                for (PotionEffect effect : e.getPlayer().getActivePotionEffects())
                    e.getPlayer().removePotionEffect(effect.getType());
            }
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        if (config.getBoolean("give-infinite-potions")) {
            if (config.getStringList("give-potions-worlds").contains("all") || config.getStringList("give-potions-worlds").contains("*") || config.getStringList("give-potions-worlds").contains(e.getPlayer().getWorld().getName())) {
                for (String s : config.getStringList("infinite-potions")) {
                    if (PotionEffectType.getByName(s.split(":")[0]) != null) {
                        Integer potionlevel = 0;
                        if (s.split(":").length != 1) potionlevel = Integer.parseInt(s.split(":")[1]);


                        for (PotionEffect effect : e.getPlayer().getActivePotionEffects()) {
                            List<String> allPotions = new ArrayList<String>();
                            for (String ps : config.getStringList("infinite-potions")) allPotions.add(ps.split(":")[0]);

                            if (!allPotions.contains(effect.getType().getName()))
                                e.getPlayer().removePotionEffect(effect.getType());
                        }
                        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.getByName(s.split(":")[0]), 999999999, potionlevel));
                    } else {
                        Bukkit.getLogger().warning("[LobbyTools] Wrong effect name: " + s.split(":")[0]);
                        Bukkit.getLogger().warning("[LobbyTools] Please fix the name in config!");
                    }
                }
            } else {
                for (PotionEffect effect : e.getPlayer().getActivePotionEffects())
                    e.getPlayer().removePotionEffect(effect.getType());
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (config.getBoolean("disable-chat")) {
            if (!LobbyTools.TOGGLE.contains(e.getPlayer()))
                e.setCancelled(true);
        }
        if (LobbyTools.CHAT) {
            if (!LobbyTools.TOGGLE.contains(e.getPlayer()))
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPreCommand(PlayerCommandPreprocessEvent e) {
        String[] args = e.getMessage().split(" ");

        if (config.getBoolean("disable-commands")) {
            List<String> whitelisted = Arrays.asList("/lobbytools", "/lt", "/lobbytool");
            List<String> blacklisted = new ArrayList<String>();
            for (String s : config.getStringList("disabled-commands-list"))
                if (s != null) blacklisted.add(s);

            if (blacklisted.contains(args[0]) || config.getStringList("disabled-commands-list").contains("all") || config.getStringList("disabled-commands-list").contains("*")) {
                if (!whitelisted.contains(args[0]))
                    if (!LobbyTools.TOGGLE.contains(e.getPlayer()))
                        e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (config.getBoolean("disable-falldamage"))
                    event.setCancelled(true);
            }

            if (config.getBoolean("disable-alldamage"))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (config.getBoolean("enable-teleport-height"))
            if (e.getPlayer().getLocation().getY() < config.getInt("teleport-height"))
                e.getPlayer().teleport(config.getLocation("teleport-height-location"));
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (config.getBoolean("disable-hunger")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (config.getBoolean("disable-death-messages"))
            e.setDeathMessage(null);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if (config.getBoolean("clearinv")) {
            e.getPlayer().getInventory().clear();
        }

        for (int x = 0; x < 10; x = x + 1) {
            if (config.getBoolean("hotbar.slot" + x + ".use")) {
                if (config.getString("hotbar.slot" + x + ".item") != null && config.getString("hotbar.slot" + x + ".item") != null) {
                    e.getPlayer().getInventory().setItem(x - 1, ItemSerialization.stringToItem(config.getString("hotbar.slot" + x + ".item")));
                }
                //e.getPlayer().getInventory().setItem(x - 1, item);
            }
        }

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (e.getTo().getBlockX() == e.getFrom().getBlockX() &&
                e.getTo().getBlockY() == e.getFrom().getBlockY() &&
                e.getTo().getBlockZ() == e.getFrom().getBlockZ()) {
            return;
        }


        if (config.getBoolean("enable-trampoline")) {
            if (e.getPlayer().getLocation().getWorld().getBlockAt(e.getPlayer().getLocation()).getType() == Material.getMaterial(171)) {
                if (e.getPlayer().getLocation().getWorld().getBlockAt(e.getPlayer().getLocation()).getRelative(0, -1, 0).getType() == Material.getMaterial(config.getInt("trampoline-id"))) {
                    e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getX(), (config.getInt("trampoline-height") / 2), e.getPlayer().getVelocity().getZ()));
                }
            }
        }

        if (config.getBoolean("enable-launchpad")) {
            Material blockid = e.getPlayer().getLocation().getWorld().getBlockAt(e.getPlayer().getLocation()).getType();
            if (blockid == Material.STONE_PLATE || blockid == Material.GOLD_PLATE || blockid == Material.IRON_PLATE || blockid == Material.WOOD_PLATE) {
                if (e.getPlayer().getLocation().getWorld().getBlockAt(e.getPlayer().getLocation()).getRelative(0, -1, 0).getType() == Material.getMaterial(config.getInt("launchpad-id"))) {
                    e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(config.getInt("launchpad-speed")));
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
        if (config.getBoolean("disable-quit-message"))
            e.setQuitMessage(null);
    }
}
