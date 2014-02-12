package org.iq.service;

import java.util.HashMap;
import java.util.Set;

import org.iq.db.mysql.MySqlSession;
import org.iq.util.string.StringUtil;

import com.iq.amms.SystemConf;
import com.iq.amms.setup.old.DatabaseHandler;

/**
 * @author Sam
 * 
 */
public abstract class BaseService {

  private HashMap<String, Object> resultMap = new HashMap<String, Object>();
  protected HashMap<String, Object> resultAttributes =
      new HashMap<String, Object>();
  protected String redirectUrl = "NOT_FOUND_PAGE";
  protected MySqlSession mySqlSession = null;


  /**
   * 
   */
  public BaseService() {

  }

  /**
   * @param input
   * @return HashMap
   * @throws AmspServiceException
   */
  public abstract void execute(HashMap<String, Object> input)
      throws Exception;

  /**
   * @param input
   * @return HashMap
   * @throws AmspServiceException
   */
  public HashMap<String, Object> executeService(HashMap<String, Object> input)
      throws Exception {
    init(input);
    execute(input);
    resultMap.put(ServiceConstants.RESULT_ATTRIBUTES, resultAttributes);
    resultMap.put(ServiceConstants.REDIRECT_URL, redirectUrl);
    return resultMap;
  }

  /**
   * @param input
   * @throws AmspServiceException
   */
  public void init(HashMap<String, Object> input) throws Exception {
    mySqlSession =
        new MySqlSession(SystemConf.getDbHost(), SystemConf.getDbPort(),
            DatabaseHandler.DB_NAME, SystemConf.getDbUser(), SystemConf
                .getDbPass());
  }

  /**
   * @param input
   * @param key
   * @throws AmspServiceException
   */
  protected void validateInput(HashMap<String, Object> input, String key)
      throws Exception {
    Object data = input.get(key);
    if (StringUtil.isEmpty(StringUtil.getStringValue(data))) {
      throw new Exception("Input Field : " + key + " is null or blank.");
    }
  }

  /**
   * @param input
   * @throws AmspServiceException
   */
  protected void validateAllInput(HashMap<String, Object> input)
      throws Exception {
    Set keys = input.keySet();
    for (Object thisKey : keys) {
      Object data = input.get(thisKey);
      if (StringUtil.isEmpty(StringUtil.getStringValue(data))) {
        throw new Exception("Input Field : " + thisKey
            + " is null or blank.");
      }
    }
  }
}
