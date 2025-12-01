import java.util.ArrayList;
import java.util.List;

/**
 * Represents a geographical area with mailmen and inhabitants.
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> registeredMails;

    /**
     * Default constructor for GeographicalArea.
     */
    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.registeredMails = new ArrayList<>();
    }

    /**
     * Gets all mailmen in the geographical area.
     * @return List of mailmen
     */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /**
     * Sets the list of mailmen in the geographical area.
     * @param mailmen List of mailmen
     */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /**
     * Gets all inhabitants in the geographical area.
     * @return List of inhabitants
     */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /**
     * Sets the list of inhabitants in the geographical area.
     * @param inhabitants List of inhabitants
     */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /**
     * Gets all registered mail deliveries in the geographical area.
     * @return List of registered mail deliveries
     */
    public List<RegisteredMail> getRegisteredMails() {
        return registeredMails;
    }

    /**
     * Sets the list of registered mail deliveries in the geographical area.
     * @param registeredMails List of registered mail deliveries
     */
    public void setRegisteredMails(List<RegisteredMail> registeredMails) {
        this.registeredMails = registeredMails;
    }

    /**
     * Retrieves all registered mail deliveries for the geographical area.
     * @return List of registered mail deliveries with the responsible mailman and addressees
     */
    public List<RegisteredMail> getAllDeliveries() {
        return registeredMails;
    }

    /**
     * Adds a new inhabitant to the geographical area.
     * @param newInhabitant Inhabitant to be added
     * @return true if the inhabitant is added successfully, false otherwise
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (!inhabitants.contains(newInhabitant)) {
            return inhabitants.add(newInhabitant);
        }
        return false;
    }

    /**
     * Removes an inhabitant from the geographical area and deletes any registered mail addressed to the inhabitant.
     * @param inhabitantToRemove Inhabitant to be removed
     * @return true if the inhabitant is removed successfully, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitants.contains(inhabitantToRemove)) {
            registeredMails.removeIf(mail -> mail.getAddressee().equals(inhabitantToRemove) && mail.getCarrier() != null);
            return inhabitants.remove(inhabitantToRemove);
        }
        return false;
    }

    /**
     * Adds a mailman to the geographical area if not already assigned.
     * @param newMailman Mailman to be added
     * @return true if the mailman is added successfully, false otherwise
     */
    public boolean addMailman(Mailman newMailman) {
        if (!mailmen.contains(newMailman)) {
            return mailmen.add(newMailman);
        }
        return false;
    }

    /**
     * Removes a mailman from the geographical area after reassigning deliveries to another mailman.
     * @param mailmanToRemove Mailman to be removed
     * @param newMailman Mailman to take over deliveries
     * @return true if the mailman is removed successfully, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmen.contains(mailmanToRemove) && mailmen.contains(newMailman) && !mailmanToRemove.equals(newMailman)) {
            for (RegisteredMail mail : registeredMails) {
                if (mail.getCarrier().equals(mailmanToRemove)) {
                    mail.setCarrier(newMailman);
                }
            }
            return mailmen.remove(mailmanToRemove);
        }
        return false;
    }

    /**
     * Assigns a mailman to deliver a registered mail item to an inhabitant.
     * @param carrier Mailman to deliver the mail
     * @param addressee Inhabitant receiving the mail
     * @param mail Registered mail item to be delivered
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (mailmen.contains(carrier) && inhabitants.contains(addressee) && mail.getCarrier() == null) {
            mail.setCarrier(carrier);
            mail.setAddressee(addressee);
            if (!registeredMails.contains(mail)) {
                registeredMails.add(mail);
            }
            return true;
        }
        return false;
    }

    /**
     * Lists all registered mail items directed to a specified inhabitant.
     * @param inhabitant Inhabitant for whom to list registered mail
     * @return List of registered mail items directed to the inhabitant, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMailForInhabitant(Inhabitant inhabitant) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : registeredMails) {
            if (mail.getAddressee().equals(inhabitant)) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }
}

/**
 * Represents a mailman.
 */
class Mailman {
    /**
     * Default constructor for Mailman.
     */
    public Mailman() {
    }
}

/**
 * Represents an inhabitant.
 */
class Inhabitant {
    /**
     * Default constructor for Inhabitant.
     */
    public Inhabitant() {
    }
}

/**
 * Abstract class representing registered mail.
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

    /**
     * Default constructor for RegisteredMail.
     */
    public RegisteredMail() {
    }

    /**
     * Gets the mailman carrying the registered mail.
     * @return Mailman carrying the mail
     */
    public Mailman getCarrier() {
        return carrier;
    }

    /**
     * Sets the mailman carrying the registered mail.
     * @param carrier Mailman carrying the mail
     */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /**
     * Gets the addressee of the registered mail.
     * @return Inhabitant receiving the mail
     */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /**
     * Sets the addressee of the registered mail.
     * @param addressee Inhabitant receiving the mail
     */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Represents a letter, a type of registered mail.
 */
class Letter extends RegisteredMail {
    /**
     * Default constructor for Letter.
     */
    public Letter() {
        super();
    }
}

/**
 * Represents a parcel, a type of registered mail.
 */
class Parcel extends RegisteredMail {
    /**
     * Default constructor for Parcel.
     */
    public Parcel() {
        super();
    }
}