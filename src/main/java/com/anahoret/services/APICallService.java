package com.anahoret.services;

import org.springframework.stereotype.Service;

@Service
public class APICallService {

    public long userProfilesAPI(long id) {
        System.out.println("userProfilesAPI(" + id + ")");
        return id;
    }

    public long userConnectionAPI(long id) {
        System.out.println("userConnectionAPI(" + id + ")");
        return id;
    }

    public long userProfilePictureAPI(long id) {
        System.out.println("userProfilePictureAPI(" + id + ")");
        return id;
    }

}
