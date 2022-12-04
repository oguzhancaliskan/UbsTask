package com.example.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component(value = "uploadService")
public class UploadService {
	public String filename;
	
	private static Map<String, Record> recordRepo = new HashMap<>();
		
	public static boolean isValidDate(final String date) {
        boolean valid = false;

        try {
            LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
            valid = true;
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            valid = false;
        }
        return valid;
    }
	
	public String fileUploadService(MultipartFile file) throws IOException {
		filename = "./src/main/resources/" + file.getOriginalFilename();
		File convertFile = new File(filename);
		convertFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		try {
			List<String> allLines = Files.readAllLines(Paths.get(filename));
			allLines.remove(0);  //removes header UPDATED_TIMESTAMP from list

			for (String line: allLines) {
				StringTokenizer st = new StringTokenizer(line, ",");  
				Record record = new Record();
				if (st.hasMoreTokens()) {
					String primaryKey = st.nextToken();
					if (primaryKey.contentEquals(" "))  //validate primary key
						continue;  //skip invalid primary key
					record.setPrimaryKey(primaryKey);
				}
				if (st.hasMoreTokens()) 
					record.setName(st.nextToken());
				if (st.hasMoreTokens()) 
					record.setDescription(st.nextToken());
				if (st.hasMoreTokens()) {
					String date = st.nextToken();
					if (!isValidDate(date)) 
						continue;  //skip invalid date
					record.setUpdatedTimestamp(date);
				}
				recordRepo.put(record.getPrimaryKey(), record);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "File is uploaded successfully";
	}  
	
	public ResponseEntity<Object> getRecordService(String id) {
		return new ResponseEntity<>(recordRepo.get(id), HttpStatus.OK);		
	}
	
	public ResponseEntity<Object> deleteRecord(String id) { 
		recordRepo.remove(id);    
	    return new ResponseEntity<>("Record is deleted successsfully", HttpStatus.OK);
	}
}
