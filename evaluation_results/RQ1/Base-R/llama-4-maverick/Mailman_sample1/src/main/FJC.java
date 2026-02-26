import java.util.*;

/**
 * Represents a geographical area.
 */
class GeographicalArea {
    private String name;
    private List<Inhabitant> inhabitants;
    private List<Mailman> mailmen;
    private List<RegisteredMail> registeredMails;

    /**
     * Constructs a new GeographicalArea.
     */
    public GeographicalArea() {
        this.inhabitants = new ArrayList<>();
        this.mailmen = new ArrayList<>();
        this.registeredMails = new ArrayList<>();
    }

    /**
     * Gets the name of the geographical area.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the geographical area.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the inhabitants of the geographical area.
     * @return the inhabitants
     */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /**
     * Gets the mailmen of the geographical area.
     * @return the mailmen
     */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /**
     * Gets the registered mails of the geographical area.
     * @return the registered mails
     */
    public List<RegisteredMail> getRegisteredMails() {
        return registeredMails;
    }

    /**
     * Adds an inhabitant to the geographical area.
     * @param inhabitant the inhabitant to add
     */
    public void addInhabitant(Inhabitant inhabitant) {
        this.inhabitants.add(inhabitant);
    }

    /**
     * Removes an inhabitant from the geographical area.
     * @param inhabitant the inhabitant to remove
     * @return true if the inhabitant was removed, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        return this.inhabitants.remove(inhabitant);
    }

    /**
     * Adds a mailman to the geographical area.
     * @param mailman the mailman to add
     * @return true if the mailman was added, false otherwise
     */
    public boolean addMailman(Mailman mailman) {
        if (!this.mailmen.contains(mailman)) {
            return this.mailmen.add(mailman);
        }
        return false;
    }

    /**
     * Removes a mailman from the geographical area.
     * @param mailman the mailman to remove
     * @param newMailman the new mailman to take over deliveries
     * @return true if the mailman was removed, false otherwise
     */
    public boolean removeMailman(Mailman mailman, Mailman newMailman) {
        if (this.mailmen.size() > 1 && this.mailmen.contains(mailman) && this.mailmen.contains(newMailman)) {
            for (RegisteredMail registeredMail : registeredMails) {
                if (registeredMail.getMailman() == mailman) {
                    registeredMail.setMailman(newMailman);
                }
            }
            return this.mailmen.remove(mailman);
        }
        return false;
    }

    /**
     * Adds a registered mail to the geographical area.
     * @param registeredMail the registered mail to add
     */
    public void addRegisteredMail(RegisteredMail registeredMail) {
        this.registeredMails.add(registeredMail);
    }

    /**
     * Removes a registered mail from the geographical area.
     * @param registeredMail the registered mail to remove
     * @return true if the registered mail was removed, false otherwise
     */
    public boolean removeRegisteredMail(RegisteredMail registeredMail) {
        return this.registeredMails.remove(registeredMail);
    }
}

/**
 * Represents an inhabitant.
 */
class Inhabitant {
    private String name;
    private GeographicalArea geographicalArea;

    /**
     * Constructs a new Inhabitant.
     */
    public Inhabitant() {}

    /**
     * Gets the name of the inhabitant.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the inhabitant.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the geographical area of the inhabitant.
     * @return the geographical area
     */
    public GeographicalArea getGeographicalArea() {
        return geographicalArea;
    }

    /**
     * Sets the geographical area of the inhabitant.
     * @param geographicalArea the geographical area to set
     */
    public void setGeographicalArea(GeographicalArea geographicalArea) {
        this.geographicalArea = geographicalArea;
    }
}

/**
 * Represents a mailman.
 */
class Mailman {
    private String name;
    private GeographicalArea geographicalArea;

    /**
     * Constructs a new Mailman.
     */
    public Mailman() {}

    /**
     * Gets the name of the mailman.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the mailman.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the geographical area of the mailman.
     * @return the geographical area
     */
    public GeographicalArea getGeographicalArea() {
        return geographicalArea;
    }

    /**
     * Sets the geographical area of the mailman.
     * @param geographicalArea the geographical area to set
     */
    public void setGeographicalArea(GeographicalArea geographicalArea) {
        this.geographicalArea = geographicalArea;
    }
}

/**
 * Represents registered mail.
 */
abstract class RegisteredMail {
    private Inhabitant addressee;
    private Mailman mailman;

    /**
     * Constructs a new RegisteredMail.
     */
    public RegisteredMail() {}

