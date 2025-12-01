import java.util.ArrayList;
import java.util.List;

/**
 * Represents a house that can contain pets.
 * A house maintains a list of pets living in it and provides operations
 * to manage and query those pets.
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
     * Retrieves the list of pets in the house.
     * 
     * @return the list of pets in the house
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the list of pets for the house.
     * 
     * @param pets the new list of pets for the house
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     * Returns an empty list if there are no pets.
     * 
     * @return a list of pet names as strings
     */
    public List<String> retrievePetNames() {
        List<String> names = new ArrayList<>();
        for (Pet pet : pets) {
            names.add(pet.getName());
        }
        return names;
    }

    /**
     * Adds a pet to the house.
     * Returns false if the pet already belongs to any house (including this one)
     * or the pet does not have a name; returns true if the pet is added successfully.
     * 
     * @param pet the pet to add to the house
     * @return true if the pet was added successfully, false otherwise
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
     * Returns false if the pet does not belong to this house; returns true if it is removed successfully.
     * 
     * @param pet the pet to remove from the house
     * @return true if the pet was removed successfully, false otherwise
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
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     * Returns an empty list if there are no pets of the specified type.
     * 
     * @param type the type of pet to retrieve ("dog" or "cat")
     * @return a list of pets of the specified type
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
     * Returns 0 if there are no pets.
     * 
     * @return the number of pets in the house
     */
    public int getPetCount() {
        return pets.size();
    }
}

/**
 * Abstract base class representing a pet.
 * A pet has a name and a reference to the house it lives in.
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
     * Retrieves the name of the pet.
     * 
     * @return the name of the pet
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet.
     * 
     * @param newName the new name for the pet
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Retrieves the house the pet lives in.
     * 
     * @return the house the pet lives in, or null if it has no house
     */
    public House getLived() {
        return lived;
    }

    /**
     * Sets the house the pet lives in.
     * 
     * @param lived the house the pet lives in
     */
    public void setLived(House lived) {
        this.lived = lived;
    }

    /**
     * Makes the pet produce its characteristic noise.
     * This method is abstract and must be implemented by subclasses.
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