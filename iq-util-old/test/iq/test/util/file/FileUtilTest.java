package iq.test.util.file;

import java.io.File;

import org.iq.util.file.FileUtil;

import iq.test.BaseTestCase;

/**
 * @author Sam
 *
 */
public class FileUtilTest extends BaseTestCase {
  
  private static final String TEST_FOLDER_NAME = "testFolder";

  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  @Override
  protected void setUp() throws Exception {
    new File(TEST_FOLDER_NAME).mkdirs();
  }
  
  /**
   * 
   */
  public static String getTestingName(String name) {
    return TEST_FOLDER_NAME+File.separator+name;
  }

  /**
   * 
   */
  public void testCreateFolder() {
    FileUtil.createFolder(getTestingName("createFolderTest"));
    boolean found = false;
    File file = new File(TEST_FOLDER_NAME);
    String[] strings = file.list();
    for (String string : strings) {
      if (string.contains("createFolderTest")) {
        found = true;
      }
    }
    assertTrue(found);
  }
  
  /* (non-Javadoc)
   * @see junit.framework.TestCase#tearDown()
   */
  @Override
  protected void tearDown() throws Exception {
    FileUtil.cleanFolder(TEST_FOLDER_NAME);
    new File(TEST_FOLDER_NAME).delete();
  }
}