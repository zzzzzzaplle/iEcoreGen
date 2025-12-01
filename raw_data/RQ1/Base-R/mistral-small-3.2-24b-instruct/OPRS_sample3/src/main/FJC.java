import java.util.ArrayList;
import java.util.List;

class User {
    private String name;
    private List<Paper> submittedPapers;

    public User() {
        this.name = "";
        this.submittedPapers = new ArrayList<>();
    }

    public User(String name) {
        this.name = name;
        this.submittedPapers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Paper> getSubmittedPapers() {
        return submittedPapers;
    }

    public void setSubmittedPapers(List<Paper> submittedPapers) {
        this.submittedPapers = submittedPapers;
    }

    public void addSubmittedPaper(Paper paper) {
        this.submittedPapers.add(paper);
    }

    public int countSubmittedPapers() {
        return submittedPapers.size();
    }
}

class Author extends User {
    public Author() {
        super();
    }

    public Author(String name) {
        super(name);
    }

    /**
     * Calculates the acceptance rate of papers for the author.
     * Example: If an author submitted 10 papers with 6 accepted, return 0.60.
     *
     * @return the acceptance rate of papers for the author
     */
    public double calculateAcceptanceRate() {
        if (getSubmittedPapers().isEmpty()) {
            return 0.0;
        }
        int acceptedPapers = 0;
        for (Paper paper : getSubmittedPapers()) {
            if (paper.getFinalDecision() == Decision.ACCEPT) {
                acceptedPapers++;
            }
        }
        return (double) acceptedPapers / getSubmittedPapers().size();
    }
}

class Reviewer extends User {
    private List<Paper> assignedPapers;
    private List<Review> reviews;

    public Reviewer() {
        super();
        this.assignedPapers = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public Reviewer(String name) {
        super(name);
        this.assignedPapers = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public List<Paper> getAssignedPapers() {
        return assignedPapers;
    }

    public void setAssignedPapers(List<Paper> assignedPapers) {
        this.assignedPapers = assignedPapers;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addAssignedPaper(Paper paper) {
        this.assignedPapers.add(paper);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    /**
     * Calculates the number of unsubmitted reviews for the reviewer.
     * Return 0 if all reviews are submitted by the reviewer.
     *
     * @return the number of unsubmitted reviews
     */
    public int countUnsubmittedReviews() {
        int unsubmittedReviews = 0;
        for (Paper paper : assignedPapers) {
            boolean reviewSubmitted = false;
            for (Review review : reviews) {
                if (review.getPaper().equals(paper) && review.getGrade() != null) {
                    reviewSubmitted = true;
                    break;
                }
            }
            if (!reviewSubmitted) {
                unsubmittedReviews++;
            }
        }
        return unsubmittedReviews;
    }

    /**
     * Converts the reviewer's historical grades (Accept = 1, Reject = 0) into a numerical average score between 0.0 and 1.0.
     * Example: 3 Accept and 1 Reject → (3×1 + 1×0)/4 = 0.75.
     *
     * @return the numerical average score
     */
    public double calculateNumericalAverageScore() {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        int totalAccept = 0;
        int totalReviews = 0;
        for (Review review : reviews) {
            if (review.getGrade() != null) {
                totalReviews++;
                if (review.getGrade() == Grade.ACCEPT) {
                    totalAccept++;
                }
            }
        }
        return (double) totalAccept / totalReviews;
    }
}

class Chair extends User {
    public Chair() {
        super();
    }

    public Chair(String name) {
        super(name);
    }

    /**
     * Checks if all reviews for a paper are either exclusively Accept or exclusively Reject before the chair makes the final decision.
     *
     * @param paper the paper to check
     * @return true if all reviews are either exclusively Accept or exclusively Reject, false otherwise
     */
    public boolean checkReviewConsistency(Paper paper) {
        if (paper.getReviews().isEmpty()) {
            return false;
        }
        Grade firstGrade = paper.getReviews().get(0).getGrade();
        for (Review review : paper.getReviews()) {
            if (review.getGrade() != firstGrade) {
                return false;
            }
        }
        return true;
    }
}

class Paper {
    private String title;
    private boolean isResearchPaper;
    private List<Review> reviews;
    private Decision finalDecision;

    public Paper() {
        this.title = "";
        this.isResearchPaper = false;
        this.reviews = new ArrayList<>();
        this.finalDecision = null;
    }

    public Paper(String title, boolean isResearchPaper) {
        this.title = title;
        this.isResearchPaper = isResearchPaper;
        this.reviews = new ArrayList<>();
        this.finalDecision = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isResearchPaper() {
        return isResearchPaper;
    }

    public void setResearchPaper(boolean researchPaper) {
        isResearchPaper = researchPaper;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Decision getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(Decision finalDecision) {
        this.finalDecision = finalDecision;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }
}

class Review {
    private Paper paper;
    private String feedback;
    private Grade grade;

    public Review() {
        this.paper = null;
        this.feedback = "";
        this.grade = null;
    }

    public Review(Paper paper, String feedback, Grade grade) {
        this.paper = paper;
        this.feedback = feedback;
        this.grade = grade;
    }

    public Paper getPaper() {
        return paper;
    }

    public void setPaper(Paper paper) {
        this.paper = paper;
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

enum Grade {
    ACCEPT, REJECT
}

enum Decision {
    ACCEPT, REJECT
}