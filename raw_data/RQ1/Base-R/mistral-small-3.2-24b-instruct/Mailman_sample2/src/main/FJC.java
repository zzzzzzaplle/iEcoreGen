import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a geographical area.
 */
class GeographicalArea {
    private String areaId;
    private List<Inhabitant> inhabitants;
    private List<Mailman> mailmen;

    /**
     * Constructs an empty GeographicalArea.
     */
    public GeographicalArea() {
        this.inhabitants = new ArrayList<>();
        this.mailmen = new ArrayList<>();
    }

    /**
     * Constructs a GeographicalArea with the given areaId.
     * @param areaId the ID of the geographical area
     */
    public GeographicalArea(String areaId) {
        this();
        this.areaId = areaId;
    }

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
    private GeographicalArea area;

    /**
     * Constructs an empty Inhabitant.
     */
    public Inhabitant() {
    }

    /**
     * Constructs an Inhabitant with the given inhabitantId and name.
     * @param inhabitantId the ID of the inhabitant
     * @param name the name of the inhabitant
     */
    public Inhabitant(String inhabitantId, String name) {
        this.inhabitantId = inhabitantId;
        this.name = name;
    }

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

    public GeographicalArea getArea() {
        return area;
    }

    public void setArea(GeographicalArea area) {
        this.area = area;
    }
}

/**
 * Represents a mailman responsible for delivering mail in a geographical area.
 */
class Mailman {
    private String mailmanId;
    private String name;
    private GeographicalArea area;

    /**
     * Constructs an empty Mailman.
     */
    public Mailman() {
    }

    /**
     * Constructs a Mailman with the given mailmanId and name.
     * @param mailmanId the ID of the mailman
     * @param name the name of the mailman
     */
    public Mailman(String mailmanId, String name) {
        this.mailmanId = mailmanId;
        this.name = name;
    }

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

    public GeographicalArea getArea() {
        return area;
    }

    public void setArea(GeographicalArea area) {
        this.area = area;
    }
}

/**
 * Represents a registered mail item.
 */
abstract class RegisteredMail {
    private String mailId;
    private Inhabitant addressee;
    private Mailman assignedMailman;

    /**
     * Constructs an empty RegisteredMail.
     */
    public RegisteredMail() {
    }

    /**
     * Constructs a RegisteredMail with the given mailId and addressee.
     * @param mailId the ID of the mail item
     * @param addressee the addressee of the mail item
     */
    public RegisteredMail(String mailId, Inhabitant addressee) {
        this.mailId = mailId;
        this.addressee = addressee;
    }

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

    public Mailman getAssignedMailman() {
        return assignedMailman;
    }

    public void setAssignedMailman(Mailman assignedMailman) {
        this.assignedMailman = assignedMailman;
    }
}

/**
 * Represents a letter, a type of registered mail.
 */
class Letter extends RegisteredMail {
    /**
     * Constructs an empty Letter.
     */
    public Letter() {
    }

    /**
     * Constructs a Letter with the given mailId and addressee.
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
     * Constructs an empty Parcel.
     */
    public Parcel() {
    }

    /**
     * Constructs a Parcel with the given mailId and addressee.
     * @param mailId the ID of the parcel
     * @param addressee the addressee of the parcel
     */
    public Parcel(String mailId, Inhabitant addressee) {
        super(mailId, addressee);
    }
}

/**
 * Manages the delivery of registered mail in geographical areas.
 */
class MailDeliveryManager {
    private Map<GeographicalArea, List<RegisteredMail>> deliveries;

    /**
     * Constructs an empty MailDeliveryManager.
     */
    public MailDeliveryManager() {
        this.deliveries = new HashMap<>();
    }

    /**
     * Assigns a specific mailman to deliver a registered inhabitant's mail item.
     * @param mailItem the mail item to be assigned
     * @param mailman the mailman to assign
     * @return true if the assignment is successful, false otherwise
     * @throws IllegalArgumentException if the mailman and inhabitant do not belong to the same geographical area
     * @throws IllegalStateException if the mail item is already assigned to a mailman
     */
    public boolean assignMailman(RegisteredMail mailItem, Mailman mailman) {
        if (mailItem.getAssignedMailman() != null) {
            throw new IllegalStateException("Mail item is already assigned to a mailman.");
        }
        if (!Objects.equals(mailItem.getAddressee().getArea(), mailman.getArea())) {
            throw new IllegalArgumentException("Mailman and inhabitant do not belong to the same geographical area.");
        }
        mailItem.setAssignedMailman(mailman);
        deliveries.computeIfAbsent(mailman.getArea(), k -> new ArrayList<>()).add(mailItem);
        return true;
    }

