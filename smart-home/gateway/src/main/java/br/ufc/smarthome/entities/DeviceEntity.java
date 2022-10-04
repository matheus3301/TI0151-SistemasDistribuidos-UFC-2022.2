package br.ufc.smarthome.entities;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.IOException;
import java.net.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Builder
public class DeviceEntity {
    private String uuid;
    private final String remote_address;
    private final int remote_port;
    private final String name;
    private final List<SensorEntity> sensors;
    private final List<ActuatorEntity> actuators;

    public boolean shutdown(){
        return this.remoteToggle(0);
    }

    public boolean remoteToggle(int actuatorId){
        try {
            String message = actuatorId+"";
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName(this.remote_address), this.remote_port);
            socket.send(sendPacket);
            return true;
        } catch (SocketException | UnknownHostException e) {
            return false;
        } catch (IOException e) {
            //TODO: remover da lista
            return false;
        }

    }

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
