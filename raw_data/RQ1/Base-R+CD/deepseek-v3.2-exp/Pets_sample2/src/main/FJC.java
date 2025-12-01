import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a house that can contain multiple pets (dogs and cats).
 * Provides functionality to manage pets including adding, removing,
 * and retrieving information about pets in the house.
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
     * Returns an empty list if there are no pets.
     * 
     * @return List of all pet names in the house
     */
    public List<String> retrievePetNames() {
        return pets.stream()
                  .map(Pet::getName)
                  .collect(Collectors.toList());
    }

    /**
     * Adds a pet to the house. Returns false if the pet already belongs to 
     * any house (including this one) or if the pet does not have a name; 
     * returns true if the pet is added successfully.
     * 
     * @param pet The pet to be added to the house
     * @return true if pet is added successfully, false otherwise
     */
    public boolean addPet(Pet pet) {
        if (pet == null || pet.getName() == null || pet.getName().trim().isEmpty()) {
            return false;
        }
        
        // Check if pet already belongs to any house
        if (pet.getHouse() != null) {
            return false;
        }
        
        // Check if pet with same name already exists in this house
        boolean nameExists = pets.stream()
                               .anyMatch(p -> p.getName().equals(pet.getName()));
        if (nameExists) {
            return false;
        }
        
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from this house. Returns false if the pet 
     * does not belong to this house; returns true if it is removed successfully.
     * 
     * @param pet The pet to be removed from the house
     * @return true if pet is removed successfully, false otherwise
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
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     * Returns an empty list if there are no pets of the specified type.
     * 
     * @param type The type of pet to retrieve ("dog" or "cat")
     * @return List of pets of the specified type in the house
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null || (!type.equalsIgnoreCase("dog") && !type.equalsIgnoreCase("cat"))) {
            return new ArrayList<>();
        }
        
        return pets.stream()
                  .filter(pet -> {
                      if (type.equalsIgnoreCase("dog")) {
                          return pet instanceof Dog;
                      } else if (type.equalsIgnoreCase("cat")) {
                          return pet instanceof Cat;
                      }
                      return false;
                  })
                  .collect(Collectors.toList());
    }

    /**
     * Counts the number of pets in this house. Returns 0 if there are no pets.
     * 
     * @return The number of pets in the house
     */
    public int getPetCount() {
        return pets.size();
    }

    /**
     * Gets the list of pets in this house.
     * 
     * @return List of pets in the house
     */
    public List<Pet> getPets() {
        return new ArrayList<>(pets);
    }

    /**
     * Sets the list of pets for this house.
     * 
     * @param pets The new list of pets for the house
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets != null ? new ArrayList<>(pets) : new ArrayList<>();
    }
}

/**
 * Abstract base class representing a pet with a name and house association.
 * Provides common functionality for all pet types.
 */
abstract class Pet {
    private String name;
    private House house;

    /**
     * Default constructor for Pet.
     */
    public Pet() {
    }

    /**
     * Constructor for Pet with name.
     * 
     * @param name The name of the pet
     */
    public Pet(String name) {
        this.name = name;
    }

    /**
     * Makes the pet produce its characteristic noise.
     * This method must be implemented by concrete pet classes.
     */
    public abstract void makeNoise();

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
     * @param name The new name for the pet
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the house where the pet lives.
     * 
     * @return The house object where the pet lives, or null if no house
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house where the pet lives.
     * 
     * @param house The house where the pet will live
     */
    public void setHouse(House house) {
        this.house = house;
    }
}

/**
 * Represents a dog, which is a type of pet.
 * Dogs make barking noises.
 */
class Dog extends Pet {
    /**
     * Default constructor for Dog.
     */
    public Dog() {
    }

    /**
     * Constructor for Dog with name.
     * 
     * @param name The name of the dog
     */
    public Dog(String name) {
        super(name);
    }

    /**
     * Makes the dog produce a barking noise.
     */
    @Override
    public void makeNoise() {
        System.out.println(getName() + " says: Woof! Woof!");
    }
}

/**
 * Represents a cat, which is a type of pet.
 * Cats make meowing noises.
 */
class Cat extends Pet {
    /**
     * Default constructor for Cat.
     */
    public Cat() {
    }

    /**
     * Constructor for Cat with name.
     * 
     * @param name The name of the cat
     */
    public Cat(String name) {
        super(name);
    }

    /**
     * Makes the cat produce a meowing noise.
     */
    @Override
    public void makeNoise() {
        System.out.println(getName() + " says: Meow! Meow!");
    }
}