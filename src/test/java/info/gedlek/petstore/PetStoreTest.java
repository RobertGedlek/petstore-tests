package info.gedlek.petstore;

import info.gedlek.petstore.utils.TestDataGenerator;
import info.gedlek.petstore.model.Pet;
import info.gedlek.petstore.service.PetStoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static info.gedlek.petstore.asserters.PetAsserter.assertThat;

public class PetStoreTest {

    private final PetStoreService petService = new PetStoreService();

    @Test
    @DisplayName("Full cycle: Add pet -> Fetch pet -> Verify data -> Delete")
    public void shouldCreateAndGetPet() {
        //given
        var newPet = TestDataGenerator.generateDefaultPet();

        //when
        petService.addPet_200(newPet);

        //then
        var fetchedPet = petService.getPetById_200(newPet.getId());

        assertThat(fetchedPet)
                .toHaveId(newPet.getId())
                .toHaveName(newPet.getName())
                .toHaveStatus(Pet.StatusEnum.AVAILABLE)
                .toHavePhotoUrls(newPet.getPhotoUrls());
    }
}