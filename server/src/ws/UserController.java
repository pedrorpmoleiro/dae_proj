package ws;

import dtos.AdministradorDTO;
import ejbs.AdministradorBean;
import entities.Administrador;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class UserController {
    @EJB
    private AdministradorBean administradorBean;

    // Converts an entity to a DTO class
    AdministradorDTO toDTO(Administrador administrador) {
        return new AdministradorDTO(
                administrador.getUsername(),
                administrador.getPassword(),
                administrador.getName(),
                administrador.getEmail()
        );
    }

    // converts an entire list of entities into a list of DTOs
    List<AdministradorDTO> toDTOs(List<Administrador> administradores) {
        return administradores.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/administradores") // means: the relative url path is “/api/students/”
    public Response all() {
        return Response.status(Response.Status.OK).entity(toDTOs(administradorBean.all())).build();
    }

    @POST
    @Path("/administradores")
    public Response createNewAdministrador(AdministradorDTO administradorDTO) {
        administradorBean.create(
                administradorDTO.getUsername(),
                administradorDTO.getPassword(),
                administradorDTO.getName(),
                administradorDTO.getEmail());

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/administradores/{username}")
    public Response getAdministradorDetails(@PathParam("username") String username) {
        Administrador administrador = administradorBean.findAdministrador(username);

        return Response.status(Response.Status.OK).entity(toDTO(administrador)).build();
    }
}
