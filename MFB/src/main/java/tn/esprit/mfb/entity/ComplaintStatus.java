package tn.esprit.mfb.entity;

public enum ComplaintStatus {
    CREATED,
    DATAVERIFICATION,
    TaskPlanning,//for admin
    Execution, //for admin
    TESTING, //for admin
    ANSWEARED,//for additional data use (so list of responsescomplaint
    VERIFIED,
    REFUSED,

    CANCELLED,
}
