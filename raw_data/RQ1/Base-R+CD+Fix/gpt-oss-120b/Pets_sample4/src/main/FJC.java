import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a house that can contain any number of {@link Pet}s.
 */
class House {

    /** The pets that currently live in this house. */
    private List<Pet> pets;

    /**
     * Constructs a new {@code House} with an empty list of pets.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves the names of all pets (dogs and cats) living in this house.
     *
     * @return an immutable list containing the names of the pets;
     *         the list is empty if there are no pets.
     */
    public List<String> retrievePetNames() {
        if (pets.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> names = new ArrayList<>(pets.size());
        for (Pet p : pets) {
            names.add(p.getName());
        }
        return Collections.unmodifiableList(names);
    }

    /**
     * Adds a pet to this house.
     *
     * <p>The method fails and returns {@code false} if any of the following
     * conditions hold:
     * <ul>
     *   <li>The pet is {@code null}.</li>
     *   <li>The pet does not have a valid (non‑null, non‑empty) name.</li>
     *   <li>The pet already belongs to a house (including this one).</li>
     * </ul>
     *
     * @param pet the pet to be added
     * @return {@code true} if the pet was added successfully; {@code false}
     *         otherwise.
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
        }
        String name = pet.getName();
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (pet.getLivedHouse() != null) {
            return false;
        }
        pets.add(pet);
        pet.setLivedHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     *
     * @param pet the pet to be removed
     * @return {@code true} if the pet was removed successfully;
     *         {@code false} if the pet does not belong to this house.
     */
    public boolean removePet(Pet pet) {
        if (pet == null || pet.getLivedHouse() != this) {
            return false;
        }
        boolean removed = pets.remove(pet);
        if (removed) {
            pet.setLivedHouse(null);
        }
        return removed;
    }

    /**
     * Retrieves all pets of a specific type that live in this house.
     *
     * @param type the type of pet to retrieve; accepted values are
     *             {@code "dog"} or {@code "cat"} (case‑insensitive)
     * @return an immutable list of pets matching the requested type;
     *         the list is empty if no such pets are present.
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null) {
            return Collections.emptyList();
        }
        String normalized = type.trim().toLowerCase();
        List<Pet> result = new ArrayList<>();
        for (Pet p : pets) {
            if ("dog".equals(normalized) && p instanceof Dog) {
                result.add(p);
            } else if ("cat".equals(normalized) && p instanceof Cat) {
                result.add(p);
            }
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Counts the number of pets currently living in this house.
     *
     * @return the number of pets; 0 if there are none.
     */
    public int getPetCount() {
        return pets.size();
    }

    // -----------------------------------------------------------------
    // Standard getters & setters for the private field (useful for tests)
    // -----------------------------------------------------------------

    /**
     * Returns the mutable list of pets owned by this house.
     * <p>
     * <strong>Note:</strong> Modifying the returned list directly can break
     * invariants (e.g., the pet's {@code lived} reference). Use the provided
     * {@code addPet} and {@code removePet} methods for safe modifications.
     *
     * @return the internal list of pets.
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Replaces the internal pet list with the supplied list.
     * <p>
     * This method is primarily intended for testing frameworks. It does not
     * update the {@code lived} reference of the individual pets; callers should
     * ensure consistency manually.
     *
     * @param pets the new list of pets
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets != null ? pets : new ArrayList<>();
    }
}

/**
 * Abstract base class for all pets.
 */
abstract class Pet {

    /** The pet's name. */
    private String name;

    /** The house where the pet currently lives; {@code null} if homeless. */
    private House lived;

    /**
     * Constructs a new {@code Pet} with no name and no house.
     */
    protected Pet() {
        this.name = null;
        this.lived = null;
    }

    /**
     * Retrieves the pet's name.
     *
     * @return the name, possibly {@code null} if not set.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the pet's name.
     *
     * @param newName the new name; must be non‑null and non‑empty after trimming
     * @return {@code true} if the name was set; {@code false} otherwise.
     */
    public boolean setName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            return false;
        }
        this.name = newName.trim();
        return true;
    }

    /**
     * Returns the house this pet lives in.
     *
     * @return the {@link House} instance, or {@code null} if the pet has no home.
     */
    public House getLivedHouse() {
        return lived;
    }

    /**
     * Sets the house where this pet lives.
     *
     * @param house the house to associate with this pet; may be {@code null}
     */
    protected void setLivedHouse(House house) {
        this.lived = house;
    }

    /**
     * Causes the pet to make its characteristic noise.
     */
    public abstract void makeNoise();

    // -----------------------------------------------------------------
    // Standard getters & setters for the private fields (useful for tests)
    // -----------------------------------------------------------------

    /**
     * Directly sets the internal {@code lived} reference.
     *
     * @param lived the house to set; may be {@code null}
     */
    public void setLived(House lived) {
        this.lived = lived;
    }
}

/**
 * Concrete implementation of a dog.
 */
class Dog extends Pet {

    /**
     * Constructs a new {@code Dog} with no name and no house.
     */
    public Dog() {
        super();
    }

    /**
     * Makes a dog-specific noise.
     * <p>
     * For demonstration purposes, the noise is printed to {@code System.out}.
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Concrete implementation of a cat.
 */
class Cat extends Pet {

    /**
     * Constructs a new {@code Cat} with no name and no house.
     */
    public Cat() {
        super();
    }

    /**
     * Makes a cat-specific noise.
     * <p>
     * For demonstration purposes, the noise is printed to {@code System.out}.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}