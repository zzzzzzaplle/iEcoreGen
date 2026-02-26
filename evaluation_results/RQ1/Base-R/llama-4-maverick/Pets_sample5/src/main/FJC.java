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
    public List<String> getAllPetNames() {
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
     * @return false if the pet already belongs to any house or does not have a name; true if the pet is added successfully.
     */
    public boolean addPet(Pet pet) {
        if (pet.getHouse() != null || pet.getName() == null || pet.getName().isEmpty()) {
            return false;
        }
        pet.setHouse(this);
        return pets.add(pet);
    }

    /**
     * Removes a specified pet from this house.
     * 
     * @param pet The pet to be removed.
     * @return false if the pet does not belong to this house; true if it is removed successfully.
     */
    public boolean removePet(Pet pet) {
        if (!pets.contains(pet) || pet.getHouse() != this) {
            return false;
        }
        pet.setHouse(null);
        return pets.remove(pet);
    }

    /**
     * Retrieves a list of pets of a specific type living in this house.
     * 
     * @param type The type of pet ("dog" or "cat").
     * @return A list of pets of the specified type. Returns an empty list if there are no pets of that type.
     */
    public List<Pet> getPetsOfType(String type) {
        List<Pet> petsOfType = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getType().equals(type)) {
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
    public int countPets() {
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
     * @param pets The list of pets to be set.
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
    private House house;

    /**
     * Constructs a new Pet object.
     */
    public Pet() {}

    /**
     * Makes noise as per the pet type.
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
     * @param name The name to be set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the house where this pet lives.
     * 
     * @return The house where this pet lives.
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house where this pet lives.
     * 
     * @param house The house to be set.
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Gets the type of this pet.
     * 
     * @return The type of this pet.
     */
    public abstract String getType();
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
     * Makes a barking noise.
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }

    /**
     * Gets the type of this pet.
     * 
     * @return "dog"
     */
    @Override
    public String getType() {
        return "dog";
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
     * Makes a meowing noise.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }

    /**
     * Gets the type of this pet.
     * 
     * @return "cat"
     */
    @Override
    public String getType() {
        return "cat";
    }
}