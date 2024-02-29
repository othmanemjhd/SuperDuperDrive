package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;
    private UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }


    public int uploadFile(File file){
        return this.fileMapper.insertFile(file);
    }
    public List<File> getAllFiles(){
        return this.fileMapper.getAllFilesByUserId(this.userService.getCurrentUserId());
    }

    public boolean isAlreadyUploaded(File file){
        return this.fileMapper.getFileByFilename(file.getFilename()) != null;
    }
    public void deleteFile(int fileId){
        this.fileMapper.deleteFile(fileId);

    }

    public File getFileById(int id){
        return  this.fileMapper.getFileById(id);
    }
}
