package org.example.neonarkintaketracker.dto;

/**
 * DTO used for CREATE and UPDATE requests coming from the Java CLI client.
 *
 * NOTE:
 *  - We intentionally EXCLUDE id and createdAt because those are DB-managed.
 *  - This is the "allowed" shape of incoming data.
 */

public record CreatureRequest (
    String name,
    String species,
    String dangerLevel,
    String condition
)
{}
