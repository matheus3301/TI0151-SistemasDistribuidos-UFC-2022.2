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
import java.util.Scanner;

@Slf4j
public class ClientApplication {
    private static final int PORT = 6666;
    private static final String HOST = "localhost";
    private static CalculatorClient calculatorClient;
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Operations operation;
        try{
            calculatorClient = new CalculatorClient(HOST, PORT);
            System.out.println("Socket criado!");
            while(Boolean.TRUE) {
                System.out.println("Digite a operação desejada: (+, -, *, /)");
                String rawOperationInput = input.next();
                char op = rawOperationInput.charAt(0);
                switch (op) {
                    case '+':
                        operation = Operations.SUM;

                        break;
                    case '-':
                        operation = Operations.SUB;

                        break;
                    case '*':
                        operation = Operations.MUL;

                        break;
                    case '/':
                        operation = Operations.DIV;

                        break;
                    default:
                        System.out.println("Operação Inválida!");
                        continue;
                }

                System.out.println("Digite o primeiro número:");
                double a = input.nextDouble();

                System.out.println("Digite o segundo número");
                double b = input.nextDouble();

                calcula(a, b, operation);
            }
        } catch (SocketException e) {
            log.error("Erro ao criar o socket");

        } catch (UnknownHostException e) {
            log.error("Erro ao detectar o host fornecido");
        }

    }

    private static void calcula(double a, double b, Operations operation) {
        try {
            CalculusResponse response = calculatorClient.calculate(a, b, operation, 4);
            if (response instanceof CalculusSuccessResponse) {
                CalculusSuccessResponse successResponse = (CalculusSuccessResponse) response;
                System.out.println(String.format("Resposta: %f", successResponse.getAnswer()));

//                log.debug("calculou {}", successResponse.getAnswer());

            } else if (response instanceof CalculusErrorResponse) {
                CalculusErrorResponse errorResponse = (CalculusErrorResponse) response;
//                log.debug("erro ao calcular: {}", errorResponse.getMessage());
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
