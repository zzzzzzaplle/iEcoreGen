import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mail carrier who delivers registered mail.
 */
 class Mailman {

    private String name;
    private String id;

    /** Unparameterized constructor */
    public Mailman() {
    }

    /** Parameterized constructor (optional, not required) */
    public Mailman(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /** Getter for {@code id}. */
    public String getId() {
        return id;
    }

    /** Setter for {@code id}. */
    public void setId(String id) {
        this.id = id;
    }

    /** Getter for {@code name}. */
    public String getName() {
        return name;
    }

    /** Setter for {@code name}. */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on unique {@code id}. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mailman mailman = (Mailman) o;
        return Objects.equals(id, mailman.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents an inhabitant who can receive registered mail.
 */
 class Inhabitant {

    private String name;
    private String id;

    /** Unparameterized constructor */
    public Inhabitant() {
    }

    /** Parameterized constructor (optional, not required) */
    public Inhabitant(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /** Getter for {@code id}. */
    public String getId() {
        return id;
    }

    /** Setter for {@code id}. */
    public void setId(String id) {
        this.id = id;
    }

    /** Getter for {@code name}. */
    public String getName() {
        return name;
    }

    /** Setter for {@code name}. */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on unique {@code id}. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inhabitant that = (Inhabitant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Abstract base class for all registered mail items.
 */
public abstract class RegisteredMail {

    private Mailman carrier;          // may be null if not yet assigned
    private Inhabitant addressee;     // must never be null

    /** Unparameterized constructor */
    public RegisteredMail() {
    }

    /** Getter for {@code carrier}. */
    public Mailman getCarrier() {
        return carrier;
    }

    /** Setter for {@code carrier}. */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /** Getter for {@code addressee}. */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /** Setter for {@code addressee}. */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Concrete class representing a registered letter.
 */
 class Letter extends RegisteredMail {

    /** Unparameterized constructor */
    public Letter() {
        super();
    }

    // Additional fields specific to Letter could be added here.
}

/**
 * Concrete class representing a registered parcel.
 */
 class Parcel extends RegisteredMail {

    /** Unparameterized constructor */
    public Parcel() {
        super();
    }

    // Additional fields specific to Parcel could be added here.
}

/**
 * Represents a geographical area that groups mailmen, inhabitants and the
 * registered mail items that belong to the area.
 */
 class GeographicalArea {

    private List<Mailman> mailmen = new ArrayList<>();
    private List<Inhabitant> inhabitants = new ArrayList<>();
    private List<RegisteredMail> allMails = new ArrayList<>();

    /** Unparameterized constructor */
    public GeographicalArea() {
    }

    /** Getter for {@code mailmen}. */
    public List<Mailman> getMailmen() {
        return Collections.unmodifiableList(mailmen);
    }

    /** Setter for {@code mailmen}. */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = new ArrayList<>(mailmen);
    }

    /** Getter for {@code inhabitants}. */
    public List<Inhabitant> getInhabitants() {
        return Collections.unmodifiableList(inhabitants);
    }

    /** Setter for {@code inhabitants}. */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = new ArrayList<>(inhabitants);
    }

    /** Getter for {@code allMails}. */
    public List<RegisteredMail> getAllMails() {
        return Collections.unmodifiableList(allMails);
    }

    /** Setter for {@code allMails}. */
    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = new ArrayList<>(allMails);
    }

    /**
     * Assigns a specific mailman to deliver a registered mail item.
     * <p>
     * The carrier and the addressee must both belong to this geographical area,
     * and the mail must not already have a carrier assigned.
     *
     * @param carrier   the mailman who will deliver the mail
     * @param addressee the inhabitant who will receive the mail
     * @param mail      the registered mail item to be assigned
     * @return {@code true} if the assignment succeeds; {@code false} otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        // Validate area membership
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }

        // Mail must be part of this area (or we add it now)
        if (!allMails.contains(mail)) {
            allMails.add(mail);
        }

        // Ensure mail is not already assigned
        if (mail.getCarrier() != null) {
            return false;
        }

        // Perform assignment
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        return true;
    }

    /**
     * Adds a new mailman to this geographical area if they are not already present.
     *
     * @param newMailman the mailman to add
     * @return {@code true} if the mailman was added; {@code false} if already present
     */
    public boolean addMailman(Mailman newMailman) {
        if (newMailman == null || mailmen.contains(newMailman)) {
            return false;
        }
        mailmen.add(newMailman);
        return true;
    }

    /**
     * Removes a mailman from the area after reassigning all of their deliveries to another mailman.
     * <p>
     * The removal succeeds only if:
     * <ul>
     *   <li>At least one other mailman remains in the area after removal.</li>
     *   <li>The {@code newMailman} exists in the area and is different from {@code mailmanToRemove}.</li>
     *   <li>All mails currently assigned to {@code mailmanToRemove} are successfully reassigned.</li>
     * </ul>
     *
     * @param mailmanToRemove the mailman that should be removed
     * @param newMailman      the mailman that will take over the deliveries
     * @return {@code true} if removal and reassignment succeed; {@code false} otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null) {
            return false;
        }
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman)) {
            return false;
        }
        if (mailmanToRemove.equals(newMailman)) {
            return false; // cannot reassign to the same person
        }
        if (mailmen.size() <= 1) {
            return false; // must keep at least one mailman
        }

        // Reassign each mail delivered by mailmanToRemove
        for (RegisteredMail mail : allMails) {
            if (mailmanToRemove.equals(mail.getCarrier())) {
                mail.setCarrier(newMailman);
            }
        }

        // Finally remove the mailman
        mailmen.remove(mailmanToRemove);
        return true;
    }

    /**
     * Adds a new inhabitant to this geographical area.
     *
     * @param newInhabitant the inhabitant to add
     * @return {@code true} if the inhabitant was added; {@code false} if already present or null
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (newInhabitant == null || inhabitants.contains(newInhabitant)) {
            return false;
        }
        inhabitants.add(newInhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from this geographical area.
     * <p>
     * All registered mail items whose addressee is this inhabitant and which have already
     * been assigned to a mailman are deleted from the system.
     *
     * @param inhabitant the inhabitant to remove
     * @return {@code true} if the inhabitant existed and was removed; {@code false} otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null || !inhabitants.contains(inhabitant)) {
            return false;
        }

        // Remove relevant mails
        allMails.removeIf(mail ->
                inhabitant.equals(mail.getAddressee()) && mail.getCarrier() != null
        );

        // Remove the inhabitant
        inhabitants.remove(inhabitant);
        return true;
    }

    /**
     * Returns a list of all registered mail items (letters and parcels) that are
     * assigned to the specified mailman.
     *
     * @param carrier the mailman whose deliveries are to be listed
     * @return a list of {@link RegisteredMail} objects; {@code null} if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        if (carrier == null) {
            return null;
        }
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (carrier.equals(mail.getCarrier())) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Returns a list of all registered mail items (letters and parcels) directed to
     * the specified inhabitant.
     *
     * @param addressee the inhabitant whose incoming mail should be listed
     * @return a list of {@link RegisteredMail} objects; {@code null} if none exist
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        if (addressee == null) {
            return null;
        }
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (addressee.equals(mail.getAddressee())) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }
}