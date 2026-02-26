import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a geographical area.
 */
class GeographicalArea {
    private String areaId;
    private List<Inhabitant> inhabitants;
    private List<Mailman> mailmen;

    /**
     * Constructs a new GeographicalArea with the specified area ID.
     * @param areaId the ID of the geographical area
     */
    public GeographicalArea(String areaId) {
        this.areaId = areaId;
        this.inhabitants = new ArrayList<>();
        this.mailmen = new ArrayList<>();
    }

    /**
     * Adds a new inhabitant to the geographical area.
     * @param inhabitant the inhabitant to add
     * @return true if the inhabitant was added successfully, false otherwise
     */
    public boolean addInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null || inhabitants.contains(inhabitant)) {
            return false;
        }
        return inhabitants.add(inhabitant);
    }

    /**
     * Removes an inhabitant from the geographical area.
     * @param inhabitant the inhabitant to remove
     * @return true if the inhabitant was removed successfully, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null || !inhabitants.contains(inhabitant)) {
            return false;
        }
        // Remove any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman
        for (Mailman mailman : mailmen) {
            mailman.removeAssignedMail(inhabitant);
        }
        return inhabitants.remove(inhabitant);
    }

    /**
     * Adds a mailman to the geographical area if they are not already assigned.
     * @param mailman the mailman to add
     * @return true if the mailman was added successfully, false otherwise
     */
    public boolean addMailman(Mailman mailman) {
        if (mailman == null || mailmen.contains(mailman)) {
            return false;
        }
        return mailmen.add(mailman);
    }

    /**
     * Removes a mailman from the geographical area.
     * @param mailman the mailman to remove
     * @param replacementMailman the mailman to take over deliveries
     * @return true if the mailman was removed successfully, false otherwise
     */
    public boolean removeMailman(Mailman mailman, Mailman replacementMailman) {
        if (mailman == null || !mailmen.contains(mailman) || replacementMailman == null || !mailmen.contains(replacementMailman) || mailmen.size() <= 1) {
            return false;
        }
        // Reassign all mail from the mailman being removed to the replacement mailman
        for (RegisteredMail mail : mailman.getAssignedMail()) {
            if (!replacementMailman.assignMail(mail)) {
                return false;
            }
        }
        return mailmen.remove(mailman);
    }

    /**
     * Retrieves all registered mail deliveries for the geographical area.
     * @return a list of registered mail deliveries along with the responsible mailman and the addressees
     */
    public List<Map.Entry<Mailman, List<RegisteredMail>>> getRegisteredMailDeliveries() {
        List<Map.Entry<Mailman, List<RegisteredMail>>> deliveries = new ArrayList<>();
        for (Mailman mailman : mailmen) {
            for (RegisteredMail mail : mailman.getAssignedMail()) {
                deliveries.add(new AbstractMap.SimpleEntry<>(mailman, new ArrayList<>(List.of(mail))));
            }
        }
        return deliveries;
    }

    // Getters and setters
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    public List<Mailman> getMailmen() {
        return mailmen;
    }

    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }
}

/**
 * Represents an inhabitant in a geographical area.
 */
class Inhabitant {
    private String inhabitantId;
    private String name;

    /**
     * Constructs a new Inhabitant with the specified ID and name.
     * @param inhabitantId the ID of the inhabitant
     * @param name the name of the inhabitant
     */
    public Inhabitant(String inhabitantId, String name) {
        this.inhabitantId = inhabitantId;
        this.name = name;
    }

    // Getters and setters
    public String getInhabitantId() {
        return inhabitantId;
    }

