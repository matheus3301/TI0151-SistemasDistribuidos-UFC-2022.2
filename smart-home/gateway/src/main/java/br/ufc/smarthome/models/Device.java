package br.ufc.smarthome.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash
@Data
@Builder
public class Device implements Serializable {
    private String id;
    private String name;
}
