import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a generic Pet.
 * Pets have a name and belong to a house.
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
     * Each subclass must implement this method.
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
     * Makes a dog-specific noise.
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
     * Makes a cat-specific noise.
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
     * @return a list of pet names; empty list if there are no pets
     */
    public List<String> getAllPetNames() {
        List<String> names = new ArrayList<>();
        for (Pet pet : pets) {
            names.add(pet.getName());
        }
        return names;
    }

    /**
     * Adds a pet to this house.
     * @param pet the pet to add
     * @return false if the pet already belongs to any house or the pet does not have a name; true if successful
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
        }
        if (pet.getName() == null || pet.getName().trim().isEmpty()) {
            return false;
        }
        if (pet.getHouse() != null) {
            return false;
        }
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     * @param pet the pet to remove
     * @return false if the pet does not belong to this house; true if successful
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
     * @param type the type of pet ("dog" or "cat")
     * @return a list of pets of the specified type; empty list if there are no such pets
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> result = new ArrayList<>();
        if (type == null) {
            return result;
        }
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
     * @return the number of pets; 0 if there are no pets
     */
    public int countPets() {
        return pets.size();
    }
}