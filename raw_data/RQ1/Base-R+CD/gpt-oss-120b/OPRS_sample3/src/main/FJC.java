import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a system user. A user can have multiple roles (author, reviewer, co‑chair).
 */
 class User {

    private String name;
    private List<UserRole> roles = new ArrayList<>();

    /** No‑arg constructor */
    public User() {
    }

    /** Constructor with name */
    public User(String name) {
        this.name = name;
    }

    /** @return the user's name */
    public String getName() {
        return name;
    }

    /** @param name the user's name */
    public void setName(String name) {
        this.name = name;
    }

    /** @return an unmodifiable view of the roles list */
    public List<UserRole> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    /** @param role role to be added to the user */
    public void addRole(UserRole role) {
        if (role != null && !roles.contains(role)) {
            roles.add(role);
        }
    }
}

/**
 * Base class for all user roles.
 */
abstract class UserRole {
    // No fields – serves as a common super‑type.
}

/**
 * Represents an author role. An author can submit papers and query statistics about them.
 */
class Author extends UserRole {

    /** All author instances created – used by static helper methods. */
    private static final List<Author> ALL_AUTHORS = new ArrayList<>();

    private List<Paper> papers = new ArrayList<>();

    /** No‑arg constructor – registers the author in the global list. */
    public Author() {
        ALL_AUTHORS.add(this);
    }

    /** @return the list of papers submitted by this author (modifiable). */
    public List<Paper> getPapers() {
        return papers;
    }

    /**
     * Submits a paper on behalf of this author.
     *
     * @param paper the paper to submit
     */
    public void submitPaper(Paper paper) {
        if (paper != null && !papers.contains(paper)) {
            papers.add(paper);
        }
    }

    /**
     * Counts the total number of papers submitted by this author.
     *
     * @return number of submitted papers
     */
    public int countSubmittedPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of this author's papers.
     * Acceptance rate = (number of accepted papers) / (total submitted papers).
     *
     * @return acceptance rate as a value between 0.0 and 1.0; 0.0 if no papers submitted
     */
    public double calculateAcceptanceRate() {
        if (papers.isEmpty()) {
            return 0.0;
        }
        long accepted = papers.stream()
                .filter(p -> p.getDecision() == Grade.ACCEPT)
                .count();
        return (double) accepted / papers.size();
    }

    /**
     * Finds all authors whose every submitted paper has reviews that are uniformly
     * either all ACCEPT or all REJECT (i.e., no mixed or undecided reviews).
     *
     * @return list of authors meeting the above criterion
     */
    public static List<Author> findAuthorsWithPositiveReviews() {
        return ALL_AUTHORS.stream()
                .filter(author -> !author.getPapers().isEmpty())
                .filter(author -> author.getPapers().stream()
                        .allMatch(Paper::isAllReviewsPositive))
                .collect(Collectors.toList());
    }

    /** @return the static list of all authors (read‑only) */
    public static List<Author> getAllAuthors() {
        return Collections.unmodifiableList(ALL_AUTHORS);
    }
}

/**
 * Represents a reviewer role. A reviewer receives review assignments and can
 * evaluate statistics about them.
 */
class Reviewer extends UserRole {

    private List<ReviewAssignment> assignments = new ArrayList<>();

    /** No‑arg constructor */
    public Reviewer() {
    }

    /** @return the list of review assignments (modifiable) */
    public List<ReviewAssignment> getReviewAssignments() {
        return assignments;
    }

    /**
     * Adds a new review assignment to this reviewer.
     *
     * @param assignment the assignment to add
     */
    public void addAssignment(ReviewAssignment assignment) {
        if (assignment != null && !assignments.contains(assignment)) {
            assignments.add(assignment);
        }
    }

    /**
     * Calculates the number of reviews that are still unsubmitted (grade == UNDECIDED).
     *
     * @return count of unsubmitted reviews; 0 if all are submitted
     */
    public int calculateUnsubmittedReviews() {
        return (int) assignments.stream()
                .filter(a -> a.getGrade() == Grade.UNDECIDED)
                .count();
    }

    /**
     * Calculates the proportion of accepted grades among the reviews that have been submitted.
     * Accept = 1, Reject = 0. Undecided reviews are ignored.
     *
     * @return average acceptance score between 0.0 and 1.0; 0.0 if no submitted reviews
     */
    public double calculateAcceptanceProportion() {
        List<ReviewAssignment> submitted = assignments.stream()
                .filter(a -> a.getGrade() != Grade.UNDECIDED)
                .collect(Collectors.toList());

        if (submitted.isEmpty()) {
            return 0.0;
        }

        long acceptCount = submitted.stream()
                .filter(a -> a.getGrade() == Grade.ACCEPT)
                .count();

        return (double) acceptCount / submitted.size();
    }
}

