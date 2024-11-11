package com.tove.enterprise_project.authorities;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tove.enterprise_project.authorities.UserPermission.*;

public enum UserRole {

    ADMIN(GET,POST,PUT,DELETE),
    USER(GET,POST);

    private final List<String> permissions;

    UserRole(UserPermission... permissionList) {
        this.permissions = Arrays.stream(permissionList)
                .map(UserPermission::getPermission)
                .toList();
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> simpleGrantedAuthorityList = new ArrayList<>();

        simpleGrantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        simpleGrantedAuthorityList.addAll(getPermissions().stream().map(SimpleGrantedAuthority::new)
                .toList());

        return simpleGrantedAuthorityList;
    }
}
