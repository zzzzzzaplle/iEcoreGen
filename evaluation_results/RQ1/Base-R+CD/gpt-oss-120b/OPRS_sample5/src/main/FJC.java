import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a generic user of the review system.
 * A user can have multiple roles (author, reviewer, co‑chair).
 */
 class User {

    private String name;
    private List<UserRole> roles = new ArrayList<>();

    /** No‑argument constructor required by the specification. */
    public User() {
    }

    /** Constructor with name. */
    public User(String name) {
        this.name = name;
    }

    /** Returns the user's name. */
    public String getName() {
        return name;
    }

    /** Sets the user's name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns an unmodifiable view of the user's roles. */
    public List<UserRole> getRoles() {
        return Collections.unmodifiableList(roles);
    }

    /** Adds a role to the user. */
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
    /** No‑argument constructor required by the specification. */
    public UserRole() {
    }
}

/**
 * Role that allows a user to submit papers and query author‑specific statistics.
 */
class Author extends UserRole {

    private List<Paper> papers = new ArrayList<>();

    /** No‑argument constructor required by the specification. */
    public Author() {
    }

    /** Returns the list of papers submitted by this author. */
    public List<Paper> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    /** Adds a paper to this author's collection. */
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
     * @return acceptance rate as a double in the range [0.0, 1.0]; 0.0 if no papers submitted
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
     * Finds all authors (including this one) whose submitted papers have only
     * positive (all ACCEPT) or only negative (all REJECT) reviews.
     *
     * @param allAuthors list containing all authors known to the system
     * @return list of authors satisfying the condition; never {@code null}
     */
    public static List<Author> findAuthorsWithPositiveReviews(List<Author> allAuthors) {
        if (allAuthors == null) {
            return Collections.emptyList();
        }
        List<Author> result = new ArrayList<>();
        for (Author author : allAuthors) {
            boolean allPositiveOrAllNegative = true;
            for (Paper paper : author.getPapers()) {
                if (!paper.isAllReviewsPositive()) {
                    allPositiveOrAllNegative = false;
                    break;
                }
            }
            if (allPositiveOrAllNegative) {
                result.add(author);
            }
        }
        return result;
    }
}

/**
 * Role that allows a user to review assigned papers.
 */
class Reviewer extends UserRole {

    private List<ReviewAssignment> assignments = new ArrayList<>();

    /** No‑argument constructor required by the specification. */
    public Reviewer() {
    }

    /** Returns an unmodifiable list of review assignments for this reviewer. */
    public List<ReviewAssignment> getReviewAssignments() {
        return Collections.unmodifiableList(assignments);
    }

    /** Adds a review assignment to this reviewer. */
    public void addAssignment(ReviewAssignment assignment) {
        if (assignment != null && !assignments.contains(assignment)) {
            assignments.add(assignment);
        }
    }

    /**
     * Calculates the number of reviews that have not yet been submitted (i.e., grade is UNDECIDED).
     *
     * @return number of unsubmitted reviews; 0 if all have been submitted
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
     * Calculates the proportion of ACCEPT grades among the submitted reviews.
     *
     * @return a value between 0.0 and 1.0; 0.0 if there are no submitted reviews
     */
    public double calculateAcceptanceProportion() {
        int submitted = 0;
        int accepted = 0;
        for (ReviewAssignment ra : assignments) {
            if (ra.getGrade() != Grade.UNDECIDED) {
                submitted++;
                if (ra.getGrade() == Grade.ACCEPT) {
                    accepted++;
                }
            }
        }
        if (submitted == 0) {
            return 0.0;
        }
        return (double) accepted / submitted;
    }

    /**
     * Computes the historical average score for this reviewer, where ACCEPT = 1 and REJECT = 0.
     *
     * @return average score in the range [0.0, 1.0]; 0.0 if the reviewer has no graded reviews
     */
    public double calculateHistoricalAverageScore() {
        int graded = 0;
        int scoreSum = 0;
        for (ReviewAssignment ra : assignments) {
            if (ra.getGrade() == Grade.ACCEPT) {
                graded++;
                scoreSum += 1;
            } else if (ra.getGrade() == Grade.REJECT) {
                graded++;
                // scoreSum += 0; // implicit
            }
        }
        if (graded == 0) {
            return 0.0;
        }
        return (double) scoreSum / graded;
    }
}

