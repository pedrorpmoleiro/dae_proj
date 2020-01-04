package ws;

import dtos.*;
import ejbs.EpocaBean;
import ejbs.EscalaoBean;
import ejbs.ModalidadeBean;
import entities.*;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
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
    @EJB
    private EpocaBean epocaBean;
    @EJB
    private EscalaoBean escalaoBean;
//region TODO DTO sin listas
    //Modalidades
    ModalidadeDTO toModalidadeDTONoEscaloes(Modalidade modalidade){
        return new ModalidadeDTO(modalidade.getNome());
    }
    Set<ModalidadeDTO> toModalidadeDTOsNoEscaloes(Set<Modalidade> modalidades){
        return modalidades.stream().map(this::toModalidadeDTONoEscaloes).collect(Collectors.toSet());
    }
    //region ESCALAO
    EscalaoDTO toEscalaoDTONoSocios(Escalao escalao){
        return new EscalaoDTO(escalao.getName());
    }
    Set<EscalaoDTO> toEscalaoDTOsNoSocios(Set<Escalao> escaloes){
        return escaloes.stream().map(this::toEscalaoDTONoSocios).collect(Collectors.toSet());
    }
    //endregion
//endregion
//region TODO DTO CON LISTAS

    //region MODALIDADE
    ModalidadeDTO toModalidadeDTOSinSocios(Modalidade modalidade){
        ModalidadeDTO modalidadeDTO= new ModalidadeDTO(modalidade.getNome());
        modalidadeDTO.setEscaloes(toEscalaoDTOsNoSocios(modalidade.getEscaloes()));
        return modalidadeDTO;
    }
    Set<ModalidadeDTO> toModalidadeDTOsSinSocios(Set<Modalidade> modalidades){
        return modalidades.stream().map(this::toModalidadeDTOSinSocios).collect(Collectors.toSet());
    }
    //endregion
    //region EPOCA
    EpocaDTO toEpocaDTO(Epoca epoca){
        EpocaDTO epocaDTO=new EpocaDTO(epoca.getNome());
                epocaDTO.setModalidades(toModalidadeDTOsSinSocios(epoca.getModalidades()));
        return  epocaDTO;
    }
    List<EpocaDTO> toEpocaDTOs(List<Epoca> epocas){
        return epocas.stream().map(this::toEpocaDTO).collect(Collectors.toList());
    }
    //endregion
    //region ESCALAO
    EscalaoDTO toEscalaoDTOConSocios(Escalao escalao){
        EscalaoDTO escalaoDTO= new EscalaoDTO(escalao.getName());
        escalaoDTO.setAtletas(toAtletaDTOs(escalao.getAtletas()));
        escalaoDTO.setTreinadores(toTreinadorDTOs(escalao.getTreinadores()));
        return  escalaoDTO;
    }
    //endregion
    //region ATLETA
    AtletaDTO toAtletaDTO(Atleta atleta){
        return  new AtletaDTO(atleta.getIdSocio(),atleta.getUsername(),atleta.getPassword(),atleta.getName(),atleta.getEmail());
    }
    Set<AtletaDTO> toAtletaDTOs(Set<Atleta> atletas){
        return atletas.stream().map(this::toAtletaDTO).collect(Collectors.toSet());
    }
    //endregion
    //region ATLETA
    TreinadorDTO toTreinadorDTO(Treinador treinador){
        return  new TreinadorDTO(treinador.getIdSocio(),treinador.getUsername(),treinador.getPassword(),treinador.getName(),treinador.getEmail());
    }
    Set<TreinadorDTO> toTreinadorDTOs(Set<Treinador> treinadores){
        return treinadores.stream().map(this::toTreinadorDTO).collect(Collectors.toSet());
    }
    //endregion
//endregion
//region LISTAR GET
    @GET // means: to call this endpoint, we need to use the verb get
    @Path("/") // means: the relative url path is “/api/students/”
    public List<EpocaDTO> all() {
        try {
            return toEpocaDTOs(epocaBean.all());
        } catch (Exception e) {
            throw new EJBException("ERROR_GET_EPOCA", e);
        }
    }
    @GET
    @Path("{epoca}/{nome}")
    public Response getModalidadeEscaloesSinSocios(@PathParam("epoca") String epoca,@PathParam("nome") String nome) throws MyEntityNotFoundException,Exception{
        Modalidade modalidade = modalidadeBean.findModalidade(epoca,nome);
        GenericEntity<Set<EscalaoDTO>> entity
                = new GenericEntity<Set<EscalaoDTO>>(toEscalaoDTOsNoSocios(modalidade.getEscaloes())) {
        };
        return Response.status(Response.Status.OK)
                .entity(entity)
                .build();
    }
    @GET
    @Path("{epoca}/{modalidade}/{escalao}/socios")
    public Response getModalidadeEscalaoSocios(@PathParam("epoca") String epoca,@PathParam("modalidade") String modalidade,@PathParam("escalao") String escalao) throws MyEntityNotFoundException,Exception{
        Escalao escalao1 = escalaoBean.findEscalao(epoca,modalidade,escalao);
        return  Response.status(Response.Status.OK)
                .entity(toEscalaoDTOConSocios(escalao1))
                .build();
    }
    @GET
    @Path("{epoca}/{modalidade}/{escalao}")
    public Response getEscalaoSocios(@PathParam("epoca") String epoca,@PathParam("modalidade") String modalidade,@PathParam("escalao") String escalao) throws MyEntityNotFoundException,Exception{
        Escalao escalao1 = escalaoBean.findEscalao(epoca,modalidade,escalao);
        return  Response.status(Response.Status.OK)
                .entity(toEscalaoDTONoSocios(escalao1))
                .build();
    }

