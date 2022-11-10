package models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PhotoResponse {
    private String photo;
    private int server;
    private String hash;
}
