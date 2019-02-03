package org.algorrim.protobuff.sample.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.algorrim.protobuff.sample.model.SimpleObject;
import org.algorrim.protobuff.sample.persistence.IPersistence;
import org.algorrim.protobuff.sample.persistence.PersistenceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Servlet that listens to POST requests at /simpleobject path.
 */
@Singleton
@Path("/simpleobject")
public class SimpleObjectEndpoint {

    private static Logger log = LogManager.getLogger(SimpleObject.class);
    @Inject
    private IPersistence persistence;

    /**
     * POST method that receives a single object as JSON, persists it and returns a 200 HTTP Code with the
     * object received.
     * @param simpleObject A simple object in JSON format.
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postSimpleObject(SimpleObject simpleObject) {
        Response response;
        log.debug("Received object [" + simpleObject + "].");
        try {
            persistence.saveSimpleObject(simpleObject);
            response = Response.ok(simpleObject).build();
            log.debug("Returning HTTP code 200.");
        } catch (PersistenceException e) {
            response = Response.serverError().build();
            log.debug("Returning HTTP code 500.");
        }
        return response;
    }

}
