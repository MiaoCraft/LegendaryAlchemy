package com.gyzer.lianyao.legendaryalchemy.alchemy;

import com.gyzer.lianyao.legendaryalchemy.LegendaryAlchemy;
import com.gyzer.lianyao.legendaryalchemy.Utils.HoloUtils;
import com.gyzer.lianyao.legendaryalchemy.Utils.ReadConfig;
import com.gyzer.lianyao.legendaryalchemy.Utils.ReadMessage;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class AlchemyRun extends BukkitRunnable {

    private static int timeCurrent;
    private static int alltime;
    private static int upTime=20;
    private static int maxTime=5;
    private static int hot;
    private static int maxHot;

    private static Player p;
    private String id;

    public AlchemyRun(Player p,String id)
    {
        this.p=p;
        this.id=id;
        hot=0;
        timeCurrent=0;
        alltime=0;
        maxTime=AlchemyData.maxtime.get(id);
        maxHot=AlchemyData.maxhot.get(id);

    }
    public String getHotBar(Player p,int a)
    {
        String str= ReadConfig.hot_color1;
        StringBuilder per=new StringBuilder(str);
        for (int d=0;d<=maxHot;d++)
        {
            per.append(ReadConfig.hot_arg);
        }

        return ReadConfig.hot_pre +per.insert((2+getHot(p)), ReadConfig.hot_color2);
    }
    public int getHot(Player p)
    {
        return hot;
    }

     public int getAllTime(Player p)
    {
        return alltime;
    }
    public int getTime(Player p)
    {
       return timeCurrent;
    }



    private static int ingoreTime=0;
    private static int shiftTime=0;
    public void setHot(int a)
    {
       hot=a;
    }
    @Override
    public void run() {


        if (!AlchemyBlock.blockLoc.containsKey(p))
        {
            hot=0;
            alltime=0;
            timeCurrent=0;
            ingoreTime=0;
            canUpHot=true;
            stop();
            return;

        }
            if (alltime==maxTime)
            {
                AlchemyBlock.begin.remove(p);
                hot=0;
               alltime=0;
               timeCurrent=0;
               canUpHot=true;
               stop();
               p.sendMessage(ReadMessage.PluginTitle+ ReadMessage.begin_overtime);
                for (ItemStack i:AlchemyBlock.putListItem.remove(p))
                {
                    p.getInventory().addItem(i);
                }
               Bukkit.getScheduler().runTask(LegendaryAlchemy.LegendaryAlchemy,() -> HoloUtils.createHolo("无","0%",p, HoloUtils.cacheLo.get(p)));
                return;
            }


            p.spigot().sendMessage(ChatMessageType.ACTION_BAR,new TextComponent(getHotBar(p,getHot(p))));
            if (p.isSneaking())
            {

                shiftTime++;
            }
            else {
                shiftTime=0;
                timeCurrent++;
            }
            if (shiftTime==20)
            {
                shiftTime=0;
                if (hot > 0) {
                    hot -= 1;
                }
            }
            else {
                if (timeCurrent==upTime)
                {
                    if (hot<= maxHot)
                    {
                       if (canUpHot) {
                           hot += 1;
                       }
                    }
                    timeCurrent=0;
                }
            }
            if (ingoreTime==20)
            {
                p.sendTitle("", ReadMessage.begin_time.replace("%time%",(maxTime-alltime)+""));
                ingoreTime=0;
                alltime++;
                Location loc= AlchemyBlock.blockLoc.get(p).clone();
                loc.add(0.5,0.5,0.5);
                for (double i = 0; i < 180; i += 180 / 6) {
                    // 依然要做角度与弧度的转换
                    double radians = Math.toRadians(i);
                    // 计算出来的半径
                    double radius = Math.sin(radians);
                    double y = Math.cos(radians);
                    for (double j = 0; j < 360; j += 180 / 6) {
                        // 依然需要做角度转弧度的操作
                        double radiansCircle = Math.toRadians(j);
                        double x = Math.cos(radiansCircle) * radius;
                        double z = Math.sin(radiansCircle) * radius;
                        loc.add(x, y, z);
                        loc.getWorld().spawnParticle( Particle.DRIP_LAVA,loc,1);
                        loc.subtract(x, y, z);
                    }
                }
            }
            ingoreTime++;
    }
    public void stop()
    {
        this.cancel();
    }
    public String getId()
    {
        return id;
    }


    public void setCanUpHot(boolean set)
    {
        canUpHot=set;
    }

    private static boolean canUpHot=true;
    public void begin()
    {

        this.runTaskTimerAsynchronously(LegendaryAlchemy.LegendaryAlchemy,1,1);
    }

}
