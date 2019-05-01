 package com.cmd.common;
 
 import java.util.ArrayList;
 import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
 import net.minecraftforge.fml.common.eventhandler.EventPriority;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
 
 public class FMLPlayerEventHandler
 {
   @SubscribeEvent(priority=EventPriority.HIGH)
   public void onPlayerLoggedInFirst(PlayerEvent.PlayerLoggedInEvent event)
   {
     try
     {
       Class.forName("net.minecraft.client.Minecraft");
       RecipeDiscs.allowSingleplayerRecipes = true;
       RecipeDiscs.serverRecipes.clear();
     }
     catch (ClassNotFoundException e) {}
   }
   
   @SubscribeEvent(priority=EventPriority.NORMAL)
   public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event)
   {
     try {
       Class.forName("net.minecraft.client.Minecraft");
     }
     catch (ClassNotFoundException e)
     {
       List<String> names = new ArrayList<String>();
       for (int i = 0; i < CMD.discs.size(); i++)
         names.add(((ItemCustomRecord)CMD.discs.get(i)).displayName);
       CMD.packetChannel.sendTo(new PacketRecordCraft(names, CMD.allLines), (EntityPlayerMP)event.player);
     }
   }
 }
