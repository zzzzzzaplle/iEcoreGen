import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a pet.
 */
abstract class Pet {
    private String name;
    private House house;

    /**
     * Default constructor for Pet.
     */
    public Pet() {
        this.name = "";
        this.house = null;
    }

    /**
     * Gets the name of the pet.
     *
     * @return the name of the pet
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the house where the pet lives.
     *
     * @return the house of the pet
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house where the pet lives.
     *
     * @param house the house to set
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Makes the pet produce noise.
     */
    public abstract void makeNoise();
}

/**
 * Class representing a dog, which is a type of pet.
 */
class Dog extends Pet {
    /**
     * Default constructor for Dog.
     */
    public Dog() {
        super();
    }

    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Class representing a cat, which is a type of pet.
 */
class Cat extends Pet {
    /**
     * Default constructor for Cat.
     */
    public Cat() {
        super();
    }

    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}

/**
 * Class representing a house that can contain pets.
 */
class House {
    private List<Pet> pets;

    /**
     * Default constructor for House.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Gets the list of pets living in this house.
     *
     * @return the list of pets
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the list of pets living in this house.
     *
     * @param pets the list of pets to set
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     *
     * @return a list of pet names, or an empty list if there are no pets
     */
    public List<String> getAllPetNames() {
        List<String> petNames = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getName() != null && !pet.getName().isEmpty()) {
                petNames.add(pet.getName());
            }
        }
        return petNames;
    }

    /**
     * Adds a pet to this house.
     *
     * @param pet the pet to add
     * @return false if the pet already belongs to any house or doesn't have a name; true if added successfully
     */
    public boolean addPet(Pet pet) {
        // Check if pet already belongs to a house
        if (pet.getHouse() != null) {
            return false;
        }

        // Check if pet has a name
        if (pet.getName() == null || pet.getName().isEmpty()) {
            return false;
        }

        // Add pet to this house
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     *
     * @param pet the pet to remove
     * @return false if the pet does not belong to this house; true if removed successfully
     */
    public boolean removePet(Pet pet) {
        // Check if pet belongs to this house
        if (pet.getHouse() != this) {
            return false;
        }

        // Remove pet from this house
        if (pets.remove(pet)) {
            pet.setHouse(null);
            return true;
        }
        return false;
    }

    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     *
     * @param petType the type of pet to retrieve ("dog" or "cat")
     * @return a list of pets of the specified type, or an empty list if there are no such pets
     */
    public List<Pet> getPetsByType(String petType) {
        List<Pet> result = new ArrayList<>();
        for (Pet pet : pets) {
            if ("dog".equalsIgnoreCase(petType) && pet instanceof Dog) {
                result.add(pet);
            } else if ("cat".equalsIgnoreCase(petType) && pet instanceof Cat) {
                result.add(pet);
            }
        }
        return result;
    }

    /**
     * Counts the number of pets in this house.
     *
     * @return the number of pets in this house, or 0 if there are no pets
     */
    public int countPets() {
        return pets.size();
    }
}