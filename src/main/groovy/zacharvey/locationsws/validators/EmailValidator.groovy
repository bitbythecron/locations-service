package zacharvey.locationsws.validators

import java.util.regex.Matcher
import java.util.regex.Pattern

class EmailValidator {
    boolean isValidEmail(String email) {
        if(!email || email.isEmpty()) {
            throw new IllegalArgumentException('Email is invalid!')
        }

        Pattern pattern = Pattern.compile('^.+@.+(\\.[^\\.]+)+$')
        Matcher matcher = pattern.matcher(email)

        matcher.matches()
    }
}
