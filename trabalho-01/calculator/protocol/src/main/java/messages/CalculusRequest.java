package messages;

import enums.Operations;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.InetAddress;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CalculusRequest {
    private CalculusRequestBody body;
    private InetAddress address;
    private int port;

    @Override
    public String toString() {
        return String.format("%s CP 1.0\n" + "Precision: %d\n\n" + "%f\n" + "%f", body.getOperation(), body.getPrecision(), body.getA(), body.getB());
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CalculusRequestBody {
        private double a, b;
        private int precision = 2;
        private Operations operation;

    }
}
