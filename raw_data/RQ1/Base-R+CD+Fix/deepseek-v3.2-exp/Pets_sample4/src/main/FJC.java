import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a house that can contain multiple pets.
 * Provides functionality to manage pets in the house.
 */
 class House {
    private List<Pet> pets;

    /**
     * Default constructor initializes an empty list of pets.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     * 
     * @return List of pet names as strings, returns empty list if there are no pets
     */
    public List<String> retrievePetNames() {
        return pets.stream()
                   .map(Pet::getName)
                   .collect(Collectors.toList());
    }

    /**
     * Adds a pet to the house. The pet must not already belong to any house
     * and must have a valid name.
     * 
     * @param pet The pet to be added to the house
     * @return true if the pet is added successfully, false if the pet already 
     *         belongs to any house or doesn't have a name
     */
    public boolean addPet(Pet pet) {
        if (pet == null || pet.getName() == null || pet.getName().trim().isEmpty()) {
            return false;
        }
        
        if (pet.getLived() != null) {
            return false;
        }
        
        pets.add(pet);
        pet.setLived(this);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     * 
     * @param pet The pet to be removed from the house
     * @return true if the pet is removed successfully, false if the pet does 
     *         not belong to this house
     */
    public boolean removePet(Pet pet) {
        if (pet == null || pet.getLived() != this) {
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
     * @param type The type of pet to filter by ("dog" or "cat")
     * @return List of pets of the specified type, returns empty list if there 
     *         are no pets of that type
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null) {
            return new ArrayList<>();
        }
        
        return pets.stream()
                   .filter(pet -> {
                       if ("dog".equalsIgnoreCase(type)) {
                           return pet instanceof Dog;
                       } else if ("cat".equalsIgnoreCase(type)) {
                           return pet instanceof Cat;
                       }
                       return false;
                   })
                   .collect(Collectors.toList());
    }

    /**
     * Counts the number of pets in this house.
     * 
     * @return The number of pets in the house, returns 0 if there are no pets
     */
    public int getPetCount() {
        return pets.size();
    }

    // Getter and setter methods
    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

/**
 * Abstract base class representing a pet.
 * Defines common properties and behaviors for all pets.
 */
abstract class Pet {
    private String name;
    private House lived;

    /**
     * Default constructor.
     */
    public Pet() {
    }

    /**
     * Gets the name of the pet.
     * 
     * @return The name of the pet
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet.
     * 
     * @param newName The new name for the pet
     * @return true if the name was set successfully, false if the new name is null or empty
     */
    public boolean setName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            return false;
        }
        this.name = newName.trim();
        return true;
    }

    /**
     * Abstract method for making noise.
     * Must be implemented by concrete pet classes.
     */
    public abstract void makeNoise();

    // Getter and setter methods
    public House getLived() {
        return lived;
    }

    public void setLived(House lived) {
        this.lived = lived;
    }
}

/**
 * Represents a dog, which is a type of pet.
 */
class Dog extends Pet {
    /**
     * Default constructor.
     */
    public Dog() {
    }

    /**
     * Makes a dog-specific noise.
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Represents a cat, which is a type of pet.
 */
class Cat extends Pet {
    /**
     * Default constructor.
     */
    public Cat() {
    }

    /**
     * Makes a cat-specific noise.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}