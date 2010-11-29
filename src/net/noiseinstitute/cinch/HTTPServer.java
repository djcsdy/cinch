package net.noiseinstitute.cinch;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class HTTPServer {

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

	public HTTPSession awaitRequest () throws IOException {
		Socket client;
		HTTPRequest request;
		do {
			client = server.awaitRequest();
			request = HTTPRequest.parseRequest(client.getInputStream());
		} while (request == null);

		HTTPResponse response = new HTTPResponse(client.getOutputStream());

		return new HTTPSession(request, response);
	}

}
