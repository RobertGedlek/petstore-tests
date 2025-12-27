package info.gedlek.petstore.asserters;

import info.gedlek.petstore.model.Category;
import info.gedlek.petstore.model.Pet;
import info.gedlek.petstore.model.Pet.StatusEnum;
import info.gedlek.petstore.model.Tag;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AbstractAssert;

import java.util.List;
import java.util.Objects;

@Slf4j
public class PetAsserter extends AbstractAssert<PetAsserter, Pet> {

    protected PetAsserter(Pet pet) {
        super(pet, PetAsserter.class);
    }

    public static PetAsserter assertThat(Pet actual) {
        return new PetAsserter(actual);
    }

    @Step("to have id: {id}")
    public PetAsserter toHaveId(Long id) {
        log.info("to have id: {}", id);
        isNotNull();

        if (!Objects.equals(actual.getId(), id)) {
            failWithMessage("Expected pet id to be <%s> but was <%s>", id, actual.getId());
        }

        return this;
    }

    @Step("to have name: {name}")
    public PetAsserter toHaveName(String name) {
        log.info("to have name: {}", name);
        isNotNull();

        if (!Objects.equals(actual.getName(), name)) {
            failWithMessage("Expected pet name to be <%s> but was <%s>", name, actual.getName());
        }

        return this;
    }

    @Step("to have status: {status}")
    public PetAsserter toHaveStatus(StatusEnum status) {
        log.info("to have status: {}", status);
        isNotNull();

        if (!Objects.equals(actual.getStatus(), status)) {
            failWithMessage("Expected pet status to be <%s> but was <%s>", status, actual.getStatus());
        }

        return this;
    }

    @Step("to have status (string): {statusName}")
    public PetAsserter toHaveStatus(String statusName) {
        log.info("to have status (string): {}", statusName);
        isNotNull();

        StatusEnum expectedStatus;
        try {
            expectedStatus = StatusEnum.fromValue(statusName);
        } catch (IllegalArgumentException e) {
            failWithMessage("Provided status string <%s> is not a valid Pet status", statusName);
            return this;
        }

        if (!Objects.equals(actual.getStatus(), expectedStatus)) {
            failWithMessage("Expected pet status to be <%s> but was <%s>", expectedStatus, actual.getStatus());
        }

        return this;
    }

    @Step("to have category: {category}")
    public PetAsserter toHaveCategory(Category category) {
        log.info("to have category: {}", category);
        isNotNull();

        if (!Objects.equals(actual.getCategory(), category)) {
            failWithMessage("Expected pet category to be <%s> but was <%s>", category, actual.getCategory());
        }

        return this;
    }

    @Step("to have photoUrls: {photoUrls}")
    public PetAsserter toHavePhotoUrls(List<String> photoUrls) {
        log.info("to have photoUrls: {}", photoUrls);
        isNotNull();

        if (!Objects.equals(actual.getPhotoUrls(), photoUrls)) {
            failWithMessage("Expected photoUrls to be <%s> but was <%s>", photoUrls, actual.getPhotoUrls());
        }

        return this;
    }

    @Step("to have tags: {tags}")
    public PetAsserter toHaveTags(List<Tag> tags) {
        log.info("to have tags: {}", tags);
        isNotNull();

        if (!Objects.equals(actual.getTags(), tags)) {
            failWithMessage("Expected tags to be <%s> but was <%s>", tags, actual.getTags());
        }

        return this;
    }
}