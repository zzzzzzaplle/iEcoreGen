import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a house that can contain any number of {@link Pet} instances.
 * Provides operations to add, remove and query pets living in the house.
 */
class House {

    /** The collection of pets that currently live in this house. */
    private List<Pet> pets;

    /** No‑argument constructor initializing the internal pet list. */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves the names of all pets (dogs and cats) living in this house.
     *
     * @return a list of pet names; never {@code null}. Returns an empty list if there are no pets.
     */
    public List<String> retrievePetNames() {
        List<String> names = new ArrayList<>();
        for (Pet p : pets) {
            if (p.getName() != null) {
                names.add(p.getName());
            }
        }
        return names;
    }

    /**
     * Attempts to add a pet to this house.
     * <p>
     * The operation fails and returns {@code false} if:
     * <ul>
     *   <li>the pet already belongs to a house (including this one), or</li>
     *   <li>the pet's name is {@code null} or empty.</li>
     * </ul>
     * If the pet can be added, it is stored in the internal list and its {@code lived}
     * reference is set to this house.
     *
     * @param pet the pet to add; must not be {@code null}
     * @return {@code true} if the pet was added successfully; {@code false} otherwise
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
        }
        // pet must have a valid name
        String name = pet.getName();
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // pet must not already belong to any house
        if (pet.getLivedHouse() != null) {
            return false;
        }
        pets.add(pet);
        pet.setLivedHouse(this);
        return true;
    }

    /**
     * Attempts to remove a specified pet from this house.
     * <p>
     * The operation fails and returns {@code false} if the pet does not belong to this house.
     * On successful removal the pet's {@code lived} reference is cleared.
     *
     * @param pet the pet to remove; must not be {@code null}
     * @return {@code true} if the pet was removed successfully; {@code false} otherwise
     */
    public boolean removePet(Pet pet) {
        if (pet == null) {
            return false;
        }
        if (pet.getLivedHouse() != this) {
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
     * @param type the type of pet to retrieve; expected values are "dog" or "cat"
     * @return a list containing the matching pets; never {@code null}. Returns an empty list if none match.
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null) {
            return Collections.emptyList();
        }
        String lowered = type.trim().toLowerCase();
        List<Pet> result = new ArrayList<>();
        for (Pet p : pets) {
            if ("dog".equals(lowered) && p instanceof Dog) {
                result.add(p);
            } else if ("cat".equals(lowered) && p instanceof Cat) {
                result.add(p);
            }
        }
        return result;
    }

    /**
     * Counts the number of pets currently living in this house.
     *
     * @return the total number of pets; zero if there are none
     */
    public int getPetCount() {
        return pets.size();
    }

    // -------------------------------------------------------------------------
    // Standard getters and setters for the private field (useful for testing)
    // -------------------------------------------------------------------------

    /**
     * Returns the mutable list of pets owned by this house.
     * <p>
     * Modifications to the returned list affect the house directly; it is
     * provided mainly for testing purposes.
     *
     * @return the internal list of pets
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Replaces the internal pet list with the supplied list.
     *
     * @param pets the new list of pets; if {@code null} an empty list is used
     */
    public void setPets(List<Pet> pets) {
        this.pets = (pets != null) ? pets : new ArrayList<>();
        // Ensure each pet knows it lives in this house (or null if list is empty)
        for (Pet p : this.pets) {
            p.setLivedHouse(this);
        }
    }
}

/**
 * Abstract base class for all pets. Holds a name and a reference to the {@link House}
 * where the pet lives.
 */
abstract class Pet {

    /** The pet's name. */
    private String name;

    /** The house where this pet lives; {@code null} if it does not belong to any house. */
    private House lived;

    /** No‑argument constructor. */
    public Pet() {
        // fields remain null until set
    }

    /**
     * Returns the pet's name.
     *
     * @return the name; may be {@code null}
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for the pet.
     *
     * @param newName the new name; must not be {@code null} or empty
     * @return {@code true} if the name was set successfully; {@code false} otherwise
     */
    public boolean setName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            return false;
        }
        this.name = newName;
        return true;
    }

    /**
     * Returns the {@link House} where this pet lives.
     *
     * @return the current house; may be {@code null}
     */
    public House getLivedHouse() {
        return lived;
    }

    /**
     * Package‑private setter used by {@link House} to update the pet's residence.
     *
     * @param house the house where the pet now lives; may be {@code null}
     */
    void setLivedHouse(House house) {
        this.lived = house;
    }

    /**
     * Makes the pet produce its characteristic noise.
     */
    public abstract void makeNoise();
}

/**
 * Concrete implementation of a dog.
 */
class Dog extends Pet {

    /** No‑argument constructor. */
    public Dog() {
        super();
    }

    /**
     * Causes the dog to bark.
     * Prints "Woof!" to standard output.
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

    /** No‑argument constructor. */
    public Cat() {
        super();
    }

    /**
     * Causes the cat to meow.
     * Prints "Meow!" to standard output.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}