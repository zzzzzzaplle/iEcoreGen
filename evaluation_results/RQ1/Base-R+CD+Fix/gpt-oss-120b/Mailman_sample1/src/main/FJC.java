import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Represents a mailman (letter carrier) who can deliver registered mail.
 */
 class Mailman {
    private String name;
    private int id;

    /** No‑argument constructor */
    public Mailman() {
    }

    /** Constructor with fields */
    public Mailman(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /** @return the mailman's identifier */
    public int getId() {
        return id;
    }

    /** @param id the identifier to set */
    public void setId(int id) {
        this.id = id;
    }

    /** @return the mailman's name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on {@code id} (assumed unique) */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mailman)) return false;
        Mailman mailman = (Mailman) o;
        return id == mailman.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents an inhabitant (recipient) of registered mail.
 */
 class Inhabitant {
    private String name;
    private int id;

    /** No‑argument constructor */
    public Inhabitant() {
    }

    /** Constructor with fields */
    public Inhabitant(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /** @return the inhabitant's identifier */
    public int getId() {
        return id;
    }

    /** @param id the identifier to set */
    public void setId(int id) {
        this.id = id;
    }

    /** @return the inhabitant's name */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** Equality based on {@code id} (assumed unique) */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inhabitant)) return false;
        Inhabitant that = (Inhabitant) o;
        return id == that.id;
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
    private Mailman carrier;      // may be null until assigned
    private Inhabitant addressee; // never null for a valid mail item

    /** No‑argument constructor */
    public RegisteredMail() {
    }

    /** Constructor with addressee */
    public RegisteredMail(Inhabitant addressee) {
        this.addressee = addressee;
    }

    /** @return the mail carrier (may be {@code null} if not yet assigned) */
    public Mailman getCarrier() {
        return carrier;
    }

    /** @param carrier the carrier to set */
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
    /** No‑argument constructor */
    public Letter() {
        super();
    }

    /** Constructor with addressee */
    public Letter(Inhabitant addressee) {
        super(addressee);
    }
}

/**
 * Concrete class representing a registered parcel.
 */
 class Parcel extends RegisteredMail {
    /** No‑argument constructor */
    public Parcel() {
        super();
    }

    /** Constructor with addressee */
    public Parcel(Inhabitant addressee) {
        super(addressee);
    }
}

/**
 * Represents a geographical area that groups mailmen, inhabitants and their mail.
 */
 class GeographicalArea {

    private List<Mailman> mailmen = new ArrayList<>();
    private List<Inhabitant> inhabitants = new ArrayList<>();
    private List<RegisteredMail> allMails = new ArrayList<>();

    /** No‑argument constructor */
    public GeographicalArea() {
    }

    /** @return the list of mailmen in this area */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /** @param mailmen the mailmen list to set */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /** @return the list of inhabitants in this area */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /** @param inhabitants the inhabitants list to set */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /** @return the list of all registered mails in this area */
    public List<RegisteredMail> getAllMails() {
        return allMails;
    }

    /** @param allMails the mail list to set */
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
     * @param addressee the recipient of the mail
     * @param mail      the registered mail to be assigned
     * @return {@code true} if the assignment succeeds; {@code false} otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (carrier == null || addressee == null || mail == null) {
            return false;
        }
        // check carrier belongs to this area
        if (!mailmen.contains(carrier)) {
            return false;
        }
        // check addressee belongs to this area
        if (!inhabitants.contains(addressee)) {
            return false;
        }
        // ensure mail is part of this area
        if (!allMails.contains(mail)) {
            return false;
        }
        // mail must not already have a carrier
        if (mail.getCarrier() != null) {
            return false;
        }
        // assign
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        return true;
    }

    /**
     * Adds a new mailman to the geographical area if they are not already present.
     *
     * @param newMailman the mailman to add
     * @return {@code true} if the mailman was added; {@code false} if they already exist or are {@code null}
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
     * Removes a mailman from the area after reassigning all of their deliveries to another mailman.
     * <p>
     * The area must retain at least one mailman after removal.
     *
     * @param mailmanToRemove the mailman to be removed
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
        // there must be at least one other mailman after removal
        if (mailmen.size() <= 1) {
            return false;
        }
        // reassign all mails currently carried by mailmanToRemove
        for (RegisteredMail mail : allMails) {
            if (mailmanToRemove.equals(mail.getCarrier())) {
                mail.setCarrier(newMailman);
            }
        }
        // finally remove the mailman
        mailmen.remove(mailmanToRemove);
        return true;
    }

    /**
     * Adds a new inhabitant to the geographical area if they are not already present.
     *
     * @param newInhabitant the inhabitant to add
     * @return {@code true} if the inhabitant was added; {@code false} if they already exist or are {@code null}
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
     * Removes an inhabitant from the area. All registered mails that are addressed to this inhabitant
     * and already have a carrier assigned are deleted from the system.
     *
     * @param removedInhabitant the inhabitant to remove
     * @return {@code true} if removal succeeds; {@code false} if the inhabitant does not belong to this area
     */
    public boolean removeInhabitant(Inhabitant removedInhabitant) {
        if (removedInhabitant == null) {
            return false;
        }
        if (!inhabitants.contains(removedInhabitant)) {
            return false;
        }

        // Remove mails addressed to this inhabitant that already have a carrier
        Iterator<RegisteredMail> iterator = allMails.iterator();
        while (iterator.hasNext()) {
            RegisteredMail mail = iterator.next();
            if (removedInhabitant.equals(mail.getAddressee()) && mail.getCarrier() != null) {
                iterator.remove();
            }
        }

        // Finally remove the inhabitant
        inhabitants.remove(removedInhabitant);
        return true;
    }

    /**
     * Lists all registered mail items (letters and parcels) that are currently assigned to the specified mailman.
     *
     * @param carrier the mailman whose deliveries should be listed
     * @return a list of {@link RegisteredMail} objects assigned to {@code carrier},
     * or {@code null} if none exist or the carrier is not part of this area
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        if (carrier == null || !mailmen.contains(carrier)) {
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
     * Lists all registered mail items (letters and parcels) that are addressed to the given inhabitant.
     *
     * @param addressee the inhabitant whose mail should be listed
     * @return a list of {@link RegisteredMail} objects addressed to {@code addressee},
     * or {@code null} if none exist or the inhabitant is not part of this area
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        if (addressee == null || !inhabitants.contains(addressee)) {
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

    /**
     * Helper method to add a registered mail item to the area.
     * This method is not part of the original specification but is useful for tests
     * and for building the initial state.
     *
     * @param mail the registered mail to add
     * @return {@code true} if the mail was added; {@code false} if {@code mail} is {@code null} or already present
     */
    public boolean addRegisteredMail(RegisteredMail mail) {
        if (mail == null) {
            return false;
        }
        if (allMails.contains(mail)) {
            return false;
        }
        allMails.add(mail);
        return true;
    }
}