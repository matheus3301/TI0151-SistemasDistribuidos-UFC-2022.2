package client;

import enums.Operations;
import exceptions.InvalidResponseException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import messages.CalculusRequest;
import messages.CalculusResponse;
import util.ConversionUtil;

import java.io.IOException;
import java.net.*;

@Slf4j
@Data
@Builder
@AllArgsConstructor
public class CalculatorClient {

    private final DatagramSocket socket;
    private final int serverPort;
    private final byte[] buffer;
    private final InetAddress serverAddress;

    public CalculatorClient(String serverAddress, int serverPort) throws UnknownHostException, SocketException {
        this.socket = new DatagramSocket();
        this.serverPort = serverPort;
        this.buffer = new byte[1024];
        this.serverAddress = InetAddress.getByName(serverAddress);
    }

    public CalculusResponse calculate(double a, double b, Operations operation, int precision) throws IOException, InvalidResponseException{
        CalculusRequest.CalculusRequestBody requestBody = new CalculusRequest.CalculusRequestBody();
        requestBody.setA(a);
        requestBody.setB(b);
        requestBody.setOperation(operation);
        requestBody.setPrecision(precision);
        CalculusRequest request = new CalculusRequest();
        request.setBody(requestBody);

        DatagramPacket sendPacket = new DatagramPacket(request.toString().getBytes(), request.toString().length(), serverAddress, serverPort);
        log.info("sending packet to the server");
        socket.send(sendPacket);
        log.info("waiting for server response");
        DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(responsePacket);
        String rawResponse = new String(responsePacket.getData(), 0, responsePacket.getLength());

        return ConversionUtil.stringToResponse(rawResponse);
    }

    public CalculusResponse calculate(double a, double b, Operations operation) throws IOException, InvalidResponseException {
        return this.calculate(a, b, operation, 2);
    }

}
