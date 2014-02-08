package org.iq.util.file;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.iq.exception.UtilityException;
import org.iq.util.string.StringUtil;

/**
 * @author Sam
 * 
 */
public class FileUtil {
  
  /**
   * @param fileName
   * @return boolean
   */
  public static boolean isFileExists(String fileName) {
    return new File(fileName).exists();
  }

  /**
   * @param fileName
   * @return boolean
   */
  public static boolean isFilenameValid(String fileName) {
    if (fileName != null) {
      File f = new File(fileName);
      try {
        f.getCanonicalPath();
        return true;
      }
      catch (IOException e) {
        System.out.println("Not a valid filename.");
        return false;
      }
    }
    return false;
  }

  /**
   * @param folderPath
   */
  public static void cleanFolder(String folderPath) {
    File folder = new File(folderPath);
    if (folder.isDirectory()) {
      File[] files = folder.listFiles();
      if (files != null && files.length > 0) {
        for (File file : files) {
          if (file.isDirectory()) {
            cleanFolder(file.getAbsolutePath());
          }
          boolean stat = file.delete();
//        System.out.println(file.getName() + " deleted.");
          if (!stat) {
            System.out.println("Cannot delete " + file.getName());
          }
        }
      }
    }
  }

  /**
   * @param fileName
   * @param content
   * @throws IOException
   */
  public static void createFile(String fileName, String content)
  throws IOException {
    createFile(fileName, content, false);
  }

  /**
   * @param fileName
   * @param content
   * @throws IOException
   */
  public static void createFile(String fileName, String content, boolean overwrite)
  throws IOException {
    if (fileName != null && isFilenameValid(fileName)&& content != null) {
      File file = new File(fileName);
      if (overwrite) {
        file.delete();
      }
      boolean exist = file.createNewFile();
      if (!exist) {
        System.out.println("File already exists.");
        return;
      }
      BufferedWriter out = new BufferedWriter(new FileWriter(file));
      out.write(content);
      out.close();
    }
  }

  /**
   * @param string
   */
  public static void createFolder(String folderName) {
    if (folderName != null && isFilenameValid(folderName)) {
      new File(folderName).mkdirs();
    }
  }

  /**
   * @param projFolder
   * @param fileFilter
   * @return
   */
  public static List<File> listAllFiles(File projFolder, FileFilter fileFilter) {
    List<File> result = new ArrayList<File>();
    File[] filesAndDirs = projFolder.listFiles(fileFilter);
    if (filesAndDirs!=null && filesAndDirs.length>0) {
      for (File file : filesAndDirs) {
        result.add(file); //always add, even if directory
        if (file.isDirectory()) {
          List<File> deeperList = listAllFiles(file,fileFilter);
          result.addAll(deeperList);
        }
      }
    }
    else{
//      System.out.println("No file found under "+projFolder.getAbsolutePath());
    }
    return result;
  }

  /**
   * @param projFolder
   * @param fileFilter
   * @return
   */
  public static List<File> listAllFiles(File projFolder, FileFilter fileFilter, boolean excludeDirs) {
    List<File> result = new ArrayList<File>();
    File[] filesAndDirs = projFolder.listFiles(fileFilter);
    if (filesAndDirs!=null && filesAndDirs.length>0) {
      for (File file : filesAndDirs) {
        if (file.isDirectory()) {
          if (!excludeDirs) {
            result.add(file);
          }
          List<File> deeperList = listAllFiles(file,fileFilter);
          result.addAll(deeperList);
        }
        else{
          result.add(file);
        }
      }
    }
    else{
//      System.out.println("No file found under "+projFolder.getAbsolutePath());
    }
    return result;
  }

