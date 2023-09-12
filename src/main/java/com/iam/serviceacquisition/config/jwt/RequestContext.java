package com.iam.serviceacquisition.config.jwt;

public class RequestContext {

  public static final String AUTHORIZATION_HEADER = "Authorization";

  private static final ThreadLocal<RequestContext> CONTEXT = new ThreadLocal<>();

  private String token;

  public static RequestContext getInstance() {
    RequestContext result = CONTEXT.get();
    if (result == null) {
      result = new RequestContext();
      CONTEXT.set(result);
    }
    return result;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void clear() {
    this.token = null;
  }
}
