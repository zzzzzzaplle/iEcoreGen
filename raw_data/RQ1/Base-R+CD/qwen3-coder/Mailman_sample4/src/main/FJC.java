import java.util.ArrayList;
import java.util.List;

/**
 * Represents a mailman responsible for delivering registered mail within a geographical area.
 */
class Mailman {
    
    public Mailman() {
        // Default constructor
    }
}

/**
 * Represents a geographical area containing inhabitants, mailmen, and registered mails.
 */
class GeographicalArea {
    private List<Mailman> mailmen;
    private List<Inhabitant> inhabitants;
    private List<RegisteredMail> allMails;

    /**
     * Constructs a new GeographicalArea with empty lists.
     */
    public GeographicalArea() {
        this.mailmen = new ArrayList<>();
        this.inhabitants = new ArrayList<>();
        this.allMails = new ArrayList<>();
    }

    /**
     * Gets the list of mailmen in this geographical area.
     *
     * @return List of mailmen
     */
    public List<Mailman> getMailmen() {
        return mailmen;
    }

    /**
     * Sets the list of mailmen in this geographical area.
     *
     * @param mailmen List of mailmen to set
     */
    public void setMailmen(List<Mailman> mailmen) {
        this.mailmen = mailmen;
    }

    /**
     * Gets the list of inhabitants in this geographical area.
     *
     * @return List of inhabitants
     */
    public List<Inhabitant> getInhabitants() {
        return inhabitants;
    }

    /**
     * Sets the list of inhabitants in this geographical area.
     *
     * @param inhabitants List of inhabitants to set
     */
    public void setInhabitants(List<Inhabitant> inhabitants) {
        this.inhabitants = inhabitants;
    }

    /**
     * Gets the list of all registered mails in this geographical area.
     *
     * @return List of registered mails
     */
    public List<RegisteredMail> getAllMails() {
        return allMails;
    }

    /**
     * Sets the list of all registered mails in this geographical area.
     *
     * @param allMails List of registered mails to set
     */
    public void setAllMails(List<RegisteredMail> allMails) {
        this.allMails = allMails;
    }

    /**
     * Assigns a mailman to deliver a registered mail item to an inhabitant.
     * The mailman and inhabitant must belong to this geographical area,
     * and the mail must not already be assigned to another mailman.
     *
     * @param carrier   The mailman who will deliver the mail
     * @param addressee The inhabitant who will receive the mail
     * @param mail      The registered mail item to be delivered
     * @return true if the assignment was successful, false otherwise
     */
    public boolean assignRegisteredMailDeliver(Mailman carrier, Inhabitant addressee, RegisteredMail mail) {
        // Check if the mailman belongs to this geographical area
        if (!mailmen.contains(carrier)) {
            return false;
        }

        // Check if the inhabitant belongs to this geographical area
        if (!inhabitants.contains(addressee)) {
            return false;