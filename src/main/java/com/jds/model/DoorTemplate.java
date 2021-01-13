package com.jds.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoorTemplate {
    private RestrictionOfSelectionFields template;
    private RestrictionOfSelectionFields restriction;
}
