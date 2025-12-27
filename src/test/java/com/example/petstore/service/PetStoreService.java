package com.example.petstore.service;

import com.example.petstore.api.PetApi;
import com.example.petstore.invoker.ApiClient;
import com.example.petstore.invoker.ApiException;
import com.example.petstore.invoker.ApiResponse;
import com.example.petstore.model.Pet;
import io.qameta.allure.Step;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

/**
 * Service object for handling Petstore API operations with built-in validation.
 * Wraps the raw PetApi client to provide business-level methods for tests.
 */
public class PetStoreService {

    private final PetApi petApi;

    public PetStoreService() {
        Logger httpLogger = LoggerFactory.getLogger("HTTP-TRAFFIC");

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> {
            httpLogger.info(message);
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        var client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        var apiClient = new ApiClient();
        apiClient.setHttpClient(client);
        apiClient.setBasePath("https://petstore.swagger.io/v2");

        this.petApi = new PetApi(apiClient);
    }

    /**
     * Adds a new pet to the store and asserts that the operation was successful.
     *
     * @param pet the pet object to be added
     */
    @Step("POST Add Pet (Expect: 200 OK)")
    public void addPet_200(Pet pet) {
        try {
            ApiResponse<Void> response = petApi.addPetWithHttpInfo(pet);

            assertThat(response.getStatusCode())
                    .as("Status code should be 200 after adding a pet")
                    .isEqualTo(200);

        } catch (ApiException e) {
            fail("API returned error while adding pet: " + e.getCode() + "\nBody: " + e.getResponseBody());
        }
    }

    /**
     * Retrieves a pet by its ID and asserts that the operation was successful.
     *
     * @param petId the ID of the pet to retrieve
     * @return the retrieved Pet object
     */
    @Step("GET Pet by ID (Expect: 200 OK)")
    public Pet getPetById_200(Long petId) {
        try {
            ApiResponse<Pet> response = petApi.getPetByIdWithHttpInfo(petId);

            assertThat(response.getStatusCode())
                    .as("Status code should be 200 when fetching pet")
                    .isEqualTo(200);

            return response.getData();

        } catch (ApiException e) {
            fail("API returned error while fetching pet ID " + petId + ": " + e.getCode());
            return null;
        }
    }

    /**
     * Deletes a pet by its ID and asserts that the operation was successful.
     *
     * @param petId the ID of the pet to delete
     */
    @Step("DELETE Pet (Expect: 200 OK)")
    public void deletePet_200(Long petId) {
        try {
            ApiResponse<Void> response = petApi.deletePetWithHttpInfo(petId, null);

            assertThat(response.getStatusCode())
                    .as("Status code should be 200 after deleting pet")
                    .isEqualTo(200);

        } catch (ApiException e) {
            fail("API returned error while deleting pet: " + e.getCode());
        }
    }

    /**
     * Attempts to retrieve a pet by its ID and asserts that the pet is not found.
     * Validates that the API throws an ApiException with status code 404.
     *
     * @param petId the ID of the non-existent pet
     */
    @Step("GET Pet by ID (Expect: 404 Not Found)")
    public void getPetById_404(Long petId) {
        assertThatThrownBy(() -> petApi.getPetByIdWithHttpInfo(petId))
                .as("Should throw ApiException for non-existing pet")
                .isInstanceOf(ApiException.class)
                .extracting(e -> ((ApiException) e).getCode())
                .as("Status code in exception should be 404")
                .isEqualTo(404);
    }
}