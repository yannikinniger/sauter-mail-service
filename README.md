# sauter-mail-service

Simple email service to send predefined emails for a very specific use case.

## Setup

Requirements:
- Docker
- Access to an SMTP Server

1. `docker build -t mail-service .`
2. `docker run --env-file env_file -p 4567:4567 mail-service`

### env_file

The env_file does have the following variables:

- SMTP_USER_NAME username of the smtp server  
- SMTP_PASSWORD password of the smtp server  
- ORDER_EMAIL email the incoming order should be sent to  
- SMTP_HOST hostname of the smtp server
- SMTP_PORT [optional] port the smtp server listens to, default is 465
- REPLY_TO_ADDRESS [optional] email address which should be set as reply to  

### Template

To send an Email the Mailer takes a template as an argument. You can customize the template by writing a new MailTemplate and passing it as an Argument.

e.g.
```kotlin
val customTemplate: MailTemplate = { order ->
    div {
        h2("New Template") +
        p("Text")
    }.render()
}
```
