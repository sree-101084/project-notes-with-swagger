package com.sree.easynotes.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sree.easynotes.exception.ResourceNotFoundException;
import com.sree.easynotes.model.NotesModel;
import com.sree.easynotes.repo.NotesRepo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api
@RestController
public class NotesMainController {

	@Autowired
	private NotesRepo repository;

	// Get All Notes
	@GetMapping("/api/notes")
	@ApiOperation(value = "Respond all Notes Detailed List", notes = "Returns a JSON object with List of all notes.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Service not available"),
			@ApiResponse(code = 500, message = "Unexpected Runtime error") })
	public List<NotesModel> getAllNotes() {

		return repository.findAll();
	}

	// Create a new Note
	@PostMapping("/api/newnotes")
	@ApiOperation(value = "Create Notes ", notes = "Accept Notes data in JSON format and return the model")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Service not available"),
			@ApiResponse(code = 500, message = "Unexpected Runtime error") })
	public NotesModel createNewNotes(@Valid @RequestBody NotesModel notesNewModel) {
		return repository.saveAndFlush(notesNewModel);

	}

	// Get a Single Note

	@GetMapping("/api/notes/{id}")
	@ApiOperation(value = "Get notes details for specific id", notes = "Returns a JSON object with List of notes matching the id input.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Service not available"),
			@ApiResponse(code = 500, message = "Unexpected Runtime error") })
	public Optional<NotesModel> getNotesById(@PathVariable String id) {

		Optional<NotesModel> notesData = repository.findById(Long.parseLong(id));
		if (notesData.isPresent()) {
			return notesData;
		}
		return notesData;
	}
	// Update a Note

	@PutMapping("/api/notes/{id}")
	@ApiOperation(value = "Update specific Notes details based on id provided ", notes = "Update Notes.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Service not available"),
			@ApiResponse(code = 500, message = "Unexpected Runtime error") })
	public NotesModel updateNotesById(@PathVariable String id, @Valid @RequestBody NotesModel noteDetails) {

		NotesModel notesData = repository.findById(Long.parseLong(id))
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id", id));

		notesData.setTitle(noteDetails.getTitle());
		notesData.setContent(noteDetails.getContent());

		NotesModel updatedData = repository.saveAndFlush(notesData);
		return updatedData;
	}

	// Delete a Note

	@DeleteMapping("/api/notes/{id}")
	@ApiOperation(value = "Delete Notes", notes = "Delete notes data with specific id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 404, message = "Service not available"),
			@ApiResponse(code = 500, message = "Unexpected Runtime error") })
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
		NotesModel note = repository.findById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

		repository.delete(note);

		return ResponseEntity.ok().build();
	}
}
