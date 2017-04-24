package de.lucawimmer.lobbytools.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import de.lucawimmer.skyplayers.api.PlayerObjectAPI;
import de.lucawimmer.skyplayers.api.ScoreboardAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;


public class BungeeCordListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        try {
            ByteArrayDataInput in = ByteStreams.newDataInput(message);
            String subchannel = in.readUTF();
            if (subchannel.equals("PlayerCount")) {
                String server = in.readUTF(); // Name of server, as given in the arguments
                if (server.equals("ALL")) ScoreboardAPI.addGlobalVar("bungeeonlineall", Integer.toString(in.readInt()));
                else ScoreboardAPI.addGlobalVar("bungeeonline" + server, Integer.toString(in.readInt()));

                for (Player p : Bukkit.getOnlinePlayers()) PlayerObjectAPI.getPlayer(p).getPlayerboard().update();

            }
        } catch (Exception ignored) {
        }

    }

}
