package br.gov.ufc.homeassistantserver.listeners;

import br.gov.ufc.homeassistantserver.configuration.QueueConfiguration;
import br.gov.ufc.homeassistantserver.constants.ExchangeNames;
import br.gov.ufc.homeassistantserver.messages.DeviceUpdateMessage;
import br.gov.ufc.homeassistantserver.models.Device;
import br.gov.ufc.homeassistantserver.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceListener {
    private final DeviceRepository deviceRepository;
    private final QueueConfiguration.QueueName queueName;
    @RabbitListener(queues = "#{queueName.getName()}")
    public void listenToDeviceStatus(@Payload DeviceUpdateMessage status){

        Device device = deviceRepository.getById(status.getUuid());

        if(device == null){
            log.error("cannot find device {}", status.getUuid());
            return;
        }

        log.info("saving data of device {} on database", status.getUuid());
        var actuators = device.getActuators();

        if(actuators != null && status.getActuators() != null) {
            for (var actuator : actuators) {
                for (var a : status.getActuators()) {
                    if (actuator.getId() == a.getId()) {
                        if (actuator.getHistory().size() >= deviceRepository.getLimit()) {
                            actuator.getHistory().remove(0);
                        }

                        actuator.getHistory().add(Device.ActuatorEntity.Status.builder().timestamp(LocalDateTime.now()).value(a.isState()).build());
                    }
                }
            }
        }

        var sensors = device.getSensors();

        if(sensors != null & status.getSensors() != null){
            for(var sensor : sensors){
                for(var a: status.getSensors()){
                    if(sensor.getId() == a.getId()){
                        if(sensor.getHistory().size() >= deviceRepository.getLimit()){
                            sensor.getHistory().remove(0);
                        }
                        sensor.getHistory().add(Device.SensorEntity.Measure.builder().timestamp(LocalDateTime.now()).value(a.getValue()).build());
                    }
                }
            }
        }
    }
}
