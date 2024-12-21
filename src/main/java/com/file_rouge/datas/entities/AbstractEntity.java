package com.file_rouge.datas.entities;

import java.time.LocalDateTime;


public class AbstractEntity {
    protected int id;

    protected LocalDateTime createAt;

    protected LocalDateTime updatedAt;
    protected Utilisateur creatorUser;

    protected Utilisateur updateUser;

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
