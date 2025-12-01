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
     * Adds a pet to the house.
     *
     * @param pet the pet to add
     * @return true if the pet is added successfully, false if the pet already belongs to any house or doesn't have a name
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
     * Removes a specified pet from its current house.
     *
     * @param pet the pet to remove
     * @return true if the pet is removed successfully, false if the pet does not belong to this house
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
     * @return the number of pets in the house, or 0 if there are no pets
     */
    public int getPetCount() {
        return pets.size();
    }

    /**
     * @return the pets
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * @param pets the pets to set
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

/**
 * Represents an abstract pet that can live in a house.
 */
abstract class Pet {
    private String name;
    private House lived;

    /**
     * Constructs an empty Pet.
     */
    public Pet() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet.
     *
     * @param newName the new name to set
     * @return true if the name is set successfully, false if the new name is null
     */
    public boolean setName(String newName) {
        if (newName == null) {
            return false;
        }
        this.name = newName;
        return true;
    }

    /**
     * Makes the pet make a noise.
     */
    public abstract void makeNoise();

    /**
     * @return the lived
     */
    public House getLived() {
        return lived;
    }

    /**
     * @param lived the lived to set
     */
    public void setLived(House lived) {
        this.lived = lived;
    }
}

/**
 * Represents a dog that can live in a house.
 */
class Dog extends Pet {

    /**
     * Constructs an empty Dog.
     */
    public Dog() {
    }

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
     * Constructs an empty Cat.
     */
    public Cat() {
    }

    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}