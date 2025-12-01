import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a house that can contain any number of {@link Pet} objects.
 */
class House {

    /** The pets that live in this house. */
    private List<Pet> pets = new ArrayList<>();

    /** Unparameterized constructor. */
    public House() {
        // No‑arg constructor required by the specification.
    }

    /**
     * Retrieves the names of all pets (dogs and cats) living in this house.
     *
     * @return a list containing the names of all pets; an empty list if there are no pets.
     */
    public List<String> retrievePetNames() {
        List<String> names = new ArrayList<>();
        for (Pet pet : pets) {
            names.add(pet.getName());
        }
        return names;
    }

    /**
     * Adds a pet to this house.
     *
     * @param pet the pet to add; must not be {@code null}
     * @return {@code false} if the pet already belongs to any house (including this one) or the pet does not have a valid name;
     *         {@code true} if the pet was added successfully.
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
        }
        // Name must be non‑null and non‑empty
        String name = pet.getName();
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // Pet must not already be associated with a house
        if (pet.getLived() != null) {
            return false;
        }
        pets.add(pet);
        pet.setLived(this);
        return true;
    }

    /**
     * Removes a specified pet from its current house.
     *
     * @param pet the pet to remove; must not be {@code null}
     * @return {@code false} if the pet does not belong to this house;
     *         {@code true} if it was removed successfully.
     */
    public boolean removePet(Pet pet) {
        if (pet == null) {
            return false;
        }
        if (pet.getLived() != this) {
            return false;
        }
        boolean removed = pets.remove(pet);
        if (removed) {
            pet.setLived(null);
        }
        return removed;
    }

    /**
     * Retrieves a list of pets of a specific type that live in this house.
     *
     * @param type the type of pet to retrieve; valid values are {@code "dog"} or {@code "cat"} (case‑insensitive)
     * @return a list containing pets of the requested type; an empty list if there are none or the type is unknown.
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null) {
            return Collections.emptyList();
        }
        String lowered = type.toLowerCase();
        List<Pet> result = new ArrayList<>();
        for (Pet pet : pets) {
            if ("dog".equals(lowered) && pet instanceof Dog) {
                result.add(pet);
            } else if ("cat".equals(lowered) && pet instanceof Cat) {
                result.add(pet);
            }
        }
        return result;
    }

    /**
     * Counts the number of pets living in this house.
     *
     * @return the number of pets; {@code 0} if there are none.
     */
    public int getPetCount() {
        return pets.size();
    }

    /** Getter for the internal pet list (read‑only). */
    public List<Pet> getPets() {
        return Collections.unmodifiableList(pets);
    }

    /** Setter for the internal pet list – mainly for testing purposes. */
    public void setPets(List<Pet> pets) {
        this.pets = pets != null ? new ArrayList<>(pets) : new ArrayList<>();
    }
}

/**
 * Abstract base class for all pets.
 */
abstract class Pet {

    /** Name of the pet. */
    private String name;

    /** The house this pet lives in; {@code null} if it does not belong to any house. */
    private House lived;

    /** Unparameterized constructor. */
    public Pet() {
        // No‑arg constructor required by the specification.
    }

    /**
     * Retrieves the pet's name.
     *
     * @return the name of the pet; may be {@code null} if not set.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for the pet.
     *
     * @param newName the new name; must be non‑null and non‑empty
     * @return {@code true} if the name was set successfully; {@code false} otherwise.
     */
    public boolean setName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            return false;
        }
        this.name = newName;
        return true;
    }

    /**
     * Retrieves the house this pet currently lives in.
     *
     * @return the {@link House} instance, or {@code null} if the pet is not associated with any house.
     */
    public House getLived() {
        return lived;
    }

    /**
     * Sets the house this pet lives in.
     *
     * @param house the house to associate with; may be {@code null} to indicate no house.
     */
    public void setLived(House house) {
        this.lived = house;
    }

    /**
     * Causes the pet to make its characteristic noise.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog, a concrete type of {@link Pet}.
 */
class Dog extends Pet {

    /** Unparameterized constructor. */
    public Dog() {
        // No‑arg constructor required by the specification.
    }

    /**
     * Makes the dog bark.
     */
    @Override
    public void makeNoise() {
        System.out.println(getName() + " says: Woof!");
    }
}

/**
 * Represents a cat, a concrete type of {@link Pet}.
 */
class Cat extends Pet {

    /** Unparameterized constructor. */
    public Cat() {
        // No‑arg constructor required by the specification.
    }

    /**
     * Makes the cat meow.
     */
    @Override
    public void makeNoise() {
        System.out.println(getName() + " says: Meow!");
    }
}