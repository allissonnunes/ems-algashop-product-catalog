package br.dev.allissonnunes.algashop.product.catalog.domain.model;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;

import java.util.UUID;

public final class IdGenerator {

    private static final TimeBasedEpochGenerator TIME_BASED_UUID_GENERATOR = Generators.timeBasedEpochGenerator();

    private IdGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static UUID generateTimeBasedUUID() {
        return TIME_BASED_UUID_GENERATOR.generate();
    }

}
