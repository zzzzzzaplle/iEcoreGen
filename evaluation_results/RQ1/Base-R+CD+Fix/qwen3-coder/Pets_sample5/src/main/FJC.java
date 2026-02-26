import java.util.ArrayList;
import java.util.List;

class House {
    private List<Pet> pets;

    /**
     * Default constructor for House
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     * 
     * @return A list of pet names, or an empty list if there are no pets
     */
    public List<String> retrievePetNames() {
        List<String> petNames = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getName() != null) {
                petNames.add(pet.getName());
            }
        }
        return petNames;
    }

    /**
     * Adds a pet to this house.
     * 
     * @param pet The pet to be added
     * @return false if the pet already belongs to any house (including this one) or the pet does not have a name; 
     *         true if the pet is added successfully
     */
    public boolean addPet(Pet pet) {
        // Check if pet already belongs to a house
        if (pet.getLived() != null) {
            return false;
        }
        
        // Check if pet has a name
        if (pet.getName() == null || pet.getName().isEmpty()) {
            return false;
        }
        
        // Add pet to this house
        pets.add(pet);
        pet.setLived(this);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     * 
     * @param pet The pet to be removed
     * @return false if the pet does not belong to this house; true if it is removed successfully
     */
    public boolean removePet(Pet pet) {
        // Check if pet belongs to this house
        if (pet.getLived() != this) {
            return false;
        }
        
        // Remove pet from this house
        if (pets.remove(pet)) {
            pet.setLived(null);
            return true;
        }
        
        return false;
    }

    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     * 
     * @param type The type of pets to retrieve ("dog" or "cat")
     * @return A list of pets of the specified type, or an empty list if there are no such pets
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> petsOfType = new ArrayList<>();
        
        for (Pet pet : pets) {
            if (type.equalsIgnoreCase("dog") && pet instanceof Dog) {
                petsOfType.add(pet);
            } else if (type.equalsIgnoreCase("cat") && pet instanceof Cat) {
                petsOfType.add(pet);
            }
        }
        
        return petsOfType;
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

abstract class Pet {
    private String name;
    private House lived;

    /**
     * Default constructor for Pet
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
     * @return true if the name was set successfully, false otherwise
     */
    public boolean setName(String newName) {
        if (newName == null || newName.isEmpty()) {
            return false;
        }
        this.name = newName;
        return true;
    }

    /**
     * Makes this pet make noise.
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

class Dog extends Pet {
    /**
     * Default constructor for Dog
     */
    public Dog() {
        super();
    }

    /**
     * Makes this dog bark.
     */
    @Override
    public void makeNoise() {
        // Dog barks
        System.out.println("Woof!");
    }
}

class Cat extends Pet {
    /**
     * Default constructor for Cat
     */
    public Cat() {
        super();
    }

    /**
     * Makes this cat meow.
     */
    @Override
    public void makeNoise() {
        // Cat meows
        System.out.println("Meow!");
    }
}