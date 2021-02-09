package com.yushkevich.metrics.commons.message;

import java.time.LocalDateTime;
import java.util.Objects;

public class OSMetric {
    private String description;
    private String name;
    private Double value;
    private LocalDateTime createdAt;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Builder newBuilder() {
        return new OSMetric().new Builder();
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public class Builder {

        private Builder() {
        }

        public Builder withDescription(String description) {
            OSMetric.this.description = description;

            return this;
        }

        public Builder withName(String name) {
            OSMetric.this.name = name;

            return this;
        }

        public Builder withValue(Double value) {
            OSMetric.this.value = value;

            return this;
        }

        public Builder withCreatedAt(LocalDateTime createdAt) {
            OSMetric.this.createdAt = createdAt;

            return this;
        }

        public OSMetric build() {
            return OSMetric.this;
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OSMetric osMetric = (OSMetric) o;
        return description.equals(osMetric.description) &&
                name.equals(osMetric.name) &&
                value.equals(osMetric.value) &&
                createdAt.equals(osMetric.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, name, value, createdAt);
    }

    @Override
    public String toString() {
        return "OSMetric{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", createdAt=" + createdAt +
                '}';
    }
}
