package com.gyzer.lianyao.legendaryalchemy.Events;

import com.gyzer.lianyao.legendaryalchemy.Utils.FileUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e)
    {
        FileUtils.createPlayerData(e.getPlayer().getName());
    }
}
