package com.anahoret.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class APICallService {

    private static final Logger LOG = LoggerFactory.getLogger(APICallService.class);

    public long userProfilesAPI(long id) {
        LOG.info("userProfilesAPI(" + id + ")");
        return id;
    }

    public long userConnectionAPI(long id) {
        LOG.info("userConnectionAPI(" + id + ")");
        return id;
    }

    public long userProfilePictureAPI(long id) {
        LOG.info("userProfilePictureAPI(" + id + ")");
        return id;
    }

}
