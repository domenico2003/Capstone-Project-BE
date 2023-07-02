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

import application.entities.Videogiochi;
import application.payloads.VideogiochiPayload;
import application.services.VideogiochiService;

@RestController
@RequestMapping("/videogioco")
public class VideogiochiController {
	@Autowired
	VideogiochiService videogiochiService;

	// crud per videogioco
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void findByIdAndDelete(@PathVariable String id) {
		videogiochiService.findByIdAndDelete(id);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Videogiochi findByid(@PathVariable String id) {
		return videogiochiService.findById(id);
	}

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public Page<Videogiochi> findAll(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "id") String order) {
		return videogiochiService.findAll(page, order);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Videogiochi findByidAndUpdate(@PathVariable String id, @RequestBody VideogiochiPayload payload) {
		return videogiochiService.findByIdAndUpdate(id, payload);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.OK)
	public Videogiochi create(@RequestBody VideogiochiPayload payload) {
		return videogiochiService.create(payload);
	}
	// endpoint custom
}