  /**
   * @param projFolder
   * @return
   */
  public static List<File> listAllFiles(File projFolder) {
    List<File> result = new ArrayList<File>();
    File[] filesAndDirs = projFolder.listFiles();
    if (filesAndDirs!=null && filesAndDirs.length>0) {
      for (File file : filesAndDirs) {
        result.add(file); //always add, even if directory
        if (file.isDirectory()) {
          List<File> deeperList = listAllFiles(file);
          result.addAll(deeperList);
        }
      }
    }
    else{
//      System.out.println("No file found under "+projFolder.getAbsolutePath());
    }
    return result;
  }
  /**
   * @param name
   * @return
   */
  public static String getShortFilename(String name) {
    return name.contains(".")? name.split("\\.")[0]:null;
  }

  /**
   * @param inputFile
   * @return String
   * @throws UtilityException
   */
  public static String getFileContent(File inputFile) throws UtilityException {
    StringBuffer stringBuffer = new StringBuffer();
    try{
      FileInputStream fstream = new FileInputStream(inputFile);
      DataInputStream in = new DataInputStream(fstream);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      if (br != null) {
        String strLine;
        while ((strLine = br.readLine()) != null) {
          stringBuffer.append(strLine);
          stringBuffer.append(StringUtil.lineSeparator);
        }
      }
      in.close();
    }
    catch (IOException e) {
      throw new UtilityException("Error reading file content as string", e);
    }
    return stringBuffer.toString();
  }

  /**
   * @param zipFileName
   * @param overwrite
   * @param contentFileNames
   * @throws IOException
   */
  public static void createZipFile(String zipFileName, boolean overwrite, String contentRoot,
      String... contentFileNames) throws IOException {
    if (isFilenameValid(zipFileName)) {
      if (zipFileName.endsWith(".zip") == false)
        zipFileName = zipFileName + ".zip";
    }

    byte[] buffer = new byte[18024];
    ZipOutputStream out =
        new ZipOutputStream(new FileOutputStream(zipFileName));
    out.setLevel(Deflater.DEFAULT_COMPRESSION);

    for (String contentFileName : contentFileNames) {
      FileInputStream in = new FileInputStream(contentFileName);
      out.putNextEntry(new ZipEntry(contentFileName.substring(contentRoot.length()+1, contentFileName.length())));
      int len;
      while ((len = in.read(buffer)) > 0) {
        out.write(buffer, 0, len);
      }
      out.closeEntry();
      in.close();
    }
    out.close();
  }
  
  public static void createZipFromFolder(String zipFileName, boolean overwrite,
      String contentFolderName) throws IOException {
    List<File> fileList = listAllFiles(new File(contentFolderName));
    String[] contentFileNames = new String[fileList.size()];
    short i = 0;
    for (File file : fileList) {
      contentFileNames[i++] = file.getCanonicalPath();
    }
    createZipFile(zipFileName, overwrite, contentFolderName, contentFileNames);
  }

  protected void createJarArchive(File archiveFile, File[] tobeJared) {/*
    try {
      byte buffer[] = new byte[BUFFER_SIZE];
      // Open archive file
      FileOutputStream stream = new FileOutputStream(archiveFile);
      JarOutputStream out = new JarOutputStream(stream, new Manifest());

      for (int i = 0; i < tobeJared.length; i++) {
        if (tobeJared[i] == null || !tobeJared[i].exists()
            || tobeJared[i].isDirectory())
          continue; // Just in case...
        System.out.println("Adding " + tobeJared[i].getName());

        // Add archive entry
        JarEntry jarAdd = new JarEntry(tobeJared[i].getName());
        jarAdd.setTime(tobeJared[i].lastModified());
        out.putNextEntry(jarAdd);

        // Write file to archive
        FileInputStream in = new FileInputStream(tobeJared[i]);
        while (true) {
          int nRead = in.read(buffer, 0, buffer.length);
          if (nRead <= 0)
            break;
          out.write(buffer, 0, nRead);
        }
        in.close();
      }

      out.close();
      stream.close();
      System.out.println("Adding completed OK");
    } catch (Exception ex) {
      ex.printStackTrace();
      System.out.println("Error: " + ex.getMessage());
    }
  */}
}