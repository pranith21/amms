package org.iq.util.file;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Sam
 *
 */
public abstract class FileChangeListener extends AbstractFileListener {

  private HashMap<String, FileProperty> filesMap =
      new HashMap<String, FileProperty>();
  private HashMap<String, FileChangeStatus> filesStatusMap =
      new HashMap<String, FileChangeStatus>();
  private boolean checkDeep = false;
  private FileFilter fileFilter;

  private class FileProperty {

    private Long lastModified;
    private FileChangeStatus fileChangeStatus = FileChangeStatus.NO_CHANGE;

    /**
     * 
     */
    public FileProperty(Long lastModified) {
      this.lastModified = lastModified;
    }

    /**
     * 
     */
    public FileProperty(Long lastModified, FileChangeStatus fileChangeStatus) {
      this.lastModified = lastModified;
      this.fileChangeStatus = fileChangeStatus;
    }

    /**
     * @return the fileChangeStatus
     */
    public FileChangeStatus getFileChangeStatus() {
      return fileChangeStatus;
    }

    /**
     * @param fileChangeStatus
     *          the fileChangeStatus to set
     */
    public void setAction(FileChangeStatus fileChangeStatus) {
      this.fileChangeStatus = fileChangeStatus;
    }

    /**
     * @return the lastModified
     */
    public Long getLastModified() {
      return lastModified;
    }

    /**
     * @param lastModified
     *          the lastModified to set
     */
    public void setLastModified(Long lastModified) {
      this.lastModified = lastModified;
    }
  }

  enum FileChangeStatus {
    NO_CHANGE, ADDED, DELETED, MODIFIED;
  }

  /**
   * @param monitoredFileName
   * @throws FileNotFoundException
   */
  public FileChangeListener(String monitoredFileName)
      throws FileNotFoundException {
    super(monitoredFileName);
    captureFileProperty();
  }

  /**
   * @param monitoredFile
   * @throws FileNotFoundException
   */
  public FileChangeListener(File monitoredFile) throws FileNotFoundException {
    super(monitoredFile);
    captureFileProperty();
  }

  /**
   * @param monitoredFileName
   * @param checkDeep
   * @throws FileNotFoundException
   */
  public FileChangeListener(String monitoredFileName, boolean checkDeep)
      throws FileNotFoundException {
    super(monitoredFileName);
    this.checkDeep = checkDeep;
    captureFileProperty();
  }

  /**
   * @param monitoredFileName
   * @param fileFilter
   * @throws FileNotFoundException
   */
  public FileChangeListener(String monitoredFileName, FileFilter fileFilter)
      throws FileNotFoundException {
    super(monitoredFileName);
    this.fileFilter = fileFilter;
    captureFileProperty();
  }

  /**
   * @param monitoredFileName
   * @param fileFilter
   * @param checkDeep
   * @throws FileNotFoundException
   */
  public FileChangeListener(String monitoredFileName, FileFilter fileFilter,
      boolean checkDeep) throws FileNotFoundException {
    super(monitoredFileName);
    this.fileFilter = fileFilter;
    this.checkDeep = checkDeep;
    captureFileProperty();
  }

  /**
   * 
   */
  private void captureFileProperty() {
    filesMap = new HashMap<String, FileProperty>();
    if (monitoredFile.isFile()) {
      filesMap.put(monitoredFile.getAbsolutePath(), new FileProperty(
          new Long(monitoredFile.lastModified())));
    }
    else if (monitoredFile.isDirectory()) {
      File[] files = getFiles();
      if (files != null && files.length > 0) {
        for (int i = 0; i < files.length; i++) {
          filesMap.put(files[i].getAbsolutePath(), new FileProperty(
              new Long(files[i].lastModified())));
        }
      }
    }
    filesStatusMap = new HashMap<String, FileChangeStatus>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.iq.util.file.AbstractFileListener#shouldListen()
   */
  @Override
  public boolean shouldListen() {
    boolean listenFlag = false;

    if (monitoredFile != null
        && monitoredFile.isFile()
        && monitoredFile.lastModified() != filesMap.values().iterator()
            .next().lastModified) {
      listenFlag = true;
      filesStatusMap.put(monitoredFile.getAbsolutePath(),
          FileChangeStatus.MODIFIED);
    }
    else if (monitoredFile != null && monitoredFile.isDirectory()) {
      File[] files = getFiles();
      HashSet<String> currentCheckedFiles = new HashSet<String>();
      if (files != null && files.length > 0) {
        for (int i = 0; i < files.length; i++) {
          FileChangeStatus fileChangeStatus = FileChangeStatus.NO_CHANGE;
          currentCheckedFiles.add(files[i].getAbsolutePath());
          FileProperty fileProperty =
              filesMap.get(files[i].getAbsolutePath());
          if (fileProperty == null) {
            // new file found
            listenFlag = true;
            fileChangeStatus = FileChangeStatus.ADDED;
          }
          else {
            Long current = fileProperty.lastModified;
            if (current.longValue() != files[i].lastModified()) {
              // modified file found
              listenFlag = true;
              fileChangeStatus = FileChangeStatus.MODIFIED;
            }
          }
          filesStatusMap.put(files[i].getAbsolutePath(), fileChangeStatus);
        }
      }
      // now check for deleted files
      Set<String> lastCheckFiles = new HashSet<String>(filesMap.keySet());
      lastCheckFiles.removeAll(currentCheckedFiles);
      if (lastCheckFiles.isEmpty() == false) {
        for (String fileName : lastCheckFiles) {
          filesStatusMap.put(fileName, FileChangeStatus.DELETED);
        }
        listenFlag = true;
      }
    }
    return listenFlag;
  }

  /**
   * @return
   */
  private File[] getFiles() {
    File[] files = null;
    if (checkDeep) {
      if (fileFilter != null) {
        List<File> fileList =
            FileUtil.listAllFiles(monitoredFile, fileFilter);
        if (fileList.isEmpty() == false) {
          files = fileList.toArray(new File[fileList.size()]);
        }
      }
      else {
        List<File> fileList = FileUtil.listAllFiles(monitoredFile);
        if (fileList.isEmpty() == false) {
          files = fileList.toArray(new File[fileList.size()]);
        }
      }
    }
    else {
      if (fileFilter != null) {
        files = monitoredFile.listFiles(fileFilter);
      }
      else {
        files = monitoredFile.listFiles();
      }
    }
    return files;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.iq.util.file.AbstractFileListener#listen()
   */
  @Override
  public void listen() {
    for (String fileName : filesStatusMap.keySet()) {
      switch (filesStatusMap.get(fileName)) {
        case NO_CHANGE:

          break;
        case ADDED:
          listenAdded(new File(fileName));
          break;
        case MODIFIED:
          listenChanged(new File(fileName));
          break;
        case DELETED:
          listenDeleted(new File(fileName));
          break;
        default:

          break;
      }
    }
    captureFileProperty();
  }

  /**
   * Invoked when a file changes.
   */
  public abstract void listenChanged(File changedFile);

  /**
   * Invoked when a file is added.
   */
  public abstract void listenAdded(File addedFile);

  /**
   * Invoked when a file is deleted.
   */
  public abstract void listenDeleted(File deletedFile);
}