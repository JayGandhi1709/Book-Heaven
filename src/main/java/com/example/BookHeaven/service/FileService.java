package com.example.BookHeaven.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {

    // Convert MultipartFile to byte array
    public byte[] convertToByteArray(MultipartFile file) throws IOException {
        return file.getBytes();
    }
    
    // Optionally, add a method to handle byte array to file conversion if needed
    public void saveToFile(byte[] data, String filePath) throws IOException {
        // Implementation for saving byte array to a file (if needed)
    }
}
