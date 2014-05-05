package org.iq.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Locale;

import org.iq.exception.DataUploaderException;
import org.iq.service.helpers.SystemConf;

import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWriteMode;
import com.dropbox.core.json.JsonReader.FileLoadException;

public class DropBoxUploader extends DataUploader {

  private static final String authTokenFilename = SystemConf.getAppRoot()
	  + "/resources/AMMSDataStoreAuthFile.txt";
  private static DbxClient _dbxClient = null;

  public DropBoxUploader() throws DataUploaderException {
	try {
	  DbxAuthInfo authInfo = DbxAuthInfo.Reader.readFromFile(authTokenFilename);
	  String userLocale = Locale.getDefault().toString();
	  DbxRequestConfig requestConfig = new DbxRequestConfig("DbxUploader",
		  userLocale);
	  _dbxClient = new DbxClient(requestConfig, authInfo.accessToken,
		  authInfo.host);
	}
	catch (FileLoadException e) {
	  throw new DataUploaderException("Error initialising DropBoxUploader", e);
	}
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.iq.data.DataUploader#uploadFile(java.io.File)
   */
  @Override
  boolean uploadFile(File srcFile) {
	try {
	  FileInputStream uploadFIS = new FileInputStream(srcFile);
	  _dbxClient.uploadFile("/ammsdbbackup/"+srcFile.getName(), DbxWriteMode.add(),
		  srcFile.length(), uploadFIS);
	  uploadFIS.close();
	  return true;
	}
	catch (FileNotFoundException e) {
	  e.printStackTrace();
	}
	catch (Exception e) {
	  System.out.println("Error while synchronizing....trying again..."
		  + e.getMessage());
	}
	return false;
  }
}
