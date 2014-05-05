package org.iq.test;

// Include the Dropbox SDK.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;

public class Main {
  public static void main(String[] args) throws IOException, DbxException {
	// Get your app key and secret from the Dropbox developers website.
	final String APP_KEY = "kykmfb8he3o1x66";
	final String APP_SECRET = "6sk9n81ebaw2gh3";

	DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

	DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale
		.getDefault().toString());
	DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
	String authorizeUrl = webAuth.start();
	System.out.println("1. Go to: " + authorizeUrl);
	System.out.println("2. Click \"Allow\" (you might have to log in first)");
	System.out.println("3. Copy the authorization code.");
	String code = new BufferedReader(new InputStreamReader(System.in))
		.readLine().trim();
	DbxAuthFinish authFinish = webAuth.finish(code);
	DbxClient client = new DbxClient(config, authFinish.accessToken);
	System.out
		.println("Linked account: " + client.getAccountInfo().displayName);
	// Save auth information to output file.
	DbxAuthInfo authInfo = new DbxAuthInfo(authFinish.accessToken, appInfo.host);
	String authTokenOutputFilename = "/resources/AMMSDataStoreAuthFile.txt";

	try {
	  DbxAuthInfo.Writer.writeToFile(authInfo, authTokenOutputFilename);
	  System.out.println("Saved authorization information to \""
		  + authTokenOutputFilename + "\".");
	}
	catch (IOException ex) {
	  System.err.println("Error saving to <auth-file-out>: " + ex.getMessage());
	  System.err.println("Dumping to stderr instead:");
	  DbxAuthInfo.Writer.writeToStream(authInfo, System.out);
	  System.exit(1);
	  return;
	}

  }
}