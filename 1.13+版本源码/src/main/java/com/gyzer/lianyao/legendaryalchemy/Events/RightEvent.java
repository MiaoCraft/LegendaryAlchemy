package com.gyzer.lianyao.legendaryalchemy.Events;

import com.gyzer.lianyao.legendaryalchemy.GUI.MainMenu;
import com.gyzer.lianyao.legendaryalchemy.GUI.PutMaterial;
import com.gyzer.lianyao.legendaryalchemy.alchemy.AlchemyBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RightEvent implements Listener {
    @EventHandler
    public void re(PlayerInteractEvent e)
    {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            Player p = e.getPlayer();
            if (!AlchemyBlock.blockLoc.containsKey(p))
            {
                return;
            }
            if (e.getClickedBlock().getType().equals(Material.CAULDRON))
            {
                Location loc= AlchemyBlock.blockLoc.get(p);
                if (e.getClickedBlock().getLocation().equals(loc))
                {
                    e.setCancelled(true);
                    if (AlchemyBlock.begin.containsKey(p))
                    {
                        PutMaterial.open(p, AlchemyBlock.begin.get(p).getHot(p));
                    }
                    else {
                        MainMenu.open(p);

                    }
                }
            }
        }
    }
}
