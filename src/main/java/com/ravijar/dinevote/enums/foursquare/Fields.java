package com.ravijar.dinevote.enums.foursquare;

import lombok.Getter;

@Getter
public enum Fields {
    PLACE_SEARCH("fsq_id,distance,name,geocodes,closed_bucket");

    private String requiredFields;

    Fields(String requiredFields) {
        this.requiredFields = requiredFields;
    }
}
