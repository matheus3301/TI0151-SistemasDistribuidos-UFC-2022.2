package br.ufc.smarthome.repositories;

import br.ufc.smarthome.models.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<Device, String> {
}
