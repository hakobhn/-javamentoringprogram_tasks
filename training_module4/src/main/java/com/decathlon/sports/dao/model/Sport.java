package com.decathlon.sports.dao.model;

import com.decathlon.sports.dao.AbstractModel;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * An entity to represent a Person.
 *
 * @author Mark Paluch
 */
@Document
public class Sport extends AbstractModel<Integer> {

    @Indexed(unique=true)
    private String name;

    private String description;
    private String slug;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String toString() {
        return "Sport{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", slug='" + slug + '\'' +
                ", id=" + id +
                ", createdAt=" + createdAt +
                '}';
    }
}