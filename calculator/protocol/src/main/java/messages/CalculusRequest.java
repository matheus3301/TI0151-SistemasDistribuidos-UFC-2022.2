package messages;

import enums.Operations;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalculusRequest {
    private double a,b;
    private int precision = 2;
    private Operations operation;
}
