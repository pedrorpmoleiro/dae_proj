package ws;

import dtos.PaymentDTO;
import ejbs.PaymentBean;
import entities.Payment;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentController {
    @EJB
    private PaymentBean paymentBean;

    PaymentDTO toDTO(Payment payment) {
        return new PaymentDTO(
                payment.getCode(),
                payment.getSocio().getUsername(),
                payment.getProduct().getCode(),
                payment.getDateTime().getTime(),
                payment.getQuantity(),
                payment.getPrice(),
                payment.getStatus(),
                payment.getReceipt()
        );
    }

    List<PaymentDTO> toDTOs(List<Payment> payments) {
        return payments.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/all")
    public Response all() {
        return Response.status(Response.Status.OK).entity(toDTOs(paymentBean.all())).build();
    }

    @GET
    @Path("/")
    public Response notDeleted() {
        return Response.status(Response.Status.OK).entity(toDTOs(paymentBean.notDeleted())).build();
    }

    @POST
    @Path("/")
    public Response create (PaymentDTO paymentDTO) throws MyEntityExistsException, MyEntityNotFoundException {
        paymentBean.create(
                paymentDTO.getCode(),
                paymentDTO.getSocioUsername(),
                paymentDTO.getProductCode(),
                paymentDTO.getTimestamp(),
                paymentDTO.getQuantity(),
                paymentDTO.getPrice(),
                paymentDTO.getStatus(),
                paymentDTO.getReceipt()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{code}")
    public Response update (@PathParam("code") int code, PaymentDTO paymentDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Payment payment = paymentBean.update(code, paymentDTO.getSocioUsername(), paymentDTO.getProductCode(), paymentDTO.getTimestamp(),
                paymentDTO.getQuantity(), paymentDTO.getPrice(), paymentDTO.getStatus(), paymentDTO.getReceipt());

        return Response.status(Response.Status.OK).entity(toDTO(payment)).build();
    }

    @GET
    @Path("{code}/details")
    public Response getDetails (@PathParam("code") int code) {
        Payment payment = paymentBean.find(code);

        return Response.status(Response.Status.OK).entity(toDTO(payment)).build();
    }

    @DELETE
    @Path("{code}/delete")
    public Response delete (@PathParam("code") int code) throws MyEntityNotFoundException
    {
        Payment payment = paymentBean.delete(code);

        return Response.status(Response.Status.OK).entity(toDTO(payment)).build();
    }
}
