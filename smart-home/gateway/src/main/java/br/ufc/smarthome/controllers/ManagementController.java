package br.ufc.smarthome.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/management")
@RequiredArgsConstructor
@Slf4j
public class ManagementController {
    @GetMapping("/scan")
    public void scanForDevices(){
        log.info("scanning for new devices on the local network");

        // TODO: get ip automatically and set port on application.yml
        String message = "localhost 8080";

        try {
            // TODO: set multicast group and port on application.yml

            DatagramSocket socket = new DatagramSocket();
            InetAddress group = InetAddress.getByName("230.0.0.0");
            byte[] buf = message.getBytes(StandardCharsets.UTF_8);

            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 7777);
            socket.send(packet);
            socket.close();
        } catch (SocketException e) {
            log.error("error during datagram socket instantiation");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            log.error("error while solving datagram group name");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("error while sending multicast message");
            e.printStackTrace();
        }
    }
}
