package ws;

import dtos.EscalaoDTO;
import dtos.ModalidadeDTO;
import ejbs.ModalidadeBean;
import entities.Escalao;
import entities.Modalidade;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/modalidades")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON})
public class ModalidadeController {
    @EJB
    private ModalidadeBean modalidadeBean;
//----------TODO DTO sin listas
    ModalidadeDTO toDTONoSocios(Modalidade modalidade){
        return new ModalidadeDTO(modalidade.getNome());
    }
    List<ModalidadeDTO> toDTOsNoSocios(List<Modalidade> modalidades){
        return modalidades.stream().map(this::toDTONoSocios).collect(Collectors.toList());
    }
//-------------TODO DTO CON LISTAS ---------------
    EscalaoDTO toEscalaoDTO(Escalao escalao){
        return new EscalaoDTO(
                escalao.getName(),escalao.getCode(),escalao.getModalidade().getNome()
        );
    }
    Set<EscalaoDTO> toEscalaoDTOs(Set<Escalao> escaloes){
        return escaloes.stream().map(this::toEscalaoDTO).collect(Collectors.toSet());
    }

    ModalidadeDTO toDTO(Modalidade modalidade){
        ModalidadeDTO modalidadeDTO= new ModalidadeDTO(modalidade.getNome());
        modalidadeDTO.setEscaloes(toEscalaoDTOs(modalidade.getEscaloes()));
        return modalidadeDTO;
    }
    List<ModalidadeDTO> toDTOs(List<Modalidade> modalidades){
        return modalidades.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the verb get
    @Path("/") // means: the relative url path is “/api/students/”
    public List<ModalidadeDTO> all() {
        try {
            return toDTOsNoSocios(modalidadeBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_MODALIDADE", e);
        }
    }
    //CREAR
    @POST
    @Path("/")
    public Response createNewModalidade (ModalidadeDTO modalidadeDTO) throws MyEntityExistsException, MyEntityNotFoundException, Exception{
        modalidadeBean.create(modalidadeDTO.getNome());
        return Response.status(Response.Status.CREATED).build();
    }
    //GET por nome
    @GET
    @Path("{nome}")
    public Response getModalidadeDetails(@PathParam("nome") String nome) throws MyEntityNotFoundException,Exception{
        Modalidade modalidade = modalidadeBean.findModalidade(nome);
        return Response.status(Response.Status.OK)
                .entity(toDTO(modalidade))
                .build();
    }
    @PUT
    @Path("{name}")
    public Response update(@PathParam("name") String name,String newNome) throws MyEntityNotFoundException,Exception{
       modalidadeBean.update(name,newNome);
        return Response.status(Response.Status.OK).entity(toDTONoSocios(modalidadeBean.findModalidade(name))).build();
    }
    @DELETE
    @Path("{name}")
    public Response delete(@PathParam("name") String name) throws MyEntityNotFoundException,Exception{
        modalidadeBean.delete(name);
        return Response.status(Response.Status.OK).build();
    }
/*
    @DELETE
    @Path("{name}")
    public Response delete(@PathParam("name") String name) throws MyEntityNotFoundException,Exception{
        modalidadeBean.update(name);
        return Response.status(Response.Status.OK).entity(toDTONoSocios(modalidadeBean.findModalidade(name))).build();
    }
*/


}
