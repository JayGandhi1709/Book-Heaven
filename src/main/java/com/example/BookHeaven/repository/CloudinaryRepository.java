package com.example.BookHeaven.repository;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryRepository {
	public Map<String, String> uploadImage(MultipartFile file);
	public List<String> uploadMultipleImages(List<MultipartFile> files);
	public String uploadPDF(MultipartFile file);
	public boolean isImage(MultipartFile file);
	public boolean isPdf(MultipartFile file);
}
