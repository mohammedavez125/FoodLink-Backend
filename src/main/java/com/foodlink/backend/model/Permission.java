package com.foodlink.backend.model;

public enum Permission {

    // Donation permissions
    DONATION_CREATE,
    DONATION_READ,
    DONATION_UPDATE,
    DONATION_DELETE,

    // User management
    USER_READ,
    USER_UPDATE,

    // Delivery operations
    DELIVERY_ASSIGN,
    DELIVERY_UPDATE_STATUS,

    // Admin operations
    ADMIN_ACCESS,
    NGO_APPROVE_DONATION,
    NGO_CANCEL_DONATION,

    // Feedback
    FEEDBACK_CREATE,
    FEEDBACK_READ
}