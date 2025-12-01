import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a geographical area.  It stores the inhabitants,
 * mailmen and all registered mail items belonging to the area.
 */
 class GeographicalArea {

    private String id;
    private String name;
    private final List<Inhabitant> inhabitants = new ArrayList<>();
    private final List<Mailman> mailmen = new ArrayList<>();
    private final List<RegisteredMail> registeredMails = new ArrayList<>();

    public GeographicalArea() {
    }

    public GeographicalArea(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /* -------------------- getters & setters -------------------- */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   /**
    * Returns an unmodifiable view of the inhabitants belonging to this area.
    */
    public List<Inhabitant> getInhabitants() {
        return Collections.unmodifiableList(inhabitants);
    }

   /**
    * Returns an unmodifiable view of the mailmen belonging to this area.
    */
    public List<Mailman> getMailmen() {
        return Collections.unmodifiableList(mailmen);
    }

   /**
    * Returns an unmodifiable view of the registered mails belonging to this area.
    */
    public List<RegisteredMail> getRegisteredMails() {
        return Collections.unmodifiableList(registeredMails);
    }

    /* -------------------- internal manipulation helpers -------------------- */

    /**
     * Adds an inhabitant to this area.
     *
     * @param inhabitant the inhabitant to add
     * @return true if the inhabitant was added, false if already present
     */
    boolean internalAddInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null || inhabitants.contains(inhabitant)) {
            return false;
        }
        inhabitants.add(inhabitant);
        inhabitant.setArea(this);
        return true;
    }

    /**
     * Removes an inhabitant from this area.
     *
     * @param inhabitant the inhabitant to remove
     * @return true if the inhabitant was removed, false otherwise
     */
    boolean internalRemoveInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null) {
            return false;
        }
        boolean removed = inhabitants.remove(inhabitant);
        if (removed) {
            inhabitant.setArea(null);
        }
        return removed;
    }

    /**
     * Adds a mailman to this area.
     *
     * @param mailman the mailman to add
     * @return true if the mailman was added, false if already present
     */
    boolean internalAddMailman(Mailman mailman) {
        if (mailman == null || mailmen.contains(mailman)) {
            return false;
        }
        mailmen.add(mailman);
        mailman.setArea(this);
        return true;
    }

    /**
     * Removes a mailman from this area.
     *
     * @param mailman the mailman to remove
     * @return true if the mailman was removed, false otherwise
     */
    boolean internalRemoveMailman(Mailman mailman) {
        if (mailman == null) {
            return false;
        }
        boolean removed = mailmen.remove(mailman);
        if (removed) {
            mailman.setArea(null);
        }
        return removed;
    }

    /**
     * Adds a registered mail item to this area.
     *
     * @param mail the mail item to add
     * @return true if added, false if already present
     */
    boolean internalAddRegisteredMail(RegisteredMail mail) {
        if (mail == null || registeredMails.contains(mail)) {
            return false;
        }
        registeredMails.add(mail);
        mail.setArea(this);
        return true;
    }

    /**
     * Removes a registered mail item from this area.
     *
     * @param mail the mail item to remove
     * @return true if removed, false otherwise
     */
    boolean internalRemoveRegisteredMail(RegisteredMail mail) {
        if (mail == null) {
            return false;
        }
        boolean removed = registeredMails.remove(mail);
        if (removed) {
            mail.setArea(null);
        }
        return removed;
    }
}

/**
 * Represents an inhabitant (a citizen) that lives in a geographical area.
 */
class Inhabitant {

    private String id;
    private String name;
    private GeographicalArea area; // set automatically when added to an area

    public Inhabitant() {
    }

