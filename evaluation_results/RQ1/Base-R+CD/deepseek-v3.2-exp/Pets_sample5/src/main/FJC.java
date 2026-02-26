import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a house that can contain multiple pets (dogs and cats)
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
     * @return List of pet names as strings, empty list if there are no pets
     */
    public List<String> retrievePetNames() {
        return pets.stream()
                   .map(Pet::getName)
                   .collect(Collectors.toList());
    }

    /**
     * Adds a pet to the house
     * @param pet The pet to be added
     * @return false if the pet already belongs to any house or the pet doesn't have a name; 
     *         true if the pet is added successfully
     */
    public boolean addPet(Pet pet) {
        if (pet == null || pet.getName() == null || pet.getName().trim().isEmpty()) {
            return false;
        }
        
        // Check if pet already belongs to a house
        if (pet.getHouse() != null) {
            return false;
        }
        
        // Add pet to this house and set its house reference
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from this house
     * @param pet The pet to be removed
     * @return false if the pet does not belong to this house; true if it is removed successfully
     */
    public boolean removePet(Pet pet) {
        if (pet == null || pet.getHouse() != this) {
            return false;
        }
        
        boolean removed = pets.remove(pet);
        if (removed) {
            pet.setHouse(null);
        }
        return removed;
    }

    /**
     * Retrieves a list of pets of a specific type that live in this house
     * @param type The type of pet to filter by ("dog" or "cat")
     * @return List of pets of the specified type, empty list if there are no pets of that type
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
     * @return The number of pets in the house, 0 if there are no pets
     */
    public int getPetCount() {
        return pets.size();
    }

    /**
     * Gets the list of all pets in the house
     * @return List of all pets in the house
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the list of pets for this house
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
    private House house;

    /**
     * Default constructor
     */
    public Pet() {
    }

    /**
     * Makes the pet produce its characteristic noise
     */
    public abstract void makeNoise();

    /**
     * Gets the name of the pet
     * @return The pet's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet
     * @param name The name to set for the pet
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the house where the pet lives
     * @return The house object where the pet lives, null if the pet doesn't have a house
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house where the pet lives
     * @param house The house to set for the pet
     */
    public void setHouse(House house) {
        this.house = house;
    }
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
        System.out.println("Meow!");
    }
}