import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a house that can contain multiple pets.
 * Provides functionality to manage pets living in the house.
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
     * Retrieves all pet names living in this house.
     * 
     * @return a list of pet names as strings; returns empty list if no pets exist
     */
    public List<String> retrievePetNames() {
        return pets.stream()
                   .map(Pet::getName)
                   .collect(Collectors.toList());
    }

    /**
     * Adds a pet to this house if it doesn't already belong to any house and has a valid name.
     * 
     * @param pet the pet to be added
     * @return true if the pet was added successfully, false if pet already has a house or has no name
     */
    public boolean addPet(Pet pet) {
        if (pet == null || pet.getLived() != null || pet.getName() == null || pet.getName().trim().isEmpty()) {
            return false;
        }
        pet.setLived(this);
        pets.add(pet);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     * 
     * @param pet the pet to be removed
     * @return true if the pet was removed successfully, false if pet doesn't belong to this house
     */
    public boolean removePet(Pet pet) {
        if (pet == null || pet.getLived() != this || !pets.contains(pet)) {
            return false;
        }
        pet.setLived(null);
        pets.remove(pet);
        return true;
    }

    /**
     * Retrieves all pets of a specific type living in this house.
     * 
     * @param type the type of pet to filter by ("dog" or "cat")
     * @return a list of pets of the specified type; returns empty list if no matching pets exist
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null) {
            return new ArrayList<>();
        }
        return pets.stream()
                   .filter(pet -> {
                       if ("dog".equalsIgnoreCase(type)) {
                           return pet instanceof Dog;
                       } else if ("cat".equalsIgnoreCase(type)) {
                           return pet instanceof Cat;
                       }
                       return false;
                   })
                   .collect(Collectors.toList());
    }

    /**
     * Counts the number of pets living in this house.
     * 
     * @return the number of pets in the house; returns 0 if no pets exist
     */
    public int getPetCount() {
        return pets.size();
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
     * @param pets the new list of pets
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

/**
 * Abstract base class representing a pet.
 * Defines common properties and behaviors for all pets.
 */
abstract class Pet {
    private String name;
    private House lived;

    /**
     * Constructs a pet with no name and no house.
     */
    public Pet() {
        this.name = null;
        this.lived = null;
    }

    /**
     * Gets the name of the pet.
     * 
     * @return the pet's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet.
     * 
     * @param newName the new name for the pet
     * @return true if the name was set successfully, false if the new name is null or empty
     */
    public boolean setName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            return false;
        }
        this.name = newName;
        return true;
    }

    /**
     * Makes the pet produce its characteristic noise.
     * Implementation is deferred to subclasses.
     */
    public abstract void makeNoise();

    /**
     * Gets the house where the pet lives.
     * 
     * @return the house where the pet lives, or null if homeless
     */
    public House getLived() {
        return lived;
    }

    /**
     * Sets the house where the pet lives.
     * 
     * @param lived the house where the pet will live
     */
    public void setLived(House lived) {
        this.lived = lived;
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