import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Represents a house that can contain any number of {@link Pet} objects.
 */
class House {

    /** The pets currently living in this house. */
    private List<Pet> pets;

    /**
     * Constructs an empty {@code House}.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Retrieves the names of all pets (dogs and cats) living in this house.
     *
     * @return a list containing the names of all pets; the list is empty if there are no pets.
     */
    public List<String> retrievePetNames() {
        List<String> names = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.getName() != null) {
                names.add(pet.getName());
            }
        }
        return names;
    }

    /**
     * Adds a pet to this house.
     *
     * @param pet the pet to add; must not be {@code null}
     * @return {@code true} if the pet was added successfully; {@code false} if the pet already belongs
     *         to any house (including this one) or the pet does not have a valid name.
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
        }
        // pet must have a non‑null, non‑empty name
        String name = pet.getName();
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        // pet must not already belong to a house
        if (pet.getLived() != null) {
            return false;
        }
        // add and set its home
        pets.add(pet);
        pet.setLived(this);
        return true;
    }

    /**
     * Removes a specified pet from its current house.
     *
     * @param pet the pet to remove; must not be {@code null}
     * @return {@code true} if the pet was removed successfully; {@code false} if the pet does not belong
     *         to this house.
     */
    public boolean removePet(Pet pet) {
        if (pet == null) {
            return false;
        }
        if (pet.getLived() != this) {
            return false;
        }
        boolean removed = pets.remove(pet);
        if (removed) {
            pet.setLived(null);
        }
        return removed;
    }

    /**
     * Retrieves a list of pets of a specific type ("dog" or "cat") that live in this house.
     *
     * @param type the type of pet to retrieve; case‑insensitive, expected values are {@code "dog"} or {@code "cat"}
     * @return a list of pets matching the requested type; the list is empty if there are none.
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null) {
            return Collections.emptyList();
        }
        List<Pet> result = new ArrayList<>();
        String lowered = type.trim().toLowerCase();
        for (Pet pet : pets) {
            if ("dog".equals(lowered) && pet instanceof Dog) {
                result.add(pet);
            } else if ("cat".equals(lowered) && pet instanceof Cat) {
                result.add(pet);
            }
        }
        return result;
    }

    /**
     * Counts the number of pets living in this house.
     *
     * @return the total number of pets; {@code 0} if there are none.
     */
    public int getPetCount() {
        return pets.size();
    }

    /** Getter for the internal pet list (read‑only copy). */
    public List<Pet> getPets() {
        return new ArrayList<>(pets);
    }

    /** Setter for the internal pet list; replaces current pets. */
    public void setPets(List<Pet> pets) {
        this.pets = (pets != null) ? new ArrayList<>(pets) : new ArrayList<>();
        // Ensure each pet knows it lives here
        for (Pet pet : this.pets) {
            pet.setLived(this);
        }
    }
}

/**
 * Abstract base class for all pets.
 */
abstract class Pet {

    /** Name of the pet. */
    private String name;

    /** The house this pet lives in; {@code null} if it has no home. */
    private House lived;

    /**
     * Constructs a {@code Pet} with no name and no home.
     */
    public Pet() {
        this.name = null;
        this.lived = null;
    }

    /**
     * Returns the pet's name.
     *
     * @return the name, or {@code null} if not set.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name for the pet.
     *
     * @param newName the new name; must be non‑null and non‑empty
     * @return {@code true} if the name was set; {@code false} otherwise.
     */
    public boolean setName(String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            return false;
        }
        this.name = newName;
        return true;
    }

    /**
     * Returns the house this pet currently lives in.
     *
     * @return the {@link House} instance, or {@code null} if the pet has no home.
     */
    public House getLived() {
        return lived;
    }

    /**
     * Sets the house where this pet lives.
     *
     * @param house the house to assign; may be {@code null} to indicate no home.
     */
    public void setLived(House house) {
        this.lived = house;
    }

    /**
     * Makes the pet produce its characteristic noise.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog, a type of {@link Pet}.
 */
class Dog extends Pet {

    /**
     * Constructs an empty {@code Dog}.
     */
    public Dog() {
        super();
    }

    /**
     * The dog barks.
     */
    @Override
    public void makeNoise() {
        System.out.println("Woof!");
    }
}

/**
 * Represents a cat, a type of {@link Pet}.
 */
class Cat extends Pet {

    /**
     * Constructs an empty {@code Cat}.
     */
    public Cat() {
        super();
    }

    /**
     * The cat meows.
     */
    @Override
    public void makeNoise() {
        System.out.println("Meow!");
    }
}