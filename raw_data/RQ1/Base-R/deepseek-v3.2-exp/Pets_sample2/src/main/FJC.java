import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a generic Pet.
 * Pets have a name and can belong to a house.
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
     * Gets the name of the pet.
     * @return the name of the pet
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the house where the pet lives.
     * @return the house of the pet
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house where the pet lives.
     * @param house the house to set
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Abstract method for making noise.
     * Each concrete pet type must implement this.
     */
    public abstract void makeNoise();
}

/**
 * Class representing a Dog, which is a type of Pet.
 */
class Dog extends Pet {
    /**
     * Default constructor for Dog.
     */
    public Dog() {
    }

    /**
     * Makes dog-specific noise.
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Class representing a Cat, which is a type of Pet.
 */
class Cat extends Pet {
    /**
     * Default constructor for Cat.
     */
    public Cat() {
    }

    /**
     * Makes cat-specific noise.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}

/**
 * Class representing a House that can contain pets.
 */
class House {
    private List<Pet> pets;

    /**
     * Default constructor for House.
     * Initializes an empty list of pets.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Gets the list of pets in the house.
     * @return the list of pets
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the list of pets in the house.
     * @param pets the list of pets to set
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     * @return list of all pet names, empty list if there are no pets
     */
    public List<String> getAllPetNames() {
        List<String> petNames = new ArrayList<>();
        for (Pet pet : pets) {
            petNames.add(pet.getName());
        }
        return petNames;
    }

    /**
     * Adds a pet to the house.
     * @param pet the pet to add
     * @return false if the pet already belongs to any house (including this one) 
     *         or the pet does not have name; true if the pet is added successfully
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
        }
        
        // Check if pet already has a house
        if (pet.getHouse() != null) {
            return false;
        }
        
        // Check if pet has a name
        if (pet.getName() == null || pet.getName().trim().isEmpty()) {
            return false;
        }
        
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     * @param pet the pet to remove
     * @return false if the pet does not belong to this house; true if removed successfully
     */
    public boolean removePet(Pet pet) {
        if (pet == null) {
            return false;
        }
        
        if (!pets.contains(pet) || pet.getHouse() != this) {
            return false;
        }
        
        pets.remove(pet);
        pet.setHouse(null);
        return true;
    }

    /**
     * Retrieves a list of pets of a specific type that live in this house.
     * @param petType the type of pet ("dog" or "cat")
     * @return list of pets of the specified type, empty list if there are no matching pets
     */
    public List<Pet> getPetsByType(String petType) {
        List<Pet> filteredPets = new ArrayList<>();
        
        if (petType == null || petType.trim().isEmpty()) {
            return filteredPets;
        }
        
        String type = petType.toLowerCase();
        
        for (Pet pet : pets) {
            if (("dog".equals(type) && pet instanceof Dog) || 
                ("cat".equals(type) && pet instanceof Cat)) {
                filteredPets.add(pet);
            }
        }
        
        return filteredPets;
    }

    /**
     * Counts the number of pets in this house.
     * @return the number of pets, 0 if there are no pets
     */
    public int countPets() {
        return pets.size();
    }
}