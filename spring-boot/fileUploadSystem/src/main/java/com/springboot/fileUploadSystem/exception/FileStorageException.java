package com.springboot.fileUploadSystem.exception;

import com.springboot.fileUploadSystem.FileStorageProperties;

public class FileStorageException extends RuntimeException{

    public FileStorageException(String message){
        super(message);
    }

    public FileStorageException(String message,Throwable cause){
        super(message,cause);

    }

}
