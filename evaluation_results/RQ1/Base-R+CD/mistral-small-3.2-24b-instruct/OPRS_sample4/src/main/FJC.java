import java.util.ArrayList;
import java.util.List;

abstract class UserRole {
    // Abstract class for user roles
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
     * Submits a paper for the author.
     * @param paper The paper to be submitted.
     */
    public void submitPaper(Paper paper) {
        this.papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by the author.
     * @return The number of papers submitted.
     */
    public int countSubmittedPapers() {
        return this.papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for the author.
     * @return The acceptance rate as a double value between 0.0 and 1.0.
     */
    public double calculateAcceptanceRate() {
        if (this.papers.isEmpty()) {
            return 0.0;
        }
        int accepted = 0;
        for (Paper paper : this.papers) {
            if (paper.getDecision() == Grade.ACCEPT) {
                accepted++;
            }
        }
        return (double) accepted / this.papers.size();
    }

    /**
     * Finds authors with positive reviews.
     * @return A list of authors with positive reviews.
     */
    public List<Author> findAuthorsWithPositiveReviews() {
        List<Author> authorsWithPositiveReviews = new ArrayList<>();
        for (Paper paper : this.papers) {
            if (paper.isAllReviewsPositive()) {
                authorsWithPositiveReviews.add(this);
                break;
            }
        }
        return authorsWithPositiveReviews;
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

    /**
     * Calculates the number of unsubmitted reviews for the reviewer.
     * @return The number of unsubmitted reviews.
     */
    public int calculateUnsubmittedReviews() {
        int unsubmitted = 0;
        for (ReviewAssignment assignment : this.assignments) {
            if (assignment.getGrade() == Grade.UNDECIDED) {
                unsubmitted++;
            }
        }
        return unsubmitted;
    }

    /**
     * Calculates the acceptance proportion for the reviewer.
     * @return The acceptance proportion as a double value between 0.0 and 1.0.
     */
    public double calculateAcceptanceProportion() {
        if (this.assignments.isEmpty()) {
            return 0.0;
        }
        int accepted = 0;
        for (ReviewAssignment assignment : this.assignments) {
            if (assignment.getGrade() == Grade.ACCEPT) {
                accepted++;
            }
        }
        return (double) accepted / this.assignments.size();
    }
}

class CoChair extends UserRole {
    /**
     * Makes a final decision for a paper.
     * @param paper The paper to make a decision for.
     * @param decision The decision to make.
     */
    public void makeFinalDecision(Paper paper, Grade decision) {
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
     * Calculates the acceptance rate for the paper.
     * @return The acceptance rate as a double value between 0.0 and 1.0.
     */
    public double calculateAcceptanceRate() {
        if (this.reviews.isEmpty()) {
            return 0.0;
        }
        int accepted = 0;
        for (ReviewAssignment review : this.reviews) {
            if (review.getGrade() == Grade.ACCEPT) {
                accepted++;
            }
        }
        return (double) accepted / this.reviews.size();
    }

    /**
     * Checks if all reviews for the paper are either exclusively Accept or exclusively Reject.
     * @return True if all reviews are either Accept or Reject, false otherwise.
     */
    public boolean isAllReviewsPositive() {
        if (this.reviews.isEmpty()) {
            return false;
        }
        Grade firstGrade = this.reviews.get(0).getGrade();
        for (ReviewAssignment review : this.reviews) {
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