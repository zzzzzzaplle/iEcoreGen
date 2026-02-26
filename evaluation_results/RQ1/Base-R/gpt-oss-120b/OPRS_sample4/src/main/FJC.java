import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Base class representing a system user.
 */
class User {
    private String name;

    /** Unparameterized constructor */
    public User() {
    }

    /** @return the user name */
    public String getName() {
        return name;
    }

    /** @param name the user name to set */
    public void setName(String name) {
        this.name = name;
    }
}

/**
 * Enumeration of possible paper types.
 */
enum PaperType {
    RESEARCH,
    EXPERIENCE_REPORT
}

/**
 * Enumeration of possible grades for a review.
 */
enum Grade {
    ACCEPT,
    REJECT
}

/**
 * Represents a scholarly paper submitted by an author.
 */
class Paper {
    private String title;
    private PaperType type;
    private Author author;
    private List<Review> reviews = new ArrayList<>();
    private Grade finalDecision; // set by the chair after evaluation

    /** Unparameterized constructor */
    public Paper() {
    }

    /** @return the paper title */
    public String getTitle() {
        return title;
    }

    /** @param title the paper title to set */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the paper type */
    public PaperType getType() {
        return type;
    }

    /** @param type the paper type to set */
    public void setType(PaperType type) {
        this.type = type;
    }

    /** @return the author of the paper */
    public Author getAuthor() {
        return author;
    }

    /** @param author the author to set */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /** @return the list of reviews attached to this paper */
    public List<Review> getReviews() {
        return reviews;
    }

    /** @param reviews the list of reviews to set */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /** @return the final decision taken by the chair (may be null if not decided yet) */
    public Grade getFinalDecision() {
        return finalDecision;
    }

    /** @param finalDecision the final decision to set */
    public void setFinalDecision(Grade finalDecision) {
        this.finalDecision = finalDecision;
    }

    /**
     * Adds a review to the paper.
     *
     * @param review the review to add
     */
    public void addReview(Review review) {
        if (review != null) {
            reviews.add(review);
        }
    }
}

/**
 * Represents a review written by a reviewer for a paper.
 */
class Review {
    private Paper paper;
    private Reviewer reviewer;
    private String feedback;
    private Grade grade;
    private boolean submitted = false; // true once feedback & grade are provided

    /** Unparameterized constructor */
    public Review() {
    }

    /** @return the paper being reviewed */
    public Paper getPaper() {
        return paper;
    }

    /** @param paper the paper to set */
    public void setPaper(Paper paper) {
        this.paper = paper;
    }

    /** @return the reviewer who wrote this review */
    public Reviewer getReviewer() {
        return reviewer;
    }

    /** @param reviewer the reviewer to set */
    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    /** @return the feedback text */
    public String getFeedback() {
        return feedback;
    }

    /** @param feedback the feedback to set */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /** @return the grade assigned by the reviewer */
    public Grade getGrade() {
        return grade;
    }

    /** @param grade the grade to set */
    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    /** @return true if the review has been submitted */
    public boolean isSubmitted() {
        return submitted;
    }

    /** @param submitted the submitted flag to set */
    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    /**
     * Submits the review by providing feedback and a grade.
     *
     * @param feedback the textual feedback
     * @param grade    the grade (ACCEPT or REJECT)
     */
    public void submit(String feedback, Grade grade) {
        this.feedback = feedback;
        this.grade = grade;
        this.submitted = true;
    }
}

/**
 * Represents an author who can submit many papers.
 */
class Author extends User {
    private List<Paper> papers = new ArrayList<>();

    /** Unparameterized constructor */
    public Author() {
        super();
    }

    /** @return the list of papers submitted by this author */
    public List<Paper> getPapers() {
        return papers;
    }

    /** @param papers the list of papers to set */
    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    /**
     * Adds a paper to the author's collection.
     *
     * @param paper the paper to add
     */
    public void addPaper(Paper paper) {
        if (paper != null) {
            papers.add(paper);
            paper.setAuthor(this);
        }
    }

