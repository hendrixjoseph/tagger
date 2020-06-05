package com.joehxblog.tagger;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AmazonTaggerTest {

    static final String EXAMPLE_TAG = "example-20";

    private final AmazonTagger tagger = new AmazonTagger(EXAMPLE_TAG);

    static Stream<Arguments> textWithUrls() {
        String beforeText = "This is some text before. ";
        String afterText = " This is some text after.";

        Function<Object, String> newString = o -> beforeText + o + afterText;

        return urls().map(args -> arguments(newString.apply(args.get()[0]), newString.apply(args.get()[1])));
    }

    static Stream<Arguments> urls() {
        return Stream.of(
                arguments("https://www.amazon.com/", "https://www.amazon.com/?tag=example-20"),
                arguments("https://www.amazon.com/dp/1707062951", "https://www.amazon.com/dp/1707062951?tag=example-20"),
                arguments("https://www.amazon.com/100-Sudoku-Puzzles-Solutions-Included/dp/1707062951/ref=sr_1_1?dchild=1&keywords=100+Sudoku+Puzzles%3A+25+Easy+%C2%B7+25+Medium+%C2%B7+25+Hard+%C2%B7+25+Very+Hard+with+Solutions+Included&qid=1591322282&s=books&sr=1-1" , "https://www.amazon.com/100-Sudoku-Puzzles-Solutions-Included/dp/1707062951/ref=sr_1_1?dchild=1&keywords=100+Sudoku+Puzzles%3A+25+Easy+%C2%B7+25+Medium+%C2%B7+25+Hard+%C2%B7+25+Very+Hard+with+Solutions+Included&qid=1591322282&s=books&sr=1-1&tag=example-20"),
                arguments("https://www.amazon.com/ref=as_li_ss_tl?ie=UTF8&linkCode=ll2&tag=someoneelses-20", "https://www.amazon.com/ref=as_li_ss_tl?ie=UTF8&linkCode=ll2&tag=example-20"),
                arguments("https://www.amazon.com/ref=as_li_ss_tl?ie=UTF8&linkCode=ll2&tag=someoneelses-20&linkId=b68ce2e09aaa16fba7f9f85658668596&language=en_US", "https://www.amazon.com/ref=as_li_ss_tl?ie=UTF8&linkCode=ll2&tag=example-20&linkId=b68ce2e09aaa16fba7f9f85658668596&language=en_US")
        );
    }

    @ParameterizedTest
    @MethodSource({"urls","textWithUrls"})
    void testTag(final String input, final String output) {
        final String tagged = this.tagger.tag(input);

        assertEquals(output, tagged);
    }
}
