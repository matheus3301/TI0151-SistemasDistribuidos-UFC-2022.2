package messages;

import enums.Operations;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalculusRequest {
    private double a,b;
    private int precision = 2;
    private Operations operation;

    @Override
    public String toString(){
        return "";
    }
}
