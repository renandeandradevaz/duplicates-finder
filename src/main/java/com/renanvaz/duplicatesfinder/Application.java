package com.renanvaz.duplicatesfinder;

import com.renanvaz.duplicatesfinder.service.DuplicatesFinderService;

public class Application {

    public static void main(String[] args) throws Exception {

        new DuplicatesFinderService().process();
    }
}