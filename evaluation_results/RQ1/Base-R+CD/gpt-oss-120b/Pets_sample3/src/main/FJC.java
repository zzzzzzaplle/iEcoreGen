import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a house that can contain any number of {@link Pet}s.
 */
 class House {

    /** The collection of pets that live in this house. */
    private List<Pet> pets;

    /**
     * Constructs an empty {@code House}.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Returns an unmodifiable view of the pets currently living in this house.
     *
     * @return the list of pets
     */
    public List<Pet> getPets() {
        return Collections.unmodifiableList(pets);
    }

    /**
     * Sets the internal list of pets. Mainly for testing purposes.
     *
     * @param pets the list of pets to set
     */
    public void setPets(List<Pet> pets) {
        this.pets = (pets != null) ? pets : new ArrayList<>();
        // Ensure each pet knows it belongs to this house
        for (Pet p : this.pets) {
            p.setHouse(this);
        }
    }

    /**
     * Retrieves the names of all pets (dogs and cats) living in this house.
     *
     * @return a list of pet names; empty if no pets are present
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
     *
     * @param pet the pet to add
     * @return {@code true} if the pet was added successfully; {@code false}
     *         if the pet already belongs to a house (including this one) or
     *         the pet has no name
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
        }
        // Name must be non‑null and non‑empty
        if (pet.getName() == null || pet.getName().trim().isEmpty()) {
            return false;
        }
        // Pet must not already belong to a house
        if (pet.getHouse() != null) {
            return false;
        }
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from its current house.
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
            return false;
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
     * @param type the type of pet to retrieve; expected values are
     *             {@code "dog"} or {@code "cat"} (case‑insensitive)
     * @return a list of pets matching the given type; empty if none match
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> result = new ArrayList<>();
        if (type == null) {
            return result;
        }
        String normalized = type.trim().toLowerCase();
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
     * Counts the number of pets living in this house.
     *
     * @return the total number of pets; {@code 0} if none live here
     */
    public int getPetCount() {
        return pets.size();
    }
}

/**
 * Abstract base class for all pets.
 */
public abstract class Pet {

    /** The name of the pet. */
    private String name;

    /** The house this pet belongs to; {@code null} if it lives nowhere. */
    private House house;

    /**
     * No‑argument constructor.
     */
    public Pet() {
        // name remains null until set
    }

    /**
     * Returns the pet's name.
     *
     * @return the name, possibly {@code null}
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the pet's name.
     *
     * @param name the name to set; may be {@code null} or empty
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the house this pet belongs to.
     *
     * @return the {@link House} instance or {@code null} if none
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house this pet belongs to.
     *
     * @param house the house to associate with this pet
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Makes the pet produce its characteristic noise.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog, a concrete type of {@link Pet}.
 */
 class Dog extends Pet {

    /**
     * No‑argument constructor.
     */
    public Dog() {
        super();
    }

    /**
     * Causes the dog to bark.
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Represents a cat, a concrete type of {@link Pet}.
 */
 class Cat extends Pet {

    /**
     * No‑argument constructor.
     */
    public Cat() {
        super();
    }

    /**
     * Causes the cat to meow.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}