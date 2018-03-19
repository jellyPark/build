package com.lush.core.models;

import java.util.List;
import lombok.Data;
import com.lush.core.models.EndpointDto.Info;

@Data
public class EndpointModel {

  /**
   * Service name of microservice.
   */
  private String service_name;

  /**
   * Service type of microservice.
   */
  private String service_type;

  /**
   * Service scope of microservice.
   */
  private String service_scope;

  /**
   * Service version of microservice.
   */
  private String service_version;

  /**
   * Endpoint list of microservice.
   */
  private List<Info> endpoints;
}
