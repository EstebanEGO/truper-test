package com.truper.test.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class ErrorResponse {

    public static class Builder {
        private Integer statusCode;
        private String detail;
        private final Map<String, Object> properties = new HashMap<>();

        private ProblemDetail problemDetail;

        public Builder statusCode(Integer statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder addProperty(String name, Object value) {
            this.properties.put(name, value);
            return this;
        }

        public ProblemDetail build() {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(this.statusCode), this.detail);
            properties.put("datetime", LocalDateTime.now());
            properties.forEach(problemDetail::setProperty);

            return problemDetail;
        }
    }
}
