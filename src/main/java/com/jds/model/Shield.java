package com.jds.model;

import com.jds.model.image.Color;
import com.jds.model.image.Image;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Shield {
   private Color color;
   private Image design;
}

