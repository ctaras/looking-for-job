package ua.chuchvaga.taras.lookingjob.util

class ErrorLogBuilder {
    StringBuilder builder;

    ErrorLogBuilder() {
        builder = new StringBuilder();
    }

    ErrorLogBuilder add(String msg) {
        builder.append(msg)
        builder.append(sprintf("%n"))
        this
    }

    String build() {
        builder.toString()
    }

    ErrorLogBuilder add(k, v) {
        builder.append(sprintf('%1$20s : %2$s%n', [k, v]))
        this
    }
}
