package net.noiseinstitute.cinch;

import java.io.OutputStream;

public class HTTPResponse {

    private OutputStream outputStream;

    public HTTPResponse (OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public OutputStream getOutputStream () {
        // TODO: This method should only be for writing the response body.
        // Helpers should handle headers, etc.
        return outputStream;
    }

}
