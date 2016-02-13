package eu.arrowhead.ext.serviceregistry;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import eu.arrowhead.common.model.ArrowheadSystem;
import eu.arrowhead.common.model.messages.ProvidedService;
import eu.arrowhead.common.model.messages.ServiceQueryForm;
import eu.arrowhead.common.model.messages.ServiceQueryResult;
import eu.arrowhead.common.model.messages.ServiceRegistryEntry;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("serviceregistry")
public class ServiceRegistryResource {

	private static List<ServiceRegistryEntry> entries = new ArrayList<ServiceRegistryEntry>();
	private static GenericEntity<List<ServiceRegistryEntry>> gentries = new GenericEntity<List<ServiceRegistryEntry>>(
			entries) {
	};
	private List<ProvidedService> pservices = new ArrayList<ProvidedService>();

	// a few hardcoded entries, so the list isn't empty
	ServiceRegistryEntry sre = new ServiceRegistryEntry(
			new ArrowheadSystem("systemGroup", "systemName", "192.168.1.1", "8080", "authenticationInfo"), "elsoUri",
			"serviceMetadata", "tSIG_key");
	ServiceRegistryEntry sre2 = new ServiceRegistryEntry(
			new ArrowheadSystem("systemGroup", "systemName", "192.168.1.2", "8080", "authenticationInfo"), "masodikUri",
			"serviceMetadata", "tSIG_key");
	ServiceRegistryEntry sre3 = new ServiceRegistryEntry(
			new ArrowheadSystem("systemGroup", "systemName", "192.168.1.3", "8080", "authenticationInfo"), "harmadikUri",
			"serviceMetadata", "tSIG_key");

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "This is the Service Registry.";
	}

	@PUT
	@Path("/register")
	public Response registerEntry(ServiceRegistryEntry sre) {
		gentries.getEntity().add(sre);
		gentries.getEntity().add(this.sre);
		gentries.getEntity().add(sre2);
		gentries.getEntity().add(sre3);
		return Response.status(Status.OK).entity(gentries).build();
	}

	@PUT
	@Path("/query")
	public Response queueEntries(ServiceQueryForm sqf) {
		System.out.println("serviceregistry: inside the queueEntries method");
		gentries.getEntity().add(sre);
		gentries.getEntity().add(sre2);
		gentries.getEntity().add(sre3);
		for (int i = 0; i < entries.size(); i++) {
			pservices.add(new ProvidedService(entries.get(i).getProvider(), entries.get(i).getServiceURI(),
					entries.get(i).getServiceMetadata()));
		}
		ServiceQueryResult sqr = new ServiceQueryResult(pservices);
		return Response.status(Status.OK).entity(sqr).build();
	}

	@GET
	@Path("/example")
	public Response exampleEntry() {
		ServiceRegistryEntry sre = new ServiceRegistryEntry(
				new ArrowheadSystem("systemGroup", "systemName", "192.168.1.1", "8080", "authenticationInfo"),
				"serviceURI", "serviceMetadata", "tSIG_key");
		return Response.status(Status.OK).entity(sre).build();
	}
	
	@GET
	@Path("/exampleSQF")
	public Response exampleSQF(){
		List<String> list = new ArrayList<String>();
		ServiceQueryForm sqf = new ServiceQueryForm("serviceMetaData", list, false, "tSIG_key");
		return Response.status(Status.OK).entity(sqf).build();
	}

}
