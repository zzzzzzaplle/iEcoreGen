import java.util.ArrayList;
import java.util.List;

class House {
    private List<Pet> pets;

    /**
     * Constructor for House class
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     *
     * @return List of pet names. Returns an empty list if there are no pets.
     */
    public List<String> retrievePetNames() {
        List<String> petNames = new ArrayList<>();
        for (Pet pet : pets) {
            petNames.add(pet.getName());
        }
        return petNames;
    }

    /**
     * Adds a pet to the house.
     *
     * @param pet The pet to be added.
     * @return true if the pet is added successfully, false if the pet already belongs to any house or does not have a name.
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
     * Removes a specified pet from its current house.
     *
     * @param pet The pet to be removed.
     * @return true if the pet is removed successfully, false if the pet does not belong to this house.
     */
    public boolean removePet(Pet pet) {
        if (!pets.contains(pet)) {
            return false;
        }
        pets.remove(pet);
        pet.setLived(null);
        return true;
    }

    /**
     * Retrieves a list of pets of a specific type that live in this house.
     *
     * @param type The type of pet to retrieve ("dog" or "cat").
     * @return List of pets of the specified type. Returns an empty list if there are no pets of the specified type.
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> petsOfType = new ArrayList<>();
        for (Pet pet : pets) {
            if (type.equalsIgnoreCase("dog") && pet instanceof Dog) {
                petsOfType.add(pet);
            } else if (type.equalsIgnoreCase("cat") && pet instanceof Cat) {
                petsOfType.add(pet);
            }
        }
        return petsOfType;
    }

    /**
     * Counts the number of pets in this house.
     *
     * @return The number of pets in the house. Returns 0 if there are no pets.
     */
    public int getPetCount() {
        return pets.size();
    }

    /**
     * Gets the list of pets in the house.
     *
     * @return List of pets.
     */
    public List<Pet> getPets() {
        return pets;
    }
}

abstract class Pet {
    private String name;
    private House lived;

    /**
     * Constructor for Pet class.
     */
    public Pet() {
        this.name = "";
    }

    /**
     * Gets the name of the pet.
     *
     * @return The name of the pet.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet.
     *
     * @param newName The new name to set.
     * @return true if the name is set successfully, false if the name is null or empty.
     */
    public boolean setName(String newName) {
        if (newName == null || newName.isEmpty()) {
            return false;
        }
        this.name = newName;
        return true;
    }

    /**
     * Gets the house where the pet lives.
     *
     * @return The house where the pet lives.
     */
    public House getLived() {
        return lived;
    }

    /**
     * Sets the house where the pet lives.
     *
     * @param lived The house where the pet lives.
     */
    public void setLived(House lived) {
        this.lived = lived;
    }

    /**
     * Makes the pet make noise.
     */
    public abstract void makeNoise();
}

class Dog extends Pet {
    /**
     * Constructor for Dog class.
     */
    public Dog() {
    }

    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

class Cat extends Pet {
    /**
     * Constructor for Cat class.
     */
    public Cat() {
    }

    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}