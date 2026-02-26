import java.util.ArrayList;
import java.util.List;

/**
 * Represents a House that can contain pets.
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
     * @param pet The pet to be added
     * @return false if the pet already belongs to any house (including this one) or the pet does not have name;
     *         true if the pet is added successfully
     */
    public boolean addPet(Pet pet) {
        // Check if pet already has a home or doesn't have a name
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
     * @param pet The pet to be removed
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
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     * 
     * @param petType The type of pet to retrieve ("dog" or "cat")
     * @return A list of pets of the specified type, or an empty list if there are no such pets
     */
    public List<Pet> getPetsByType(String petType) {
        List<Pet> result = new ArrayList<>();
        for (Pet pet : pets) {
            if ((petType.equalsIgnoreCase("dog") && pet instanceof Dog) ||
                (petType.equalsIgnoreCase("cat") && pet instanceof Cat)) {
                result.add(pet);
            }
        }
        return result;
    }

    /**
     * Counts the number of pets in this house.
     * 
     * @return The number of pets in this house, or 0 if there are no pets
     */
    public int getPetCount() {
        return pets.size();
    }

    /**
     * Gets the list of pets in this house.
     * 
     * @return The list of pets
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the list of pets in this house.
     * 
     * @param pets The list of pets to set
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

/**
 * Abstract base class for all pets.
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
     * Gets the name of this pet.
     * 
     * @return The name of this pet
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this pet.
     * 
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the house where this pet lives.
     * 
     * @return The house of this pet
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house where this pet lives.
     * 
     * @param house The house to set
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Makes the pet produce its characteristic noise.
     */
    public abstract void makeNoise();
}

/**
 * Represents a Dog, a type of Pet.
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
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Represents a Cat, a type of Pet.
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
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}