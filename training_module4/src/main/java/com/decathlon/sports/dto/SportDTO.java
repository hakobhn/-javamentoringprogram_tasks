package com.decathlon.sports.dto;

public class SportDTO {
    private int id;
    private AttributesDTO attributes;

    public SportDTO() {
    }

    public SportDTO(int id, AttributesDTO attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AttributesDTO getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesDTO attributes) {
        this.attributes = attributes;
    }

    public static class AttributesDTO {
        private String name;
        private String description;
        private String slug;

        public AttributesDTO() {
        }

        public AttributesDTO(String name) {
            this.name = name;
        }

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
            return "AttributesDTO{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SportDTO{" +
                "id=" + id +
                ", attributes=" + attributes +
                '}';
    }
}
