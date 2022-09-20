package util;

import enums.Operations;
import exceptions.InvalidRequestException;
import messages.CalculusRequest;

public class ConversionUtil {
    public static CalculusRequest StringToObject(String s) throws InvalidRequestException{
        CalculusRequest request = new CalculusRequest();

        s = s.trim();

        String[] lines = s.split("\n");
        if(lines.length < 4)
            throw new InvalidRequestException();

        lines[0] = lines[0].trim();
        String[] firstLine = lines[0].split(" ");

        if(firstLine[0].toUpperCase().equals("SUM")){
            request.setOperation(Operations.SUM);
        } else if (firstLine[0].toUpperCase().equals("SUB")){
            request.setOperation(Operations.SUB);
        } else if (firstLine[0].toUpperCase().equals("DIV")){
            request.setOperation(Operations.DIV);
        } else if (firstLine[0].toUpperCase().equals("MUL")){
            request.setOperation(Operations.MUL);
        } else {
            throw new InvalidRequestException();
        }

        if(!firstLine[1].toUpperCase().equals("CP")){
            throw new InvalidRequestException();
        }

        if(!firstLine[2].equals("1.0")){
            throw new InvalidRequestException();
        }

        String[] headerLine;

        for(int i = 1; i < lines.length; i++){
            String blankLine = lines[i];
            if (blankLine.equals("")){
                if(i != 1){
                    headerLine = lines[i-1].split(" ");
                    request.setPrecision(Integer.parseInt(headerLine[1]));
                }
                request.setA(Double.parseDouble(lines[i+1]));
                request.setB(Double.parseDouble(lines[i+2]));
                break;
            }
        }

        return request;
    }
}
