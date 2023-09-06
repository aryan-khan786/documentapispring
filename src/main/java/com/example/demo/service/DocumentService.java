package com.example.demo.service;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DocumentRepository;
import com.example.demo.dao.UserRepository;
import com.example.demo.exception.DocumentNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Document;
import com.example.demo.model.User;

@Service
public class DocumentService {
	
	 @Autowired
	    private DocumentRepository documentRepository;
	 
	 @Autowired
	 private UserRepository userRepository;

	 
	 public Document createDocument(String content, String username)  throws Exception{
	        User owner = userRepository.findByUsername(username);
	        if (owner == null) {
	            throw new UserNotFoundException("User not found");
	        }

	        Document document = new  Document();
	        document.setContent(content);
	        document.setOwner(owner);
	        return documentRepository.save(document);
	    }

	    public Document getDocumentById(Integer id) {
	        return documentRepository.findById(id).orElse(null);
	    }

	    public List<Document> getAllDocuments() {
	        return documentRepository.findAll();
	    }

	    public void grantReadAccess(Integer documentId, String username) throws Exception {
	        Document document = documentRepository.findById(documentId).orElse(null);
	        if (document == null) {
	            throw new DocumentNotFoundException("Document not found");
	        }

	        User user = userRepository.findByUsername(username);
	        if (user == null) {
	            throw new UserNotFoundException("User not found");
	        }

	        if (document.getOwner().getUsername().equals(username)) {
	            document.getAllowedReaders().add(user);
	            documentRepository.save(document);
	        } else {
	            throw new AccessDeniedException("Access denied: Only the owner can grant read access.");
	        }
	    }

	    public void deleteDocument(Integer documentId, String username)  throws Exception{
	        Document document = documentRepository.findById(documentId).orElse(null);
	        if (document == null) {
	            throw new DocumentNotFoundException("Document not found");
	        }

	        if (document.getOwner().getUsername().equals(username)) {
	            documentRepository.delete(document);
	        } else {
	            throw new AccessDeniedException("Access denied: Only the owner can delete the document.");
	        }
	    }

	    public void grantEditAccess(Integer documentId, String username, String ownerUsername) throws Exception{
	        Document document = documentRepository.findById(documentId).orElse(null);
	        if (document == null) {
	            throw new DocumentNotFoundException("Document not found");
	        }

	        User user = userRepository.findByUsername(username);
	        if (user == null) {
	            throw new UserNotFoundException("User not found");
	        }

	        User owner = userRepository.findByUsername(ownerUsername);
	        if (owner == null) {
	            throw new UserNotFoundException("Owner not found");
	        }

	        if (document.getOwner().getUsername().equals(owner.getUsername())) {
	            document.getAllowedEditors().add(user);
	            documentRepository.save(document);
	        } else {
	            throw new AccessDeniedException("Access denied: Only the owner can grant edit access.");
	        }
	    }

}
