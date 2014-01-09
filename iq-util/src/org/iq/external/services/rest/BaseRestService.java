/**
 * Copyright (c) 2009, Amdocs. -- All Rights Reserved
 * 
 */

package org.iq.external.services.rest;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * @author sc64807
 * 
 */
public abstract class BaseRestService {

  /**
   * @return String
   */
  @GET
  @Path("/test/get")
  @Produces("text/html")
  public String testGet() {
    return "TEST RESTFUL SERVICE CALLED OVER <b>GET</b> SUCCESSFULLY";
  }
  
  /**
   * @return String
   */
  @POST
  @Path("/test/post")
  @Produces("text/html")
  public String testPost() {
    return "TEST RESTFUL SERVICE CALLED OVER <b>POST</b> SUCCESSFULLY";
  }
  
  /**
   * @param object
   * @return String
   * @throws JsonGenerationException
   * @throws JsonMappingException
   * @throws IOException
   */
  protected String getJson(Object object) throws JsonGenerationException,
      JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }

  /**
   * @param json
   * @param inputClass
   * @return Object
   * @throws JsonParseException
   * @throws JsonMappingException
   * @throws IOException
   */
  @SuppressWarnings("unchecked")
  protected Object getObject(String json, Class inputClass)
      throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(json, inputClass);
  }

  
  /*    private static String getResponse(WebResource service) {
        return service.accept(MediaType.TEXT_XML).get(ClientResponse.class).toString();
    }

    private static String getOutputAsXML(WebResource service) {
        return service.accept(MediaType.TEXT_XML).get(String.class);
    }

    private static String getOutputAsText(WebResource service) {
        return service.accept(MediaType.TEXT_PLAIN).get(String.class);
    }*/

}
