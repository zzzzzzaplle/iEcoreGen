import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a system user. A user can have multiple roles such as Author,
 * Reviewer or CoChair.
 */
 class User {

    private String name;
    private List<UserRole> roles = new ArrayList<>();

    /** Default constructor */
    public User() {
    }

    /**
     * Constructs a user with a given name.
     *
     * @param name the name of the user
     */
    public User(String name) {
        this.name = name;
    }

    /** @return the name of the user */
    public String getName() {
        return name;
    }

    /** @param name the name to set */
    public void setName(String name) {
        this.name = name;
    }

    /** @return an unmodifiable view of the user roles */
    public List<UserRole> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    /** @param roles the list of roles to set */
    public void setRoles(List<UserRole> roles) {
        this.roles = roles == null ? new ArrayList<>() : new ArrayList<>(roles);
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
 * Marker abstract class for all possible user roles.
 */
abstract class UserRole {
    // No fields – serves only as a common superclass.
}

/**
 * Author role. An author can submit papers and query statistics about them.
 */
class Author extends UserRole {

    private static final List<Author> ALL_AUTHORS = new ArrayList<>();

    private List<Paper> papers = new ArrayList<>();

    /** Default constructor – registers the author globally. */
    public Author() {
        ALL_AUTHORS.add(this);
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
     * @return the number of submitted papers
     */
    public int countSubmittedPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate for this author.
     * Acceptance rate = (number of accepted papers) / (total submitted papers).
     *
     * @return a value between 0.0 and 1.0; 0.0 if no papers have been submitted
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
     * Finds all authors (including this one) that have at least one paper whose
     * reviews are exclusively ACCEPT.
     *
     * @return list of authors with positively reviewed papers
     */
    public static List<Author> findAuthorsWithPositiveReviews() {
        List<Author> result = new ArrayList<>();
        for (Author a : ALL_AUTHORS) {
            for (Paper p : a.getPapers()) {
                if (!p.getReviews().isEmpty() && p.isAllReviewsPositive() && p.isAllAccepts()) {
                    result.add(a);
                    break;
                }
            }
        }
        return result;
    }

    /** @return the list of papers submitted by this author */
    public List<Paper> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    /** @param papers the list of papers to set */
    public void setPapers(List<Paper> papers) {
        this.papers = papers == null ? new ArrayList<>() : new ArrayList<>(papers);
    }
}

/**
 * Reviewer role. A reviewer receives assignments and can evaluate them.
 */
class Reviewer extends UserRole {

    private List<ReviewAssignment> assignments = new ArrayList<>();

    /** Default constructor */
    public Reviewer() {
    }

    /**
     * Returns the list of review assignments given to this reviewer.
     *
     * @return unmodifiable list of assignments
     */
    public List<ReviewAssignment> getReviewAssignments() {
        return Collections.unmodifiableList(assignments);
    }

    /** @param assignments the list of assignments to set */
    public void setReviewAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments == null ? new ArrayList<>() : new ArrayList<>(assignments);
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     * A review is considered unsubmitted when its grade is UNDECIDED.
     *
     * @return number of assignments with grade UNDECIDED (0 if none)
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
     * Calculates the average acceptance proportion of the reviews this reviewer
     * has already submitted. Accept = 1, Reject = 0. UNDECIDED reviews are ignored.
     *
     * @return a value between 0.0 and 1.0; 0.0 if no submitted reviews exist
     */
    public double calculateAcceptanceProportion() {
        int submitted = 0;
        int acceptCount = 0;
        for (ReviewAssignment ra : assignments) {
            Grade g = ra.getGrade();
            if (g == Grade.ACCEPT) {
                submitted++;
                acceptCount++;
            } else if (g == Grade.REJECT) {
                submitted++;
            }
        }
        if (submitted == 0) {
            return 0.0;
        }
        return (double) acceptCount / submitted;
    }
}

/**
 * CoChair role. The CoChair makes the final decision on a paper.
 */
class CoChair extends UserRole {

    /** Default constructor */
    public CoChair() {
    }

    /**
     * Makes the final decision for a paper.
     * The decision can only be recorded if all reviews are exclusively ACCEPT
     * or exclusively REJECT (i.e., homogeneous). Otherwise an IllegalStateException
     * is thrown.
     *
     * @param paper    the paper for which the decision is made
     * @param decision the final decision (ACCEPT or REJECT)
     * @return 1 if the decision was successfully recorded, 0 otherwise
     * @throws IllegalArgumentException if the decision is UNDECIDED or null
     * @throws IllegalStateException    if the paper's reviews are not homogeneous
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (paper == null) {
            throw new IllegalArgumentException("Paper cannot be null.");
        }
        if (decision == null || decision == Grade.UNDECIDED) {
            throw new IllegalArgumentException("Decision must be ACCEPT or REJECT.");
        }
        if (!paper.isAllReviewsPositive()) {
            throw new IllegalStateException("Cannot make final decision: reviews are not homogeneous.");
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
 * Represents a paper submitted to the system.
 */
class Paper {

    private String title;
    private PaperType type;
    private Grade decision = Grade.UNDECIDED;
    private List<ReviewAssignment> reviews = new ArrayList<>();

    /** Default constructor */
    public Paper() {
    }

    /**
     * Constructs a paper with title and type.
     *
     * @param title the title of the paper
     * @param type  the paper type
     */
    public Paper(String title, PaperType type) {
        this.title = title;
        this.type = type;
    }

    /** @return the paper title */
    public String getTitle() {
        return title;
    }

    /** @param title the title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the paper type */
    public PaperType getType() {
        return type;
    }

    /** @param type the type to set */
    public void setType(PaperType type) {
        this.type = type;
    }

    /** @return the final decision for the paper */
    public Grade getDecision() {
        return decision;
    }

    /** @param decision the decision to set */
    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    /** @return unmodifiable list of review assignments */
    public List<ReviewAssignment> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    /** @param reviews the list of reviews to set */
    public void setReviews(List<ReviewAssignment> reviews) {
        this.reviews = reviews == null ? new ArrayList<>() : new ArrayList<>(reviews);
    }

    /**
     * Adds a review assignment to this paper.
     *
     * @param review the review to add
     */
    public void addReview(ReviewAssignment review) {
        if (review != null) {
            reviews.add(review);
        }
    }

    /**
     * Calculates the acceptance rate for this paper based on its reviews.
     * Acceptance rate = (number of ACCEPT grades) / (total submitted reviews).
     *
     * @return a value between 0.0 and 1.0; 0.0 if there are no submitted reviews
     */
    public double calculateAcceptanceRate() {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        long acceptCount = reviews.stream()
                .filter(r -> r.getGrade() == Grade.ACCEPT)
                .count();
        long submitted = reviews.stream()
                .filter(r -> r.getGrade() != Grade.UNDECIDED)
                .count();
        if (submitted == 0) {
            return 0.0;
        }
        return (double) acceptCount / submitted;
    }

    /**
     * Determines whether all reviews for this paper are homogeneous,
     * i.e., either all ACCEPT or all REJECT.
     *
     * @return true if all submitted reviews share the same grade (Accept or Reject),
     * false otherwise or if there are no submitted reviews
     */
    public boolean isAllReviewsPositive() {
        Grade first = null;
        for (ReviewAssignment ra : reviews) {
            Grade g = ra.getGrade();
            if (g == Grade.UNDECIDED) {
                continue; // ignore undecided reviews
            }
            if (first == null) {
                first = g;
            } else if (g != first) {
                return false; // mixed grades
            }
        }
        // If we never found a submitted grade, treat as not homogeneous
        return first != null;
    }

    /**
     * Helper method to check if all submitted reviews are ACCEPT.
     *
     * @return true if every submitted review is ACCEPT, false otherwise
     */
    public boolean isAllAccepts() {
        for (ReviewAssignment ra : reviews) {
            Grade g = ra.getGrade();
            if (g == Grade.UNDECIDED) {
                continue;
            }
            if (g != Grade.ACCEPT) {
                return false;
            }
        }
        return true;
    }
}

/**
 * Represents a review assignment given to a reviewer.
 */
class ReviewAssignment {

    private String feedback;
    private Grade grade = Grade.UNDECIDED;

    /** Default constructor */
    public ReviewAssignment() {
    }

    /**
     * Constructs a review assignment with feedback and grade.
     *
     * @param feedback the textual feedback
     * @param grade    the grade (default UNDECIDED if null)
     */
    public ReviewAssignment(String feedback, Grade grade) {
        this.feedback = feedback;
        this.grade = grade == null ? Grade.UNDECIDED : grade;
    }

    /** @return the feedback text */
    public String getFeedback() {
        return feedback;
    }

    /** @param feedback the feedback to set */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /** @return the grade */
    public Grade getGrade() {
        return grade;
    }

    /** @param grade the grade to set */
    public void setGrade(Grade grade) {
        this.grade = grade == null ? Grade.UNDECIDED : grade;
    }
}