package com.springboot.fileUploadSystem.service;

import com.springboot.fileUploadSystem.FileStorageProperties;
import com.springboot.fileUploadSystem.exception.FileStorageException;
import com.springboot.fileUploadSystem.exception.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImp{

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageServiceImp(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);

        } catch (Exception ex) {
            throw new FileStorageException("Could not create a file directory");
        }
    }

    // This service helps to store the file 
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store " + fileName + " , Please try again our services.", ex);
        }
    }

    //This service helps to load the file
    public Resource loadFileResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("This file " + fileName + "is not present in our server.");
            }
        } catch (MalformedURLException ex) {
            // if URL is mal formed then this exception will trigger.
            throw new MyFileNotFoundException("This file " + fileName + "is not present in our server.");

        }
    }
}
