package org.iq.data;

import java.io.File;

import org.iq.exception.DataUploaderException;
import org.iq.service.helpers.SystemConf;
import org.iq.util.file.FileUtil;

/**
 * @author Sam
 */
public class DataUploadManager {

  static final String DATA_FILE_EXT = "sql";
  static final String DATA_UPLOAD_SRC_FOLDER = SystemConf.getAmmsHome()
	  + File.separator + "datasrc";
  static final String UPLOADER_TYPE = "uploaderType";

  static {
	FileUtil.createFolder(DATA_UPLOAD_SRC_FOLDER);
  }

  /**
   * 
   */
  public enum UploaderType {

	DROPBOX("Dropbox Uploader");

	private final String uploaderType;

	UploaderType(String type) {
	  uploaderType = type;
	}

	/**
	 * @return String
	 */
	public String getUploaderTypeValue() {
	  return uploaderType;
	}

	/**
	 * @param type
	 * @return CommunicationType
	 */
	public static UploaderType getUploaderType(String type) {
	  UploaderType uploaderType = null;
	  for (UploaderType thisUploaderType : UploaderType.values()) {
		if (thisUploaderType.getUploaderTypeValue().equals(type)) {
		  uploaderType = thisUploaderType;
		  break;
		}
	  }
	  return uploaderType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
	  return uploaderType;
	}
  }

  public static DataUploader getDataUploader(UploaderType uploaderType)
	  throws DataUploaderException {
	switch (uploaderType) {
	  case DROPBOX:
		return new DropBoxUploader();
	  default:
		return null;
	}
  }
}