 package com.cmd.common;
 
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Set;
 import net.minecraft.item.Item;
 import net.minecraft.item.crafting.CraftingManager;
 import net.minecraft.util.ResourceLocation;
 import net.minecraft.util.registry.RegistryNamespaced;
 import net.minecraft.world.storage.loot.LootTableList;
 import net.minecraftforge.common.MinecraftForge;
 import net.minecraftforge.common.config.Configuration;
 import net.minecraftforge.common.config.Property;
 import net.minecraftforge.fml.common.Mod;
 import net.minecraftforge.fml.common.Mod.EventHandler;
 import net.minecraftforge.fml.common.event.FMLInitializationEvent;
 import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
 import net.minecraftforge.fml.common.eventhandler.EventBus;
 import net.minecraftforge.fml.common.network.NetworkRegistry;
 import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
 import net.minecraftforge.fml.relauncher.Side;
 import net.minecraftforge.oredict.RecipeSorter;
 import net.minecraftforge.oredict.RecipeSorter.Category;
 
 @Mod(modid="cmd", version="1.10.0", acceptedMinecraftVersions="[1.12.2]")
 public class CMD
 {
   @net.minecraftforge.fml.common.SidedProxy(clientSide="com.cmd.client.CMDClientProxy", serverSide="com.cmd.common.CMDCommonProxy")
   public static CMDCommonProxy proxy;
   public static final String MODID = "cmd";
   public static final String VERSION = "1.10.0";
   public static SimpleNetworkWrapper packetChannel;
   public static final List<Item> discs = new ArrayList();
   public static final List<Boolean> isDiscInMods = new ArrayList();
   public static final List<List<String>> allLines = new ArrayList();
   public static boolean creepers_drop_discs_when_shot_by_skeletons;
   public static boolean enable_discs_crafting;
   public static boolean enable_discs_dropping_by_mobs;
   public static boolean enable_discs_generation_in_dungeons;
   public static boolean update_discs_when_launching_Minecraft;
   public static boolean disc_name_is_song_name;
   
   public static class ItemTextureInfo {
     public Item item;
     public String textureName;
     
     public ItemTextureInfo(Item i, String t) {
       this.item = i;
       this.textureName = t;
     }
   }
   
  public static List<ItemTextureInfo> itemTextures = new ArrayList();
   
   @Mod.EventHandler
   public void preInit(FMLPreInitializationEvent event)
   {
     Configuration config = new Configuration(new File(event.getModConfigurationDirectory(), "Custom Music Discs.cfg"));
     config.load();
     creepers_drop_discs_when_shot_by_skeletons = config.get("Mod options", "creepers_drop_discs_when_shot_by_skeletons", true).getBoolean(true);
     enable_discs_crafting = config.get("Mod options", "enable_discs_crafting", true).getBoolean(true);
     enable_discs_dropping_by_mobs = config.get("Mod options", "enable_discs_dropping_by_mobs", true).getBoolean(true);
     enable_discs_generation_in_dungeons = config.get("Mod options", "enable_discs_generation_in_dungeons", true).getBoolean(true);
     update_discs_when_launching_Minecraft = config.get("Mod options", "update_discs_when_launching_Minecraft", true).getBoolean(true);
     disc_name_is_song_name = config.get("Mod options", "disc_name_is_song_name", false).getBoolean(false);
     config.save();
     
     proxy.preInit();
     
     packetChannel = NetworkRegistry.INSTANCE.newSimpleChannel("CMD_Channel");
     packetChannel.registerMessage(PacketRecordCraft.Handler.class, PacketRecordCraft.class, 0, Side.CLIENT);
   }

   @Mod.EventHandler
   public void init(FMLInitializationEvent event)
   {
     MinecraftForge.EVENT_BUS.register(new EntityLivingHandler());
     MinecraftForge.EVENT_BUS.register(new EntityHandler());
     MinecraftForge.EVENT_BUS.register(new FMLPlayerEventHandler());
     MinecraftForge.EVENT_BUS.register(new LootTableEventHandler());
     
//     CraftingManager.func_77594_a().func_77592_b().add(new RecipeDiscs());
     RecipeSorter.register("RecipeDiscs", RecipeDiscs.class, RecipeSorter.Category.SHAPED, "");
     
     proxy.init();
     
     for (int i = 0; i < discs.size(); i++)
     {
       List<String> allLinesNew = new ArrayList();
       allLines.add(allLinesNew);
       try
       {
        String s = ((Boolean)isDiscInMods.get(i)).booleanValue() ? "mods/" : "";
         InputStream ips = new FileInputStream(s + "CMD files/" + ((ItemCustomRecord)discs.get(i)).displayName + ".txt");
         
         BufferedReader br = new BufferedReader(new InputStreamReader(ips));
         
         RecordCraft.CraftItem[][] craft = new RecordCraft.CraftItem[3][3];
         for (int j = 0; j < 3; j++) {
           for (int k = 0; k < 3; k++)
             craft[j][k] = null;
         }
         int j = 0;int k = 0;
         boolean inCraft = false;
         String line; while ((line = br.readLine()) != null)
         {
           ((List)allLines.get(i)).add(line);
           if ((!inCraft) && (line.equals("craft_start")))
           {
             inCraft = true;
             j = 0;
             k = 0;
 
           }
           else if ((inCraft) && (line.equals("craft_end")))
           {
             if (enable_discs_crafting)
               RecipeDiscs.addCraft(craft, (Item)discs.get(i), false);
             inCraft = false;
             for (int j2 = 0; j2 < 3; j2++) {
               for (int k2 = 0; k2 < 3; k2++) {
                 craft[j2][k2] = null;
               }
             }
           } else if ((inCraft) && (k < 3))
           {
             if (!line.isEmpty())
             {
               int firstColon = line.indexOf(':');
               if ((firstColon == -1) || ((firstColon < line.length() - 1) && (line.charAt(firstColon + 1) >= '0') && (line.charAt(firstColon + 1) <= '9')))
               {
                 line = "minecraft:" + line; }
             }
             Object obj = Item.REGISTRY.containsKey(new ResourceLocation(line));
             if (obj != null)
             {
               craft[j][k] = new RecordCraft.CraftItem((Item)obj);
             }
             else if (line.contains(":"))
             {
               try
               {
                int colonIndex = line.lastIndexOf(':');
                 int metadata = Integer.parseInt(line.substring(colonIndex + 1));
                 obj = Item.REGISTRY.containsKey(new ResourceLocation(line.substring(0, colonIndex)));
                 if (obj != null) {
                   craft[j][k] = new RecordCraft.CraftItem((Item)obj, metadata);
                 }
               } catch (Exception e) {}
             }
             j++;
            if (j == 3)
             {
               j = 0;
               k++;
             }
           }
         }
         br.close();
         
        for (int l = 0; l < ((List)allLines.get(i)).size(); l++)
         {
           String currentLine = (String)((List)allLines.get(i)).get(l);
           if ((enable_discs_dropping_by_mobs) && (currentLine.startsWith("drops:")))
           {
             try
             {
               int colonIndex = currentLine.indexOf(':', 6);
               float chance = Float.parseFloat(currentLine.substring(colonIndex + 1));
               String creature = currentLine.substring(6, colonIndex);
               ((ItemCustomRecord)discs.get(i)).creatures.add(creature);
               ((ItemCustomRecord)discs.get(i)).chances.add(Float.valueOf(chance));
 
             }
             catch (Exception e) {}
           } else if ((enable_discs_generation_in_dungeons) && (currentLine.startsWith("dungeons:")))
           {
             try
             {
               int colonIndex = currentLine.lastIndexOf(':');
               float chance = Float.parseFloat(currentLine.substring(colonIndex + 1));
               String dungeon = currentLine.substring(9, colonIndex);
               
               ResourceLocation dungeonLoc = null;
               Set<ResourceLocation> existingLootTables = LootTableList.getAll();
               
//               if (dungeon.trim().toLowerCase().equals("dungeonchest")) {
//                 dungeonLoc = LootTableList.field_186422_d;
//               } else if (dungeon.trim().toLowerCase().equals("bonuschest")) {
//                 dungeonLoc = LootTableList.field_186420_b;
//               } else 
	
				if (dungeon.trim().toLowerCase().equals("villageblacksmith")) {
                 dungeonLoc = LootTableList.CHESTS_VILLAGE_BLACKSMITH;
               } else if (dungeon.trim().toLowerCase().equals("mineshaftcorridor")) {
                 dungeonLoc = LootTableList.CHESTS_ABANDONED_MINESHAFT;
               } else if (dungeon.trim().toLowerCase().equals("pyramiddesertychest")) {
                 dungeonLoc = LootTableList.CHESTS_DESERT_PYRAMID;
               } else if (dungeon.trim().toLowerCase().equals("pyramidjunglechest")) {
                 dungeonLoc = LootTableList.CHESTS_JUNGLE_TEMPLE;
               } else if (dungeon.trim().toLowerCase().equals("strongholdcorridor")) {
                 dungeonLoc = LootTableList.CHESTS_STRONGHOLD_CORRIDOR;
               } else if (dungeon.trim().toLowerCase().equals("strongholdlibrary")) {
                 dungeonLoc = LootTableList.CHESTS_STRONGHOLD_LIBRARY;
               } else if (dungeon.trim().toLowerCase().equals("strongholdcrossing")) {
                dungeonLoc = LootTableList.CHESTS_STRONGHOLD_CROSSING;
               }
               else {
                Iterator<ResourceLocation> it = existingLootTables.iterator();
                 while (it.hasNext())
                 {
                  ResourceLocation loc = (ResourceLocation)it.next();
                   if (loc.equals(new ResourceLocation("chests/" + dungeon)))
                   {
                     dungeonLoc = loc;
                     break;
                   }
                 }
               }
               
               if (dungeonLoc == null) {
                System.err.println("Custom Music Discs error: loot table \"" + dungeon + "\" doesn't exist!");
               } else {
                 LootTableEventHandler.addDisc(dungeonLoc, (Item)discs.get(i), chance);
               }
             }
             catch (Exception e) {}
           }
         }
       }
       catch (Exception e) {}
     }
   }
 }