    public Inhabitant(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /* -------------------- getters & setters -------------------- */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    void setArea(GeographicalArea area) {
        this.area = area;
    }

    /* -------------------- equality based on id -------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inhabitant that = (Inhabitant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents a mailman (letter carrier) that works in a geographical area.
 */
class Mailman {

    private String id;
    private String name;
    private GeographicalArea area; // set automatically when added to an area

    public Mailman() {
    }

    public Mailman(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /* -------------------- getters & setters -------------------- */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    void setArea(GeographicalArea area) {
        this.area = area;
    }

    /* -------------------- equality based on id -------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mailman mailman = (Mailman) o;
        return Objects.equals(id, mailman.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Abstract base class for all registered mail items (letters and parcels).
 */
abstract class RegisteredMail {

    private String id;
    private Inhabitant addressee;                     // the intended recipient
    private Mailman assignedMailman;                  // null until assignment
    private GeographicalArea area;                    // set automatically when added to an area

    public RegisteredMail() {
    }

    public RegisteredMail(String id, Inhabitant addressee) {
        this.id = id;
        this.addressee = addressee;
    }

    /* -------------------- getters & setters -------------------- */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

   public GeographicalArea getArea() {
        return area;
    }

    void setArea(GeographicalArea area) {
        this.area = area;
    }

    /* -------------------- equality based on id -------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegisteredMail that = (RegisteredMail) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Concrete class representing a registered letter.
 */
class Letter extends RegisteredMail {

    private String subject;
    private String body;

    public Letter() {
    }

    public Letter(String id, Inhabitant addressee, String subject, String body) {
        super(id, addressee);
        this.subject = subject;
        this.body = body;
    }

    /* -------------------- getters & setters -------------------- */

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

   public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

/**
 * Concrete class representing a registered parcel.
 */
class Parcel extends RegisteredMail {

    private double weightKg;
    private String description;

    public Parcel() {
    }

    public Parcel(String id, Inhabitant addressee, double weightKg, String description) {
        super(id, addressee);
        this.weightKg = weightKg;
        this.description = description;
    }

    /* -------------------- getters & setters -------------------- */

    public double getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(double weightKg) {
        this.weightKg = weightKg;
    }

   public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

/**
 * Service class that implements all functional requirements concerning
 * mail delivery, inhabitants and mailmen management.
 */
 class MailDeliverySystem {

    private final List<GeographicalArea> areas = new ArrayList<>();

    public MailDeliverySystem() {
    }

    /* -------------------- Area handling helpers -------------------- */

    /**
     * Adds a new geographical area to the system.
     *
     * @param area the area to add
     * @return true if added, false if already present
     */
    public boolean addArea(GeographicalArea area) {
        if (area == null || areas.contains(area)) {
            return false;
        }
        areas.add(area);
        return true;
    }

    /**
     * Retrieves an area by its identifier.
     *
     * @param areaId identifier of the area
     * @return the matching GeographicalArea or null if not found
     */
    public GeographicalArea getAreaById(String areaId) {
        for (GeographicalArea a : areas) {
            if (Objects.equals(a.getId(), areaId)) {
                return a;
            }
        }
        return null;
    }

    /* -------------------- Functional Requirement Implementations -------------------- */

    /**
     * Assigns a specific mailman to deliver a registered mail item.
     * The mailman and the addressee must belong to the same geographical area,
     * and the mail must not already be assigned.
     *
     * @param mail    the registered mail to be assigned
     * @param mailman the mailman who will deliver the mail
     * @return true if the assignment succeeded, false otherwise
     */
    public boolean assignMailToMailman(RegisteredMail mail, Mailman mailman) {
        if (mail == null || mailman == null) {
            return false;
        }
        // mail must belong to an area, mailman must belong to an area
        GeographicalArea mailArea = mail.getArea();
        GeographicalArea mailmanArea = mailman.getArea();

        if (mailArea == null || mailmanArea == null) {
            return false;
        }

        // both must be in the same area
        if (!mailArea.equals(mailmanArea)) {
            return false;
        }

        // mail must not already have an assigned mailman
        if (mail.getAssignedMailman() != null) {
            return false;
        }

        mail.setAssignedMailman(mailman);
        return true;
    }

    /**
     * Retrieves all registered mail deliveries for a given geographical area,
     * together with the responsible mailman and the addressees.
     *
     * @param area the geographical area to query
     * @return a list of DeliveryInfo objects; empty list if none exist
     */
    public List<DeliveryInfo> getDeliveriesByArea(GeographicalArea area) {
        if (area == null) {
            return Collections.emptyList();
        }
        List<DeliveryInfo> result = new ArrayList<>();
        for (RegisteredMail mail : area.getRegisteredMails()) {
            // Only consider mails that have been assigned to a mailman
            if (mail.getAssignedMailman() != null) {
                result.add(new DeliveryInfo(mail, mail.getAssignedMailman(), mail.getAddressee()));
            }
        }
        return result;
    }

    /**
     * Adds a new inhabitant to a geographical area.
     *
     * @param area       the area where the inhabitant will live
     * @param inhabitant the inhabitant to add
     * @return true if the inhabitant was added, false otherwise
     */
    public boolean addInhabitant(GeographicalArea area, Inhabitant inhabitant) {
        if (area == null || inhabitant == null) {
            return false;
        }
        return area.internalAddInhabitant(inhabitant);
    }

    /**
     * Removes an inhabitant from a geographical area.  All registered mail
     * whose addressee is that inhabitant and which has already been assigned
     * to a mailman are deleted.
     *
     * @param area       the area from which the inhabitant will be removed
     * @param inhabitant the inhabitant to remove
     * @return true if removal (and associated deletions) succeeded, false otherwise
     */
    public boolean removeInhabitant(GeographicalArea area, Inhabitant inhabitant) {
        if (area == null || inhabitant == null) {
            return false;
        }
        if (!area.getInhabitants().contains(inhabitant)) {
            return false;
        }

        // Delete registered mails that are assigned and belong to this inhabitant
        List<RegisteredMail> toDelete = new ArrayList<>();
        for (RegisteredMail mail : area.getRegisteredMails()) {
            if (inhabitant.equals(mail.getAddressee()) && mail.getAssignedMailman() != null) {
                toDelete.add(mail);
            }
        }
        for (RegisteredMail mail : toDelete) {
            area.internalRemoveRegisteredMail(mail);
        }

        // Finally remove the inhabitant
        return area.internalRemoveInhabitant(inhabitant);
    }

    /**
     * Adds a mailman to a geographical area if the mailman is not already
     * assigned to that area.
     *
     * @param area    the area to which the mailman will be added
     * @param mailman the mailman to add
     * @return true if the mailman was added, false otherwise
     */
    public boolean addMailman(GeographicalArea area, Mailman mailman) {
        if (area == null || mailman == null) {
            return false;
        }
        return area.internalAddMailman(mailman);
    }

    /**
     * Removes a mailman from a geographical area.  The removal succeeds only
     * if:
     * <ul>
     *   <li>At least one other mailman will remain in the area after removal.</li>
     *   <li>A replacement mailman (different from the one being removed) is
     *   supplied and already belongs to the same area.</li>
     *   <li>All deliveries assigned to the removed mailman are successfully
     *   reassigned to the replacement mailman.</li>
     * </ul>
     *
     * @param area          the area from which the mailman will be removed
     * @param mailmanToRemove the mailman to remove
     * @param replacementMailman a different mailman that will take over the deliveries
     * @return true if removal and reassignment succeeded, false otherwise
     */
    public boolean removeMailman(GeographicalArea area,
                                 Mailman mailmanToRemove,
                                 Mailman replacementMailman) {
        if (area == null || mailmanToRemove == null || replacementMailman == null) {
            return false;
        }

        // Both mailmen must belong to the same area
        if (!area.getMailmen().contains(mailmanToRemove) ||
            !area.getMailmen().contains(replacementMailman) ||
            mailmanToRemove.equals(replacementMailman)) {
            return false;
        }

        // Ensure at least one mailman will remain after removal
        if (area.getMailmen().size() <= 1) {
            return false;
        }

        // Reassign all deliveries from mailmanToRemove to replacementMailman
        for (RegisteredMail mail : area.getRegisteredMails()) {
            if (mailmanToRemove.equals(mail.getAssignedMailman())) {
                mail.setAssignedMailman(replacementMailman);
            }
        }

        // Remove the mailman from the area
        return area.internalRemoveMailman(mailmanToRemove);
    }

    /**
     * Lists all registered mail items (letters and parcels) directed to a
     * specified inhabitant.  Only mail items that list the given inhabitant as
     * the addressee are returned.
     *
     * @param inhabitant the inhabitant whose mail items are requested
     * @return a list of matching RegisteredMail objects, or null if none exist
     */
    public List<RegisteredMail> listMailForInhabitant(Inhabitant inhabitant) {
        if (inhabitant == null) {
            return null;
        }
        GeographicalArea area = inhabitant.getArea();
        if (area == null) {
            return null;
        }
        List<RegisteredMail> result = new ArrayList<>();
        for (RegisteredMail mail : area.getRegisteredMails()) {
            if (inhabitant.equals(mail.getAddressee())) {
                result.add(mail);
            }
        }
        return result.isEmpty() ? null : result;
    }

    /* -------------------- Helper DTO -------------------- */

    /**
     * Simple data‑transfer object used to expose delivery information.
     */
    public static class DeliveryInfo {
        private final RegisteredMail mail;
        private final Mailman mailman;
        private final Inhabitant addressee;

        public DeliveryInfo(RegisteredMail mail, Mailman mailman, Inhabitant addressee) {
            this.mail = mail;
            this.mailman = mailman;
            this.addressee = addressee;
        }

        public RegisteredMail getMail() {
            return mail;
        }

        public Mailman getMailman() {
            return mailman;
        }

        public Inhabitant getAddressee() {
            return addressee;
        }
    }
}