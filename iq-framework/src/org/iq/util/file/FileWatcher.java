package org.iq.util.file;

import java.io.File;
import java.util.Hashtable;

/**
 * Class monitoring a {@link File} for changes.
 * 
 * @author Sam
 */
public class FileWatcher implements Runnable {

  private static final float DEFAULT_WATCH_INTERVAL = 0.0333333333333333333333333333333333333333333333333f;
  // private static final float DEFAULT_NO_LISTENER_WATCH_INTERVAL =
  // 0.0666666666666666666666666666666666666666666666666f;
  private static final int MINUTE_TO_MILLIS = 1000 * 60;

  private static final FileWatcher watcherInstance = new FileWatcher();
  private Thread runner;
  private boolean runnerFlag = false;

  private Hashtable<String, AbstractFileListener> listenerTable;

  /**
   * Constructor.
   */
  private FileWatcher() {
	runner = new Thread(this, "File Watcher Thread");
	listenerTable = new Hashtable<String, AbstractFileListener>();
	startFileWatcher();
	// Create timer, run timer thread as daemon.
	// timer = new Timer();
  }

  /**
   * Gets the LoggerThread watcherInstance.
   * 
   * @return LoggerThread watcherInstance
   */
  public static FileWatcher getWatcherInstance() {
	return watcherInstance;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Runnable#run()
   */
  public void run() {
	while (runnerFlag == true) {
	  // System.out.println(runner.getName()+" Watching now...");

	  if (listenerTable.isEmpty() == false) {
		// System.out.println("There are "+listenerTable.size()+" file listeners in the table.");
	  }

	  long sleepTime = 0;
	  sleepTime = (long) (DEFAULT_WATCH_INTERVAL * MINUTE_TO_MILLIS);
	  // System.out.println(runner.getName()+" Sleeping now for "+((float)sleepTime/MINUTE_TO_MILLIS)+" minutes...");
	  try {
		Thread.sleep(sleepTime);
	  }
	  catch (InterruptedException e) {
		e.printStackTrace();
	  }
	}
	System.out.println("Exiting " + runner.getName() + ".");
  }

  /**
   * 
   */
  public void startFileWatcher() {
	runnerFlag = true;
	runner.start();
	System.out.println(runner.getName() + " started...");
  }

  /**
   * 
   */
  public void stopFileWatcher() {
	runnerFlag = false;
	System.out.println("Requesting " + runner.getName() + " to stop...");
  }

  /**
   * Adds a {@link AbstractFileListener} to the notification list.
   * 
   * @param listener
   */
  public void addFileListener(AbstractFileListener listener) {
	listenerTable.put(listener.getId(), listener);
	listener.startListening();
  }

  /**
   * Remove the listener from the notification list.
   * 
   * @param listener
   *          the listener to be removed.
   */
  public void removeFileListener(AbstractFileListener listener) {
	listener.cancel();
	listenerTable.remove(listener.getId());
  }
}