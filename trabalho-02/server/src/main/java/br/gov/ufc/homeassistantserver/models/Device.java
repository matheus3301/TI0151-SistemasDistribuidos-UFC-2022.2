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

    @Data
    @Builder
    public static class SensorEntity{
        private final int id;
        private final String name;
        private final List<Measure> history;

        @Data
        @Builder
        public static class Measure{
            private final LocalDateTime timestamp;
            private final double value;
        }
    }

    @Data
    @Builder
    public static class ActuatorEntity {
        private final int id;
        private final String name;
        private final List<Status> history;

        @Data
        @Builder
        public static class Status {
            private final LocalDateTime timestamp;
            private final boolean value;
        }
    }
}
