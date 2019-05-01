package com.cmd.common;
 
import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;


public class RecipeDiscs
  implements IRecipe
{
   public static List<RecordCraft> singleplayerRecipes = new ArrayList();
   public static List<RecordCraft> serverRecipes = new ArrayList();
   public static boolean allowSingleplayerRecipes = true;
  
  private ItemStack outputStack;
  

  public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World)
  {
     int x1 = 3;int x2 = -1;int y1 = 3;int y2 = -1;
     for (int i = 0; i < par1InventoryCrafting.getWidth(); i++)
       for (int j = 0; j < par1InventoryCrafting.getHeight(); j++)
        if (par1InventoryCrafting.getStackInRowAndColumn(i, j) != null)
        {
           if (i < x1)
             x1 = i;
           if (i > x2)
            x2 = i;
           if (j < y1)
             y1 = j;
          if (j > y2)
             y2 = j;
        }
     if (x1 == 3)
       return false;
    int w = x2 - x1 + 1;int h = y2 - y1 + 1;
     ItemStack[][] tab = new ItemStack[w][h];
     for (int i = 0; i < w; i++) {
       for (int j = 0; j < h; j++)
         tab[i][j] = par1InventoryCrafting.getStackInRowAndColumn(x1 + i, y1 + j);
    }
    for (int i = 0; i < serverRecipes.size(); i++)
    {
       if (((RecordCraft)serverRecipes.get(i)).matches(w, h, tab))
      {
         this.outputStack = ((RecordCraft)serverRecipes.get(i)).getResult();
         return true;
      }
    }
     if (allowSingleplayerRecipes) {
       for (int i = 0; i < singleplayerRecipes.size(); i++)
      {
         if (((RecordCraft)singleplayerRecipes.get(i)).matches(w, h, tab))
        {
          this.outputStack = ((RecordCraft)singleplayerRecipes.get(i)).getResult();
           return true;
        } }
    }
     return false;
  }
  

  public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting)
  {
     return this.outputStack.copy();
  }
  

  public int func_77570_a()
  {
     return 9;
  }
  

  public ItemStack getRecipeOutput()
  {
     return this.outputStack;
  }
  

  public ItemStack[] func_179532_b(InventoryCrafting inventory)
  {
     ItemStack[] aitemstack = new ItemStack[inventory.getSizeInventory()];
    
     for (int i = 0; i < aitemstack.length; i++) {
       aitemstack[i] = ForgeHooks.getContainerItem(inventory.getStackInSlot(i));
    }
     return aitemstack;
  }
  
  public static void addCraft(RecordCraft.CraftItem[][] craft, Item disc, boolean receivedFromPacket)
  {
     int x1 = 3;int x2 = -1;int y1 = 3;int y2 = -1;
     for (int i = 0; i < 3; i++)
      for (int j = 0; j < 3; j++)
         if (craft[i][j] != null)
        {
           if (i < x1)
            x1 = i;
           if (i > x2)
             x2 = i;
           if (j < y1)
             y1 = j;
           if (j > y2)
             y2 = j;
        }
    if (x1 == 3) {
       return;
    }
     int w = x2 - x1 + 1;int h = y2 - y1 + 1;
     RecordCraft.CraftItem[][] tab = new RecordCraft.CraftItem[w][h];
     for (int i = 0; i < w; i++) {
       for (int j = 0; j < h; j++)
         tab[i][j] = craft[(x1 + i)][(y1 + j)];
    }
     RecordCraft recordCraft = new RecordCraft(w, h, tab, new ItemStack(disc));
     if (receivedFromPacket) {
       serverRecipes.add(recordCraft);
    }
    else {
      try
      {
         Class.forName("net.minecraft.client.Minecraft");
         singleplayerRecipes.add(recordCraft);
      }
      catch (ClassNotFoundException e)
      {
         serverRecipes.add(recordCraft);
      }
    }
  }


@Override
public IRecipe setRegistryName(ResourceLocation name) {
	// TODO Auto-generated method stub
	return null;
}


@Override
public ResourceLocation getRegistryName() {
	// TODO Auto-generated method stub
	return null;
}


@Override
public Class<IRecipe> getRegistryType() {
	// TODO Auto-generated method stub
	return null;
}


@Override
public boolean canFit(int width, int height) {
	// TODO Auto-generated method stub
	return false;
}
}
