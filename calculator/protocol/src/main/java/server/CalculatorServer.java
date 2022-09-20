package server;


import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import messages.CalculusRequest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

@Slf4j
public class CalculatorServer {
    private final DatagramSocket socket;
    private byte[] buffer;

    public CalculatorServer(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
        this.buffer = new byte[1024];
    }

    public CalculusRequest getRequest() throws IOException {
        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
        socket.receive(request);
        String rawRequest = new String(request.getData(), 0, request.getLength());

        log.info("new request from {}", request.getAddress().getHostAddress());
        log.info("{}", rawRequest);

        return null;
    }

    public void close(){
        this.socket.close();
    }
}
