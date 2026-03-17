package org.example.neonarkintaketracker.dto;

public record CreatureResponse(
        Long id,
        String name,
        String species,
        String dangerLevel,
        String condition,
        java.time.Instant createdAt
) {
}
