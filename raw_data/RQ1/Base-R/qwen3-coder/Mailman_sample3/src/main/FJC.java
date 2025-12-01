import java.util.*;

/**
 * Represents a geographical area in the mail delivery system.
 */
class Area {
    private String name;
    private List<Inhabitant> inhabitants;
    private List<Mailman> mailmen;
    private List<RegisteredMail> deliveries;

    /**
     * Constructs an Area with default values.
     */
    public Area() {
        this.inhabitants = new ArrayList<>();
        this.mailmen = new ArrayList<>();
        this.deliveries = new ArrayList<>();
    }

    /**
     * Gets the name of the area.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the area.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of inhabitants in this area.
     *
     * @return the inhabitants
     */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /**
     * Sets the list of inhabitants in this area.
     *
     * @param inhabitants the inhabitants to set
     */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /**
     * Gets the list of mailmen assigned to this area.
     *
     * @return the mailmen
     */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /**
     * Sets the list of mailmen assigned to this area.
     *
     * @param mailmen the mailmen to set
     */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /**
     * Gets the list of deliveries in this area.
     *
     * @return the deliveries
     */
    public List<RegisteredMail> getDeliveries() {
        return deliveries;
    }

    /**
     * Sets the list of deliveries in this area.
     *
     * @param deliveries the deliveries to set
     */
    public void setDeliveries(List<RegisteredMail> deliveries) {
        this.deliveries = deliveries;
    }
}

/**
 * Represents an inhabitant in the mail delivery system.
 */
class Inhabitant {
    private String name;
    private Area area;

    /**
     * Constructs an Inhabitant with default values.
     */
    public Inhabitant() {
    }

    /**
     * Gets the name of the inhabitant.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the inhabitant.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the area where the inhabitant lives.
     *
     * @return the area
     */
    public Area getArea() {
        return area;
    }

    /**
     * Sets the area where the inhabitant lives.
     *
     * @param area the area to set
     */
    public void setArea(Area area) {
        this.area = area;
    }
}

/**
 * Represents a mailman in the mail delivery system.
 */
class Mailman {
    private String name;
    private List<Area> areas;

    /**
     * Constructs a Mailman with default values.
     */
    public Mailman() {
        this.areas = new ArrayList<>();
    }

    /**
     * Gets the name of the mailman.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the mailman.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of areas assigned to this mailman.
     *
     * @return the areas
     */
    public List<Area> getAreas() {
        return areas;
    }

    /**
     * Sets the list of areas assigned to this mailman.
     *
     * @param areas the areas to set
     */
    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}

/**
 * Abstract class representing registered mail.
 */
abstract class RegisteredMail {
    private Inhabitant addressee;
    private Mailman deliveringMailman;

    /**
     * Constructs a RegisteredMail with default values.
     */
    public RegisteredMail() {
    }

    /**
     * Gets the addressee of the mail.
     *
     * @return the addressee
     */
    public Inhabitant getAddressee() {
        return addressee;
    }

    /**
     * Sets the addressee of the mail.
     *
     * @param addressee the addressee to set
     */
    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }

    /**
     * Gets the mailman delivering this mail.
     *
     * @return the deliveringMailman
     */
    public Mailman getDeliveringMailman() {
        return deliveringMailman;
    }

    /**
     * Sets the mailman delivering this mail.
     *
     * @param deliveringMailman the deliveringMailman to set
     */
    public void setDeliveringMailman(Mailman deliveringMailman) {
        this.deliveringMailman = deliveringMailman;
    }
}

/**
 * Represents a letter, a type of registered mail.
 */
class Letter extends RegisteredMail {
    /**
     * Constructs a Letter with default values.
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
     * Constructs a Parcel with default values.
     */
    public Parcel() {
        super();
    }
}

/**
 * Manages the mail delivery system operations.
 */
class MailDeliverySystem {
    private List<Area> areas;

    /**
     * Constructs a MailDeliverySystem with default values.
     */
    public MailDeliverySystem() {
        this.areas = new ArrayList<>();
    }

    /**
     * Gets the list of areas in the system.
     *
     * @return the areas
     */
    public List<Area> getAreas() {
        return areas;
    }

    /**
     * Sets the list of areas in the system.
     *
     * @param areas the areas to set
     */
    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    /**
     * Assigns a specific mailman to deliver a registered inhabitant's mail item.
     * The mailman and the inhabitant must belong to the addressee's geographical area.
     * Ensures the mail isn't already assigned to any mailman.
     *
     * @param mailItem The mail item to be assigned
     * @param mailman  The mailman to assign for delivery
     * @param inhabitant The inhabitant who will receive the mail
     * @return true if the assignment is successful; otherwise, false
     */
    public boolean assignMailmanToDeliverMail(RegisteredMail mailItem, Mailman mailman, Inhabitant inhabitant) {
        // Check if mail is already assigned
        if (mailItem.getDeliveringMailman() != null) {
            return false;
        }

        // Check if mailman and inhabitant belong to the same area as the addressee
        Area addresseeArea = inhabitant.getArea();
        if (addresseeArea == null) {
            return false;
        }

        // Check if mailman is assigned to the area
        if (!mailman.getAreas().contains(addresseeArea)) {
            return false;
        }

        // Check if inhabitant belongs to the area
        if (!addresseeArea.getInhabitants().contains(inhabitant)) {
            return false;
        }

        // Assign the mailman and add to deliveries
        mailItem.setDeliveringMailman(mailman);
        mailItem.setAddressee(inhabitant);
        addresseeArea.getDeliveries().add(mailItem);
        
        return true;
    }

