import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mailman responsible for delivering registered mail in a geographical area.
 */
class Mailman {
    // Empty class as per design model - can be extended with properties if needed
}

/**
 * Represents an inhabitant associated with a geographical area.
 */
class Inhabitant {
    // Empty class as per design model - can be extended with properties if needed
}

/**
 * Abstract class representing registered mail with a carrier and addressee.
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

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
 * Represents a letter, a type of registered mail.
 */
class Letter extends RegisteredMail {
    public Letter() {
        // Unparameterized constructor
    }
}

/**
 * Represents a parcel, a type of registered mail.
 */
class Parcel extends RegisteredMail {
    public Parcel() {
        // Unparameterized constructor
    }
}

/**
 * Represents a geographical area containing inhabitants, mailmen, and registered mails.
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> allMails;

    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.allMails = new ArrayList<>();
    }

    /**
     * Gets the list of mailmen in this geographical area.
     *
     * @return the list of mailmen
     */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /**
     * Sets the list of mailmen in this geographical area.
     *
     * @param mailmen the list of mailmen to set
     */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /**
     * Gets the list of inhabitants in this geographical area.
     *
     * @return the list of inhabitants
     */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /**
     * Sets the list of inhabitants in this geographical area.
     *
     * @param inhabitants the list of inhabitants to set
     */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /**
     * Gets the list of all registered mails in this geographical area.
     *
     * @return the list of registered mails
     */
    public List<RegisteredMail> getAllMails() {
        return allMails;
    }

    /**
     * Sets the list of all registered mails in this geographical area.
     *
     * @param allMails the list of registered mails to set
     */
    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = allMails;
    }

    /**
     * Assigns a specific mailman to deliver a registered inhabitant's mail item.
     * The mailman and the inhabitant must belong to the addressee's geographical area.
     * Ensures the mail isn't already assigned to any mailman.
     *
     * @param carrier   the mailman to assign for delivery
     * @param addressee the inhabitant who is the addressee of the mail
     * @param mail      the registered mail item to be delivered
     * @return true if the assignment is successful; otherwise, false
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        // Check if the mailman belongs to this geographical area
        if (!mailmen.contains(carrier)) {
            return false;
        }

        // Check if the inhabitant belongs to this geographical area
        if (!inhabitants.contains(addressee)) {
            return false;
        }

        // Check if the mail is already assigned to a mailman
        if (mail.getCarrier() != null) {
            return false;
        }

        // Assign the mailman to deliver the mail
        mail.setCarrier(carrier);
        mail.setAddressee(addressee);
        return true;
    }

    /**
     * Lists all registered mail items (letters and parcels) assigned to a specified mailman.
     * Includes only mail items that specify the given mailman as the carrier.
     *
     * @param carrier the mailman for which to list assigned mail items
     * @return a list of registered mail items assigned to the specified mailman, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
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
     * @param addressee the inhabitant for which to list mail items
     * @return a list of registered mail items directed to the specified inhabitant, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (addressee.equals(mail.getAddressee())) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Adds a new mailman to this geographical area.
     *
     * @param newMailman the mailman to add
     * @return true if the operation is successful, false otherwise
     */
    public boolean addMailman(Mailman newMailman) {
        // Check if the mailman is already assigned to this geographical area
        if (mailmen.contains(newMailman)) {
            return false;
        }

        mailmen.add(newMailman);
        return true;
    }

    /**
     * Removes a mailman from this geographical area.
     * Requires keeping at least one mailman in the area.
     * Requires specifying a different, existing mailman to take over deliveries.
     * Requires successfully reassigning all mail before removal.
     *
     * @param mailmanToRemove the mailman to remove
     * @param newMailman      the mailman to take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        // Check if the mailman to remove exists in this area
        if (!mailmen.contains(mailmanToRemove)) {
            return false;
        }

        // Check if there's at least one mailman remaining after removal
        if (mailmen.size() <= 1) {
            return false;
        }

        // Check if the new mailman is different and exists in this area
        if (mailmanToRemove.equals(newMailman) || !mailmen.contains(newMailman)) {
            return false;
        }

        // Reassign all mails from the mailman to remove to the new mailman
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
        inhabitants.add(newInhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from this geographical area.
     * Deletes any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman.
     *
     * @param inhabitantToRemove the inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        // Check if the inhabitant exists in this area
        if (!inhabitants.contains(inhabitantToRemove)) {
            return false;
        }

        // Remove all mails assigned to this inhabitant
        allMails.removeIf(mail -> inhabitantToRemove.equals(mail.getAddressee()));

        // Remove the inhabitant
        inhabitants.remove(inhabitantToRemove);
        return true;
    }
}