import java.util.*;

enum PaperType {
    RESEARCH,
    EXPERIENCE
}

enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

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

abstract class UserRole {
    public UserRole() {
    }
}

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

    public void submitPaper(Paper paper) {
        this.papers.add(paper);
    }

    /**
     * Count the total number of papers submitted by this author.
     *
     * @return the total number of papers submitted
     */
    public int countSubmittedPapers() {
        return this.papers.size();
    }

    /**
     * Calculate the acceptance rate of papers for this author.
     * Acceptance rate = (number of accepted papers) / (total papers submitted).
     *
     * @return the acceptance rate as a double between 0.0 and 1.0
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
     * Find authors with at least one positive review (ACCEPT).
     * Note: This method is implemented here for demonstration but would typically be in a service class.
     *
     * @return list of authors with at least one ACCEPT review
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // This would normally be implemented in a service class with access to all authors
        // For now, returning an empty list as placeholder
        return new ArrayList<>();
    }
}

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
        return this.assignments;
    }

    /**
     * Calculate the number of unsubmitted reviews for this reviewer.
     * A review is considered unsubmitted if its grade is UNDECIDED.
     *
     * @return the count of unsubmitted reviews
     */
    public int calculateUnsubmittedReviews() {
        int unsubmittedCount = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.UNDECIDED) {
                unsubmittedCount++;
            }
        }
        return unsubmittedCount;
    }

    /**
     * Convert this reviewer's historical grades into a numerical average score.
     * Accept = 1, Reject = 0. UNDECIDED grades are excluded from calculation.
     *
     * @return the average score between 0.0 and 1.0
     */
    public double calculateAcceptanceProportion() {
        if (assignments.isEmpty()) {
            return 0.0;
        }

        int total = 0;
        int count = 0;

        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() != Grade.UNDECIDED) {
                total += (assignment.getGrade() == Grade.ACCEPT) ? 1 : 0;
                count++;
            }
        }

        if (count == 0) {
            return 0.0;
        }

        return (double) total / count;
    }
}

class CoChair extends UserRole {
    public CoChair() {
    }

    /**
     * Make the final decision for a paper.
     * This method sets the decision on the paper.
     *
     * @param paper     the paper to make decision for
     * @param decision  the final decision (ACCEPT or REJECT)
     * @return 1 if decision was set successfully, 0 otherwise
     */
    public int makeFinalDecision(Paper paper, Grade decision) {
        if (decision == Grade.UNDECIDED) {
            return 0;
        }
        paper.setDecision(decision);
        return 1;
    }
}

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
     * Check if all reviews for this paper are either exclusively ACCEPT or exclusively REJECT.
     * UNDECIDED reviews are ignored in this check.
     *
     * @return true if all reviews are exclusively ACCEPT or exclusively REJECT, false otherwise
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
            return false;
        }

        List<ReviewAssignment> decidedReviews = new ArrayList<>();
        for (ReviewAssignment review : reviews) {
            if (review.getGrade() != Grade.UNDECIDED) {
                decidedReviews.add(review);
            }
        }

        if (decidedReviews.isEmpty()) {
            return false;
        }

        Grade firstGrade = decidedReviews.get(0).getGrade();
        for (ReviewAssignment review : decidedReviews) {
            if (review.getGrade() != firstGrade) {
                return false;
            }
        }

        return true;
    }
}

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