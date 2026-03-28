package org.example.neonarkintaketracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO used for CREATE and UPDATE requests coming from the Java CLI client.
 *
 * NOTE:
 *  - We intentionally EXCLUDE id and createdAt because those are DB-managed.
 *  - This is the "allowed" shape of incoming data.
 */

public record CreatureRequest ( // Request DTO with validation rules.
                                @NotBlank String name,               // Reject empty names early.
                                @NotBlank String species,            // Reject missing species.
                                @NotBlank String dangerLevel,        // Reject missing danger level.
                                @NotBlank String condition,          // Reject missing condition.
                                @Size(max = 500) String notes,       // Limit input size.
                                @NotNull Long habitatId              // Require valid habitat reference.
)
{}
