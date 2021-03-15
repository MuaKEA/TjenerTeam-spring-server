package com.example.demo.facebook.model;

import javax.persistence.*;

@Entity
@Table(name = "postCode")
public class PostCodes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer postCode;

    public PostCodes() {

    }

    public PostCodes(Integer postCode) {
        this.postCode = postCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    @Override
    public String toString() {
        return "Postnr: " + postCode;
    }
}
