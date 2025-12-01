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

    /** Default constructor */
    public User() {
        this.roles = new ArrayList<>();
    }

    /** Constructor with name */
    public User(String name) {
        this.name = name;
        this.roles = new ArrayList<>();
    }

    /** Returns the name of the user. */
    public String getName() {
        return name;
    }

    /** Sets the name of the user. */
    public void setName(String name) {
        this.name = name;
    }

    /** Returns an unmodifiable view of the roles attached to the user. */
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
 * Base class for all user roles. It is abstract because a role is never instantiated directly.
 */
abstract class UserRole {
    /** Default constructor */
    public UserRole() {
    }
}

/**
 * Role representing an author. An author can submit many papers.
 */
class Author extends UserRole {

    private List<Paper> papers;

    /** Default constructor */
    public Author() {
        this.papers = new ArrayList<>();
    }

    /** Returns the list of papers submitted by the author. */
    public List<Paper> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    /**
     * Submits a paper on behalf of the author.
     *
     * @param paper the paper to submit; must not be {@code null}
     */
    public void submitPaper(Paper paper) {
        if (paper == null) {
            throw new IllegalArgumentException("Paper cannot be null");
        }
        papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by the author.
     *
     * @return number of submitted papers (may be zero)
     */
    public int countSubmittedPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of the author's papers.
     *
     * @return a value between 0.0 and 1.0 representing accepted papers / total papers,
     *         or 0.0 if the author has not submitted any papers
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
     * Finds all authors (including this one) whose papers have only positive
     * reviews (i.e., every review for every paper is ACCEPT).
     *
     * <p>This method is a placeholder; in a real system it would need access to
     * all authors in the conference. Here it simply checks the current author.
     *
     * @return a list containing this author if all his/her papers have only
     *         ACCEPT reviews, otherwise an empty list
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        for (Paper p : papers) {
            if (!p.isAllReviewsPositive()) {
                return Collections.emptyList();
            }
        }
        List<Author> result = new ArrayList<>();
        result.add(this);
        return result;
    }
}

/**
 * Role representing a reviewer. A reviewer receives assignments to review papers.
 */
class Reviewer extends UserRole {

    private List<ReviewAssignment> assignments;

    /** Default constructor */
    public Reviewer() {
        this.assignments = new ArrayList<>();
    }

    /**
     * Returns the list of review assignments for this reviewer.
     *
     * @return an unmodifiable list of assignments
     */
    public List<ReviewAssignment> getReviewAssignments() {
        return Collections.unmodifiableList(assignments);
    }

