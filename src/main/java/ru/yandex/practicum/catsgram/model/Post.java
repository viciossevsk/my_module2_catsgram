package ru.yandex.practicum.catsgram.model;

import lombok.Data;

import java.time.Instant;

@Data
public class Post {

    private Integer id;
    private final String author; // автор
    private final Instant creationDate = Instant.now(); // дата создания
    private String description; // описание
    private String photoUrl; // url-адрес фотографии

    /*
     * создали конструктор при лумбоковском @Data
     * так как лумбок создавал конструктор не того вида,
     * который нам нужен.
     * не все поля помечены final
     * */
    public Post(String author, String description, String photoUrl) {
        this.author = author;
        this.description = description;
        this.photoUrl = photoUrl;
    }
}