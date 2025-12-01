import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a geographical area. It keeps track of its inhabitants, mailmen and the
 * registered mail items that belong to the area.
 */
class GeographicalArea {
    private String id;
    private String name;
    private List<Inhabitant> inhabitants;
    private List<Mailman> mailmen;
    private List<MailItem> mailItems;

    public GeographicalArea() {
        this.inhabitants = new ArrayList<>();
        this.mailmen = new ArrayList<>();
        this.mailItems = new ArrayList<>();
    }

    public GeographicalArea(String id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

    // ---------- getters & setters ----------
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

    public List<MailItem> getMailItems() {
        return mailItems;
    }

    public void setMailItems(List<MailItem> mailItems) {
        this.mailItems = mailItems;
    }

    // ---------- utility methods ----------
    /**
     * Finds an inhabitant by its identifier.
     *
     * @param inhabitantId the identifier of the inhabitant
     * @return the {@link Inhabitant} if found, otherwise {@code null}
     */
    public Inhabitant findInhabitantById(String inhabitantId) {
        for (Inhabitant i : inhabitants) {
            if (Objects.equals(i.getId(), inhabitantId)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Finds a mailman by its identifier.
     *
     * @param mailmanId the identifier of the mailman
     * @return the {@link Mailman} if found, otherwise {@code null}
     */
    public Mailman findMailmanById(String mailmanId) {
        for (Mailman m : mailmen) {
            if (Objects.equals(m.getId(), mailmanId)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Finds a mail item by its identifier.
     *
     * @param mailItemId the identifier of the mail item
     * @return the {@link MailItem} if found, otherwise {@code null}
     */
    public MailItem findMailItemById(String mailItemId) {
        for (MailItem mi : mailItems) {
            if (Objects.equals(mi.getId(), mailItemId)) {
                return mi;
            }
        }
        return null;
    }
}

/**
 * Represents a person living in a geographical area.
 */
class Inhabitant {
    private String id;
    private String name;
    private GeographicalArea area;

    public Inhabitant() {
    }

    public Inhabitant(String id, String name, GeographicalArea area) {
        this.id = id;
        this.name = name;
        this.area = area;
    }

    // ---------- getters & setters ----------
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

    public void setArea(GeographicalArea area) {
        this.area = area;
    }
}

/**
 * Represents a mailman who delivers registered mail in a geographical area.
 */
class Mailman {
    private String id;
    private String name;
    private GeographicalArea area;

    public Mailman() {
    }

    public Mailman(String id, String name, GeographicalArea area) {
        this.id = id;
        this.name = name;
        this.area = area;
    }

    // ---------- getters & setters ----------
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

    public void setArea(GeographicalArea area) {
        this.area = area;
    }
}

/**
 * Base class for all registered mail items.
 */
abstract class MailItem {
    private String id;
    private Inhabitant addressee;            // who receives the mail
    private Mailman assignedMailman;         // who delivered it (null if not yet assigned)

    public MailItem() {
    }

    public MailItem(String id, Inhabitant addressee) {
        this.id = id;
        this.addressee = addressee;
    }

    // ---------- getters & setters ----------
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

    /**
     * Returns the geographical area of this mail item, derived from its addressee.
     *
     * @return the {@link GeographicalArea} of the addressee
     */
    public GeographicalArea getArea() {
        return addressee != null ? addressee.getArea() : null;
    }
}

/**
 * Represents a registered letter.
 */
class Letter extends MailItem {
    private String subject;
    private String content;

    public Letter() {
        super();
    }

    public Letter(String id, Inhabitant addressee, String subject, String content) {
        super(id, addressee);
        this.subject = subject;
        this.content = content;
    }

    // ---------- getters & setters ----------
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

   public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

/**
 * Represents a registered parcel.
 */
class Parcel extends MailItem {
    private double weight;               // in kilograms
    private String description;

    public Parcel() {
        super();
    }

    public Parcel(String id, Inhabitant addressee, double weight, String description) {
        super(id, addressee);
        this.weight = weight;
        this.description = description;
    }

    // ---------- getters & setters ----------
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

   public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

/**
 * Data Transfer Object used when returning delivery information for a geographical area.
 */
class DeliveryInfo {
    private MailItem mailItem;
    private Mailman mailman;
    private Inhabitant addressee;

    public DeliveryInfo() {
    }

    public DeliveryInfo(MailItem mailItem, Mailman mailman, Inhabitant addressee) {
        this.mailItem = mailItem;
        this.mailman = mailman;
        this.addressee = addressee;
    }

    // ---------- getters & setters ----------
    public MailItem getMailItem() {
        return mailItem;
    }

    public void setMailItem(MailItem mailItem) {
        this.mailItem = mailItem;
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

/**
 * Core service class that implements all functional requirements.
 */
class MailService {
    private List<GeographicalArea> areas;

    public MailService() {
        this.areas = new ArrayList<>();
    }

    // ---------- getters & setters ----------
    public List<GeographicalArea> getAreas() {
        return areas;
    }

    public void setAreas(List<GeographicalArea> areas) {
        this.areas = areas;
    }

    /**
     * Assigns a specific mailman to deliver a registered mail item.
     * <p>
     * The mailman and the mail item must belong to the same geographical area and the
     * item must not already be assigned to any mailman.
     *
     * @param mailItemId the identifier of the mail item to assign
     * @param mailmanId  the identifier of the mailman who will deliver the item
     * @return {@code true} if the assignment succeeded; {@code false} otherwise
     */
    public boolean assignMailItemToMailman(String mailItemId, String mailmanId) {
        // locate the mail item
        MailItem item = null;
        GeographicalArea areaOfItem = null;
        for (GeographicalArea area : areas) {
            item = area.findMailItemById(mailItemId);
            if (item != null) {
                areaOfItem = area;
                break;
            }
        }
        if (item == null) {
            return false; // mail item does not exist
        }

        // locate the mailman (must be in the same area)
        Mailman mailman = areaOfItem.findMailmanById(mailmanId);
        if (mailman == null) {
            return false; // mailman not in the same area
        }

        // check if already assigned
        if (item.getAssignedMailman() != null) {
            return false; // already assigned
        }

        // perform assignment
        item.setAssignedMailman(mailman);
        return true;
    }

    /**
     * Retrieves all registered mail deliveries for a given geographical area.
     * Each entry contains the mail item, the responsible mailman and the addressee.
     *
     * @param areaId the identifier of the geographical area
     * @return a list of {@link DeliveryInfo}; empty list if there are no deliveries
     */
    public List<DeliveryInfo> getDeliveriesByArea(String areaId) {
        GeographicalArea area = findAreaById(areaId);
        if (area == null) {
            return Collections.emptyList();
        }

        List<DeliveryInfo> result = new ArrayList<>();
        for (MailItem mi : area.getMailItems()) {
            if (mi.getAssignedMailman() != null) {
                result.add(new DeliveryInfo(mi, mi.getAssignedMailman(), mi.getAddressee()));
            }
        }
        return result;
    }

    /**
     * Adds a new inhabitant to a geographical area.
     *
     * @param areaId      the identifier of the area
     * @param inhabitant  the inhabitant to add (its {@code area} field will be set automatically)
     * @return {@code true} if the inhabitant was added; {@code false} if the area does not exist
     */
    public boolean addInhabitant(String areaId, Inhabitant inhabitant) {
        GeographicalArea area = findAreaById(areaId);
        if (area == null) {
            return false;
        }
        inhabitant.setArea(area);
        area.getInhabitants().add(inhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from a geographical area.
     * All registered mail whose addressee is that inhabitant and which has already been
     * assigned to a mailman are deleted.
     *
     * @param areaId       the identifier of the area
     * @param inhabitantId the identifier of the inhabitant to remove
     * @return {@code true} if the inhabitant existed and was removed; {@code false} otherwise
     */
    public boolean removeInhabitant(String areaId, String inhabitantId) {
        GeographicalArea area = findAreaById(areaId);
        if (area == null) {
            return false;
        }

        Inhabitant target = area.findInhabitantById(inhabitantId);
        if (target == null) {
            return false;
        }

        // Remove mail items that satisfy the condition
        List<MailItem> toRemove = new ArrayList<>();
        for (MailItem mi : area.getMailItems()) {
            if (mi.getAddressee() != null && Objects.equals(mi.getAddressee().getId(), inhabitantId)
                    && mi.getAssignedMailman() != null) {
                toRemove.add(mi);
            }
        }
        area.getMailItems().removeAll(toRemove);

        // Finally remove the inhabitant
        area.getInhabitants().remove(target);
        return true;
    }

    /**
     * Adds a mailman to a geographical area if not already present.
     *
     * @param areaId  the identifier of the area
     * @param mailman the mailman to add (its {@code area} field will be set automatically)
     * @return {@code true} if the mailman was added; {@code false} if the area does not exist
     *         or the mailman already belongs to the area
     */
    public boolean addMailman(String areaId, Mailman mailman) {
        GeographicalArea area = findAreaById(areaId);
        if (area == null) {
            return false;
        }

        // Check duplication
        for (Mailman m : area.getMailmen()) {
            if (Objects.equals(m.getId(), mailman.getId())) {
                return false; // already present
            }
        }

        mailman.setArea(area);
        area.getMailmen().add(mailman);
        return true;
    }

    /**
     * Removes a mailman from a geographical area.
     * <p>
     * Requirements:
     * <ul>
     *   <li>At least one mailman must remain in the area after removal.</li>
     *   <li>A different existing mailman must be supplied to take over all deliveries.</li>
     *   <li>All deliveries currently assigned to the removed mailman are reassigned to the
     *       replacement mailman.</li>
     * </ul>
     *
     * @param areaId                the identifier of the area
     * @param mailmanIdToRemove     the identifier of the mailman to remove
     * @param replacementMailmanId  the identifier of the mailman that will take over deliveries
     * @return {@code true} if removal and reassignment succeeded; {@code false} otherwise
     */
    public boolean removeMailman(String areaId, String mailmanIdToRemove, String replacementMailmanId) {
        GeographicalArea area = findAreaById(areaId);
        if (area == null) {
            return false;
        }

        Mailman toRemove = area.findMailmanById(mailmanIdToRemove);
        Mailman replacement = area.findMailmanById(replacementMailmanId);

        if (toRemove == null || replacement == null) {
            return false; // either mailman does not exist in the area
        }

        if (area.getMailmen().size() <= 1) {
            return false; // cannot leave area without a mailman
        }

        // Reassign deliveries
        for (MailItem mi : area.getMailItems()) {
            if (toRemove.equals(mi.getAssignedMailman())) {
                mi.setAssignedMailman(replacement);
            }
        }

        // Remove the mailman
        area.getMailmen().remove(toRemove);
        return true;
    }

    /**
     * Lists all registered mail items (letters and parcels) directed to a specified inhabitant.
     *
     * @param inhabitantId the identifier of the inhabitant
     * @return a list of {@link MailItem} directed to the inhabitant; {@code null} if none exist
     */
    public List<MailItem> getMailItemsForInhabitant(String inhabitantId) {
        List<MailItem> result = new ArrayList<>();

        for (GeographicalArea area : areas) {
            for (MailItem mi : area.getMailItems()) {
                if (mi.getAddressee() != null && Objects.equals(mi.getAddressee().getId(), inhabitantId)) {
                    result.add(mi);
                }
            }
        }

        return result.isEmpty() ? null : result;
    }

    // -------------------------------------------------------------------------
    // Helper methods for internal use
    // -------------------------------------------------------------------------

    /**
     * Finds a geographical area by its identifier.
     *
     * @param areaId the identifier of the area
     * @return the {@link GeographicalArea} if found; {@code null} otherwise
     */
    private GeographicalArea findAreaById(String areaId) {
        for (GeographicalArea a : areas) {
            if (Objects.equals(a.getId(), areaId)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Adds a new geographical area to the system.
     *
     * @param area the area to add
     * @return {@code true} if the area was added; {@code false} if an area with the same id already exists
     */
    public boolean addArea(GeographicalArea area) {
        if (findAreaById(area.getId()) != null) {
            return false;
        }
        areas.add(area);
        return true;
    }

    /**
     * Registers a new mail item (letter or parcel) in its addressee's area.
     *
     * @param mailItem the mail item to register
     * @return {@code true} if registration succeeded; {@code false} if the addressee or area does not exist
     */
    public boolean registerMailItem(MailItem mailItem) {
        if (mailItem == null || mailItem.getAddressee() == null) {
            return false;
        }
        GeographicalArea area = mailItem.getArea();
        if (area == null) {
            return false;
        }
        // Ensure the area we have in the service contains this mail item
        GeographicalArea serviceArea = findAreaById(area.getId());
        if (serviceArea == null) {
            return false;
        }
        serviceArea.getMailItems().add(mailItem);
        return true;
    }
}

/* -------------------------------------------------------------------------
 * Example usage (not part of the required library, just for illustration):
 *
 * MailService service = new MailService();
 *
 * GeographicalArea area = new GeographicalArea("A1", "Downtown");
 * service.addArea(area);
 *
 * Inhabitant alice = new Inhabitant("I1", "Alice", null);
 * service.addInhabitant(area.getId(), alice);
 *
 * Mailman bob = new Mailman("M1", "Bob", null);
 * service.addMailman(area.getId(), bob);
 *
 * Letter letter = new Letter("L1", alice, "Hello", "Hi Alice!");
 * service.registerMailItem(letter);
 *
 * service.assignMailItemToMailman("L1", "M1");
 *
 * List<DeliveryInfo> deliveries = service.getDeliveriesByArea(area.getId());
 * ------------------------------------------------------------------------- */