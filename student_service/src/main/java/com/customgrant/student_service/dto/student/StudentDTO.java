package com.customgrant.student_service.dto.student;

import java.time.Instant;
import java.util.UUID;

public class StudentDTO {

    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final Instant lastActivity;
    private final String photoProfileUrl;
    private final String biography;
    private final Boolean notificationsEnabled;
    private final Boolean active;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final Instant deletedAt;

    private StudentDTO(final Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.lastActivity = builder.lastActivity;
        this.photoProfileUrl = builder.photoProfileUrl;
        this.biography = builder.biography;
        this.notificationsEnabled = builder.notificationsEnabled;
        this.active = builder.active;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.deletedAt = builder.deletedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Instant getLastActivity() {
        return lastActivity;
    }

    public String getPhotoProfileUrl() {
        return photoProfileUrl;
    }

    public String getBiography() {
        return biography;
    }

    public Boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    public Boolean getActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public static class Builder {

        private UUID id;
        private String firstName;
        private String lastName;
        private Instant lastActivity;
        private String photoProfileUrl;
        private String biography;
        private Boolean notificationsEnabled;
        private Boolean active;
        private Instant createdAt;
        private Instant updatedAt;
        private Instant deletedAt;

        public Builder id(final UUID id) {
            this.id = id;
            return this;
        }

        public Builder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder lastActivity(final Instant lastActivity) {
            this.lastActivity = lastActivity;
            return this;
        }

        public Builder photoProfileUrl(final String photoProfileUrl) {
            this.photoProfileUrl = photoProfileUrl;
            return this;
        }

        public Builder biography(final String biography) {
            this.biography = biography;
            return this;
        }

        public Builder notificationsEnabled(final Boolean notificationsEnabled) {
            this.notificationsEnabled = notificationsEnabled;
            return this;
        }

        public Builder active(final Boolean active) {
            this.active = active;
            return this;
        }

        public Builder createdAt(final Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(final Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder deletedAt(final Instant deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        public StudentDTO build() {
            return new StudentDTO(this);
        }
    }
}
