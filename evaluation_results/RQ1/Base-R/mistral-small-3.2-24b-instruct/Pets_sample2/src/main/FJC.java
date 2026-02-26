import java.util.ArrayList;
import java.util.List;

/**
 * Represents a pet in a house.
 */
abstract class Pet {
    private String name;
    private House house;

    /**
     * Constructs a new Pet with no name and no house.
     */
    public Pet() {
        this.name = null;
        this.house = null;
    }

    /**
     * Constructs a new Pet with the specified name and no house.
     * @param name the name of the pet
     */
    public Pet(String name) {
        this.name = name;
        this.house = null;
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
     * @param name the new name of the pet
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the house of the pet.
     * @return the house of the pet
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house of the pet.
     * @param house the new house of the pet
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
     * Constructs a new Dog with the specified name and no house.
     * @param name the name of the dog
     */
    public Dog(String name) {
        super(name);
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
     * Constructs a new Cat with the specified name and no house.
     * @param name the name of the cat
     */
    public Cat(String name) {
        super(name);
    }

    /**
     * Makes the cat meow.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}

/**
 * Represents a house that can contain pets.
 */
class House {
    private List<Pet> pets;

    /**
     * Constructs a new House with no pets.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Gets the list of pets in the house.
     * @return the list of pets in the house
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the list of pets in the house.
     * @param pets the new list of pets in the house
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     * @return a list of all pet names in the house
     */
    public List<String> getAllPetNames() {
        List<String> petNames = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getName() != null) {
                petNames.add(pet.getName());
            }
        }
        return petNames;
    }

    /**
     * Adds a pet to the house.
     * @param pet the pet to add
     * @return true if the pet is added successfully, false otherwise
     */
    public boolean addPet(Pet pet) {
        if (pet.getName() == null || pet.getHouse() != null) {
            return false;
        }
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from the house.
     * @param pet the pet to remove
     * @return true if the pet is removed successfully, false otherwise
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
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     * @param type the type of pet ("dog" or "cat")
     * @return a list of pets of the specified type
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> petsByType = new ArrayList<>();
        for (Pet pet : pets) {
            if (type.equalsIgnoreCase("dog") && pet instanceof Dog) {
                petsByType.add(pet);
            } else if (type.equalsIgnoreCase("cat") && pet instanceof Cat) {
                petsByType.add(pet);
            }
        }
        return petsByType;
    }

    /**
     * Counts the number of pets in the house.
     * @return the number of pets in the house
     */
    public int countPets() {
        return pets.size();
    }
}