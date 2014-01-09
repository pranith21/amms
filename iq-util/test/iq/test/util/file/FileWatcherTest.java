package iq.test.util.file;

import iq.test.BaseTestCase;

import org.iq.util.file.FileChangeListener;
import org.iq.util.file.FileWatcher;



public class FileWatcherTest extends BaseTestCase {

//  private static final String TEST_FOLDER_NAME = "testFolder";

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  @Override
  protected void setUp() throws Exception {
    /*FileUtil.createFolder(TEST_FOLDER_NAME);
    FileWatcher fileWatcher = FileWatcher.getWatcherInstance();
    FileChangeListener fileChangeListener =
        new MyFileChangeListener(TEST_FOLDER_NAME, true);
    fileWatcher.addFileListener(fileChangeListener);*/
  }
  
  /**
   * 
   */
  public void testFileAdded() {
    /*String fileName = "someFile.txt";
    try {
      FileUtil.createFile(FileUtilTest.getTestingName(fileName), "some content");
    }
    catch (IOException e) {
      assertTrue(e.getMessage(),false);
    }*/
  }
  
  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  @Override
  protected void tearDown() throws Exception {
    /*FileWatcher fileWatcher = FileWatcher.getWatcherInstance();
    fileWatcher.stopFileWatcher();
    FileUtil.cleanFolder(TEST_FOLDER_NAME);
    new File(TEST_FOLDER_NAME).delete();*/
  }
  
  public static void main(String args[]) throws Exception {
    // monitor a single file
    /*TimerTask task = new FileWatcherSam( new File("c:/user.js") ) {
      protected void onChange( File file ) {
        // here we code the action on a change
        System.out.println( "File "+ file.getName() +" have change !" );
      }
    };
    
        for (String key : listenerTable.keySet()) {
      removeFileListener(listenerTable.get(key));
    }


    Timer timer = new Timer();
    // repeat the check every second
    timer.schedule( task , new Date(), 1000 );*/
	
	FileWatcher fileWatcher = FileWatcher.getWatcherInstance();
    
    FileChangeListener fileChangeListener = new MyFileChangeListener("D:/amsp_log",true);
//    FileChangeListener fileChangeListener = new MyFileChangeListener("D:/test.txt");
    fileWatcher.addFileListener(fileChangeListener);
    
    /*try {
      Thread.sleep(5000);
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }
    
    fileWatcher.removeFileListener(fileChangeListener);
    
    try {
      Thread.sleep(15000);
    }
    catch (InterruptedException e) {
      e.printStackTrace();
    }*/

//    fileWatcher.stopFileWatcher();
//	fileWatcher.addFileChangeListener(new FileChangeListener(), "c:/user.js", 1000);
  }
}