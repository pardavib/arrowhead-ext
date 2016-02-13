package eu.arrowhead.ext.initialservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.arrowhead.common.model.ArrowheadSystem;
import eu.arrowhead.common.model.messages.ServiceRegistryEntry;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("initservice")

public class InitialServiceResource {
	
	private ServiceRegistryEntry sre = new ServiceRegistryEntry(
			new ArrowheadSystem("systemGroup", "systemName", "iPAddress", "port", "authenticationInfo"),
			"serviceURI", "serviceMetadata", "tSIG_key");
	private String serviceRegistryURI = "http://localhost:8080/ext/serviceregistry/register";
	Client client = ClientBuilder.newClient();
	
	@GET
	public Response registerDefault(){
		return client.target(serviceRegistryURI).request().put(Entity.json(sre));
	}
	
	@PUT
	public Response registerCustom(ServiceRegistryEntry sre){
		return client.target(serviceRegistryURI).request().put(Entity.json(sre));
	}
}
