package iq.test.util.file;

import java.io.File;
import java.io.FileNotFoundException;

import org.iq.util.file.FileChangeListener;

/**
 * @author Sam
 *
 */
public class MyFileChangeListener extends FileChangeListener {

  /**
   * @param monitoredFileName
   * @throws FileNotFoundException
   */
  public MyFileChangeListener(String monitoredFileName) throws FileNotFoundException {
    super(monitoredFileName);
  }

  public MyFileChangeListener(String monitoredFileName, boolean deep) throws FileNotFoundException {
    super(monitoredFileName,deep);
  }

  /* (non-Javadoc)
   * @see org.iq.util.file.FileChangeListener#listenAdded(java.io.File)
   */
  @Override
  public void listenAdded(File addedFile) {
    System.out.println("added "+addedFile.getAbsolutePath());
  }

  /* (non-Javadoc)
   * @see org.iq.util.file.FileChangeListener#listenChanged(java.io.File)
   */
  @Override
  public void listenChanged(File changedFile) {
    System.out.println("changed "+changedFile.getAbsolutePath());
  }

  /* (non-Javadoc)
   * @see org.iq.util.file.FileChangeListener#listenDeleted(java.io.File)
   */
  @Override
  public void listenDeleted(File deletedFile) {
    System.out.println("deleted "+deletedFile.getAbsolutePath());
  }

}
