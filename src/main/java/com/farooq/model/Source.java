package com.farooq.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by farooq khan on 11/12/2018.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Source {
    private Branch branch;
}
