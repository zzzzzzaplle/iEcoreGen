import java.util.*;

/**
 * Represents a mailman who delivers registered mail in a geographical area.
 */
class Mailman {
    // Empty unparameterized constructor
    public Mailman() {}
}

/**
 * Represents a geographical area that contains inhabitants, mailmen, and registered mails.
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> allMails;

    /**
     * Constructs a new GeographicalArea with empty lists.
     */
    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.allMails = new ArrayList<>();
    }

    /**
     * Assigns a specific mailman to deliver a registered inhabitant's mail item.
     * The mailman and the inhabitant must belong to the addressee's geographical area.
     * The mail isn't already assigned to any mailman.
     *
     * @param carrier The mailman to assign for delivery
     * @param addressee The inhabitant who is the recipient of the mail
     * @param mail The registered mail item to be assigned
     * @return true if the assignment is successful; otherwise, false
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        // Check if mailman belongs to this geographical area
        if (!mailmen.contains(carrier)) {
            return false;
        }

        // Check if inhabitant belongs to this geographical area
        if (!inhabitants.contains(addressee)) {
            return false;
        }

        // Check if the mail is already assigned to a mailman
        if (mail.getCarrier() != null) {
            return false;
        }

        // Check if the mail's addressee is the specified inhabitant
        if (!mail.getAddressee().equals(addressee)) {
            return false;
        }

        // Assign the carrier to the mail
        mail.setCarrier(carrier);
        return true;
    }

    /**
     * Adds a new mailman to this geographical area if they're not already assigned to it.
     *
     * @param newMailman The mailman to add
     * @return true if the mailman was successfully added; otherwise, false
     */
    public boolean addMailman(Mailman newMailman) {
        if (newMailman == null || mailmen.contains(newMailman)) {
            return false;
        }
        return mailmen.add(newMailman);
    }

    /**
     * Removes a mailman from this geographical area.
     * Requirements:
     * - Must keep at least one mailman in the area
     * - Must specify a different, existing mailman to take over deliveries
     * - Must successfully reassign all mail before removal
     *
     * @param mailmanToRemove The mailman to remove
     * @param newMailman The mailman to take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        // Check if there's at least one mailman remaining after removal
        if (mailmen.size() <= 1) {
            return false;
        }

        // Check if mailmanToRemove exists in this area
        if (!mailmen.contains(mailmanToRemove)) {
            return false;
        }

        // Check if newMailman is different and exists in this area
        if (mailmanToRemove.equals(newMailman) || !mailmen.contains(newMailman)) {
            return false;
        }

        // Reassign all mails assigned to mailmanToRemove to newMailman
        for (RegisteredMail mail : allMails) {
            if (mailmanToRemove.equals(mail.getCarrier())) {
                mail.setCarrier(newMailman);
            }
        }

        // Remove the mailman
        return mailmen.remove(mailmanToRemove);
    }

    /**
     * Adds a new inhabitant to this geographical area.
     *
     * @param newInhabitant The inhabitant to add
     * @return true if the inhabitant was successfully added; otherwise, false
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (newInhabitant == null || inhabitants.contains(newInhabitant)) {
            return false;
        }
        return inhabitants.add(newInhabitant);
    }

    /**
     * Removes an inhabitant from this geographical area.
     * Also deletes any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman.
     *
     * @param inhabitantToRemove The inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitantToRemove == null || !inhabitants.contains(inhabitantToRemove)) {
            return false;
        }

        // Remove all mails directed to this inhabitant that have been assigned to a mailman
        Iterator<RegisteredMail> mailIterator = allMails.iterator();
        while (mailIterator.hasNext()) {
            RegisteredMail mail = mailIterator.next();
            if (inhabitantToRemove.equals(mail.getAddressee()) && mail.getCarrier() != null) {
                mailIterator.remove();
            }
        }

        // Remove the inhabitant
        return inhabitants.remove(inhabitantToRemove);
    }

    /**
     * Lists all registered mail items (letters and parcels) assigned to a specified mailman.
     * Includes only mail items that specify the given mailman as the carrier.
     *
     * @param carrier The mailman whose assigned mails are to be listed
     * @return A list of registered mail items assigned to the carrier, or null if none exist
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
     * Lists all registered mail items (letters and parcels) directed to a specified inhabitant.
     * Includes only mail items that specify the given inhabitant as the addressee.
     *
     * @param addressee The inhabitant whose mails are to be listed
     * @return A list of registered mail items directed to the addressee, or null if none exist
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

    // Getters and setters for private fields to allow testing
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    public List<RegisteredMail> getAllMails() {
        return allMails;
    }

    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = allMails;
    }
}

/**
 * Represents an inhabitant associated with a geographical area.
 */
class Inhabitant {
    // Empty unparameterized constructor
    public Inhabitant() {}
}

/**
 * Represents a registered mail item that has an addressee and a carrier.
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

    /**
     * Constructs a new RegisteredMail with no carrier and no addressee.
     */
    public RegisteredMail() {
        this.carrier = null;
        this.addressee = null;
    }

    /**
     * Gets the mailman carrier for this mail item.
     *
     * @return the carrier mailman
     */
    public Mailman getCarrier() {
        return carrier;
    }

    /**
     * Sets the mailman carrier for this mail item.
     *
     * @param carrier the mailman to set as carrier
     */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /**
     * Gets the inhabitant addressee for this mail item.
     *
     * @return the addressee inhabitant
     */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /**
     * Sets the inhabitant addressee for this mail item.
     *
     * @param addressee the inhabitant to set as addressee
     */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Represents a letter, which is a type of registered mail.
 */
class Letter extends RegisteredMail {
    // Empty unparameterized constructor
    public Letter() {
        super();
    }
}

/**
 * Represents a parcel, which is a type of registered mail.
 */
class Parcel extends RegisteredMail {
    // Empty unparameterized constructor
    public Parcel() {
        super();
    }
}