package com.segroup2.progettosegroup2.Actions;

import com.segroup2.progettosegroup2.TestFX;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActionCopyFileTest extends TestFX {
    private File fileExists;
    private File fileExistsOtherDirectory;
    private File otherFileExists;
    private File fileNotExists;
    private File directoryExists;
    private File otherDirectoryExists;
    private File directoryNotExists;

    @BeforeAll
    void createFiles() throws IOException {
        File source= Files.createTempDirectory("Test_source").toFile();

        directoryExists= Files.createTempDirectory("Test_destination_exists").toFile();

        otherDirectoryExists= Files.createTempDirectory("Test_destination_exists_other").toFile();

        directoryNotExists= Files.createTempDirectory("Test_destination_not_exists").toFile();
        FileUtils.deleteDirectory(directoryNotExists);

        String fileExistsName= "Test_file_exists_empty";
        String fileExistsExtension= ".txt";
        fileExists= File.createTempFile(fileExistsName, fileExistsExtension, source);

        File otherDirectory= Files.createTempDirectory(source.toPath(), "Other directory").toFile();

        fileExistsOtherDirectory= File.createTempFile(fileExistsName, fileExistsExtension, otherDirectory);

        otherFileExists= File.createTempFile("Test_file_exists_other_empty", ".txt", source);

        fileNotExists= source.toPath().resolve("Test_file_not_exists_empty.txt").toFile();
    }

    @Test
    void constructorFailure(){
        //caso almeno uno dei due parametri in ingressi nulli
        assertThrowsExactly(IllegalArgumentException.class, ()-> new ActionCopyFile(null, null) );
        assertThrowsExactly(IllegalArgumentException.class, ()-> new ActionCopyFile(fileExists, null) );
        assertThrowsExactly(IllegalArgumentException.class, ()-> new ActionCopyFile(null, directoryExists) );
    }

    @Test
    void executeTrue() {
        ActionCopyFile action;

        action= new ActionCopyFile(fileExists, directoryExists);
        assertTrue(action.execute());

        action= new ActionCopyFile(fileExists, directoryNotExists);
        assertTrue(action.execute());
    }

    @Test
    void executeThrowException() {
        //caso file destinazione già esistente
        assertThrowsExactly(RuntimeException.class, () -> new ActionCopyFile(fileExists, otherFileExists).execute() );
        assertThrowsExactly(RuntimeException.class, () -> new ActionCopyFile(fileExists, fileExistsOtherDirectory).execute() );

        //caso file sorgente non esistente
        assertThrowsExactly(RuntimeException.class, () -> new ActionCopyFile(fileNotExists, directoryExists).execute() );
        assertThrowsExactly(RuntimeException.class, () -> new ActionCopyFile(fileNotExists, directoryNotExists).execute() );

        //caso file sorgente è una cartella (che può esistente o meno)
        assertThrowsExactly(RuntimeException.class, () -> new ActionCopyFile(otherDirectoryExists, directoryExists).execute() );
        assertThrowsExactly(RuntimeException.class, () -> new ActionCopyFile(directoryNotExists, directoryExists).execute() );
    }
}