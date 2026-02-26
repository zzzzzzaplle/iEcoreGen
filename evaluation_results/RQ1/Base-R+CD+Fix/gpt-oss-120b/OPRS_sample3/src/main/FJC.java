import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a generic user of the review system.
 */
 class User {

    /** The name of the user. */
    private String name;

    /** The roles assigned to the user (Author, Reviewer, CoChair, ...). */
    private List<UserRole> roles;

    /** No‑argument constructor required for the task. */
    public User() {
        this.roles = new ArrayList<>();
    }

    /** Getter for name. */
    public String getName() {
        return name;
    }

    /** Setter for name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for the list of roles (modifiable). */
    public List<UserRole> getRoles() {
        return roles;
    }

    /** Setter for the list of roles. */
    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    /**
     * Adds a role to this user.
     *
     * @param role the role to add
     */
    public void addRole(UserRole role) {
        if (role != null && !roles.contains(role)) {
            roles.add(role);
        }
    }
}

/**
 * Base class for all specific user roles.
 */
abstract class UserRole {
    // No fields – serves as a common super‑type.
}

/**
 * Role representing an author that can submit papers.
 */
class Author extends UserRole {

    /** All papers submitted by this author. */
    private List<Paper> papers;

    /** Keeps track of all created authors (used by static queries). */
    private static final List<Author> ALL_AUTHORS = new ArrayList<>();

    /** No‑argument constructor required for the task. */
    public Author() {
        this.papers = new ArrayList<>();
        ALL_AUTHORS.add(this);
    }

    /** Getter for the list of papers. */
    public List<Paper> getPapers() {
        return papers;
    }

    /** Setter for the list of papers. */
    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    /**
     * Submits a paper on behalf of the author.
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
     * Acceptance rate = (number of papers with decision ACCEPT) / (total papers)
     *
     * @return acceptance rate between 0.0 and 1.0; 0.0 if author has no papers
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
     * Finds all authors whose every submitted paper has only ACCEPT reviews
     * (i.e. each paper's reviews are exclusively positive).
     *
     * @return list of authors meeting the criterion
     */
    public static List<Author> findAuthorsWithPositiveReviews() {
        List<Author> result = new ArrayList<>();
        for (Author a : ALL_AUTHORS) {
            boolean allPositive = true;
            for (Paper p : a.getPapers()) {
                if (!p.isAllReviewsPositive()) {
                    allPositive = false;
                    break;
                }
            }
            if (allPositive) {
                result.add(a);
            }
        }
        return result;
    }

    /**
     * Returns an unmodifiable view of all authors created so far.
     *
     * @return list of all authors
     */
    public static List<Author> getAllAuthors() {
        return Collections.unmodifiableList(ALL_AUTHORS);
    }
}

/**
 * Role representing a reviewer that can be assigned review tasks.
 */
class Reviewer extends UserRole {

    /** Review assignments given to this reviewer. */
    private List<ReviewAssignment> assignments;

    /** No‑argument constructor required for the task. */
    public Reviewer() {
        this.assignments = new ArrayList<>();
    }

    /** Getter for the list of assignments. */
    public List<ReviewAssignment> getReviewAssignments() {
        return assignments;
    }

