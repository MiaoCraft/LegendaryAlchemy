package com.gyzer.lianyao.legendaryalchemy.API;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AlchemySuccessEvent extends Event implements Cancellable {
    private static boolean cancel;
    private static Player p;
    private static String id;
    private static final HandlerList handlers = new HandlerList();
    public AlchemySuccessEvent(Player p,String paperId)
    {
        this.p=p;
        this.id=paperId;
    }
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel=cancel;
    }

    public Player getPlayer()
    {
        return this.p;
    }

    public String getPaperId()
    {
        return this.id;
    }
}