    /**
     * Adds a review assignment to the reviewer.
     *
     * @param assignment the assignment to add; must not be {@code null}
     */
    public void addAssignment(ReviewAssignment assignment) {
        if (assignment == null) {
            throw new IllegalArgumentException("Assignment cannot be null");
        }
        assignments.add(assignment);
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     * A review is considered unsubmitted if its grade is {@link Grade#UNDECIDED}.
     *
     * @return number of assignments whose grade is UNDECIDED; returns 0 if all
     *         reviews have been submitted
     */
    public int calculateUnsubmittedReviews() {
        int count = 0;
        for (ReviewAssignment a : assignments) {
            if (a.getGrade() == Grade.UNDECIDED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the proportion of ACCEPT grades among the reviewer's submitted reviews.
     *
     * @return a value between 0.0 and 1.0; returns 0.0 if the reviewer has no submitted reviews
     */
    public double calculateAcceptanceProportion() {
        int submitted = 0;
        int accepted = 0;
        for (ReviewAssignment a : assignments) {
            if (a.getGrade() != Grade.UNDECIDED) {
                submitted++;
                if (a.getGrade() == Grade.ACCEPT) {
                    accepted++;
                }
            }
        }
        if (submitted == 0) {
            return 0.0;
        }
        return (double) accepted / submitted;
    }
}

/**
 * Role representing a co-chair (program chair). The chair makes the final decision on papers.
 */
class CoChair extends UserRole {

    /** Default constructor */
    public CoChair() {
    }

    /**
     * Makes the final decision for a paper.
     *
     * <p>The method first checks that all reviews for the paper are either
     * exclusively ACCEPT or exclusively REJECT (no mixed grades and no UNDECIDED
     * grades). If the check fails, an {@link IllegalStateException} is thrown.
     *
     * @param paper    the paper for which the decision is made; must not be {@code null}
     * @param decision the final decision; must be either ACCEPT or REJECT
     * @return {@code 1} if the decision was successfully recorded; {@code 0} otherwise
     * @throws IllegalArgumentException if {@code paper} or {@code decision} is {@code null}
     * @throws IllegalStateException    if the paper's reviews are not uniformly ACCEPT or REJECT
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (paper == null) {
            throw new IllegalArgumentException("Paper cannot be null");
        }
        if (decision == null || (decision != Grade.ACCEPT && decision != Grade.REJECT)) {
            throw new IllegalArgumentException("Decision must be ACCEPT or REJECT");
        }
        if (!paper.isAllReviewsPositive()) {
            throw new IllegalStateException(
                    "Cannot make final decision: reviews are not uniformly ACCEPT or REJECT");
        }
        paper.setDecision(decision);
        return 1;
    }
}

/**
 * Enumeration for the type of a paper.
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE
}

/**
 * Enumeration for the grade given to a review or final decision.
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
    private Grade decision;
    private List<ReviewAssignment> reviews;

    /** Default constructor */
    public Paper() {
        this.decision = Grade.UNDECIDED;
        this.reviews = new ArrayList<>();
    }

    /** Constructor with mandatory fields */
    public Paper(String title, PaperType type) {
        this.title = title;
        this.type = type;
        this.decision = Grade.UNDECIDED;
        this.reviews = new ArrayList<>();
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

    /** Returns the final decision for the paper (may be UNDECIDED). */
    public Grade getDecision() {
        return decision;
    }

    /** Sets the final decision for the paper. */
    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    /** Returns an unmodifiable view of the review assignments for this paper. */
    public List<ReviewAssignment> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    /**
     * Adds a review assignment to the paper.
     *
     * @param assignment the assignment to add; must not be {@code null}
     */
    public void addReview(ReviewAssignment assignment) {
        if (assignment == null) {
            throw new IllegalArgumentException("ReviewAssignment cannot be null");
        }
        reviews.add(assignment);
    }

    /**
     * Calculates the acceptance rate of the paper based on submitted reviews.
     *
     * @return proportion of ACCEPT grades among submitted reviews; returns 0.0 if no reviews submitted
     */
    public double calculateAcceptanceRate() {
        int submitted = 0;
        int accepted = 0;
        for (ReviewAssignment r : reviews) {
            if (r.getGrade() != Grade.UNDECIDED) {
                submitted++;
                if (r.getGrade() == Grade.ACCEPT) {
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
     * or exclusively REJECT. UNDECIDED grades cause the method to return {@code false}.
     *
     * @return {@code true} if all submitted reviews are the same grade (all ACCEPT or all REJECT);
     *         {@code false} otherwise
     */
    public boolean isAllReviewsPositive() {
        Grade uniformGrade = null;
        for (ReviewAssignment r : reviews) {
            Grade g = r.getGrade();
            if (g == Grade.UNDECIDED) {
                return false; // there exists an unsubmitted review
            }
            if (uniformGrade == null) {
                uniformGrade = g;
            } else if (uniformGrade != g) {
                return false; // mixed grades
            }
        }
        // if there were no reviews at all, we consider it not positive
        return uniformGrade != null;
    }
}

/**
 * Represents an assignment of a reviewer to a paper, containing feedback and a grade.
 */
class ReviewAssignment {

    private String feedback;
    private Grade grade;

    /** Default constructor */
    public ReviewAssignment() {
        this.grade = Grade.UNDECIDED;
    }

    /** Constructor with feedback */
    public ReviewAssignment(String feedback) {
        this.feedback = feedback;
        this.grade = Grade.UNDECIDED;
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

    /**
     * Sets the grade for this review.
     *
     * @param grade the grade to set; must not be {@code null}
     */
    public void setGrade(Grade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("Grade cannot be null");
        }
        this.grade = grade;
    }
}