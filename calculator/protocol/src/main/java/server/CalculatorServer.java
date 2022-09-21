package server;


import exceptions.InvalidRequestException;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import messages.CalculusErrorResponse;
import messages.CalculusRequest;
import messages.CalculusResponse;
import util.ConversionUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

@Slf4j
public class CalculatorServer {
    private final DatagramSocket socket;
    private final byte[] buffer;

    public CalculatorServer(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
        this.buffer = new byte[1024];
    }

    public CalculusRequest getRequest() throws IOException{
        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
        socket.receive(request);
        String rawRequest = new String(request.getData(), 0, request.getLength());


        log.info("new request from {}", request.getAddress().getHostAddress());
        log.info("{}", rawRequest);

        CalculusRequest calculusRequest = new CalculusRequest();
        try{
            calculusRequest.setBody(
                    ConversionUtil.StringToObject(rawRequest)
            );
        }catch (InvalidRequestException exception){
            log.error("invalid message from {}", request.getAddress().getHostAddress());

            CalculusErrorResponse calculusErrorResponse = new CalculusErrorResponse();

            calculusErrorResponse.setAddress(request.getAddress());
            calculusErrorResponse.setPort(request.getPort());
            calculusErrorResponse.setMessage("Invalid parameters!");

            this.sendMessage(calculusErrorResponse);

            return null;
        }

        calculusRequest.setAddress(
                request.getAddress()
        );

        calculusRequest.setPort(
                request.getPort()
        );

        return calculusRequest;
    }

    public void sendMessage(CalculusResponse calculusResponse){

        DatagramPacket response = new DatagramPacket(
                calculusResponse.toString().getBytes(),
                calculusResponse.toString().length(),
                calculusResponse.getAddress(),
                calculusResponse.getPort()
        );

        try{
            this.socket.send(response);
        }catch (IOException ignored){

        }
    }

    public void close(){
        this.socket.close();
    }
}
