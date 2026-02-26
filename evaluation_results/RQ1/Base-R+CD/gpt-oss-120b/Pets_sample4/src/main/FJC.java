import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a house that can contain any number of pets (dogs and cats).
 */
 class House {

    /** List holding all pets that live in this house. */
    private List<Pet> pets;

    /** Unparameterized constructor that creates an empty list of pets. */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves the names of all pets (dogs and cats) living in this house.
     *
     * @return a list of pet names; if there are no pets an empty list is returned
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
     * The method fails (returns {@code false}) if:
     * <ul>
     *   <li>the pet already belongs to any house (including this one), or</li>
     *   <li>the pet does not have a name (null or empty).</li>
     * </ul>
     *
     * @param pet the pet to be added
     * @return {@code true} if the pet was added successfully; {@code false} otherwise
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
        }
        // name must be non‑null and non‑empty
        if (pet.getName() == null || pet.getName().trim().isEmpty()) {
            return false;
        }
        // pet must not already belong to a house
        if (pet.getHouse() != null) {
            return false;
        }
        // add pet and set its house reference
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from its current house.
     * <p>
     * The method fails (returns {@code false}) if the pet does not belong to this house.
     *
     * @param pet the pet to be removed
     * @return {@code true} if the pet was removed successfully; {@code false} otherwise
     */
    public boolean removePet(Pet pet) {
        if (pet == null) {
            return false;
        }
        // pet must belong to this house
        if (!pets.contains(pet) || pet.getHouse() != this) {
            return false;
        }
        pets.remove(pet);
        pet.setHouse(null);
        return true;
    }

    /**
     * Retrieves all pets of a specific type that live in this house.
     *
     * @param type the type of pet to filter by; accepted values are "dog" or "cat"
     * @return a list of pets matching the requested type; an empty list if none match
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null) {
            return Collections.emptyList();
        }
        String lower = type.trim().toLowerCase();
        List<Pet> result = new ArrayList<>();
        for (Pet pet : pets) {
            if (("dog".equals(lower) && pet instanceof Dog) ||
                ("cat".equals(lower) && pet instanceof Cat)) {
                result.add(pet);
            }
        }
        return result;
    }

    /**
     * Counts the total number of pets living in this house.
     *
     * @return the number of pets; 0 if there are none
     */
    public int getPetCount() {
        return pets.size();
    }

    // -----------------------------------------------------------------
    // Getters and Setters for the private field 'pets'
    // -----------------------------------------------------------------

    /**
     * Returns the internal list of pets. The returned list is the actual
     * list used by the house, so modifications affect the house directly.
     *
     * @return the list of pets
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Replaces the current list of pets with the provided list.
     *
     * @param pets the new list of pets; if {@code null} an empty list is set
     */
    public void setPets(List<Pet> pets) {
        this.pets = (pets != null) ? pets : new ArrayList<>();
        // ensure house reference consistency
        for (Pet pet : this.pets) {
            pet.setHouse(this);
        }
    }
}

/**
 * Abstract representation of a pet. Each pet has a name and belongs to exactly
 * one house (or none). Sub‑classes must implement {@link #makeNoise()}.
 */
public abstract class Pet {

    /** The pet's name. */
    private String name;

    /** Reference to the house this pet belongs to; may be {@code null}. */
    private House house;

    /** Unparameterized constructor. */
    public Pet() {
        // default constructor
    }

    /**
     * Makes the characteristic noise of the pet.
     */
    public abstract void makeNoise();

    // -----------------------------------------------------------------
    // Getters and Setters
    // -----------------------------------------------------------------

    /**
     * Returns the name of the pet.
     *
     * @return the pet's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the pet's name.
     *
     * @param name the name to assign; may be {@code null} or empty
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the house this pet belongs to.
     *
     * @return the associated {@link House}, or {@code null} if none
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house this pet belongs to.
     *
     * @param house the house to associate with this pet; may be {@code null}
     */
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

    /**
     * Makes a barking noise.
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

    /** Unparameterized constructor. */
    public Cat() {
        super();
    }

    /**
     * Makes a meowing noise.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}