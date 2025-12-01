import java.util.ArrayList;
import java.util.List;

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
     * @return a list of pet names, or an empty list if there are no pets
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
     * @param pet the pet to add
     * @return true if the pet is added successfully, false if the pet already belongs to any house or doesn't have a name
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
     * @param pet the pet to remove
     * @return true if the pet is removed successfully, false if the pet does not belong to this house
     */
    public boolean removePet(Pet pet) {
        if (pets.contains(pet)) {
            pets.remove(pet);
            pet.setLived(null);
            return true;
        }
        return false;
    }

    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     *
     * @param type the type of pets to retrieve ("dog" or "cat")
     * @return a list of pets of the specified type, or an empty list if there are no pets of that type
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> petsByType = new ArrayList<>();
        for (Pet pet : pets) {
            if (type.equalsIgnoreCase("dog") && pet instanceof Dog ||
                type.equalsIgnoreCase("cat") && pet instanceof Cat) {
                petsByType.add(pet);
            }
        }
        return petsByType;
    }

    /**
     * Counts the number of pets in this house.
     *
     * @return the number of pets in the house, or 0 if there are no pets
     */
    public int getPetCount() {
        return pets.size();
    }

    /**
     * Gets the list of pets in this house.
     *
     * @return the list of pets
     */
    public List<Pet> getPets() {
        return pets;
    }
}

abstract class Pet {
    private String name;
    private House lived;

    /**
     * Constructs a new Pet with no name and no house.
     */
    public Pet() {
        this.name = "";
        this.lived = null;
    }

    /**
     * Gets the name of the pet.
     *
     * @return the name of the pet
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet.
     *
     * @param newName the new name of the pet
     * @return true if the name is set successfully, false if the new name is null or empty
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
     * @return the house where the pet lives, or null if the pet has no house
     */
    public House getLived() {
        return lived;
    }

    /**
     * Sets the house where the pet lives.
     *
     * @param newLived the new house where the pet lives
     */
    public void setLived(House newLived) {
        this.lived = newLived;
    }

    /**
     * Makes the pet make noise.
     */
    public abstract void makeNoise();
}

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