package ws;

import dtos.ProductDTO;
import ejbs.ProductBean;
import entities.Product;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
public class ProductController {
    @EJB
    private ProductBean productBean;

    ProductDTO toDTO(Product product) {
        return null;
    }

    List<ProductDTO> toDTOs(List<Product> products) {
        return products.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public Response all() {
        return Response.status(Response.Status.OK).entity(toDTOs(productBean.all())).build();
    }
}
