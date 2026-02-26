import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a generic user of the review system.
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

    /** Returns the user name. */
    public String getName() {
        return name;
    }

    /** Sets the user name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns the list of roles assigned to this user. */
    public List<UserRole> getRoles() {
        return roles;
    }

    /** Sets the list of roles (replaces current list). */
    public void setRoles(List<UserRole> roles) {
        this.roles = roles != null ? roles : new ArrayList<>();
    }

    /**
     * Adds a role to the user.
     *
     * @param role the role to be added
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
    /** No‑arg constructor */
    public UserRole() {
    }
}

/**
 * Role representing an author.
 */
class Author extends UserRole {
    private List<Paper> papers = new ArrayList<>();

    /** No‑arg constructor */
    public Author() {
    }

    /** Returns the list of papers submitted by this author. */
    public List<Paper> getPapers() {
        return papers;
    }

    /** Sets the list of papers (replaces current list). */
    public void setPapers(List<Paper> papers) {
        this.papers = papers != null ? papers : new ArrayList<>();
    }

    /**
     * Submits a new paper on behalf of the author.
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
     * Acceptance rate = (number of papers with decision ACCEPT) / (total submitted papers).
     *
     * @return acceptance rate as a value between 0.0 and 1.0;
     *         returns 0.0 if the author has not submitted any papers
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
     * Finds authors (from the supplied list) that have at least one paper whose
     * reviews are all positive (either all ACCEPT or all REJECT).
     *
     * @param authors list of authors to be inspected
     * @return list of authors meeting the criteria; never {@code null}
     */
    public static List<Author> findAuthorsWithPositiveReviews(List<Author> authors) {
        if (authors == null) {
            return Collections.emptyList();
        }
        List<Author> result = new ArrayList<>();
        for (Author a : authors) {
            for (Paper p : a.getPapers()) {
                if (p.isAllReviewsPositive()) {
                    result.add(a);
                    break; // one qualifying paper is enough
                }
            }
        }
        return result;
    }
}

/**
 * Role representing a reviewer.
 */
class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments = new ArrayList<>();

    /** No‑arg constructor */
    public Reviewer() {
    }

    /** Returns the list of review assignments for this reviewer. */
    public List<ReviewAssignment> getAssignments() {
        return assignments;
    }

    /** Sets the list of assignments (replaces current list). */
    public void setAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments != null ? assignments : new ArrayList<>();
    }

    /**
     * Calculates the number of reviews that are still unsubmitted
     * (i.e., have grade {@link Grade#UNDECIDED}).
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
     * Calculates the average acceptance proportion for the reviewer.
     * Accept = 1, Reject = 0, UNDECIDED reviews are ignored.
     *
     * @return average score between 0.0 and 1.0; returns 0.0 if no submitted reviews
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
            // UNDECIDED is ignored
        }
        if (submitted == 0) {
            return 0.0;
        }
        return (double) acceptCount / submitted;
    }
}

/**
 * Role representing a co‑chair (program chair).
 */
class CoChair extends UserRole {
    /** No‑arg constructor */
    public CoChair() {
    }

    /**
     * Makes the final decision for a paper if all its reviews are exclusive
     * (all ACCEPT or all REJECT). The decision is set on the paper.
     *
     * @param paper   the paper for which to set the decision
     * @param decision the final decision (ACCEPT or REJECT)
     * @return {@code 1} if the decision was applied; {@code 0} otherwise
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        Objects.requireNonNull(paper, "paper must not be null");
        Objects.requireNonNull(decision, "decision must not be null");
        if (paper.isAllReviewsPositive()) {
            paper.setDecision(decision);
            return 1;
        }
        return 0;
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
 * Represents a paper submitted to the conference.
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

    /** Returns the title of the paper. */
    public String getTitle() {
        return title;
    }

    /** Sets the title of the paper. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Returns the type of the paper. */
    public PaperType getType() {
        return type;
    }

    /** Sets the type of the paper. */
    public void setType(PaperType type) {
        this.type = type;
    }

    /** Returns the final decision of the paper. */
    public Grade getDecision() {
        return decision;
    }

    /** Sets the final decision of the paper. */
    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    /** Returns the list of review assignments for this paper. */
    public List<ReviewAssignment> getReviews() {
        return reviews;
    }

    /** Sets the list of reviews (replaces current list). */
    public void setReviews(List<ReviewAssignment> reviews) {
        this.reviews = reviews != null ? reviews : new ArrayList<>();
    }

    /**
     * Calculates the acceptance rate based on the reviews received.
     * Acceptance rate = (number of ACCEPT grades) / (total number of submitted reviews).
     *
     * @return acceptance rate between 0.0 and 1.0; 0.0 if there are no submitted reviews
     */
    public double calculateAcceptanceRate() {
        int submitted = 0;
        int acceptCount = 0;
        for (ReviewAssignment ra : reviews) {
            Grade g = ra.getGrade();
            if (g == Grade.ACCEPT) {
                submitted++;
                acceptCount++;
            } else if (g == Grade.REJECT) {
                submitted++;
            }
            // UNDECIDED reviews are ignored
        }
        if (submitted == 0) {
            return 0.0;
        }
        return (double) acceptCount / submitted;
    }

    /**
     * Determines whether all submitted reviews for this paper are exclusive,
     * i.e., either all ACCEPT or all REJECT, and there is at least one submitted review.
     *
     * @return {@code true} if all submitted reviews share the same grade (excluding UNDECIDED);
     *         {@code false} otherwise
     */
    public boolean isAllReviewsPositive() {
        Grade firstNonUndecided = null;
        for (ReviewAssignment ra : reviews) {
            Grade g = ra.getGrade();
            if (g == Grade.UNDECIDED) {
                continue; // ignore undecided reviews
            }
            if (firstNonUndecided == null) {
                firstNonUndecided = g;
            } else if (g != firstNonUndecided) {
                return false; // mixed grades found
            }
        }
        // If no submitted reviews, we consider the condition not satisfied
        return firstNonUndecided != null;
    }
}

/**
 * Represents a review assignment linking a reviewer to a paper.
 */
class ReviewAssignment {
    private String feedback;
    private Grade grade = Grade.UNDECIDED;

    /** No‑arg constructor */
    public ReviewAssignment() {
    }

    /** Constructor with feedback and grade */
    public ReviewAssignment(String feedback, Grade grade) {
        this.feedback = feedback;
        this.grade = grade != null ? grade : Grade.UNDECIDED;
    }

    /** Returns the feedback text. */
    public String getFeedback() {
        return feedback;
    }

    /** Sets the feedback text. */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /** Returns the grade assigned in the review. */
    public Grade getGrade() {
        return grade;
    }

    /** Sets the grade for this review. */
    public void setGrade(Grade grade) {
        this.grade = grade != null ? grade : Grade.UNDECIDED;
    }
}