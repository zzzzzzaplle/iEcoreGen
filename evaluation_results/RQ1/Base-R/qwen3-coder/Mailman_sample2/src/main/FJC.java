import java.util.*;

/**
 * Represents a geographical area in the mail delivery system.
 */
class Area {
    private String name;
    private Set<Inhabitant> inhabitants;
    private Set<Mailman> mailmen;
    private Set<RegisteredMail> mails;

    /**
     * Constructs a new Area with default values.
     */
    public Area() {
        this.inhabitants = new HashSet<>();
        this.mailmen = new HashSet<>();
        this.mails = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    public void setInhabitants(Set<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    public Set<Mailman> getMailmen() {
        return mailmen;
    }

    public void setMailmen(Set<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    public Set<RegisteredMail> getMails() {
        return mails;
    }

    public void setMails(Set<RegisteredMail> mails) {
        this.mails = mails;
    }

    /**
     * Adds an inhabitant to this area.
     *
     * @param inhabitant the inhabitant to add
     * @return true if the inhabitant was added, false otherwise
     */
    public boolean addInhabitant(Inhabitant inhabitant) {
        return this.inhabitants.add(inhabitant);
    }

    /**
     * Removes an inhabitant from this area and deletes any registered mail
     * addressed to that inhabitant which has already been assigned to a mailman.
     *
     * @param inhabitant the inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (!this.inhabitants.contains(inhabitant)) {
            return false;
        }

        // Remove the inhabitant
        this.inhabitants.remove(inhabitant);

        // Delete any registered mail addressed to that inhabitant which has already been assigned
        Iterator<RegisteredMail> iterator = this.mails.iterator();
        while (iterator.hasNext()) {
            RegisteredMail mail = iterator.next();
            if (mail.getAddressee().equals(inhabitant) && mail.getMailman() != null) {
                iterator.remove();
            }
        }

        return true;
    }

    /**
     * Adds a mailman to this area if they're not already assigned to it.
     *
     * @param mailman the mailman to add
     * @return true if the mailman was added, false otherwise
     */
    public boolean addMailman(Mailman mailman) {
        if (this.mailmen.contains(mailman)) {
            return false;
        }
        return this.mailmen.add(mailman);
    }

    /**
     * Removes a mailman from this area with the following conditions:
     * - Keep at least one mailman in the area
     * - Specify a different, existing mailman to take over deliveries
     * - Successfully reassign all mail before removal
     *
     * @param mailmanToRemove the mailman to remove
     * @param replacementMailman the mailman to take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman replacementMailman) {
        // Check if mailmanToRemove exists in this area
        if (!this.mailmen.contains(mailmanToRemove)) {
            return false;
        }

        // Check if replacementMailman exists in this area and is different
        if (!this.mailmen.contains(replacementMailman) || mailmanToRemove.equals(replacementMailman)) {
            return false;
        }

        // Ensure at least one mailman remains after removal
        if (this.mailmen.size() <= 1) {
            return false;
        }

        // Reassign all mails from mailmanToRemove to replacementMailman
        for (RegisteredMail mail : this.mails) {
            if (mail.getMailman() != null && mail.getMailman().equals(mailmanToRemove)) {
                mail.setMailman(replacementMailman);
            }
        }

        // Remove the mailman
        return this.mailmen.remove(mailmanToRemove);
    }

    /**
     * Retrieves all registered mail deliveries for this area, along with the responsible mailman and the addressees.
     *
     * @return a list of registered mail deliveries or an empty list if there are no deliveries
     */
    public List<RegisteredMail> getAllDeliveries() {
        return new ArrayList<>(this.mails);
    }

    /**
     * Assigns a specific mailman to deliver a registered inhabitant's mail item.
     * The mailman and the inhabitant must belong to this area. And ensures the mail isn't already assigned to any mailman.
     *
     * @param mail the mail item to assign
     * @param mailman the mailman to assign
     * @param inhabitant the inhabitant (addressee)
     * @return true if the assignment is successful; otherwise, false
     */
    public boolean assignMailToMailman(RegisteredMail mail, Mailman mailman, Inhabitant inhabitant) {
        // Check if mailman belongs to this area
        if (!this.mailmen.contains(mailman)) {
            return false;
        }

        // Check if inhabitant belongs to this area
        if (!this.inhabitants.contains(inhabitant)) {
            return false;
        }

        // Check if mail is already assigned
        if (mail.getMailman() != null) {
            return false;
        }

        // Check if mail's addressee is the inhabitant
        if (!mail.getAddressee().equals(inhabitant)) {
            return false;
        }

        // Assign the mailman
        mail.setMailman(mailman);
        return true;
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
    private Set<Area> areas;

    /**
     * Constructs a new Mailman with default values.
     */
    public Mailman() {
        this.areas = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Area> getAreas() {
        return areas;
    }

    public void setAreas(Set<Area> areas) {
        this.areas = areas;
    }
}

/**
 * Represents a registered mail item in the mail delivery system.
 */
abstract class RegisteredMail {
    private Inhabitant addressee;
    private Mailman mailman;

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

    public Mailman getMailman() {
        return mailman;
    }

    public void setMailman(Mailman mailman) {
        this.mailman = mailman;
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
    private Set<Area> areas;

    /**
     * Constructs a new MailDeliverySystem with default values.
     */
    public MailDeliverySystem() {
        this.areas = new HashSet<>();
    }

    public Set<Area> getAreas() {
        return areas;
    }

    public void setAreas(Set<Area> areas) {
        this.areas = areas;
    }

    /**
     * Lists all registered mail items (letters and parcels) directed to a specified inhabitant.
     * Includes only mail items that specify the given inhabitant as the addressee.
     *
     * @param inhabitant the inhabitant to find mail for
     * @return a list of registered mail items or null if none exist
     */
    public List<RegisteredMail> getMailForInhabitant(Inhabitant inhabitant) {
        List<RegisteredMail> result = new ArrayList<>();
        
        // Find the area of the inhabitant
        Area area = inhabitant.getArea();
        if (area == null) {
            return null;
        }
        
        // Look through all mails in the area
        for (RegisteredMail mail : area.getMails()) {
            if (mail.getAddressee().equals(inhabitant)) {
                result.add(mail);
            }
        }
        
        return result.isEmpty() ? null : result;
    }
}