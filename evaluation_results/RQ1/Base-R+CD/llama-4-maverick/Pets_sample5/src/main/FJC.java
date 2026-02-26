import java.util.ArrayList;
import java.util.List;

/**
 * Represents a house that can have multiple pets living in it.
 */
class House {
    private List<Pet> pets;

    /**
     * Default constructor to initialize an empty list of pets.
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
     * @return true if the pet is added successfully, false if the pet already belongs to any house or does not have a name.
     */
    public boolean addPet(Pet pet) {
        if (pet.getLived() != null || pet.getName() == null || pet.getName().isEmpty()) {
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
            if (pet.getClass().getSimpleName().toLowerCase().equals(type)) {
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
     * Default constructor.
     */
    public Pet() {}

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
     * @return true if the name is set successfully (i.e., the new name is not null or empty), false otherwise.
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
     * @return The house where the pet lives, or null if it doesn't live in any house.
     */
    public House getLived() {
        return lived;
    }

    /**
     * Sets the house where this pet lives.
     * 
     * @param lived The house where the pet lives.
     */
    public void setLived(House lived) {
        this.lived = lived;
    }

    /**
     * Makes this pet make noise.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog that can live in a house.
 */
class Dog extends Pet {
    /**
     * Default constructor.
     */
    public Dog() {}

    /**
     * Makes this dog make noise (bark).
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
     * Default constructor.
     */
    public Cat() {}

    /**
     * Makes this cat make noise (meow).
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}