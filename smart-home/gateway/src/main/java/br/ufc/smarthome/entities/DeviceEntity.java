package br.ufc.smarthome.entities;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DeviceEntity {
    private final String uuid;
    private final String remote_address;
    private final int port;
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

        public boolean remoteToggle(){


            return true;
        }
    }
}
