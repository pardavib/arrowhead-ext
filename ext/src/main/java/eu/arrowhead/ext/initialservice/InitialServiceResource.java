package eu.arrowhead.ext.initialservice;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.arrowhead.common.model.ArrowheadService;
import eu.arrowhead.common.model.ArrowheadSystem;
import eu.arrowhead.common.model.messages.OrchestrationForm;
import eu.arrowhead.common.model.messages.OrchestrationResponse;
import eu.arrowhead.common.model.messages.ServiceRegistryEntry;
import eu.arrowhead.common.model.messages.ServiceRequestForm;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("initservice")

public class InitialServiceResource {
	
	private ServiceRegistryEntry sre = new ServiceRegistryEntry(
			new ArrowheadSystem("systemGroup", "systemName", "eztenylegazamitaserviceregistryad", "port", "authenticationInfo"),
			"initialURI", "serviceMetadata", "tSIG_key");
	private String serviceRegistryURI = "http://localhost:8080/ext/serviceregistry/register";
	private String orchestratorURI = "http://localhost:8080/core/orchestrator/orchestration";
	Client client = ClientBuilder.newClient();
	List<String> stringlist = new ArrayList<String>();
	private ServiceRequestForm srf = new ServiceRequestForm(
			new ArrowheadService("serviceGroup", "serviceDefinition", stringlist, "metaData"),
			"requestedQoS",
			new ArrowheadSystem("systemGroup", "systemName", "192.168.1.1", "8080", "authenticationInfo"), 2);
	
	@GET
	public Response registerDefault(){
		return client.target(serviceRegistryURI).request().put(Entity.json(sre));
	}
	
	@PUT
	public Response registerCustom(ServiceRegistryEntry sre){
		return client.target(serviceRegistryURI).request().put(Entity.json(sre));
	}
	
	@GET
	@Path("/SRF")
	public OrchestrationResponse sendSRF(){
		System.out.println("initservice: SRF sent out to " + orchestratorURI);
		Response response =  client.target(orchestratorURI).request().post(Entity.json(srf));
		System.out.println("initservice: Orchestrator has returned the response");
		OrchestrationResponse or = response.readEntity(OrchestrationResponse.class);
		for(OrchestrationForm of : or.getResponse()){
			System.out.println("IP address of system: " + of.getProvider().getIPAddress());
			System.out.println("Service Definiton of service: " + of.getService().getServiceDefinition());
			System.out.println("Service URI: " + of.getServiceURI());
			System.out.println("Authorization Info: " + of.getAuthorizationInfo());
		}
		return or;
	}
}
