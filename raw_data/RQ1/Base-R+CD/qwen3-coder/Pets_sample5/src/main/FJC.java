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
     * @param pet The pet to add to the house
     * @return true if the pet is added successfully, false if the pet already belongs to any house or doesn't have a name
     */
    public boolean addPet(Pet pet) {
        // Check if pet already has a house (name check is done in Pet class)
        if (pet.getName() == null || pet.getName().isEmpty()) {
            return false;
        }
        
        // Check if pet already belongs to this house
        if (pets.contains(pet)) {
            return false;
        }
        
        // For this implementation, we're assuming a pet can only belong to one house
        // In a more complete implementation, we might need a reference from Pet to House
        pets.add(pet);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     * 
     * @param pet The pet to remove from the house
     * @return true if the pet is removed successfully, false if the pet does not belong to this house
     */
    public boolean removePet(Pet pet) {
        return pets.remove(pet);
    }

    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     * 
     * @param type The type of pets to retrieve ("dog" or "cat")
     * @return A list of pets of the specified type, or an empty list if there are no such pets
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> petsOfType = new ArrayList<>();
        for (Pet pet : pets) {
            if ((type.equalsIgnoreCase("dog") && pet instanceof Dog) ||
                (type.equalsIgnoreCase("cat") && pet instanceof Cat)) {
                petsOfType.add(pet);
            }
        }
        return petsOfType;
    }

    /**
     * Counts the number of pets in this house.
     * 
     * @return The number of pets in the house, or 0 if there are no pets
     */
    public int getPetCount() {
        return pets.size();
    }

    /**
     * Gets the list of all pets in this house.
     * 
     * @return The list of pets
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the list of pets for this house.
     * 
     * @param pets The list of pets to set
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

/**
 * Abstract class representing a pet.
 */
abstract class Pet {
    protected String name;

    /**
     * Constructs a new Pet with no name.
     */
    public Pet() {
        this.name = "";
    }

    /**
     * Makes the pet make its characteristic noise.
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
     * @param name The name to set for the pet
     */
    public void setName(String name) {
        this.name = name;
    }
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