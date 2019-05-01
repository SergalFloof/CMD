 package com.cmd.common;
 
 import java.util.List;
 import java.util.Random;

import net.minecraft.entity.item.EntityItem;
 import net.minecraft.entity.monster.EntityCreeper;
 import net.minecraft.init.Items;
 import net.minecraft.item.Item;
 import net.minecraft.item.ItemStack;
 import net.minecraftforge.event.entity.EntityJoinWorldEvent;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 
 public class EntityHandler
 {
   private static Item getRandomRecord(Random rand)
   {
     int r = rand.nextInt(12 + CMD.discs.size());
     if (r < 12)
     {
       switch (r) {
       case 0:  return Items.RECORD_11;
       case 1:  return Items.RECORD_13;
       case 2:  return Items.RECORD_BLOCKS;
       case 3:  return Items.RECORD_CAT;
       case 4:  return Items.RECORD_FAR;
       case 5:  return Items.RECORD_MALL;
       case 6:  return Items.RECORD_MELLOHI;
       case 7:  return Items.RECORD_STAL;
       case 8:  return Items.RECORD_STRAD;
       case 9:  return Items.RECORD_WAIT;
       case 10:  return Items.RECORD_WARD; }
       return Items.RECORD_CHIRP;
     }
     
     return (Item)CMD.discs.get(r - 12);
   }
   
   private static boolean isVanillaRecord(Item item)
   {
     return (item == Items.RECORD_CHIRP) || (item == Items.RECORD_13) || (item == Items.RECORD_BLOCKS) || (item == Items.RECORD_CAT) || (item == Items.RECORD_FAR) || (item == Items.RECORD_MALL) || (item == Items.RECORD_MELLOHI) || (item == Items.RECORD_STAL) || (item == Items.RECORD_STRAD) || (item == Items.RECORD_WAIT) || (item == Items.RECORD_11) || (item == Items.RECORD_WARD);
   }
   
 
 
 
   @SubscribeEvent
   public void onEntityJoinWorld(EntityJoinWorldEvent event)
   {
     if ((CMD.creepers_drop_discs_when_shot_by_skeletons) && (!event.getWorld().isRemote) && ((event.getEntity() instanceof EntityItem)) && (((EntityItem)event.getEntity()).getItem() != null) && (isVanillaRecord(((EntityItem)event.getEntity()).getItem().getItem())))
     {
 
 
       @SuppressWarnings("rawtypes")
	List list = event.getWorld().getEntitiesWithinAABB(EntityCreeper.class, event.getEntity().getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D));
       
       boolean ok = false;
       for (int i = 0; (i < list.size()) && (!ok); i++)
       {
         if (((EntityCreeper)list.get(i)).getHealth() <= 0.0F)
           ok = true;
       }
       if (ok) {
         ((EntityItem)event.getEntity()).setItem(new ItemStack(getRandomRecord(event.getWorld().rand)));
       }
     }
   }
 }
