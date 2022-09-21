package messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculusErrorResponse extends CalculusResponse{
    private String message;

    @Override
    public String toString(){
        return "CP 1.0 ERROR\n"+
                String.format("Message: %s", message);
    }
}
