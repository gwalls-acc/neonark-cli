package org.example.neonarkintaketracker.controller;
import jakarta.validation.Valid;
import org.example.neonarkintaketracker.dto.CreatureRequest;
import org.example.neonarkintaketracker.dto.CreatureResponse;
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

    @PostMapping
    public ResponseEntity<CreatureResponse> create(@Valid @RequestBody CreatureRequest req) {
        CreatureResponse created = service.createCreature(req);

        // Option A: return 201 Created (recommended) with a response body
        // Option B: return 200 OK with a response body

        return ResponseEntity.status(201).body(created);
    }

    @PostMapping("/api/creatures")                      // Handles POST requests to create creatures.
    public CreatureResponse create(
            @RequestBody CreatureRequest req) {         // Request body mapped to DTO.
        return service.create(req);                     // Delegates logic to service layer.
    }

}