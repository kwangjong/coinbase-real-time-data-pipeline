FROM golang:latest
COPY . .
RUN go build
CMD ./main