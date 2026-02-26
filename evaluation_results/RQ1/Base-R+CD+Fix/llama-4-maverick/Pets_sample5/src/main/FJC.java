import java.util.ArrayList;
import java.util.List;

/**
 * Represents a house that can have multiple pets living in it.
 */
class House {
    private List<Pet> pets;

    /**
     * Constructs a new House object with an empty list of pets.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves a list of names of all pets living in this house.
     * 
     * @return A list of pet names. If there are no pets, an empty list is returned.
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
     * @return true if the pet is added successfully, false if the pet already belongs to a house or does not have a name.
     */
    public boolean addPet(Pet pet) {
        if (pet.getName() == null || pet.getName().isEmpty() || pet.getLived() != null) {
            return false;
        }
        pets.add(pet);
        pet.setLived(this);
        return true;
    }

    /**
     * Removes a pet from this house.
     * 
     * @param pet The pet to be removed.
     * @return true if the pet is removed successfully, false if the pet does not belong to this house.
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
     * Retrieves a list of pets of a specific type living in this house.
     * 
     * @param type The type of pet ("dog" or "cat").
     * @return A list of pets of the specified type. If there are no pets of that type, an empty list is returned.
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> petsOfType = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getClass().getSimpleName().equalsIgnoreCase(type)) {
                petsOfType.add(pet);
            }
        }
        return petsOfType;
    }

    /**
     * Counts the number of pets living in this house.
     * 
     * @return The number of pets. If there are no pets, 0 is returned.
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
 * Represents a pet that can live in a house.
 */
abstract class Pet {
    private String name;
    private House lived;

    /**
     * Constructs a new Pet object with default values.
     */
    public Pet() {
        this.name = null;
        this.lived = null;
    }

    /**
     * Gets the name of this pet.
     * 
     * @return The name of the pet.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this pet.
     * 
     * @param newName The new name for the pet.
     * @return true if the name is set successfully, false if the new name is null or empty.
     */
    public boolean setName(String newName) {
        if (newName == null || newName.isEmpty()) {
            return false;
        }
        this.name = newName;
        return true;
    }

    /**
     * Gets the house where this pet lives.
     * 
     * @return The house where the pet lives.
     */
    public House getLived() {
        return lived;
    }

    /**
     * Sets the house where this pet lives.
     * 
     * @param lived The new house for the pet.
     */
    public void setLived(House lived) {
        this.lived = lived;
    }

    /**
     * Makes noise according to the type of pet.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog that can live in a house and make noise.
 */
class Dog extends Pet {
    /**
     * Constructs a new Dog object with default values.
     */
    public Dog() {
        super();
    }

    /**
     * Makes noise like a dog.
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Represents a cat that can live in a house and make noise.
 */
class Cat extends Pet {
    /**
     * Constructs a new Cat object with default values.
     */
    public Cat() {
        super();
    }

    /**
     * Makes noise like a cat.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}