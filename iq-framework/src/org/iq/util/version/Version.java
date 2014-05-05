package org.iq.util.version;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Sam
 */
public class Version {
  public static final String versionNumber;
  private static String majorVersion = "beta";
  private static String minorVersion = "0";
  private static String patchVersion = "0";
  private static String hotfixVersion = "0";

  static {
	Properties localProperties = new Properties();
	InputStream localInputStream = Version.class
		.getResourceAsStream("version.properties");
	if (localInputStream != null) {
	  try {
		localProperties.load(localInputStream);
	  }
	  catch (IOException localIOException) {
		System.err.println("Could not read version.properties: "
			+ localIOException);
	  }
	  majorVersion = localProperties.getProperty("version.major", "");
	  minorVersion = localProperties.getProperty("version.minor", "");
	  patchVersion = localProperties.getProperty("version.patch", "");
	  hotfixVersion = localProperties.getProperty("version.hotfix", "");
	}
	versionNumber = majorVersion + "." + minorVersion + "." + patchVersion
		+ "." + hotfixVersion;
  }
}