package org.example.neonarkintaketracker.controller;
import org.example.neonarkintaketracker.entity.Creature;
import org.example.neonarkintaketracker.service.CreatureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
 * This controller handles incoming HTTP requests for /api/creatures
 */
@RestController
@RequestMapping("/api/creatures")
public class CreatureController {

    private final CreatureService service;

    public CreatureController(CreatureService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Creature>> getAllCreatures() {
        List<Creature> creatures = service.getAllCreatures();
        return ResponseEntity.ok(creatures);
    } // This was followed by an extra brace in your snippet!

    @GetMapping("/{id}")
    public ResponseEntity<Creature> getCreatureById(@PathVariable Long id) {
        Optional<Creature> maybeCreature = service.getCreatureById(id);

        if (maybeCreature.isEmpty()) {
            //return ResponseEntity.notFound().build();
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(maybeCreature.get());
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("PONG - The controller is alive!");
    }
}