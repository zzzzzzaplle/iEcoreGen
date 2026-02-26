import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mail carrier (mailman) that works in a {@link GeographicalArea}.
 */
 class Mailman {


    private String name;
    private String id;

    /** Unparameterized constructor required for testing. */
    public Mailman() {
    }

    public Mailman(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /** Returns the unique identifier of the mailman. */
    public String getId() {
        return id;
    }

    /** Sets the unique identifier of the mailman. */
    public void setId(String id) {
        this.id = id;
    }

    /** Returns the name of the mailman. */
    public String getName() {
        return name;
    }

    /** Sets the name of the mailman. */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on the unique identifier. */
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
 * Represents an inhabitant living in a {@link GeographicalArea}.
 */
 class Inhabitant {

    private String name;
    private String id;

    /** Unparameterized constructor required for testing. */
    public Inhabitant() {
    }

    public Inhabitant(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /** Returns the unique identifier of the inhabitant. */
    public String getId() {
        return id;
    }

    /** Sets the unique identifier of the inhabitant. */
    public void setId(String id) {
        this.id = id;
    }

    /** Returns the name of the inhabitant. */
    public String getName() {
        return name;
    }

    /** Sets the name of the inhabitant. */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on the unique identifier. */
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
 * Base class for all registered mail items (letters and parcels).
 */
public abstract class RegisteredMail {

    private Mailman carrier;       // may be null if not yet assigned
    private Inhabitant addressee;  // must be non‑null for a valid mail item

    /** Unparameterized constructor required for testing. */
    public RegisteredMail() {
    }

    public RegisteredMail(Inhabitant addressee) {
        this.addressee = addressee;
    }

    /** Returns the carrier (mailman) that delivers this mail. May be null. */
    public Mailman getCarrier() {
        return carrier;
    }

    /** Sets the carrier (mailman) for this mail. */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /** Returns the addressee (inhabitant) of this mail. */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /** Sets the addressee (inhabitant) of this mail. */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Concrete class representing a registered letter.
 */
 class Letter extends RegisteredMail {

    /** Unparameterized constructor required for testing. */
    public Letter() {
        super();
    }

    public Letter(Inhabitant addressee) {
        super(addressee);
    }
}

/**
 * Concrete class representing a registered parcel.
 */
 class Parcel extends RegisteredMail {

    /** Unparameterized constructor required for testing. */
    public Parcel() {
        super();
    }

    public Parcel(Inhabitant addressee) {
        super(addressee);
    }
}

/**
 * Represents a geographical area that groups mailmen, inhabitants and
 * the registered mail items that travel within it.
 */
 class GeographicalArea {

    private List<Mailman> mailmen = new ArrayList<>();
    private List<Inhabitant> inhabitants = new ArrayList<>();
    private List<RegisteredMail> allMails = new ArrayList<>();

    /** Unparameterized constructor required for testing. */
    public GeographicalArea() {
    }

    /** Returns an unmodifiable view of the mailmen in this area. */
    public List<Mailman> getMailmen() {
        return Collections.unmodifiableList(mailmen);
    }

    /** Returns an unmodifiable view of the inhabitants in this area. */
    public List<Inhabitant> getInhabitants() {
        return Collections.unmodifiableList(inhabitants);
    }

    /** Returns an unmodifiable view of all registered mails in this area. */
    public List<RegisteredMail> getAllMails() {
        return Collections.unmodifiableList(allMails);
    }

    /**
     * Assigns a specific mailman to deliver a registered mail item.
     * The mailman and the inhabitant must belong to this geographical area.
     * The mail must not already be assigned to any mailman.
     *
     * @param carrier   the mailman who will deliver the mail
     * @param addressee the intended recipient of the mail
     * @param mail      the registered mail item to be assigned
     * @return {@code true} if the assignment succeeds; {@code false} otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (carrier == null || addressee == null || mail == null) {
            return false;
        }
        // both carrier and addressee must belong to this area
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }
        // mail must not already have a carrier
        if (mail.getCarrier() != null) {
            return false;
        }

        // perform assignment
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        if (!allMails.contains(mail)) {
            allMails.add(mail);
        }
        return true;
    }

    /**
     * Adds a new mailman to the geographical area if not already present.
     *
     * @param newMailman the mailman to add
     * @return {@code true} if the mailman was added; {@code false} if already present or null
     */
    public boolean addMailman(Mailman newMailman) {
        if (newMailman == null) {
            return false;
        }
        if (mailmen.contains(newMailman)) {
            return false;
        }
        mailmen.add(newMailman);
        return true;
    }

    /**
     * Removes a mailman from the area after reassigning all of his deliveries.
     * At least one mailman must remain after removal.
     *
     * @param mailmanToRemove the mailman that is to be removed
     * @param newMailman      an existing mailman who will take over the deliveries
     * @return {@code true} if removal and reassignment succeed; {@code false} otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null) {
            return false;
        }
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman)) {
            return false;
        }
        if (mailmen.size() <= 1) { // would leave the area without a mailman
            return false;
        }

        // Reassign all mails belonging to the removed mailman
        for (RegisteredMail mail : allMails) {
            if (mailmanToRemove.equals(mail.getCarrier())) {
                mail.setCarrier(newMailman);
            }
        }

        // Finally remove the mailman
        return mailmen.remove(mailmanToRemove);
    }

    /**
     * Adds a new inhabitant to the geographical area if not already present.
     *
     * @param newInhabitant the inhabitant to add
     * @return {@code true} if the inhabitant was added; {@code false} if already present or null
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (newInhabitant == null) {
            return false;
        }
        if (inhabitants.contains(newInhabitant)) {
            return false;
        }
        inhabitants.add(newInhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from the area and deletes any already‑assigned
     * registered mail whose addressee is that inhabitant.
     *
     * @param inhabitant the inhabitant to remove
     * @return {@code true} if the inhabitant was removed (or did not exist); {@code false} otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null) {
            return false;
        }
        if (!inhabitants.contains(inhabitant)) {
            return false;
        }

        // Remove mails that have this inhabitant as addressee and already have a carrier
        allMails.removeIf(mail -> inhabitant.equals(mail.getAddressee()) && mail.getCarrier() != null);

        // Remove the inhabitant itself
        return inhabitants.remove(inhabitant);
    }

    /**
     * Retrieves all registered mail items (letters and parcels) that are
     * assigned to the specified mailman.
     *
     * @param carrier the mailman whose deliveries are requested
     * @return a {@link List} of matching mail items, or {@code null} if none exist
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
     * Retrieves all registered mail items (letters and parcels) that are
     * addressed to the specified inhabitant.
     *
     * @param addressee the inhabitant whose incoming mail is requested
     * @return a {@link List} of matching mail items, or {@code null} if none exist
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

    // -----------------------------------------------------------------------
    // Additional setters (mostly for testing) – not part of the original spec
    // -----------------------------------------------------------------------

    /** Sets the internal list of mailmen (used mainly for testing). */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen != null ? mailmen : new ArrayList<>();
    }

    /** Sets the internal list of inhabitants (used mainly for testing). */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants != null ? inhabitants : new ArrayList<>();
    }

    /** Sets the internal list of all registered mails (used mainly for testing). */
    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = allMails != null ? allMails : new ArrayList<>();
    }
}