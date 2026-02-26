import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the review system
 */
class User {
    private String name;
    private List<Paper> submittedPapers;
    private List<Review> reviews;

    /**
     * Default constructor
     */
    public User() {
        this.submittedPapers = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    /**
     * Gets the user's name
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the list of papers submitted by this user
     * @return list of submitted papers
     */
    public List<Paper> getSubmittedPapers() {
        return submittedPapers;
    }

    /**
     * Sets the list of papers submitted by this user
     * @param submittedPapers the list of submitted papers
     */
    public void setSubmittedPapers(List<Paper> submittedPapers) {
        this.submittedPapers = submittedPapers;
    }

    /**
     * Gets the list of reviews written by this user
     * @return list of reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews written by this user
     * @param reviews the list of reviews
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}

/**
 * Represents a paper in the review system
 */
class Paper {
    private String title;
    private boolean isResearchPaper;
    private List<Review> reviews;
    private User author;
    private Decision finalDecision;

    /**
     * Default constructor
     */
    public Paper() {
        this.reviews = new ArrayList<>();
    }

    /**
     * Gets the paper title
     * @return the title of the paper
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the paper title
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Checks if this is a research paper
     * @return true if research paper, false if experience report
     */
    public boolean isResearchPaper() {
        return isResearchPaper;
    }

    /**
     * Sets whether this is a research paper
     * @param researchPaper true for research paper, false for experience report
     */
    public void setResearchPaper(boolean researchPaper) {
        isResearchPaper = researchPaper;
    }

    /**
     * Gets the list of reviews for this paper
     * @return list of reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews for this paper
     * @param reviews the list of reviews
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Gets the author of this paper
     * @return the author user
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Sets the author of this paper
     * @param author the author user
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * Gets the final decision for this paper
     * @return the final decision
     */
    public Decision getFinalDecision() {
        return finalDecision;
    }

    /**
     * Sets the final decision for this paper
     * @param finalDecision the final decision
     */
    public void setFinalDecision(Decision finalDecision) {
        this.finalDecision = finalDecision;
    }
}

/**
 * Represents a review of a paper
 */
class Review {
    private String feedback;
    private Grade grade;
    private boolean isSubmitted;
    private User reviewer;
    private Paper paper;

    /**
     * Default constructor
     */
    public Review() {
        this.isSubmitted = false;
    }

    /**
     * Gets the feedback content of the review
     * @return the feedback text
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback content of the review
     * @param feedback the feedback text
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the grade given in the review
     * @return the grade
     */
    public Grade getGrade() {
        return grade;
    }

    /**
     * Sets the grade for the review
     * @param grade the grade to set
     */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    /**
     * Checks if the review has been submitted
     * @return true if submitted, false otherwise
     */
    public boolean isSubmitted() {
        return isSubmitted;
    }

    /**
     * Sets the submission status of the review
     * @param submitted true if submitted, false otherwise
     */
    public void setSubmitted(boolean submitted) {
        isSubmitted = submitted;
    }

    /**
     * Gets the reviewer who wrote this review
     * @return the reviewer user
     */
    public User getReviewer() {
        return reviewer;
    }

    /**
     * Sets the reviewer who wrote this review
     * @param reviewer the reviewer user
     */
    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    /**
     * Gets the paper being reviewed
     * @return the paper
     */
    public Paper getPaper() {
        return paper;
    }

    /**
     * Sets the paper being reviewed
     * @param paper the paper
     */
    public void setPaper(Paper paper) {
        this.paper = paper;
    }
}

/**
 * Enum representing possible grades for a review
 */
enum Grade {
    ACCEPT, REJECT
}

/**
 * Enum representing final decision for a paper
 */
enum Decision {
    ACCEPTED, REJECTED
}

/**
 * Service class containing business logic for the review system
 */
class ReviewSystemService {

    /**
     * Calculates the number of unsubmitted reviews for a given reviewer
     * @param reviewer the reviewer to check
     * @return the number of unsubmitted reviews, returns 0 if all reviews are submitted
     */
    public int calculateUnsubmittedReviews(User reviewer) {
        if (reviewer == null || reviewer.getReviews() == null) {
            return 0;
        }
        
        int unsubmittedCount = 0;
        for (Review review : reviewer.getReviews()) {
            if (!review.isSubmitted()) {
                unsubmittedCount++;
            }
        }
        return unsubmittedCount;
    }

    /**
     * Checks if all reviews for a paper are either exclusively Accept or exclusively Reject
     * This should be checked before the chair makes the final decision
     * @param paper the paper to check
     * @return true if all reviews are either all Accept or all Reject, false otherwise
     */
    public boolean areReviewsUnanimous(Paper paper) {
        if (paper == null || paper.getReviews() == null || paper.getReviews().isEmpty()) {
            return false;
        }
        
        List<Review> reviews = paper.getReviews();
        Grade firstGrade = reviews.get(0).getGrade();
        
        for (Review review : reviews) {
            if (review.getGrade() != firstGrade) {
                return false;
            }
        }
        return true;
    }

    /**
     * Counts the total number of papers submitted by an author
     * @param author the author to count papers for
     * @return the total number of papers submitted by the author
     */
    public int countSubmittedPapers(User author) {
        if (author == null || author.getSubmittedPapers() == null) {
            return 0;
        }
        return author.getSubmittedPapers().size();
    }

    /**
     * Calculates the acceptance rate of papers for an author
     * @param author the author to calculate acceptance rate for
     * @return the acceptance rate as a double between 0.0 and 1.0, returns 0.0 if no papers submitted
     */
    public double calculateAcceptanceRate(User author) {
        if (author == null || author.getSubmittedPapers() == null || author.getSubmittedPapers().isEmpty()) {
            return 0.0;
        }
        
        int totalPapers = author.getSubmittedPapers().size();
        int acceptedPapers = 0;
        
        for (Paper paper : author.getSubmittedPapers()) {
            if (paper.getFinalDecision() == Decision.ACCEPTED) {
                acceptedPapers++;
            }
        }
        
        return (double) acceptedPapers / totalPapers;
    }

    /**
     * Converts a reviewer's historical grades into a numerical average score between 0.0 and 1.0
     * Accept = 1, Reject = 0
     * @param reviewer the reviewer to calculate average score for
     * @return the average score between 0.0 and 1.0, returns 0.0 if no reviews found
     */
    public double calculateReviewerAverageScore(User reviewer) {
        if (reviewer == null || reviewer.getReviews() == null || reviewer.getReviews().isEmpty()) {
            return 0.0;
        }
        
        List<Review> reviews = reviewer.getReviews();
        double totalScore = 0.0;
        int count = 0;
        
        for (Review review : reviews) {
            if (review.isSubmitted()) {
                if (review.getGrade() == Grade.ACCEPT) {
                    totalScore += 1.0;
                }
                count++;
            }
        }
        
        if (count == 0) {
            return 0.0;
        }
        
        return totalScore / count;
    }
}