package com.joehxblog.tagger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmazonTagger {

    private final String tag;

    public AmazonTagger(final String tag) {
        this.tag = tag;
    }

    public String tag(final String string) {
        final Pattern pattern = Pattern.compile("(https:\\/\\/.+?\\.amazon\\.[^\\s]*)");
        final Matcher matcher = pattern.matcher(string);
        final StringBuffer stringBuffer = new StringBuffer();

        while (matcher.find()) {
            final String match = matcher.group();
            if (!match.endsWith("png")) {
                matcher.appendReplacement(stringBuffer, tagUrl(match));
            }
        }

        matcher.appendTail(stringBuffer);

        return stringBuffer.toString();
    }

    private String tagUrl(final String url) {
        final String tag = "tag=" + this.tag;

        final String newUrl;

        if (url.contains("tag=")) {
            newUrl = url.replaceAll("tag=.+?(&|$)", tag + "$1");
        } else if (url.contains("?")) {
            newUrl = url + "&" + tag;
        } else {
            newUrl = url + "?" + tag;
        }

        return newUrl;
    }
}
