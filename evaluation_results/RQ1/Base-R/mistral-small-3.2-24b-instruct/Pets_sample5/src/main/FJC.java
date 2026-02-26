import java.util.ArrayList;
import java.util.List;

abstract class Pet {
    private String name;
    private House house;

    public Pet() {
        this.name = "";
        this.house = null;
    }

    public Pet(String name) {
        this.name = name;
        this.house = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public abstract void makeNoise();

    public boolean isHomeless() {
        return house == null;
    }
}

class Dog extends Pet {
    public Dog() {
        super();
    }

    public Dog(String name) {
        super(name);
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

    public Cat(String name) {
        super(name);
    }

    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}

class House {
    private List<Pet> pets;

    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves a list of all pet names (dogs and cats) living in this house.
     *
     * @return A list of pet names. An empty list if there are no pets.
     */
    public List<String> getAllPetNames() {
        List<String> petNames = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getName() != null && !pet.getName().isEmpty()) {
                petNames.add(pet.getName());
            }
        }
        return petNames;
    }

    /**
     * Adds a pet to the house.
     *
     * @param pet The pet to add.
     * @return true if the pet is added successfully, false if the pet already belongs to any house or does not have a name.
     */
    public boolean addPet(Pet pet) {
        if (pet == null || pet.getName() == null || pet.getName().isEmpty() || !pet.isHomeless()) {
            return false;
        }
        pets.add(pet);
        pet.setHouse(this);
        return true;
    }

    /**
     * Removes a specified pet from the house.
     *
     * @param pet The pet to remove.
     * @return true if the pet is removed successfully, false if the pet does not belong to this house.
     */
    public boolean removePet(Pet pet) {
        if (pet == null || !pets.contains(pet)) {
            return false;
        }
        pets.remove(pet);
        pet.setHouse(null);
        return true;
    }

    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     *
     * @param type The type of pet to retrieve ("dog" or "cat").
     * @return A list of pets of the specified type. An empty list if there are no pets of the specified type.
     * @throws IllegalArgumentException if the type is not "dog" or "cat".
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null || (!type.equals("dog") && !type.equals("cat"))) {
            throw new IllegalArgumentException("Type must be either 'dog' or 'cat'.");
        }
        List<Pet> petsOfType = new ArrayList<>();
        for (Pet pet : pets) {
            if (type.equals("dog") && pet instanceof Dog) {
                petsOfType.add(pet);
            } else if (type.equals("cat") && pet instanceof Cat) {
                petsOfType.add(pet);
            }
        }
        return petsOfType;
    }

    /**
     * Counts the number of pets in this house.
     *
     * @return The number of pets in the house. 0 if there are no pets.
     */
    public int countPets() {
        return pets.size();
    }
}