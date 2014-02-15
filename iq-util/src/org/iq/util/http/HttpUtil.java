package org.iq.util.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * @author Sam
 *
 */
public class HttpUtil {
  private static final String END_OF_INPUT = "\\Z";

  public static String getPageContent(String inputUrlString)
  throws IOException {
    URL url = new URL(inputUrlString);
    return getPageContent(url);
  }

  public static String getPageContent(URL inputUrl) throws IOException {
    String result = null;
    URLConnection connection = null;
    connection = inputUrl.openConnection();
    Scanner scanner = new Scanner(connection.getInputStream());
    scanner.useDelimiter(END_OF_INPUT);
    result = scanner.next();
    return result;
  }

  public static boolean checkUrl(String inputUrlString) throws IOException {
    URL url = new URL(inputUrlString);
    return checkUrl(url);
  }

  public static boolean checkUrl(URL inputUrl) throws IOException {
    HttpURLConnection huc = (HttpURLConnection)inputUrl.openConnection();
    huc.setRequestMethod("GET");
    huc.connect();
    if (HttpURLConnection.HTTP_OK == huc.getResponseCode()) {
      return true;
    }
    return false;
  }
}
