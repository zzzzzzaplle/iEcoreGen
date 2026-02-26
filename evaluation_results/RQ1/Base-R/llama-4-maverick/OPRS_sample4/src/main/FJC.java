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
     * @return the list of papers
     */
    public List<Paper> getPapers() {
        return papers;
    }

    /**
     * Sets the papers submitted by the author.
     * @param papers the list of papers to set
     */
    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    /**
     * Adds a paper to the author's submissions.
     * @param paper the paper to add
     */
    public void addPaper(Paper paper) {
        this.papers.add(paper);
    }

    /**
     * Counts the total number of papers submitted by the author.
     * @return the total number of papers
     */
    public int countPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of papers for the author.
     * @return the acceptance rate between 0.0 and 1.0
     */
    public double calculateAcceptanceRate() {
        int acceptedCount = 0;
        for (Paper paper : papers) {
            if (paper.getFinalDecision() != null && paper.getFinalDecision().equals("Accept")) {
                acceptedCount++;
            }
        }
        return papers.isEmpty() ? 0.0 : (double) acceptedCount / papers.size();
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
     * Gets the reviews assigned to the reviewer.
     * @return the list of reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets the reviews assigned to the reviewer.
     * @param reviews the list of reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Adds a review to the reviewer's assignments.
     * @param review the review to add
     */
    public void addReview(Review review) {
        this.reviews.add(review);
    }

    /**
     * Calculates the number of unsubmitted reviews for the reviewer.
     * @return the number of unsubmitted reviews
     */
    public int countUnsubmittedReviews() {
        int count = 0;
        for (Review review : reviews) {
            if (review.getFeedback() == null || review.getGrade() == null) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculates the average score of the reviewer's historical grades.
     * @return the average score between 0.0 and 1.0
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
        return count == 0 ? 0.0 : (double) totalScore / count;
    }
}

/**
 * Represents a paper in the system.
 */
class Paper {
    private String title;
    private String type;
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
     * @return the title
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
     * Gets the type of the paper.
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the paper.
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the reviews for the paper.
     * @return the list of reviews
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets the reviews for the paper.
     * @param reviews the list of reviews to set
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Adds a review to the paper.
     * @param review the review to add
     */
    public void addReview(Review review) {
        this.reviews.add(review);
    }

    /**
     * Gets the final decision for the paper.
     * @return the final decision
     */
    public String getFinalDecision() {
        return finalDecision;
    }

    /**
     * Sets the final decision for the paper.
     * @param finalDecision the final decision to set
     */
    public void setFinalDecision(String finalDecision) {
        this.finalDecision = finalDecision;
    }

    /**
     * Checks if all reviews for the paper are either exclusively Accept or exclusively Reject before the chair makes the final decision.
     * @return true if all reviews are consistent, false otherwise
     */
    public boolean areReviewsConsistent() {
        if (finalDecision != null) {
            return true; // Final decision is already made
        }
        String grade = null;
        for (Review review : reviews) {
            if (review.getGrade() != null) {
                if (grade == null) {
                    grade = review.getGrade();
                } else if (!grade.equals(review.getGrade())) {
                    return false; // Inconsistent grades
                }
            }
        }
        return true; // All reviews are consistent or null
    }
}

/**
 * Represents a review in the system.
 */
class Review {
    private String feedback;
    private String grade;

    /**
     * Constructs a new Review.
     */
    public Review() {}

    /**
     * Gets the feedback of the review.
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets the feedback of the review.
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets the grade of the review.
     * @return the grade
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Sets the grade of the review.
     * @param grade the grade to set
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }
}