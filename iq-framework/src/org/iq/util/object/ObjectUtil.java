package org.iq.util.object;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Sam
 * 
 */
public class ObjectUtil {

  /**
   * @param object
   * @param file
   * @return boolean
   */
  public static boolean store(Object object, File file) {
	FileOutputStream fos = null;
	ObjectOutputStream oos = null;
	try {
	  fos = new FileOutputStream(file);
	  oos = new ObjectOutputStream(fos);
	  oos.writeObject(object);
	  return true;
	}
	catch (IOException e) {
	  return false;
	}
	finally {
	  try {
		if (oos != null) {
		  oos.close();
		}
		if (fos != null) {
		  fos.close();
		}
	  }
	  catch (IOException e) {
		// Do Nothing
	  }
	}
  }

  /**
   * @param file
   * @return Object
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public static Object load(File file) throws IOException,
	  ClassNotFoundException {
	FileInputStream fis = null;
	ObjectInputStream ois = null;
	try {
	  fis = new FileInputStream(file);
	  ois = new ObjectInputStream(fis);
	  return ois.readObject();
	}
	finally {
	  try {
		if (ois != null) {
		  ois.close();
		}
		if (fis != null) {
		  fis.close();
		}
	  }
	  catch (IOException e) {
		// Do Nothing
	  }
	}
  }
}
