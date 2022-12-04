package com.example.task;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {
	@Autowired
	private final UploadService uploadService;
	
	UploadController(UploadService uploadService) {
	    this.uploadService = uploadService;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST,
		      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)		   
	public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
		return uploadService.fileUploadService(file);
	}    
	
	@RequestMapping(value = "/upload/{id}")
	public ResponseEntity<Object> getRecord(@PathVariable("id") String id) {
	   return uploadService.getRecordService(id);
	}
	
	@RequestMapping(value = "/upload/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteRecord(@PathVariable("id") String id) { 
			return uploadService.deleteRecord(id);
	}	
}
