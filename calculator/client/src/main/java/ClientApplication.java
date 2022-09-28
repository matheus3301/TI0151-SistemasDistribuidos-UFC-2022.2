import client.CalculatorClient;
import enums.Operations;
import exceptions.InvalidResponseException;
import lombok.extern.slf4j.Slf4j;
import messages.CalculusErrorResponse;
import messages.CalculusResponse;
import messages.CalculusSuccessResponse;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

@Slf4j
public class ClientApplication {
    private static final int PORT = 6666;
    private static final String HOST = "localhost";
    private static CalculatorClient calculatorClient;

    public static void main(String[] args) {
        try {
            calculatorClient = new CalculatorClient(HOST, PORT);
            log.info("conectado ao servidor!");
        } catch (UnknownHostException e) {
            log.error("host desconhecido");
        } catch (SocketException e) {
            log.error("porta desconhecida");
        }

        testaConta(1, 1, Operations.SUM);
        testaConta(3, 6, Operations.MUL);
        testaConta(4, 5, Operations.SUB);
        testaConta(18, 0, Operations.DIV);
    }

    private static void testaConta(double a, double b, Operations operation) {
        try {
            CalculusResponse response = calculatorClient.calculate(a, b, operation, 4);
            if (response instanceof CalculusSuccessResponse) {
                CalculusSuccessResponse successResponse = (CalculusSuccessResponse) response;
                log.info("calculou {}", successResponse.getAnswer());

            } else if (response instanceof CalculusErrorResponse) {
                CalculusErrorResponse errorResponse = (CalculusErrorResponse) response;
                log.error("erro ao calcular: {}", errorResponse.getMessage());
            }
        } catch (IOException e) {
            log.error("erro no socket");
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            log.error("resposta inválida vinda do servidor");
            e.printStackTrace();

        }
    }
}
