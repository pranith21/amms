package org.iq.util.version;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.iq.util.string.StringUtil;

public class Version {
  private static String majorVersion = "Beta";
  private static String minorVersion = "";
  private static String patchVersion = "";
  public static final String versionNumber;

  static {
    Properties localProperties = new Properties();
    InputStream localInputStream =
        Version.class.getResourceAsStream("version.properties");
    if (localInputStream != null) {
      try {
        localProperties.load(localInputStream);
      }
      catch (IOException localIOException) {
        System.err.println("Could not read version.properties: "
            + localIOException);
      }
      majorVersion = localProperties.getProperty("version.major", "Beta");
      minorVersion = localProperties.getProperty("version.minor", "");
      patchVersion = localProperties.getProperty("version.patch", "");
    }
    versionNumber =
        majorVersion
            + (StringUtil.isEmpty(minorVersion) ? "" : "." + minorVersion)
            + (StringUtil.isEmpty(patchVersion) ? "" : ".pb" + patchVersion);
  }
}