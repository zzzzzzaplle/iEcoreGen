import java.util.*;

/**
 * Represents a geographical area in the mail delivery system.
 */
class Area {
    private String name;
    private List<Inhabitant> inhabitants;
    private List<Mailman> mailmen;
    private List<RegisteredMail> mails;

    /**
     * Constructs a new Area with default values.
     */
    public Area() {
        this.inhabitants = new ArrayList<>();
        this.mailmen = new ArrayList<>();
        this.mails = new ArrayList<>();
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

    public List<RegisteredMail> getMails() {
        return mails;
    }

    public void setMails(List<RegisteredMail> mails) {
        this.mails = mails;
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
     * Removes an inhabitant from this area and deletes their assigned mails.
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
        mails.removeIf(mail -> mail.getAddressee().equals(inhabitant) && mail.getMailman() != null);

        return true;
    }

    /**
     * Adds a mailman to this area if not already assigned.
     *
     * @param mailman the mailman to add
     * @return true if added, false if already exists
     */
    public boolean addMailman(Mailman mailman) {
        if (mailmen.contains(mailman)) {
            return false;
        }
        mailmen.add(mailman);
        return true;
    }

    /**
     * Removes a mailman from this area with reassignment of mails.
     *
     * @param mailmanToRemove the mailman to remove
     * @param replacementMailman the mailman to take over deliveries
     * @return true if the operation is successful, false otherwise
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman replacementMailman) {
        // Check if mailman exists in area
        if (!mailmen.contains(mailmanToRemove)) {
            return false;
        }

        // Check if keeping at least one mailman
        if (mailmen.size() <= 1) {
            return false;
        }

        // Check if replacement mailman exists in area
        if (!mailmen.contains(replacementMailman)) {
            return false;
        }

        // Reassign all mails from mailmanToRemove to replacementMailman
        for (RegisteredMail mail : mails) {
            if (mail.getMailman() != null && mail.getMailman().equals(mailmanToRemove)) {
                mail.setMailman(replacementMailman);
            }
        }

        // Remove the mailman
        mailmen.remove(mailmanToRemove);
        return true;
    }

    /**
     * Assigns a specific mailman to deliver a registered inhabitant's mail item.
     *
     * @param mail the mail item to assign
     * @param mailman the mailman to assign
     * @param inhabitant the inhabitant (addressee) of the mail
     * @return true if the assignment is successful; otherwise, false
     */
    public boolean assignMailmanToMail(RegisteredMail mail, Mailman mailman, Inhabitant inhabitant) {
        // Check if mailman and inhabitant belong to this area
        if (!mailmen.contains(mailman) || !inhabitants.contains(inhabitant)) {
            return false;
        }

        // Check if mail's addressee is the inhabitant
        if (!mail.getAddressee().equals(inhabitant)) {
            return false;
        }

        // Check if mail is already assigned to any mailman
        if (mail.getMailman() != null) {
            return false;
        }

        // Assign the mailman to the mail
        mail.setMailman(mailman);
        return true;
    }

    /**
     * Retrieves all registered mail deliveries for this area.
     *
     * @return a list of mail deliveries with responsible mailman and addressees, or empty list if none
     */
    public List<MailDeliveryInfo> getMailDeliveries() {
        List<MailDeliveryInfo> deliveries = new ArrayList<>();
        
        for (RegisteredMail mail : mails) {
            if (mail.getMailman() != null) { // Only include assigned mails
                deliveries.add(new MailDeliveryInfo(mail, mail.getMailman(), mail.getAddressee()));
            }
        }
        
        return deliveries;
    }

    /**
     * Lists all registered mail items directed to a specified inhabitant.
     *
     * @param inhabitant the inhabitant to find mails for
     * @return a list of mail items or null if none exist
     */
    public List<RegisteredMail> getMailsForInhabitant(Inhabitant inhabitant) {
        List<RegisteredMail> result = new ArrayList<>();
        
        for (RegisteredMail mail : mails) {
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
    private List<Area> areas;

    /**
     * Constructs a new Mailman with default values.
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

    /**
     * Assigns this mailman to an area.
     *
     * @param area the area to assign to
     */
    public void assignToArea(Area area) {
        if (!areas.contains(area)) {
            areas.add(area);
        }
    }

    /**
     * Removes this mailman from an area.
     *
     * @param area the area to remove from
     */
    public void removeFromArea(Area area) {
        areas.remove(area);
    }
}

/**
 * Represents a registered mail item (abstract base class).
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
 * Represents information about a mail delivery.
 */
class MailDeliveryInfo {
    private RegisteredMail mail;
    private Mailman mailman;
    private Inhabitant addressee;

    /**
     * Constructs a new MailDeliveryInfo with default values.
     */
    public MailDeliveryInfo() {}

    /**
     * Constructs a new MailDeliveryInfo with specified values.
     *
     * @param mail the mail item
     * @param mailman the responsible mailman
     * @param addressee the addressee
     */
    public MailDeliveryInfo(RegisteredMail mail, Mailman mailman, Inhabitant addressee) {
        this.mail = mail;
        this.mailman = mailman;
        this.addressee = addressee;
    }

    public RegisteredMail getMail() {
        return mail;
    }

    public void setMail(RegisteredMail mail) {
        this.mail = mail;
    }

    public Mailman getMailman() {
        return mailman;
    }

    public void setMailman(Mailman mailman) {
        this.mailman = mailman;
    }

    public Inhabitant getAddressee() {
        return addressee;
    }

    public void setAddressee(Inhabitant addressee) {
        this.addressee = addressee;
    }
}