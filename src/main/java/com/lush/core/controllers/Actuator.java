package com.lush.core.controllers;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.lush.core.models.EndpointDto;
import com.lush.core.models.EndpointModel;

@RestController
public class Actuator {

  /**
   * Define HttpServletRequest for get client request information.
   */
  @Autowired
  private HttpServletRequest request;

  /**
   * Define RestTemplate for get response information of uri.
   */
  @Autowired
  private RestTemplate restTemplate;

  /**
   * Define Gson for json convert and parse.
   */
  @Autowired
  private Gson gson;

  /**
   * Define service name.
   */
  private String serviceName = "service-example";

  /**
   * Define service type.
   */
  private String serviceType = "microservice";

  /**
   * Define service scope.
   */
  private String serviceScope = "examples";

  /**
   * Define service version.
   */
  private String serviceVersion = "master";
  // /**
  // * Define service name.
  // */
  // @Value("${service.name}")
  // private String serviceName;
  //
  // /**
  // * Define service type.
  // */
  // @Value("${service.type}")
  // private String serviceType;
  //
  // /**
  // * Define service scope.
  // */
  // @Value("${service.scope}")
  // private String serviceScope;
  //
  // /**
  // * Define service version.
  // */
  // @Value("${service.version}")
  // private String serviceVersion;

  /**
   * Define InetAddress for get host name. The hostname can be imported as a HttpServletRequest
   * object, but an issue occurs in the docker collector container that recognizes the hostname as '
   * java_http'.
   */
  private InetAddress ip;

  /**
   * Method name : setUri Description : Set uri for rest api.
   *
   * @param context
   * @return String
   * @throws UnknownHostException
   */
  public String setUri(String context) throws UnknownHostException {
    ip = InetAddress.getLocalHost();
    return request.getScheme() + "://" + ip.getHostName() + ":" + request.getServerPort() + "/"
        + context;
  }

  /**
   * Method name : endpoints. Description : Get endpoint list of api.
   *
   * @return ResponseEntity
   * @throws UnknownHostException
   */
  @GetMapping("/")
  public ResponseEntity<Object> endpoints() throws UnknownHostException {
    // Get endpoints data.
    String uri = setUri("mappings");
    ResponseEntity<JsonNode> data = restTemplate.getForEntity(uri, JsonNode.class);
    JsonNode dataBody = data.getBody().findPath("dispatcherServlet");

    // Find all method and uri
    List<JsonNode> methods = dataBody.findValues("methods");
    List<JsonNode> patterns = dataBody.findValues("patterns");

    String method = "";
    String pattern = "";
    String regex = "[\"\\[\\]]";
    List<EndpointDto.Info> endpointList = new ArrayList<EndpointDto.Info>();

    for (int idx = 0; idx < methods.size(); idx++) {
      method = methods.get(idx).toString().replaceAll(regex, "");
      pattern = patterns.get(idx).toString().replaceAll(regex, "");

      if (method.length() == 0 || pattern.length() == 0 || "/health".equals(pattern)
          || "/mappings".equals(pattern)) {
        continue;
      }

      EndpointDto.Info endpoint = new EndpointDto.Info();
      endpoint.setMethod(method);
      endpoint.setUri(pattern);
      endpointList.add(endpoint);
    }

    // Set endpoints data.
    EndpointModel endpoints = new EndpointModel();
    endpoints.setService_name(serviceName);
    endpoints.setService_type(serviceType);
    endpoints.setService_scope(serviceScope);
    endpoints.setService_version(serviceVersion);
    endpoints.setEndpoints(endpointList);

    return new ResponseEntity<Object>(endpoints, HttpStatus.OK);
  }
}
