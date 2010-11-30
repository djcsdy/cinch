package net.noiseinstitute.cinch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;

public class Server extends SelectableChannel {

    private static final InetAddress DEFAULT_ADDRESS;

    static {
        try {
            DEFAULT_ADDRESS = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
        } catch (UnknownHostException e) {
            throw new IllegalStateException(e);
        }
    }

    private InetAddress address;
    private int port;
    private ServerSocket socket;
    private ServerSocketChannel channel;


    public Server (int port) {
        this(DEFAULT_ADDRESS, port);
    }

    public Server (InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public void start () throws IOException {
        socket = new ServerSocket(port, 0, address);
        channel = socket.getChannel();
    }

    public Socket accept () throws IOException {
        return socket.accept();
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

    @Override
    protected void implCloseChannel () throws IOException {
        channel.close();
    }
    
}
