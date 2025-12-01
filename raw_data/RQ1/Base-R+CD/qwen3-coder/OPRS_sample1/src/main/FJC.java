import java.util.*;

/**
 * Represents a user in the review system.
 */
class User {
    private String name;
    private List<UserRole> roles;

    public User() {
        this.roles = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public void addRole(UserRole role) {
        this.roles.add(role);
    }
}

/**
 * Abstract base class for different user roles.
 */
abstract class UserRole {
    public UserRole() {
    }
}

/**
 * Represents an author who can submit papers.
 */
class Author extends UserRole {
    private List<Paper> papers;

    public Author() {
        this.papers = new ArrayList<>();
    }

    public List<Paper> getPapers() {
        return papers;
    }

    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    /**
     * Submits a paper by adding it to the author's list of papers.
     *
     * @param paper The paper to be submitted.
     */
    public void submitPaper(Paper paper) {
        this.papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by this author.
     *
     * @return The number of papers submitted.
     */
    public int countSubmittedPapers() {
        return this.papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for this author.
     * Acceptance rate = (number of accepted papers) / (total papers submitted).
     *
     * @return The acceptance rate as a double between 0.0 and 1.0.
     */
    public double calculateAcceptanceRate() {
        if (papers.isEmpty()) {
            return 0.0;
        }

        int acceptedCount = 0;
        for (Paper paper : papers) {
            if (paper.getDecision() == Grade.ACCEPT) {
                acceptedCount++;
            }
        }

        return (double) acceptedCount / papers.size();
    }

    /**
     * Finds authors who have received at least one positive review across all their papers.
     *
     * @return A list of authors with positive reviews.
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // This method would need access to all authors in the system
        // For now, returning an empty list as it's not fully specified how to access all authors
        return new ArrayList<>();
    }
}

/**
 * Represents a reviewer who can review papers.
 */
class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments;

    public Reviewer() {
        this.assignments = new ArrayList<>();
    }

    public List<ReviewAssignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
    }

    public List<ReviewAssignment> getReviewAssignments() {
        return assignments;
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     * A review is considered unsubmitted if its grade is UNDECIDED.
     *
     * @return The count of unsubmitted reviews.
     */
    public int calculateUnsubmittedReviews() {
        int count = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.UNDECIDED) {
                count++;
            }
        }
        return count;
    }

    /**
     * Converts a reviewer's historical grades into a numerical average score.
     * Accept = 1, Reject = 0.
     *
     * @return The average score between 0.0 and 1.0.
     */
    public double calculateAcceptanceProportion() {
        if (assignments.isEmpty()) {
            return 0.0;
        }

        int totalScore = 0;
        int validReviews = 0;

        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.ACCEPT) {
                totalScore += 1;
                validReviews++;
            } else if (assignment.getGrade() == Grade.REJECT) {
                totalScore += 0;
                validReviews++;
            }
            // UNDECIDED reviews are not counted
        }

        if (validReviews == 0) {
            return 0.0;
        }

        return (double) totalScore / validReviews;
    }
}

/**
 * Represents a co-chair who can make final decisions on papers.
 */
class CoChair extends UserRole {
    public CoChair() {
    }

    /**
     * Makes a final decision on a paper.
     *
     * @param paper    The paper to make a decision on.
     * @param decision The final decision (ACCEPT or REJECT).
     * @return 1 if the decision was successfully made, 0 otherwise.
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (decision == Grade.UNDECIDED) {
            return 0;
        }
        paper.setDecision(decision);
        return 1;
    }
}

/**
 * Enum representing the type of paper.
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE
}

/**
 * Enum representing the grade of a review.
 */
enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

/**
 * Represents a paper submitted for review.
 */
class Paper {
    private String title;
    private PaperType type;
    private Grade decision;
    private List<ReviewAssignment> reviews;

    public Paper() {
        this.reviews = new ArrayList<>();
        this.decision = Grade.UNDECIDED;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PaperType getType() {
        return type;
    }

    public void setType(PaperType type) {
        this.type = type;
    }

    public Grade getDecision() {
        return decision;
    }

    public void setDecision(Grade decision) {
        this.decision = decision;
    }

    public List<ReviewAssignment> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewAssignment> reviews) {
        this.reviews = reviews;
    }

    /**
     * Checks if all reviews for this paper are either exclusively Accept or exclusively Reject.
     *
     * @return true if all reviews have the same grade (excluding UNDECIDED), false otherwise.
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
            return false;
        }

        // Check if all grades are the same and not UNDECIDED
        Grade firstGrade = null;
        for (ReviewAssignment review : reviews) {
            if (review.getGrade() == Grade.UNDECIDED) {
                return false;
            }
            
            if (firstGrade == null) {
                firstGrade = review.getGrade();
            } else if (firstGrade != review.getGrade()) {
                return false;
            }
        }
        
        return firstGrade != null;
    }
}

/**
 * Represents a review assignment for a paper.
 */
class ReviewAssignment {
    private String feedback;
    private Grade grade;

    public ReviewAssignment() {
        this.grade = Grade.UNDECIDED;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}