import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a generic Pet.
 * Pets have a name and belong to a house.
 */
abstract class Pet {
    private String name;
    private House house;

    /**
     * Default constructor for Pet.
     */
    public Pet() {
    }

    /**
     * Makes the pet produce its characteristic noise.
     * This method should be implemented by subclasses.
     */
    public abstract void makeNoise();

    /**
     * Gets the name of the pet.
     * @return the name of the pet
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the house where the pet lives.
     * @return the house object, or null if the pet has no house
     */
    public House getHouse() {
        return house;
    }

    /**
     * Sets the house where the pet lives.
     * This method is package-private to control access.
     * @param house the house to set
     */
    void setHouse(House house) {
        this.house = house;
    }
}

/**
 * Class representing a Dog, which is a type of Pet.
 */
class Dog extends Pet {
    /**
     * Default constructor for Dog.
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
 * Class representing a Cat, which is a type of Pet.
 */
class Cat extends Pet {
    /**
     * Default constructor for Cat.
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

/**
 * Class representing a House that can contain pets.
 */
class House {
    private List<Pet> pets;

    /**
     * Default constructor for House.
     * Initializes an empty list of pets.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     * @return a list of pet names; returns an empty list if there are no pets
     */
    public List<String> getAllPetNames() {
        List<String> names = new ArrayList<>();
        for (Pet pet : pets) {
            names.add(pet.getName());
        }
        return names;
    }

    /**
     * Adds a pet to this house.
     * @param pet the pet to be added
     * @return false if the pet already belongs to any house (including this one) or the pet does not have a name; 
     *         true if the pet is added successfully
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
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
     * Removes a specified pet from this house.
     * @param pet the pet to be removed
     * @return false if the pet does not belong to this house; true if it is removed successfully
     */
    public boolean removePet(Pet pet) {
        if (pet == null) {
            return false;
        }
        if (!pets.contains(pet) || pet.getHouse() != this) {
            return false;
        }
        pets.remove(pet);
        pet.setHouse(null);
        return true;
    }

    /**
     * Retrieves a list of pets of a specific type that live in this house.
     * @param type the type of pet ("dog" or "cat")
     * @return a list of pets of the specified type; returns an empty list if there are no matching pets
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> result = new ArrayList<>();
        if (type == null) {
            return result;
        }
        String lowerType = type.toLowerCase();
        for (Pet pet : pets) {
            if (("dog".equals(lowerType) && pet instanceof Dog) || 
                ("cat".equals(lowerType) && pet instanceof Cat)) {
                result.add(pet);
            }
        }
        return result;
    }

    /**
     * Counts the number of pets in this house.
     * @return the number of pets; returns 0 if there are no pets
     */
    public int countPets() {
        return pets.size();
    }

    /**
     * Gets the list of pets in this house.
     * @return the list of pets
     */
    public List<Pet> getPets() {
        return pets;
    }

    /**
     * Sets the list of pets for this house.
     * @param pets the list of pets to set
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}