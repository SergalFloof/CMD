 package com.cmd.common;
 
 import java.io.File;
 import java.util.ArrayList;
 
 public class CMDCommonProxy
 {
   public void preInit()
   {
     File f = new File("CMD files");
     ArrayList<String> fichiers = new ArrayList<String>();
     File[] listFiles = f.listFiles();
     if (listFiles != null)
     {
       for (int i = 0; i < listFiles.length; i++)
       {
         if (listFiles[i].getName().endsWith(".txt"))
         {
           fichiers.add(listFiles[i].getName().substring(0, listFiles[i].getName().length() - 4));
         }
       }
     }
     File f2 = new File("mods/CMD files");
     ArrayList<String> fichiersMods = new ArrayList<String>();
     File[] listFiles2 = f2.listFiles();
     if (listFiles2 != null)
     {
       for (int i = 0; i < listFiles2.length; i++)
       {
         if (listFiles2[i].getName().endsWith(".txt"))
         {
           fichiersMods.add(listFiles2[i].getName().substring(0, listFiles2[i].getName().length() - 4));
         }
       }
     }
     
     for (int i = 0; i < fichiers.size(); i++)
     {
       CMD.discs.add(new ItemCustomRecord((String)fichiers.get(i)));
       CMD.isDiscInMods.add(Boolean.valueOf(false));
     }
     for (int i = 0; i < fichiersMods.size(); i++)
     {
       CMD.discs.add(new ItemCustomRecord((String)fichiersMods.get(i)));
       CMD.isDiscInMods.add(Boolean.valueOf(true));
     }
   }
   
   public void init() {}
 }
