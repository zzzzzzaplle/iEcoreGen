import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a house that can contain pets (dogs and cats)
 */
 class House {
    private List<Pet> pets;

    /**
     * Default constructor that initializes an empty list of pets
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house
     * 
     * @return List of pet names as strings, returns empty list if there are no pets
     */
    public List<String> retrievePetNames() {
        return pets.stream()
                   .map(Pet::getName)
                   .collect(Collectors.toList());
    }

    /**
     * Adds a pet to the house
     * 
     * @param pet The pet to be added to the house
     * @return false if the pet already belongs to any house or the pet does not have name; 
     *         true if the pet is added successfully
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
     * Removes a specified pet from this house
     * 
     * @param pet The pet to be removed from the house
     * @return false if the pet does not belong to this house; true if it is removed successfully
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
     * Retrieves a list of pets of a specific type that live in this house
     * 
     * @param type The type of pet to retrieve ("dog" or "cat")
     * @return List of pets of the specified type, returns empty list if there are no pets
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
     * Counts the number of pets in this house
     * 
     * @return The number of pets in the house, returns 0 if there are no pets
     */
    public int getPetCount() {
        return pets.size();
    }

    /**
     * Gets the list of pets in this house
     * 
     * @return The list of pets
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the list of pets for this house
     * 
     * @param pets The list of pets to set
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

/**
 * Abstract base class representing a pet
 */
abstract class Pet {
    private String name;
    private House lived;

    /**
     * Default constructor
     */
    public Pet() {
    }

    /**
     * Gets the name of the pet
     * 
     * @return The pet's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet
     * 
     * @param newName The new name for the pet
     * @return true if the name was set successfully, false if the name is null or empty
     */
    public boolean setName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            return false;
        }
        this.name = newName.trim();
        return true;
    }

    /**
     * Gets the house where the pet lives
     * 
     * @return The house where the pet lives, or null if the pet has no home
     */
    public House getLived() {
        return lived;
    }

    /**
     * Sets the house where the pet lives
     * 
     * @param lived The house where the pet lives
     */
    public void setLived(House lived) {
        this.lived = lived;
    }

    /**
     * Makes the pet produce its characteristic noise
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog pet
 */
class Dog extends Pet {
    /**
     * Default constructor
     */
    public Dog() {
    }

    /**
     * Makes the dog bark
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof! Woof!");
    }
}

/**
 * Represents a cat pet
 */
class Cat extends Pet {
    /**
     * Default constructor
     */
    public Cat() {
    }

    /**
     * Makes the cat meow
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow! Meow!");
    }
}