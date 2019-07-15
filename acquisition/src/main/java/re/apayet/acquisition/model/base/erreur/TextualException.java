package re.apayet.acquisition.model.base.erreur;

public enum TextualException {

    ABSTRACT {
        public String toString() {
            return "ABSTRACT method";
        }
    },
    FRONT {
        public String toString() {
            return "FRONT method";
        }
    },
    BACK {
        public String toString() {
            return "BACK Method";
        }
    },
    PARAM {
        public String toString() {
            return "Parameter";
        }
    },
    CALL {
        public String toString() {
            return "Call";
        }
    },
    NULL {
        public String toString() {
            return "None instantiate";
        }
    },
    EMPTY {
        public String toString() {
            return "Empty";
        }
    },
}
