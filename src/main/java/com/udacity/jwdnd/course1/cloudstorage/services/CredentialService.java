package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {


    private  UserService userService;
    private CredentialMapper credentialMapper;

    public CredentialService(UserService userService, CredentialMapper credentialMapper) {
        this.userService = userService;
        this.credentialMapper = credentialMapper;
    }

    public List<Credential> getAllCredentials(){
        return this.credentialMapper.getAllCredentialsByUser(this.userService.getCurrentUserId());

    }

    public int addCredential(Credential c){
        return this.credentialMapper.insertCredential(c);
    }

    public void deleteCredential(int id){
         this.credentialMapper.deleteCredential(id);
    }
    public int updateCredential(Credential c ){
        return this.credentialMapper.updateCredential(c);
    }

    public Credential getCredentialsById(Integer credentialid) {
        return  this.credentialMapper.getCredentialById(credentialid);
    }
}
