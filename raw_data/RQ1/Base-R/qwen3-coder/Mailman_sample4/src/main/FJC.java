import java.util.*;

/**
 * Represents a geographical area in the mail delivery system.
 */
class Area {
    private String name;
    private List<Inhabitant> inhabitants;
    private List<Mailman> mailmen;
    private List<RegisteredMail> registeredMails;

    /**
     * Constructs a new Area with empty lists for inhabitants, mailmen, and registered mails.
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
     * Assigns a specific mailman to deliver a registered inhabitant's mail item.
     * The mailman and the inhabitant must belong to the addressee's geographical area.
     * Ensures the mail isn't already assigned to any mailman.
     *
     * @param mail The registered mail item to be assigned
     * @param mailman The mailman to assign the delivery to
     * @param inhabitant The inhabitant (addressee) of the mail
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

        // Check if mail's addressee is the inhabitant
        if (!mail.getAddressee().equals(inhabitant)) {
            return false;
        }

        // Check if mail is already assigned to a mailman
        if (mail.getMailman() != null) {
            return false;
        }

        // Assign the mailman to the mail
        mail.setMailman(mailman);
        return true;
    }

    /**
     * Retrieves all registered mail deliveries for this geographical area,
     * along with the responsible mailman and the addressees.
     *
     * @return A list of registered mail deliveries, or an empty list if there are no deliveries
     */
    public List<RegisteredMail> getDeliveries() {
        return new ArrayList<>(this.registeredMails);
    }

    /**
     * Adds a new inhabitant to this geographical area.
     *
     * @param inhabitant The inhabitant to add
     * @return true if the operation is successful, false otherwise
     */
    public boolean addInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null) {
            return false;
        }
        return this.inhabitants.add(inhabitant);
    }

    /**
     * Removes an inhabitant from this geographical area and deletes any registered mail
     * whose addressee is that inhabitant and which has already been assigned to a mailman.
     *
     * @param inhabitant The inhabitant to remove
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null || !this.inhabitants.contains(inhabitant)) {
            return false;
        }

        // Remove the inhabitant
        boolean removed = this.inhabitants.remove(inhabitant);

        // Delete registered mails for this inhabitant that are assigned to a mailman
        Iterator<RegisteredMail> iterator = this.registeredMails.iterator();
        while (iterator.hasNext()) {
            RegisteredMail mail = iterator.next();
            if (mail.getAddressee().equals(inhabitant) && mail.getMailman() != null) {
                iterator.remove();
            }
        }

        return removed;
    }

    /**
     * Adds a mailman to this area if they're not already assigned to it.
     *
     * @param mailman The mailman to add
     * @return true if the operation is successful, false otherwise
     */
    public boolean addMailman(Mailman mailman) {
        if (mailman == null || this.mailmen.contains(mailman)) {
            return false;
        }
        return this.mailmen.add(mailman);
    }

    /**
     * Removes a mailman from this area, ensuring:
     * - At least one mailman remains in the area
     * - A different, existing mailman is specified to take over deliveries
     * - All mail is successfully reassigned before removal
     *
     * @param mailmanToRemove The mailman to remove
     * @param replacementMailman The mailman to take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman replacementMailman) {
        // Check if there's at least one mailman remaining after removal
        if (this.mailmen.size() <= 1) {
            return false;
        }

        // Check if mailmanToRemove exists in this area
        if (!this.mailmen.contains(mailmanToRemove)) {
            return false;
        }

        // Check if replacementMailman exists in this area and is different
        if (!this.mailmen.contains(replacementMailman) || mailmanToRemove.equals(replacementMailman)) {
            return false;
        }

        // Reassign all mail from mailmanToRemove to replacementMailman
        for (RegisteredMail mail : this.registeredMails) {
            if (mail.getMailman() != null && mail.getMailman().equals(mailmanToRemove)) {
                mail.setMailman(replacementMailman);
            }
        }

        // Remove the mailman
        return this.mailmen.remove(mailmanToRemove);
    }

    /**
     * Lists all registered mail items (letters and parcels) directed to a specified inhabitant.
     * Includes only mail items that specify the given inhabitant as the addressee.
     *
     * @param inhabitant The inhabitant to find mail for
     * @return A list of registered mail items, or null if none exist
     */
    public List<RegisteredMail> getMailForInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null) {
            return null;
        }

        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : this.registeredMails) {
            if (mail.getAddressee().equals(inhabitant)) {
                result.add(mail);
            }
        }

        return result.isEmpty() ? null : result;
    }
}

/**
 * Represents an inhabitant in the mail delivery system.
 */
class Inhabitant {
    private String name;
    private Area area;

    /**
     * Constructs a new Inhabitant with no assigned area.
     */
    public Inhabitant() {
    }

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
    private List<Area> areas;

    /**
     * Constructs a new Mailman with an empty list of areas.
     */
    public Mailman() {
        this.areas = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}

/**
 * Represents a registered mail item in the mail delivery system.
 * This is an abstract class with two concrete subclasses: Letter and Parcel.
 */
abstract class RegisteredMail {
    private Inhabitant addressee;
    private Mailman mailman;

    /**
     * Constructs a new RegisteredMail with no addressee or mailman assigned.
     */
    public RegisteredMail() {
    }

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
 * Represents a letter, which is a type of registered mail.
 */
class Letter extends RegisteredMail {
    private String content;

    /**
     * Constructs a new Letter with no content.
     */
    public Letter() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

/**
 * Represents a parcel, which is a type of registered mail.
 */
class Parcel extends RegisteredMail {
    private double weight;
    private String contentsDescription;

    /**
     * Constructs a new Parcel with default weight and no contents description.
     */
    public Parcel() {
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getContentsDescription() {
        return contentsDescription;
    }

    public void setContentsDescription(String contentsDescription) {
        this.contentsDescription = contentsDescription;
    }
}