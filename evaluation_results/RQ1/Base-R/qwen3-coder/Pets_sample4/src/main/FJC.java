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
     * Retrieves a list of all pet names living in this house.
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
     * @return false if the pet already belongs to any house or doesn't have a name; true if added successfully
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
     * @param pet The pet to be removed
     * @return false if the pet does not belong to this house; true if removed successfully
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
     * Retrieves a list of pets of a specific type living in this house.
     * 
     * @param petType The type of pet ("dog" or "cat")
     * @return A list of pets of the specified type, or an empty list if there are no matching pets
     */
    public List<Pet> getPetsByType(String petType) {
        List<Pet> result = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getType().equals(petType)) {
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
     * @param pets The new list of pets
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

/**
 * Abstract base class representing a pet.
 */
abstract class Pet {
    protected String name;
    protected House house;

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
     * @param name The new name for this pet
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
     * @param house The new house for this pet
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Gets the type of this pet.
     * 
     * @return The type of this pet ("dog" or "cat")
     */
    public abstract String getType();

    /**
     * Makes the pet produce its characteristic noise.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog, a type of pet.
 */
class Dog extends Pet {
    /**
     * Constructs a new Dog with no name and no house.
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
     * Constructs a new Cat with no name and no house.
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