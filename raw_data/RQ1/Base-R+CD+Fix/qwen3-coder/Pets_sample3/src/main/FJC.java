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
    public List<String> retrievePetNames() {
        List<String> names = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getName() != null) {
                names.add(pet.getName());
            }
        }
        return names;
    }

    /**
     * Adds a pet to this house.
     * 
     * @param pet The pet to add
     * @return true if the pet is added successfully, false if the pet already belongs to any house or has no name
     */
    public boolean addPet(Pet pet) {
        // Check if pet already belongs to a house or has no name
        if (pet.getLived() != null || pet.getName() == null || pet.getName().isEmpty()) {
            return false;
        }
        
        pets.add(pet);
        pet.setLived(this);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     * 
     * @param pet The pet to remove
     * @return true if the pet is removed successfully, false if the pet does not belong to this house
     */
    public boolean removePet(Pet pet) {
        if (pet.getLived() != this) {
            return false;
        }
        
        pets.remove(pet);
        pet.setLived(null);
        return true;
    }

    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     * 
     * @param type The type of pets to retrieve ("dog" or "cat")
     * @return A list of pets of the specified type, or an empty list if there are no such pets
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> result = new ArrayList<>();
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
 * Abstract class representing a pet.
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
     * @return The name of this pet
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this pet.
     * 
     * @param newName The new name for this pet
     * @return true if the name is set successfully, false if the new name is null or empty
     */
    public boolean setName(String newName) {
        if (newName == null || newName.isEmpty()) {
            return false;
        }
        this.name = newName;
        return true;
    }

    /**
     * Makes this pet make its characteristic noise.
     */
    public abstract void makeNoise();

    /**
     * Gets the house where this pet lives.
     * 
     * @return The house where this pet lives
     */
    public House getLived() {
        return lived;
    }

    /**
     * Sets the house where this pet lives.
     * 
     * @param lived The house where this pet lives
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
     * Constructs a new Dog with no name and no house.
     */
    public Dog() {
        super();
    }

    /**
     * Makes this dog bark.
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
     * Makes this cat meow.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}