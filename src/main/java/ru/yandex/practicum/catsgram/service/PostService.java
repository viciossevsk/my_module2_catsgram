package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();

    private final UserService userService;
    private static Integer generatorId = 0;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Доработайте метод findAll класса-сервиса PostService.
     * Из общего списка публикаций должен возвращаться список
     * первых или последних постов, начиная с заданного порядкового номера.
     * Посты должны быть отсортированы по дате создания. Для этого добавьте
     * в метод аргументы size, sort и from.
     */
    public List<Post> findAll(Integer size, String sort, Integer from) {
        return posts.stream().sorted((p0, p1) -> {
            int comp = p0.getCreationDate().compareTo(p1.getCreationDate()); //прямой порядок сортировки
            if (sort.equals("desc")) {
                comp = -1 * comp; //обратный порядок сортировки
            }
            return comp;
        }).skip(from).limit(size).collect(Collectors.toList());
    }

    public Post create(Post post) {
        User postUser = userService.findUserByEmail(post.getAuthor());
        if (postUser == null) {
            throw new UserNotFoundException(String.format("Пользователь %s не найден", post.getAuthor()));
        }
        post.setId(getNextId());
        posts.add(post);
        return post;
    }

    public Post findPostById(Integer postId) {
        return posts.stream()
                .filter(p -> p.getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Пост № %d не найден", postId)));
    }

    private static Integer getNextId() {
        return generatorId++;
    }
}