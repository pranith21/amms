package org.iq.data;

import java.io.File;

public class DataUploaderThread implements Runnable {

  private DataUploader dataUploader = null;
  private File srcFile = null;
  private boolean runnerFlag = true;

  /**
   * @param srcFile2
   */
  public DataUploaderThread(DataUploader dataUploader, File srcFile) {
	this.dataUploader = dataUploader;
	this.srcFile = srcFile;
  }

  /**
   * @param dataUploader
   * @param srcFile
   */
  public static void
	  uploadFileOnThread(DataUploader dataUploader, File srcFile) {
	Thread dataUploaderThread = new Thread(new DataUploaderThread(dataUploader,
		srcFile));
	dataUploaderThread.start();
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Runnable#run()
   */
  @Override
  public void run() {
	while (runnerFlag) {
	  boolean success = dataUploader.uploadFile(srcFile);
	  if (success) {
		System.out.println("Deleting data file, status : " + srcFile.delete());
		runnerFlag = false;
	  }

	  try {
		Thread.sleep(30000);
	  }
	  catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	}
  }
}
