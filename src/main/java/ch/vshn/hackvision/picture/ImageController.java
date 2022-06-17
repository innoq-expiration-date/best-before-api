package ch.vshn.hackvision.picture;

import ch.vshn.hackvision.processing.DateExtractionResult;
import ch.vshn.hackvision.processing.TextExtractor;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.reactive.MultipartForm;

@Path("/images")
@ApplicationScoped
public class ImageController {

    @Inject
    private TextExtractor textExtractor;

    @POST
    @Path("/extract-date")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response extractDate(@MultipartForm MultipartBody multipartBody) throws IOException {
        DateExtractionResult result = textExtractor.extractTextAndDates(multipartBody.image);
        return Response.ok(result).build();
    }

}
