import java.util.ArrayList;
import java.util.List;

/**
 * Represents a house that can have multiple pets living in it.
 */
class House {
    private List<Pet> pets;

    /**
     * Constructs a new House object.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves a list of all pet names living in this house.
     * 
     * @return A list of pet names. Returns an empty list if there are no pets.
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
     * @param pet The pet to be added.
     * @return false if the pet already belongs to any house or the pet does not have a name; true if the pet is added successfully.
     */
    public boolean addPet(Pet pet) {
        if (pet.getName() == null || pet.getLived() != null) {
            return false;
        }
        pets.add(pet);
        pet.setLived(this);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     * 
     * @param pet The pet to be removed.
     * @return false if the pet does not belong to this house; true if it is removed successfully.
     */
    public boolean removePet(Pet pet) {
        if (!pets.contains(pet) || pet.getLived() != this) {
            return false;
        }
        pets.remove(pet);
        pet.setLived(null);
        return true;
    }

    /**
     * Retrieves a list of pets of a specific type that live in this house.
     * 
     * @param type The type of pet ("dog" or "cat").
     * @return A list of pets of the specified type. Returns an empty list if there are no pets.
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> petsOfType = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getClass().getSimpleName().toLowerCase().equals(type)) {
                petsOfType.add(pet);
            }
        }
        return petsOfType;
    }

    /**
     * Counts the number of pets in this house.
     * 
     * @return The number of pets. Returns 0 if there are no pets.
     */
    public int getPetCount() {
        return pets.size();
    }

    /**
     * Gets the list of pets living in this house.
     * 
     * @return The list of pets.
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the list of pets living in this house.
     * 
     * @param pets The new list of pets.
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
     * Constructs a new Pet object.
     */
    public Pet() {}

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
     * @param newName The new name.
     * @return true if the name is set successfully; false otherwise.
     */
    public boolean setName(String newName) {
        if (newName == null) {
            return false;
        }
        this.name = newName;
        return true;
    }

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
     * @param lived The new house.
     */
    public void setLived(House lived) {
        this.lived = lived;
    }

    /**
     * Makes noise as per the pet type.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog that can live in a house.
 */
class Dog extends Pet {
    /**
     * Constructs a new Dog object.
     */
    public Dog() {}

    /**
     * Makes noise as a dog.
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
     * Constructs a new Cat object.
     */
    public Cat() {}

    /**
     * Makes noise as a cat.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}