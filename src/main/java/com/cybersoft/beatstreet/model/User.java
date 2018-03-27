package com.cybersoft.beatstreet.model;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Users")
@JsonIgnoreProperties("beats")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;
    private String profileImagePath;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    @ManyToMany( cascade = CascadeType.ALL, fetch = FetchType.EAGER )
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Beat> beats = new ArrayList<>();


    public void addBeat(Beat beat) {
        beats.add(beat);
    }


    // GETTERS AND SETTERS


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public List<Beat> getBeats() {
        return beats;
    }

    public void setBeats(List<Beat> beats) {
        this.beats = beats;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRoles(String roleName) {
        if (this.roles == null || this.roles.isEmpty())
            this.roles = new HashSet<>();
        this.roles.add(new Role(roleName));
    }
}
