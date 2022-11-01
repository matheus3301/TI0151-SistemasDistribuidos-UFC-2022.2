package br.gov.ufc.homeassistantserver.messages;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceUpdateMessage {
    private String uuid;
    private List<Sensor> sensors;
    private List<Actuator> actuators;

    @Data
    public static class Sensor{
        private int id;
        private double value;
    }

    @Data
    public static class Actuator{
        private int id;
        private boolean state;
    }
}
