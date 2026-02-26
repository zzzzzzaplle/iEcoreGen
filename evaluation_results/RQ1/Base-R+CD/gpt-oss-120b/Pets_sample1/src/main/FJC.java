import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a house that can contain any number of pets (dogs or cats).
 */
 class House {

    /** List that holds all pets living in this house. */
    private List<Pet> pets;

    /**
     * Constructs an empty {@code House}.
     * The internal pet list is initialized to an empty {@link ArrayList}.
     */
    public House() {
        this.pets = new ArrayList<>();
    }

    /**
     * Returns an unmodifiable view of the pets currently living in this house.
     *
     * @return the list of pets (never {@code null})
     */
    public List<Pet> getPets() {
        return Collections.unmodifiableList(pets);
    }

    /**
     * Sets the internal list of pets.
     * <p>
     * This method is mainly for testing purposes; it replaces the current list
     * with the supplied one. All pets in the supplied list will have their
     * {@code home} reference updated to this {@code House}.
     *
     * @param pets the new list of pets; if {@code null} an empty list is used
     */
    public void setPets(List<Pet> pets) {
        this.pets.clear();
        if (pets != null) {
            for (Pet p : pets) {
                addPet(p);
            }
        }
    }

    /**
     * Retrieves the names of all pets (dogs and cats) living in this house.
     *
     * @return a {@link List} of pet names; empty if there are no pets
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
     * <p>
     * The operation fails and returns {@code false} if:
     * <ul>
     *   <li>The supplied {@code pet} is {@code null}.</li>
     *   <li>The pet does not have a name (null or empty).</li>
     *   <li>The pet already belongs to a house (including this house).</li>
     * </ul>
     *
     * @param pet the pet to add
     * @return {@code true} if the pet was added successfully; {@code false} otherwise
     */
    public boolean addPet(Pet pet) {
        if (pet == null) {
            return false;
        }
        String name = pet.getName();
        if (name == null || name.trim().isEmpty()) {
            return false; // pet must have a name
        }
        if (pet.getHome() != null) {
            return false; // pet already belongs to a house
        }
        pets.add(pet);
        pet.setHome(this);
        return true;
    }

    /**
     * Removes a specified pet from its current house.
     * <p>
     * The operation fails and returns {@code false} if the pet does not belong
     * to this house.
     *
     * @param pet the pet to remove
     * @return {@code true} if the pet was removed successfully; {@code false} otherwise
     */
    public boolean removePet(Pet pet) {
        if (pet == null) {
            return false;
        }
        if (!pets.contains(pet)) {
            return false; // pet not in this house
        }
        pets.remove(pet);
        pet.setHome(null);
        return true;
    }

    /**
     * Retrieves all pets of a specific type that live in this house.
     *
     * @param type the type of pet to retrieve; expected values are {@code "dog"} or {@code "cat"}
     * @return a {@link List} of pets matching the given type; empty list if none match
     */
    public List<Pet> getPetsByType(String type) {
        if (type == null) {
            return Collections.emptyList();
        }
        List<Pet> result = new ArrayList<>();
        String lowered = type.trim().toLowerCase();
        for (Pet pet : pets) {
            if (("dog".equals(lowered) && pet instanceof Dog) ||
                ("cat".equals(lowered) && pet instanceof Cat)) {
                result.add(pet);
            }
        }
        return result;
    }

    /**
     * Counts the number of pets living in this house.
     *
     * @return the total number of pets; {@code 0} if there are none
     */
    public int getPetCount() {
        return pets.size();
    }
}

/**
 * Abstract base class for all pets.
 */
abstract class Pet {

    /** Name of the pet. */
    private String name;

    /** The house this pet belongs to; {@code null} if it does not belong to any house. */
    private House home;

    /**
     * Constructs a {@code Pet} with no name.
     */
    public Pet() {
        // default constructor; name remains null
    }

    /**
     * Returns the pet's name.
     *
     * @return the name, possibly {@code null}
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the pet's name.
     *
     * @param name the name to set; may be {@code null} or empty
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the house this pet belongs to.
     *
     * @return the {@link House} instance, or {@code null} if none
     */
    public House getHome() {
        return home;
    }

    /**
     * Sets the house this pet belongs to.
     *
     * @param home the {@link House} to associate with this pet; may be {@code null}
     */
    public void setHome(House home) {
        this.home = home;
    }

    /**
     * Makes the pet produce its characteristic noise.
     * Concrete subclasses must provide an implementation.
     */
    public abstract void makeNoise();
}

/**
 * Represents a dog, which is a kind of {@link Pet}.
 */
class Dog extends Pet {

    /**
     * Constructs an empty {@code Dog}.
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

/**
 * Represents a cat, which is a kind of {@link Pet}.
 */
class Cat extends Pet {

    /**
     * Constructs an empty {@code Cat}.
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