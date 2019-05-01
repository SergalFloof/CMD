 package com.cmd.common;
 
 import java.util.ArrayList;
 import net.minecraft.entity.EntityLivingBase;
 import net.minecraftforge.event.entity.living.LivingDeathEvent;
 
 public class EntityLivingHandler
 {
   @net.minecraftforge.fml.common.eventhandler.SubscribeEvent
   public void onEntityLivingDeath(LivingDeathEvent event)
   {
     if (event.getEntityLiving().world.isRemote)
       return;
     if ((event.getEntityLiving() != null) && (net.minecraft.entity.EntityList.getEntityString(event.getEntityLiving()) != null)) {
       for (int i = 0; i < CMD.discs.size(); i++)
       {
         float totalChance = 0.0F;
         ItemCustomRecord currentItem = (ItemCustomRecord)CMD.discs.get(i);
         for (int j = 0; j < currentItem.creatures.size(); j++)
         {
           if (((currentItem.creatures.get(j) != null) && (net.minecraft.entity.EntityList.getEntityString(event.getEntityLiving()).equals(currentItem.creatures.get(j)))) || (((String)currentItem.creatures.get(j)).equals("All")))
           {
 
             totalChance += ((Float)currentItem.chances.get(j)).floatValue(); }
         }
         if (event.getEntityLiving().getRNG().nextFloat() * 100.0F < totalChance) {
           event.getEntityLiving().dropItem(currentItem, 1);
         }
       }
     }
   }
 }
