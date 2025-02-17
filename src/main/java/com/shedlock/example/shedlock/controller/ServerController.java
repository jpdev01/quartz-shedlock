package com.shedlock.example.shedlock.controller;

import com.shedlock.example.shedlock.service.ServerService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class ServerController {

    private final ServerService serverService;

    public ServerController(ServerService serverService) {
        this.serverService = serverService;
    }

    @DeleteMapping("/stop")
    public void stop() {
        try {
            serverService.stop();
        } catch (Exception e) {
            System.out.println("Error stopping server: " + e.getMessage());
        }
    }
}
