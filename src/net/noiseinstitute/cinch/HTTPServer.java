package net.noiseinstitute.cinch;

import java.io.IOException;
import java.net.*;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;

public class HTTPServer extends SelectableChannel {

    private ServerSocket socket;
    private ServerSocketChannel channel;
    private Socket client;

    public HTTPServer () throws IOException {
        socket = new ServerSocket();
        channel = socket.getChannel();
    }

    public HTTPServer (InetAddress address, int port) throws IOException {
        this();
        bind(new InetSocketAddress(address, port));
    }

    public void bind (SocketAddress address) throws IOException {
        socket.bind(address);
    }

    public HTTPSession accept () throws IOException {
        HTTPRequest request;
        do {
            if (client.isClosed()) {
                client = socket.accept();
            }
            request = HTTPRequest.parseRequest(client.getInputStream());
            if (request == null) {
                client.close();
            }
        } while (request == null);

        HTTPResponse response = new HTTPResponse(client.getOutputStream());

        return new HTTPSession(request, response);
    }

    @Override
    protected void implCloseChannel () throws IOException {
        channel.close();
    }

    @Override
    public SelectorProvider provider () {
        return channel.provider();
    }

    @Override
    public int validOps () {
        return channel.validOps();
    }

    @Override
    public boolean isRegistered () {
        return channel.isRegistered();
    }

    @Override
    public SelectionKey keyFor (Selector sel) {
        return channel.keyFor(sel);
    }

    @Override
    public SelectionKey register (Selector sel, int ops, Object att) throws ClosedChannelException {
        return channel.register(sel, ops, att);
    }

    @Override
    public SelectableChannel configureBlocking (boolean block) throws IOException {
        channel.configureBlocking(block);
        return this;
    }

    @Override
    public boolean isBlocking () {
        return channel.isBlocking();
    }

    @Override
    public Object blockingLock () {
        return channel.blockingLock();
    }

}
