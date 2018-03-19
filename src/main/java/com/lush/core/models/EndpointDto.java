package com.lush.core.models;

import lombok.Data;

@Data
public class EndpointDto {

  @Data
  public static class Info {

    /**
     * Endpoint uri.
     */
    private String uri;

    /**
     * Http protocol method.
     */
    private String method;
  }
}
