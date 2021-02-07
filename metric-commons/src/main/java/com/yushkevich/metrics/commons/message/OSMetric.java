package com.yushkevich.metrics.commons.message;

import java.time.LocalDateTime;
import java.util.Objects;

public class OSMetric {
    private String description;
    private String name;
    private Double value;
    private LocalDateTime time;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Double getValue() {
        return value;
    }

    public LocalDateTime getTime() {
        return time;
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

    public void setTime(LocalDateTime time) {
        this.time = time;
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

        public Builder withTime(LocalDateTime time) {
            OSMetric.this.time = time;

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
                time.equals(osMetric.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, name, value, time);
    }

    @Override
    public String toString() {
        return "OSMetric{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", time=" + time +
                '}';
    }
}
