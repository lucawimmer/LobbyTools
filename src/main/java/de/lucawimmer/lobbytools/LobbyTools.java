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

package de.lucawimmer.lobbytools;


import de.lucawimmer.lobbytools.commands.LobbyToolsCommand;
import de.lucawimmer.lobbytools.listener.PlayerListener;
import de.lucawimmer.lobbytools.utils.Metrics;
import de.lucawimmer.lobbytools.utils.SimpleConfig;
import de.lucawimmer.lobbytools.utils.SimpleConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;


public class LobbyTools extends JavaPlugin {

    public static HashSet<Player> TOGGLE = new HashSet<Player>();
    public static boolean CHAT = false;
    private static SimpleConfig CONFIG;
    private static JavaPlugin PLUGIN;

    public SimpleConfigManager manager;

    public static SimpleConfig getDefaultConfig() {
        return CONFIG;
    }

    public static JavaPlugin getPlugin() {
        return PLUGIN;
    }

    @Override
    public void onEnable() {
        PLUGIN = this;
        this.manager = new SimpleConfigManager(this);
        String[] header = {"LobbyTools", "Developed and written by", "magl1te", "skypvp.cc:25565"};
        CONFIG = manager.getNewConfig("config.yml", header);
        CONFIG.addDefault("use-permissions", false, new String[]{"Enable permissions? Please note to enter the worlds below"});
        CONFIG.addDefault("world-permissions", Arrays.asList("world", "lobby", "hub"), new String[]{"Enter all worlds you want to enable permissions", "Enter \"all\" or \"*\" to enable permissions for all worlds"});
        CONFIG.addDefault("auto-toggle-op", false, new String[]{"Should op players dont have to type /lt t?"});
        CONFIG.addDefault("use-exact-spawn", false, new String[]{"Want to spawn at a exact location?"});
        CONFIG.addDefault("exact-spawn-loc", "world,0,0,0,0,0", new String[]{"The location you want to spawn", "Please use /lobbytools setspawn"});
        CONFIG.addDefault("give-infinite-potions", false, new String[]{"Give players a infinite potion?"});
        CONFIG.addDefault("infinite-potions", Arrays.asList("SPEED:2", "JUMP:1"), new String[]{"Usage.. POTION_NAME(double point)LEVEL", "Example names.. www.goo.gl/xvqBtV"});
        CONFIG.addDefault("give-potions-worlds", Arrays.asList("world", "lobby", "hub"), new String[]{"Enter all worlds you want to give the potion", "Enter \"all\" or \"*\" to give potion in all worlds"});
        CONFIG.addDefault("disable-inventory-move", true, new String[]{"Disable item moves?", "Permissions? lobbytools.allowinventory"});
        CONFIG.addDefault("disable-item-drop", true, new String[]{"Disable item drops?", "Permissions? lobbytools.allowitemdop"});
        CONFIG.addDefault("disable-item-pickup", true, new String[]{"Disable pickup of items?", "Permissions? lobbytools.allowitempickup"});
        CONFIG.addDefault("disable-build", true, new String[]{"Disable placing fo blocks?", "Permissions? lobbytools.allowbuild"});
        CONFIG.addDefault("disable-death-messages", true, new String[]{"Disable death-messages?"});
        CONFIG.addDefault("disable-chat", false, new String[]{"Disable the chat?"});
        CONFIG.addDefault("disable-commands", false, new String[]{"Disable commands?", "Excludeing all lobbytools commands because this is toggable"});
        CONFIG.addDefault("disabled-commands-list", Arrays.asList("/warp", "/server"), new String[]{"Which commands you want to disable?", "Enter \"all\" or \"*\" for all"});
        CONFIG.addDefault("disable-join-message", true, new String[]{"Disable the join messages?"});
        CONFIG.addDefault("disable-quit-message", true, new String[]{"Disable the quit messages?"});
        CONFIG.addDefault("enable-launchpad", true, new String[]{"Enable the launchpad?"});
        CONFIG.addDefault("launchpad-id", 152, new String[]{"Launchpad block under the pressure plate [Redstone Block]"});
        CONFIG.addDefault("launchpad-speed", 6, new String[]{"Specify the launchpad speed"});
        CONFIG.addDefault("enable-trampoline", true, new String[]{"Enable the trampoline?"});
        CONFIG.addDefault("trampoline-id", 19, new String[]{"Whats your trampoline block id? [Sponge]"});
        CONFIG.addDefault("trampoline-height", 10, new String[]{"Specify the trampoline jump height"});
        CONFIG.addDefault("disable-falldamage", true, new String[]{"Disable fall -damage?"});
        CONFIG.addDefault("disable-alldamage", true, new String[]{"Disable all damage?"});
        CONFIG.addDefault("disable-hunger", true, new String[]{"Disable hunger?"});
        CONFIG.addDefault("enable-teleport-height", false, new String[]{"Enable the height you want to be teleportet to a location", "Example.. Teleport to the spawn"});
        CONFIG.addDefault("teleport-height", 0, new String[]{"Specify the teleport height"});
        CONFIG.addDefault("teleport-height-location", "world,0,0,0,0,0", new String[]{"The location you want to be teleported", "Use this command.. /lobbytools settploc"});
        CONFIG.addDefault("clearinv", true, new String[]{"Clear the inventory on join?"});
        CONFIG.addDefault("hotbar-items-on-death", true, new String[]{"Give the items below after death?"});
        CONFIG.addDefault("hotbar.slot1.item", "id@345 name@&a&lChoose_a_server amount@1 lore@Play_on_me! lore@Just_click_on_me. enchant@PROTECTION_ENVIRONMENTAL@2", new String[]{"Example items.. www.goo.gl/k1XsaF"});
        CONFIG.addDefault("hotbar.slot1.execute-command", false);
        CONFIG.addDefault("hotbar.slot1.command", "");
        CONFIG.addDefault("hotbar.slot1.use", false);
        CONFIG.addDefault("hotbar.slot2.item", "id@347");
        CONFIG.addDefault("hotbar.slot2.execute-command", true);
        CONFIG.addDefault("hotbar.slot2.command", "/say Hello!");
        CONFIG.addDefault("hotbar.slot2.use", false);
        CONFIG.addDefault("hotbar.slot3.item", "id@0");
        CONFIG.addDefault("hotbar.slot3.execute-command", false);
        CONFIG.addDefault("hotbar.slot3.command", "");
        CONFIG.addDefault("hotbar.slot3.use", false);
        CONFIG.addDefault("hotbar.slot4.item", "id@0");
        CONFIG.addDefault("hotbar.slot4.execute-command", false);
        CONFIG.addDefault("hotbar.slot4.command", "");
        CONFIG.addDefault("hotbar.slot4.use", false);
        CONFIG.addDefault("hotbar.slot5.item", "id@0");
        CONFIG.addDefault("hotbar.slot5.execute-command", false);
        CONFIG.addDefault("hotbar.slot5.command", "");
        CONFIG.addDefault("hotbar.slot5.use", false);
        CONFIG.addDefault("hotbar.slot6.item", "id@0");
        CONFIG.addDefault("hotbar.slot6.execute-command", false);
        CONFIG.addDefault("hotbar.slot6.command", "");
        CONFIG.addDefault("hotbar.slot6.use", false);
        CONFIG.addDefault("hotbar.slot7.item", "id@0");
        CONFIG.addDefault("hotbar.slot7.execute-command", false);
        CONFIG.addDefault("hotbar.slot7.command", "");
        CONFIG.addDefault("hotbar.slot7.use", false);
        CONFIG.addDefault("hotbar.slot8.item", "id@0");
        CONFIG.addDefault("hotbar.slot8.execute-command", false);
        CONFIG.addDefault("hotbar.slot8.command", "");
        CONFIG.addDefault("hotbar.slot8.use", false);
        CONFIG.addDefault("hotbar.slot9.item", "id@0");
        CONFIG.addDefault("hotbar.slot9.execute-command", false);
        CONFIG.addDefault("hotbar.slot9.command", "");
        CONFIG.addDefault("hotbar.slot9.use", false);
        CONFIG.saveConfig();
        getLogger().info("Config initialized");

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getCommand("lobbytools").setExecutor(new LobbyToolsCommand());

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException ignored) {
        }
    }
}
