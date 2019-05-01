 package com.cmd.common;
 
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import net.minecraft.item.Item;
 import net.minecraft.util.ResourceLocation;
 import net.minecraft.world.storage.loot.LootEntryItem;
 import net.minecraft.world.storage.loot.LootPool;
 import net.minecraft.world.storage.loot.LootTable;
 import net.minecraft.world.storage.loot.RandomValueRange;
 import net.minecraft.world.storage.loot.conditions.LootCondition;
 import net.minecraft.world.storage.loot.conditions.RandomChance;
 import net.minecraft.world.storage.loot.functions.LootFunction;
 import net.minecraftforge.event.LootTableLoadEvent;
 import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 
 public class LootTableEventHandler
 {
   private static int poolID = 0;
   
   private static class ChestEntry
   {
     public final Item item;
     public final float chance;
     
     public ChestEntry(Item i, float c) {
       this.item = i;
       this.chance = c;
     } }
   
   private static Map<ResourceLocation, List<ChestEntry>> dungeonLoot = new HashMap();
   
   public static void addDisc(ResourceLocation dungeon, Item item, float chance)
   {
     List<ChestEntry> list = (List)dungeonLoot.get(dungeon);
    if (list == null)
     {
       list = new ArrayList();
       dungeonLoot.put(dungeon, list);
     }
     list.add(new ChestEntry(item, chance));
   }
   
   @SubscribeEvent
   public void onLootTableLoad(LootTableLoadEvent event)
   {
     List<ChestEntry> entries = (List)dungeonLoot.get(event.getName());
     if (entries != null) {
       for (ChestEntry entry : entries)
       {
         event.getTable().addPool(new LootPool(new LootEntryItem[] { new LootEntryItem(entry.item, 1, 1, new LootFunction[0], new LootCondition[0], "") }, new LootCondition[] { new RandomChance(entry.chance / 100.0F) }, new RandomValueRange(1.0F, 1.0F), new RandomValueRange(0.0F, 0.0F), "CMD_pool_" + poolID++));
       }
     }
   }
 }