package ws;

import dtos.AdministradorDTO;
import dtos.AtletaDTO;
import dtos.SocioDTO;
import dtos.TreinadorDTO;
import ejbs.AdministradorBean;
import ejbs.AtletaBean;
import ejbs.SocioBean;
import ejbs.TreinadorBean;
import entities.Administrador;
import entities.Atleta;
import entities.Socio;
import entities.Treinador;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

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
    @EJB
    private SocioBean socioBean;


    // Converts an entity to a DTO class
    SocioDTO toDTO(Socio socio) {
        return new SocioDTO(
                socio.getIdSocio(),
                socio.getPassword(),
                socio.getName(),
                socio.getEmail(),
                socio.getUsername(),
                socio.getTipo()
        );
    }

    // converts an entire list of entities into a list of DTOs
    List<SocioDTO> toDTOs(List<Socio> socios) {
        return socios.stream().map(this::toDTO).collect(Collectors.toList());
    }


    @GET // means: to call this endpoint, we need to use the HTTP GET method
    @Path("/") // means: the relative url path is “/api/users/”
    public Response allSocios() {
        return Response.status(Response.Status.OK).entity(toDTOs(socioBean.all())).build();
    }

    @POST
    @Path("/")
    public Response createNewSocio(SocioDTO socioDTO) throws MyEntityExistsException {
        socioBean.create(
                socioDTO.getUsername(),
                socioDTO.getPassword(),
                socioDTO.getName(),
                socioDTO.getEmail());

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{username}")
    public Response updateSocio(@PathParam("username") String username,SocioDTO socioDTO) throws MyEntityExistsException, MyEntityNotFoundException {

        socioBean.update(
                username,
                socioDTO.getName(),
                socioDTO.getEmail());

        return Response.status(Response.Status.ACCEPTED).build();
    }

    @DELETE
    @Path("/{username}")
    public Response removeSocio(@PathParam("username") String username) throws MyEntityExistsException, MyEntityNotFoundException {

        if(socioBean.remove(username)){
            return Response.status(Response.Status.OK).build();
        }
        return  Response.status(Response.Status.EXPECTATION_FAILED).build();

    }
//********************Administrador *****************
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

    /*@PUT
    @Path("/administradores/{username}")
    public Response updateAdministrador(@PathParam("username") String username,AdministradorDTO administradorDTO) throws MyEntityExistsException, MyEntityNotFoundException {

        administradorBean.update(
                username,
                administradorDTO.getName(),
                administradorDTO.getEmail());

        return Response.status(Response.Status.ACCEPTED).build();
    }*/
/*
    @DELETE
    @Path("/administradores/{username}")
    public Response removeAdministrador(@PathParam("username") String username) throws MyEntityExistsException, MyEntityNotFoundException {

        administradorBean.remove(username);

        return Response.status(Response.Status.OK).build();
    }*/

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
   /* @PUT
    @Path("/atletas/{username}")
    public Response updateAtletas(@PathParam("username") String username,AtletaDTO atletaDTO) throws MyEntityExistsException, MyEntityNotFoundException {

        administradorBean.update(
                username,
                atletaDTO.getName(),
                atletaDTO.getEmail());

        return Response.status(Response.Status.ACCEPTED).build();
    }*/
/*
    @DELETE
    @Path("/atletas/{username}")
    public Response removeAtleta(@PathParam("username") String username) throws MyEntityExistsException, MyEntityNotFoundException {

        atletaBean.remove(username);

        return Response.status(Response.Status.OK).build();
    }*/

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

  /*  @PUT
    @Path("/treinadores/{username}")
    public Response updateTreinadores(@PathParam("username") String username,TreinadorDTO treinadorDTO) throws MyEntityExistsException, MyEntityNotFoundException {

        administradorBean.update(
                username,
                treinadorDTO.getName(),
                treinadorDTO.getEmail());

        return Response.status(Response.Status.ACCEPTED).build();
    }*/
/*
    @DELETE
    @Path("/treinadores/{username}")
    public Response removeTreinador(@PathParam("username") String username) throws MyEntityExistsException, MyEntityNotFoundException {

        treinadorBean.remove(username);

        return Response.status(Response.Status.OK).build();
    }*/

}
