import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a house that can contain multiple pets.
 * Provides methods to manage and query the pets living in the house.
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
     * @return a list containing the names of all pets in the house, 
     *         or an empty list if there are no pets
     */
    public List<String> retrievePetNames() {
        return pets.stream()
                   .map(Pet::getName)
                   .collect(Collectors.toList());
    }

    /**
     * Adds a pet to this house.
     * 
     * @param pet the pet to be added to the house
     * @return false if the pet already belongs to any house (including this one) 
     *         or the pet does not have a name; true if the pet is added successfully
     */
    public boolean addPet(Pet pet) {
        if (pet == null || pet.getName() == null || pet.getName().trim().isEmpty()) {
            return false;
        }
        
        if (pet.getLived() != null) {
            return false;
        }
        
        pets.add(pet);
        pet.setLived(this);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     * 
     * @param pet the pet to be removed from the house
     * @return false if the pet does not belong to this house; 
     *         true if it is removed successfully
     */
    public boolean removePet(Pet pet) {
        if (pet == null || pet.getLived() != this) {
            return false;
        }
        
        boolean removed = pets.remove(pet);
        if (removed) {
            pet.setLived(null);
        }
        return removed;
    }

    /**
     * Retrieves a list of pets of a specific type that live in this house.
     * 
     * @param type the type of pet to filter by ("dog" or "cat")
     * @return a list containing pets of the specified type in the house,
     *         or an empty list if there are no pets of that type
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
     * Counts the number of pets in this house.
     * 
     * @return the number of pets in the house, or 0 if there are no pets
     */
    public int getPetCount() {
        return pets.size();
    }

    /**
     * Gets the list of pets living in this house.
     * 
     * @return the list of pets in this house
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the list of pets living in this house.
     * 
     * @param pets the new list of pets for this house
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
     * Constructs a new Pet with no name and no house.
     */
    public Pet() {
        this.name = null;
        this.lived = null;
    }

    /**
     * Gets the name of this pet.
     * 
     * @return the name of the pet
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this pet.
     * 
     * @param newName the new name for the pet
     * @return true if the name was set successfully, false if the new name is null or empty
     */
    public boolean setName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            return false;
        }
        this.name = newName.trim();
        return true;
    }

    /**
     * Gets the house where this pet lives.
     * 
     * @return the house where this pet lives, or null if the pet is homeless
     */
    public House getLived() {
        return lived;
    }

    /**
     * Sets the house where this pet lives.
     * 
     * @param lived the house where this pet will live
     */
    public void setLived(House lived) {
        this.lived = lived;
    }

    /**
     * Makes the pet produce its characteristic noise.
     * This method must be implemented by concrete subclasses.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog, which is a type of pet.
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
 * Represents a cat, which is a type of pet.
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