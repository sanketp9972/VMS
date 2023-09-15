package com.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import com.app.dto.ApiResponse;
import com.app.service.ImageHandlingService;

@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageHandlingController {
	
	@Autowired
	private ImageHandlingService imageHandling;

	
//	@PostMapping(value = "/images/{carId}", consumes = "multipart/form-data")
//	  public ResponseEntity<?> uploadImage(@PathVariable Long carId, @RequestParam MultipartFile imageFile)
//	      throws IOException {
//	    System.out.println("in upload img " + carId);
//	    return ResponseEntity.status(HttpStatus.CREATED).body(imageHandling.uploadImage(carId, imageFile));
//	  }
//	  
	
	
//	   @GetMapping(value="/images/{carId}",produces = {MediaType.IMAGE_GIF_VALUE,MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
//	    public ResponseEntity<?> downloadImage(@PathVariable Long carId) throws IOException {
//	      
//	      System.out.println("in download img ");
//	      return ResponseEntity.ok(imageHandling.downloadImage(carId));
//	    }
//	   
	   
	   
	   
	   
	   @PostMapping(value = "/images/{carId}", consumes = "multipart/form-data")
	    public ResponseEntity<?> uploadImage(@PathVariable Long carId, @RequestParam MultipartFile imageFile)
	            throws IOException {
		   imageHandling.uploadImage(carId, imageFile);
	        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Image Added Successfully"));
	    }
	   
	   
	   @GetMapping(value = "/images/{carId}", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
	    public ResponseEntity<byte[]> downloadImage(@PathVariable Long carId) {
	        try {
	            byte[] imageBytes = imageHandling.downloadImage(carId);

	            if (imageBytes != null && imageBytes.length > 0) {
	                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
	            } else {
	                return ResponseEntity.notFound().build();
	            }
	        } catch (Exception e) {
	            // Handle exceptions, log errors, and return an appropriate response.
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }
}
