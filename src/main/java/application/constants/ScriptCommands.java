package application.constants;

public enum ScriptCommands {
    REG_USER {
        @Override
        public boolean executeCommand(String expression) {
            String userData = expression.substring(expression.indexOf(':') + 1);
            String[] splittedUserData = userData.split("|");
            String fullName = splittedUserData[0];
            String eMail = splittedUserData[1];
            String phoneNumber = splittedUserData[2];
            return false;
//            return expression.substring(expression.indexOf(':') + 1);
        }
    },
    ADD_ADDR {
        @Override
        public boolean executeCommand(String expression) {
            return false;
//            return expression.substring(expression.indexOf(':') + 1);
        }
    },
    ADD_TMPL {
        @Override
        public boolean executeCommand(String expression) {
            return false;
//            return expression.substring(expression.indexOf(':') + 1);
        }
    },
    ADD_PMNT {
        @Override
        public boolean executeCommand(String expression) {
            return false;
//            return expression.substring(expression.indexOf(':') + 1);
        }
    };

    public abstract boolean executeCommand(String expression);
}
