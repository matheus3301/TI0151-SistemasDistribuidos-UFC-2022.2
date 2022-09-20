package util;

import enums.Operations;
import exceptions.InvalidRequestException;
import messages.CalculusRequest;

public class ConversionUtil {
    public static CalculusRequest StringToObject(String s) throws InvalidRequestException {
        CalculusRequest request = new CalculusRequest();

        s = s.trim();

        String[] lines = s.split("\n");
        if(lines.length < 4)
            throw new InvalidRequestException();

        lines[0] = lines[0].trim();
        String[] firstLine = lines[0].split(" ");

        if(firstLine[0].toUpperCase().equals("SUM")){
            request.setOperation(Operations.SUM);
        }

        return request;
    }
}
