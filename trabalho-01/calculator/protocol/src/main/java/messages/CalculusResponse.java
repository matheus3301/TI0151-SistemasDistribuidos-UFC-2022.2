package messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.InetAddress;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculusResponse {
    protected InetAddress address;
    protected int port;
}
