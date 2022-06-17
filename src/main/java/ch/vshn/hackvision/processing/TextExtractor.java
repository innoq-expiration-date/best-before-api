package ch.vshn.hackvision.processing;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TextExtractor {

    private ImageAnnotatorClient client;

    public TextExtractor() throws IOException {
        client = ImageAnnotatorClient.create();
    }

    // Detects text in the specified image.
    public DateExtractionResult extractTextAndDates(File filePath) throws IOException {

        DateExtractionResult result = new DateExtractionResult();
        ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

        List<AnnotateImageRequest> requests = new ArrayList<>();
        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        BatchAnnotateImagesResponse batchResponse = client.batchAnnotateImages(requests);
        List<AnnotateImageResponse> responses = batchResponse.getResponsesList();

        if (responses.size() == 1) {
            AnnotateImageResponse response = responses.get(0);
            if (response.hasError()) {
                result.errorMessage = response.getError().getMessage();
                return result;
            }
            result.candidateFound = true;
            result.textContents = response.getFullTextAnnotation().getText();
            // TODO extract dates from full text or individual text fragements
        } else {
            // TODO handle unexpected results (0 response, more than 1 response)
        }

        return result;
    }
}
