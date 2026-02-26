import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mail carrier (mailman) that works in a geographical area.
 */
 class Mailman {

    private String name;
    private int id;

    /** No‑argument constructor required by the specification. */
    public Mailman() {
    }

    /** Full constructor for convenience. */
    public Mailman(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /** @return the mailman's name */
    public String getName() {
        return name;
    }

    /** @param name the mailman's name */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the mailman's identifier */
    public int getId() {
        return id;
    }

    /** @param id the mailman's identifier */
    public void setId(int id) {
        this.id = id;
    }

    /** Equality based on unique identifier. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mailman mailman = (Mailman) o;
        return id == mailman.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents an inhabitant of a geographical area.
 */
 class Inhabitant {

    private String name;
    private int id;

    /** No‑argument constructor required by the specification. */
    public Inhabitant() {
    }

    /** Full constructor for convenience. */
    public Inhabitant(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /** @return the inhabitant's name */
    public String getName() {
        return name;
    }

    /** @param name the inhabitant's name */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the inhabitant's identifier */
    public int getId() {
        return id;
    }

    /** @param id the inhabitant's identifier */
    public void setId(int id) {
        this.id = id;
    }

    /** Equality based on unique identifier. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inhabitant that = (Inhabitant) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Abstract base class for all registered mail items (letters and parcels).
 */
public abstract class RegisteredMail {

    private Mailman carrier;          // may be null until assigned
    private Inhabitant addressee;     // never null

    /** No‑argument constructor required by the specification. */
    public RegisteredMail() {
    }

    /** Constructor used by subclasses. */
    protected RegisteredMail(Inhabitant addressee) {
        this.addressee = addressee;
    }

    /** @return the carrier that delivers this mail, may be {@code null} */
    public Mailman getCarrier() {
        return carrier;
    }

    /** @param carrier the mail carrier to set */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /** @return the intended addressee of the mail */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /** @param addressee the addressee to set */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Concrete type representing a registered letter.
 */
 class Letter extends RegisteredMail {

    /** No‑argument constructor required by the specification. */
    public Letter() {
        super();
    }

    /** Convenience constructor. */
    public Letter(Inhabitant addressee) {
        super(addressee);
    }
}

/**
 * Concrete type representing a registered parcel.
 */
 class Parcel extends RegisteredMail {

    /** No‑argument constructor required by the specification. */
    public Parcel() {
        super();
    }

    /** Convenience constructor. */
    public Parcel(Inhabitant addressee) {
        super(addressee);
    }
}

/**
 * Represents a geographical area that groups mailmen, inhabitants,
 * and the registered mails that travel inside it.
 */
 class GeographicalArea {

    private final List<Mailman> mailmen = new ArrayList<>();
    private final List<Inhabitant> inhabitants = new ArrayList<>();
    private final List<RegisteredMail> allMails = new ArrayList<>();

    /** No‑argument constructor required by the specification. */
    public GeographicalArea() {
    }

    /**
     * Assigns a specific mailman to deliver a registered mail item.
     * <p>
     * The carrier and the addressee must both belong to this geographical
     * area, and the mail item must not already have a carrier assigned.
     *
     * @param carrier   the mailman that will deliver the mail
     * @param addressee the intended recipient of the mail
     * @param mail      the registered mail to be assigned
     * @return {@code true} if the assignment succeeds; {@code false}
     * otherwise (e.g., carrier or addressee not in area, mail already assigned,
     * or mail not part of the area)
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier,
                                                Inhabitant addressee,
                                                RegisteredMail mail) {
        // Validate presence in this area
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }
        // Mail must be known by the area
        if (!allMails.contains(mail)) {
            return false;
        }
        // Mail must not already have a carrier
        if (mail.getCarrier() != null) {
            return false;
        }
        // Perform assignment
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        return true;
    }

    /**
     * Adds a new mailman to the geographical area if not already present.
     *
     * @param newMailman the mailman to add
     * @return {@code true} if the mailman was added; {@code false} if a mailman
     * with the same identifier already exists in the area
     */
    public boolean addMailman(Mailman newMailman) {
        if (newMailman == null || mailmen.contains(newMailman)) {
            return false;
        }
        mailmen.add(newMailman);
        return true;
    }

    /**
     * Removes a mailman from the area after reassigning all his deliveries
     * to another existing mailman.
     *
     * @param mailmanToRemove the mailman that should be removed
     * @param newMailman      an existing mailman who will take over the deliveries
     * @return {@code true} if removal and reassignment succeed; {@code false}
     * if the constraints are violated (e.g., only one mailman would remain,
     * newMailman does not exist, or mailmanToRemove is not present)
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null) {
            return false;
        }
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman)) {
            return false;
        }
        // At least one mailman must stay after removal
        if (mailmen.size() <= 1) {
            return false;
        }
        // Reassign all mails currently carried by mailmanToRemove
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
     * Adds a new inhabitant to the geographical area.
     *
     * @param newInhabitant the inhabitant to add
     * @return {@code true} if the inhabitant was added; {@code false} if an
     * inhabitant with the same identifier already exists in the area
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (newInhabitant == null || inhabitants.contains(newInhabitant)) {
            return false;
        }
        inhabitants.add(newInhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from the area and deletes any registered mail
     * addressed to that inhabitant that has already been assigned to a mailman.
     *
     * @param inhabitant the inhabitant to remove
     * @return {@code true} if the inhabitant existed and was removed; {@code false}
     * otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null || !inhabitants.contains(inhabitant)) {
            return false;
        }
        // Remove mails that are both addressed to the inhabitant and already assigned
        allMails.removeIf(mail ->
                inhabitant.equals(mail.getAddressee()) && mail.getCarrier() != null);
        // Remove the inhabitant from the list
        inhabitants.remove(inhabitant);
        return true;
    }

    /**
     * Returns a list of all registered mail items (letters and parcels) that
     * are assigned to the specified carrier.
     *
     * @param carrier the mailman whose deliveries are requested
     * @return an immutable list of mail items, or {@code null} if none exist
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
        return result.isEmpty() ? null : Collections.unmodifiableList(result);
    }

    /**
     * Returns a list of all registered mail items (letters and parcels) addressed
     * to the specified inhabitant.
     *
     * @param addressee the inhabitant whose mail is requested
     * @return an immutable list of mail items, or {@code null} if none exist
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
        return result.isEmpty() ? null : Collections.unmodifiableList(result);
    }

    /** @return an unmodifiable view of the mailmen in this area */
    public List<Mailman> getMailmen() {
        return Collections.unmodifiableList(mailmen);
    }

    /** @return an unmodifiable view of the inhabitants in this area */
    public List<Inhabitant> getInhabitants() {
        return Collections.unmodifiableList(inhabitants);
    }

    /** @return an unmodifiable view of all registered mails in this area */
    public List<RegisteredMail> getAllMails() {
        return Collections.unmodifiableList(allMails);
    }

    /**
     * Adds a registered mail item (letter or parcel) to the area.
     * This method is not part of the original functional requirements but
     * is useful for constructing test scenarios.
     *
     * @param mail the mail item to add
     * @return {@code true} if the mail was added; {@code false} if it already exists
     */
    public boolean addRegisteredMail(RegisteredMail mail) {
        if (mail == null || allMails.contains(mail)) {
            return false;
        }
        allMails.add(mail);
        return true;
    }

    /**
     * Removes a registered mail item from the area.
     *
     * @param mail the mail item to remove
     * @return {@code true} if the mail was removed; {@code false} if it was not present
     */
    public boolean removeRegisteredMail(RegisteredMail mail) {
        return allMails.remove(mail);
    }
}