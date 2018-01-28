package javache;

import javache.http.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RequestHandler {
    private HttpRequest httpRequest;

    private HttpResponse httpResponse;

    public RequestHandler() { }

    public byte[] handleRequest(String requestContent) {
        this.httpRequest = new HttpRequestImpl(requestContent);
        this.httpResponse = new HttpResponseImpl();

        if(this.httpRequest.isResource()){
            try {
               byte[] fileContents = Files.readAllBytes(
                        Paths.get(
                                System.getProperty("user.dir")
                                    +"\\src\\resources\\assets"
                                    +this.httpRequest.getRequestUrl()
                        )
                );
               return this.Ok(fileContents);
            } catch (IOException e) {
               return this.BadRequest(new byte[0]);
            }
        }else {
            return this.Ok("<h1>Uppsss i am not a resource </h1>".getBytes());
        }

    }

    private byte[] Ok(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.Ok);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] BadRequest(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.BadRequest);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] NotFound(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.NotFound);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] Redirect(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.SeeOther);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] InternalServerError(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.InternalServerError);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }
}
