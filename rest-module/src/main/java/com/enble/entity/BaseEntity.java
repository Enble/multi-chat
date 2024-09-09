package com.enble.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.Getter;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity<T> implements Persistable<T> {

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP", length = 6)
    protected Instant createdAt;
    @Column(columnDefinition = "TIMESTAMP", length = 6)
    protected Instant removedAt;

    protected BaseEntity() {
        this.createdAt = Instant.now();
        this.removedAt = null;
    }

    public boolean isRemoved() {
        return this.removedAt != null;
    }

    public Instant remove() {
        this.removedAt = Instant.now();
        return removedAt;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