/**
 * Represents a co‑chair role. The co‑chair can make the final decision on a paper.
 */
class CoChair extends UserRole {

    /** No‑arg constructor */
    public CoChair() {
    }

    /**
     * Makes the final decision on a paper.
     *
     * @param paper   the paper to decide upon
     * @param decision the final decision (ACCEPT or REJECT)
     * @return 1 if the decision was set successfully, 0 otherwise
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (paper == null || decision == null || decision == Grade.UNDECIDED) {
            return 0;
        }
        paper.setDecision(decision);
        return 1;
    }
}

/**
 * Types of papers that can be submitted.
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE
}

/**
 * Possible grades for a review or final decision.
 */
enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

/**
 * Represents a submitted paper with its metadata and associated reviews.
 */
class Paper {

    private String title;
    private PaperType type;
    private Grade decision = Grade.UNDECIDED;
    private List<ReviewAssignment> reviews = new ArrayList<>();

    /** No‑arg constructor */
    public Paper() {
    }

    /** Constructor with title and type */
    public Paper(String title, PaperType type) {
        this.title = title;
        this.type = type;
    }

    /** @return the paper title */
    public String getTitle() {
        return title;
    }

    /** @param title the paper title */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the paper type */
    public PaperType getType() {
        return type;
    }

    /** @param type the paper type */
    public void setType(PaperType type) {
        this.type = type;
    }

    /** @return the final decision for the paper */
    public Grade getDecision() {
        return decision;
    }

    /** @param decision the final decision */
    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    /** @return the list of review assignments (modifiable) */
    public List<ReviewAssignment> getReviews() {
        return reviews;
    }

    /**
     * Adds a review assignment to this paper.
     *
     * @param assignment the review assignment
     */
    public void addReview(ReviewAssignment assignment) {
        if (assignment != null && !reviews.contains(assignment)) {
            reviews.add(assignment);
        }
    }

    /**
     * Calculates the acceptance rate of the paper based on its reviews.
     * Acceptance rate = (number of ACCEPT grades) / (total submitted reviews).
     *
     * @return acceptance rate between 0.0 and 1.0; 0.0 if no submitted reviews
     */
    public double calculateAcceptanceRate() {
        List<ReviewAssignment> submitted = reviews.stream()
                .filter(a -> a.getGrade() != Grade.UNDECIDED)
                .collect(Collectors.toList());

        if (submitted.isEmpty()) {
            return 0.0;
        }

        long acceptCount = submitted.stream()
                .filter(a -> a.getGrade() == Grade.ACCEPT)
                .count();

        return (double) acceptCount / submitted.size();
    }

    /**
     * Checks whether all reviews for this paper are uniformly ACCEPT or uniformly REJECT.
     * Reviews that are still UNDECIDED make the method return {@code false}.
     *
     * @return {@code true} if all submitted reviews share the same definitive grade,
     *         {@code false} otherwise
     */
    public boolean isAllReviewsPositive() {
        // Filter out undecided reviews
        List<Grade> definitiveGrades = reviews.stream()
                .map(ReviewAssignment::getGrade)
                .filter(g -> g != Grade.UNDECIDED)
                .collect(Collectors.toList());

        if (definitiveGrades.isEmpty()) {
            return false; // no decisive reviews yet
        }

        Grade first = definitiveGrades.get(0);
        for (Grade g : definitiveGrades) {
            if (g != first) {
                return false; // mixed grades
            }
        }
        // All grades are the same (either all ACCEPT or all REJECT)
        return true;
    }
}

/**
 * Represents an individual review assignment given to a reviewer.
 */
class ReviewAssignment {

    private String feedback;
    private Grade grade = Grade.UNDECIDED;

    /** No‑arg constructor */
    public ReviewAssignment() {
    }

    /** Constructor with feedback */
    public ReviewAssignment(String feedback) {
        this.feedback = feedback;
    }

    /** @return the review feedback */
    public String getFeedback() {
        return feedback;
    }

    /** @param feedback the review feedback */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /** @return the grade assigned in this review */
    public Grade getGrade() {
        return grade;
    }

    /** @param grade the grade to set */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}