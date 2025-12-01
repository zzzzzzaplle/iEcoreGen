import java.util.ArrayList;
import java.util.List;

/**
 * Represents a house that can contain pets (dogs and cats).
 * Provides functionality to manage pets in the house.
 */
 class House {
    private List<Pet> pets;

    /**
     * Constructs an empty house with no pets.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     * 
     * @return a list of all pet names; returns an empty list if there are no pets
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
     * @param pet the pet to be added
     * @return false if the pet already belongs to any house (including this one) 
     *         or the pet does not have a name; true if the pet is added successfully
     */
    public boolean addPet(Pet pet) {
        if (pet == null || pet.getName() == null || pet.getName().trim().isEmpty() || pet.getHouse() != null) {
            return false;
        }
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     * 
     * @param pet the pet to be removed
     * @return false if the pet does not belong to this house; true if removed successfully
     */
    public boolean removePet(Pet pet) {
        if (pet == null || !pets.contains(pet) || pet.getHouse() != this) {
            return false;
        }
        pets.remove(pet);
        pet.setHouse(null);
        return true;
    }

    /**
     * Retrieves a list of pets of a specific type that live in this house.
     * 
     * @param type the type of pet to retrieve ("dog" or "cat")
     * @return a list of pets of the specified type; returns an empty list if there are no matching pets
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> result = new ArrayList<>();
        if (type == null) {
            return result;
        }
        
        for (Pet pet : pets) {
            if (("dog".equalsIgnoreCase(type) && pet instanceof Dog) ||
                ("cat".equalsIgnoreCase(type) && pet instanceof Cat)) {
                result.add(pet);
            }
        }
        return result;
    }

    /**
     * Counts the number of pets in this house.
     * 
     * @return the number of pets in the house; returns 0 if there are no pets
     */
    public int countPets() {
        return pets.size();
    }

    // Getters and setters
    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

/**
 * Abstract base class for pets.
 * Defines common properties and behaviors for all pets.
 */
abstract class Pet {
    private String name;
    private House house;

    /**
     * Constructs a pet with no name and no house.
     */
    public Pet() {
        this.name = null;
        this.house = null;
    }

    /**
     * Makes the pet produce its characteristic noise.
     * This method must be implemented by concrete pet classes.
     */
    public abstract void makeNoise();

    // Getters and setters
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
 * Represents a dog, which is a type of pet.
 */
class Dog extends Pet {
    /**
     * Constructs a dog with no name and no house.
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
     * Constructs a cat with no name and no house.
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