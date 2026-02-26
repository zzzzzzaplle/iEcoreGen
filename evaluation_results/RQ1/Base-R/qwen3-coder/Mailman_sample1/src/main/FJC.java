import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a geographical area in the mail delivery system.
 */
class Area {
    private String name;
    private List<Inhabitant> inhabitants;
    private List<Mailman> mailmen;
    private List<RegisteredMail> registeredMails;

    /**
     * Constructs a new Area with default values.
     */
    public Area() {
        this.inhabitants = new ArrayList<>();
        this.mailmen = new ArrayList<>();
        this.registeredMails = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<RegisteredMail> getRegisteredMails() {
        return registeredMails;
    }

    public void setRegisteredMails(List<RegisteredMail> registeredMails) {
        this.registeredMails = registeredMails;
    }

    /**
     * Adds an inhabitant to this area.
     *
     * @param inhabitant the inhabitant to add
     */
    public void addInhabitant(Inhabitant inhabitant) {
        if (!inhabitants.contains(inhabitant)) {
            inhabitants.add(inhabitant);
        }
    }

    /**
     * Removes an inhabitant from this area and deletes any registered mail
     * assigned to a mailman where the addressee is that inhabitant.
     *
     * @param inhabitant the inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (!inhabitants.contains(inhabitant)) {
            return false;
        }

        // Remove the inhabitant
        inhabitants.remove(inhabitant);

        // Delete any registered mail whose addressee is that inhabitant and which has already been assigned to a mailman
        registeredMails.removeIf(mail -> 
            Objects.equals(mail.getAddressee(), inhabitant) && mail.getAssignedMailman() != null);

        return true;
    }

    /**
     * Adds a mailman to this area if they're not already assigned to it.
     *
     * @param mailman the mailman to add
     * @return true if added successfully, false if already exists in area
     */
    public boolean addMailman(Mailman mailman) {
        if (mailmen.contains(mailman)) {
            return false;
        }
        mailmen.add(mailman);
        return true;
    }

    /**
     * Removes a mailman from this area with conditions:
     * - Must keep at least one mailman in the area
     * - Must specify a replacement mailman
     * - Must successfully reassign all mail before removal
     *
     * @param mailmanToRemove the mailman to remove
     * @param replacementMailman the mailman to take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman replacementMailman) {
        // Check if mailman exists in area
        if (!mailmen.contains(mailmanToRemove) || !mailmen.contains(replacementMailman)) {
            return false;
        }

        // Must keep at least one mailman
        if (mailmen.size() <= 1) {
            return false;
        }

        // Reassign all mail from mailmanToRemove to replacementMailman
        for (RegisteredMail mail : registeredMails) {
            if (Objects.equals(mail.getAssignedMailman(), mailmanToRemove)) {
                mail.setAssignedMailman(replacementMailman);
            }
        }

        // Remove the mailman
        mailmen.remove(mailmanToRemove);
        return true;
    }

    /**
     * Retrieves all registered mail deliveries for this area, along with the responsible mailman and the addressees.
     *
     * @return a list of registered mails with delivery information, or an empty list if there are no deliveries
     */
    public List<RegisteredMail> getAllDeliveries() {
        return new ArrayList<>(registeredMails);
    }
}

/**
 * Represents an inhabitant in the mail delivery system.
 */
class Inhabitant {
    private String name;
    private Area area;

    /**
     * Constructs a new Inhabitant with default values.
     */
    public Inhabitant() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}

/**
 * Represents a mailman in the mail delivery system.
 */
class Mailman {
    private String name;
    private List<Area> assignedAreas;

    /**
     * Constructs a new Mailman with default values.
     */
    public Mailman() {
        this.assignedAreas = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Area> getAssignedAreas() {
        return assignedAreas;
    }

    public void setAssignedAreas(List<Area> assignedAreas) {
        this.assignedAreas = assignedAreas;
    }

    /**
     * Adds an area to this mailman's assigned areas.
     *
     * @param area the area to assign
     */
    public void addAssignedArea(Area area) {
        if (!assignedAreas.contains(area)) {
            assignedAreas.add(area);
        }
    }

    /**
     * Checks if this mailman is assigned to the specified area.
     *
     * @param area the area to check
     * @return true if assigned, false otherwise
     */
    public boolean isAssignedToArea(Area area) {
        return assignedAreas.contains(area);
    }
}

/**
 * Abstract base class for registered mail items.
 */
abstract class RegisteredMail {
    private Inhabitant addressee;
    private Mailman assignedMailman;
    private Area area;

    /**
     * Constructs a new RegisteredMail with default values.
     */
    public RegisteredMail() {}

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

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    /**
     * Assigns a specific mailman to deliver this mail item.
     * The mailman and the inhabitant must belong to the addressee's geographical area.
     * Ensures the mail isn't already assigned to any mailman.
     *
     * @param mailman the mailman to assign
     * @return true if the assignment is successful; otherwise, false
     */
    public boolean assignMailman(Mailman mailman) {
        // Check if already assigned
        if (this.assignedMailman != null) {
            return false;
        }

        // Check if mailman and addressee belong to the same area
        if (!mailman.isAssignedToArea(this.area) || 
            !Objects.equals(this.addressee.getArea(), this.area)) {
            return false;
        }

        this.assignedMailman = mailman;
        return true;
    }
}

/**
 * Represents a letter, a type of registered mail.
 */
class Letter extends RegisteredMail {
    private String content;

    /**
     * Constructs a new Letter with default values.
     */
    public Letter() {
        super();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

/**
 * Represents a parcel, a type of registered mail.
 */
class Parcel extends RegisteredMail {
    private double weight;

    /**
     * Constructs a new Parcel with default values.
     */
    public Parcel() {
        super();
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}

/**
 * Manages the mail delivery system operations.
 */
class MailDeliverySystem {
    private List<Area> areas;

    /**
     * Constructs a new MailDeliverySystem with default values.
     */
    public MailDeliverySystem() {
        this.areas = new ArrayList<>();
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    /**
     * Lists all registered mail items (letters and parcels) directed to a specified inhabitant.
     * Includes only mail items that specify the given inhabitant as the addressee.
     *
     * @param inhabitant the inhabitant to find mail for
     * @return a list of registered mail items, or null if none exist
     */
    public List<RegisteredMail> getMailForInhabitant(Inhabitant inhabitant) {
        List<RegisteredMail> result = new ArrayList<>();
        
        for (Area area : areas) {
            for (RegisteredMail mail : area.getRegisteredMails()) {
                if (Objects.equals(mail.getAddressee(), inhabitant)) {
                    result.add(mail);
                }
            }
        }
        
        return result.isEmpty() ? null : result;
    }
}