    /**
     * Retrieves all registered mail deliveries for a given geographical area, along with the responsible mailman and the addressees.
     * @param area the geographical area
     * @return a list of registered mail deliveries, or an empty list if there are no deliveries
     */
    public List<RegisteredMail> getDeliveriesForArea(GeographicalArea area) {
        return deliveries.getOrDefault(area, new ArrayList<>());
    }

    /**
     * Adds a new inhabitant to a geographical area.
     * @param inhabitant the inhabitant to add
     * @param area the geographical area
     * @return true if the operation is successful, false otherwise
     */
    public boolean addInhabitant(Inhabitant inhabitant, GeographicalArea area) {
        if (inhabitant.getArea() != null) {
            return false;
        }
        inhabitant.setArea(area);
        area.getInhabitants().add(inhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from the geographical area.
     * @param inhabitant the inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitant.getArea() == null) {
            return false;
        }
        GeographicalArea area = inhabitant.getArea();
        area.getInhabitants().remove(inhabitant);
        inhabitant.setArea(null);
        // Delete any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman
        List<RegisteredMail> mailItemsToRemove = new ArrayList<>();
        for (List<RegisteredMail> mailItems : deliveries.values()) {
            for (RegisteredMail mailItem : mailItems) {
                if (mailItem.getAddressee().equals(inhabitant) && mailItem.getAssignedMailman() != null) {
                    mailItemsToRemove.add(mailItem);
                }
            }
        }
        for (RegisteredMail mailItem : mailItemsToRemove) {
            mailItem.setAssignedMailman(null);
        }
        return true;
    }

    /**
     * Adds a mailman to a geographical area.
     * @param mailman the mailman to add
     * @param area the geographical area
     * @return true if the operation is successful, false otherwise
     */
    public boolean addMailman(Mailman mailman, GeographicalArea area) {
        if (mailman.getArea() != null) {
            return false;
        }
        mailman.setArea(area);
        area.getMailmen().add(mailman);
        return true;
    }

    /**
     * Removes a mailman from a geographical area.
     * @param mailman the mailman to remove
     * @param replacementMailman the mailman to take over the deliveries
     * @return true if the operation is successful, false otherwise
     * @throws IllegalArgumentException if there is only one mailman in the area or the replacement mailman does not exist
     */
    public boolean removeMailman(Mailman mailman, Mailman replacementMailman) {
        if (mailman.getArea() == null) {
            return false;
        }
        GeographicalArea area = mailman.getArea();
        if (area.getMailmen().size() <= 1) {
            throw new IllegalArgumentException("There must be at least one mailman in the area.");
        }
        if (!area.getMailmen().contains(replacementMailman)) {
            throw new IllegalArgumentException("Replacement mailman does not exist in the area.");
        }
        // Reassign all mail items to the replacement mailman
        for (List<RegisteredMail> mailItems : deliveries.values()) {
            for (RegisteredMail mailItem : mailItems) {
                if (mailItem.getAssignedMailman().equals(mailman)) {
                    mailItem.setAssignedMailman(replacementMailman);
                }
            }
        }
        area.getMailmen().remove(mailman);
        mailman.setArea(null);
        return true;
    }

    /**
     * Lists all registered mail items directed to a specified inhabitant.
     * @param inhabitant the inhabitant
     * @return a list of registered mail items, or null if none exist
     */
    public List<RegisteredMail> listMailItemsForInhabitant(Inhabitant inhabitant) {
        List<RegisteredMail> mailItems = new ArrayList<>();
        for (List<RegisteredMail> deliveries : deliveries.values()) {
            for (RegisteredMail mailItem : deliveries) {
                if (mailItem.getAddressee().equals(inhabitant)) {
                    mailItems.add(mailItem);
                }
            }
        }
        return mailItems.isEmpty() ? null : mailItems;
    }
}