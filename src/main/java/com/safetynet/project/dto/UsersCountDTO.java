package com.safetynet.project.dto;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UsersCountDTO {

    private static final Logger logger = LogManager.getLogger(UsersCountDTO.class);

    private List<UserDTO> userList;
    private long countChildren;
    private long countAdult;

    public List<UserDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDTO> userList) {
        this.userList = userList;
    }

    public long getCountChildren() {
        return countChildren;
    }

    public void setCountChildren(long countChildren) {
        this.countChildren = countChildren;
    }

    public long getCountAdult() {
        return countAdult;
    }

    public void setCountAdult(long countAdult) {
        this.countAdult = countAdult;
    }

    public UsersCountDTO(List<UserDTO> userList, long countChildren, long countAdult) {
        this.userList = userList;
        this.countChildren = countChildren;
        this.countAdult = countAdult;
    }
}
