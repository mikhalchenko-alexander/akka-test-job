package com.anahoret.services;

import org.springframework.stereotype.Service;

@Service
public class DBManagerService {

    public void saveUserProfile(long id) {
        System.out.println("saveUserProfile(" + id + ")");
    }

    public void saveUserConnection(long id) {
        System.out.println("saveUserConnection(" + id + ")");
    }

    public void saveUserProfilePicture(long id) {
        System.out.println("saveUserProfilePicture(" + id + ")");
    }
}
