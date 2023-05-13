package com.gyzer.lianyao.legendaryalchemy.Events;

import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {
    @EventHandler
    public void q(PlayerQuitEvent e)
    {
        Player p=e.getPlayer();
        if (AlchemyBlock.blockLoc.containsKey(p))
        {
            AlchemyBlock.removePlayerPot(p,"中途退出");
        }
    }
}
