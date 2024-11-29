package com.example.androidlab;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an RSS item with a title, date, description, and a link.
 */
public class RSSItem {

    private String title;      // The title of the RSS item
    private String date;       // The date the RSS item was published
    private String description; // A description of the RSS item
    private String link;       // The link associated with the RSS item

    /**
     * Default constructor for RSSItem.
     */
    public RSSItem() {
    }

    // A static list to hold RSSItem objects
    public static List<RSSItem> items = new ArrayList<>();

    /**
     * Constructor to initialize an RSSItem with specified values.
     *
     * @param title       The title of the RSS item.
     * @param date        The publication date of the RSS item.
     * @param description A description of the RSS item.
     * @param link        The link associated with the RSS item.
     */
    public RSSItem(String title, String date, String description, String link) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.link = link;
    }

    /**
     * Gets the title of the RSS item.
     *
     * @return The title of the RSS item.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the RSS item.
     *
     * @param title The new title for the RSS item.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the publication date of the RSS item.
     *
     * @return The publication date of the RSS item.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the publication date of the RSS item.
     *
     * @param date The new publication date for the RSS item.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the description of the RSS item.
     *
     * @return The description of the RSS item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the RSS item.
     *
     * @param description The new description for the RSS item.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the link associated with the RSS item.
     *
     * @return The link associated with the RSS item.
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the link associated with the RSS item.
     *
     * @param link The new link for the RSS item.
     */
    public void setLink(String link) {
        this.link = link;
    }
}
