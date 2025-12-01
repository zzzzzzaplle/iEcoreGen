import java.util.ArrayList;
import java.util.List;

/**
 * Represents a house that can contain pets.
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
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     * 
     * @return A list of pet names, or an empty list if there are no pets.
     */
    public List<String> retrievePetNames() {
        List<String> petNames = new ArrayList<>();
        for (Pet pet : pets) {
            petNames.add(pet.getName());
        }
        return petNames;
    }

    /**
     * Adds a pet to this house.
     * 
     * @param pet The pet to be added.
     * @return false if the pet already belongs to any house (including this one) 
     *         or the pet does not have a name; true if the pet is added successfully.
     */
    public boolean addPet(Pet pet) {
        // Check if pet already has a house (non-null reference to a house)
        // Also check if pet has a name
        if (pet.getName() == null || pet.getName().isEmpty()) {
            return false;
        }
        
        // If pet is already in this house, return false
        if (this.pets.contains(pet)) {
            return false;
        }
        
        // Add the pet to this house
        this.pets.add(pet);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     * 
     * @param pet The pet to be removed.
     * @return false if the pet does not belong to this house; true if it is removed successfully.
     */
    public boolean removePet(Pet pet) {
        return this.pets.remove(pet);
    }

    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     * 
     * @param type The type of pets to retrieve ("dog" or "cat").
     * @return A list of pets of the specified type, or an empty list if there are no such pets.
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> result = new ArrayList<>();
        for (Pet pet : pets) {
            if (type.equalsIgnoreCase("dog") && pet instanceof Dog) {
                result.add(pet);
            } else if (type.equalsIgnoreCase("cat") && pet instanceof Cat) {
                result.add(pet);
            }
        }
        return result;
    }

    /**
     * Counts the number of pets in this house.
     * 
     * @return The number of pets in this house, or 0 if there are no pets.
     */
    public int getPetCount() {
        return this.pets.size();
    }

    /**
     * Gets the list of pets in this house.
     * 
     * @return The list of pets.
     */
    public List<Pet> getPets() {
        return new ArrayList<>(this.pets);
    }
}

/**
 * Abstract class representing a pet with a name.
 */
abstract class Pet {
    private String name;

    /**
     * Constructs a new Pet with no name.
     */
    public Pet() {
        this.name = null;
    }

    /**
     * Gets the name of the pet.
     * 
     * @return The name of the pet, or null if the pet has no name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet.
     * 
     * @param name The name to set for the pet.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Makes the pet produce its characteristic noise.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog, which is a type of pet.
 */
class Dog extends Pet {
    /**
     * Constructs a new Dog with no name.
     */
    public Dog() {
        super();
    }

    /**
     * Makes the dog bark.
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
     * Constructs a new Cat with no name.
     */
    public Cat() {
        super();
    }

    /**
     * Makes the cat meow.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}