package com.framgia.vhlee.photomatic.util;

import java.io.File;
import java.util.ArrayList;

public class FileSearch {
    /**
     * list directories of directory
     *
     * @param pathDir
     * @return
     */
    public static ArrayList<String> getDirectoryPaths(String pathDir) {
        ArrayList<String> directories = new ArrayList<>();
        File directory = new File(pathDir);
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory() && !file.isHidden()) directories.add(file.getAbsolutePath());
        }
        return directories;
    }

    /**
     * list files of directory
     *
     * @param pathDir
     * @return
     */
    public static ArrayList<String> getFilePaths(String pathDir) {
        ArrayList<String> photos = new ArrayList<>();
        File directory = new File(pathDir);
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile()) photos.add(file.getAbsolutePath());
        }
        return photos;
    }
}