/**
 * Role that allows a user (the co‑chair) to make the final decision on a paper.
 */
class CoChair extends UserRole {

    /** No‑argument constructor required by the specification. */
    public CoChair() {
    }

    /**
     * Makes the final decision for a given paper.
     *
     * @param paper   the paper for which the decision is made; must not be {@code null}
     * @param decision the final decision (ACCEPT or REJECT); must not be UNDECIDED
     * @return 1 if the decision was set successfully; otherwise 0
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        Objects.requireNonNull(paper, "paper must not be null");
        Objects.requireNonNull(decision, "decision must not be null");
        if (decision == Grade.UNDECIDED) {
            return 0;
        }
        paper.setDecision(decision);
        return 1;
    }
}

/**
 * Represents a paper submitted to the conference.
 */
class Paper {

    private String title;
    private PaperType type;
    private Grade decision = Grade.UNDECIDED;
    private List<ReviewAssignment> reviews = new ArrayList<>();

    /** No‑argument constructor required by the specification. */
    public Paper() {
    }

    /** Constructor with title and type. */
    public Paper(String title, PaperType type) {
        this.title = title;
        this.type = type;
    }

    /** Returns the paper title. */
    public String getTitle() {
        return title;
    }

    /** Sets the paper title. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Returns the paper type. */
    public PaperType getType() {
        return type;
    }

    /** Sets the paper type. */
    public void setType(PaperType type) {
        this.type = type;
    }

    /** Returns the final decision for the paper. */
    public Grade getDecision() {
        return decision;
    }

    /** Sets the final decision for the paper. */
    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    /** Returns an unmodifiable list of review assignments for this paper. */
    public List<ReviewAssignment> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    /** Adds a review assignment to this paper. */
    public void addReview(ReviewAssignment review) {
        if (review != null && !reviews.contains(review)) {
            reviews.add(review);
        }
    }

    /**
     * Calculates the acceptance rate of this paper based on its reviews.
     * Acceptance rate = (number of ACCEPT grades) / (total number of submitted reviews).
     *
     * @return acceptance rate as a double between 0.0 and 1.0; 0.0 if there are no submitted reviews
     */
    public double calculateAcceptanceRate() {
        int submitted = 0;
        int accepted = 0;
        for (ReviewAssignment ra : reviews) {
            if (ra.getGrade() != Grade.UNDECIDED) {
                submitted++;
                if (ra.getGrade() == Grade.ACCEPT) {
                    accepted++;
                }
            }
        }
        if (submitted == 0) {
            return 0.0;
        }
        return (double) accepted / submitted;
    }

    /**
     * Checks whether all reviews for this paper are either exclusively ACCEPT
     * or exclusively REJECT (i.e., no mixed grades and no UNDECIDED grades).
     *
     * @return {@code true} if all reviews share the same final grade (all ACCEPT or all REJECT);
     *         {@code false} otherwise
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
            return false;
        }
        Grade firstDecided = null;
        for (ReviewAssignment ra : reviews) {
            Grade g = ra.getGrade();
            if (g == Grade.UNDECIDED) {
                return false;
            }
            if (firstDecided == null) {
                firstDecided = g;
            } else if (g != firstDecided) {
                return false;
            }
        }
        // At this point all grades are the same and are either ACCEPT or REJECT
        return true;
    }
}

/**
 * Represents a single review assignment (feedback + grade) for a paper.
 */
class ReviewAssignment {

    private String feedback;
    private Grade grade = Grade.UNDECIDED;

    /** No‑argument constructor required by the specification. */
    public ReviewAssignment() {
    }

    /** Constructor with feedback and grade. */
    public ReviewAssignment(String feedback, Grade grade) {
        this.feedback = feedback;
        this.grade = grade;
    }

    /** Returns the feedback text. */
    public String getFeedback() {
        return feedback;
    }

    /** Sets the feedback text. */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /** Returns the grade assigned in this review. */
    public Grade getGrade() {
        return grade;
    }

    /** Sets the grade for this review. */
    public void setGrade(Grade grade) {
        this.grade = grade;
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