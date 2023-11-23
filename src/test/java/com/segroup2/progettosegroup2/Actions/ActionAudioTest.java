package com.segroup2.progettosegroup2.Actions;

import com.segroup2.progettosegroup2.HelloApplication;
import javafx.application.Platform;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class ActionAudioTest {
    private static ActionAudio action;
    private static File copyOfFile;
    private static File realFile;

    @BeforeAll
    static void beforeAll() {
        Platform.startup(()->{});
        action= new ActionAudio();

        //copy of the orignal file
        try{
            realFile= new File( HelloApplication.class.getResource("Audio/default_audio.wav").toURI() );
            copyOfFile= copyFile( realFile.toPath(), Path.of(realFile.getParentFile().toPath().toString(), "temp_audio_test_filewav") );
        }catch(URISyntaxException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Copy of a file
     * @param src path of the source file
     * @param dest path of the destination file
     * @return the destination file
     */
    private static File copyFile(Path src, Path dest){
        try{
            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
            return new File(dest.toUri());
        }catch( IOException e){
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void afterAll() {
        //delete the file copied and restore the original file
        if( !realFile.exists() ){
            copyFile(copyOfFile.toPath(), realFile.toPath());
        }
        copyOfFile.delete();

        Platform.exit();
    }

    @Test
    void execute() {
        realFile.delete();
        assertFalse(action.execute());
        copyFile(copyOfFile.toPath(), realFile.toPath());

        assertTrue(action.execute());
    }
}