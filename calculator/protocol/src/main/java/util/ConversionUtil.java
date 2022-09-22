package util;

import enums.Operations;
import exceptions.InvalidRequestException;
import exceptions.InvalidResponseException;
import messages.CalculusErrorResponse;
import messages.CalculusRequest;
import messages.CalculusResponse;
import messages.CalculusSuccessResponse;

public class ConversionUtil {
    public static CalculusRequest.CalculusRequestBody stringToRequest(String s) throws InvalidRequestException{
        CalculusRequest.CalculusRequestBody request = new CalculusRequest.CalculusRequestBody();

        s = s.trim();

        String[] lines = s.split("\n");
        if(lines.length < 4)
            throw new InvalidRequestException();

        lines[0] = lines[0].trim();
        String[] firstLine = lines[0].split(" ");

        if(firstLine[0].equalsIgnoreCase("SUM")){
            request.setOperation(Operations.SUM);
        } else if (firstLine[0].equalsIgnoreCase("SUB")){
            request.setOperation(Operations.SUB);
        } else if (firstLine[0].equalsIgnoreCase("DIV")){
            request.setOperation(Operations.DIV);
        } else if (firstLine[0].equalsIgnoreCase("MUL")){
            request.setOperation(Operations.MUL);
        } else {
            throw new InvalidRequestException();
        }

        if(!firstLine[1].equalsIgnoreCase("CP")){
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
                    headerLine = lines[i-1].split(":");
                    if(!headerLine[0].equals("Precision")){
                        throw new InvalidRequestException();
                    }
                    try {
                        Double.parseDouble(headerLine[1]);
                    } catch (NumberFormatException e) {
                    }
                    request.setPrecision(Integer.parseInt(headerLine[1]));
                }
                try {
                    Double.parseDouble(lines[i+1]);
                } catch (NumberFormatException e) {
                }
                try {
                    Double.parseDouble(lines[i+2]);
                } catch (NumberFormatException e) {
                }
                request.setA(Double.parseDouble(lines[i+1]));
                request.setB(Double.parseDouble(lines[i+2]));
                break;
            }
        }

        return request;
    }

    public static CalculusResponse stringToResponse(String s) throws InvalidResponseException {
        s = s.trim();

        String[] lines = s.split("\n");
        if(lines.length != 2)
            throw new InvalidResponseException();

        lines[0] = lines[0].trim();
        String[] firstLine = lines[0].split(" ");

        if(!firstLine[0].equals("CP")){
            throw new InvalidResponseException();
        }
        if(!firstLine[1].equals("1.0")){
            throw new InvalidResponseException();
        }
        if(!firstLine[2].equals("OK") && !firstLine[2].equals("ERROR")){
            throw new InvalidResponseException();
        }

        String[] secondLine = lines[1].trim().split(":");
        if(secondLine.length != 2){
            throw new InvalidResponseException();
        }

        if(firstLine[2].equals("OK")){
            if(!secondLine[0].equals("Answer")){
                throw new InvalidResponseException();
            }
            String[] rightSecondLine = secondLine[1].split(".");
            if(rightSecondLine.length != 2){
               throw new InvalidResponseException();
            }
            try {
                Double.parseDouble(rightSecondLine[0]);
            } catch (NumberFormatException e) {
            }
            try {
                Double.parseDouble(rightSecondLine[1]);
            } catch (NumberFormatException e){

            }
            CalculusSuccessResponse responseSuccess = new CalculusSuccessResponse();
            responseSuccess.setAnswer(Double.parseDouble(secondLine[1]));
            responseSuccess.setPrecision(rightSecondLine[1].length());
            return responseSuccess;
        } else {
            CalculusErrorResponse responseError = new CalculusErrorResponse();
            if(!secondLine[0].equals("Message")){
                throw new InvalidResponseException();
            }
            responseError.setMessage(secondLine[1]);
            return responseError;
        }
    }
}
