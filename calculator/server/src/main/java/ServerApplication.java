import exceptions.DivisionByZeroException;
import lombok.extern.slf4j.Slf4j;
import messages.CalculusErrorResponse;
import messages.CalculusRequest;
import messages.CalculusSuccessResponse;
import server.CalculatorServer;

import java.io.IOException;
import java.net.SocketException;

@Slf4j
public class ServerApplication {
    private static int PORT = 6666;

    // TODO: refatorar processamento de operações (Criar uma classe calculadora)

    public static void main(String[] args) {
        log.info("starting application");
        try{

            CalculatorServer calculatorServer = new CalculatorServer(PORT);
            log.info("server is running on port {}", PORT);

            while(true){
                CalculusRequest request = calculatorServer.getRequest();
                if(request == null) continue;

                double answer = 0;
                double a = request.getBody().getA();
                double b = request.getBody().getB();
                try{
                    switch (request.getBody().getOperation()) {
                        case SUM:
                            answer = a + b;
                            break;
                        case SUB:
                            answer = a - b;
                            break;
                        case MUL:
                            answer = a * b;
                            break;
                        case DIV:
                            if (b == 0) throw new DivisionByZeroException();
                            answer = a / b;
                    }

                    log.info("calculated response {} for client {}",answer, request.getAddress());

                    CalculusSuccessResponse response = new CalculusSuccessResponse();
                    response.setAnswer(answer);
                    response.setPrecision(request.getBody().getPrecision());
                    response.setAddress(request.getAddress());
                    response.setPort(request.getPort());

                    calculatorServer.sendMessage(response);

                }catch (DivisionByZeroException exception){
                    log.error("division by zero requested by the client {}", request.getAddress());

                    CalculusErrorResponse response = new CalculusErrorResponse();
                    response.setMessage("Division by zero");
                    response.setAddress(request.getAddress());
                    response.setPort(request.getPort());

                    calculatorServer.sendMessage(response);
                }
            }
        }catch (SocketException exception){
            log.error("unable to start server, port is already in use");
        } catch (IOException e) {
            log.error("error on handling requests");
        }
    }
}
