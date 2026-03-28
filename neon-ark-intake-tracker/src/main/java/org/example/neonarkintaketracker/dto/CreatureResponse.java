package org.example.neonarkintaketracker.dto;

public record CreatureResponse(   // Outgoing DTO for API responses.
                                  Long id,                   // Safe server-generated identifier.
                                  String name,               // Creature name.
                                  String species,            // Species label.
                                  String dangerLevel,        // Danger level for display.
                                  String condition,          // Condition for display.
                                  String notes,              // Notes returned by design choice.
                                  Long habitatId,            // Relationship returned as ID only.
                                  String createdAt           // Timestamp formatted for display.
) {}
