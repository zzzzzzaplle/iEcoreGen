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
        List<String> names = new ArrayList<>();
        for (Pet pet : pets) {
            names.add(pet.getName());
        }
        return names;
    }

    /**
     * Adds a pet to this house.
     * 
     * @param pet The pet to be added.
     * @return false if the pet already belongs to any house (including this one) 
     *         or the pet does not have a name; true if the pet is added successfully.
     */
    public boolean addPet(Pet pet) {
        // Check if pet already has a home or doesn't have a name
        if (pet.getHome() != null || pet.getName() == null || pet.getName().isEmpty()) {
            return false;
        }
        
        pets.add(pet);
        pet.setHome(this);
        return true;
    }

    /**
     * Removes a specified pet from this house.
     * 
     * @param pet The pet to be removed.
     * @return false if the pet does not belong to this house; true if it is removed successfully.
     */
    public boolean removePet(Pet pet) {
        if (pet.getHome() != this) {
            return false;
        }
        
        pets.remove(pet);
        pet.setHome(null);
        return true;
    }

    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     * 
     * @param type The type of pets to retrieve ("dog" or "cat").
     * @return A list of pets of the specified type, or an empty list if there are no such pets.
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> result = new ArrayList<>();
        for (Pet pet : pets) {
            if ((type.equals("dog") && pet instanceof Dog) || 
                (type.equals("cat") && pet instanceof Cat)) {
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
     * @param pets The list of pets to set.
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

/**
 * Abstract class representing a pet with a name.
 */
abstract class Pet {
    private String name;
    private House home;

    /**
     * Constructs a new Pet with no name and no home.
     */
    public Pet() {
        this.name = null;
        this.home = null;
    }

    /**
     * Makes noise specific to the pet type.
     */
    public abstract void makeNoise();

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
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the home of this pet.
     * 
     * @return The house where this pet lives.
     */
    public House getHome() {
        return home;
    }

    /**
     * Sets the home of this pet.
     * 
     * @param home The house to set as this pet's home.
     */
    public void setHome(House home) {
        this.home = home;
    }
}

/**
 * Represents a dog, which is a type of pet.
 */
class Dog extends Pet {
    /**
     * Constructs a new Dog with no name and no home.
     */
    public Dog() {
        super();
    }

    /**
     * Makes a dog-specific noise.
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
     * Constructs a new Cat with no name and no home.
     */
    public Cat() {
        super();
    }

    /**
     * Makes a cat-specific noise.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}