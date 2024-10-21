package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FULLTAGNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddShortCutCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.shortcut.Alias;
import seedu.address.model.shortcut.FullTagName;
import seedu.address.model.shortcut.ShortCut;

/**
 * ShortTagCommand Parser to parse the arguments of the short tag command
 */
public class AddShortCutCommandParser implements Parser<AddShortCutCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ShortTagCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddShortCutCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALIAS, PREFIX_FULLTAGNAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_ALIAS, PREFIX_FULLTAGNAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddShortCutCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ALIAS, PREFIX_FULLTAGNAME);
        FullTagName tagName = ParserUtil.parseFullTagName(argMultimap.getValue(PREFIX_FULLTAGNAME).get());
        Alias alias = ParserUtil.parseAlias(argMultimap.getValue(PREFIX_ALIAS).get());
        return new AddShortCutCommand(new ShortCut(alias, tagName));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
