package com.lush.core.models;

import lombok.Data;

@Data
public class Response {

  /**
   * Response status.
   */
  private String status;

  /**
   * Response message.
   */
  private String message;

  /**
   * Response data.
   */
  private Object data;

  /**
   * Description : Default constructor.
   */
  public Response() {
    this.status = "ok";
    this.message = "";
    this.data = "";
  }

  /**
   * Description : Constructor.
   *
   * @param status
   */
  public Response(String status) {
    this.status = status;
    this.message = "";
    this.data = "";
  }

  /**
   * Description : Constructor.
   *
   * @param status
   * @param message
   */
  public Response(String status, String message) {
    this.status = status;
    this.message = message;
    this.data = "";
  }
}
