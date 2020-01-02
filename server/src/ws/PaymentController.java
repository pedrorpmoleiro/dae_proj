package ws;

import dtos.PaymentDTO;
import dtos.ProductDTO;
import ejbs.PaymentBean;
import entities.Payment;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

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
    @Path("/")
    public Response all() {
        return Response.status(Response.Status.OK).entity(toDTOs(paymentBean.all())).build();
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
}
