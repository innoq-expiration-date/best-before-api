package ch.vshn.hackvision.picture;

import org.jboss.resteasy.reactive.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.File;


public class MultipartBody {

    @FormParam("image")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    public File image;

    @FormParam("fileName")
    @PartType(MediaType.TEXT_PLAIN)
    public String fileName;
}



