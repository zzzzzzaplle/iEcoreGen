import java.util.*;

/**
 * Represents a mailman who delivers registered mail in a geographical area.
 */
class Mailman {
    // Empty unparameterized constructor
    public Mailman() {}
}

/**
 * Represents a geographical area containing inhabitants, mailmen, and registered mails.
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> allMails;

    /**
     * Creates a new GeographicalArea with empty lists.
     */
    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.allMails = new ArrayList<>();
    }

    /**
     * Assigns a specific mailman to deliver a registered inhabitant's mail item.
     * The mailman and the inhabitant must belong to the addressee's geographical area.
     * The mail must not already be assigned to any mailman.
     *
     * @param carrier   the mailman to assign for delivery
     * @param addressee the inhabitant who is the recipient of the mail
     * @param mail      the registered mail item to be delivered
     * @return true if the assignment is successful; otherwise, false
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        // Check if the mailman belongs to this geographical area
        if (!mailmen.contains(carrier)) {
            return false;
        }

        // Check if the addressee belongs to this geographical area
        if (!inhabitants.contains(addressee)) {
            return false;
        }

        // Check if the mail is in this geographical area's mail list
        if (!allMails.contains(mail)) {
            return false;
        }

        // Check if the mail is already assigned to a mailman
        if (mail.getCarrier() != null) {
            return false;
        }

        // Check if the mail's addressee matches the provided addressee
        if (!mail.getAddressee().equals(addressee)) {
            return false;
        }

        // Assign the carrier to the mail
        mail.setCarrier(carrier);
        return true;
    }

    /**
     * Adds a new mailman to this geographical area if they're not already assigned.
     *
     * @param newMailman the mailman to add
     * @return true if the mailman was successfully added, false otherwise
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
     * Removes a mailman from this geographical area with the following requirements:
     * 1. Keep at least one mailman in the area
     * 2. Specify a different, existing mailman to take over deliveries
     * 3. Successfully reassign all mail before removal
     *
     * @param mailmanToRemove the mailman to remove
     * @param newMailman      the mailman who will take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        // Check if there's at least one mailman remaining after removal
        if (mailmen.size() <= 1) {
            return false;
        }

        // Check if the mailman to remove exists in this area
        if (!mailmen.contains(mailmanToRemove)) {
            return false;
        }

        // Check if the new mailman exists in this area and is different from the one being removed
        if (!mailmen.contains(newMailman) || mailmanToRemove.equals(newMailman)) {
            return false;
        }

        // Reassign all mails assigned to the mailman being removed
        for (RegisteredMail mail : allMails) {
            if (mailmanToRemove.equals(mail.getCarrier())) {
                mail.setCarrier(newMailman);
            }
        }

        // Remove the mailman
        mailmen.remove(mailmanToRemove);
        return true;
    }

    /**
     * Adds a new inhabitant to this geographical area.
     *
     * @param newInhabitant the inhabitant to add
     * @return true if the operation is successful, false otherwise
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
     * Removes an inhabitant from this geographical area and deletes any registered mail
     * whose addressee is that inhabitant and which has already been assigned to a mailman.
     *
     * @param inhabitantToRemove the inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (!inhabitants.contains(inhabitantToRemove)) {
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
        inhabitants.remove(inhabitantToRemove);
        return true;
    }

    /**
     * Lists all registered mail items (letters and parcels) assigned to a specified mailman.
     * Only includes mail items that specify the given mailman as the carrier.
     *
     * @param carrier the mailman whose assigned mails to list
     * @return a list of registered mail items or null if none exist
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
     * Only includes mail items that specify the given inhabitant as the addressee.
     *
     * @param addressee the inhabitant whose mails to list
     * @return a list of registered mail items or null if none exist
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
 * Abstract class representing registered mail with a carrier and addressee.
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

    /**
     * Creates a new RegisteredMail with no carrier and no addressee.
     */
    public RegisteredMail() {
        this.carrier = null;
        this.addressee = null;
    }

    /**
     * Gets the mailman assigned to deliver this mail.
     *
     * @return the carrier mailman
     */
    public Mailman getCarrier() {
        return carrier;
    }

    /**
     * Sets the mailman assigned to deliver this mail.
     *
     * @param carrier the mailman to assign
     */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /**
     * Gets the inhabitant who is the recipient of this mail.
     *
     * @return the addressee inhabitant
     */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /**
     * Sets the inhabitant who is the recipient of this mail.
     *
     * @param addressee the recipient inhabitant
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