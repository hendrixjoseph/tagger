package com.joehxblog.tagger;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AmazonTaggerTest {

    static final String EXAMPLE_TAG = "example-20";

    private final AmazonTagger tagger = new AmazonTagger(EXAMPLE_TAG);

    static Stream<Arguments> differentUrls(final Function<Object, String> newString) {
        return urls().map(Arguments::get)
                     .map(o -> arguments(newString.apply(o[0]),
                                         newString.apply(o[1])));
    }

    static Stream<Arguments> textWithUrls() {
        final String beforeText = "This is some text before. ";
        final String afterText = " This is some text after.";

        return differentUrls(o -> beforeText + o + afterText);
    }

    static Stream<Arguments> smileUrls() {
        return differentUrls(o -> o.toString().replace("https://www", "https://smile"));
    }

    static Stream<Arguments> longTextWithUrls() {
        final BiFunction<Stream<Arguments>, Integer, String> toString
                = (s, i) -> s.map(Arguments::get)
                             .map(o -> o[i])
                             .map(Object::toString)
                             .collect(Collectors.joining(" some text "));

        final String oldString = toString.apply(urls(), 0);
        final String newString = toString.apply(urls(), 1);

        return Stream.of(arguments(oldString, newString));
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
    @MethodSource({"urls","textWithUrls","longTextWithUrls","smileUrls"})
    void testTag(final String input, final String output) {
        final String tagged = this.tagger.tag(input);

        assertEquals(output, tagged);
    }
}
