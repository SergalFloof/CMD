 package com.cmd.common;
 
 import java.util.ArrayList;
 import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.item.ItemRecord;
 import net.minecraft.item.ItemStack;
 import net.minecraft.util.ResourceLocation;
 import net.minecraft.util.SoundEvent;
 import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
 import net.minecraftforge.fml.relauncher.SideOnly;
 
 public class ItemCustomRecord
   extends ItemRecord
 {
   public String displayName;
   public ArrayList<String> creatures = new ArrayList();
   public ArrayList<Float> chances = new ArrayList();
   
   public ItemCustomRecord(String name)
   {
     super("cmd:" + encodeName(name), new SoundEvent(new ResourceLocation("records.cmd:" + encodeName(name))));
     setCreativeTab(CreativeTabs.MISC);
     this.displayName = name;
     setUnlocalizedName(name);
//     GameRegistry.register(setRegistryName("cmd", name));
     CMD.itemTextures.add(new CMD.ItemTextureInfo(this, name));
   }
   
   public static String encodeName(String name)
   {
     String str = "";
     for (int i = 0; i < name.length(); i++)
     {
       if (i != 0)
         str = str + "_";
       str = str + Integer.toString(Character.codePointAt(name, i));
     }
     return str;
   }
   
   public static String hashNameToIcon(String name)
   {
     int somme = 0;
     for (int i = 0; i < name.length(); i++)
       somme += name.charAt(i);
     somme %= 11;
     switch (somme) {
     case 0:  return "items/record_13";
     case 1:  return "items/record_cat";
     case 2:  return "items/record_blocks";
     case 3:  return "items/record_chirp";
     case 4:  return "items/record_far";
     case 5:  return "items/record_mall";
     case 6:  return "items/record_mellohi";
     case 7:  return "items/record_stal";
     case 8:  return "items/record_strad";
     case 9:  return "items/record_ward"; }
     return "items/record_wait";
   }
   
 
   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
   {
     if (!CMD.disc_name_is_song_name) {
       par3List.add(getRecordNameLocal());
     }
   }
   
   @SideOnly(Side.CLIENT)
   public String getRecordNameLocal() {
     return this.displayName;
   }
   
   public String getItemStackDisplayName(ItemStack par1ItemStack)
   {
     if (CMD.disc_name_is_song_name) {
       return this.displayName;
     }
     return I18n.translateToLocal("item.record.name");
   }
 }