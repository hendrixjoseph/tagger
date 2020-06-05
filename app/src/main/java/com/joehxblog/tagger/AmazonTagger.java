package com.joehxblog.tagger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmazonTagger {

    final private List<String> tags = new ArrayList<>();
    private String currentTag;

    public void addTag(String tag) {
        if (this.tags.isEmpty()) {
            this.currentTag = tag;
        }

        this.tags.add(tag);
    }

    public List<String> getTags() {
        return tags;
    }

    public String getCurrentTag() {
        return currentTag;
    }

    public void setCurrentTag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }

        this.currentTag = tag;
    }

    public String tag(String string) {
        Pattern pattern = Pattern.compile("(https:\\/\\/www\\.amazon\\.com[^\\s]*)");
        Matcher matcher = pattern.matcher(string);
        StringBuffer stringBuffer = new StringBuffer();

        while (matcher.find()) {
            String match = matcher.group();
            if (!match.endsWith("png")) {
                matcher.appendReplacement(stringBuffer, tagUrl(match));
            }
        }

        matcher.appendTail(stringBuffer);

        return stringBuffer.toString();
    }

    private String tagUrl(String url) {
        String tag = "tag=" + currentTag;

        String newUrl;

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
