package com.foodlink.backend.model;
public enum Permission {

    // Donation
    DONATION_CREATE,
    DONATION_READ,
    DONATION_VIEW,
    DONATION_VIEW_OWN,
    DONATION_HISTORY_VIEW,
    DONATION_UPDATE,
    DONATION_DELETE,

    // Donor
    DONOR_PROFILE_READ,
    DONOR_PROFILE_UPDATE,
    DONOR_PROFILE_DELETE,

    // NGO
    NGO_PROFILE_READ,
    NGO_PROFILE_UPDATE,
    NGO_PROFILE_DELETE,
    DONATION_VIEW_ACCEPTED,

    // Donation Management
    DONATION_ACCEPT,
    DONATION_REJECT,
    DONATION_LOCK,
    DONATION_REJECT_BY_NGO,

    // Delivery Workflow
    DELIVERY_ASSIGN,
    DELIVERY_PICKUP,
    DELIVERY_START_TRANSIT,
    DELIVERY_COMPLETE,

    // Route & Address Sharing
    ADDRESS_VIEW,
    ADDRESS_SHARE,
    ROUTE_VIEW,
    ETA_VIEW,

    // Feedback
    FEEDBACK_CREATE,
    FEEDBACK_READ,
    FEEDBACK_DELETE,

    // Reports & Analytics
    REPORT_VIEW,
    REPORT_EXPORT,

    // NGO Verification
    NGO_APPROVE,
    NGO_REJECT,
    NGO_SUSPEND,

    // User Management
    USER_READ,
    USER_UPDATE,
    USER_DELETE,

    // Role Management
    ROLE_READ,
    ROLE_CREATE,
    ROLE_UPDATE,
    ROLE_DELETE,

    // Admin
    ADMIN_ACCESS
}
