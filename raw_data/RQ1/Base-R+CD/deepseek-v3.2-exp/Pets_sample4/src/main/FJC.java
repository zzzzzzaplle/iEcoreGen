import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a house that can contain pets.
 * A house may have any number of pets living in it.
 */
 class House {
    private List<Pet> pets;
    
    /**
     * Default constructor that initializes an empty list of pets.
     */
    public House() {
        this.pets = new ArrayList<>();
    }
    
    /**
     * Gets the list of pets in this house.
     * @return the list of pets in this house
     */
    public List<Pet> getPets() {
        return pets;
    }
    
    /**
     * Sets the list of pets for this house.
     * @param pets the new list of pets for this house
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
    
    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     * Returns an empty list if there are no pets.
     * @return a list of all pet names in the house
     */
    public List<String> retrievePetNames() {
        List<String> names = new ArrayList<>();
        for (Pet pet : pets) {
            names.add(pet.getName());
        }
        return names;
    }
    
    /**
     * Adds a pet to the house. Returns false if the pet already belongs to any house
     * (including this one) or the pet does not have a name; returns true if the pet 
     * is added successfully.
     * @param pet the pet to be added to the house
     * @return true if the pet was added successfully, false otherwise
     * @throws IllegalArgumentException if the pet is null
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
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
     * Removes a specified pet from this house. Returns false if the pet does not 
     * belong to this house; returns true if it is removed successfully.
     * @param pet the pet to be removed from the house
     * @return true if the pet was removed successfully, false otherwise
     * @throws IllegalArgumentException if the pet is null
     */
    public boolean removePet(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet cannot be null");
        }
        
        if (!pets.contains(pet) || pet.getHouse() != this) {
            return false;
        }
        
        pets.remove(pet);
        pet.setHouse(null);
        return true;
    }
    
    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     * Returns an empty list if there are no pets of the specified type.
     * @param type the type of pet to retrieve ("dog" or "cat")
     * @return a list of pets of the specified type in the house
     * @throws IllegalArgumentException if the type is null or not "dog"/"cat"
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        
        if (!type.equalsIgnoreCase("dog") && !type.equalsIgnoreCase("cat")) {
            throw new IllegalArgumentException("Type must be 'dog' or 'cat'");
        }
        
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
     * Counts the number of pets in this house. Returns 0 if there are no pets.
     * @return the number of pets in the house
     */
    public int getPetCount() {
        return pets.size();
    }
}

/**
 * Abstract base class representing a pet.
 * An animal's house is its one and only home.
 */
abstract class Pet {
    private String name;
    private House house;
    
    /**
     * Default constructor.
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
     * @param name the new name for the pet
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the house where the pet lives.
     * @return the house where the pet lives, or null if the pet has no house
     */
    public House getHouse() {
        return house;
    }
    
    /**
     * Sets the house where the pet lives.
     * @param house the new house for the pet
     */
    public void setHouse(House house) {
        this.house = house;
    }
    
    /**
     * Makes the pet produce its characteristic noise.
     */
    public abstract void makeNoise();
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pet pet = (Pet) obj;
        return Objects.equals(name, pet.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

/**
 * Represents a dog pet.
 */
class Dog extends Pet {
    /**
     * Default constructor.
     */
    public Dog() {
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
 * Represents a cat pet.
 */
class Cat extends Pet {
    /**
     * Default constructor.
     */
    public Cat() {
    }
    
    /**
     * Makes the cat meow.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}