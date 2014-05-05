package org.iq.comm;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.iq.comm.CommunicationManager.CommunicationType;
import org.iq.util.file.FileUtil;

public class CommunicatorThread implements Runnable {

  private static CommunicatorThread threadInstance = null;
  private static Thread runner = null;
  private static boolean runnerFlag = true;

  private CommunicatorThread() {
	runner = new Thread(this, "CommunicatorThread");
	runner.start();
  }

  public static void startThread() {
	if (threadInstance == null) {
	  threadInstance = new CommunicatorThread();
	}
  }

  public static void stopThread() {
	runnerFlag = false;
  }

  @Override
  public void run() {
	while (runnerFlag) {
	  System.out.print("CommunicatorThread called, ");
	  List<File> files = FileUtil.listAllFiles(new File(
		  CommunicationManager.COMM_FOLDER), new FileFilter() {

		@Override
		public boolean accept(File commFile) {
		  return commFile.getName()
			  .endsWith(CommunicationManager.COMM_FILE_EXT);
		}
	  });

	  if (files != null && files.size() > 0) {
		System.out.println("processing " + files.size() + " communications.");
		for (File file : files) {
		  System.out.println("Communication file : " + file.getName());
		  try {
			FileInputStream commFileInputStream = new FileInputStream(file);

			Properties properties = new Properties();
			properties.load(commFileInputStream);

			String communicationType = properties
				.getProperty(CommunicationManager.COMM_TYPE);
			String from = properties.getProperty(Communicator.FROM_PARAM_NAME);
			String to = properties.getProperty(Communicator.TO_PARAM_NAME);
			String cc = properties.getProperty(Communicator.CC_PARAM_NAME);
			String bcc = properties.getProperty(Communicator.BCC_PARAM_NAME);
			String subject = properties
				.getProperty(Communicator.SUBJECT_PARAM_NAME);
			String message = properties
				.getProperty(Communicator.MESSAGE_PARAM_NAME);
			String attachmentFilename = properties
				.getProperty(Communicator.ATTACHMENT_PARAM_NAME);

			// code to close file access
			properties = null;
			if (commFileInputStream != null) {
			  commFileInputStream.close();
			}

			Communicator communicator = CommunicationManager
				.getCommunicator(CommunicationType
					.getCommunicationType(communicationType));
			boolean success = communicator.processCommunication(from, to, cc,
				bcc, subject, message, attachmentFilename);

			if (success) {
			  System.out.println("Deleting communication file, status : "
				  + file.delete());
			}
		  }
		  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		  finally {

		  }
		}
	  }
	  else {
		System.out.println("processing 0 communications.");
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
