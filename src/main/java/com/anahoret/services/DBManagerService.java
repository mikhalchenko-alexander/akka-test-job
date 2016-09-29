package com.anahoret.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DBManagerService {

    private static final Logger LOG = LoggerFactory.getLogger(DBManagerService.class);

    public void saveUserProfile(long id) {
        LOG.info("saveUserProfile(" + id + ")");
    }

    public void saveUserConnection(long id) {
        LOG.info("saveUserConnection(" + id + ")");
    }

    public void saveUserProfilePicture(long id) {
        LOG.info("saveUserProfilePicture(" + id + ")");
    }
}
