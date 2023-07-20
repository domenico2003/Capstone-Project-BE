package application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import application.entities.Commenti;
import application.payloads.CommentoPayload;
import application.services.CommentiService;

@RestController
@RequestMapping("/commento")
public class CommentiController {

	@Autowired
	CommentiService commentiService;

	// crud per commenti
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void findByIdAndDelete(@PathVariable String id) {
		commentiService.findByIdAndDelete(id);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Commenti findByid(@PathVariable String id) {
		return commentiService.findById(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Commenti findByidAndUpdate(@PathVariable String id, @RequestBody CommentoPayload payload) {
		return commentiService.findByIdAndUpdate(id, payload);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.OK)
	public Commenti create(@RequestBody CommentoPayload payload) {

		return commentiService.create(payload);
	}
	// endpoint custom

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public Page<Commenti> findAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "id") String order, @RequestParam(required = false) String postId) {

		if (postId != null) {
			return commentiService.findByIdPost(postId, page, order);
		} else {
			return commentiService.findAll(page, order);
		}
	}
}
