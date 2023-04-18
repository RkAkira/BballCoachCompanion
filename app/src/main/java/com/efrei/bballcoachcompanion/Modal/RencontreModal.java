package com.efrei.bballcoachcompanion.Modal;

public class RencontreModal {
    private int id;
    private String equipe1;
    private String equipe2;
    private String score;
    private String bestScoreur;
    private int pts_mis;

    public RencontreModal(String equipe1, String equipe2, String score, String bestScoreur, int pts_mis, String date) {
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
        this.score = score;
        this.bestScoreur = bestScoreur;
        this.pts_mis = pts_mis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquipe1() {
        return equipe1;
    }

    public void setEquipe1(String equipe1) {
        this.equipe1 = equipe1;
    }

    public String getEquipe2() {
        return equipe2;
    }

    public void setEquipe2(String equipe2) {
        this.equipe2 = equipe2;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getBestScoreur() {
        return bestScoreur;
    }

    public void setBestScoreur(String bestScoreur) {
        this.bestScoreur = bestScoreur;
    }

    public int getPts_mis() {
        return pts_mis;
    }

    public void setPts_mis(int pts_mis) {
        this.pts_mis = pts_mis;
    }
}
