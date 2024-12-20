package com.file_rouge.datas.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode
@MappedSuperclass
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_user_id", updatable = false)  // Changé le nom de la colonne
    private Utilisateur creatorUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_user_id", updatable = false)  // Nouveau nom de colonne
    private Utilisateur updateUser;

    public AbstractEntity() {
        this.createAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Utilisateur getCreatorUser() {
        return creatorUser;
    }

    public void setCreatorUser(Utilisateur creatorUser) {
        this.creatorUser = creatorUser;
    }

    public Utilisateur getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Utilisateur updateUser) {
        this.updateUser = updateUser;
    }
}
