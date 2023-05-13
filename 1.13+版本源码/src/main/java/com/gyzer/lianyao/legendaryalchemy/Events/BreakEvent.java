package com.gyzer.lianyao.legendaryalchemy.Events;

import com.gyzer.lianyao.legendaryalchemy.Utils.ReadMessage;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyBlock;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakEvent implements Listener {
    @EventHandler
    public void onBreakE(BlockBreakEvent e)
    {
        if (e.getBlock().getType().equals(Material.CAULDRON))
        {
            if (AlchemyBlock.locBlock.containsKey(e.getBlock().getLocation()))
            {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ReadMessage.PluginTitle+ ReadMessage.block_break);
            }
        }
    }
}
