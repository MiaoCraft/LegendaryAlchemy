package com.gyzer.lianyao.legendaryalchemy.Events;

import com.gyzer.lianyao.legendaryalchemy.Utils.HoloUtils;
import com.gyzer.lianyao.legendaryalchemy.Utils.MsgUtils;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import sun.nio.cs.MS1250;

import java.util.HashMap;
import java.util.List;

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
