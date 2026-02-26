import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the system.
 */
class User {
    private String name;

    /**
     * Constructs a new User.
     */
    public User() {}

    /**
     * Gets the name of the user.
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Represents an author in the system.
 */
class Author extends User {
    private List<Paper> papers;

    /**
     * Constructs a new Author.
     */
    public Author() {
        this.papers = new ArrayList<>();
    }

    /**
     * Gets the papers submitted by the author.
     * @return the papers submitted by the author
     */
    public List<Paper> getPapers() {
        return papers;
    }

    /**
     * Sets the papers submitted by the author.
     * @param papers the papers to set
     */
    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    /**
     * Counts the total number of papers submitted by the author.
     * @return the total number of papers submitted by the author
     */
    public int countPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for the author.
     * @return the acceptance rate of papers for the author
     */
    public double calculateAcceptanceRate() {
        int acceptedCount = 0;
        for (Paper paper : papers) {
            if (paper.getFinalDecision() != null && paper.getFinalDecision().equals("Accept")) {
                acceptedCount++;
            }
        }
        return (double) acceptedCount / papers.size();
    }
}

/**
 * Represents a reviewer in the system.
 */
class Reviewer extends User {
    private List<Review> reviews;

    /**
     * Constructs a new Reviewer.
     */
    public Reviewer() {
        this.reviews = new ArrayList<>();
    }

    /**
     * Gets the reviews submitted by the reviewer.
     * @return the reviews submitted by the reviewer
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets the reviews submitted by the reviewer.
     * @param reviews the reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Calculates the number of unsubmitted reviews for the reviewer.
     * @return the number of unsubmitted reviews for the reviewer
     */
    public int countUnsubmittedReviews() {
        int unsubmittedCount = 0;
        for (Review review : reviews) {
            if (!review.isSubmitted()) {
                unsubmittedCount++;
            }
        }
        return unsubmittedCount;
    }

    /**
     * Calculates the average score of the reviewer's historical grades.
     * @return the average score of the reviewer's historical grades
     */
    public double calculateAverageScore() {
        int totalScore = 0;
        int count = 0;
        for (Review review : reviews) {
            if (review.getGrade() != null) {
                totalScore += review.getGrade().equals("Accept") ? 1 : 0;
                count++;
            }
        }
        return (double) totalScore / count;
    }
}

/**
 * Represents a paper in the system.
 */
class Paper {
    private String title;
    private boolean isResearchPaper;
    private List<Review> reviews;
    private String finalDecision;

    /**
     * Constructs a new Paper.
     */
    public Paper() {
        this.reviews = new ArrayList<>();
    }

    /**
     * Gets the title of the paper.
     * @return the title of the paper
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the paper.
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Checks if the paper is a research paper.
     * @return true if the paper is a research paper, false otherwise
     */
    public boolean isResearchPaper() {
        return isResearchPaper;
    }

    /**
     * Sets whether the paper is a research paper.
     * @param researchPaper whether the paper is a research paper
     */
    public void setResearchPaper(boolean researchPaper) {
        isResearchPaper = researchPaper;
    }

    /**
     * Gets the reviews submitted for the paper.
     * @return the reviews submitted for the paper
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets the reviews submitted for the paper.
     * @param reviews the reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Gets the final decision made for the paper.
     * @return the final decision made for the paper
     */
    public String getFinalDecision() {
        return finalDecision;
    }

    /**
     * Sets the final decision made for the paper.
     * @param finalDecision the final decision to set
     */
    public void setFinalDecision(String finalDecision) {
        this.finalDecision = finalDecision;
    }

    /**
     * Checks if all reviews for the paper are either exclusively Accept or exclusively Reject before the chair makes the final decision.
     * @return true if all reviews are either exclusively Accept or exclusively Reject, false otherwise
     */
    public boolean checkReviews() {
        if (finalDecision != null) {
            return true; // final decision already made
        }
        boolean hasAccept = false;
        boolean hasReject = false;
        for (Review review : reviews) {
            if (review.getGrade() != null) {
                if (review.getGrade().equals("Accept")) {
                    hasAccept = true;
                } else if (review.getGrade().equals("Reject")) {
                    hasReject = true;
                }
            }
        }
        return !(hasAccept && hasReject);
    }
}

/**
 * Represents a review in the system.
 */
class Review {
    private String feedback;
    private String grade;
    private boolean isSubmitted;

    /**
     * Constructs a new Review.
     */
    public Review() {}

    /**
     * Gets the feedback submitted for the review.
     * @return the feedback submitted for the review
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback submitted for the review.
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the grade submitted for the review.
     * @return the grade submitted for the review
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Sets the grade submitted for the review.
     * @param grade the grade to set
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * Checks if the review is submitted.
     * @return true if the review is submitted, false otherwise
     */
    public boolean isSubmitted() {
        return isSubmitted;
    }

    /**
     * Sets whether the review is submitted.
     * @param submitted whether the review is submitted
     */
    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }
}