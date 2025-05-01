package com.innervoice.common.domain;

public enum S3DirectoryName {

    CHARACTER_IMAGE("/character-image");

    private final String directoryName;

    S3DirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getDirectoryName() {
        return directoryName;
    }
}
