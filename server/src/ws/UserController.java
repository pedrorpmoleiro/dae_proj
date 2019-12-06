package ws;

import dtos.AdministradorDTO;
import dtos.AtletaDTO;
import dtos.TreinadorDTO;
import ejbs.AdministradorBean;
import ejbs.AtletaBean;
import ejbs.TreinadorBean;
import entities.Administrador;
import entities.Atleta;
import entities.Treinador;
import exceptions.MyEntityExistsException;

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
    @EJB
    private AtletaBean atletaBean;
    @EJB
    private TreinadorBean treinadorBean;


    // Converts an entity to a DTO class
    AdministradorDTO toDTO(Administrador administrador) {
        return new AdministradorDTO(
                administrador.getIdSocio(),
                administrador.getUsername(),
                administrador.getPassword(),
                administrador.getName(),
                administrador.getEmail()
        );
    }

    // converts an entire list of entities into a list of DTOs
    List<AdministradorDTO> toDTOsAdministrador(List<Administrador> administradores) {
        return administradores.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/administradores") // means: the relative url path is “/api/students/”
    public Response allAdministradores() {
        return Response.status(Response.Status.OK).entity(toDTOsAdministrador(administradorBean.all())).build();
    }

    @POST
    @Path("/administradores")
    public Response createNewAdministrador(AdministradorDTO administradorDTO) throws MyEntityExistsException {
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


    //**********************************Atletas**********************************

    // Converts an entity to a DTO class
    AtletaDTO toDTO(Atleta atleta) {
        return new AtletaDTO(
                atleta.getIdSocio(),
                atleta.getUsername(),
                atleta.getPassword(),
                atleta.getName(),
                atleta.getEmail()
        );
    }

    // converts an entire list of entities into a list of DTOs
    List<AtletaDTO> toDTOsAtleta(List<Atleta> atletas) {
        return atletas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/atletas") // means: the relative url path is “/api/students/”
    public Response allAtletas() {
        return Response.status(Response.Status.OK).entity(toDTOsAtleta(atletaBean.all())).build();
    }

    @POST
    @Path("/atletas")
    public Response createNewAtleta(AtletaDTO atletaDTO) throws MyEntityExistsException {
        atletaBean.create(
                atletaDTO.getUsername(),
                atletaDTO.getPassword(),
                atletaDTO.getName(),
                atletaDTO.getEmail());

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/atletas/{username}")
    public Response getAtletasDetails(@PathParam("username") String username) {
        Atleta atleta = atletaBean.findAtleta(username);

        return Response.status(Response.Status.OK).entity(toDTO(atleta)).build();
    }
    //**********************************Treinador**********************************

    // Converts an entity to a DTO class
    TreinadorDTO toDTO(Treinador treinador) {
        return new TreinadorDTO(
                treinador.getIdSocio(),
                treinador.getUsername(),
                treinador.getPassword(),
                treinador.getName(),
                treinador.getEmail()
        );
    }

    // converts an entire list of entities into a list of DTOs
    List<TreinadorDTO> toDTOsTreinador(List<Treinador> treinadores) {
        return treinadores.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/treinadores") // means: the relative url path is “/api/students/”
    public Response allTreinadores() {
        return Response.status(Response.Status.OK).entity(toDTOsTreinador(treinadorBean.all())).build();
    }

    @POST
    @Path("/treinadores")
    public Response createNewTreinador(TreinadorDTO treinadorDTO) throws MyEntityExistsException {
        treinadorBean.create(
                treinadorDTO.getUsername(),
                treinadorDTO.getPassword(),
                treinadorDTO.getName(),
                treinadorDTO.getEmail());

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/treinadores/{username}")
    public Response getTreinadoresDetails(@PathParam("username") String username) {
        Treinador treinador = treinadorBean.findTreinador(username);

        return Response.status(Response.Status.OK).entity(toDTO(treinador)).build();
    }
}
