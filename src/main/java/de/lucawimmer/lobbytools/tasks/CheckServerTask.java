package de.lucawimmer.lobbytools.tasks;

import com.google.common.collect.Iterables;
import de.lucawimmer.lobbytools.LobbyTools;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CheckServerTask implements Runnable {

    @Override
    public void run() {
        if (Bukkit.getOnlinePlayers().size() == 0) return;
        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("PlayerCount");
            out.writeUTF("ALL"); // Target Server

        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(LobbyTools.getPlugin(), "BungeeCord", b.toByteArray());


        for (String s : LobbyTools.getDefaultConfig().getStringList("check-servers")) {
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            DataOutputStream outs = new DataOutputStream(bs);
            try {
                outs.writeUTF("PlayerCount");
                outs.writeUTF(s);

            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendPluginMessage(LobbyTools.getPlugin(), "BungeeCord", bs.toByteArray());
        }

    }
}
