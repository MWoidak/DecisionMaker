package com.bidasco;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DecisionMaker {

    private static final Scanner inputReader = new Scanner(System.in);
    private static final PrintStream outputWriter = System.out;
    private static final String DELAY_DOT = ". ";
    private static final String VERSION = "v1.1";
    private static final String NOT_HUNGRY = "You lucky thing! See you soon .. ;)";
    private static final String UNKNOWN_ANSWER = "Oops, fallen asleep on the keyboard!? Try again.";
    private static final int DELAY_TIMEOUT = 1000;
    private static final int DELAY_SCALE = 3;

    public static void main(String[] args) throws InterruptedException {
        printInfo();

        String answer;
        boolean repeat;
        do {
            repeat = false;
            writeln("Are you hungry? (y/n)");
            answer = inputReader.nextLine();
            outputWriter.println();

            if ("y".equalsIgnoreCase(answer) || "yes".equalsIgnoreCase(answer)) {
                proceedProposal();
            } else if ("n".equalsIgnoreCase(answer)) {
                writeln(NOT_HUNGRY);
            } else {
                writeln(UNKNOWN_ANSWER);
                repeat = true;
            }
        } while (answer.isEmpty() || repeat);

        writeln();
        writeln("(Press ENTER to exit.)");
        inputReader.nextLine();
    }

    /**
     * Prints an info about this application.
     */
    private static void printInfo() {
        writeln("Hi this is DecisionMaker " + VERSION);
        writeln("I am here to help you choosing your destination for lunch today.");
        writeln("So let's start.");
        writeln();
    }

    /**
     * Ensures that the application is delayed by 5 * 1000 milliseconds, whereby a "." (dot) is printed in every step.
     *
     * @throws InterruptedException in case the {@link Thread#sleep(long)} throws one.
     */
    private static void dottedDelay() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            write(DELAY_DOT);

            Thread.sleep(DELAY_TIMEOUT);
        }
    }

    /**
     * Ensures that the application is delayed by 2000 milliseconds.
     *
     * @throws InterruptedException in case the {@link Thread#sleep(long)} throws one.
     */
    private static void delay() throws InterruptedException {
        Thread.sleep(DELAY_TIMEOUT * DELAY_SCALE);
    }

    /**
     * Gets a random proposal.
     *
     * @throws InterruptedException In case the delays are interrupted.
     */
    private static void randomProposal() throws InterruptedException {
        randomProposal(new ArrayList<>());
    }

    /**
     * Gets a random proposal by excluding the given ones.
     *
     * @param excludes The list of {@link Proposal}s that will be excluded.
     * @throws InterruptedException In case the delays are interrupted.
     */
    private static void randomProposal(List<Proposal> excludes) throws InterruptedException {
        writeln("And the winner is ");
        dottedDelay();
        Proposal randomProposal;
        do {
            randomProposal = Proposal.getRandomProposal();
        } while (excludes.contains(randomProposal));
        outputWriter.println(randomProposal);
        delay();
        outputWriter.println("Fate has decided!");
    }

    /**
     * Proceeds with the random proposal in regard of to be specified excludes.
     *
     * @throws InterruptedException In case the delays are interrupted.
     */
    private static void proposalInRegardOfExclude() throws InterruptedException {
        writeln("Here are the options:");
        int counter = 0;
        final Proposal[] proposals = Proposal.values();
        for (Proposal proposal : proposals) {
            final String proposalString = proposal.toString();
            writeln(counter++ + ") " + proposalString);
        }

        writeln("Please specify the one(s) you want to exclude:");
        String answer = inputReader.nextLine();
        List<Proposal> answers = new ArrayList<>();
        do {
            final Integer intValueOfAnswer = Integer.valueOf(answer);
            answers.add(proposals[intValueOfAnswer]);
            answer = inputReader.nextLine();
        } while (!answer.isEmpty());

        randomProposal(answers);
    }

    /**
     * Proceeds depending on the option the user chooses.
     *
     * @throws InterruptedException In case the delays are interrupted.
     */
    private static void proceedProposal() throws InterruptedException {
        String answer;
        Option chosenOption = askOption();
        do {
            switch (chosenOption) {
                case DEFAULT:
                    randomProposal();
                    break;
                case BY_EXCLUDE:
                    proposalInRegardOfExclude();
                    break;
                default:
                    throw new IllegalStateException("Option unknown!");
            }
            outputWriter.println("Not excited, try again !? (y/n)");
            answer = inputReader.nextLine();
        } while ("y".equalsIgnoreCase(answer) || "yes".equalsIgnoreCase(answer));
    }

    /**
     * Asks for the option how to proceed with the application.
     *
     * @return The {@link Option} the user has chosen.
     */
    private static Option askOption() {
        final String enterOptionMsg = "Please enter an option how to proceed:";
        String[] optionMsgs = new String[]{"0) DEFAULT - you will get a random proposal.",
                "1) EXCLUDE - you will get a random proposal, whereby you can exclude options."};

        outputWriter.println(enterOptionMsg);
        for (String msg : optionMsgs) {
            outputWriter.println(msg);
        }
        String answer = inputReader.nextLine();
        for (Option option : Option.values()) {
            if (option.ordinal() == Integer.valueOf(answer)) {
                return option;
            }
        }

        throw new IllegalStateException("Invalid option chosen.");
    }

    /**
     * Writes an empty line to the Console ended by a carriage return.
     */
    private static void writeln() {
        outputWriter.println();
    }

    /**
     * Writes the given string to the Console ended by a carriage return.
     *
     * @param string The string that will be given.
     */
    private static void writeln(String string) {
        outputWriter.println(string);
    }

    /**
     * Writes the given string to the Console.
     *
     * @param string The string that will be given.
     */
    private static void write(String string) {
        outputWriter.print(string);
    }
}
