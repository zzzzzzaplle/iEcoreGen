import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GeographicalArea {
    private String name;
    private List<Inhabitant> inhabitants;
    private List<Mailman> mailmen;
    private List<RegisteredMail> registeredMails;

    public GeographicalArea() {
        this.inhabitants = new ArrayList<>();
        this.mailmen = new ArrayList<>();
        this.registeredMails = new ArrayList<>();
    }

    public GeographicalArea(String name) {
        this();
        this.name = name;
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
}

class Inhabitant {
    private String name;
    private GeographicalArea geographicalArea;

    public Inhabitant() {
    }

    public Inhabitant(String name, GeographicalArea geographicalArea) {
        this.name = name;
        this.geographicalArea = geographicalArea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeographicalArea getGeographicalArea() {
        return geographicalArea;
    }

    public void setGeographicalArea(GeographicalArea geographicalArea) {
        this.geographicalArea = geographicalArea;
    }
}

class Mailman {
    private String name;
    private GeographicalArea geographicalArea;

    public Mailman() {
    }

    public Mailman(String name, GeographicalArea geographicalArea) {
        this.name = name;
        this.geographicalArea = geographicalArea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeographicalArea getGeographicalArea() {
        return geographicalArea;
    }

    public void setGeographicalArea(GeographicalArea geographicalArea) {
        this.geographicalArea = geographicalArea;
    }
}

abstract class RegisteredMail {
    private Inhabitant addressee;
    private Mailman mailman;
    private GeographicalArea geographicalArea;

    public RegisteredMail() {
    }

    public RegisteredMail(Inhabitant addressee, Mailman mailman, GeographicalArea geographicalArea) {
        this.addressee = addressee;
        this.mailman = mailman;
        this.geographicalArea = geographicalArea;
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

    public GeographicalArea getGeographicalArea() {
        return geographicalArea;
    }

    public void setGeographicalArea(GeographicalArea geographicalArea) {
        this.geographicalArea = geographicalArea;
    }
}

class Letter extends RegisteredMail {
    public Letter() {
    }

    public Letter(Inhabitant addressee, Mailman mailman, GeographicalArea geographicalArea) {
        super(addressee, mailman, geographicalArea);
    }
}

class Parcel extends RegisteredMail {
    public Parcel() {
    }

    public Parcel(Inhabitant addressee, Mailman mailman, GeographicalArea geographicalArea) {
        super(addressee, mailman, geographicalArea);
    }
}

class MailDeliverySystem {
    private Map<GeographicalArea, List<RegisteredMail>> deliveries;

    public MailDeliverySystem() {
        this.deliveries = new HashMap<>();
    }

    /**
     * Assigns a specific mailman to deliver a registered inhabitant's mail item.
     * The mailman and the inhabitant must belong to the addressee's geographical area.
     * Ensures the mail isn't already assigned to any mailman.
     *
     * @param mail The registered mail to be assigned.
     * @param mailman The mailman to assign to the mail.
     * @return true if the assignment is successful, false otherwise.
     */
    public boolean assignMail(RegisteredMail mail, Mailman mailman) {
        if (mail.getMailman() != null) {
            return false;
        }
        if (!mail.getGeographicalArea().equals(mailman.getGeographicalArea())) {
            return false;
        }
        mail.setMailman(mailman);
        deliveries.computeIfAbsent(mail.getGeographicalArea(), k -> new ArrayList<>()).add(mail);
        return true;
    }

    /**
     * Retrieves all registered mail deliveries for a given geographical area,
     * along with the responsible mailman and the addressees.
     *
     * @param area The geographical area to retrieve deliveries for.
     * @return A list of registered mail deliveries, or an empty list if there are no deliveries.
     */
    public List<RegisteredMail> getDeliveriesForArea(GeographicalArea area) {
        return deliveries.getOrDefault(area, new ArrayList<>());
    }

    /**
     * Adds a new inhabitant to a geographical area.
     *
     * @param inhabitant The inhabitant to add.
     * @param area The geographical area to add the inhabitant to.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean addInhabitant(Inhabitant inhabitant, GeographicalArea area) {
        if (inhabitant.getGeographicalArea() != null) {
            return false;
        }
        inhabitant.setGeographicalArea(area);
        area.getInhabitants().add(inhabitant);
        return true;
    }

    /**
     * Removes an inhabitant from the geographical area.
     * Deletes any registered mail whose addressee is that inhabitant
     * and which has already been assigned to a mailman.
     *
     * @param inhabitant The inhabitant to remove.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean removeInhabitant(Inhabitant inhabitant) {
        if (inhabitant.getGeographicalArea() == null) {
            return false;
        }
        GeographicalArea area = inhabitant.getGeographicalArea();
        area.getInhabitants().remove(inhabitant);
        inhabitant.setGeographicalArea(null);

        List<RegisteredMail> mailsToRemove = new ArrayList<>();
        for (RegisteredMail mail : area.getRegisteredMails()) {
            if (mail.getAddressee().equals(inhabitant) && mail.getMailman() != null) {
                mailsToRemove.add(mail);
            }
        }
        area.getRegisteredMails().removeAll(mailsToRemove);
        return true;
    }

    /**
     * Adds a mailman to a geographical area if they're not already assigned.
     *
     * @param mailman The mailman to add.
     * @param area The geographical area to add the mailman to.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean addMailman(Mailman mailman, GeographicalArea area) {
        if (mailman.getGeographicalArea() != null) {
            return false;
        }
        mailman.setGeographicalArea(area);
        area.getMailmen().add(mailman);
        return true;
    }

    /**
     * Removes a mailman from a geographical area.
     * Keeps at least one mailman in the area.
     * Specifies a different, existing mailman to take over deliveries.
     * Successfully reassigns all mail before removal.
     *
     * @param mailmanToRemove The mailman to remove.
     * @param newMailman The new mailman to take over deliveries.
     * @param area The geographical area to remove the mailman from.
     * @return true if the operation is successful, false otherwise.
     */
    public boolean removeMailman(Mailman mailmanToRemove, Mailman newMailman, GeographicalArea area) {
        if (area.getMailmen().size() <= 1) {
            return false;
        }
        if (!area.getMailmen().contains(newMailman)) {
            return false;
        }

        List<RegisteredMail> mailsToReassign = new ArrayList<>();
        for (RegisteredMail mail : area.getRegisteredMails()) {
            if (mail.getMailman().equals(mailmanToRemove)) {
                mailsToReassign.add(mail);
            }
        }

        for (RegisteredMail mail : mailsToReassign) {
            if (!assignMail(mail, newMailman)) {
                return false;
            }
        }

        area.getMailmen().remove(mailmanToRemove);
        mailmanToRemove.setGeographicalArea(null);
        return true;
    }

    /**
     * Lists all registered mail items directed to a specified inhabitant.
     * Includes only mail items that specify the given inhabitant as the addressee.
     *
     * @param inhabitant The inhabitant to list mail items for.
     * @return A list of registered mail items, or null if none exist.
     */
    public List<RegisteredMail> listMailItemsForInhabitant(Inhabitant inhabitant) {
        List<RegisteredMail> mailItems = new ArrayList<>();
        for (List<RegisteredMail> mails : deliveries.values()) {
            for (RegisteredMail mail : mails) {
                if (mail.getAddressee().equals(inhabitant)) {
                    mailItems.add(mail);
                }
            }
        }
        return mailItems.isEmpty() ? null : mailItems;
    }
}