package eu.arrowhead.ext.qos;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import eu.arrowhead.common.model.ArrowheadSystem;
import eu.arrowhead.common.model.messages.QoSVerificationResponse;
import eu.arrowhead.common.model.messages.QoSVerify;

/**
 * @author pardavib
 *
 */
@Path("qosservice")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QoSResource {

	private Map<ArrowheadSystem, Boolean> qosMap = new HashMap<ArrowheadSystem, Boolean>();

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Hello, I am the temporary QoS Service.";
	}

	@PUT
	@Path("/verification")
	public Response qosVerification(QoSVerify qosVerify) {
		for (ArrowheadSystem system : qosVerify.getProvider()) {
			qosMap.put(system, true);
		}

		return Response.status(Status.OK).entity(qosMap).build();

	}

	@PUT
	@Path("/reservation")
	public Response qosReservation(QoSVerificationResponse qosReservation) {
		boolean reserved = true;

		return Response.status(Status.OK).entity(reserved).build();
	}
}
