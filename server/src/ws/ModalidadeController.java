package ws;

import dtos.ModalidadeDTO;
import ejbs.ModalidadeBean;
import entities.Modalidade;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/modalidades")
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON})
public class ModalidadeController {
    @EJB
    private ModalidadeBean modalidadeBean;

    ModalidadeDTO toDTONoSocios(Modalidade modalidade){
        return new ModalidadeDTO(modalidade.getNome());
    }
    List<ModalidadeDTO> toDTOsNoSocios(List<Modalidade> modalidades){
        return modalidades.stream().map(this::toDTONoSocios).collect(Collectors.toList());
    }

    @GET // means: to call this endpoint, we need to use the verb get
    @Path("/") // means: the relative url path is “/api/students/”
    public List<ModalidadeDTO> all() {
        try {
            return toDTOsNoSocios(modalidadeBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_STUDENTS", e);
        }
    }
    @POST
    @Path("/")
    public Response createNewStudent (ModalidadeDTO modalidadeDTO) throws MyEntityExistsException, MyEntityNotFoundException, Exception{
        modalidadeBean.create(modalidadeDTO.getNome());
        return Response.status(Response.Status.CREATED).build();
    }

}
