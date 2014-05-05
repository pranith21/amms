package org.iq.data;

import java.io.File;

public abstract class DataUploader {

  /**
   * 
   */
  public void upload(String srcFilename) {
	File srcFile = new File(srcFilename);
	DataUploaderThread.uploadFileOnThread(this, srcFile);
  }

  /**
   * 
   */
  abstract boolean uploadFile(File srcFile);

}