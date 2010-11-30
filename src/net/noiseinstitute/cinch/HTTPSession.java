package net.noiseinstitute.cinch;

public class HTTPSession {

    private HTTPRequest request;
    private HTTPResponse response;

    public HTTPSession (HTTPRequest request, HTTPResponse response) {
        this.request = request;
        this.response = response;
    }

    public HTTPRequest getRequest () {
        return request;
    }

    public HTTPResponse getResponse () {
        return response;
    }

}
