package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }


    public int uploadFile(File file){
        return this.fileMapper.insertFile(file);
    }
    public List<File> getAllFiles(Integer userid){
        return this.fileMapper.getAllFilesByUserId(userid);
    }

    public boolean isAlreadyUploaded(File file){
        return this.fileMapper.getFileByFilename(file.getFilename()) != null;
    }
    public void deleteFile(int fileId){
        this.fileMapper.deleteFile(fileId);

    }
}