//endregion
//region CREAR
    //TODO crear modalidad
    @POST
    @Path("/{epoca}")
    public Response createNewModalidade (@PathParam("epoca") String epoca,ModalidadeDTO modalidadeDTO) throws MyEntityExistsException, MyEntityNotFoundException, Exception{
        modalidadeBean.create(modalidadeDTO.getNome(),epoca);
        return Response.status(Response.Status.CREATED).build();
    }
    //TODO crear epoca
    @POST
    @Path("/epoca")
    public Response createNewEpoca (EpocaDTO epocaDTO) throws MyEntityExistsException, MyEntityNotFoundException, Exception{
        epocaBean.create(epocaDTO.getNome());
        return Response.status(Response.Status.CREATED).build();
    }
    //TODO crear escalao
    @POST
    @Path("/{epoca}/{modalidade}")
    public Response createNewEscalao (@PathParam("epoca") String epoca,@PathParam("modalidade") String modalidade,EscalaoDTO escalaoDTO) throws MyEntityExistsException, MyEntityNotFoundException, Exception{
        escalaoBean.create(escalaoDTO.getName(),modalidade,epoca);

        Modalidade modalidade1 = modalidadeBean.findModalidade(epoca,modalidade);
        GenericEntity<Set<EscalaoDTO>> entity
                = new GenericEntity<Set<EscalaoDTO>>(toEscalaoDTOsNoSocios(modalidade1.getEscaloes())) {
        };
        return Response.status(Response.Status.OK)
                .entity(entity)
                .build();
    }
    //TODO crear socio
    @POST
    @Path("/{epoca}/{modalidade}/{escalao}/{username}/atleta")
    public Response inscreverNewAtleta (@PathParam("epoca") String epoca,@PathParam("modalidade") String modalidade,@PathParam("escalao") String escalao,@PathParam("username") String username) throws MyEntityExistsException, MyEntityNotFoundException, Exception{
        modalidadeBean.enrollAtletaEscalao(escalao,username,epoca,modalidade);
        Escalao escalao1 = escalaoBean.findEscalao(epoca,modalidade,escalao);
        return  Response.status(Response.Status.OK)
                .entity(toEscalaoDTOConSocios(escalao1))
                .build();
    }
    @POST
    @Path("/{epoca}/{modalidade}/{escalao}/{username}/treinador")
    public Response inscreverNewTreinador(@PathParam("epoca") String epoca,@PathParam("modalidade") String modalidade,@PathParam("escalao") String escalao,@PathParam("username") String username) throws MyEntityExistsException, MyEntityNotFoundException, Exception{
        modalidadeBean.enrollTreinadorEscalao(escalao,username,epoca,modalidade);
        Escalao escalao1 = escalaoBean.findEscalao(epoca,modalidade,escalao);
        return  Response.status(Response.Status.OK)
                .entity(toEscalaoDTOConSocios(escalao1))
                .build();
    }


    //endregion
//region ACTUALIZAR
    @PUT
    @Path("{epoca}/{name}")
    public Response update(@PathParam("epoca") String epoca,@PathParam("name") String name,ModalidadeDTO newModalidade) throws MyEntityNotFoundException,Exception{
        modalidadeBean.update(epoca,name,newModalidade.getNome());
        return Response.status(Response.Status.OK).build();
    }
    @PUT
    @Path("{epoca}/{modalidade}/{escalao}")
    public Response updateEscalao(@PathParam("epoca") String epoca,@PathParam("modalidade") String modalidade,@PathParam("escalao") String escalao,EscalaoDTO newEscalao) throws MyEntityNotFoundException,Exception{
        escalaoBean.updateEscalao(epoca,modalidade,escalao,newEscalao.getName());
        Modalidade modalidade1 = modalidadeBean.findModalidade(epoca,modalidade);
        GenericEntity<Set<EscalaoDTO>> entity
                = new GenericEntity<Set<EscalaoDTO>>(toEscalaoDTOsNoSocios(modalidade1.getEscaloes())) {
        };
        return Response.status(Response.Status.OK)
                .entity(entity)
                .build();
    }
//endregion
//region DELETE
    @DELETE
    @Path("{epoca}/{name}")
    public Response delete(@PathParam("epoca") String epoca,@PathParam("name") String name) throws MyEntityNotFoundException,Exception{
        epocaBean.deleteModalidade(name,epoca);
        return Response.status(Response.Status.OK).build();
    }
//endregion



}
