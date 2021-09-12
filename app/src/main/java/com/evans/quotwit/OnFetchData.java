package com.evans.quotwit;

import java.util.List;

import models.Headlines;

public interface OnFetchData {
    void onFetchData(List<Headlines> list, String message);
    void onError (String message);
}
