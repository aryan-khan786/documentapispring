package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.DocumentRepository;
import com.example.demo.model.User;
import com.example.demo.service.DocumentService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/main")
public class MainController {

	@Autowired
	private UserService userService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private DocumentRepository documentRepository;

	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody User user) {
		User createdUser = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Integer id) {
		User user = userService.getUserById(id);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/by-username/{username}")
	public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
		User user = userService.getUserByUsername(username);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/{id}/grant-read-access")
	public ResponseEntity<?> grantReadAccess(@PathVariable Integer id, Authentication authentication) {
		// Get the authenticated username from Authentication object
		String username = authentication.getUsername();

		try {
			documentService.grantReadAccess(id, username);
			return ResponseEntity.ok("Read access granted.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}

	@PostMapping("/{id}/grant-edit-access")
	public ResponseEntity<?> grantEditAccess(@PathVariable Integer id, @RequestParam String username,
			Authentication authentication) {
		// Get the authenticated username from Authentication object
		String ownerUsername = authentication.getUsername();

		try {
			documentService.grantEditAccess(id, username, ownerUsername);
			return ResponseEntity.ok("Edit access granted.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDocument(@PathVariable Integer id, Authentication authentication) {
		// Get the authenticated username from Authentication object
		String username = authentication.getUsername();

		try {
			documentService.deleteDocument(id, username);
			return ResponseEntity.ok("Document deleted.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}

}
