import java.util.ArrayList;
import java.util.List;

class House {
    private List<Pet> pets;

    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     * @return A list of pet names. An empty list if there are no pets.
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
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     * @param type The type of pet to retrieve ("dog" or "cat").
     * @return A list of pets of the specified type. An empty list if there are no pets of the specified type.
     */
    public List<Pet> getPetsByType(String type) {
        List<Pet> petsByType = new ArrayList<>();
        for (Pet pet : pets) {
            if (type.equalsIgnoreCase(pet.getClass().getSimpleName().toLowerCase())) {
                petsByType.add(pet);
            }
        }
        return petsByType;
    }

    /**
     * Counts the number of pets in this house.
     * @return The number of pets in the house. 0 if there are no pets.
     */
    public int getPetCount() {
        return pets.size();
    }

    public List<Pet> getPets() {
        return pets;
    }
}

abstract class Pet {
    private String name;
    private House lived;

    public Pet() {
        this.name = "";
        this.lived = null;
    }

    public String getName() {
        return name;
    }

    /**
     * Sets the name of the pet.
     * @param newName The new name of the pet.
     * @return true if the name is set successfully, false if the new name is null or empty.
     */
    public boolean setName(String newName) {
        if (newName == null || newName.isEmpty()) {
            return false;
        }
        this.name = newName;
        return true;
    }

    public House getLived() {
        return lived;
    }

    public void setLived(House lived) {
        this.lived = lived;
    }

    public abstract void makeNoise();
}

class Dog extends Pet {
    public Dog() {
        super();
    }

    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

class Cat extends Pet {
    public Cat() {
        super();
    }

    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}