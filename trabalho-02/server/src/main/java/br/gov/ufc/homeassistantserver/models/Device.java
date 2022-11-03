package br.gov.ufc.homeassistantserver.models;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Builder
public class Device {
    private String uuid;
    private final String name;
    private final List<SensorEntity> sensors;
    private final List<ActuatorEntity> actuators;
    private int grpc_port;
    private String remote_address;

    @Data
    @Builder
    public static class SensorEntity{
        private int id;
        private String name;
        private List<Measure> history;
        @Data
        @Builder
        public static class Measure{
            private LocalDateTime timestamp;
            private double value;
        }
    }

    @Data
    @Builder
    public static class ActuatorEntity {
        private int id;
        private String name;
        private List<Status> history;

        @Data
        @Builder
        public static class Status {
            private LocalDateTime timestamp;
            private boolean value;
        }
    }
}
