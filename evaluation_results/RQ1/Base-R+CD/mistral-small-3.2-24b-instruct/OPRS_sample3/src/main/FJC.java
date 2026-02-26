import java.util.ArrayList;
import java.util.List;

abstract class UserRole {
    // Common properties and methods for all user roles can be added here
}

class Author extends UserRole {
    private List<Paper> papers;

    public Author() {
        this.papers = new ArrayList<>();
    }

    /**
     * Adds a paper to the author's list of submitted papers.
     *
     * @param paper The paper to be submitted.
     */
    public void submitPaper(Paper paper) {
        if (paper != null) {
            papers.add(paper);
        }
    }

    /**
     * Counts the total number of papers submitted by the author.
     *
     * @return The number of submitted papers.
     */
    public int countSubmittedPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for the author.
     *
     * @return The acceptance rate as a double between 0.0 and 1.0.
     *         Returns 0.0 if no papers are accepted or if no papers are submitted.
     */
    public double calculateAcceptanceRate() {
        if (papers.isEmpty()) {
            return 0.0;
        }

        int acceptedPapers = 0;
        for (Paper paper : papers) {
            if (paper.getDecision() == Grade.ACCEPT) {
                acceptedPapers++;
            }
        }

        if (acceptedPapers == 0) {
            return 0.0;
        }

        return (double) acceptedPapers / papers.size();
    }

    /**
     * Finds authors with positive reviews.
     *
     * @return A list of authors with positive reviews.
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        // Implementation to find authors with positive reviews
        return new ArrayList<>();
    }

    // Getters and setters
    public List<Paper> getPapers() {
        return papers;
    }

    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }
}

class Reviewer extends UserRole {
    private List<ReviewAssignment> assignments;

    public Reviewer() {
        this.assignments = new ArrayList<>();
    }

    /**
     * Gets the list of review assignments for the reviewer.
     *
     * @return The list of review assignments.
     */
    public List<ReviewAssignment> getReviewAssignments() {
        return assignments;
    }

    /**
     * Calculates the number of unsubmitted reviews for the reviewer.
     *
     * @return The number of unsubmitted reviews. Returns 0 if all reviews are submitted.
     */
    public int calculateUnsubmittedReviews() {
        int unsubmittedReviews = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.UNDECIDED) {
                unsubmittedReviews++;
            }
        }
        return unsubmittedReviews;
    }

    /**
     * Calculates the acceptance proportion for the reviewer.
     *
     * @return The acceptance proportion as a double between 0.0 and 1.0.
     *         Returns 0.0 if no reviews are submitted.
     */
    public double calculateAcceptanceProportion() {
        if (assignments.isEmpty()) {
            return 0.0;
        }

        int acceptedReviews = 0;
        for (ReviewAssignment assignment : assignments) {
            if (assignment.getGrade() == Grade.ACCEPT) {
                acceptedReviews++;
            }
        }

        return (double) acceptedReviews / assignments.size();
    }

    // Getters and setters
    public void setReviewAssignments(List<ReviewAssignment> assignments) {
        this.assignments = assignments;
    }
}

class CoChair extends UserRole {
    /**
     * Makes the final decision for a paper.
     *
     * @param paper The paper to make a decision for.
     * @param decision The decision to make (ACCEPT or REJECT).
     * @throws IllegalArgumentException if the decision is not ACCEPT or REJECT.
     */
    public void makeFinalDecision(Paper paper, Grade decision) {
        if (decision != Grade.ACCEPT && decision != Grade.REJECT) {
            throw new IllegalArgumentException("Decision must be either ACCEPT or REJECT");
        }
        paper.setDecision(decision);
    }
}

enum PaperType {
    RESEARCH,
    EXPERIENCE
}

enum Grade {
    UNDECIDED,
    ACCEPT,
    REJECT
}

class Paper {
    private String title;
    private PaperType type;
    private Grade decision;
    private List<ReviewAssignment> reviews;

    public Paper() {
        this.reviews = new ArrayList<>();
    }

    /**
     * Calculates the acceptance rate for the paper based on reviews.
     *
     * @return The acceptance rate as a double between 0.0 and 1.0.
     *         Returns 0.0 if no reviews are submitted or all reviews are REJECT.
     */
    public double calculateAcceptanceRate() {
        if (reviews.isEmpty()) {
            return 0.0;
        }

        int acceptedReviews = 0;
        for (ReviewAssignment review : reviews) {
            if (review.getGrade() == Grade.ACCEPT) {
                acceptedReviews++;
            }
        }

        if (acceptedReviews == 0) {
            return 0.0;
        }

        return (double) acceptedReviews / reviews.size();
    }

    /**
     * Checks if all reviews for the paper are either exclusively Accept or exclusively Reject.
     *
     * @return True if all reviews are either Accept or Reject, false otherwise.
     */
    public boolean isAllReviewsPositive() {
        if (reviews.isEmpty()) {
            return false;
        }

        Grade firstGrade = reviews.get(0).getGrade();
        if (firstGrade == Grade.ACCEPT) {
            for (ReviewAssignment review : reviews) {
                if (review.getGrade() != Grade.ACCEPT) {
                    return false;
                }
            }
            return true;
        } else if (firstGrade == Grade.REJECT) {
            for (ReviewAssignment review : reviews) {
                if (review.getGrade() != Grade.REJECT) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // Getters and setters
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
}

class ReviewAssignment {
    private String feedback;
    private Grade grade;

    public ReviewAssignment() {
        this.grade = Grade.UNDECIDED;
    }

    // Getters and setters
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

class User {
    private String name;
    private List<UserRole> roles;

    public User() {
        this.roles = new ArrayList<>();
    }

    /**
     * Adds a role to the user.
     *
     * @param role The role to be added.
     */
    public void addRole(UserRole role) {
        if (role != null) {
            roles.add(role);
        }
    }

    // Getters and setters
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
}