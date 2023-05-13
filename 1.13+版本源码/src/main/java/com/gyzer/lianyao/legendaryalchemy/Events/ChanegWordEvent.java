package com.gyzer.lianyao.legendaryalchemy.Events;

import com.gyzer.lianyao.legendaryalchemy.Utils.ReadMessage;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class ChanegWordEvent implements Listener {
    @EventHandler
    public void cw(PlayerChangedWorldEvent e)
    {
        Player p=e.getPlayer();
        if (AlchemyBlock.blockLoc.containsKey(p))
        {
            AlchemyBlock.removePlayerPot(p,"切换世界");

            p.sendMessage(ReadMessage.PluginTitle+ ReadMessage.removePot_far);
        }
    }
}
