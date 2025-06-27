package com.example.csv_reader.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldMapping {
    private String sourceField;
    private String destinationField;
}
