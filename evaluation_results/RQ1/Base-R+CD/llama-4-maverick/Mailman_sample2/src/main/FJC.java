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
     * Gets all mailmen in the geographical area.
     * @return List of Mailman objects
     */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /**
     * Sets the list of mailmen in the geographical area.
     * @param mailmen List of Mailman objects
     */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /**
     * Gets all inhabitants in the geographical area.
     * @return List of Inhabitant objects
     */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /**
     * Sets the list of inhabitants in the geographical area.
     * @param inhabitants List of Inhabitant objects
     */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /**
     * Gets all registered mail deliveries in the geographical area.
     * @return List of RegisteredMail objects
     */
    public List<RegisteredMail> getRegisteredMails() {
        return registeredMails;
    }

    /**
     * Sets the list of registered mail deliveries in the geographical area.
     * @param registeredMails List of RegisteredMail objects
     */
    public void setRegisteredMails(List<RegisteredMail> registeredMails) {
        this.registeredMails = registeredMails;
    }

    /**
     * Retrieves all registered mail deliveries for the geographical area.
     * @return List of RegisteredMail objects
     */
    public List<RegisteredMail> getAllDeliveries() {
        return registeredMails;
    }

    /**
     * Adds a new inhabitant to the geographical area.
     * @param newInhabitant Inhabitant object to be added
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
     * @param inhabitantToRemove Inhabitant object to be removed
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
     * Adds a mailman to the geographical area if they are not already assigned.
     * @param newMailman Mailman object to be added
     * @return true if the mailman is added successfully, false otherwise
     */
    public boolean addMailman(Mailman newMailman) {
        if (!mailmen.contains(newMailman)) {
            return mailmen.add(newMailman);
        }
        return false;
    }

    /**
     * Removes a mailman from the geographical area after reassigning their deliveries to another mailman.
     * @param mailmanToRemove Mailman object to be removed
     * @param newMailman Mailman object to take over deliveries
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
     * @param carrier Mailman object to deliver the mail
     * @param addressee Inhabitant object receiving the mail
     * @param mail RegisteredMail object to be delivered
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
     * @param inhabitant Inhabitant object to retrieve mail for
     * @return List of RegisteredMail objects directed to the inhabitant, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMailForInhabitant(Inhabitant inhabitant) {
        List<RegisteredMail> mailForInhabitant = new ArrayList<>();
        for (RegisteredMail mail : registeredMails) {
            if (mail.getAddressee().equals(inhabitant)) {
                mailForInhabitant.add(mail);
            }
        }
        return mailForInhabitant.isEmpty() ? null : mailForInhabitant;
    }
}

/**
 * Represents a mailman.
 */
class Mailman {
    /**
     * Constructs a new Mailman object.
     */
    public Mailman() {
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

/**
 * Represents an inhabitant.
 */
class Inhabitant {
    /**
     * Constructs a new Inhabitant object.
     */
    public Inhabitant() {
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

/**
 * Abstract class representing registered mail.
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

    /**
     * Constructs a new RegisteredMail object.
     */
    public RegisteredMail() {
    }

    /**
     * Gets the mailman carrying the registered mail.
     * @return Mailman object
     */
    public Mailman getCarrier() {
        return carrier;
    }

    /**
     * Sets the mailman carrying the registered mail.
     * @param carrier Mailman object
     */
    public void setCarrier(Mailman carrier) {
        this.carrier = carrier;
    }

    /**
     * Gets the addressee of the registered mail.
     * @return Inhabitant object
     */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /**
     * Sets the addressee of the registered mail.
     * @param addressee Inhabitant object
     */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

/**
 * Represents a letter, a type of registered mail.
 */
class Letter extends RegisteredMail {
    /**
     * Constructs a new Letter object.
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
     * Constructs a new Parcel object.
     */
    public Parcel() {
        super();
    }
}