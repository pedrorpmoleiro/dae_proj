package ws;

import dtos.EmailDTO;
import ejbs.EmailBean;
import ejbs.SocioBean;
import entities.Socio;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/email")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmailController {

    @EJB
    private EmailBean emailBean;

    @EJB
    private SocioBean socioBean;

    @POST
    @Path("/{username}")
    public Response sendEmail(@PathParam("username") String username, EmailDTO emailDTO) throws MyEntityNotFoundException {
        Socio socio = socioBean.findSocio(username);

        if (socio == null) {
            throw new MyEntityNotFoundException("Socio Not Found");
        }

        emailBean.send(socio.getEmail(), emailDTO.getSubject(), emailDTO.getMessage());

        return Response.status(Response.Status.OK).entity("EMail Sent").build();
    }
}
