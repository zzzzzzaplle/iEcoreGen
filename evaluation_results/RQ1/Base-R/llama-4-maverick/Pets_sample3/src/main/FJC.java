import java.util.ArrayList;
import java.util.List;

/**
 * Represents a pet living in a house.
 */
abstract class Pet {
    private String name;
    private House house;

    /**
     * Unparameterized constructor for Pet.
     */
    public Pet() {}

    /**
     * Constructs a Pet with a given name.
     * @param name The name of the pet.
     */
    public Pet(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the pet.
     * @return The name of the pet.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet.
     * @param name The new name for the pet.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the house where the pet lives.
     * @return The house where the pet lives.
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house where the pet lives.
     * @param house The new house for the pet.
     */
    public void setHouse(House house) {
        this.house = house;
    }

    /**
     * Makes the pet make noise.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog living in a house.
 */
class Dog extends Pet {
    /**
     * Unparameterized constructor for Dog.
     */
    public Dog() {}

    /**
     * Constructs a Dog with a given name.
     * @param name The name of the dog.
     */
    public Dog(String name) {
        super(name);
    }

    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Represents a cat living in a house.
 */
class Cat extends Pet {
    /**
     * Unparameterized constructor for Cat.
     */
    public Cat() {}

    /**
     * Constructs a Cat with a given name.
     * @param name The name of the cat.
     */
    public Cat(String name) {
        super(name);
    }

    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}

/**
 * Represents a house where pets live.
 */
class House {
    private List<Pet> pets;

    /**
     * Unparameterized constructor for House.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Gets the list of pets living in the house.
     * @return The list of pets.
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the list of pets living in the house.
     * @param pets The new list of pets.
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    /**
     * Retrieves a list of all pet names living in the house.
     * @return A list of pet names.
     */
    public List<String> getPetNames() {
        List<String> petNames = new ArrayList<>();
        for (Pet pet : pets) {
            petNames.add(pet.getName());
        }
        return petNames;
    }

    /**
     * Adds a pet to the house.
     * @param pet The pet to be added.
     * @return True if the pet is added successfully, false otherwise.
     */
    public boolean addPet(Pet pet) {
        if (pet.getHouse() != null || pet.getName() == null) {
            return false;
        }
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a pet from the house.
     * @param pet The pet to be removed.
     * @return True if the pet is removed successfully, false otherwise.
     */
    public boolean removePet(Pet pet) {
        if (pet.getHouse() != this) {
            return false;
        }
        pets.remove(pet);
        pet.setHouse(null);
        return true;
    }

    /**
     * Retrieves a list of pets of a specific type living in the house.
     * @param type The type of pet ("dog" or "cat").
     * @return A list of pets of the specified type.
     */
    public List<Pet> getPetsOfType(String type) {
        List<Pet> petsOfType = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet instanceof Dog && type.equalsIgnoreCase("dog")) {
                petsOfType.add(pet);
            } else if (pet instanceof Cat && type.equalsIgnoreCase("cat")) {
                petsOfType.add(pet);
            }
        }
        return petsOfType;
    }

    /**
     * Counts the number of pets living in the house.
     * @return The number of pets.
     */
    public int countPets() {
        return pets.size();
    }
}