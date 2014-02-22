package com.iq.amms.services.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Locale;

import com.dropbox.core.DbxAuthInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWriteMode;
import com.dropbox.core.json.JsonReader.FileLoadException;
import com.iq.amms.SystemConf;

public class SyncThread extends Thread{

	private String srcFilename = null;
	private String destFilename = null;
	
	public SyncThread(String srcFilename,  String destFilename){
		System.out.println("Sync Thread Initialized...");
		this.srcFilename = srcFilename;
		this.destFilename = destFilename;
	}

	@Override
	public void run() {
		System.out.println("Sync called for the file"+srcFilename);
		syncFile();
	}

	private void syncFile(){
		String authTokenFilename = SystemConf.getAppRoot()+"/resources/AMMSDataStoreAuthFile.txt";
		try{
			DbxAuthInfo authInfo;
			authInfo = DbxAuthInfo.Reader.readFromFile(authTokenFilename);

			String userLocale = Locale.getDefault().toString();
			DbxRequestConfig requestConfig = new DbxRequestConfig("DbxUploader", userLocale);
			DbxClient _dbxClient = new DbxClient(requestConfig, authInfo.accessToken, authInfo.host);

			File uploadFile = new File (srcFilename);
			FileInputStream uploadFIS;
			uploadFIS = new FileInputStream(uploadFile);


			String targetPath = destFilename; 
			_dbxClient.uploadFile(targetPath, DbxWriteMode.add(), uploadFile.length(), uploadFIS);
			System.out.println("Sync done!!! Deleting the file...");
			uploadFIS.close();
			File file = new File(srcFilename);
			System.out.println("Deleted : "+file.delete());			
		} catch (FileLoadException e) {
			System.out.println("Error while synchronizing....trying again..."+e.getMessage());
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("Error while synchronizing....trying again..."+e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error while synchronizing....trying again..."+e.getMessage());
			syncFile();
		}
	}
}