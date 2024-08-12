package com.example.BookHeaven.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.example.BookHeaven.repository.CloudinaryRepository;

@Service
public class CloudinaryService implements CloudinaryRepository {

	@Autowired
	private Cloudinary cloudinary;

	public Map<String, String> uploadImage(MultipartFile file) {
		try {
			return this.cloudinary.uploader().upload(file.getBytes(), Map.of("folder", "book images"));
		} catch (IOException e) {
			throw new RuntimeException("Image uploading failed");
		}
	}

	public List<String> uploadMultipleImages(List<MultipartFile> files) {
		List<String> urls = new ArrayList<>();
		// List<Map> uploadResults = new ArrayList<>();

		for (MultipartFile file : files) {
			if (isImage(file)) {
				try {
					Map<String, String> uploadResult = this.cloudinary.uploader().upload(file.getBytes(),
							Map.of("folder", "book images"));
					// uploadResults.add(uploadResult);
					String url = uploadResult.get("url");
					urls.add(url);
				} catch (IOException e) {
					throw new RuntimeException("Image uploading failed", e);
				}
			} else {
				throw new RuntimeException("Invalid file type: " + file.getOriginalFilename());

			}
		}

		return urls;

	}

	public String uploadPDF(MultipartFile file) {
		if (isPdf(file)) {
			try {
				Map<String, String> uploadResult = this.cloudinary.uploader().upload(file.getBytes(),
						Map.of("folder", "book pdf"));
				return uploadResult.get("url");
			} catch (IOException e) {
				throw new RuntimeException("Image uploading failed");
			}
		} else {
			throw new RuntimeException("Invalid file type: " + file);
		}
	}

	public boolean isImage(MultipartFile file) {
		String mimeType = file.getContentType();
		return mimeType != null && mimeType.startsWith("image/");
	}

	public boolean isPdf(MultipartFile file) {
		String mimeType = file.getContentType();
		return mimeType != null && mimeType.equals("application/pdf");
	}

}
