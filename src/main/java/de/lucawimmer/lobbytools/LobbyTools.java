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
        String[] header = {"LobbyTools", "Developed and written by", "Luca Wimmer", "skylands.eu:25565"};
        CONFIG = manager.getNewConfig("config.yml", header);
        CONFIG.addDefault("lobbytools.use-permissions", false);
        CONFIG.addDefault("lobbytools.use-per-world-permissions", false);
        CONFIG.addDefault("lobbytools.use-exact-spawn", false);
        CONFIG.addDefault("lobbytools.exact-spawn-loc", "world,0,0,0,0,0");
        CONFIG.addDefault("lobbytools.disable-inventory-move", true);
        CONFIG.addDefault("lobbytools.disable-item-drop", true);
        CONFIG.addDefault("lobbytools.disable-item-pickup", true);
        CONFIG.addDefault("lobbytools.disable-build", true);
        CONFIG.addDefault("lobbytools.disable-death-messages", true);
        CONFIG.addDefault("lobbytools.disable-chat", false);
        CONFIG.addDefault("lobbytools.enable-launchpad", true);
        CONFIG.addDefault("lobbytools.launchpad-id", 152);
        CONFIG.addDefault("lobbytools.launchpad-speed", true);
        CONFIG.addDefault("lobbytools.enable-trampoline", true);
        CONFIG.addDefault("lobbytools.trampoline-id", 3);
        CONFIG.addDefault("lobbytools.trampoline-height", 15);
        CONFIG.addDefault("lobbytools.disable-falldamage", true);
        CONFIG.addDefault("lobbytools.disable-alldamage", true);
        CONFIG.addDefault("lobbytools.disable-hunger", true);
        CONFIG.addDefault("lobbytools.clearinv", true);
        CONFIG.addDefault("lobbytools.hotbar-items-on-death", true);
        CONFIG.addDefault("lobbytools.hotbar.slot1.use", false);
        CONFIG.addDefault("lobbytools.hotbar.slot1.id", 345);
        CONFIG.addDefault("lobbytools.hotbar.slot1.name", "&a&l&nServer Auswahl");
        CONFIG.addDefault("lobbytools.hotbar.slot1.use", false);
        CONFIG.addDefault("lobbytools.hotbar.slot2.id", 0);
        CONFIG.addDefault("lobbytools.hotbar.slot2.name", "");
        CONFIG.addDefault("lobbytools.hotbar.slot2.use", false);
        CONFIG.addDefault("lobbytools.hotbar.slot3.id", 0);
        CONFIG.addDefault("lobbytools.hotbar.slot3.name", "");
        CONFIG.addDefault("lobbytools.hotbar.slot3.use", false);
        CONFIG.addDefault("lobbytools.hotbar.slot4.id", 0);
        CONFIG.addDefault("lobbytools.hotbar.slot4.name", "");
        CONFIG.addDefault("lobbytools.hotbar.slot4.use", false);
        CONFIG.addDefault("lobbytools.hotbar.slot5.id", 0);
        CONFIG.addDefault("lobbytools.hotbar.slot5.name", "");
        CONFIG.addDefault("lobbytools.hotbar.slot5.use", false);
        CONFIG.addDefault("lobbytools.hotbar.slot6.id", 0);
        CONFIG.addDefault("lobbytools.hotbar.slot6.name", "");
        CONFIG.addDefault("lobbytools.hotbar.slot6.use", false);
        CONFIG.addDefault("lobbytools.hotbar.slot7.id", 0);
        CONFIG.addDefault("lobbytools.hotbar.slot7.name", "");
        CONFIG.addDefault("lobbytools.hotbar.slot7.use", false);
        CONFIG.addDefault("lobbytools.hotbar.slot8.id", 0);
        CONFIG.addDefault("lobbytools.hotbar.slot8.name", "");
        CONFIG.addDefault("lobbytools.hotbar.slot8.use", false);
        CONFIG.addDefault("lobbytools.hotbar.slot9.id", 0);
        CONFIG.addDefault("lobbytools.hotbar.slot9.name", "");
        CONFIG.addDefault("lobbytools.hotbar.slot9.use", false);
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
