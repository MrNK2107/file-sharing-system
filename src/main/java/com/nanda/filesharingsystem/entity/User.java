package com.nanda.filesharingsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private Instant createdAt;

	@Column(nullable = false)
	private Double storageQuota;

	@Column(nullable = false)
	private boolean active = true;

	@OneToMany(mappedBy = "owner")
	private List<Folder> folders = new ArrayList<>();

	@OneToMany(mappedBy = "owner")
	private List<StoredFile> ownedFiles = new ArrayList<>();

	@OneToMany(mappedBy = "sharedWithUser")
	private List<SharePermission> receivedPermissions = new ArrayList<>();

	@OneToOne(mappedBy = "user")
	private StorageUsage storageUsage;

	@PrePersist
	void prePersist() {
		if (createdAt == null) {
			createdAt = Instant.now();
		}
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Double getStorageQuota() {
		return storageQuota;
	}

	public void setStorageQuota(Double storageQuota) {
		this.storageQuota = storageQuota;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<Folder> getFolders() {
		return folders;
	}

	public void setFolders(List<Folder> folders) {
		this.folders = folders;
	}

	public List<StoredFile> getOwnedFiles() {
		return ownedFiles;
	}

	public void setOwnedFiles(List<StoredFile> ownedFiles) {
		this.ownedFiles = ownedFiles;
	}

	public List<SharePermission> getReceivedPermissions() {
		return receivedPermissions;
	}

	public void setReceivedPermissions(List<SharePermission> receivedPermissions) {
		this.receivedPermissions = receivedPermissions;
	}

	public StorageUsage getStorageUsage() {
		return storageUsage;
	}

	public void setStorageUsage(StorageUsage storageUsage) {
		this.storageUsage = storageUsage;
	}
}
