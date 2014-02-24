package simpleblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import simpleblog.model.Post;
import simpleblog.service.BlogService;

@Controller
public class PostController {
	
	@Autowired
	BlogService blogService;

	@ModelAttribute("posts")
	public Iterable<Post> posts() {
	    return blogService.getItems();
	}
	
	@RequestMapping("/")
	public String newPost(ModelMap model) {
		model.put("post", new Post());
		return "posts";
	}
	
	@RequestMapping("/{id}/edit")
	public String editPost(@PathVariable("id") Post post, ModelMap model) {
		model.put("post", post);
		return "posts";
	}
	
	@RequestMapping("/{id}/delete")
	public String deletePost(@PathVariable Long id) {
		blogService.deletePostById(id);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String save(@Valid Post post, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			return "posts";
		}
		blogService.save(post);
		return "redirect:/";
	}
	
	@RequestMapping("/ping")
	public @ResponseBody String ping() {
		return "OK";
	}
	
}
