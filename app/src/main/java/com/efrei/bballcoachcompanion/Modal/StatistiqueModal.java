package com.efrei.bballcoachcompanion.Modal;

public class StatistiqueModal {
    private int id;
    private int id_match;
    private String equipe;
    private int q1;
    private int q2;
    private int q3;
    private int q4;
    private int tt_ast;
    private int tt_ctr;
    private int tt_rbs;
    private int tt_pf;
    private int tt_to;

    public StatistiqueModal(int id_match, String equipe, int q1, int q2, int q3, int q4, int tt_ast, int tt_ctr, int tt_rbs, int tt_pf, int tt_to) {
        this.id_match = id_match;
        this.equipe = equipe;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.tt_ast = tt_ast;
        this.tt_ctr = tt_ctr;
        this.tt_rbs = tt_rbs;
        this.tt_pf = tt_pf;
        this.tt_to = tt_to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_match() {
        return id_match;
    }

    public void setId_match(int id_match) {
        this.id_match = id_match;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public int getQ1() {
        return q1;
    }

    public void setQ1(int q1) {
        this.q1 = q1;
    }

    public int getQ2() {
        return q2;
    }

    public void setQ2(int q2) {
        this.q2 = q2;
    }

    public int getQ3() {
        return q3;
    }

    public void setQ3(int q3) {
        this.q3 = q3;
    }

    public int getQ4() {
        return q4;
    }

    public void setQ4(int q4) {
        this.q4 = q4;
    }

    public int getTt_ast() {
        return tt_ast;
    }

    public void setTt_ast(int tt_ast) {
        this.tt_ast = tt_ast;
    }

    public int getTt_ctr() {
        return tt_ctr;
    }

    public void setTt_ctr(int tt_ctr) {
        this.tt_ctr = tt_ctr;
    }

    public int getTt_rbs() {
        return tt_rbs;
    }

    public void setTt_rbs(int tt_rbs) {
        this.tt_rbs = tt_rbs;
    }

    public int getTt_pf() {
        return tt_pf;
    }

    public void setTt_pf(int tt_pf) {
        this.tt_pf = tt_pf;
    }

    public int getTt_to() {
        return tt_to;
    }

    public void setTt_to(int tt_to) {
        this.tt_to = tt_to;
    }
}
