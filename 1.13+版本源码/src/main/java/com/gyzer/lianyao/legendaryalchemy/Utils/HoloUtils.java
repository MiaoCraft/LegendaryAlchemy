package com.gyzer.lianyao.legendaryalchemy.Utils;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Holograms.CMIHologram;
import com.gyzer.lianyao.legendaryalchemy.LegendaryAlchemy;
import eu.decentsoftware.holograms.api.DHAPI;
import me.filoghost.holographicdisplays.api.HolographicDisplaysAPI;
import me.filoghost.holographicdisplays.api.Position;
import me.filoghost.holographicdisplays.api.hologram.Hologram;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class HoloUtils {
    public static HashMap<Location,Player> cachePl=new HashMap<>();
    public static HashMap<Location,Hologram> cacheHH=new HashMap<>();
    public static HashMap<Location, CMIHologram> cacheCMI=new HashMap<>();
    public static HashMap<Location, eu.decentsoftware.holograms.api.holograms.Hologram> cacheDHHo=new HashMap<>();
    public static HashMap<Player,Location> cacheLo=new HashMap<>();

    public static void createHolo(String obejcet,String progress, Player player, Location location)
    {
        if (ReadConfig.hologram.equals("HolographicDisplays"))
        {
            showHolo(obejcet,progress,player,location);
        }
        else if (ReadConfig.hologram.equals("DecentHolograms"))
        {
            showHoloD(obejcet,progress,player,location);
        }
        else if  (ReadConfig.hologram.equals("CMI"))
        {
            showHoloCMI(obejcet,progress,player,location);
        }
    }

    public static void showHolo(String obejcet,String progress, Player player, Location location) {
        if (cachePl.remove(location)!=null)
        {
             cacheHH.remove(location).delete();
             cacheLo.remove(player);
        }
        Hologram ho=HolographicDisplaysAPI.get(LegendaryAlchemy.LegendaryAlchemy).createHologram(location);
        ho.getLines().appendText(ReadMessage.holo_head);
        ho.getLines().appendText(ReadMessage.holo_object.replace("%id%",obejcet));
        ho.getLines().appendText(ReadMessage.holo_progress.replace("%progress%",progress));
        ho.getLines().appendText(ReadMessage.holo_click);
        Position po=ho.getPosition().add(0.5,2,0.5);
        ho.setPosition(po);
        cachePl.put(location,player);
        cacheLo.put(player,location);
        cacheHH.put(location,ho);

    }

    public static void showHoloCMI(String obejcet,String progress, Player player, Location location) {
        if (cachePl.remove(location)!=null)
        {
            cacheCMI.remove(location).remove();
            cacheLo.remove(player);
        }



        CMIHologram holo= new CMIHologram("p",location.clone().add(0.5,2.1,0.5));
        ArrayList l=new ArrayList();
        l.add(ReadMessage.holo_head);
        l.add(ReadMessage.holo_object.replace("%id%",obejcet));
        l.add(ReadMessage.holo_progress.replace("%progress%",progress));
        l.add(ReadMessage.holo_click);
        holo.setLines(l);
        CMI.getInstance().getHologramManager().addHologram(holo);
        holo.update();
        cachePl.put(location,player);
        cacheLo.put(player,location);
        cacheCMI.put(location,holo);

    }

    public static void showHoloD(String obejcet,String progress, Player player, Location location)
    {
        if (cachePl.remove(location)!=null)
        {
            cacheDHHo.remove(location).delete();
            cacheLo.remove(player);
        }
        ArrayList l=new ArrayList();
        l.add(ReadMessage.holo_head);
        l.add(ReadMessage.holo_object.replace("%id%",obejcet));
        l.add(ReadMessage.holo_progress.replace("%progress%",progress));
        l.add(ReadMessage.holo_click);
        eu.decentsoftware.holograms.api.holograms.Hologram holo= DHAPI.createHologram(player.getName(),location.clone().add(0.5,2.1,0.5),l);
        cachePl.put(location,player);
        cacheLo.put(player,location);
        cacheDHHo.put(location,holo);

    }
    public static void removeHo(Location loc)
    {
        if (ReadConfig.hologram.equals("HolographicDisplays"))
        {
            cacheHH.remove(loc).delete();
            return;
        }
        else if (ReadConfig.hologram.equals("DecentHolograms"))
        {
            cacheDHHo.remove(loc).delete();
            return;
        }
        else if (ReadConfig.hologram.equals("CMI"))
        {
            cacheCMI.remove(loc).remove();
            return;
        }
    }
}
