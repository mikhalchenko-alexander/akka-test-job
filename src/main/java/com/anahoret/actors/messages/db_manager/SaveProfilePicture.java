package com.anahoret.actors.messages.db_manager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaveProfilePicture implements Comparable<SaveProfilePicture> {

    private long id;

    @Override
    public int compareTo(SaveProfilePicture other) {
        return this.id > other.id ?  1 :
               this.id < other.id ? -1 : 0;
    }
}
