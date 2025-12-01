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
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     * 
     * @return A list of pet names, or an empty list if there are no pets.
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
     * @param pet The pet to add.
     * @return false if the pet already belongs to any house (including this one) 
     *         or the pet does not have a name; true if the pet is added successfully.
     */
    public boolean addPet(Pet pet) {
        // Check if pet already belongs to a house or doesn't have a name
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
     * @param pet The pet to remove.
     * @return false if the pet does not belong to this house; true if it is removed successfully.
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
     * Retrieves a list of pets of a specific type that live in this house.
     * 
     * @param type The type of pet to retrieve ("dog" or "cat").
     * @return A list of pets of the specified type, or an empty list if there are no matching pets.
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
     * @return The number of pets in this house, or 0 if there are no pets.
     */
    public int getPetCount() {
        return pets.size();
    }

    /**
     * Gets the list of pets in this house.
     * 
     * @return The list of pets.
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the list of pets in this house.
     * 
     * @param pets The new list of pets.
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

/**
 * Abstract class representing a pet that can live in a house.
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
     * @return The name of this pet.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this pet.
     * 
     * @param newName The new name for this pet.
     * @return true if the name was set successfully, false otherwise.
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
     * @return The house where this pet lives.
     */
    public House getLived() {
        return lived;
    }

    /**
     * Sets the house where this pet lives.
     * 
     * @param lived The house where this pet will live.
     */
    public void setLived(House lived) {
        this.lived = lived;
    }
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

    /**
     * Makes this dog bark.
     */
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

    /**
     * Makes this cat meow.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}