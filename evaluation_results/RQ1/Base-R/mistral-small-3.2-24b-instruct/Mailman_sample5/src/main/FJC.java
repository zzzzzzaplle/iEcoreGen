import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a geographical area.
 */
class GeographicalArea {
    private String areaId;
    private List<Inhabitant> inhabitants;
    private List<Mailman> mailmen;

    /**
     * Constructor for GeographicalArea.
     */
    public GeographicalArea() {
        this.inhabitants = new ArrayList<>();
        this.mailmen = new ArrayList<>();
    }

    /**
     * @return the areaId
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * @param areaId the areaId to set
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    /**
     * @return the inhabitants
     */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /**
     * @param inhabitants the inhabitants to set
     */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /**
     * @return the mailmen
     */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /**
     * @param mailmen the mailmen to set
     */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /**
     * Adds an inhabitant to the geographical area.
     * @param inhabitant the inhabitant to add
     * @return true if the operation is successful, false otherwise
     */
    public boolean addInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null || inhabitants.contains(inhabitant)) {
            return false;
        }
        inhabitants.add(inhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from the geographical area.
     * @param inhabitant the inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null || !inhabitants.contains(inhabitant)) {
            return false;
        }
        inhabitants.remove(inhabitant);
        return true;
    }

    /**
     * Adds a mailman to the geographical area.
     * @param mailman the mailman to add
     * @return true if the operation is successful, false otherwise
     */
    public boolean addMailman(Mailman mailman) {
        if (mailman == null || mailmen.contains(mailman)) {
            return false;
        }
        mailmen.add(mailman);
        return true;
    }

    /**
     * Removes a mailman from the geographical area.
     * @param mailmanToRemove the mailman to remove
     * @param newMailman the new mailman to take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman) {
        if (mailmanToRemove == null || !mailmen.contains(mailmanToRemove) ||
            newMailman == null || !mailmen.contains(newMailman) || mailmen.size() <= 1) {
            return false;
        }
        for (RegisteredMail mail : mailmanToRemove.getDeliveries()) {
            if (!mail.assignMailman(newMailman)) {
                return false;
            }
        }
        mailmen.remove(mailmanToRemove);
        return true;
    }

    /**
     * Retrieves all registered mail deliveries for the geographical area.
     * @return a list of registered mail deliveries
     */
    public List<RegisteredMail> getDeliveries() {
        List<RegisteredMail> deliveries = new ArrayList<>();
        for (Mailman mailman : mailmen) {
            deliveries.addAll(mailman.getDeliveries());
        }
        return deliveries;
    }
}

/**
 * Represents an inhabitant.
 */
class Inhabitant {
    private String inhabitantId;
    private String name;
    private GeographicalArea geographicalArea;

    /**
     * Constructor for Inhabitant.
     */
    public Inhabitant() {
    }

    /**
     * @return the inhabitantId
     */
    public String getInhabitantId() {
        return inhabitantId;
    }

    /**
     * @param inhabitantId the inhabitantId to set
     */
    public void setInhabitantId(String inhabitantId) {
        this.inhabitantId = inhabitantId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the geographicalArea
     */
    public GeographicalArea getGeographicalArea() {
        return geographicalArea;
    }

    /**
     * @param geographicalArea the geographicalArea to set
     */
    public void setGeographicalArea(GeographicalArea geographicalArea) {
        this.geographicalArea = geographicalArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inhabitant that = (Inhabitant) o;
        return Objects.equals(inhabitantId, that.inhabitantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inhabitantId);
    }
}

/**
 * Represents a mailman.
 */
class Mailman {
    private String mailmanId;
    private String name;
    private List<RegisteredMail> deliveries;
    private GeographicalArea geographicalArea;

    /**
     * Constructor for Mailman.
     */
    public Mailman() {
        this.deliveries = new ArrayList<>();
    }

    /**
     * @return the mailmanId
     */
    public String getMailmanId() {
        return mailmanId;
    }