    /**
     * Gets the addressee of the registered mail.
     * @return the addressee
     */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /**
     * Sets the addressee of the registered mail.
     * @param addressee the addressee to set
     */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }

    /**
     * Gets the mailman of the registered mail.
     * @return the mailman
     */
    public Mailman getMailman() {
        return mailman;
    }

    /**
     * Sets the mailman of the registered mail.
     * @param mailman the mailman to set
     */
    public void setMailman(Mailman mailman) {
        this.mailman = mailman;
    }

    /**
     * Assigns a mailman to the registered mail.
     * @param mailman the mailman to assign
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignMailman(Mailman mailman) {
        if (this.mailman == null && mailman.getGeographicalArea() == this.addressee.getGeographicalArea()) {
            this.mailman = mailman;
            return true;
        }
        return false;
    }
}

/**
 * Represents a letter.
 */
class Letter extends RegisteredMail {
    private String content;

    /**
     * Constructs a new Letter.
     */
    public Letter() {}

    /**
     * Gets the content of the letter.
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the letter.
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
}

/**
 * Represents a parcel.
 */
class Parcel extends RegisteredMail {
    private double weight;

    /**
     * Constructs a new Parcel.
     */
    public Parcel() {}

    /**
     * Gets the weight of the parcel.
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the parcel.
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
}

/**
 * Manages mail deliveries.
 */
class MailDeliveryManager {
    private List<GeographicalArea> geographicalAreas;

    /**
     * Constructs a new MailDeliveryManager.
     */
    public MailDeliveryManager() {
        this.geographicalAreas = new ArrayList<>();
    }

    /**
     * Gets the geographical areas.
     * @return the geographical areas
     */
    public List<GeographicalArea> getGeographicalAreas() {
        return geographicalAreas;
    }

    /**
     * Adds a geographical area.
     * @param geographicalArea the geographical area to add
     */
    public void addGeographicalArea(GeographicalArea geographicalArea) {
        this.geographicalAreas.add(geographicalArea);
    }

    /**
     * Retrieves all registered mail deliveries for a given geographical area.
     * @param geographicalArea the geographical area
     * @return a list of registered mail deliveries
     */
    public List<RegisteredMail> getRegisteredMailDeliveries(GeographicalArea geographicalArea) {
        return geographicalArea.getRegisteredMails();
    }

    /**
     * Assigns a mailman to a registered mail item.
     * @param registeredMail the registered mail item
     * @param mailman the mailman to assign
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignMailmanToRegisteredMail(RegisteredMail registeredMail, Mailman mailman) {
        return registeredMail.assignMailman(mailman);
    }

    /**
     * Adds an inhabitant to a geographical area.
     * @param inhabitant the inhabitant to add
     * @param geographicalArea the geographical area
     * @return true if the inhabitant was added, false otherwise
     */
    public boolean addInhabitantToGeographicalArea(Inhabitant inhabitant, GeographicalArea geographicalArea) {
        if (geographicalArea.getInhabitants().contains(inhabitant)) {
            return false;
        }
        geographicalArea.addInhabitant(inhabitant);
        inhabitant.setGeographicalArea(geographicalArea);
        return true;
    }

    /**
     * Removes an inhabitant from a geographical area.
     * @param inhabitant the inhabitant to remove
     * @return true if the inhabitant was removed, false otherwise
     */
    public boolean removeInhabitantFromGeographicalArea(Inhabitant inhabitant) {
        GeographicalArea geographicalArea = inhabitant.getGeographicalArea();
        if (geographicalArea.removeInhabitant(inhabitant)) {
            for (RegisteredMail registeredMail : new ArrayList<>(geographicalArea.getRegisteredMails())) {
                if (registeredMail.getAddressee() == inhabitant && registeredMail.getMailman() != null) {
                    geographicalArea.removeRegisteredMail(registeredMail);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Adds a mailman to a geographical area.
     * @param mailman the mailman to add
     * @param geographicalArea the geographical area
     * @return true if the mailman was added, false otherwise
     */
    public boolean addMailmanToGeographicalArea(Mailman mailman, GeographicalArea geographicalArea) {
        mailman.setGeographicalArea(geographicalArea);
        return geographicalArea.addMailman(mailman);
    }

    /**
     * Removes a mailman from a geographical area.
     * @param mailman the mailman to remove
     * @param newMailman the new mailman to take over deliveries
     * @return true if the mailman was removed, false otherwise
     */
    public boolean removeMailmanFromGeographicalArea(Mailman mailman, Mailman newMailman) {
        return mailman.getGeographicalArea().removeMailman(mailman, newMailman);
    }

    /**
     * Lists all registered mail items directed to a specified inhabitant.
     * @param inhabitant the inhabitant
     * @return a list of registered mail items, or null if none exist
     */
    public List<RegisteredMail> listRegisteredMailForInhabitant(Inhabitant inhabitant) {
        GeographicalArea geographicalArea = inhabitant.getGeographicalArea();
        List<RegisteredMail> registeredMails = new ArrayList<>();
        for (RegisteredMail registeredMail : geographicalArea.getRegisteredMails()) {
            if (registeredMail.getAddressee() == inhabitant) {
                registeredMails.add(registeredMail);
            }
        }
        return registeredMails.isEmpty() ? null : registeredMails;
    }
}