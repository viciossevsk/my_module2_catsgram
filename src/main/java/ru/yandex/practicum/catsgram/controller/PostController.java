package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts/{postId}")
    public Post findById(@PathVariable int postId) {
        return postService.findPostById(postId);
    }

    /**
     * ЗАДАНИЕ:
     * Дополните метод findAll класса-контроллера PostController параметрами запроса: sort — строка со значениями asc
     * (от англ. ascending, «восходящий» — по возрастанию) или desc (от англ. descending, «нисходящий» — по убыванию)
     * , page и size — целочисленные, size — больше нуля.
     * <p>
     * Измените код контроллера так, чтобы эндпоинт /posts возвращал 10 самых свежих постов или чётко следовал
     * заданным параметрам.
     */
    @GetMapping("/posts")
    public List<Post> findAll(@RequestParam(defaultValue = "desc") String sort,
                              @RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "10") Integer size) {
        if (!(sort.equals("asc") || sort.equals("desc"))) {
            throw new IllegalArgumentException();
        }
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException();
        }

        Integer from = page * size;
        return postService.findAll(size, sort, from);
    }

    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }
}