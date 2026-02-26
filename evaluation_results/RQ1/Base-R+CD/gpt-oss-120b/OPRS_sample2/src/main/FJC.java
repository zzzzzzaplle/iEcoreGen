import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a generic user of the review system.
 */
 class User {
    private String name;
    private List<UserRole> roles;

    /** No‑argument constructor initializing the role list. */
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

    /** Getter for the list of roles. */
    public List<UserRole> getRoles() {
        return roles;
    }

    /** Setter for the list of roles (replaces the current list). */
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
    /** No‑argument constructor. */
    public UserRole() {
    }
}

/**
 * Role representing an author. An author can submit many papers.
 */
class Author extends UserRole {
    private List<Paper> papers;

    /** No‑argument constructor initializing the paper list. */
    public Author() {
        this.papers = new ArrayList<>();
    }

    /** Getter for the list of submitted papers. */
    public List<Paper> getPapers() {
        return papers;
    }

    /** Setter for the list of submitted papers (replaces the current list). */
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
     * Counts how many papers the author has submitted.
     *
     * @return the number of submitted papers
     */
    public int countSubmittedPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of the author's papers.
     * Acceptance rate = (number of papers with decision ACCEPT) / (total submitted papers).
     *
     * @return a value between 0.0 and 1.0; 0.0 if the author has not submitted any paper
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
     * Finds all authors (from the supplied list) whose every submitted paper has only
     * positive (ACCEPT) reviews. This helper method is static because it does not depend on a particular author instance.
     *
     * @param authors the list of authors to search
     * @return a list of authors that satisfy the condition; never {@code null}
     */
    public static List<Author> findAuthorsWithPositiveReviews(List<Author> authors) {
        if (authors == null) {
            return Collections.emptyList();
        }
        List<Author> result = new ArrayList<>();
        for (Author a : authors) {
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
}

/**
 * Role representing a reviewer. A reviewer receives many review assignments.
 */
class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments;

    /** No‑argument constructor initializing the assignment list. */
    public Reviewer() {
        this.assignments = new ArrayList<>();
    }

    /** Getter for the list of review assignments. */
    public List<ReviewAssignment> getAssignments() {
        return assignments;
    }

    /** Setter for the list of review assignments (replaces the current list). */
    public void setAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
    }

    /**
     * Returns the list of review assignments for this reviewer.
     *
     * @return an unmodifiable view of the assignments list
     */
    public List<ReviewAssignment> getReviewAssignments() {
        return Collections.unmodifiableList(assignments);
    }

    /**
     * Calculates the number of reviews that are still unsubmitted (grade = UNDECIDED).
     *
     * @return the number of unsubmitted reviews; 0 if all have been submitted
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
     * Calculates the historical acceptance proportion of this reviewer.
     * Accept = 1, Reject = 0; UNDECIDED grades are ignored.
     *
     * @return a value between 0.0 and 1.0; 0.0 if the reviewer has no submitted reviews
     */
    public double calculateAcceptanceProportion() {
        int submitted = 0;
        int accepted = 0;
        for (ReviewAssignment ra : assignments) {
            if (ra.getGrade() == Grade.ACCEPT) {
                accepted++;
                submitted++;
            } else if (ra.getGrade() == Grade.REJECT) {
                submitted++;
            }
        }
        if (submitted == 0) {
            return 0.0;
        }
        return (double) accepted / submitted;
    }
}

/**
 * Role representing a co‑chair (program chair). The chair makes final decisions on papers.
 */
class CoChair extends UserRole {
    /** No‑argument constructor. */
    public CoChair() {
    }

    /**
     * Makes the final decision on a paper if the paper's reviews are consistent
     * (all ACCEPT or all REJECT). The method sets the paper's decision and returns
     * {@code 1} on success, {@code 0} otherwise.
     *
     * @param paper    the paper to decide upon
     * @param decision the final decision (must be ACCEPT or REJECT)
     * @return {@code 1} if the decision was recorded; {@code 0} if the reviews are not consistent
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        Objects.requireNonNull(paper, "paper must not be null");
        Objects.requireNonNull(decision, "decision must not be null");
        if (decision == Grade.UNDECIDED) {
            return 0;
        }
        if (!paper.isAllReviewsConsistent()) {
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
 * Enumeration of possible grades for a review or final decision.
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
    private String title;
    private PaperType type;
    private Grade decision;
    private List<ReviewAssignment> reviews;

    /** No‑argument constructor initializing fields and the review list. */
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

    /** Setter for the list of reviews (replaces the current list). */
    public void setReviews(List<ReviewAssignment> reviews) {
        this.reviews = reviews;
    }

    /**
     * Adds a review assignment to this paper.
     *
     * @param assignment the review assignment to add
     */
    public void addReview(ReviewAssignment assignment) {
        if (assignment != null && !reviews.contains(assignment)) {
            reviews.add(assignment);
        }
    }

    /**
     * Calculates the acceptance rate based on the submitted reviews.
     * Acceptance rate = (number of ACCEPT grades) / (total submitted reviews).
     *
     * @return a value between 0.0 and 1.0; 0.0 if there are no submitted reviews
     */
    public double calculateAcceptanceRate() {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        long accepted = reviews.stream()
                .filter(r -> r.getGrade() == Grade.ACCEPT)
                .count();
        long submitted = reviews.stream()
                .filter(r -> r.getGrade() != Grade.UNDECIDED)
                .count();
        if (submitted == 0) {
            return 0.0;
        }
        return (double) accepted / submitted;
    }

    /**
     * Checks whether **all** reviews for this paper are positive (i.e., have grade ACCEPT).
     *
     * @return {@code true} if every review has grade ACCEPT; {@code false} otherwise
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

    /**
     * Checks whether all submitted reviews are consistent, i.e., they are
     * either all ACCEPT or all REJECT (no UNDECIDED grades allowed).
     *
     * @return {@code true} if the reviews are consistent; {@code false} otherwise
     */
    public boolean isAllReviewsConsistent() {
        Grade firstNonUndecided = null;
        for (ReviewAssignment ra : reviews) {
            Grade g = ra.getGrade();
            if (g == Grade.UNDECIDED) {
                return false;
            }
            if (firstNonUndecided == null) {
                firstNonUndecided = g;
            } else if (g != firstNonUndecided) {
                return false;
            }
        }
        // If there were no reviews at all, we consider it not consistent.
        return firstNonUndecided != null;
    }
}

/**
 * Represents a review assignment given to a reviewer.
 */
class ReviewAssignment {
    private String feedback;
    private Grade grade;

    /** No‑argument constructor initializing the grade to UNDECIDED. */
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