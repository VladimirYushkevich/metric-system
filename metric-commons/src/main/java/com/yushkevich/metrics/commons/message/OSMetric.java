package com.yushkevich.metrics.commons.message;

import java.time.LocalDateTime;
import java.util.Objects;

public class OSMetric {
    private String description;
    private String name;
    private Double value;
    private LocalDateTime createdAt;
    private String env;

    public static Builder newBuilder() {
        return new OSMetric().new Builder();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OSMetric osMetric = (OSMetric) o;
        return description.equals(osMetric.description) &&
                name.equals(osMetric.name) &&
                value.equals(osMetric.value) &&
                env.equals(osMetric.env) &&
                createdAt.equals(osMetric.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, name, value, createdAt, env);
    }

    @Override
    public String toString() {
        return "OSMetric{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", createdAt=" + createdAt +
                ", env=" + env +
                '}';
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

        public Builder withEnv(String env) {
            OSMetric.this.env = env;

            return this;
        }

        public OSMetric build() {
            return OSMetric.this;
        }

    }
}
