 package com.cmd.common;
 
 import io.netty.buffer.ByteBuf;
 import java.util.ArrayList;
 import java.util.List;

import net.minecraft.item.Item;
 import net.minecraft.util.ResourceLocation;
 import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
 import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
 import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
 
 
 
 public class PacketRecordCraft
   implements IMessage
 {
   private List<List<String>> lines;
   private List<String> recordName;
   
   public PacketRecordCraft() {}
   
   public PacketRecordCraft(List<String> r, List<List<String>> l)
   {
     this.recordName = r;
     this.lines = l;
   }
   
 
   public void fromBytes(ByteBuf buf)
   {
     this.lines = new ArrayList();
     this.recordName = new ArrayList();
     if (!buf.readBoolean())
       return;
     int nb = buf.readInt();
     for (int i = 0; i < nb; i++)
     {
       this.recordName.add("");
       int nbChar = buf.readInt();
       for (int j = 0; j < nbChar; j++)
         this.recordName.set(i, (String)this.recordName.get(i) + buf.readChar());
       ArrayList<String> sublines = new ArrayList();
       int nbLignes = buf.readInt();
       for (int j = 0; j < nbLignes; j++)
       {
         sublines.add("");
         nbChar = buf.readInt();
        for (int k = 0; k < nbChar; k++) {
          sublines.set(j, (String)sublines.get(j) + buf.readChar());
         }
       }
       this.lines.add(sublines);
     }
   }
   
 
   public void toBytes(ByteBuf buf)
   {
     try
     {
       buf.writeBoolean(CMD.enable_discs_crafting);
       buf.writeInt(this.recordName.size());
       for (int rec = 0; rec < this.recordName.size(); rec++)
       {
         buf.writeInt(((String)this.recordName.get(rec)).length());
         for (int i = 0; i < ((String)this.recordName.get(rec)).length(); i++)
           buf.writeChar(((String)this.recordName.get(rec)).codePointAt(i));
         buf.writeInt(((List)this.lines.get(rec)).size());
         for (int i = 0; i < ((List)this.lines.get(rec)).size(); i++)
         {
           buf.writeInt(((String)((List)this.lines.get(rec)).get(i)).length());
           for (int j = 0; j < ((String)((List)this.lines.get(rec)).get(i)).length(); j++) {
             buf.writeChar(((String)((List)this.lines.get(rec)).get(i)).codePointAt(j));
           }
         }
       }
     }
     catch (Exception e) {}
   }
   
   public static class Handler implements IMessageHandler<PacketRecordCraft, IMessage>
   {
     public IMessage onMessage(PacketRecordCraft message, MessageContext ctx)
     {
       RecipeDiscs.allowSingleplayerRecipes = false;
       
       for (int i = 0; i < message.recordName.size(); i++)
       {
         ItemCustomRecord disque = null;
         for (int m = 0; m < CMD.discs.size(); m++)
           if (((ItemCustomRecord)CMD.discs.get(m)).displayName.equals(message.recordName.get(i)))
             disque = (ItemCustomRecord)CMD.discs.get(m);
         if (disque != null)
         {
 
           RecordCraft.CraftItem[][] craft = new RecordCraft.CraftItem[3][3];
           for (int j = 0; j < 3; j++) {
             for (int k = 0; k < 3; k++)
             {
               craft[j][k] = null; }
           }
           int j = 0;int k = 0;
           boolean inCraft = false;
           for (int m = 0; m < ((List)message.lines.get(i)).size(); m++)
           {
             String line = (String)((List)message.lines.get(i)).get(m);
             if ((!inCraft) && (line.equals("craft_start")))
             {
               inCraft = true;
               j = 0;
               k = 0;
 
             }
             else if ((inCraft) && (line.equals("craft_end")))
             {
               RecipeDiscs.addCraft(craft, disque, true);
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
               Object obj = Item.REGISTRY.getObject(new ResourceLocation(line));
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
                   obj = Item.REGISTRY.getObject(new ResourceLocation(line.substring(0, colonIndex)));
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
         } }
       return null;
     }
   }
 }
