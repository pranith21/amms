package org.iq.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Example {

  private static final String USER_AGENT = "";

  public static void main(String[] args) throws IOException {
	String username = "pranithkumarp";
	String password = "75855715";
	String senderid = "UPDATE";
	String mobileNo = "919836609996";

	String url = "http://www.txtguru.in/imobile/api.php?username=" + username
		+ "&password=" + password + "&source=" + senderid + "&dmobile="
		+ mobileNo + "&message=Hi!Howareyou? SMS FROM TextGuru SMSGATEWAY";

	URL obj = new URL(url);
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();

	// optional default is GET
	con.setRequestMethod("GET");

	// add request header
	con.setRequestProperty("User-Agent", USER_AGENT);

	int responseCode = con.getResponseCode();
	System.out.println("\nSending 'GET' request to URL : " + url);
	System.out.println("Response Code : " + responseCode);

	BufferedReader in = new BufferedReader(new InputStreamReader(
		con.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
	  response.append(inputLine);
	}
	in.close();

	// print result
	System.out.println(response.toString());

  }
}
