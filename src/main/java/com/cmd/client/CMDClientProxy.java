 package com.cmd.client;
 import java.io.BufferedWriter;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.OutputStreamWriter;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.jar.JarEntry;
 import java.util.jar.JarOutputStream;

import com.cmd.common.CMD;
import com.cmd.common.CMDCommonProxy;
import com.cmd.common.ItemCustomRecord;

import net.minecraft.client.Minecraft;
 import net.minecraft.client.renderer.ItemModelMesher;
 import net.minecraft.client.renderer.block.model.ModelResourceLocation;
 
 public class CMDClientProxy extends CMDCommonProxy
 {
   public void preInit()
   {
     try
     {
       List<String> fichiers = new ArrayList();
       
       File f = new File("CMD files");
       File[] listFiles = f.listFiles();
       if (listFiles != null)
       {
         for (int i = 0; i < listFiles.length; i++)
         {
           if (listFiles[i].getName().endsWith(".ogg"))
           {
             fichiers.add(listFiles[i].getName().substring(0, listFiles[i].getName().length() - 4));
             CMD.isDiscInMods.add(Boolean.valueOf(false));
           }
         }
       }
       
       File f2 = new File("mods/CMD files");
       File[] listFiles2 = f2.listFiles();
       if (listFiles2 != null)
       {
         for (int i = 0; i < listFiles2.length; i++)
         {
           if (listFiles2[i].getName().endsWith(".ogg"))
           {
             fichiers.add(listFiles2[i].getName().substring(0, listFiles2[i].getName().length() - 4));
             CMD.isDiscInMods.add(Boolean.valueOf(true));
           }
         }
       }
       
       for (int i = 0; i < fichiers.size(); i++)
       {
         ItemCustomRecord newDisc = new ItemCustomRecord((String)fichiers.get(i));
         CMD.discs.add(newDisc);
       }
       
       if (CMD.update_discs_when_launching_Minecraft)
       {
         JarOutputStream out = new JarOutputStream(new FileOutputStream("mods/CMD files.jar"));
         File json = new File("mods/sounds.json");
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(json), "UTF-8"));
         writer.write(123);
         writer.newLine();
         
         byte[] buf = new byte['1'];
         
         out.putNextEntry(new JarEntry("assets/"));
         out.putNextEntry(new JarEntry("assets/records.cmd/"));
         out.putNextEntry(new JarEntry("assets/records.cmd/sounds/"));
         out.putNextEntry(new JarEntry("assets/cmd/"));
         out.putNextEntry(new JarEntry("assets/cmd/textures/"));
         out.putNextEntry(new JarEntry("assets/cmd/textures/items/"));
         out.putNextEntry(new JarEntry("assets/cmd/models/"));
         out.putNextEntry(new JarEntry("assets/cmd/models/item/"));
         out.putNextEntry(new JarEntry("cmd/"));
         out.putNextEntry(new JarEntry("cmd/common/"));
         
         byte[] buf2 = { -54, -2, -70, -66, 0, 0, 0, 50, 0, 22, 7, 0, 2, 1, 0, 20, 99, 109, 100, 47, 99, 111, 109, 109, 111, 110, 47, 66, 97, 115, 101, 67, 108, 97, 115, 115, 7, 0, 4, 1, 0, 16, 106, 97, 118, 97, 47, 108, 97, 110, 103, 47, 79, 98, 106, 101, 99, 116, 1, 0, 6, 60, 105, 110, 105, 116, 62, 1, 0, 3, 40, 41, 86, 1, 0, 4, 67, 111, 100, 101, 10, 0, 3, 0, 9, 12, 0, 5, 0, 6, 1, 0, 15, 76, 105, 110, 101, 78, 117, 109, 98, 101, 114, 84, 97, 98, 108, 101, 1, 0, 18, 76, 111, 99, 97, 108, 86, 97, 114, 105, 97, 98, 108, 101, 84, 97, 98, 108, 101, 1, 0, 4, 116, 104, 105, 115, 1, 0, 22, 76, 99, 109, 100, 47, 99, 111, 109, 109, 111, 110, 47, 66, 97, 115, 101, 67, 108, 97, 115, 115, 59, 1, 0, 10, 83, 111, 117, 114, 99, 101, 70, 105, 108, 101, 1, 0, 14, 66, 97, 115, 101, 67, 108, 97, 115, 115, 46, 106, 97, 118, 97, 1, 0, 25, 82, 117, 110, 116, 105, 109, 101, 86, 105, 115, 105, 98, 108, 101, 65, 110, 110, 111, 116, 97, 116, 105, 111, 110, 115, 1, 0, 35, 76, 110, 101, 116, 47, 109, 105, 110, 101, 99, 114, 97, 102, 116, 102, 111, 114, 103, 101, 47, 102, 109, 108, 47, 99, 111, 109, 109, 111, 110, 47, 77, 111, 100, 59, 1, 0, 5, 109, 111, 100, 105, 100, 1, 0, 8, 67, 77, 68, 102, 105, 108, 101, 115, 1, 0, 7, 118, 101, 114, 115, 105, 111, 110, 1, 0, 1, 48, 0, 33, 0, 1, 0, 3, 0, 0, 0, 0, 0, 1, 0, 1, 0, 5, 0, 6, 0, 1, 0, 7, 0, 0, 0, 47, 0, 1, 0, 1, 0, 0, 0, 5, 42, -73, 0, 8, -79, 0, 0, 0, 2, 0, 10, 0, 0, 0, 6, 0, 1, 0, 0, 0, 6, 0, 11, 0, 0, 0, 12, 0, 1, 0, 0, 0, 5, 0, 12, 0, 13, 0, 0, 0, 2, 0, 14, 0, 0, 0, 2, 0, 15, 0, 16, 0, 0, 0, 16, 0, 1, 0, 17, 0, 2, 0, 18, 115, 0, 19, 0, 20, 115, 0, 21 };
         
         out.putNextEntry(new JarEntry("cmd/common/BaseClass.class"));
         out.write(buf2, 0, buf2.length);
         
         for (int i = 0; i < fichiers.size(); i++)
         {
           if (i > 0)
           {
             writer.write(44);
             writer.newLine();
           }
           writer.write("\"" + ItemCustomRecord.encodeName((String)fichiers.get(i)) + "\": {\"category\": \"record\",\"sounds\": [{\"name\": \"records.cmd:" + ItemCustomRecord.encodeName((String)fichiers.get(i)) + "\",\"stream\": true}]}");
           
 
 
 
           out.putNextEntry(new JarEntry("assets/records.cmd/sounds/" + ItemCustomRecord.encodeName((String)fichiers.get(i)) + ".ogg"));
           String s = ((Boolean)CMD.isDiscInMods.get(i)).booleanValue() ? "mods/" : "";
           FileInputStream f1 = new FileInputStream(new File(s + "CMD files/" + (String)fichiers.get(i) + ".ogg"));
           int len; while ((len = f1.read(buf)) > 0)
             out.write(buf, 0, len);
           f1.close();
           
          boolean hasIcon = false;
           if (((Boolean)CMD.isDiscInMods.get(i)).booleanValue())
           {
             for (int j = 0; j < listFiles2.length; j++) {
               if (listFiles2[j].getName().equals((String)fichiers.get(i) + ".png"))
               {
                 hasIcon = true;
                 break;
               }
               
             }
           } else {
             for (int j = 0; j < listFiles.length; j++) {
               if (listFiles[j].getName().equals((String)fichiers.get(i) + ".png"))
               {
                 hasIcon = true;
                 break;
               }
             }
           }
           File itemFile = new File("mods/itemFileTemp.json");
           BufferedWriter writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(itemFile), "UTF-8"));
           
           writer2.write("{\"parent\": \"item/generated\",\"textures\":{\"layer0\": \"");
           if (hasIcon) {
             writer2.write("cmd:items/" + (String)fichiers.get(i));
           } else
             writer2.write(ItemCustomRecord.hashNameToIcon((String)fichiers.get(i)));
           writer2.write("\"}}");
           writer2.close();
           FileInputStream in2 = new FileInputStream(itemFile);
           out.putNextEntry(new JarEntry("assets/cmd/models/item/" + (String)fichiers.get(i) + ".json"));
           while ((len = in2.read(buf)) > 0)
           out.write(buf, 0, len);
           out.closeEntry();
           in2.close();
          itemFile.delete();
           
           if (hasIcon)
           {
             out.putNextEntry(new JarEntry("assets/cmd/textures/items/" + (String)fichiers.get(i) + ".png"));
             FileInputStream f0 = new FileInputStream(new File(s + "CMD files/" + (String)fichiers.get(i) + ".png"));
             while ((len = f0.read(buf)) > 0)
               out.write(buf, 0, len);
             f0.close();
           }
         }
         writer.newLine();
         writer.write(125);
         writer.close();
         
         FileInputStream in2 = new FileInputStream(json);
         out.putNextEntry(new JarEntry("assets/records.cmd/sounds.json"));
         int len;
         while ((len = in2.read(buf)) > 0)
           out.write(buf, 0, len);
         out.closeEntry();
         in2.close();
         out.close();
         json.delete();
       }
     }
     catch (IOException e)
     {
       System.out.println(e.toString());
     }
   }
   
 
   public void init()
   {
     ItemModelMesher itemModelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
     for (int i = 0; i < CMD.itemTextures.size(); i++)
     {
       itemModelMesher.register(((CMD.ItemTextureInfo)CMD.itemTextures.get(i)).item, 0, new ModelResourceLocation("cmd:" + ((CMD.ItemTextureInfo)CMD.itemTextures.get(i)).textureName, "inventory"));
     }
     
     CMD.itemTextures.clear();
   }
 }
