package com.customgrant.student_service.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "last_activity")
    private Instant lastActivity;

    @Column(name = "photo_profile_url", length = 255)
    private String photoProfileUrl;

    @Column(name = "biography", columnDefinition = "TEXT")
    private String biography;

    @Column(name = "notifications_enabled", nullable = false)
    private Boolean notificationsEnabled;

    @Column(name = "is_active", nullable = false)
    private Boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updateAt;

    @Column(name = "deleted_at")
    private Instant deleteAt;

    public Student() { }

    private Student(
            final UUID id,
            final String firstName,
            final String lastName,
            final Instant lastActivity,
            final String photoProfileUrl,
            final String biography,
            final Boolean notificationsEnabled,
            final Boolean isActive,
            final Instant createdAt,
            final Instant updateAt,
            final Instant deleteAt
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastActivity = lastActivity;
        this.photoProfileUrl = photoProfileUrl;
        this.biography = biography;
        this.notificationsEnabled = notificationsEnabled;
        this.active = isActive;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.deleteAt = deleteAt;
    }
    public static Student of(
            final UUID id,
            final String firstName,
            final String lastName,
            final Instant lastActivity,
            final String photoProfileUrl,
            final String biography,
            final Boolean notificationsEnabled,
            final boolean isActive
    ) {
        final Instant deletedAt = isActive ? null : Instant.now();
        return new Student(
                id,
                firstName,
                lastName,
                lastActivity,
                photoProfileUrl,
                biography,
                notificationsEnabled,
                isActive,
                Instant.now(),
                Instant.now(),
                deletedAt
        );
    }

    public static Student from(final Student student) {
        return Student.of(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getLastActivity(),
                student.getPhotoProfileUrl(),
                student.getBiography(),
                student.getNotificationsEnabled(),
                student.getIsActive()
        );
    }

    public String fullName() {
        return getFirstName() + " " + getLastName();
    }

    public Instant getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Instant deleteAt) {
        this.deleteAt = deleteAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Instant updateAt) {
        this.updateAt = updateAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsActive() {
        return active;
    }

    public void setIsActive(Boolean active) {
        active = active;
    }

    public Boolean getNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(Boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getPhotoProfileUrl() {
        return photoProfileUrl;
    }

    public void setPhotoProfileUrl(String photoProfileUrl) {
        this.photoProfileUrl = photoProfileUrl;
    }

    public Instant getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Instant lastActivity) {
        this.lastActivity = lastActivity;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
