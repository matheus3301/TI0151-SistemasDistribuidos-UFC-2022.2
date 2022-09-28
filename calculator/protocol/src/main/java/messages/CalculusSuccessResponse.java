package messages;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalculusSuccessResponse extends CalculusResponse{
    private double answer;
    private int precision;

    @Override
    public String toString(){
        return "CP 1.0 OK\n"+
                   String.format("Answer: %."+precision+"f", answer);
    }
}