    /** Setter for the list of assignments. */
    public void setReviewAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
    }

    /**
     * Calculates the number of reviews that have not yet been submitted
     * (i.e., assignments whose grade is UNDECIDED).
     *
     * @return number of unsubmitted reviews; 0 if all are submitted
     */
    public int calculateUnsubmittedReviews() {
        int count = 0;
        for (ReviewAssignment ra : assignments) {
            if (ra.getGrade() == Grade.UNDECIDED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the acceptance proportion for the reviews that have been submitted.
     * Accept = 1, Reject = 0. UNDECIDED grades are ignored.
     *
     * @return average score between 0.0 and 1.0; 0.0 if no submitted reviews exist
     */
    public double calculateAcceptanceProportion() {
        int submitted = 0;
        int acceptCount = 0;
        for (ReviewAssignment ra : assignments) {
            if (ra.getGrade() == Grade.ACCEPT) {
                submitted++;
                acceptCount++;
            } else if (ra.getGrade() == Grade.REJECT) {
                submitted++;
            }
        }
        if (submitted == 0) {
            return 0.0;
        }
        return (double) acceptCount / submitted;
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
}

/**
 * Role representing a co‑chair (or program chair) that can make final decisions.
 */
class CoChair extends UserRole {

    /**
     * Makes the final decision for a paper.
     *
     * @param paper   the paper whose decision is to be set
     * @param decision the final decision (ACCEPT or REJECT)
     * @return 1 if decision was set successfully, 0 otherwise
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (paper == null || decision == null
                || decision == Grade.UNDECIDED) {
            return 0;
        }
        paper.setDecision(decision);
        return 1;
    }
}

/**
 * Enumeration of paper types.
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE
}

/**
 * Enumeration of possible grades for a review.
 */
enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

/**
 * Represents a submitted paper.
 */
class Paper {

    /** Title of the paper. */
    private String title;

    /** Type of the paper (research or experience). */
    private PaperType type;

    /** Final decision made by the chair (initially UNDECIDED). */
    private Grade decision;

    /** All review assignments associated with this paper. */
    private List<ReviewAssignment> reviews;

    /** No‑argument constructor required for the task. */
    public Paper() {
        this.reviews = new ArrayList<>();
        this.decision = Grade.UNDECIDED;
    }

    /** Getter for title. */
    public String getTitle() {
        return title;
    }

    /** Setter for title. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Getter for type. */
    public PaperType getType() {
        return type;
    }

    /** Setter for type. */
    public void setType(PaperType type) {
        this.type = type;
    }

    /** Getter for decision. */
    public Grade getDecision() {
        return decision;
    }

    /** Setter for decision. */
    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    /** Getter for the list of reviews. */
    public List<ReviewAssignment> getReviews() {
        return reviews;
    }

    /** Setter for the list of reviews. */
    public void setReviews(List<ReviewAssignment> reviews) {
        this.reviews = reviews;
    }

    /**
     * Adds a review assignment to this paper.
     *
     * @param review the review assignment to add
     */
    public void addReview(ReviewAssignment review) {
        if (review != null && !reviews.contains(review)) {
            reviews.add(review);
        }
    }

    /**
     * Calculates the acceptance rate based on the grades of the reviews.
     * Acceptance rate = (number of ACCEPT grades) / (total number of reviews)
     *
     * @return acceptance rate between 0.0 and 1.0; 0.0 if there are no reviews
     */
    public double calculateAcceptanceRate() {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        long acceptCount = reviews.stream()
                .filter(r -> r.getGrade() == Grade.ACCEPT)
                .count();
        return (double) acceptCount / reviews.size();
    }

    /**
     * Checks whether all reviews for this paper are exclusively ACCEPT.
     *
     * @return true if every review has grade ACCEPT; false otherwise (including
     *         the case of no reviews)
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
            return false;
        }
        for (ReviewAssignment ra : reviews) {
            if (ra.getGrade() != Grade.ACCEPT) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Represents a single review assignment (feedback + grade).
 */
class ReviewAssignment {

    /** Textual feedback provided by the reviewer. */
    private String feedback;

    /** Grade assigned by the reviewer (initially UNDECIDED). */
    private Grade grade;

    /** No‑argument constructor required for the task. */
    public ReviewAssignment() {
        this.grade = Grade.UNDECIDED;
    }

    /** Getter for feedback. */
    public String getFeedback() {
        return feedback;
    }

    /** Setter for feedback. */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /** Getter for grade. */
    public Grade getGrade() {
        return grade;
    }

    /** Setter for grade. */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}