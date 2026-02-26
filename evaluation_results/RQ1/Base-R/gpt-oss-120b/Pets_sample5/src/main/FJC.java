import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract representation of a pet that can live in a {@link House}.
 * Every pet has a name and a reference to the house it lives in (its home).
 */
public abstract class Pet {

    /** The pet's name. */
    private String name;

    /** The house this pet belongs to. Null if the pet does not belong to any house. */
    private House house;

    /** Unparameterized constructor. */
    public Pet() {
        // default constructor
    }

    /**
     * Makes the pet produce its characteristic noise.
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
 * Concrete implementation of a dog.
 */
 class Dog extends Pet {

    /** Unparameterized constructor. */
    public Dog() {
        super();
    }

    /** {@inheritDoc} */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Concrete implementation of a cat.
 */
 class Cat extends Pet {

    /** Unparameterized constructor. */
    public Cat() {
        super();
    }

    /** {@inheritDoc} */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}

/**
 * Represents a house that can contain any number of pets (dogs and cats).
 */
 class House {

    /** Internal storage for the pets living in this house. */
    private List<Pet> pets;

    /** Unparameterized constructor. */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves the names of all pets (dogs and cats) living in this house.
     *
     * @return a list containing the names of all pets; the list is empty if there are no pets.
     */
    public List<String> getAllPetNames() {
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
     * The operation fails and returns {@code false} if:
     * <ul>
     *   <li>the pet already belongs to any house (including this one),</li>
     *   <li>the pet's name is {@code null} or empty, or</li>
     *   <li>the pet argument itself is {@code null}.</li>
     * </ul>
     * Otherwise, the pet is added, its {@link Pet#setHouse(House)} reference is updated,
     * and the method returns {@code true}.
     *
     * @param pet the pet to add
     * @return {@code true} if the pet was added successfully; {@code false} otherwise
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
        }
        // Name must be non‑null and non‑empty (ignoring surrounding whitespace)
        String name = pet.getName();
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // Pet must not already belong to a house
        if (pet.getHouse() != null) {
            return false;
        }
        // Prevent duplicate entries of the same pet instance
        if (pets.contains(pet)) {
            return false;
        }
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from its current house.
     * <p>
     * The operation fails and returns {@code false} if the pet does not belong to this house.
     * If successful, the pet is removed from the internal collection, its {@link Pet#setHouse(House)}
     * reference is cleared (set to {@code null}), and the method returns {@code true}.
     *
     * @param pet the pet to remove
     * @return {@code true} if the pet was removed successfully; {@code false} otherwise
     */
    public boolean removePet(Pet pet) {
        if (pet == null) {
            return false;
        }
        // The pet must be associated with this house
        if (pet.getHouse() != this) {
            return false;
        }
        boolean removed = pets.remove(pet);
        if (removed) {
            pet.setHouse(null);
        }
        return removed;
    }

    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     *
     * @param type the type of pet to retrieve; must be either "dog" or "cat" (case‑insensitive)
     * @return a list of pets matching the requested type; the list is empty if there are none
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> result = new ArrayList<>();
        if (type == null) {
            return result;
        }
        String lowered = type.trim().toLowerCase();
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
     * @return the total number of pets; {@code 0} if there are none
     */
    public int countPets() {
        return pets.size();
    }

    /** @return an unmodifiable view of the pets currently in the house */
    public List<Pet> getPets() {
        return Collections.unmodifiableList(pets);
    }

    /** @param pets the internal list of pets to set (used mainly for testing) */
    public void setPets(List<Pet> pets) {
        this.pets = pets != null ? pets : new ArrayList<>();
        // Ensure each pet knows it belongs to this house
        for (Pet pet : this.pets) {
            pet.setHouse(this);
        }
    }
}