import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract base class representing a generic pet.
 * Every pet has a name and belongs to exactly one {@link House}.
 */
public abstract class Pet {

    /** The name of the pet. */
    private String name;

    /** The house this pet belongs to; may be {@code null} if the pet is not assigned. */
    private House house;

    /** No‑argument constructor required by the task specification. */
    public Pet() {
        // fields are initialised to null by default
    }

    /**
     * Makes the pet produce its characteristic noise.
     * Concrete subclasses must provide an implementation.
     */
    public abstract void makeNoise();

    /** @return the pet's name */
    public String getName() {
        return name;
    }

    /** @param name the name to set for this pet */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the house this pet belongs to, or {@code null} if none */
    public House getHouse() {
        return house;
    }

    /** @param house the house to associate with this pet */
    public void setHouse(House house) {
        this.house = house;
    }
}

/**
 * Concrete class representing a dog.
 */
 class Dog extends Pet {

    /** No‑argument constructor required by the task specification. */
    public Dog() {
        super();
    }

    /**
     * Prints the dog's noise to {@code System.out}.
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Concrete class representing a cat.
 */
 class Cat extends Pet {

    /** No‑argument constructor required by the task specification. */
    public Cat() {
        super();
    }

    /**
     * Prints the cat's noise to {@code System.out}.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}

/**
 * Represents a house that can contain any number of {@link Pet}s.
 */
 class House {

    /** List holding all pets that currently live in this house. */
    private final List<Pet> pets = new ArrayList<>();

    /** No‑argument constructor required by the task specification. */
    public House() {
        // nothing else to initialise
    }

    /**
     * Retrieves a list containing the names of all pets (dogs and cats) living in this house.
     * <p>
     * If the house has no pets, an empty list is returned.
     *
     * @return an immutable list of pet names
     */
    public List<String> getAllPetNames() {
        List<String> names = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getName() != null) {
                names.add(pet.getName());
            }
        }
        return Collections.unmodifiableList(names);
    }

    /**
     * Attempts to add a pet to this house.
     * <p>
     * The operation fails and returns {@code false} if:
     * <ul>
     *   <li>The supplied {@code pet} is {@code null}.</li>
     *   <li>The pet does not have a name ({@code null} or empty).</li>
     *   <li>The pet already belongs to a house (including this one).</li>
     * </ul>
     * If none of the above conditions hold, the pet is added, its {@link Pet#setHouse}
     * reference is updated, and the method returns {@code true}.
     *
     * @param pet the pet to add
     * @return {@code true} if the pet was added successfully; {@code false} otherwise
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
        }
        String name = pet.getName();
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        if (pet.getHouse() != null) {
            return false; // already belongs to a house
        }
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from its current house (which must be this house).
     * <p>
     * The operation fails and returns {@code false} if:
     * <ul>
     *   <li>The supplied {@code pet} is {@code null}.</li>
     *   <li>The pet does not belong to this house.</li>
     * </ul>
     * On successful removal, the pet's house reference is cleared and {@code true}
     * is returned.
     *
     * @param pet the pet to remove
     * @return {@code true} if the pet was removed successfully; {@code false} otherwise
     */
    public boolean removePet(Pet pet) {
        if (pet == null) {
            return false;
        }
        if (!pets.contains(pet)) {
            return false; // pet not in this house
        }
        pets.remove(pet);
        pet.setHouse(null);
        return true;
    }

    /**
     * Retrieves all pets of a specific type that live in this house.
     * <p>
     * The {@code type} argument must be either {@code "dog"} or {@code "cat"}
     * (case‑insensitive). If the argument does not match either type, an empty
     * list is returned.
     *
     * @param type the type of pets to retrieve ("dog" or "cat")
     * @return an immutable list of pets of the requested type; empty if none match
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> result = new ArrayList<>();
        if (type == null) {
            return Collections.unmodifiableList(result);
        }
        String lowered = type.toLowerCase();
        for (Pet pet : pets) {
            if ("dog".equals(lowered) && pet instanceof Dog) {
                result.add(pet);
            } else if ("cat".equals(lowered) && pet instanceof Cat) {
                result.add(pet);
            }
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Counts the total number of pets residing in this house.
     *
     * @return the number of pets; {@code 0} if the house is empty
     */
    public int countPets() {
        return pets.size();
    }

    /** @return an unmodifiable view of the internal pet list */
    public List<Pet> getPets() {
        return Collections.unmodifiableList(pets);
    }

    // No setter for the internal list is provided to preserve encapsulation.
}