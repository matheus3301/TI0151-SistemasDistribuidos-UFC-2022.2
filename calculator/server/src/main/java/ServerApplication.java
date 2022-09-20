import lombok.extern.slf4j.Slf4j;
import messages.CalculusRequest;
import server.CalculatorServer;

import java.io.IOException;
import java.net.SocketException;

@Slf4j
public class ServerApplication {
    private static int PORT = 6666;

    public static void main(String[] args) {
        log.info("starting application");
        try{

            CalculatorServer calculatorServer = new CalculatorServer(PORT);
            log.info("server is running on port {}", PORT);

            while(true){
                calculatorServer.getRequest();


            }
        }catch (SocketException exception){
            log.error("unable to start server, port is already in use");
        } catch (IOException e) {
            log.error("error on handling requests");
        }
    }
}
