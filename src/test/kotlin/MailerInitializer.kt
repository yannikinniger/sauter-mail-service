
fun initMailer(): Mailer {
    val userName = System.getenv("ZOHO_USER_NAME")
    val password = System.getenv("ZOHO_PASSWORD")
    if (userName == null || password == null) {
        System.err.println("missing arguments")
        System.exit(1)
    }
    return Mailer(userName, password)
}