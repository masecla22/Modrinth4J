package masecla.modrinth4j.endpoints.images;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import masecla.modrinth4j.client.HttpClient;
import masecla.modrinth4j.model.image.ModrinthImage;

/**
 * Endpoints for uploading images.
 */
@AllArgsConstructor
public class ImagesEndpoints {
    /** The Gson instance to use */
    private Gson gson;
    /** The HTTP client to use */
    private HttpClient client;

    /**
     * Uploads an image with context.
     * 
     * @param file      - The image file to upload
     * @param ext       - The file extension
     * @param context   - The context for the image (e.g., "project", "version")
     * @param contextId - Optional context ID (project_id, version_id, etc.)
     * @return A {@link CompletableFuture} that will return the uploaded image
     * @throws FileNotFoundException if the file is not found
     */
    public CompletableFuture<ModrinthImage> upload(File file, String ext, String context, String contextId)
            throws FileNotFoundException {
        return upload(new FileInputStream(file), file.getName(), ext, context, contextId);
    }

    /**
     * Uploads an image with context.
     * 
     * @param stream    - The image stream
     * @param fileName  - The file name
     * @param ext       - The file extension
     * @param context   - The context for the image (e.g., "project", "version")
     * @param contextId - Optional context ID (project_id, version_id, etc.)
     * @return A {@link CompletableFuture} that will return the uploaded image
     */
    public CompletableFuture<ModrinthImage> upload(InputStream stream, String fileName, String ext, String context,
            String contextId) {
        return new UploadImage(client, gson)
                .sendRequest(new UploadImage.UploadImageRequest(stream, fileName, ext, context, contextId));
    }
}
