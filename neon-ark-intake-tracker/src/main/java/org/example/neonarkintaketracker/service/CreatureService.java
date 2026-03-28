package org.example.neonarkintaketracker.service;
import org.example.neonarkintaketracker.entity.Creature;
import org.example.neonarkintaketracker.entity.Habitat;
import org.example.neonarkintaketracker.repository.CreatureRepository;
import org.example.neonarkintaketracker.repository.HabitatRepository;
import org.example.neonarkintaketracker.dto.CreatureResponse;
import org.example.neonarkintaketracker.dto.CreatureRequest;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class CreatureService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final CreatureRepository repository;
    private final HabitatRepository habitatRepository;


    public CreatureService(CreatureRepository repository,HabitatRepository habitatRepository) {
        this.repository = repository;
        this.habitatRepository = habitatRepository;
    }

    /*
     * Return every creature currently in the database.
     * This is the "Read" operation for GET /api/creatures
     */
    public List<Creature> getAllCreatures() {
        return repository.findAll();
    }

    // NEW: Return one creature by id (Optional = may not exist)
    public Optional<Creature> getCreatureById(Long id) {
        return repository.findById(id);
    }

    public CreatureResponse createCreature(CreatureRequest req) {
        // 1) Get the Habitat
        Habitat habitat = habitatRepository.findById(req.habitatId())
                .orElseThrow(() -> new RuntimeException("Habitat not found"));
        // 2) Map request DTO -> entity
        Creature creature = toEntity(req, habitat);
        creature.setHabitat(habitat);

        // 3) Save (DB assigns id, timestamps, etc.)
        Creature saved = repository.save(creature);

        // 4) Map entity -> response DTO
        return toResponse(saved);
    }
    private Creature toEntity(
            CreatureRequest r,                          // Incoming request DTO.
            Habitat habitat) {                           // Habitat resolved server-side.
        return Creature.builder()                        // Builder pattern for clarity.
                .name(r.name())                              // Copy allowed fields.
                .species(r.species())
                .dangerLevel(r.dangerLevel())
                .condition(r.condition())
                .notes(r.notes())
                .habitat(habitat)                            // Enforce relationship internally.
                .build();                                    // Finish entity creation.
    }
    private CreatureResponse toResponse(
            Creature c) {                              // Internal entity.
        return new CreatureResponse(
                c.getId(),                                 // Extract ID.
                c.getName(),                               // Extract name.
                c.getSpecies(),                            // Extract species.
                c.getDangerLevel(),                        // Extract danger level.
                c.getCondition(),                          // Extract condition.
                c.getNotes(),                              // Extract notes.
                c.getHabitat().getId(),                    // Return habitat as ID.
                c.getCreatedAt().toString()                // Convert timestamp to string.
        );
    }
}