    /**
     * Counts the total number of papers submitted by the author.
     *
     * @return the number of submitted papers (0 if none)
     */
    public int getTotalSubmittedPapers() {
        return papers.size();
    }

    /**
     * Calculates the acceptance rate of the author's papers.
     * Acceptance rate = (number of accepted papers) / (total submitted papers).
     *
     * @return a double in the range [0.0, 1.0]; returns 0.0 if no papers were submitted
     */
    public double getAcceptanceRate() {
        if (papers.isEmpty()) {
            return 0.0;
        }
        long acceptedCount = papers.stream()
                .filter(p -> p.getFinalDecision() == Grade.ACCEPT)
                .count();
        return (double) acceptedCount / papers.size();
    }
}

/**
 * Represents a reviewer who can be assigned papers and submit reviews.
 */
class Reviewer extends User {
    private List<Review> assignedReviews = new ArrayList<>();

    /** Unparameterized constructor */
    public Reviewer() {
        super();
    }

    /** @return the list of reviews assigned to this reviewer */
    public List<Review> getAssignedReviews() {
        return assignedReviews;
    }

    /** @param assignedReviews the list of assigned reviews to set */
    public void setAssignedReviews(List<Review> assignedReviews) {
        this.assignedReviews = assignedReviews;
    }

    /**
     * Assigns a new review to the reviewer.
     *
     * @param review the review to assign
     */
    public void assignReview(Review review) {
        if (review != null) {
            assignedReviews.add(review);
            review.setReviewer(this);
        }
    }

    /**
     * Calculates the number of unsubmitted reviews for this reviewer.
     *
     * @return the count of reviews that have not been submitted; 0 if all are submitted
     */
    public int getUnsubmittedReviewCount() {
        return (int) assignedReviews.stream()
                .filter(r -> !r.isSubmitted())
                .count();
    }

    /**
     * Computes the average historical score of the reviewer based on submitted grades.
     * Accept = 1, Reject = 0.
     *
     * @return a double between 0.0 and 1.0; returns 0.0 if the reviewer has no submitted reviews
     */
    public double getHistoricalAverageScore() {
        List<Review> submitted = assignedReviews.stream()
                .filter(Review::isSubmitted)
                .collect(Collectors.toList());

        if (submitted.isEmpty()) {
            return 0.0;
        }

        double sum = submitted.stream()
                .mapToInt(r -> r.getGrade() == Grade.ACCEPT ? 1 : 0)
                .sum();

        return sum / submitted.size();
    }
}

/**
 * Represents the program chair who makes final decisions on papers.
 */
class Chair extends User {
    /** Unparameterized constructor */
    public Chair() {
        super();
    }

    /**
     * Checks whether all reviews for a given paper are exclusively Accept or exclusively Reject.
     *
     * @param paper the paper whose reviews are to be examined
     * @return true if all submitted reviews have the same grade (all Accept or all Reject);
     * false if there is a mix or if there are no submitted reviews
     */
    public boolean areAllReviewsUniform(Paper paper) {
        Objects.requireNonNull(paper, "Paper must not be null");
        List<Review> submitted = paper.getReviews().stream()
                .filter(Review::isSubmitted)
                .collect(Collectors.toList());

        if (submitted.isEmpty()) {
            return false;
        }

        Grade first = submitted.get(0).getGrade();
        return submitted.stream().allMatch(r -> r.getGrade() == first);
    }

    /**
     * Makes the final decision for a paper if all reviews are uniform.
     * The decision is set to the common grade of the reviews.
     *
     * @param paper the paper for which the final decision is to be made
     * @throws IllegalStateException if the reviews are not uniform
     */
    public void makeFinalDecision(Paper paper) {
        Objects.requireNonNull(paper, "Paper must not be null");
        if (!areAllReviewsUniform(paper)) {
            throw new IllegalStateException("Cannot make final decision: reviews are not uniform");
        }
        // All reviews have the same grade; pick the grade from any submitted review
        Grade decision = paper.getReviews().stream()
                .filter(Review::isSubmitted)
                .findFirst()
                .map(Review::getGrade)
                .orElse(null);
        paper.setFinalDecision(decision);
    }
}