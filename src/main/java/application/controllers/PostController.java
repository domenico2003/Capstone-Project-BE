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

import application.entities.Post;
import application.payloads.PostPayload;
import application.services.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
	@Autowired
	PostService postService;

	// crud per post
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void findByIdAndDelete(@PathVariable String id) {
		postService.findByIdAndDelete(id);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Post findByid(@PathVariable String id) {
		return postService.findById(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Post findByidAndUpdate(@PathVariable String id, @RequestBody PostPayload payload) {
		return postService.findByIdAndUpdate(id, payload);
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.OK)
	public Post create(@RequestBody PostPayload payload) {
		return postService.create(payload);
	}
	// endpoint custom

	@GetMapping("")
	@ResponseStatus(HttpStatus.OK)
	public Page<Post> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(required = false) String userId,
			@RequestParam(required = false) String gruppoId) {
		if (userId != null) {
			return postService.findByUtente(userId, page);
		} else if (gruppoId != null) {
			return postService.findByGruppo(gruppoId, page);
		} else {
			return postService.findAll(page);
		}
	}

	@PostMapping("/miPiace/{postId}")
	@ResponseStatus(HttpStatus.OK)
	public void create(@PathVariable String postId) {
		postService.addMiPiace(postId);
	}
}
