package ws;

import dtos.ProductDTO;
import ejbs.ProductBean;
import entities.Product;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;
import exceptions.MyIllegalArgumentException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {
    @EJB
    private ProductBean productBean;

    ProductDTO toDTO (Product product) {
        return new ProductDTO(
                product.getCode(),
                product.getType(),
                product.getDescription(),
                product.getValue()
        );
    }

    List<ProductDTO> toDTOs (List<Product> products) {
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/all")
    public Response all () {
        return Response.status(Response.Status.OK).entity(toDTOs(productBean.all())).build();
    }

    @GET
    @Path("/")
    public Response notDeleted () {
        return Response.status(Response.Status.OK).entity(toDTOs(productBean.notDeleted())).build();
    }

    @POST
    @Path("/")
    public Response create (ProductDTO productDTO) throws MyEntityExistsException {
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
    public Response getDetails (@PathParam("code") int code) {
        Product product = productBean.find(code);

        return Response.status(Response.Status.OK).entity(toDTO(product)).build();
    }

    @PUT
    @Path("{code}")
    public Response update (@PathParam("code") int code, ProductDTO productDTO) throws MyEntityNotFoundException, MyIllegalArgumentException {
        Product product = productBean.update(code, productDTO.getType(), productDTO.getDescription(), productDTO.getValue());

        return Response.status(Response.Status.OK).entity(toDTO(product)).build();
    }

    @DELETE
    @Path("{code}/delete")
    public Response delete (@PathParam("code") int code) throws MyEntityNotFoundException {
        Product product = productBean.delete(code);

        return Response.status(Response.Status.OK).entity(toDTO(product)).build();
    }
}
