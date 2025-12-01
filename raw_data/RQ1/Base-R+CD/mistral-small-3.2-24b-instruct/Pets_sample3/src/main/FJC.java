import java.util.ArrayList;
import java.util.List;

import java.util.Objects;

/**
 * Represents a house that can contain pets.
 */
class House {
    private List<Pet> pets;

    /**
     * Constructs an empty House.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     *
     * @return a list of pet names, or an empty list if there are no pets
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
     * @param pet the pet to add
     * @return true if the pet was added successfully, false otherwise
     */
    public boolean addPet(Pet pet) {
        if (pet == null || pet.getName() == null || pet.getLived() != null) {
            return false;
        }
        pets.add(pet);
        pet.setLived(this);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     *
     * @param pet the pet to remove
     * @return true if the pet was removed successfully, false otherwise
     */
    public boolean removePet(Pet pet) {
        if (pet == null || !pets.contains(pet)) {
            return false;
        }
        pets.remove(pet);
        pet.setLived(null);
        return true;
    }

    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     *
     * @param type the type of pet to retrieve ("dog" or "cat")
     * @return a list of pets of the specified type, or an empty list if there are no pets of that type
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> petsByType = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getClass().getSimpleName().equalsIgnoreCase(type)) {
                petsByType.add(pet);
            }
        }
        return petsByType;
    }

    /**
     * Counts the number of pets in this house.
     *
     * @return the number of pets in this house, or 0 if there are no pets
     */
    public int getPetCount() {
        return pets.size();
    }

    /**
     * Retrieves the list of pets in this house.
     *
     * @return the list of pets
     */
    public List<Pet> getPets() {
        return pets;
    }
}

/**
 * Represents an abstract pet that can live in a house.
 */
abstract class Pet {
    private String name;
    private House lived;

    /**
     * Constructs a Pet with no name and no house.
     */
    public Pet() {
        this.name = null;
        this.lived = null;
    }

    /**
     * Retrieves the name of this pet.
     *
     * @return the name of this pet
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this pet.
     *
     * @param newName the new name to set
     * @return true if the name was set successfully, false otherwise
     */
    public boolean setName(String newName) {
        if (newName == null) {
            return false;
        }
        this.name = newName;
        return true;
    }

    /**
     * Retrieves the house where this pet lives.
     *
     * @return the house where this pet lives
     */
    public House getLived() {
        return lived;
    }

    /**
     * Sets the house where this pet lives.
     *
     * @param newLived the new house to set
     */
    public void setLived(House newLived) {
        this.lived = newLived;
    }

    /**
     * Tells this pet to make noise.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog that can live in a house.
 */
class Dog extends Pet {
    /**
     * Constructs a Dog with no name and no house.
     */
    public Dog() {
        super();
    }

    /**
     * Tells this dog to make noise.
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Represents a cat that can live in a house.
 */
class Cat extends Pet {
    /**
     * Constructs a Cat with no name and no house.
     */
    public Cat() {
        super();
    }

    /**
     * Tells this cat to make noise.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}