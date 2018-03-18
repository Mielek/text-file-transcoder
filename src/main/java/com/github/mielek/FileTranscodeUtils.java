package com.github.mielek;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileTranscodeUtils {

    public static void transcodeFile(Path source, Charset decode, Path target, Charset encode) throws IOException {
        try(FileChannel reader = FileChannel.open(source, StandardOpenOption.READ);
            FileChannel writer = FileChannel.open(target, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE, StandardOpenOption.WRITE)){
            ByteBuffer buffer = ByteBuffer.allocate((int) reader.size());
            long size = Files.size(source);
            while(reader.position() < size){
                reader.read(buffer);
                buffer.flip();
                writer.write(encode.encode(decode.decode(buffer)));
            }
        }
    }
}