    /**
     * Retrieves all registered mail deliveries for a given geographical area,
     * along with the responsible mailman and the addressees.
     *
     * @param area The geographical area for which to retrieve deliveries
     * @return A list of registered mail deliveries, or an empty list if there are no deliveries
     */
    public List<RegisteredMail> getDeliveriesForArea(Area area) {
        if (area == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(area.getDeliveries());
    }

    /**
     * Adds a new inhabitant to a geographical area.
     *
     * @param inhabitant The inhabitant to add
     * @param area       The geographical area to which the inhabitant will be added
     * @return true if the operation is successful, false otherwise
     */
    public boolean addInhabitant(Inhabitant inhabitant, Area area) {
        if (inhabitant == null || area == null) {
            return false;
        }
        
        inhabitant.setArea(area);
        return area.getInhabitants().add(inhabitant);
    }

    /**
     * Removes an inhabitant from the geographical area and deletes any registered mail
     * whose addressee is that inhabitant and which has already been assigned to a mailman.
     *
     * @param inhabitant The inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null || inhabitant.getArea() == null) {
            return false;
        }

        Area area = inhabitant.getArea();
        
        // Remove the inhabitant from the area
        boolean removed = area.getInhabitants().remove(inhabitant);
        if (!removed) {
            return false;
        }

        // Delete registered mail items assigned to this inhabitant
        Iterator<RegisteredMail> deliveryIterator = area.getDeliveries().iterator();
        while (deliveryIterator.hasNext()) {
            RegisteredMail mail = deliveryIterator.next();
            if (mail.getAddressee() == inhabitant) {
                deliveryIterator.remove();
            }
        }

        return true;
    }

    /**
     * Adds a mailman to the system if they're not already assigned the geographical area.
     *
     * @param mailman The mailman to add
     * @param area    The area to which the mailman should be assigned
     * @return true if the operation is successful, false otherwise
     */
    public boolean addMailman(Mailman mailman, Area area) {
        if (mailman == null || area == null) {
            return false;
        }

        // Check if mailman is already assigned to this area
        if (mailman.getAreas().contains(area)) {
            return false;
        }

        mailman.getAreas().add(area);
        return area.getMailmen().add(mailman);
    }

    /**
     * Removes a mailman from the system with all required constraints.
     *
     * @param mailmanToRemove   The mailman to remove
     * @param area              The area from which to remove the mailman
     * @param replacementMailman The mailman to take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Area area, Mailman replacementMailman) {
        if (mailmanToRemove == null || area == null || replacementMailman == null) {
            return false;
        }

        // Check if the area has at least one mailman after removal
        if (area.getMailmen().size() <= 1 && area.getMailmen().contains(mailmanToRemove)) {
            return false;
        }

        // Check if replacement mailman exists and is assigned to the area
        if (!area.getMailmen().contains(replacementMailman)) {
            return false;
        }

        // Check if mailman to remove is actually assigned to the area
        if (!area.getMailmen().contains(mailmanToRemove)) {
            return false;
        }

        // Reassign all deliveries from mailmanToRemove to replacementMailman
        for (RegisteredMail mail : area.getDeliveries()) {
            if (mail.getDeliveringMailman() == mailmanToRemove) {
                mail.setDeliveringMailman(replacementMailman);
            }
        }

        // Remove the mailman from the area
        mailmanToRemove.getAreas().remove(area);
        return area.getMailmen().remove(mailmanToRemove);
    }

    /**
     * Lists all registered mail items (letters and parcels) directed to a specified inhabitant.
     * Includes only mail items that specify the given inhabitant as the addressee.
     *
     * @param inhabitant The inhabitant for whom to list mail items
     * @return A list of registered mail items, or null if none exist
     */
    public List<RegisteredMail> getMailForInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null) {
            return null;
        }

        List<RegisteredMail> mailItems = new ArrayList<>();
        
        // Search in all areas for mail addressed to this inhabitant
        for (Area area : areas) {
            for (RegisteredMail mail : area.getDeliveries()) {
                if (mail.getAddressee() == inhabitant) {
                    mailItems.add(mail);
                }
            }
        }

        return mailItems.isEmpty() ? null : mailItems;
    }
}