import java.util.ArrayList;
import java.util.List;

/**
 * Represents a geographical area with associated mailmen and inhabitants.
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> registeredMails;

    /**
     * Constructs a new GeographicalArea object.
     */
    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.registeredMails = new ArrayList<>();
    }

    /**
     * Retrieves all registered mail deliveries for this geographical area.
     * 
     * @return A list of RegisteredMail objects representing all deliveries.
     */
    public List<RegisteredMail> getAllDeliveries() {
        return this.registeredMails;
    }

    /**
     * Adds a new inhabitant to this geographical area.
     * 
     * @param newInhabitant The inhabitant to be added.
     * @return True if the inhabitant is added successfully, false otherwise.
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (!this.inhabitants.contains(newInhabitant)) {
            return this.inhabitants.add(newInhabitant);
        }
        return false;
    }

    /**
     * Removes an inhabitant from this geographical area and deletes any registered mail addressed to them.
     * 
     * @param inhabitant The inhabitant to be removed.
     * @return True if the inhabitant is removed successfully, false otherwise.
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (this.inhabitants.contains(inhabitant)) {
            this.inhabitants.remove(inhabitant);
            this.registeredMails.removeIf(mail -> mail.getAddressee().equals(inhabitant) && mail.getCarrier() != null);
            return true;
        }
        return false;
    }

    /**
     * Adds a new mailman to this geographical area if they are not already assigned.
     * 
     * @param newMailman The mailman to be added.
     * @return True if the mailman is added successfully, false otherwise.
     */
    public boolean addMailman(Mailman newMailman) {
        if (!this.mailmen.contains(newMailman)) {
            return this.mailmen.add(newMailman);
        }
        return false;
    }

    /**
     * Removes a mailman from this geographical area after reassigning their deliveries to another mailman.
     * 
     * @param mailmanToRemove The mailman to be removed.
     * @param newMailman      The mailman to take over deliveries.
     * @return True if the mailman is removed successfully, false otherwise.
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (this.mailmen.contains(mailmanToRemove) && this.mailmen.contains(newMailman) && !mailmanToRemove.equals(newMailman)) {
            for (RegisteredMail mail : this.registeredMails) {
                if (mail.getCarrier() != null && mail.getCarrier().equals(mailmanToRemove)) {
                    mail.setCarrier(newMailman);
                }
            }
            return this.mailmen.remove(mailmanToRemove);
        }
        return false;
    }

    /**
     * Assigns a mailman to deliver a registered mail item to an inhabitant.
     * 
     * @param carrier   The mailman to be assigned.
     * @param addressee The inhabitant receiving the mail.
     * @param mail      The registered mail item.
     * @return True if the assignment is successful, false otherwise.
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (this.mailmen.contains(carrier) && this.inhabitants.contains(addressee) && mail.getCarrier() == null) {
            mail.setCarrier(carrier);
            mail.setAddressee(addressee);
            if (this.registeredMails.add(mail)) {
                return true;
            } else {
                mail.setCarrier(null); // Revert the carrier assignment if adding to the list fails
            }
        }
        return false;
    }

    /**
     * Lists all registered mail items delivered by a specific mailman.
     * 
     * @param carrier The mailman whose deliveries are to be listed.
     * @return A list of RegisteredMail objects delivered by the specified mailman.
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : this.registeredMails) {
            if (mail.getCarrier() != null && mail.getCarrier().equals(carrier)) {
                result.add(mail);
            }
        }
        return result;
    }

    /**
     * Lists all registered mail items directed to a specified inhabitant.
     * 
     * @param inhabitant The inhabitant whose mail is to be listed.
     * @return A list of RegisteredMail objects addressed to the specified inhabitant, or null if none exist.
     */
    public List<RegisteredMail> listRegisteredMailForInhabitant(Inhabitant inhabitant) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : this.registeredMails) {
            if (mail.getAddressee().equals(inhabitant)) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }

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

    public List<RegisteredMail> getRegisteredMails() {
        return registeredMails;
    }

    public void setRegisteredMails(List<RegisteredMail> registeredMails) {
        this.registeredMails = registeredMails;
    }
}

/**
 * Represents a mailman.
 */
class Mailman {
    public Mailman() {
    }

    @Override
    public boolean equals(Object obj) {
        // Implement equals logic based on your requirements
        return super.equals(obj);
    }
}

/**
 * Represents an inhabitant.
 */
class Inhabitant {
    public Inhabitant() {
    }

    @Override
    public boolean equals(Object obj) {
        // Implement equals logic based on your requirements
        return super.equals(obj);
    }
}

/**
 * Abstract base class for registered mail items.
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

    /**
     * Constructs a new RegisteredMail object.
     */
    public RegisteredMail() {
    }

    public Mailman getCarrier() {
        return carrier;
    }

    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    public Inhabitant getAddressee() {
        return addressee;
    }

    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Represents a letter.
 */
class Letter extends RegisteredMail {
    public Letter() {
        super();
    }
}

/**
 * Represents a parcel.
 */
class Parcel extends RegisteredMail {
    public Parcel() {
        super();
    }
}