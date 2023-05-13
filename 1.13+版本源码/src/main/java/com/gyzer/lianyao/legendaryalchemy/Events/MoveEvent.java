package com.gyzer.lianyao.legendaryalchemy.Events;

import com.gyzer.lianyao.legendaryalchemy.Utils.ReadMessage;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyBlock;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {

    @EventHandler
    public void onM(PlayerMoveEvent e)
    {
        Player p=e.getPlayer();
        if (AlchemyBlock.blockLoc.containsKey(p))
        {
            Location loc= AlchemyBlock.blockLoc.get(p);
            if (e.getTo().distance(loc) >= 10)
            {
             AlchemyBlock.removePlayerPot(p,"距离炼药锅大于10格");
             AlchemyBlock.begin.remove(p);
             p.sendMessage(ReadMessage.PluginTitle+ ReadMessage.removePot_far);
            }
        }
    }
}
