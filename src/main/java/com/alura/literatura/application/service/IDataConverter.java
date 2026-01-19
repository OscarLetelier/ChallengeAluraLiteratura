package com.alura.literatura.application.service;

public interface IDataConverter {
    <T> T getData(String json, Class<T> classType);
}
