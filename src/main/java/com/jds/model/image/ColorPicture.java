package com.jds.model.image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColorPicture implements Comparable<ColorPicture> {
    private int id;
    private String name;
    private String path;

    public ColorPicture(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }


    public int compareTo(ColorPicture colorPicture) {
        return colorPicture.getName().toUpperCase().compareTo(this.getName().toUpperCase());
    }
}
