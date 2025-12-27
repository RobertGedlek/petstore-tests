package info.gedlek.petstore.utils;

import info.gedlek.petstore.model.Pet;
import com.github.javafaker.Faker;
import lombok.Getter;

import java.util.Collections;
import java.util.Locale;

/**
 * Utility class responsible for generating random test data.
 * <p>
 * It acts as a wrapper around the <a href="https://github.com/DiUS/java-faker">Java Faker</a> library
 * to ensure consistent configuration (e.g., Locale) and provide domain-specific data generation methods.
 * </p>
 */
public class TestDataGenerator {

    /**
     * Shared instance of the Faker library configured with the US locale.
     * -- GETTER --
     * Provides direct access to the underlying Faker instance.
     * Use this method for edge cases not covered by specific helper methods.
     *
     * @return the singleton {@link Faker} instance.
     */
    @Getter
    private static final Faker faker = new Faker(new Locale("en-US"));

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TestDataGenerator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Generates a random, positive ID for a pet.
     * <p>
     * The ID is guaranteed to be a fixed length of 7 digits to simulate realistic database keys.
     * </p>
     *
     * @return a random 7-digit {@code long} value (e.g., 1234567).
     */
    public static long getRandomPetId() {
        return faker.number().randomNumber(7, true);
    }

    /**
     * Generates a random pet name.
     *
     * @return a random {@code String} representing a dog's name (e.g., "Buddy", "Rex").
     */
    public static String getRandomPetName() {
        return faker.dog().name();
    }

    /**
     * Generates a random category ID within a safe range.
     *
     * @return a random {@code int} between 1 and 100 (inclusive).
     */
    public static int getRandomCategoryId() {
        return faker.number().numberBetween(1, 101);
    }

    /**
     * Generates a default {@link Pet} instance with random ID and name,
     * status set to AVAILABLE, and a default photo URL.
     *
     * @return a new {@link Pet} object with default test data
     */
    public static Pet generateDefaultPet() {
            Pet pet = new Pet();
            pet.setId(getRandomPetId());
            pet.setName(getRandomPetName());
            pet.setStatus(Pet.StatusEnum.AVAILABLE);
            pet.setPhotoUrls(Collections.singletonList("http://url.com/photo"));
            return pet;
        }
    }