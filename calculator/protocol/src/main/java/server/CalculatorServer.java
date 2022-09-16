package server;


import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.net.DatagramSocket;
import java.net.SocketException;

@Slf4j
public class CalculatorServer {
    private final DatagramSocket socket;

    public CalculatorServer(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
    }

    public void close(){
        this.socket.close();
    }
}
