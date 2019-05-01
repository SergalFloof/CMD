 package com.cmd.common;
 
 import net.minecraft.item.ItemStack;
 
 public class RecordCraft {
   private int craftWidth;
   private int craftHeight;
   private CraftItem[][] items;
   private ItemStack result;
   
   public static class CraftItem {
     private net.minecraft.item.Item item;
     private boolean metadataNeeded;
     private int metadata;
     
     public CraftItem(net.minecraft.item.Item i) { this.item = i;
       this.metadataNeeded = false;
     }
     
     public CraftItem(net.minecraft.item.Item i, int m)
     {
       this.item = i;
       this.metadataNeeded = true;
       this.metadata = m;
     }
     
     public boolean matches(ItemStack stack)
     {
       if (stack == null) {
         return false;
       }
       if (stack.getItem() != this.item) {
         return false;
       }
       if ((this.metadataNeeded) && (this.metadata != stack.getMetadata())) {
         return false;
       }
       return true;
     }
   }
   
 
 
 
 
   public RecordCraft(int w, int h, CraftItem[][] tab, ItemStack stack)
   {
     this.craftWidth = w;
     this.craftHeight = h;
     this.items = tab;
     this.result = stack;
   }
   
   public ItemStack getResult()
   {
     return this.result;
   }
   
   public boolean matches(int w, int h, ItemStack[][] tab)
   {
     if ((w != this.craftWidth) || (h != this.craftHeight)) {
       return false;
     }
     for (int i = 0; i < w; i++) {
       for (int j = 0; j < h; j++)
         if (((this.items[i][j] == null) && (tab[i][j] != null)) || ((this.items[i][j] != null) && (!this.items[i][j].matches(tab[i][j]))))
         {
           return false; }
     }
     return true;
   }
 }
