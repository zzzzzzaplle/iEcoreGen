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
     * Constructs a new House with an empty list of pets.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Gets the list of pets in this house.
     * @return List of pets in the house
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the list of pets for this house.
     * @param pets The new list of pets
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     * Returns an empty list if there are no pets.
     * @return List of pet names as strings
     */
    public List<String> retrievePetNames() {
        return pets.stream()
                   .map(Pet::getName)
                   .collect(Collectors.toList());
    }

    /**
     * Adds a pet to the house.
     * Returns false if the pet already belongs to any house (including this one)
     * or the pet does not have a name; returns true if the pet is added successfully.
     * @param pet The pet to add to the house
     * @return true if pet was added successfully, false otherwise
     */
    public boolean addPet(Pet pet) {
        if (pet == null || pet.getName() == null || pet.getName().isEmpty() || pet.getHouse() != null) {
            return false;
        }
        
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from its current house.
     * Returns false if the pet does not belong to this house; 
     * returns true if it is removed successfully.
     * @param pet The pet to remove from the house
     * @return true if pet was removed successfully, false otherwise
     */
    public boolean removePet(Pet pet) {
        if (pet == null || pet.getHouse() != this || !pets.contains(pet)) {
            return false;
        }
        
        pets.remove(pet);
        pet.setHouse(null);
        return true;
    }

    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     * Returns an empty list if there are no pets of the specified type.
     * @param type The type of pet to retrieve ("dog" or "cat")
     * @return List of pets of the specified type
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null) {
            return new ArrayList<>();
        }
        
        return pets.stream()
                   .filter(pet -> {
                       if ("dog".equalsIgnoreCase(type) && pet instanceof Dog) {
                           return true;
                       }
                       if ("cat".equalsIgnoreCase(type) && pet instanceof Cat) {
                           return true;
                       }
                       return false;
                   })
                   .collect(Collectors.toList());
    }

    /**
     * Counts the number of pets in this house.
     * Returns 0 if there are no pets.
     * @return The number of pets in the house
     */
    public int getPetCount() {
        return pets.size();
    }
}

/**
 * Abstract base class representing a pet.
 * Defines common properties and behaviors for all pets.
 */
abstract class Pet {
    private String name;
    private House house;

    /**
     * Constructs a new Pet with no name and no house.
     */
    public Pet() {
        this.name = null;
        this.house = null;
    }

    /**
     * Gets the name of the pet.
     * @return The pet's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet.
     * @param name The new name for the pet
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the house where the pet lives.
     * @return The house where the pet lives, or null if homeless
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house where the pet lives.
     * @param house The house where the pet lives
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Makes the pet produce its characteristic noise.
     * This method must be implemented by concrete pet classes.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog, which is a type of pet.
 */
class Dog extends Pet {
    /**
     * Constructs a new Dog with no name and no house.
     */
    public Dog() {
        super();
    }

    /**
     * Makes the dog bark.
     * Implements the abstract method from Pet class.
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof! Woof!");
    }
}

/**
 * Represents a cat, which is a type of pet.
 */
class Cat extends Pet {
    /**
     * Constructs a new Cat with no name and no house.
     */
    public Cat() {
        super();
    }

    /**
     * Makes the cat meow.
     * Implements the abstract method from Pet class.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow! Meow!");
    }
}