    public void setInhabitantId(String inhabitantId) {
        this.inhabitantId = inhabitantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents a mailman responsible for delivering registered mail.
 */
class Mailman {
    private String mailmanId;
    private String name;
    private List<RegisteredMail> assignedMail;

    /**
     * Constructs a new Mailman with the specified ID and name.
     * @param mailmanId the ID of the mailman
     * @param name the name of the mailman
     */
    public Mailman(String mailmanId, String name) {
        this.mailmanId = mailmanId;
        this.name = name;
        this.assignedMail = new ArrayList<>();
    }

    /**
     * Assigns a registered mail item to the mailman.
     * @param mail the registered mail item to assign
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignMail(RegisteredMail mail) {
        if (mail == null || assignedMail.contains(mail)) {
            return false;
        }
        return assignedMail.add(mail);
    }

    /**
     * Removes a registered mail item from the mailman's assignments.
     * @param mail the registered mail item to remove
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeAssignedMail(RegisteredMail mail) {
        if (mail == null || !assignedMail.contains(mail)) {
            return false;
        }
        return assignedMail.remove(mail);
    }

    /**
     * Removes all registered mail items assigned to the specified inhabitant.
     * @param inhabitant the inhabitant whose mail items should be removed
     */
    public void removeAssignedMail(Inhabitant inhabitant) {
        assignedMail.removeIf(mail -> mail.getAddressee().equals(inhabitant));
    }

    // Getters and setters
    public String getMailmanId() {
        return mailmanId;
    }

    public void setMailmanId(String mailmanId) {
        this.mailmanId = mailmanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RegisteredMail> getAssignedMail() {
        return assignedMail;
    }

    public void setAssignedMail(List<RegisteredMail> assignedMail) {
        this.assignedMail = assignedMail;
    }
}

/**
 * Represents a registered mail item.
 */
abstract class RegisteredMail {
    private String mailId;
    private Inhabitant addressee;

    /**
     * Constructs a new RegisteredMail with the specified ID and addressee.
     * @param mailId the ID of the registered mail item
     * @param addressee the addressee of the registered mail item
     */
    public RegisteredMail(String mailId, Inhabitant addressee) {
        this.mailId = mailId;
        this.addressee = addressee;
    }

    // Getters and setters
    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public Inhabitant getAddressee() {
        return addressee;
    }

    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}

/**
 * Represents a letter, a type of registered mail.
 */
class Letter extends RegisteredMail {
    /**
     * Constructs a new Letter with the specified ID and addressee.
     * @param mailId the ID of the letter
     * @param addressee the addressee of the letter
     */
    public Letter(String mailId, Inhabitant addressee) {
        super(mailId, addressee);
    }
}

/**
 * Represents a parcel, a type of registered mail.
 */
class Parcel extends RegisteredMail {
    /**
     * Constructs a new Parcel with the specified ID and addressee.
     * @param mailId the ID of the parcel
     * @param addressee the addressee of the parcel
     */
    public Parcel(String mailId, Inhabitant addressee) {
        super(mailId, addressee);
    }
}

/**
 * Utility class to manage geographical areas, inhabitants, and mailmen.
 */
class MailDeliveryManager {
    private Map<String, GeographicalArea> geographicalAreas;

    /**
     * Constructs a new MailDeliveryManager.
     */
    public MailDeliveryManager() {
        this.geographicalAreas = new HashMap<>();
    }

    /**
     * Assigns a specific mailman to deliver a registered inhabitant's mail item.
     * @param areaId the ID of the geographical area
     * @param mailman the mailman to assign
     * @param mail the registered mail item to assign
     * @return true if the assignment is successful, false otherwise
     */
    public boolean assignMailmanToMail(String areaId, Mailman mailman, RegisteredMail mail) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null || !area.getInhabitants().contains(mail.getAddressee()) || !area.getMailmen().contains(mailman) || mailman.getAssignedMail().contains(mail)) {
            return false;
        }
        return mailman.assignMail(mail);
    }

    /**
     * Retrieves all registered mail deliveries for a given geographical area.
     * @param areaId the ID of the geographical area
     * @return a list of registered mail deliveries along with the responsible mailman and the addressees
     */
    public List<Map.Entry<Mailman, List<RegisteredMail>>> getRegisteredMailDeliveries(String areaId) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null) {
            return new ArrayList<>();
        }
        return area.getRegisteredMailDeliveries();
    }

    /**
     * Adds a new inhabitant to a geographical area.
     * @param areaId the ID of the geographical area
     * @param inhabitant the inhabitant to add
     * @return true if the operation is successful, false otherwise
     */
    public boolean addInhabitant(String areaId, Inhabitant inhabitant) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null) {
            return false;
        }
        return area.addInhabitant(inhabitant);
    }

    /**
     * Removes an inhabitant from the geographical area.
     * @param areaId the ID of the geographical area
     * @param inhabitant the inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(String areaId, Inhabitant inhabitant) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null) {
            return false;
        }
        return area.removeInhabitant(inhabitant);
    }

    /**
     * Adds a mailman to a geographical area if they are not already assigned.
     * @param areaId the ID of the geographical area
     * @param mailman the mailman to add
     * @return true if the operation is successful, false otherwise
     */
    public boolean addMailman(String areaId, Mailman mailman) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null) {
            return false;
        }
        return area.addMailman(mailman);
    }

    /**
     * Removes a mailman from the geographical area.
     * @param areaId the ID of the geographical area
     * @param mailman the mailman to remove
     * @param replacementMailman the mailman to take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(String areaId, Mailman mailman, Mailman replacementMailman) {
        GeographicalArea area = geographicalAreas.get(areaId);
        if (area == null) {
            return false;
        }
        return area.removeMailman(mailman, replacementMailman);
    }

    /**
     * Lists all registered mail items directed to a specified inhabitant.
     * @param inhabitant the inhabitant to list mail items for
     * @return a list of registered mail items, or null if none exist
     */
    public List<RegisteredMail> listMailItemsForInhabitant(Inhabitant inhabitant) {
        List<RegisteredMail> mailItems = new ArrayList<>();
        for (GeographicalArea area : geographicalAreas.values()) {
            for (Mailman mailman : area.getMailmen()) {
                for (RegisteredMail mail : mailman.getAssignedMail()) {
                    if (mail.getAddressee().equals(inhabitant)) {
                        mailItems.add(mail);
                    }
                }
            }
        }
        return mailItems.isEmpty() ? null : mailItems;
    }

    // Getters and setters
    public Map<String, GeographicalArea> getGeographicalAreas() {
        return geographicalAreas;
    }

    public void setGeographicalAreas(Map<String, GeographicalArea> geographicalAreas) {
        this.geographicalAreas = geographicalAreas;
    }
}