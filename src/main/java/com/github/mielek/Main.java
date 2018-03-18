package com.github.mielek;

import org.apache.commons.cli.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    private static final Option fileOpt = Option.builder("f")
            .longOpt("file")
            .required()
            .hasArg()
            .type(String.class)
            .argName("FILE")
            .desc("Sets text file to transcode.")
            .build();
    private static final Option decodeOpt = Option.builder("d")
            .longOpt("charset-decode")
            .required()
            .hasArg()
            .type(String.class)
            .argName("CHARSET")
            .desc("Sets charset of the file.")
            .build();
    private static final Option encodeOpt = Option.builder("e")
            .longOpt("charset-encode")
            .hasArg()
            .type(String.class)
            .argName("CHARSET")
            .desc("Sets charset for result file. Default value is \"UTF-8\".")
            .build();
    private static final Option renameOpt = Option.builder("r")
            .required()
            .longOpt("rename")
            .hasArg()
            .argName("NEW_NAME")
            .desc("Rename result file.")
            .build();
    private static final Option helpOpt = Option.builder().longOpt("help")
            .desc("Shows help.")
            .build();
    private static  final  Option listOpt =  Option.builder()
            .longOpt("list-charset")
            .desc("Show all available charsets.")
            .build();

    public static void main(String... args) {
        DefaultParser parser = new DefaultParser();
        try {
            CommandLine cliLine = parser.parse(new Options().addOption(helpOpt), args, true);
            if (cliLine.hasOption(helpOpt.getLongOpt())) {
                showHelp();
                return;
            }
            cliLine = parser.parse(new Options().addOption(listOpt), args, true);
            if (cliLine.hasOption(listOpt.getLongOpt())) {
                showCharsets();
                return;
            }
            Options cliOptions = new Options().addOption(fileOpt).addOption(decodeOpt).addOption(renameOpt).addOption(encodeOpt);
            cliLine = parser.parse(cliOptions, args, true);
            try {
                Path source = Paths.get(cliLine.getOptionValue(fileOpt.getOpt()));
                Path target = Paths.get(cliLine.getOptionValue(renameOpt.getOpt()));
                Charset decode = Charset.forName(cliLine.getOptionValue(decodeOpt.getOpt()));
                Charset encode = Charset.forName("UTF-8");
                if(cliLine.hasOption(encodeOpt.getOpt())){
                    encode = Charset.forName(cliLine.getOptionValue(encodeOpt.getOpt()));
                }
                if(!source.equals(target)){
                    FileTranscodeUtils.transcodeFile(source,decode,target,encode);
                } else {
                    System.err.println("File to transcode cannot be the same as result file");
                }
            }catch (IOException e){
                System.err.println(e.getMessage());
            }catch (UnsupportedCharsetException e){
                System.err.println(e.getMessage());
                showCharsets();
            }
        } catch (ParseException e) {
            System.err.println("Error parsing arguments. " + e.getMessage());
            showHelp();
        }
    }

    private static void showCharsets() {
        System.out.println("Available charsets:");
        Charset.availableCharsets().keySet().stream().forEach(System.out::println);
    }

    private static void showHelp() {
        Options all = new Options()
                .addOption(fileOpt)
                .addOption(decodeOpt)
                .addOption(renameOpt)
                .addOption(encodeOpt)
                .addOption(listOpt)
                .addOption(helpOpt);
        new HelpFormatter().printHelp("transcoder", "Simple text file transcoding application.", all, "Created by Rafal Mielowski",true);
    }
}
