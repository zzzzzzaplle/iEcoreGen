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
     * Constructs a GeographicalArea object.
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
     * Sets the inhabitants of the geographical area.
     * @param inhabitants the inhabitants to set
     */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /**
     * Gets the mailmen of the geographical area.
     * @return the mailmen
     */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /**
     * Sets the mailmen of the geographical area.
     * @param mailmen the mailmen to set
     */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /**
     * Gets the registered mails of the geographical area.
     * @return the registeredMails
     */
    public List<RegisteredMail> getRegisteredMails() {
        return registeredMails;
    }

    /**
     * Sets the registered mails of the geographical area.
     * @param registeredMails the registeredMails to set
     */
    public void setRegisteredMails(List<RegisteredMail> registeredMails) {
        this.registeredMails = registeredMails;
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
     * @return true if the inhabitant is removed, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        return this.inhabitants.remove(inhabitant);
    }

    /**
     * Adds a mailman to the geographical area.
     * @param mailman the mailman to add
     */
    public void addMailman(Mailman mailman) {
        this.mailmen.add(mailman);
    }

    /**
     * Removes a mailman from the geographical area.
     * @param mailman the mailman to remove
     * @return true if the mailman is removed, false otherwise
     */
    public boolean removeMailman(Mailman mailman) {
        return this.mailmen.remove(mailman);
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
     * @return true if the registered mail is removed, false otherwise
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
     * Constructs an Inhabitant object.
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
     * @return the geographicalArea
     */
    public GeographicalArea getGeographicalArea() {
        return geographicalArea;
    }

    /**
     * Sets the geographical area of the inhabitant.
     * @param geographicalArea the geographicalArea to set
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
     * Constructs a Mailman object.
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
     * @return the geographicalArea
     */
    public GeographicalArea getGeographicalArea() {
        return geographicalArea;
    }

    /**
     * Sets the geographical area of the mailman.
     * @param geographicalArea the geographicalArea to set
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
     * Constructs a RegisteredMail object.
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
        if (this.mailman != null) {
            return false;
        }
        this.mailman = mailman;
        return true;
    }
}

/**
 * Represents a letter.
 */
class Letter extends RegisteredMail {
    private String content;

    /**
     * Constructs a Letter object.
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
     * Constructs a Parcel object.
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
     * Constructs a MailDeliveryManager object.
     */
    public MailDeliveryManager() {
        this.geographicalAreas = new ArrayList<>();
    }

    /**
     * Gets the geographical areas.
     * @return the geographicalAreas
     */
    public List<GeographicalArea> getGeographicalAreas() {
        return geographicalAreas;
    }

    /**
     * Sets the geographical areas.
     * @param geographicalAreas the geographicalAreas to set
     */
    public void setGeographicalAreas(List<GeographicalArea> geographicalAreas) {
        this.geographicalAreas = geographicalAreas;
    }

    /**
     * Assigns a mailman to deliver a registered mail item.
     * @param registeredMail the registered mail item
     * @param mailman the mailman to assign
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignMailman(RegisteredMail registeredMail, Mailman mailman) {
        if (registeredMail.getAddressee().getGeographicalArea() != mailman.getGeographicalArea()) {
            return false;
        }
        return registeredMail.assignMailman(mailman);
    }

    /**
     * Retrieves all registered mail deliveries for a given geographical area.
     * @param geographicalArea the geographical area
     * @return a list of registered mail deliveries
     */
    public List<RegisteredMail> getRegisteredMailDeliveries(GeographicalArea geographicalArea) {
        List<RegisteredMail> deliveries = new ArrayList<>();
        for (RegisteredMail registeredMail : geographicalArea.getRegisteredMails()) {
            if (registeredMail.getMailman() != null) {
                deliveries.add(registeredMail);
            }
        }
        return deliveries;
    }

    /**
     * Manages inhabitants.
     * @param inhabitant the inhabitant to manage
     * @param geographicalArea the geographical area
     * @param isAdd true to add, false to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean manageInhabitant(Inhabitant inhabitant, GeographicalArea geographicalArea, boolean isAdd) {
        if (isAdd) {
            geographicalArea.addInhabitant(inhabitant);
            inhabitant.setGeographicalArea(geographicalArea);
            return true;
        } else {
            for (RegisteredMail registeredMail : new ArrayList<>(geographicalArea.getRegisteredMails())) {
                if (registeredMail.getAddressee() == inhabitant && registeredMail.getMailman() != null) {
                    geographicalArea.removeRegisteredMail(registeredMail);
                }
            }
            return geographicalArea.removeInhabitant(inhabitant);
        }
    }

    /**
     * Manages mailmen.
     * @param mailman the mailman to manage
     * @param geographicalArea the geographical area
     * @param newMailman the new mailman to take over deliveries
     * @param isAdd true to add, false to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean manageMailman(Mailman mailman, GeographicalArea geographicalArea, Mailman newMailman, boolean isAdd) {
        if (isAdd) {
            if (geographicalArea.getMailmen().contains(mailman)) {
                return false;
            }
            geographicalArea.addMailman(mailman);
            mailman.setGeographicalArea(geographicalArea);
            return true;
        } else {
            if (geographicalArea.getMailmen().size() == 1) {
                return false;
            }
            for (RegisteredMail registeredMail : geographicalArea.getRegisteredMails()) {
                if (registeredMail.getMailman() == mailman) {
                    if (!registeredMail.assignMailman(newMailman)) {
                        return false;
                    }
                }
            }
            return geographicalArea.removeMailman(mailman);
        }
    }

    /**
     * Lists all registered mail items directed to a specified inhabitant.
     * @param inhabitant the inhabitant
     * @return a list of registered mail items
     */
    public List<RegisteredMail> listRegisteredMailItems(Inhabitant inhabitant) {
        List<RegisteredMail> mailItems = new ArrayList<>();
        for (RegisteredMail registeredMail : inhabitant.getGeographicalArea().getRegisteredMails()) {
            if (registeredMail.getAddressee() == inhabitant) {
                mailItems.add(registeredMail);
            }
        }
        return mailItems.isEmpty() ? null : mailItems;
    }
}