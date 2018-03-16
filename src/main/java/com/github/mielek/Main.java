package com.github.mielek;

import org.apache.commons.cli.*;
import java.nio.charset.Charset;

public class Main {
    private static final Option fileOpt = Option.builder("f")
            .longOpt("file")
            .required()
            .hasArg()
            .type(String.class)
            .argName("FILE")
            .desc("Sets text file to transcode.")
            .build();
    private static final Option dirOpt = Option.builder("d")
            .longOpt("dir")
            .required()
            .hasArg()
            .type(String.class)
            .argName("DIR")
            .desc("Sets directory of files to transcode.")
            .build();
    private static final Option decodeOpt = Option.builder()
            .longOpt("charset-decode")
            .required()
            .hasArg()
            .type(String.class)
            .argName("CHARSET")
            .desc("Sets charset of the file.")
            .build();
    private static final Option encodeOpt = Option.builder()
            .longOpt("charset-encode")
            .hasArg()
            .type(String.class)
            .argName("CHARSET")
            .desc("Sets charset for result file. Default value is \"UTF-8\".")
            .build();
    private static final Option renameOpt = Option.builder()
            .longOpt("rename")
            .desc("Used with -f, --file option. Rename result file.")
            .build();
    private static final Option recursiveOpt = Option.builder("r")
            .desc("Used with -d, --dir option. Transcode files in child catalogs.")
            .build();
    private static final Option threadsOpt = Option.builder("t")
            .longOpt("threads")
            .required(false)
            .hasArg()
            .argName("NUMBER")
            .type(Integer.class)
            .desc("Sets number of threads used to transcode file.")
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
            OptionGroup group = new OptionGroup().addOption(fileOpt).addOption(dirOpt);
            group.setRequired(true);
            Options cliOptions = new Options().addOptionGroup(group).addOption(decodeOpt).addOption(encodeOpt).addOption(renameOpt).addOption(threadsOpt);
            cliLine = parser.parse(cliOptions, args, true);
            try {
                if (cliLine.hasOption(fileOpt.getOpt())) {
                    FileTranscodeUtils.transcodeFile();
                } else if (cliLine.hasOption(dirOpt.getLongOpt())) {
                    FileTranscodeUtils.transcodeFilesInDir();
                }
            }catch (TranscodeException e){

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
        OptionGroup group = new OptionGroup().addOption(fileOpt).addOption(dirOpt);
        group.setRequired(true);
        Options all = new Options()
                .addOptionGroup(group)
                .addOption(decodeOpt)
                .addOption(encodeOpt)
                .addOption(renameOpt)
                .addOption(threadsOpt)
                .addOption(listOpt)
                .addOption(helpOpt);
        new HelpFormatter().printHelp("transcoder", "TODO: header", all, "TODO: footer",true);
    }
}
