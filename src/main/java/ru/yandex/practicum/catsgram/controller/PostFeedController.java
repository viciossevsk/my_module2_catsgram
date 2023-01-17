package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.FriendsParams;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostFeedController {

    /***
     * Задание для самостоятельной работы
     * Ваши коллеги-фронтендеры что-то перепутали и присылают странную строчку в контроллер, который отвечает за
     * отображение последних постов друзей.
     * "{\"sort\":\"desc\",\"size\":3,\"friends\":[\"puss@boots.com\",\"cat@dogs.net\",\"purrr@luv.me\"]}"
     * Нужно скорее решить проблему, потому что эта важная функция должна была появиться в проекте Catsgram ещё вчера!
     * Добавьте контроллер PostFeedController, который будет обрабатывать POST-запрос на эндпоинт /feed/friends.
     * Запрос должен принимать тело запроса @RequestBody в параметр типа String.
     * Используйте ObjectMapper, чтобы преобразовать входящий результат.
     * Верните список постов List<Post>.
     * Подсказки
     * Строка похожа на JSON-объект, но довольно странный — как будто его преобразовали дважды.
     * Без ObjectMapper здесь не обойтись.
     * Возможно, вам понадобится написать простой Java-класс для преобразования параметров запроса.
     * Не забудьте проверить себя по авторскому решению из ветки
     * https://github.com/praktikum-java/module2_catsgram/tree/spring-request_friends-will-be-friends
     */

    private final PostService postService;

    @PostMapping(value = "/feed/friends")
    public List<Post> getFriendsFeed(@RequestBody String params) {
        ObjectMapper objectMapper = new ObjectMapper();
        FriendsParams friendsParams;

        try {
            String paramsForString = objectMapper.readValue(params, String.class);
            friendsParams = objectMapper.readValue(paramsForString, FriendsParams.class);


        } catch (JsonProcessingException e) {
            throw new RuntimeException("невалидный формат json", e);
        }

        if (friendsParams != null) {
            List<Post> result = new ArrayList<>();
            for (String friend : friendsParams.getFriends()) {
                result.addAll(postService.findAllByUserEmail(friend, friendsParams.getSize(), friendsParams.getSort()));
            }
            return result;
        } else {
            throw new RuntimeException("неверно заполнены параметры");
        }


    }


}
