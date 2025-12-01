import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mail carrier (mailman) working in a geographical area.
 */
class Mailman {
    private String name;
    private String id;

    /** Unparameterized constructor required for tests. */
    public Mailman() {
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
    public String getId() {
        return id;
    }

    /** @param id the mailman's identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** Equality based on unique identifier. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mailman)) return false;
        Mailman mailman = (Mailman) o;
        return Objects.equals(id, mailman.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents an inhabitant (recipient) living in a geographical area.
 */
class Inhabitant {
    private String name;
    private String id;

    /** Unparameterized constructor required for tests. */
    public Inhabitant() {
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
    public String getId() {
        return id;
    }

    /** @param id the inhabitant's identifier to set */
    public void setId(String id) {
        this.id = id;
    }

    /** Equality based on unique identifier. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inhabitant)) return false;
        Inhabitant that = (Inhabitant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Abstract base class for all registered mails (letters and parcels).
 */
abstract class RegisteredMail {
    private Mailman carrier;      // may be null if not yet assigned
    private Inhabitant addressee; // must never be null

    /** Unparameterized constructor required for tests. */
    public RegisteredMail() {
    }

    /** @return the carrier that delivers the mail (may be null) */
    public Mailman getCarrier() {
        return carrier;
    }

    /** @param carrier the carrier to set (may be null) */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /** @return the addressee of the mail */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /** @param addressee the addressee to set (must not be null) */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Concrete class representing a registered letter.
 */
class Letter extends RegisteredMail {
    /** Unparameterized constructor required for tests. */
    public Letter() {
    }

    // Additional fields specific to Letter could be added here.
}

/**
 * Concrete class representing a registered parcel.
 */
class Parcel extends RegisteredMail {
    /** Unparameterized constructor required for tests. */
    public Parcel() {
    }

    // Additional fields specific to Parcel could be added here.
}

/**
 * Represents a geographical area that groups mailmen, inhabitants and all registered mails.
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

    /** @return the list of mailmen (modifiable) */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /** @param mailmen the mailmen list to set */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /** @return the list of inhabitants (modifiable) */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /** @param inhabitants the inhabitants list to set */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /** @return the list of all registered mails (modifiable) */
    public List<RegisteredMail> getAllMails() {
        return allMails;
    }

    /** @param allMails the collection of all mails to set */
    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = allMails;
    }

    /**
     * Assigns a specific mailman to deliver a registered mail item.
     * Both the carrier and the addressee must belong to this geographical area,
     * and the mail must not already have a carrier assigned.
     *
     * @param carrier   the mailman that will deliver the mail
     * @param addressee the intended recipient of the mail
     * @param mail      the registered mail to be assigned
     * @return {@code true} if the assignment succeeded; {@code false} otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        // Preconditions: carrier, addressee and mail must be non‑null
        if (carrier == null || addressee == null || mail == null) {
            return false;
        }
        // Both carrier and addressee must belong to this area
        if (!mailmen.contains(carrier) || !inhabitants.contains(addressee)) {
            return false;
        }
        // Mail must be part of this area
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
     * Adds a new mailman to the geographical area if they are not already present.
     *
     * @param newMailman the mailman to be added
     * @return {@code true} if the mailman was added; {@code false} if they already exist or the argument is {@code null}
     */
    public boolean addMailman(Mailman newMailman) {
        if (newMailman == null) {
            return false;
        }
        if (mailmen.contains(newMailman)) {
            return false;
        }
        return mailmen.add(newMailman);
    }

    /**
     * Removes a mailman from the area after re‑assigning all of their deliveries to another mailman.
     * At least one mailman must remain after removal.
     *
     * @param mailmanToRemove the mailman that should be removed
     * @param newMailman      an existing mailman who will take over the deliveries
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
            return false; // cannot reassign to the same person
        }
        if (mailmen.size() <= 1) {
            return false; // would leave the area without any mailman
        }

        // Reassign all mails delivered by mailmanToRemove
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
     * @param newInhabitant the inhabitant to be added
     * @return {@code true} if the inhabitant was added; {@code false} if they already exist or the argument is {@code null}
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (newInhabitant == null) {
            return false;
        }
        if (inhabitants.contains(newInhabitant)) {
            return false;
        }
        return inhabitants.add(newInhabitant);
    }

    /**
     * Removes an inhabitant from the area and deletes any registered mail that
     * (a) is addressed to that inhabitant and (b) already has a carrier assigned.
     *
     * @param inhabitantToRemove the inhabitant to be removed
     * @return {@code true} if the inhabitant existed and was removed; {@code false} otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitantToRemove == null) {
            return false;
        }
        if (!inhabitants.contains(inhabitantToRemove)) {
            return false;
        }

        // Remove mails that belong to the inhabitant and have already been assigned
        List<RegisteredMail> toDelete = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (inhabitantToRemove.equals(mail.getAddressee()) && mail.getCarrier() != null) {
                toDelete.add(mail);
            }
        }
        allMails.removeAll(toDelete);

        // Finally remove the inhabitant
        return inhabitants.remove(inhabitantToRemove);
    }

    /**
     * Returns a list of all registered mails (letters and parcels) that are assigned
     * to the specified mailman. If no such mails exist, {@code null} is returned.
     *
     * @param carrier the mailman whose deliveries should be listed
     * @return an immutable list of mails delivered by {@code carrier}, or {@code null} if none exist
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
     * Returns a list of all registered mails (letters and parcels) addressed to a
     * particular inhabitant. If none exist, {@code null} is returned.
     *
     * @param addressee the inhabitant whose incoming mails should be listed
     * @return an immutable list of mails addressed to {@code addressee}, or {@code null} if none exist
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

    /**
     * Helper method to add a mail item to the area. The mail must have a non‑null
     * addressee that belongs to this area. The carrier may be null (unassigned).
     *
     * @param mail the registered mail to add
     * @return {@code true} if the mail was added; {@code false} otherwise
     */
    public boolean addMail(RegisteredMail mail) {
        if (mail == null || mail.getAddressee() == null) {
            return false;
        }
        if (!inhabitants.contains(mail.getAddressee())) {
            return false;
        }
        // Carrier, if present, must also belong to this area
        if (mail.getCarrier() != null && !mailmen.contains(mail.getCarrier())) {
            return false;
        }
        return allMails.add(mail);
    }
}