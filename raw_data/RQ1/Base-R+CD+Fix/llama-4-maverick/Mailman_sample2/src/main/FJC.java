import java.util.ArrayList;
import java.util.List;

/**
 * Represents a geographical area with mailmen, inhabitants, and registered mail.
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> allMails;

    /**
     * Constructs a new GeographicalArea.
     */
    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.allMails = new ArrayList<>();
    }

    /**
     * Assigns a mailman to deliver a registered mail item to an inhabitant.
     * 
     * @param carrier  the mailman to assign
     * @param addressee the inhabitant to receive the mail
     * @param mail      the registered mail item
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        if (mailmen.contains(carrier) && inhabitants.contains(addressee) && mail.getCarrier() == null) {
            mail.setCarrier(carrier);
            mail.setAddressee(addressee);
            allMails.add(mail);
            return true;
        }
        return false;
    }

    /**
     * Adds a new mailman to the geographical area.
     * 
     * @param newMailman the mailman to add
     * @return true if the mailman is added successfully, false otherwise
     */
    public boolean addMailman(Mailman newMailman) {
        if (!mailmen.contains(newMailman)) {
            mailmen.add(newMailman);
            return true;
        }
        return false;
    }

    /**
     * Removes a mailman from the geographical area and reassigns their deliveries.
     * 
     * @param mailmanToRemove the mailman to remove
     * @param newMailman      the mailman to take over deliveries
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmen.contains(mailmanToRemove) && mailmen.contains(newMailman) && mailmen.size() > 1) {
            for (RegisteredMail mail : allMails) {
                if (mail.getCarrier() == mailmanToRemove) {
                    mail.setCarrier(newMailman);
                }
            }
            mailmen.remove(mailmanToRemove);
            return true;
        }
        return false;
    }

    /**
     * Adds a new inhabitant to the geographical area.
     * 
     * @param newInhabitant the inhabitant to add
     * @return true if the inhabitant is added successfully, false otherwise
     */
    public boolean addInhabitant(Inhabitant newInhabitant) {
        if (!inhabitants.contains(newInhabitant)) {
            inhabitants.add(newInhabitant);
            return true;
        }
        return false;
    }

    /**
     * Removes an inhabitant from the geographical area and deletes their registered mail.
     * 
     * @param inhabitantToRemove the inhabitant to remove
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitantToRemove) {
        if (inhabitants.contains(inhabitantToRemove)) {
            inhabitants.remove(inhabitantToRemove);
            allMails.removeIf(mail -> mail.getAddressee() == inhabitantToRemove);
            return true;
        }
        return false;
    }

    /**
     * Lists all registered mail items assigned to a specified mailman.
     * 
     * @param carrier the mailman to list mail for
     * @return a list of registered mail items, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMail(Mailman carrier) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (mail.getCarrier() == carrier) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Lists all registered mail items directed to a specified inhabitant.
     * 
     * @param addressee the inhabitant to list mail for
     * @return a list of registered mail items, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMailWithInhabitant(Inhabitant addressee) {
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : allMails) {
            if (mail.getAddressee() == addressee) {
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

    public List<RegisteredMail> getAllMails() {
        return allMails;
    }

    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = allMails;
    }
}

/**
 * Represents a mailman.
 */
class Mailman {
    public Mailman() {
    }
}

/**
 * Represents an inhabitant.
 */
class Inhabitant {
    public Inhabitant() {
    }
}

/**
 * Represents registered mail.
 */
abstract class RegisteredMail {
    private Mailman carrier;
    private Inhabitant addressee;

    /**
     * Constructs a new RegisteredMail.
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