package net.noiseinstitute.cinch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;

public class HTTPServer extends SelectableChannel {

    private static final int DEFAULT_PORT = 80;

    private Server server;

    public HTTPServer () {
        this(DEFAULT_PORT);
    }

    public HTTPServer (int port) {
        server = new Server(port);
    }

    public HTTPServer (InetAddress address) {
        this(address, DEFAULT_PORT);
    }

    public HTTPServer (InetAddress address, int port) {
        server = new Server(address, port);
    }

    public HTTPSession accept () throws IOException {
        Socket client;
        HTTPRequest request;
        do {
            client = server.accept();
            request = HTTPRequest.parseRequest(client.getInputStream());
        } while (request == null);

        HTTPResponse response = new HTTPResponse(client.getOutputStream());

        return new HTTPSession(request, response);
    }

    @Override
    protected void implCloseChannel () throws IOException {
        server.close();
    }

    @Override
    public SelectorProvider provider () {
        return server.provider();
    }

    @Override
    public int validOps () {
        return server.validOps();
    }

    @Override
    public boolean isRegistered () {
        return server.isRegistered();
    }

    @Override
    public SelectionKey keyFor (Selector sel) {
        return server.keyFor(sel);
    }

    @Override
    public SelectionKey register (Selector sel, int ops, Object att) throws ClosedChannelException {
        return server.register(sel, ops, att);
    }

    @Override
    public SelectableChannel configureBlocking (boolean block) throws IOException {
        server.configureBlocking(block);
        return this;
    }

    @Override
    public boolean isBlocking () {
        return server.isBlocking();
    }

    @Override
    public Object blockingLock () {
        return server.blockingLock();
    }

}
