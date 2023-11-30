package com.segroup2.progettosegroup2.Actions;

import com.segroup2.progettosegroup2.MainApplication;
import com.segroup2.progettosegroup2.TestFX;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActionCopyFileTest extends TestFX {
    private File base;
    private File fileExists;
    private File fileExistsOtherDirectory;
    private File otherFileExists;
    private File fileNotExists;
    private File directoryExists;
    private File otherDirectoryExists;
    private File directoryNotExists;

    @BeforeAll
    void createFiles() throws URISyntaxException, IOException {
        base= new File( MainApplication.class.getResource("").toURI().resolve("ActionCopyFileTest") );
        FileUtils.deleteDirectory(base);
        base.mkdir();

        File source= base.toPath().resolve("Test_source").toFile();
        source.mkdir();

        directoryExists= base.toPath().resolve("Test_destination_exists").toFile();
        directoryExists.mkdir();

        otherDirectoryExists= base.toPath().resolve("Test_destination_exists_other").toFile();
        otherDirectoryExists.mkdir();

        directoryNotExists= base.toPath().resolve("Test_destination_not_exists").toFile();

        fileExists = source.toPath().resolve("Test_file_exists_empty.txt").toFile();
        fileExists.createNewFile();

        File otherDirectory= source.toPath().resolve("Other directory").toFile();
        otherDirectory.mkdir();

        fileExistsOtherDirectory= otherDirectory.toPath().resolve(fileExists.getName()).toFile();
        fileExistsOtherDirectory.createNewFile();

        otherFileExists = source.toPath().resolve("Test_file_exists_other_empty.txt").toFile();
        otherFileExists.createNewFile();

        fileNotExists= source.toPath().resolve("Test_file_not_exists_empty.txt").toFile();
    }

    @AfterAll
    void deleteAll() throws IOException {
        FileUtils.deleteDirectory(base);
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