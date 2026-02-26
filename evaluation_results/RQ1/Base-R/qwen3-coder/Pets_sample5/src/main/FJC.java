import java.util.ArrayList;
import java.util.List;

/**
 * Represents a house that can contain pets.
 */
class House {
    private List<Pet> pets;

    /**
     * Creates a new house with no pets.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     * 
     * @return A list of pet names, or an empty list if there are no pets
     */
    public List<String> getAllPetNames() {
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
     * @return false if the pet already belongs to any house (including this one) or the pet does not have a name;
     *         true if the pet is added successfully
     */
    public boolean addPet(Pet pet) {
        // Check if pet already has a house or doesn't have a name
        if (pet.getHouse() != null || pet.getName() == null || pet.getName().isEmpty()) {
            return false;
        }
        
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     * 
     * @param pet The pet to remove from the house
     * @return false if the pet does not belong to this house; true if it is removed successfully
     */
    public boolean removePet(Pet pet) {
        if (pet.getHouse() != this) {
            return false;
        }
        
        pets.remove(pet);
        pet.setHouse(null);
        return true;
    }

    /**
     * Retrieves a list of pets of a specific type that live in this house.
     * 
     * @param petType The type of pet to retrieve ("dog" or "cat")
     * @return A list of pets of the specified type, or an empty list if there are no such pets
     */
    public List<Pet> getPetsByType(String petType) {
        List<Pet> result = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getType().equalsIgnoreCase(petType)) {
                result.add(pet);
            }
        }
        return result;
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
}

/**
 * Abstract class representing a pet that can live in a house.
 */
abstract class Pet {
    private String name;
    private House house;

    /**
     * Creates a new pet with no name and no house.
     */
    public Pet() {
        this.name = null;
        this.house = null;
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
     * @param name The name to set for the pet
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the house where the pet lives.
     * 
     * @return The house of the pet
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house where the pet lives.
     * 
     * @param house The house to set for the pet
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Gets the type of the pet.
     * 
     * @return The type of the pet as a string
     */
    public abstract String getType();

    /**
     * Makes the pet make its characteristic noise.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog, a type of pet.
 */
class Dog extends Pet {
    /**
     * Creates a new dog with no name and no house.
     */
    public Dog() {
        super();
    }

    @Override
    public String getType() {
        return "dog";
    }

    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Represents a cat, a type of pet.
 */
class Cat extends Pet {
    /**
     * Creates a new cat with no name and no house.
     */
    public Cat() {
        super();
    }

    @Override
    public String getType() {
        return "cat";
    }

    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}