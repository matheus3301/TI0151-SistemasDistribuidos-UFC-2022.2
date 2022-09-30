package br.ufc.smarthome.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DeviceDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @NotBlank(message = "name is a required field")
    @NotNull(message = "name is a required field")
    private String name;
}
