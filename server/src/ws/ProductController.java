package ws;

import dtos.ProductDTO;
import ejbs.ProductBean;
import entities.Product;
import entities.ProductType;
import exceptions.MyConstraintViolationException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
public class ProductController {
    @EJB
    private ProductBean productBean;

    ProductDTO toDTO (Product product) {
        return null;
    }

    List<ProductDTO> toDTOs (List<Product> products) {
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public Response all () {
        return Response.status(Response.Status.OK).entity(toDTOs(productBean.all())).build();
    }

    @POST
    @Path("/")
    public Response create (ProductDTO productDTO) throws MyConstraintViolationException {
        productBean.create(
                productDTO.getCode(),
                productDTO.getType(),
                productDTO.getDescription(),
                productDTO.getValue()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("{code}/details")
    public Response getDetails (@PathParam("code") int code) throws MyConstraintViolationException {
        Product product = productBean.find(code);

        return Response.status(Response.Status.OK).entity(toDTO(product)).build();
    }

    @PUT
    @Path("{code}")
    public Response update (@PathParam("code") int code, @FormParam("type") ProductType type, @FormParam("description") String description, @FormParam("value") double value) throws MyConstraintViolationException {
        Product product = productBean.update(code, type, description, value);

        return Response.status(Response.Status.OK).entity(toDTO(product)).build();
    }

    @DELETE
    @Path("/")
    public Response delete (@FormParam("code") int code) throws MyConstraintViolationException {
        Product product = productBean.delete(code);

        return Response.status(Response.Status.OK).entity(toDTO(product)).build();
    }
}
