import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mail carrier.
 */
 class Mailman {

    private String name;
    private int id;

    /** Unparameterized constructor. */
    public Mailman() {
    }

    /** Parameterized constructor for convenience. */
    public Mailman(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /** @return the mailman's name */
    public String getName() {
        return name;
    }

    /** @param name the mailman's name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the mailman's identifier */
    public int getId() {
        return id;
    }

    /** @param id the mailman's identifier to set */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mailman mailman = (Mailman) o;
        return id == mailman.id && Objects.equals(name, mailman.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}

/**
 * Represents an inhabitant of a geographical area.
 */
 class Inhabitant {

    private String name;
    private int id;

    /** Unparameterized constructor. */
    public Inhabitant() {
    }

    /** Parameterized constructor for convenience. */
    public Inhabitant(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /** @return the inhabitant's name */
    public String getName() {
        return name;
    }

    /** @param name the inhabitant's name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the inhabitant's identifier */
    public int getId() {
        return id;
    }

    /** @param id the inhabitant's identifier to set */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inhabitant that = (Inhabitant) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}

/**
 * Abstract base class for all registered mail items.
 */
public abstract class RegisteredMail {

    private Mailman carrier;      // may be null until assigned
    private Inhabitant addressee; // never null for a valid mail item

    /** Unparameterized constructor. */
    public RegisteredMail() {
    }

    /** @return the carrier assigned to this mail, may be {@code null} */
    public Mailman getCarrier() {
        return carrier;
    }

    /** @param carrier the carrier to assign to this mail */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /** @return the addressee of this mail */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /** @param addressee the addressee to set */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Represents a registered letter.
 */
 class Letter extends RegisteredMail {

    /** Unparameterized constructor. */
    public Letter() {
        super();
    }

    // Additional fields specific to letters could be added here
}

/**
 * Represents a registered parcel.
 */
 class Parcel extends RegisteredMail {

    /** Unparameterized constructor. */
    public Parcel() {
        super();
    }

    // Additional fields specific to parcels could be added here
}

/**
 * Manages mailmen, inhabitants and registered mail within a geographical area.
 */
 class GeographicalArea {

    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> allMails;

    /** Unparameterized constructor that initialises internal collections. */
    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.allMails = new ArrayList<>();
    }

    /** @return modifiable list of mailmen */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /** @param mailmen the mailmen list to set */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /** @return modifiable list of inhabitants */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /** @param inhabitants the inhabitants list to set */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /** @return modifiable list of all registered mails */
    public List<RegisteredMail> getAllMails() {
        return allMails;
    }

    /** @param allMails the list of all registered mails to set */
    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = allMails;
    }

    /**
     * Assigns a specific mailman to deliver a registered mail item for a given addressee.
     * <p>
     * The carrier and the addressee must belong to this geographical area, and the mail must
     * not already have a carrier assigned.
     *
     * @param carrier   the mailman who will deliver the mail
     * @param addressee the intended recipient of the mail
     * @param mail      the registered mail item to be assigned
     * @return {@code true} if the assignment succeeds; {@code false} otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        // Validate that carrier and addressee belong to this area
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }

        // Mail must be part of the area (if not, add it)
        if (!allMails.contains(mail)) {
            allMails.add(mail);
        }

        // Ensure mail has no carrier yet
        if (mail.getCarrier() != null) {
            return false;
        }

        // Perform assignment
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        return true;
    }

    /**
     * Adds a new mailman to the geographical area if they are not already present.
     *
     * @param newMailman the mailman to add
     * @return {@code true} if the mailman was added; {@code false} if they already exist
     */
    public boolean addMailman(Mailman newMailman) {
        if (mailmen.contains(newMailman)) {
            return false;
        }
        mailmen.add(newMailman);
        return true;
    }

    /**
     * Removes a mailman from the area after reassigning all of their deliveries to another
     * existing mailman. At least one mailman must remain after removal.
     *
     * @param mailmanToRemove the mailman to be removed
     * @param newMailman      the mailman who will take over the deliveries
     * @return {@code true} if removal and reassignment succeed; {@code false} otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        // Must keep at least one mailman after removal
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman) || mailmen.size() <= 1) {
            return false;
        }

        // Reassign each mail delivered by the mailman to be removed
        for (RegisteredMail mail : allMails) {
            if (mailmanToRemove.equals(mail.getCarrier())) {
                mail.setCarrier(newMailman);
            }
        }

        // Finally remove the mailman
        return mailmen.remove(mailmanToRemove);
    }

    /**
     * Adds a new inhabitant to the geographical area if they are not already present.
     *
     * @param newInhabitant the inhabitant to add
     * @return {@code true} if the inhabitant was added; {@code false} if they already exist
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (inhabitants.contains(newInhabitant)) {
            return false;
        }
        inhabitants.add(newInhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from the area. All registered mails that have this inhabitant as
     * addressee and that have already been assigned to a carrier are deleted.
     *
     * @param addedInhabitant the inhabitant to remove
     * @return {@code true} if the inhabitant (and associated mails) were removed; {@code false}
     *         if the inhabitant does not belong to this area
     */
    public boolean removeInhabitant(Inhabitant addedInhabitant) {
        if (!inhabitants.contains(addedInhabitant)) {
            return false;
        }

        // Remove relevant mails
        List<RegisteredMail> toRemove = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (addedInhabitant.equals(mail.getAddressee()) && mail.getCarrier() != null) {
                toRemove.add(mail);
            }
        }
        allMails.removeAll(toRemove);

        // Remove inhabitant
        return inhabitants.remove(addedInhabitant);
    }

    /**
     * Retrieves all registered mail items that are assigned to a specific mailman.
     *
     * @param carrier the mailman whose deliveries are to be listed
     * @return a list of registered mails delivered by {@code carrier}, or {@code null}
     *         if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        if (!mailmen.contains(carrier)) {
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
     * Retrieves all registered mail items addressed to a specific inhabitant.
     *
     * @param addressee the inhabitant whose incoming mail is to be listed
     * @return a list of registered mails for {@code addressee}, or {@code null}
     *         if none exist
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        if (!inhabitants.contains(addressee)) {
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
}