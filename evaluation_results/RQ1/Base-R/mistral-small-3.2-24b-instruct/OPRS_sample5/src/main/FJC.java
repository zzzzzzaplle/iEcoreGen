import java.util.ArrayList;
import java.util.List;

 class User {
    private String name;
    private List<Paper> submittedPapers;
    private List<Review> submittedReviews;

    public User() {
        this.name = "";
        this.submittedPapers = new ArrayList<>();
        this.submittedReviews = new ArrayList<>();
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

    public List<Review> getSubmittedReviews() {
        return submittedReviews;
    }

    public void setSubmittedReviews(List<Review> submittedReviews) {
        this.submittedReviews = submittedReviews;
    }

    public void addSubmittedPaper(Paper paper) {
        this.submittedPapers.add(paper);
    }

    public void addSubmittedReview(Review review) {
        this.submittedReviews.add(review);
    }
}

 class Paper {
    private String title;
    private boolean isResearchPaper;
    private List<Review> reviews;
    private String finalDecision;

    public Paper() {
        this.title = "";
        this.isResearchPaper = true;
        this.reviews = new ArrayList<>();
        this.finalDecision = "";
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

    public String getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(String finalDecision) {
        this.finalDecision = finalDecision;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }
}

 class Review {
    private String feedback;
    private String grade;

    public Review() {
        this.feedback = "";
        this.grade = "";
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}

 class Reviewer extends User {
    /**
     * Calculates the number of unsubmitted reviews for the reviewer.
     * @return the number of unsubmitted reviews
     */
    public int calculateUnsubmittedReviews() {
        int unsubmittedReviews = 0;
        for (Review review : getSubmittedReviews()) {
            if (review.getGrade() == null || review.getGrade().isEmpty()) {
                unsubmittedReviews++;
            }
        }
        return unsubmittedReviews;
    }

    /**
     * Calculates the average score of the reviewer's historical grades.
     * @return the average score between 0.0 and 1.0
     */
    public double calculateAverageGradeScore() {
        int totalReviews = getSubmittedReviews().size();
        if (totalReviews == 0) {
            return 0.0;
        }
        int totalScore = 0;
        for (Review review : getSubmittedReviews()) {
            if ("Accept".equals(review.getGrade())) {
                totalScore += 1;
            } else if ("Reject".equals(review.getGrade())) {
                totalScore += 0;
            }
        }
        return (double) totalScore / totalReviews;
    }
}

 class Chair extends User {
    /**
     * Checks if all reviews for a paper are either exclusively Accept or exclusively Reject.
     * @param paper the paper to check
     * @return true if all reviews are either Accept or Reject, false otherwise
     */
    public boolean checkConsistentReviews(Paper paper) {
        boolean allAccept = true;
        boolean allReject = true;
        for (Review review : paper.getReviews()) {
            if (!"Accept".equals(review.getGrade())) {
                allAccept = false;
            }
            if (!"Reject".equals(review.getGrade())) {
                allReject = false;
            }
        }
        return allAccept || allReject;
    }
}

 class Author extends User {
    /**
     * Counts the total number of papers submitted by the author.
     * @return the number of submitted papers
     */
    public int countSubmittedPapers() {
        return getSubmittedPapers().size();
    }

    /**
     * Calculates the acceptance rate of papers for the author.
     * @return the acceptance rate as a double between 0.0 and 1.0
     */
    public double calculateAcceptanceRate() {
        int totalPapers = countSubmittedPapers();
        if (totalPapers == 0) {
            return 0.0;
        }
        int acceptedPapers = 0;
        for (Paper paper : getSubmittedPapers()) {
            if ("Accept".equals(paper.getFinalDecision())) {
                acceptedPapers++;
            }
        }
        return (double) acceptedPapers / totalPapers;
    }
}