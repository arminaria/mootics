package de.tu.berlin.control;

/**
 * Set the positions in the parser
 *
 * User: ara
 * Date: 10.12.13
 */
public enum POS {

    id {
        @Override
        public int get() {
            return 0;
        }
    },
    time {
        @Override
        public int get() {
            return 1;
        }
    },
    userId {
        @Override
        public int get() {
            return 2;
        }
    },
    course {
        @Override
        public int get() {
            return 3;
        }
    },
    module {
        @Override
        public int get() {
            return 4;
        }
    },
    cmid {
        @Override
        public int get() {
            return 5;
        }
    },
    action {
        @Override
        public int get() {
            return 6;
        }
    },
    url {
        @Override
        public int get() {
            return 7;
        }
    },
    info {
        @Override
        public int get() {
            return 8;
        }
    },;

    public abstract int get();
}
