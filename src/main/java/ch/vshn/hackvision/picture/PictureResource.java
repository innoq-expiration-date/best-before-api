package ch.vshn.hackvision.picture;

import io.quarkus.hibernate.reactive.panache.Panache;

import io.smallrye.mutiny.Uni;
import org.apache.commons.lang3.ArrayUtils;
import org.jboss.resteasy.reactive.MultipartForm;
import org.jboss.resteasy.reactive.RestPath;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Path("/picture")
@ApplicationScoped
public class PictureResource {

    public static final String MD_5 = "MD5";

    @Inject



    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response create(@MultipartForm MultipartBody multipartBody) throws IOException, NoSuchAlgorithmException {
        MessageDigest md5Digest = MessageDigest.getInstance(MD_5);
        Picture picture = new Picture();

        byte[] bytesOfImage = Files.readAllBytes(multipartBody.image.toPath());

        picture.hash = String.valueOf(md5Digest.digest(bytesOfImage));
        picture.image = ArrayUtils.toObject(bytesOfImage);



        return Response.accepted().build();
    }


}
