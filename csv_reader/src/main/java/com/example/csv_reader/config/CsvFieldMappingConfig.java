package com.example.csv_reader.config;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CsvFieldMappingConfig {
    private List<FieldMapping> mapping;
}
