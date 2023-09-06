package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;

    @ManyToOne
    private User owner;

    @ManyToMany
    private Set<User> allowedReaders = new HashSet<>();

    @ManyToMany
    private Set<User> allowedEditors = new HashSet<>();
   
	public Document(Integer id, String content, User owner, Set<User> allowedReaders, Set<User> allowedEditors) {
		super();
		this.id = id;
		this.content = content;
		this.owner = owner;
		this.allowedReaders = allowedReaders;
		this.allowedEditors = allowedEditors;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Set<User> getAllowedReaders() {
		return allowedReaders;
	}

	public void setAllowedReaders(Set<User> allowedReaders) {
		this.allowedReaders = allowedReaders;
	}

	public Set<User> getAllowedEditors() {
		return allowedEditors;
	}

	public void setAllowedEditors(Set<User> allowedEditors) {
		this.allowedEditors = allowedEditors;
	}

	public Document() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	


	
    
    

    
}