    /**
     * @param mailmanId the mailmanId to set
     */
    public void setMailmanId(String mailmanId) {
        this.mailmanId = mailmanId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the deliveries
     */
    public List<RegisteredMail> getDeliveries() {
        return deliveries;
    }

    /**
     * @param deliveries the deliveries to set
     */
    public void setDeliveries(List<RegisteredMail> deliveries) {
        this.deliveries = deliveries;
    }

    /**
     * @return the geographicalArea
     */
    public GeographicalArea getGeographicalArea() {
        return geographicalArea;
    }

    /**
     * @param geographicalArea the geographicalArea to set
     */
    public void setGeographicalArea(GeographicalArea geographicalArea) {
        this.geographicalArea = geographicalArea;
    }

    /**
     * Assigns a registered mail to the mailman.
     * @param mail the registered mail to assign
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignMail(RegisteredMail mail) {
        if (mail == null || deliveries.contains(mail) || mail.getMailman() != null) {
            return false;
        }
        deliveries.add(mail);
        mail.setMailman(this);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mailman mailman = (Mailman) o;
        return Objects.equals(mailmanId, mailman.mailmanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailmanId);
    }
}

/**
 * Represents a registered mail item.
 */
abstract class RegisteredMail {
    private String mailId;
    private Inhabitant addressee;
    private Mailman mailman;

    /**
     * Constructor for RegisteredMail.
     */
    public RegisteredMail() {
    }

    /**
     * @return the mailId
     */
    public String getMailId() {
        return mailId;
    }

    /**
     * @param mailId the mailId to set
     */
    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    /**
     * @return the addressee
     */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /**
     * @param addressee the addressee to set
     */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }

    /**
     * @return the mailman
     */
    public Mailman getMailman() {
        return mailman;
    }

    /**
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
        if (mailman == null || this.mailman != null || !mailman.getGeographicalArea().equals(this.addressee.getGeographicalArea())) {
            return false;
        }
        this.mailman = mailman;
        mailman.getDeliveries().add(this);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisteredMail that = (RegisteredMail) o;
        return Objects.equals(mailId, that.mailId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mailId);
    }
}

/**
 * Represents a letter.
 */
class Letter extends RegisteredMail {
    private String content;

    /**
     * Constructor for Letter.
     */
    public Letter() {
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
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
    private String description;

    /**
     * Constructor for Parcel.
     */
    public Parcel() {
    }

    /**
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}

/**
 * Utility class for managing mail and inhabitants.
 */
class MailManagementSystem {
    private Map<String, GeographicalArea> geographicalAreas;

    /**
     * Constructor for MailManagementSystem.
     */
    public MailManagementSystem() {
        this.geographicalAreas = new HashMap<>();
    }

    /**
     * Adds a geographical area to the system.
     * @param area the geographical area to add
     */
    public void addGeographicalArea(GeographicalArea area) {
        geographicalAreas.put(area.getAreaId(), area);
    }

    /**
     * Retrieves all registered mail items for a given inhabitant.
     * @param inhabitant the inhabitant
     * @return a list of registered mail items
     */
    public List<RegisteredMail> getMailForInhabitant(Inhabitant inhabitant) {
        List<RegisteredMail> mailItems = new ArrayList<>();
        for (GeographicalArea area : geographicalAreas.values()) {
            for (Mailman mailman : area.getMailmen()) {
                for (RegisteredMail mail : mailman.getDeliveries()) {
                    if (mail.getAddressee().equals(inhabitant)) {
                        mailItems.add(mail);
                    }
                }
            }
        }
        return mailItems.isEmpty() ? null : mailItems;
    }

    /**
     * Retrieves all registered mail deliveries for a given geographical area.
     * @param areaId the ID of the geographical area
     * @return a list of registered mail deliveries
     */
    public List<RegisteredMail> getDeliveriesForArea(String areaId) {
        GeographicalArea area = geographicalAreas.get(areaId);
        return area != null ? area.getDeliveries() : new ArrayList<>();
    }

    /**
     * Adds an inhabitant to a geographical area.
     * @param areaId the ID of the geographical area
     * @param inhabitant the inhabitant to add
     * @return true if the operation is successful, false otherwise
     */
    public boolean addInhabitantToArea(String areaId, Inhabitant inhabitant) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null) {
            return false;
        }
        return area.addInhabitant(inhabitant);
    }

    /**
     * Removes an inhabitant from a geographical area.
     * @param areaId the ID of the geographical area
     * @param inhabitant the inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitantFromArea(String areaId, Inhabitant inhabitant) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null) {
            return false;
        }
        return area.removeInhabitant(inhabitant);
    }

    /**
     * Adds a mailman to a geographical area.
     * @param areaId the ID of the geographical area
     * @param mailman the mailman to add
     * @return true if the operation is successful, false otherwise
     */
    public boolean addMailmanToArea(String areaId, Mailman mailman) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null) {
            return false;
        }
        return area.addMailman(mailman);
    }

    /**
     * Removes a mailman from a geographical area.
     * @param areaId the ID of the geographical area
     * @param mailmanToRemove the mailman to remove
     * @param newMailman the new mailman to take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailmanFromArea(String areaId, Mailman mailmanToRemove, Mailman newMailman) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null) {
            return false;
        }
        return area.removeMailman(mailmanToRemove, newMailman);
    }
}