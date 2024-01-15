package com.juneox.marketspace.domain.raw.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "file_meta_info")
@Entity
public class FileMetaInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fm_info_id")
    Long id;

    @Column(name = "file_name")
    String fileName;
    @Column(name = "file_hash_s256")
    String fileHashS256;

    @Column(name = "created_at")
    @CreationTimestamp
    Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    @UpdateTimestamp
    Instant updatedAt;

}
