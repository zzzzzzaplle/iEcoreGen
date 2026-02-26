import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a house that can contain any number of pets.
 */
 class House {

    /** The list of pets residing in this house. */
    private final List<Pet> pets = new ArrayList<>();

    /**
     * Creates an empty house.
     */
    public House() {
        // No initialization required beyond the field declaration.
    }

    /**
     * Returns an unmodifiable copy of the list of all pets in this house.
     *
     * @return a list of pets living in this house
     */
    public List<Pet> getPets() {
        return Collections.unmodifiableList(new ArrayList<>(pets));
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     * If there are no pets, an empty list is returned.
     *
     * @return a list of pet names
     */
    public List<String> getAllPetNames() {
        List<String> names = new ArrayList<>();
        for (Pet pet : pets) {
            names.add(pet.getName());
        }
        return names;
    }

    /**
     * Adds a pet to this house.
     * <p>
     * A pet can only be added if it does not already belong to any house
     * (including this one) and it has a non‑null, non‑empty name.
     *
     * @param pet the pet to add
     * @return {@code true} if the pet was added successfully,
     *         {@code false} otherwise
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
        }
        if (pet.getName() == null || pet.getName().trim().isEmpty()) {
            return false; // pet must have a name
        }
        if (pet.getHouse() != null) {
            return false; // pet already belongs to a house
        }
        // Assign this house to the pet and add it to the list
        pet.setHouse(this);
        pets.add(pet);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     * <p>
     * The removal succeeds only if the pet currently belongs to this house.
     *
     * @param pet the pet to remove
     * @return {@code true} if the pet was removed successfully,
     *         {@code false} otherwise
     */
    public boolean removePet(Pet pet) {
        if (pet == null) {
            return false;
        }
        if (pet.getHouse() != this) {
            return false; // pet does not belong to this house
        }
        // Remove the pet from the house and clear its house reference
        pets.remove(pet);
        pet.setHouse(null);
        return true;
    }

    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") living in this house.
     * The comparison is case‑insensitive.
     *
     * @param type the type of pet ("dog" or "cat")
     * @return a list of pets of the requested type; empty if none found
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null) {
            return Collections.emptyList();
        }
        List<Pet> result = new ArrayList<>();
        String lower = type.trim().toLowerCase();
        for (Pet pet : pets) {
            if ("dog".equals(lower) && pet instanceof Dog) {
                result.add(pet);
            } else if ("cat".equals(lower) && pet instanceof Cat) {
                result.add(pet);
            }
        }
        return result;
    }

    /**
     * Counts the number of pets currently residing in this house.
     *
     * @return the number of pets; {@code 0} if the house has no pets
     */
    public int countPets() {
        return pets.size();
    }
}

/**
 * Abstract base class representing a generic pet.
 */
abstract class Pet {

    /** The pet's name. */
    private String name;

    /** The house that currently contains this pet. */
    private House house;

    /**
     * Creates a pet with no name and no house.
     */
    public Pet() {
        // Default constructor; fields start as null.
    }

    /**
     * Returns the pet's name.
     *
     * @return the name of the pet
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the pet's name.
     *
     * @param name the new name for the pet
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the house that currently contains this pet.
     *
     * @return the house, or {@code null} if the pet is not in a house
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house that contains this pet.
     *
     * @param house the house to assign; can be {@code null}
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Causes the pet to make its characteristic noise.
     * Implementations should provide the specific noise for each pet type.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog, which is a specific type of pet.
 */
class Dog extends Pet {

    /**
     * Creates a dog with no name and no house.
     */
    public Dog() {
        super();
    }

    /**
     * Makes the dog bark.
     * The dog will print "Woof!" to the console.
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Represents a cat, which is a specific type of pet.
 */
class Cat extends Pet {

    /**
     * Creates a cat with no name and no house.
     */
    public Cat() {
        super();
    }

    /**
     * Makes the cat meow.
     * The cat will print "Meow!" to the console.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}