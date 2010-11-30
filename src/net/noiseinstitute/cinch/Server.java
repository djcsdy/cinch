package net.noiseinstitute.cinch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

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

    public Server (int port) {
        this(DEFAULT_ADDRESS, port);
    }

    public Server (InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public void start () throws IOException {
        socket = new ServerSocket(port, 0, address);
    }

    public Socket awaitRequest () throws IOException {
        return socket.accept();
    }

}
