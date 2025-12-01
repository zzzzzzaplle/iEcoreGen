import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a house that can contain any number of {@link Pet} objects.
 */
 class House {

    /** The list of pets that live in this house. */
    private List<Pet> pets;

    /**
     * Creates a new {@code House} with an empty list of pets.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Returns the mutable list of pets belonging to this house.
     * <p>
     * Use the provided {@code addPet} and {@code removePet} methods to modify the
     * collection safely.
     *
     * @return the list of pets
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the internal list of pets. Primarily intended for testing or
     * deserialization purposes.
     *
     * @param pets the list of pets to set
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets != null ? pets : new ArrayList<>();
    }

    /**
     * Retrieves the names of all pets (dogs and cats) living in this house.
     *
     * @return a list containing the names of all pets; never {@code null}
     *         but may be empty if no pets live here
     */
    public List<String> retrievePetNames() {
        List<String> names = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getName() != null) {
                names.add(pet.getName());
            }
        }
        return names;
    }

    /**
     * Adds a pet to this house.
     * <p>
     * The operation succeeds only if:
     * <ul>
     *   <li>The pet is not {@code null}.</li>
     *   <li>The pet has a non‑null, non‑empty name.</li>
     *   <li>The pet does not already belong to any house (including this one).</li>
     * </ul>
     *
     * @param pet the pet to add
     * @return {@code true} if the pet was added successfully; {@code false}
     *         otherwise
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
        }
        String name = pet.getName();
        if (name == null || name.trim().isEmpty()) {
            return false; // pet must have a name
        }
        if (pet.getHouse() != null) {
            return false; // pet already belongs to a house
        }
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     *
     * @param pet the pet to remove
     * @return {@code true} if the pet was removed successfully; {@code false}
     *         if the pet does not belong to this house
     */
    public boolean removePet(Pet pet) {
        if (pet == null) {
            return false;
        }
        if (pet.getHouse() != this) {
            return false; // pet is not associated with this house
        }
        boolean removed = pets.remove(pet);
        if (removed) {
            pet.setHouse(null);
        }
        return removed;
    }

    /**
     * Retrieves a list of pets of a specific type that live in this house.
     *
     * @param type the type of pet to retrieve; expected values are {@code "dog"}
     *             or {@code "cat"} (case‑insensitive)
     * @return a list of pets matching the requested type; never {@code null}
     *         but may be empty if no such pets are present
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null) {
            return Collections.emptyList();
        }
        String normalized = type.trim().toLowerCase();
        List<Pet> result = new ArrayList<>();
        for (Pet pet : pets) {
            if ("dog".equals(normalized) && pet instanceof Dog) {
                result.add(pet);
            } else if ("cat".equals(normalized) && pet instanceof Cat) {
                result.add(pet);
            }
        }
        return result;
    }

    /**
     * Counts the number of pets currently living in this house.
     *
     * @return the number of pets; {@code 0} if there are none
     */
    public int getPetCount() {
        return pets.size();
    }
}

/**
 * Abstract base class representing a generic pet.
 */
public abstract class Pet {

    /** The name of the pet. */
    private String name;

    /** The house this pet belongs to; {@code null} if it does not belong to any house. */
    private House house;

    /**
     * Default constructor.
     */
    public Pet() {
        // name and house remain null until set explicitly
    }

    /**
     * Returns the pet's name.
     *
     * @return the name, or {@code null} if not set
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the pet's name.
     *
     * @param name the name to assign; may be {@code null} or empty (validation
     *             occurs when adding the pet to a house)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the house this pet currently belongs to.
     *
     * @return the house, or {@code null} if the pet is not assigned to any house
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house this pet belongs to.
     *
     * @param house the house to assign; may be {@code null} to indicate no
     *              association
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Causes the pet to make its characteristic noise.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog, a specific kind of {@link Pet}.
 */
 class Dog extends Pet {

    /**
     * Default constructor.
     */
    public Dog() {
        super();
    }

    /**
     * Makes the dog bark.
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Represents a cat, a specific kind of {@link Pet}.
 */
 class Cat extends Pet {

    /**
     * Default constructor.
     */
    public Cat() {
        super();
    }

    /**
     * Makes the cat meow.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}