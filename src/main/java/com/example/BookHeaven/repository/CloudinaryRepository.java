package com.example.BookHeaven.repository;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryRepository {
	public String uploadImage(MultipartFile file, String folderName);

	public List<String> uploadMultipleImages(List<MultipartFile> files);

	public String uploadPDF(MultipartFile file);

	public boolean isImage(MultipartFile file);

	public boolean isPdf(MultipartFile file);
}
