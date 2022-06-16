package ch.vshn.hackvision.picture;

import io.quarkus.hibernate.reactive.panache.Panache;

import io.smallrye.mutiny.Uni;
import org.apache.commons.lang3.ArrayUtils;
import org.jboss.resteasy.reactive.MultipartForm;
import org.jboss.resteasy.reactive.RestPath;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Path("/pictures")
@ApplicationScoped
public class PictureResource {

    @GET
    public Uni<List<Picture>> get() {
        return Picture.listAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Picture> findById(Long id) {
        return Picture.findById(id);
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Uni<Response> create(@MultipartForm MultipartBody multipartBody) throws IOException, NoSuchAlgorithmException {
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");
        Picture picture = new Picture();

        byte[] bytesOfImage = Files.readAllBytes(multipartBody.image.toPath());

        picture.hash = String.valueOf(md5Digest.digest(bytesOfImage));
        picture.image = ArrayUtils.toObject(bytesOfImage);

        return Panache.<Picture>withTransaction(picture::persist)
                .onItem()
                .transform(inserted ->
                        Response.created(
                                URI.create("/pictures/" + inserted.id)
                        ).build());
    }


    @PUT
    @Path("{id}")
    public Uni<Response> update(@RestPath Long id, Picture picture) {
        if (picture == null || picture.hash == null) {
            throw new WebApplicationException("Picture should have a firstname and a lastname", 422);
        }

        return Panache.withTransaction(() -> Picture.<Picture>findById(id)
                        .onItem().ifNotNull()
                        .invoke(entity -> entity.hash = picture.hash)                        
                .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                .onItem().ifNull().continueWith(Response.ok().status(Response.Status.NOT_FOUND)::build));
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(@RestPath Long id) {
        return Panache.withTransaction(() -> Picture.deleteById(id))
                .map(deleted ->
                        deleted ? Response.ok().status(Response.Status.NO_CONTENT).build()
                                : Response.ok().status(Response.Status.NOT_FOUND).build());
    }
}
