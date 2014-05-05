/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package org.iq.util.version;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author SC64807
 * 
 */
public class GenerateImportString {

  /**
   * @param args
   * @throws
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
	Class[] classes = getClasses("org.iq");
	for (Class class1 : classes) {
	  System.out.println(class1.getCanonicalName());
	}
	classes = getClasses("com.iq");
	for (Class class1 : classes) {
	  System.out.println(class1.getCanonicalName());
	}
  }

  private static Class[] getClasses(String packageName)
	  throws ClassNotFoundException, IOException {
	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	assert classLoader != null;
	String path = packageName.replace('.', '/');
	Enumeration<URL> resources = classLoader.getResources(path);
	List<File> dirs = new ArrayList<File>();
	while (resources.hasMoreElements()) {
	  URL resource = resources.nextElement();
	  dirs.add(new File(resource.getFile()));
	}
	ArrayList<Class> classes = new ArrayList<Class>();
	for (File directory : dirs) {
	  classes.addAll(findClasses(directory, packageName));
	}
	return classes.toArray(new Class[classes.size()]);
  }

  private static List<Class> findClasses(File directory, String packageName)
	  throws ClassNotFoundException {
	List<Class> classes = new ArrayList<Class>();
	if (!directory.exists()) {
	  return classes;
	}
	File[] files = directory.listFiles();
	for (File file : files) {
	  if (file.isDirectory()) {
		assert !file.getName().contains(".");
		classes.addAll(findClasses(file, packageName + "." + file.getName()));
	  }
	  else if (file.getName().endsWith(".class")) {
		classes.add(Class.forName(packageName + '.'
			+ file.getName().substring(0, file.getName().length() - 6)));
	  }
	}
	return classes;
  }
}
