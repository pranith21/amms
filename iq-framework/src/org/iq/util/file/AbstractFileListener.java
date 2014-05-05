package org.iq.util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Listener listening to {@link File} changes.
 * 
 * @author Sam
 * 
 */
public abstract class AbstractFileListener extends TimerTask {

  private Timer timer = new Timer(true);
  protected File monitoredFile = null;

  /**
   * @throws FileNotFoundException
   * 
   */
  public AbstractFileListener(String monitoredFileName)
	  throws FileNotFoundException {
	this(new File(monitoredFileName));
  }

  /**
   * @throws FileNotFoundException
   * 
   */
  public AbstractFileListener(File monitoredFile) throws FileNotFoundException {
	this.monitoredFile = monitoredFile;
	if (!this.monitoredFile.exists()) { // but is it on CLASSPATH?
	  URL fileURL = this.getClass().getClassLoader()
		  .getResource(monitoredFile.toString());
	  if (fileURL != null) {
		this.monitoredFile = new File(fileURL.getFile());
	  }
	  else {
		throw new FileNotFoundException("File Not Found: " + monitoredFile);
	  }
	}
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.util.TimerTask#run()
   */
  @Override
  public void run() {
	// System.out.println("Processing " + this.getClass().getName()+"...");
	// System.out.println("Monitored File: " +
	// this.monitoredFile.getName()+"...");
	if (this.shouldListen()) {
	  this.listen();
	}
  }

  /**
   * 
   */
  public void startListening() {
	timer.schedule(this, 0, 500);
  }

  /**
   * @return
   */
  public String getId() {
	return monitoredFile.getName() + this.hashCode();
  }

  /**
   * Checks whether to listen now.
   */
  public abstract boolean shouldListen();

  /**
   * Invoked when a file changes.
   */
  public abstract void listen();
}