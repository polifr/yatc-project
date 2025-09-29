package it.poli.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/quarkus-jee/test")
public class TestEndpoint {

  @Path("v1")
  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String testV1() {
    return "This is a Quarkus JEE microservice!";
  }
}
