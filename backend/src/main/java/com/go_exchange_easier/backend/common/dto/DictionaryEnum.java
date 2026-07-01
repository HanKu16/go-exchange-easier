package com.go_exchange_easier.backend.common.dto;

public interface DictionaryEnum {

    String getLabel();

    default DictionaryEntry toEntry() {
        return new DictionaryEntry(this.toString(), getLabel());
    }

}