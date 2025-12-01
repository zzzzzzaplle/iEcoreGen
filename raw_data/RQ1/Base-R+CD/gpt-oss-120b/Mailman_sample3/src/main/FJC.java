import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mail carrier (mailman) who works in a geographical area.
 */
 class Mailman {
    private String name;

    public Mailman() {
        // no‑arg constructor
    }

    public Mailman(String name) {
        this.name = name;
    }

    /** @return the mailman's name */
    public String getName() {
        return name;
    }

    /** @param name the mailman's name */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on name – useful for list operations */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mailman)) return false;
        Mailman mailman = (Mailman) o;
        return Objects.equals(name, mailman.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

/**
 * Represents an inhabitant who can receive registered mail.
 */
 class Inhabitant {
    private String name;

    public Inhabitant() {
        // no‑arg constructor
    }

    public Inhabitant(String name) {
        this.name = name;
    }

    /** @return the inhabitant's name */
    public String getName() {
        return name;
    }

    /** @param name the inhabitant's name */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on name – useful for list operations */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inhabitant)) return false;
        Inhabitant that = (Inhabitant) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

/**
 * Abstract base class for all registered mail items (letters and parcels).
 */
public abstract class RegisteredMail {
    private Mailman carrier;          // may be null until assigned
    private Inhabitant addressee;     // never null – the intended recipient

    public RegisteredMail() {
        // no‑arg constructor
    }

    public RegisteredMail(Inhabitant addressee) {
        this.addressee = addressee;
    }

    /** @return the mail carrier (may be null) */
    public Mailman getCarrier() {
        return carrier;
    }

    /** @param carrier the mail carrier to set */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /** @return the addressee of the mail */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /** @param addressee the addressee to set */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Concrete class representing a registered letter.
 */
 class Letter extends RegisteredMail {
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
    public Parcel() {
        super();
    }

    public Parcel(Inhabitant addressee) {
        super(addressee);
    }
}

/**
 * Manages mailmen, inhabitants, and registered mail within a geographical area.
 */
 class GeographicalArea {
    private List<Mailman> mailmen = new ArrayList<>();
    private List<Inhabitant> inhabitants = new ArrayList<>();
    private List<RegisteredMail> allMails = new ArrayList<>();

    public GeographicalArea() {
        // no‑arg constructor
    }

    /** @return an unmodifiable view of mailmen in this area */
    public List<Mailman> getMailmen() {
        return Collections.unmodifiableList(mailmen);
    }

    /** @param mailmen the mailmen list to set (used mainly for testing) */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /** @return an unmodifiable view of inhabitants in this area */
    public List<Inhabitant> getInhabitants() {
        return Collections.unmodifiableList(inhabitants);
    }

    /** @param inhabitants the inhabitants list to set (used mainly for testing) */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /** @return an unmodifiable view of all registered mails in this area */
    public List<RegisteredMail> getAllails() {
        return Collections.unmodifiableList(allMails);
    }

    /** @param allMails the mail list to set (used mainly for testing) */
    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = allMails;
    }

    /**
     * Assigns a specific mailman to deliver a registered mail item.
     * <p>
     * The carrier and the addressee must both belong to this geographical area.
     * The mail must not already have a carrier assigned.
     *
     * @param carrier   the mailman who will deliver the mail
     * @param addressee the intended recipient of the mail
     * @param mail      the registered mail to be assigned
     * @return {@code true} if the assignment succeeded; {@code false} otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier,
                                               Inhabitant addressee,
                                               RegisteredMail mail) {
        if (carrier == null || addressee == null || mail == null) {
            return false;
        }
        // both carrier and addressee must belong to this area
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }
        // mail must not already be assigned
        if (mail.getCarrier() != null) {
            return false;
        }
        // perform assignment
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        // ensure mail is tracked in the area
        if (!allMails.contains(mail)) {
            allMails.add(mail);
        }
        return true;
    }

    /**
     * Adds a new mailman to the geographical area if they are not already present.
     *
     * @param newMailman the mailman to add
     * @return {@code true} if the mailman was added; {@code false} if they already exist
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
     * Removes a mailman from the area after reassigning all of their deliveries
     * to another existing mailman. At least one mailman must remain after removal.
     *
     * @param mailmanToRemove the mailman that should be removed
     * @param newMailman      the mailman that will take over the deliveries
     * @return {@code true} if removal and reassignment succeeded; {@code false} otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || newMailman == null) {
            return false;
        }
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(newMailman)) {
            return false;
        }
        if (mailmanToRemove.equals(newMailman)) {
            return false; // cannot reassign to self
        }
        // Ensure at least one mailman will remain after removal
        if (mailmen.size() <= 1) {
            return false;
        }
        // Reassign all mails carried by mailmanToRemove
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
     * Removes an inhabitant from the area. Any registered mail that has already been
     * assigned to a mailman and is addressed to this inhabitant is deleted.
     *
     * @param inhabitant the inhabitant to remove
     * @return {@code true} if the inhabitant was removed; {@code false} if they were not present
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null) {
            return false;
        }
        if (!inhabitants.contains(inhabitant)) {
            return false;
        }
        // Remove assigned mails addressed to this inhabitant
        allMails.removeIf(mail ->
                inhabitant.equals(mail.getAddressee()) && mail.getCarrier() != null);
        // Remove the inhabitant
        return inhabitants.remove(inhabitant);
    }

    /**
     * Returns all registered mail (letters and parcels) that are assigned to a given mailman.
     *
     * @param carrier the mailman whose deliveries should be listed
     * @return a list of registered mail items; {@code null} if none exist or carrier is null
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
     * Returns all registered mail (letters and parcels) directed to a given inhabitant.
     *
     * @param addressee the inhabitant whose mail should be listed
     * @return a list of registered mail items; {@code null} if none exist or addressee is null
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