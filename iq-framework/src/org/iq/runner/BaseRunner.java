package org.iq.runner;

public abstract class BaseRunner implements Runnable {

  protected static final short RUN_ONCE = 0;
  protected static final short RUN_INFINITELY = 1;
  protected static final short RUN_WITH_DELAY = 2;

  private Thread runner = null;
  private short runMode = 0;
  private int runDelay = 0;

  /**
   * 
   */
  protected BaseRunner(String runnerName) {
	this.runner = new Thread(this, runnerName);
  }

  /**
   * 
   */
  protected BaseRunner(String runnerName, short runMode) {
	this.runner = new Thread(this, runnerName);
	this.runMode = runMode;
  }

  /**
   * 
   */
  protected BaseRunner(String runnerName, short runMode, int runDelay) {
	this.runner = new Thread(this, runnerName);
	this.runMode = runMode;
	this.runDelay = runDelay;
  }

  protected void startRunner() {
	if (runner != null) {
	  runner.start();
	}
  }

  protected abstract boolean preCheckConditions();

  protected abstract void performAction();

  @Override
  public void run() {
	if (preCheckConditions()) {
	  performAction();
	}
  }
}