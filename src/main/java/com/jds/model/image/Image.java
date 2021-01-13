package com.jds.model.image;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    private int id;
    private String name;
    private String pathPictures;
}
