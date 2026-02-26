import java.util.ArrayList;
import java.util.List;

/**
 * Represents a house that can contain pets (dogs and cats)
 */
 class House {
    private List<Pet> pets;

    /**
     * Default constructor initializes an empty list of pets
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house
     * @return List of pet names, empty list if there are no pets
     */
    public List<String> retrievePetNames() {
        List<String> petNames = new ArrayList<>();
        for (Pet pet : pets) {
            petNames.add(pet.getName());
        }
        return petNames;
    }

    /**
     * Adds a pet to the house
     * @param pet The pet to be added
     * @return false if the pet already belongs to any house or the pet does not have a name; 
     *         true if the pet is added successfully
     */
    public boolean addPet(Pet pet) {
        if (pet == null || pet.getName() == null || pet.getName().trim().isEmpty()) {
            return false;
        }
        
        // Check if pet already belongs to any house
        if (pet.getHouse() != null) {
            return false;
        }
        
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from this house
     * @param pet The pet to be removed
     * @return false if the pet does not belong to this house; true if removed successfully
     */
    public boolean removePet(Pet pet) {
        if (pet == null || !pets.contains(pet)) {
            return false;
        }
        
        pets.remove(pet);
        pet.setHouse(null);
        return true;
    }

    /**
     * Retrieves a list of pets of a specific type that live in this house
     * @param type The type of pet to filter by ("dog" or "cat")
     * @return List of pets of the specified type, empty list if there are no matching pets
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> filteredPets = new ArrayList<>();
        
        if (type == null || (!type.equalsIgnoreCase("dog") && !type.equalsIgnoreCase("cat"))) {
            return filteredPets;
        }
        
        for (Pet pet : pets) {
            if (type.equalsIgnoreCase("dog") && pet instanceof Dog) {
                filteredPets.add(pet);
            } else if (type.equalsIgnoreCase("cat") && pet instanceof Cat) {
                filteredPets.add(pet);
            }
        }
        
        return filteredPets;
    }

    /**
     * Counts the number of pets in this house
     * @return The number of pets, 0 if there are no pets
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

    // Getter and setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public House getHouse() {
        return house;
    }

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
        System.out.println("Woof!");
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